package com.zh.awe.cloud.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author zh 2023/10/7 23:13
 */
@Configuration
@EnableConfigurationProperties(DruidConfig.class)
@Order(100)
public class NacosDruidConfig {
}
