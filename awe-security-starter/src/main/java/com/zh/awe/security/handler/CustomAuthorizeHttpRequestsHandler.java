package com.zh.awe.security.handler;

import com.zh.awe.security.config.WebSecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * 自定义访问请求处理
 * 通过参数控制是否添加spring security安全控制，控制白名单
 * @author zh 2023/7/8 2:58
 */
@AllArgsConstructor
public class CustomAuthorizeHttpRequestsHandler implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {
    private WebSecurityProperties webSecurityProperties;

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        if (webSecurityProperties.getNeed()){
            registry.requestMatchers(HttpMethod.GET,
                            "/",
                            "/favicon.ico",
                            "/**.html",
                            "/**.css",
                            "/**.js")
                    .permitAll()
//                    .requestMatchers(HttpMethod.POST,webSecurityProperties.getLoginUri(),webSecurityProperties.getLogoutUri())
//                    .permitAll()
                    .requestMatchers(
                            "/v2/api-docs",
                            "/v3/api-docs/**",
                            "/configuration/ui",
                            "/swagger-resources",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/swagger-ui/**",
                            "/doc.html",
                            "/doc.html/**",
                            "/doc.html#/**")
                    .permitAll()
                    .requestMatchers(webSecurityProperties.getWhitelist().toArray(new String[0]))
                    .permitAll()
                    .anyRequest()
                    .authenticated();
            // TODO: 2023/7/30 动态url权限控制
        } else {
            registry.anyRequest().permitAll();
        }
    }
}
