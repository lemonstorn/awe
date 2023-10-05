package com.zh.awe.web.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zh 2023/7/9 22:23
 */
@Data
@ConfigurationProperties(prefix = "system.exception")
public class ExceptionProperties {
    /**
     * 是否开启自定义异常处理
     */
    private Boolean flag = false;
}
