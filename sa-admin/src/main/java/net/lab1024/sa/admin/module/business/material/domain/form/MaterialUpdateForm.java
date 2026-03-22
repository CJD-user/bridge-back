package net.lab1024.sa.admin.module.business.material.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 材料 更新表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialUpdateForm extends MaterialAddForm {

    @Schema(description = "材料ID")
    @NotNull(message = "材料ID不能为空")
    private Long materialId;
}
