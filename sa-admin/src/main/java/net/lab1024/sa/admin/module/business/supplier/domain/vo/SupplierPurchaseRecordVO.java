package net.lab1024.sa.admin.module.business.supplier.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 供应商采购记录 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class SupplierPurchaseRecordVO {

    @Schema(description = "采购申请单ID")
    private Long purchaseOrderId;

    @Schema(description = "采购单号")
    private String purchaseOrderNo;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "采购类型")
    private Integer purchaseType;

    @Schema(description = "采购类型名称")
    private String purchaseTypeName;

    @Schema(description = "材料ID")
    private Long materialId;

    @Schema(description = "材料编码")
    private String materialCode;

    @Schema(description = "材料名称")
    private String materialName;

    @Schema(description = "采购数量")
    private BigDecimal purchaseQuantity;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "总价")
    private BigDecimal totalPrice;

    @Schema(description = "审批状态")
    private Integer approvalStatus;

    @Schema(description = "审批状态名称")
    private String approvalStatusName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
