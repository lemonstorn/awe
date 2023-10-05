package com.zh.awe.common.tree;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础树模型
 * @author zh 2023/8/6 18:01
 */
@Schema(name = "TreeNode", description = "树节点")
@Data
public class TreeNode {
    private String id;
    private String parentId;
    private String name;
    @Schema(name = "meta", description = "元数据")
    private Object meta;
    @Schema(name = "children", description = "子节点")
    private List<TreeNode> children = new ArrayList<>();

}
