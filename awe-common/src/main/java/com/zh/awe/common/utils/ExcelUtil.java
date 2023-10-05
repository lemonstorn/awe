package com.zh.awe.common.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.util.List;

/**
 * @author zh 2023/9/9 13:25
 */
public class ExcelUtil {
    /**
     * 将数据转换成excel
     * @param data  需要的数据
     * @return  excel
     */
    public static <T> File writeExcel(List<T> data, Class<T> clazz) {
        File tempFile = FileUtil.createTempFile("temp", ".xlsx", true);
        EasyExcel.write(tempFile, clazz)
                .sheet("模板")
                .doWrite(data);
        return tempFile;
    }


}
