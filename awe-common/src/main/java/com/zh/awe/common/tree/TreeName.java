package com.zh.awe.common.tree;

import java.lang.annotation.*;

/**
 * 标注当前属性为前端显示名称
 * @author zh 2023/8/26 13:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented
public @interface TreeName {
}
