package com.zh.awe.security.handler;

import com.zh.awe.security.enums.OrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义过滤器配置
 * @author zh 2023/7/8 14:20
 */
@Data
@Schema(name = "过滤器主类")
@AllArgsConstructor
public class FilterProvider {
    /**
     * 在相对过滤器的哪个位置
     */
    private OrderType orderType;
    /**
     * 相对于哪个过滤器
     */
    private Class<? extends Filter> positionFilter;
    /**
     * 兹定于i过滤器
     */
    private Filter customFilter;

}
