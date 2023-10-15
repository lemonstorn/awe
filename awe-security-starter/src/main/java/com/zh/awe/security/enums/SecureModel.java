package com.zh.awe.security.enums;

import lombok.Getter;

/**
 * @author zh 2023/10/15 23:34
 */
@Getter
public enum SecureModel {
    JWT("jwt","JWT-token"),;

    SecureModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private final String code;
    private final String name;
}
