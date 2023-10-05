package com.zh.awe.web.web;

import com.zh.awe.web.controller.AweCommonController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zh 2023/8/1 22:22
 */
@Configuration
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {
    /**
     * 注册公共controller
     */
    @Bean
    public AweCommonController aweCommonController(){
        return new AweCommonController();
    }
}
