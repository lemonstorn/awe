package com.zh.awe.security.enums;

import lombok.Getter;

/**
 * @author zh 2023/7/8 13:48
 */
@Getter
public enum OrderType {
    None(0, "仅添加"),
    Replace(1,"替换存在的过滤器"),
    Before(2, "添加到某个过滤器之前"),
    After(3, "添加到某个过滤器之后");

    OrderType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private final Integer code;
    private final String name;

}