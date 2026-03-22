package net.lab1024.sa.admin.module.business.material.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 材料分类 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class MaterialCategoryAddForm {

    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    @Length(max = 100, message = "分类名称最多100字符")
    private String categoryName;

    @Schema(description = "分类编码")
    @Size(max = 50, message = "分类编码最多50字符")
    private String categoryCode;

    @Schema(description = "父级分类ID")
    private Long parentId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注最多500字符")
    private String remark;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;
}
