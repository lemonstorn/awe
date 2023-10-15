package com.zh.awe.web.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zh 2023/7/9 22:23
 */
@Data
@ConfigurationProperties(prefix = "system.web")
public class WebProperties {
    /**
     * 是否开启自定义异常处理
     */
    private List<String> enumPath = new ArrayList<>();
}
