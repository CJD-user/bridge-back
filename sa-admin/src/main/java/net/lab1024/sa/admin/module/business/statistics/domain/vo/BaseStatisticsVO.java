package net.lab1024.sa.admin.module.business.statistics.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 基础数据统计 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class BaseStatisticsVO {

    @Schema(description = "材料种类总数")
    private Long materialCount;

    @Schema(description = "项目总数")
    private Long projectCount;

    @Schema(description = "供应商总数")
    private Long supplierCount;

    @Schema(description = "库存预警数量")
    private Long inventoryWarningCount;

    @Schema(description = "待审批采购单数量")
    private Long pendingPurchaseCount;

    @Schema(description = "待审批出库单数量")
    private Long pendingOutboundCount;
}
