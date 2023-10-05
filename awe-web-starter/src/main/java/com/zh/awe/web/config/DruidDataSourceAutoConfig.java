package com.zh.awe.web.config;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 导入druid连接池
 * @author zh 2023/9/2 1:09
 */
@Configuration
@Import(DruidDataSourceAutoConfigure.class)
public class DruidDataSourceAutoConfig {

}
