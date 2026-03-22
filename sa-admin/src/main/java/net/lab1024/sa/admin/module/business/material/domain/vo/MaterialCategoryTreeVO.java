package net.lab1024.sa.admin.module.business.material.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 材料分类 树形结构 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class MaterialCategoryTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类ID")
    private Long materialCategoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "分类编码")
    private String categoryCode;

    @Schema(description = "分类层级全称")
    private String categoryFullName;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "分类ID(用于选择器)")
    private Long value;

    @Schema(description = "分类名称(用于选择器)")
    private String label;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;

    @Schema(description = "子分类")
    private List<MaterialCategoryTreeVO> children;
}
