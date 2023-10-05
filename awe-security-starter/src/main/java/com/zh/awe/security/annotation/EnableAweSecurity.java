package com.zh.awe.security.annotation;

import com.zh.awe.security.config.WebSecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zh 2023/7/2 16:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Import(WebSecurityAutoConfiguration.class)
public @interface EnableAweSecurity {
}
