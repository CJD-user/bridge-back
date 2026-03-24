package net.lab1024.sa.admin.module.business.approval.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 审批处理表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ApprovalHandleForm {

    @Schema(description = "业务类型: 1-采购申请 2-出库申请")
    @NotNull(message = "业务类型不能为空")
    private Integer businessType;

    @Schema(description = "业务单据ID")
    @NotNull(message = "业务单据ID不能为空")
    private Long businessId;

    @Schema(description = "审批状态: 1-通过 2-打回")
    @NotNull(message = "审批状态不能为空")
    private Integer approvalStatus;

    @Schema(description = "审批意见")
    @Size(max = 500, message = "审批意见最多500字符")
    private String approvalOpinion;

    @Schema(description = "附件")
    @Size(max = 1000, message = "附件路径最多1000字符")
    private String attachment;
}
