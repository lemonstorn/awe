package com.zh.awe.common.tree;

import java.lang.annotation.*;

/**
 * 标注当前属性为父节点id
 * @author zh 2023/8/26 13:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented
public @interface TreeParentId {
}
