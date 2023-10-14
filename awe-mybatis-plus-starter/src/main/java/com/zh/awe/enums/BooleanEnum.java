package com.zh.awe.enums;

/**
 * @author zh 2023/8/6 13:49
 */

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.zh.awe.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Optional;

@Getter
public enum BooleanEnum implements BaseEnum<BooleanEnum> {
    TRUE(1, "是"),
    FALSE(0, "否");

    BooleanEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @EnumValue    //标记数据库存的值
    private final Integer code;
    @JsonValue   //标记响应json值
    private final String name;

    public static BooleanEnum of(Integer code) {
        return Optional.ofNullable(BaseEnum.parseByCode(BooleanEnum.class, code)).orElse(BooleanEnum.FALSE);
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean parse(){
        return this.code == 1;
    }
}