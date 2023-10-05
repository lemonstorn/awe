package com.zh.awe.annotation;

import com.zh.awe.config.MybatisPlusConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zh 2023/7/8 22:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Import(MybatisPlusConfiguration.class)
public @interface EnableAweJdbc {
}
