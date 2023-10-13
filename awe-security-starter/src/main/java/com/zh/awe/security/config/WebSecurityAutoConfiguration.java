package com.zh.awe.security.config;

import com.zh.awe.security.enums.OrderType;
import com.zh.awe.security.handler.*;
import com.zh.awe.web.factory.WebBeanFactory;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Map;

/**
 * @author zh 2023/7/2 16:15
 */
@Configuration
@EnableConfigurationProperties(WebSecurityProperties.class)
@EnableWebSecurity    // 添加 security 过滤器
@AllArgsConstructor
@ComponentScan(basePackages = "com.zh.awe.security")
@Order(10000)
public class WebSecurityAutoConfiguration {
    private final WebSecurityProperties webSecurityProperties;

    /**
     * 暴露filter
     */
    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * 配置受限资源
     */
    @Bean
    @DependsOn({"webBeanFactory"})
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //路径配置
        http.authorizeHttpRequests(WebBeanFactory.getBean(CustomAuthorizeHttpRequestsHandler.class));
        //登陆
        addFilters(http);
        // 自己拓展登录接口
        http.userDetailsService(WebBeanFactory.getBean(UserDetailsService.class));
        //登出
        http.logout(WebBeanFactory.getBean(LogoutHandler.class));
        //csrf验证 存储到Cookie中
        http.csrf(WebBeanFactory.getBean(CustomCsrfHandler.class));
        //rememberMe
//        http.rememberMe().rememberMeServices(rememberMeServices);

        // 权限不足时的处理
        http.exceptionHandling(WebBeanFactory.getBean(CustomExceptionHandlingHandler.class));

        return http.build();
    }

    //region 过滤器链条上的默认处理

    /**
     * 请求设置
     */
    @Bean
    @ConditionalOnMissingBean(CustomAuthorizeHttpRequestsHandler.class)
    public CustomAuthorizeHttpRequestsHandler getPermitHttpRequest(){
        return new CustomAuthorizeHttpRequestsHandler(webSecurityProperties);
    }

    /**
     * 请求设置
     */
    @Bean
    @ConditionalOnMissingBean(CustomCsrfHandler.class)
    public CustomCsrfHandler getCsrfConfigurer(){
        return new CustomCsrfHandler(webSecurityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(LogoutHandler.class)
    public LogoutHandler getLogoutConfigurer(){
        return new LogoutHandler(webSecurityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(CustomExceptionHandlingHandler.class)
    public CustomExceptionHandlingHandler getCustomExceptionHandlingConfigurer(){
        return new CustomExceptionHandlingHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler getCustomAuthenticationFailureConfigurer(){
        return new LoginFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler getCustomAuthenticationSuccessHandlerConfigurer(){
        return new LoginSuccessHandler();
    }
    //endregion

    /**
     * 根据自定义过滤器配置插入到过滤器链中
     */
    private void addFilters(HttpSecurity http){
        Map<String, CustomFilter> filters = WebBeanFactory.getBeansOfType(CustomFilter.class);
        filters.values().forEach(filter->{
            FilterProvider provider = filter.custom();
            Integer action = provider.getOrderType().getCode();
            Filter customFilter = provider.getCustomFilter();
            Class<? extends Filter> positionFilter = provider.getPositionFilter();

            if (OrderType.None.getCode().equals(action)){
                http.addFilter(customFilter);
            } else if (OrderType.Replace.getCode().equals(action)){
                http.addFilterAt(customFilter,positionFilter);
            } else if (OrderType.Before.getCode().equals(action)){
                http.addFilterBefore(customFilter,positionFilter);
            } else {
                http.addFilterAfter(customFilter,positionFilter);
            }
        });
    }
}
