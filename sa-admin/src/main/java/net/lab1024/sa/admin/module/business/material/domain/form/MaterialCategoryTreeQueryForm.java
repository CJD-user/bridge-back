package net.lab1024.sa.admin.module.business.material.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 材料分类 树查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class MaterialCategoryTreeQueryForm {

    @Schema(description = "父级分类ID|可选")
    private Long parentId;
}
