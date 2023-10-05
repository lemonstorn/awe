package com.zh.awe.common.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zh 2023/8/4 22:37
 */
public class StringUtils {
    @SuppressWarnings({"unchecked"})
    public static String toString(Object value) {
        if(value==null){
            return "";
        }else if(value instanceof BigDecimal){
            return ((BigDecimal) value).stripTrailingZeros().toPlainString();
        }else if(value instanceof String||value instanceof Number){
            return value.toString();
        }else if(value.getClass().isArray()){
            return join(Arrays.stream((Object[])value).map(StringUtils::toString).collect(Collectors.toList()));
        }else if(value instanceof List){
            return join(((List<Object>)value).stream().map(StringUtils::toString).collect(Collectors.toList()));
        }else{
            return value.toString();
        }
    }

    public static String join(List<String> list){
        return String.join(",",list);
    }

    public static boolean isBlank(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isBlank(cs);
    }

    public static boolean isAllBlank(final CharSequence... cs) {
        return org.apache.commons.lang3.StringUtils.isAllBlank(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(cs);
    }

    public static boolean isNoneBlank(final CharSequence... cs) {
        return org.apache.commons.lang3.StringUtils.isNoneBlank(cs);
    }

    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
    }


}
