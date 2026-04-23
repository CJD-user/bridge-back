package net.lab1024.sa.admin.module.business.warning.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 库存预警 处理表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryWarningHandleForm {

    @Schema(description = "预警ID")
    @NotNull(message = "预警ID不能为空")
    private Long warningId;

    @Schema(description = "处理状态: 1-已处理 2-已忽略")
    @NotNull(message = "处理状态不能为空")
    private Integer handleStatus;

    @Schema(description = "处理备注")
    @Size(max = 500, message = "处理备注最多500字符")
    private String handleRemark;
}
