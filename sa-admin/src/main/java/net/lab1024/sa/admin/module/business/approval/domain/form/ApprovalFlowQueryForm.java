package net.lab1024.sa.admin.module.business.approval.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 审批流程 查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ApprovalFlowQueryForm extends PageParam {

    @Schema(description = "流程名称")
    @Length(max = 100, message = "流程名称最多100字符")
    private String flowName;

    @Schema(description = "流程编码")
    @Length(max = 50, message = "流程编码最多50字符")
    private String flowCode;

    @Schema(description = "业务类型: 1-采购申请 2-出库申请")
    private Integer businessType;

    @Schema(description = "状态: 0-停用 1-启用")
    private Integer status;
}
