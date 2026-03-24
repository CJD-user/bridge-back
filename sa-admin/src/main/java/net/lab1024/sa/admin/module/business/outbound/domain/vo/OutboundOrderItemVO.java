package net.lab1024.sa.admin.module.business.outbound.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 出库申请单明细 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class OutboundOrderItemVO {

    @Schema(description = "明细ID")
    private Long outboundOrderItemId;

    @Schema(description = "出库单ID")
    private Long outboundOrderId;

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

    @Schema(description = "申请数量")
    private BigDecimal requestQuantity;

    @Schema(description = "审批数量")
    private BigDecimal approvedQuantity;

    @Schema(description = "实际出库数量")
    private BigDecimal actualQuantity;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "总价")
    private BigDecimal totalPrice;

    @Schema(description = "出库批号")
    private String batchNo;

    @Schema(description = "存放位置")
    private String storageLocation;

    @Schema(description = "备注")
    private String remark;
}
