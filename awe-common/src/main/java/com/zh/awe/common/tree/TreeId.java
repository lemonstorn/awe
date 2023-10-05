package com.zh.awe.common.tree;

import java.lang.annotation.*;

/**
 * 标注当前属性为节点id
 * @author zh 2023/8/26 13:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented
public @interface TreeId {
    /**
     * 非id字段为父节点唯一标识时使用，该值越大，越优先
     * @return
     */
    int order() default 0;
}
