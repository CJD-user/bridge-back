package net.lab1024.sa.admin.module.business.statistics.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 库存统计 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryStatisticsVO {

    @Schema(description = "材料ID")
    private Long materialId;

    @Schema(description = "材料编码")
    private String materialCode;

    @Schema(description = "材料名称")
    private String materialName;

    @Schema(description = "规格型号")
    private String specificationModel;

    @Schema(description = "计量单位")
    private String unit;

    @Schema(description = "库存数量")
    private BigDecimal quantity;

    @Schema(description = "可用数量")
    private BigDecimal availableQuantity;

    @Schema(description = "库存金额")
    private BigDecimal totalAmount;
}
