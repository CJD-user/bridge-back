package net.lab1024.sa.admin.module.business.approval.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审批记录 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ApprovalRecordVO {

    @Schema(description = "审批记录ID")
    private Long approvalRecordId;

    @Schema(description = "审批流程ID")
    private Long approvalFlowId;

    @Schema(description = "审批节点ID")
    private Long approvalNodeId;

    @Schema(description = "业务类型: 1-采购申请 2-出库申请")
    private Integer businessType;

    @Schema(description = "业务单据ID")
    private Long businessId;

    @Schema(description = "业务单据编号")
    private String businessNo;

    @Schema(description = "节点顺序")
    private Integer nodeOrder;

    @Schema(description = "审批人ID")
    private Long approverId;

    @Schema(description = "审批人姓名")
    private String approverName;

    @Schema(description = "审批状态: 1-通过 2-打回")
    private Integer approvalStatus;

    @Schema(description = "审批状态名称")
    private String approvalStatusName;

    @Schema(description = "审批意见")
    private String approvalOpinion;

    @Schema(description = "附件")
    private String attachment;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
