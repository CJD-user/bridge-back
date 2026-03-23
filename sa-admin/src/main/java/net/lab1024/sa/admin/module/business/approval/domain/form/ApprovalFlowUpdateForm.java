package net.lab1024.sa.admin.module.business.approval.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审批流程 更新表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApprovalFlowUpdateForm extends ApprovalFlowAddForm {

    @Schema(description = "审批流程ID")
    @NotNull(message = "审批流程ID不能为空")
    private Long approvalFlowId;
}
