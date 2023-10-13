package com.zh.awe.web.config;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

/**
 * 导入druid连接池
 * @author zh 2023/9/2 1:09
 */
@Configuration
@Order(Integer.MAX_VALUE-1)
public class DruidDataSourceAutoConfig {
    @Bean
    public DruidDataSourceAutoConfigure druidDataSourceAutoConfigure(){
        return new DruidDataSourceAutoConfigure();
    }
}
