package com.zh.awe.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.awe.common.utils.BeanUtils;

import java.util.List;

/**
 * 分页工具类
 * @author zh 2023/8/4 23:05
 */
public class PageUtils {
    public static <F,T> IPage<T> copyPage(IPage<F> page, Class<T> clazz) {
        List<T> records = BeanUtils.copyList(page.getRecords(), clazz);
        IPage<T> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(records);
        return result;
    }
}
