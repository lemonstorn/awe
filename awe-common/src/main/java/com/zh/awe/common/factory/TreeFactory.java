package com.zh.awe.common.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import com.zh.awe.common.tree.TreeId;
import com.zh.awe.common.tree.TreeName;
import com.zh.awe.common.tree.TreeNode;
import com.zh.awe.common.tree.TreeParentId;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zh 2023/8/6 18:12
 */
public class TreeFactory {
    private static final int annotationNum = 3;
    private static final String ID = "id";
    private static final String PARENT_ID = "parentId";
    private static final String NAME = "name";

    /**
     * 根据注解构建简单树
     *
     * @param dataList 数据列表
     * @param clazz    数据类型
     * @param hasRoot  是否包含根节点
     * @return 树
     */
    @SneakyThrows
    public static <T> List<TreeNode> buildTree(List<T> dataList, Class<T> clazz, boolean hasRoot) {
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        if (CollectionUtil.isEmpty(dataList)) {
            return treeNodes;
        }
        // 获取id和parentId字段
        Field[] fields = ReflectUtil.getFields(clazz);
        HashMap<String, Field> fieldMap = new  HashMap<>(annotationNum);
        for (Field field : fields) {
            TreeId treeId = field.getAnnotation(TreeId.class);
            if (treeId != null) {
                if (fieldMap.containsKey(ID)){
                    fieldMap.put(ID, fieldMap.computeIfPresent(ID, (k, v) -> v.getAnnotation(TreeId.class).order() > treeId.order() ? v : field));
                } else {
                    fieldMap.put(ID, field);
                }
            }
            TreeParentId treeParentId = field.getAnnotation(TreeParentId.class);
            if (treeParentId != null) {
                fieldMap.put(PARENT_ID, field);
            }
            TreeName treeName = field.getAnnotation(TreeName.class);
            if (treeName != null) {
                fieldMap.put(NAME, field);
            }
            if (fieldMap.size() == annotationNum) {
                break;
            }
        }
        if (fieldMap.size() != annotationNum) {
            throw new RuntimeException("树节点必须包含id、name和parentId字段,请在" + clazz.getName() + "对象字段上添加@TreeId和@TreeParentId注解");
        }
        // 构建节点
        TreeNode root = new TreeNode();
        root.setId("0");
        root.setParentId("-1");
        root.setChildren(CollectionUtil.newArrayList());
        treeNodes.add(root);
        for (T data : dataList) {
            TreeNode node = new TreeNode();
            node.setId(ReflectUtil.getFieldValue(data, fieldMap.get(ID)).toString());
            node.setParentId(ReflectUtil.getFieldValue(data, fieldMap.get(PARENT_ID)).toString());
            node.setName((String) ReflectUtil.getFieldValue(data, fieldMap.get(NAME)));
            node.setMeta(data);
            treeNodes.add(node);
        }
        // 构建树
        List<TreeNode> result = new ArrayList<>();

        buildTree(treeNodes, root);
        result.add(root);
        if (!hasRoot) {
            result = root.getChildren();
        }
        return result;
    }

    /**
     * 递归造树
     *
     * @param dataList 数据列表
     * @return 树
     */
    private static void buildTree(List<TreeNode> dataList, TreeNode node) {
        List<TreeNode> children = new ArrayList<>();
        for (TreeNode treeNode : dataList) {
            if (treeNode.getParentId().equals(node.getId())) {
                children.add(treeNode);
            }
        }
        if (CollectionUtil.isNotEmpty(children)) {
            node.setChildren(children);
            for (TreeNode child : children) {
                buildTree(dataList, child);
            }
        }
    }
}
