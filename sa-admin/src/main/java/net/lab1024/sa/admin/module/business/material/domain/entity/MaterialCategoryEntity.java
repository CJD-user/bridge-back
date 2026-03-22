package net.lab1024.sa.admin.module.business.material.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 材料分类 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_material_category")
public class MaterialCategoryEntity {

    @TableId(type = IdType.AUTO)
    private Long materialCategoryId;

    private String categoryName;

    private String categoryCode;

    private Long parentId;

    private Integer sort;

    private String remark;

    private Boolean disabledFlag;

    private Boolean deletedFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
