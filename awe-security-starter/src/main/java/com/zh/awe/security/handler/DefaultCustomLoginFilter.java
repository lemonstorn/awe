package com.zh.awe.security.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zh.awe.security.config.WebSecurityProperties;
import com.zh.awe.security.constants.AuthConstants;
import com.zh.awe.security.enums.OrderType;
import com.zh.awe.web.factory.WebBeanFactory;
import com.zh.awe.web.util.WebContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 示例filter类
 * 替换登录filter {@link UsernamePasswordAuthenticationFilter}
 * @author zh 2023/7/8 13:58
 */
@Component
public class DefaultCustomLoginFilter extends UsernamePasswordAuthenticationFilter implements CustomFilter{

    public DefaultCustomLoginFilter(AuthenticationManager authenticationManager,
                                    WebBeanFactory webBeanFactory, // 为了自动注入，防止出现webBeanFactory找不到的情况
                                    WebSecurityProperties properties) {
        super(authenticationManager);
        String loginUri = properties.getLoginUri();
        if (StringUtils.isBlank(loginUri)){
            loginUri = AuthConstants.LOGIN_URL;
        } else if (!loginUri.startsWith("/")){
            loginUri = "/" + loginUri;
        }
        setFilterProcessesUrl(loginUri);
        setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        setAuthenticationSuccessHandler(WebBeanFactory.getBean(AuthenticationSuccessHandler.class));
        setAuthenticationFailureHandler(WebBeanFactory.getBean(AuthenticationFailureHandler.class));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = null;
        String password = null;
        //json请求放在请求体中
        if (WebContextUtil.isAjax(request)) {
            try {
                Map<String, String> map = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<>() {
                });
                username = map.get(getUsernameParameter());
                password = map.get(getPasswordParameter());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //get请求放在请求参数中
            username = obtainUsername(request);
            password = obtainPassword(request);
        }

        username = (username != null) ? username.trim() : "";
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    @Override
    public FilterProvider custom() {
        return new FilterProvider(OrderType.Replace, UsernamePasswordAuthenticationFilter.class,this);
    }
}
