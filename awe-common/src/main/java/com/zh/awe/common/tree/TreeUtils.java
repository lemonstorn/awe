package com.zh.awe.common.tree;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zh.awe.common.utils.BeanUtils;

import java.util.List;

/**
 * @author zh 2023/10/14 22:04
 */
public class TreeUtils {
    /**
     * 将树形结构的内置对象转换为指定对象
     */
    public static void modelConverter(List<TreeNode> nodeList, Class<?> source, Class<?> target) {
        if (CollectionUtil.isEmpty(nodeList)){
            return;
        }
        for (TreeNode treeNode : nodeList) {
            if (ObjectUtil.isNotEmpty(treeNode.getMeta()) && treeNode.getMeta().getClass().equals(source)){
                treeNode.setMeta(BeanUtils.copy(treeNode.getMeta(),target));
            }
            if (CollectionUtil.isNotEmpty(treeNode.getChildren())){
                modelConverter(treeNode.getChildren(),source,target);
            }
        }
    }
}
