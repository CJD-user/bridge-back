package net.lab1024.sa.admin.module.business.approvalFlow.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 审批流程配置表 列表VO
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@Data
public class ApprovalFlowVO {


    @Schema(description = "审批流程ID")
    private Long approvalFlowId;

    @Schema(description = "流程名称")
    private String flowName;

    @Schema(description = "流程编码")
    private String flowCode;

    @Schema(description = "业务类型: 1-采购申请 2-出库申请 3-供应商评价")
    private Integer businessType;

    @Schema(description = "流程描述")
    private String description;

    @Schema(description = "状态: 0-停用 1-启用")
    private Integer status;

    @Schema(description = "删除状态: 0未删除 1已删除")
    private Integer deletedFlag;

    @Schema(description = "创建人ID")
    private Long createUserId;

    @Schema(description = "创建人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
