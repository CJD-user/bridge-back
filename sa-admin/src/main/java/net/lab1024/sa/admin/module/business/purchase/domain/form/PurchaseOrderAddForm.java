package net.lab1024.sa.admin.module.business.purchase.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购申请单 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class PurchaseOrderAddForm {

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    @Size(max = 200, message = "项目名称最多200字符")
    private String projectName;

    @Schema(description = "审批流程ID")
    @NotNull(message = "审批流程ID不能为空")
    private Long approvalFlowId;

    @Schema(description = "采购类型: 1-常规采购 2-紧急采购 3-补货采购")
    private Integer purchaseType;

    @Schema(description = "采购原因")
    @Size(max = 500, message = "采购原因最多500字符")
    private String purchaseReason;

    @Schema(description = "期望到货日期")
    private LocalDate expectedArrivalDate;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注最多500字符")
    private String remark;

    @Schema(description = "采购明细列表")
    @NotEmpty(message = "采购明细不能为空")
    @Valid
    private List<ItemForm> itemList;

    @Data
    public static class ItemForm {

        @Schema(description = "材料ID")
        @NotNull(message = "材料ID不能为空")
        private Long materialId;

        @Schema(description = "采购数量")
        @NotNull(message = "采购数量不能为空")
        private BigDecimal purchaseQuantity;

        @Schema(description = "单价")
        private BigDecimal unitPrice;

        @Schema(description = "建议供应商ID")
        private Long supplierId;

        @Schema(description = "建议供应商名称")
        @Size(max = 200, message = "供应商名称最多200字符")
        private String supplierName;

        @Schema(description = "备注")
        @Size(max = 500, message = "备注最多500字符")
        private String remark;
    }
}
