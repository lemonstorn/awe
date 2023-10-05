package com.zh.awe.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.zh.awe.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Optional;

/**
 * @author zh 2023/9/17 1:20
 */
@Getter
public enum ExportType implements BaseEnum<ExportType> {
    JSON(0, ".json"),
    XLS(1, ".xls"),
    XLSX(2, ".xlsx");

    ExportType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @EnumValue    //标记数据库存的值
    private final Integer code;
    @JsonValue   //标记响应json值
    private final String name;

    public static Optional<ExportType> of(Integer code) {
        return Optional.ofNullable(BaseEnum.parseByCode(ExportType.class, code));
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
