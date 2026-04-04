package net.lab1024.sa.admin.module.business.purchase.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购申请单 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class PurchaseOrderVO {

    @Schema(description = "采购申请单ID")
    private Long purchaseOrderId;

    @Schema(description = "采购单号")
    private String purchaseOrderNo;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "采购类型: 1-常规采购 2-紧急采购 3-补货采购")
    private Integer purchaseType;

    @Schema(description = "采购类型名称")
    private String purchaseTypeName;

    @Schema(description = "采购原因")
    private String purchaseReason;

    @Schema(description = "采购总金额")
    private BigDecimal totalAmount;

    @Schema(description = "期望到货日期")
    private LocalDate expectedArrivalDate;

    @Schema(description = "审批状态: 0-待提交 1-审批中 2-已通过 3-已驳回 4-已撤回")
    private Integer approvalStatus;

    @Schema(description = "审批状态名称")
    private String approvalStatusName;

    @Schema(description = "审批流程ID")
    private Long approvalFlowId;

    @Schema(description = "当前审批节点序号")
    private Integer currentApprovalNode;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "申请人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "采购明细列表")
    private List<PurchaseOrderItemVO> itemList;
}
