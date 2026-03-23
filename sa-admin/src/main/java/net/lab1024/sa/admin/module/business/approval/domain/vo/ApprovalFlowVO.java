package net.lab1024.sa.admin.module.business.approval.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 审批流程 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ApprovalFlowVO {

    @Schema(description = "审批流程ID")
    private Long approvalFlowId;

    @Schema(description = "流程名称")
    private String flowName;

    @Schema(description = "流程编码")
    private String flowCode;

    @Schema(description = "业务类型: 1-采购申请 2-出库申请")
    private Integer businessType;

    @Schema(description = "业务类型名称")
    private String businessTypeName;

    @Schema(description = "流程描述")
    private String description;

    @Schema(description = "状态: 0-停用 1-启用")
    private Integer status;

    @Schema(description = "创建人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "审批节点列表")
    private List<ApprovalNodeVO> nodeList;
}
