package net.lab1024.sa.admin.module.business.approval.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 审批流程 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ApprovalFlowAddForm {

    @Schema(description = "流程名称")
    @NotBlank(message = "流程名称不能为空")
    @Size(max = 100, message = "流程名称最多100字符")
    private String flowName;

    @Schema(description = "流程编码")
    @NotBlank(message = "流程编码不能为空")
    @Size(max = 50, message = "流程编码最多50字符")
    private String flowCode;

    @Schema(description = "业务类型: 1-采购申请 2-出库申请")
    @NotNull(message = "业务类型不能为空")
    private Integer businessType;

    @Schema(description = "流程描述")
    @Size(max = 500, message = "流程描述最多500字符")
    private String description;

    @Schema(description = "状态: 0-停用 1-启用")
    private Integer status;

    @Schema(description = "审批节点列表")
    private List<ApprovalNodeForm> nodeList;

    @Data
    public static class ApprovalNodeForm {

        @Schema(description = "节点名称")
        @NotBlank(message = "节点名称不能为空")
        @Size(max = 100, message = "节点名称最多100字符")
        private String nodeName;

        @Schema(description = "节点编码")
        @Size(max = 50, message = "节点编码最多50字符")
        private String nodeCode;

        @Schema(description = "节点顺序")
        @NotNull(message = "节点顺序不能为空")
        private Integer nodeOrder;

        @Schema(description = "审批人类型: 1-指定人员 2-部门负责人 3-角色 4-发起人自选")
        @NotNull(message = "审批人类型不能为空")
        private Integer approverType;

        @Schema(description = "审批人ID列表(逗号分隔)")
        @Size(max = 500, message = "审批人ID列表最多500字符")
        private String approverIds;

        @Schema(description = "审批人姓名列表(逗号分隔)")
        @Size(max = 500, message = "审批人姓名列表最多500字符")
        private String approverNames;

        @Schema(description = "角色ID(当approver_type=3时)")
        private Long roleId;

        @Schema(description = "备注")
        @Size(max = 500, message = "备注最多500字符")
        private String remark;
    }
}
