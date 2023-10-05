package com.zh.awe.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zh 2023/7/30 17:04
 */
public class BeanUtils {
    public static <T> T copy(Object source, Class<T> c) {
        if (source == null) {
            return null;
        }
        try {

            T instance = c.getDeclaredConstructor().newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T copy(Object source, T instance) {
        if (source == null) {
            return null;
        }
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <E, T> List<T> copyList(List<E> sources, Class<T> c) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        for (E source : sources) {
            list.add(copy(source, c));
        }
        return list;
    }

}
