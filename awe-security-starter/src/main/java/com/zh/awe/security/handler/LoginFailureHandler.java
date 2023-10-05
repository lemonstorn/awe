package com.zh.awe.security.handler;

import com.zh.awe.common.enums.CodeEnum;
import com.zh.awe.common.model.R;
import com.zh.awe.web.util.WebContextUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 系统异常显示
 * @author zh 2023/7/9 22:02
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        R<String> result;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result = R.error(CodeEnum.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result = R.error(CodeEnum.USER_CREDENTIALS_ERROR);
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result = R.error(CodeEnum.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof DisabledException) {
            //账号不可用
            result = R.error(CodeEnum.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException) {
            //账号锁定
            result = R.error(CodeEnum.USER_ACCOUNT_LOCKED);
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = R.error(CodeEnum.USER_ACCOUNT_NOT_EXIST);
        }else{
            //其他错误
            result = R.error(CodeEnum.Fail);
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        WebContextUtil.responseOutWithJson(response, result);
    }
}
