package net.lab1024.sa.admin.module.business.approvalFlow.domain.form;

import net.lab1024.sa.base.common.domain.PageParam;
import net.lab1024.sa.admin.module.business.approvalFlow.constant.ApprovalFlowEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.swagger.SchemaEnum;
import net.lab1024.sa.base.common.validator.enumeration.CheckEnum;

/**
 * 审批流程配置表 分页查询表单
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ApprovalFlowQueryForm extends PageParam {

    @SchemaEnum(value = ApprovalFlowEnum.class, desc = "业务类型: 1-采购申请 2-出库申请 3-供应商评价")
    @CheckEnum(value = ApprovalFlowEnum.class, message = "业务类型: 1-采购申请 2-出库申请 3-供应商评价 错误")
    private Integer businessType;

    @SchemaEnum(value = ApprovalFlowEnum.class, desc = "状态: 0-停用 1-启用")
    @CheckEnum(value = ApprovalFlowEnum.class, message = "状态: 0-停用 1-启用 错误")
    private Integer status;

    @SchemaEnum(value = ApprovalFlowEnum.class, desc = "删除状态: 0未删除 1已删除")
    @CheckEnum(value = ApprovalFlowEnum.class, message = "删除状态: 0未删除 1已删除 错误")
    private Integer deletedFlag;

    @Schema(description = "流程名称")
    private String flowName;

}
