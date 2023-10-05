package com.zh.awe.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zh.awe.common.tree.TreeId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author zh 2023/6/22 21:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AweBaseEntity<E extends Model<E>> extends Model<E> {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @TreeId
    private Long id;
    @TableField(value = "created_by",fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
