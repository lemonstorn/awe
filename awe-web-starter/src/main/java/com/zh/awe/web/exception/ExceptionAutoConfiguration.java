package com.zh.awe.web.exception;

import com.zh.awe.common.model.R;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;
import java.util.Set;

/**
 * 全局异常处理
 * 核心是@ControllerAdvice和@Configuration
 * @author zh 2023/7/2 15:32
 */
@Configuration
@ControllerAdvice
@Slf4j
@EnableConfigurationProperties(ExceptionProperties.class)
@AllArgsConstructor
public class ExceptionAutoConfiguration {
    private ExceptionProperties exceptionProperties;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handleException(Exception e) {
        if (e instanceof AweBaseException bootCustomException) {
            return R.error(bootCustomException.getMessage());
        } else if (e instanceof BindException) {
            BindingResult result = ((BindException) e).getBindingResult();
            return R.error(handlerNotValidException(result));
        }
        else if (e instanceof ConstraintViolationException) {
            return R.error(getConstraintViolationMessage((ConstraintViolationException) e));
        }
        else {
            log.error(e.getMessage(), e);
            if (!exceptionProperties.getFlag()) {
                return R.error("【系统异常】");
            }
            return R.error(e.getMessage());
        }
    }

    private String handlerNotValidException(BindingResult result) {
        if (result.hasErrors()) {
            return Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
        }
        return null;
    }

    private static String getConstraintViolationMessage(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (constraintViolations.isEmpty()) {
            return null;
        }
        ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
        return constraintViolation.getMessage();
    }
}
