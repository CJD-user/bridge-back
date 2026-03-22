package net.lab1024.sa.admin.module.business.approvalFlow.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 审批流程配置表 更新表单
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@Data
public class ApprovalFlowUpdateForm {

    @Schema(description = "审批流程ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审批流程ID 不能为空")
    private Long approvalFlowId;

}