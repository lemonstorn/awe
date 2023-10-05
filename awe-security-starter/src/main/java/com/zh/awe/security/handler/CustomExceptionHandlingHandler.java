package com.zh.awe.security.handler;

import com.zh.awe.common.enums.CodeEnum;
import com.zh.awe.common.model.R;
import com.zh.awe.web.util.WebContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

/**
 * @author zh 2023/7/8 4:34
 */
public class CustomExceptionHandlingHandler implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        // 未登录时的处理 返回401
        configurer.accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    WebContextUtil.responseOutWithJson(response, R.error(CodeEnum.USER_NOT_LOGIN));
                }))
                //匿名用户访问无权限资源时的异常处理 返回403
                .authenticationEntryPoint(((request, response, authException) -> {
                    authException.printStackTrace();
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    WebContextUtil.responseOutWithJson(response,R.error(CodeEnum.NO_PERMISSION));
                }));
    }
}
