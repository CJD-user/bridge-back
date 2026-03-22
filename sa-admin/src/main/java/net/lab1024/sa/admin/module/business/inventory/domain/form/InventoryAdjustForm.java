package net.lab1024.sa.admin.module.business.inventory.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 库存调整表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryAdjustForm {

    @Schema(description = "库存ID")
    @NotNull(message = "库存ID不能为空")
    private Long inventoryId;

    @Schema(description = "调整类型: 1-入库 2-出库 3-盘点调整")
    @NotNull(message = "调整类型不能为空")
    private Integer adjustType;

    @Schema(description = "调整数量(正数增加,负数减少)")
    @NotNull(message = "调整数量不能为空")
    private BigDecimal adjustQuantity;

    @Schema(description = "调整原因")
    private String adjustReason;
}
