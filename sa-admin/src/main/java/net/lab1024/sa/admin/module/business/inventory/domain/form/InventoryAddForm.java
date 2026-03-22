package net.lab1024.sa.admin.module.business.inventory.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 库存 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryAddForm {

    @Schema(description = "材料ID")
    @NotNull(message = "材料ID不能为空")
    private Long materialId;

    @Schema(description = "批次号")
    @Size(max = 100, message = "批次号最多100字符")
    private String batchNo;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    private LocalDate expirationDate;

    @Schema(description = "库存数量")
    @NotNull(message = "库存数量不能为空")
    @DecimalMin(value = "0.00", message = "库存数量不能为负数")
    private BigDecimal quantity;

    @Schema(description = "单价")
    @DecimalMin(value = "0.00", message = "单价不能为负数")
    private BigDecimal unitPrice;

    @Schema(description = "存放位置")
    @Size(max = 100, message = "存放位置最多100字符")
    private String storageLocation;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注最多500字符")
    private String remark;
}
