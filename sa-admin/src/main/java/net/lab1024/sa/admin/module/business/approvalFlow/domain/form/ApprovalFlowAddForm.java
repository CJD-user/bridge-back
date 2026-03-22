package net.lab1024.sa.admin.module.business.approvalFlow.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 审批流程配置表 新建表单
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@Data
public class ApprovalFlowAddForm {

    @Schema(description = "审批流程ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审批流程ID 不能为空")
    private Long approvalFlowId;

    @Schema(description = "流程名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "流程名称 不能为空")
    private String flowName;

    @Schema(description = "流程编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "流程编码 不能为空")
    private String flowCode;

    @Schema(description = "业务类型: 1-采购申请 2-出库申请 3-供应商评价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "业务类型: 1-采购申请 2-出库申请 3-供应商评价 不能为空")
    private Integer businessType;

}