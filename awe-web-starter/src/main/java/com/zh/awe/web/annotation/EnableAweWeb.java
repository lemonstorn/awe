package com.zh.awe.web.annotation;

import com.zh.awe.web.config.DruidDataSourceAutoConfig;
import com.zh.awe.web.config.JacksonAutoConfig;
import com.zh.awe.web.exception.ExceptionAutoConfiguration;
import com.zh.awe.web.factory.WebBeanFactory;
import com.zh.awe.web.web.WebAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 启用awe web模块
 * 1. 启用异常处理
 * 2. 启用web配置
 * 3. 启用web bean工厂 将beanDefinition定义的工厂注册到spring容器中
 * @author zh 2023/7/2 15:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Import({ExceptionAutoConfiguration.class, WebAutoConfiguration.class, WebBeanFactory.class,
        DruidDataSourceAutoConfig.class, JacksonAutoConfig.class})
@Order(1000)
public @interface EnableAweWeb {
}
