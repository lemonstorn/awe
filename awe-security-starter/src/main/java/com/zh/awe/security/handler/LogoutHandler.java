package com.zh.awe.security.handler;

import com.zh.awe.common.model.R;
import com.zh.awe.security.config.WebSecurityProperties;
import com.zh.awe.security.constants.AuthConstants;
import com.zh.awe.web.util.WebContextUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

/**
 * 自定义登出处理器
 * @author zh 2023/7/8 4:04
 */
@AllArgsConstructor
public class LogoutHandler implements Customizer<LogoutConfigurer<HttpSecurity>> {
    private WebSecurityProperties webSecurityProperties;
    @Override
    public void customize(LogoutConfigurer<HttpSecurity> configurer) {
        String logoutUri = webSecurityProperties.getLogoutUri();
        if (StringUtils.isBlank(logoutUri)){
            logoutUri = AuthConstants.LOGOUT_URL;
        } else if (!logoutUri.startsWith("/")){
            logoutUri = "/" + logoutUri;
        }
        configurer.logoutUrl(logoutUri)
                .logoutSuccessHandler(((request, response, authentication) ->
                        WebContextUtil.responseOutWithJson(response, R.ok("登出成功"))))
                .deleteCookies("JSESSIONID");//登出时删除cookie
    }
}
