package com.zh.awe.security.handler;

import com.zh.awe.security.config.WebSecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

/**
 * @author zh 2023/7/8 4:04
 */
@AllArgsConstructor
public class CustomCsrfHandler implements Customizer<CsrfConfigurer<HttpSecurity>> {
    private WebSecurityProperties webSecurityProperties;

    @Override
    public void customize(CsrfConfigurer<HttpSecurity> configurer) {
        if (webSecurityProperties.getCsrf()){
            configurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
        } else {
            configurer.disable();
        }
    }
}
