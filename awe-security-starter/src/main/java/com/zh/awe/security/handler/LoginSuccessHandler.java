package com.zh.awe.security.handler;

import com.zh.awe.common.model.R;
import com.zh.awe.web.util.WebContextUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 自定义登录成功处理器
 * @author zh 2023/7/9 22:02
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        WebContextUtil.responseOutWithJson(response, R.ok("登录成功"));
    }
}
