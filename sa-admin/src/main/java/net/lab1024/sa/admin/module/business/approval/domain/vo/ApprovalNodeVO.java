package net.lab1024.sa.admin.module.business.approval.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审批节点 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ApprovalNodeVO {

    @Schema(description = "审批节点ID")
    private Long approvalNodeId;

    @Schema(description = "审批流程ID")
    private Long approvalFlowId;

    @Schema(description = "节点名称")
    private String nodeName;

    @Schema(description = "节点编码")
    private String nodeCode;

    @Schema(description = "节点顺序")
    private Integer nodeOrder;

    @Schema(description = "审批人类型: 1-指定人员 2-部门负责人 3-角色 4-发起人自选")
    private Integer approverType;

    @Schema(description = "审批人类型名称")
    private String approverTypeName;

    @Schema(description = "审批人ID列表(逗号分隔)")
    private String approverIds;

    @Schema(description = "审批人姓名列表(逗号分隔)")
    private String approverNames;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
