package net.lab1024.sa.admin.module.business.purchase.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购申请单明细 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class PurchaseOrderItemVO {

    @Schema(description = "明细ID")
    private Long purchaseOrderItemId;

    @Schema(description = "采购申请单ID")
    private Long purchaseOrderId;

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

    @Schema(description = "采购数量")
    private BigDecimal purchaseQuantity;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "总价")
    private BigDecimal totalPrice;

    @Schema(description = "建议供应商ID")
    private Long supplierId;

    @Schema(description = "建议供应商名称")
    private String supplierName;

    @Schema(description = "备注")
    private String remark;
}
