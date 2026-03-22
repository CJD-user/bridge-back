package net.lab1024.sa.admin.module.business.supplier.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 供应商评价 更新表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SupplierEvaluateUpdateForm extends SupplierEvaluateAddForm {

    @Schema(description = "评价ID")
    @NotNull(message = "评价ID不能为空")
    private Long evaluateId;
}
