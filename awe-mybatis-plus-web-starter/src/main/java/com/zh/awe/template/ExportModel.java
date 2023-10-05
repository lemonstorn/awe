package com.zh.awe.template;

import com.zh.awe.enums.ExportType;

/**
 * @author zh 2023/9/17 0:39
 */
public record ExportModel<T>(boolean temp, ExportType exportType, T meta) {
}
