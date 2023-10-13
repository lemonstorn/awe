package com.zh.awe.cloud.annotation;

import com.zh.awe.cloud.config.NacosDruidConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用awe cloud 模块
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Import({NacosDruidConfig.class})
public @interface EnableAweDruidCloud {
}
