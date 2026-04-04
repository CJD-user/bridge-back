package net.lab1024.sa.admin.module.business.purchase.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 采购申请单 查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class PurchaseOrderQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "采购单号")
    @Length(max = 50, message = "采购单号最多50字符")
    private String purchaseOrderNo;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    @Length(max = 200, message = "项目名称最多200字符")
    private String projectName;

    @Schema(description = "采购类型: 1-常规采购 2-紧急采购 3-补货采购")
    private Integer purchaseType;

    @Schema(description = "审批状态: 0-待提交 1-审批中 2-已通过 3-已驳回 4-已撤回")
    private Integer approvalStatus;

    @Schema(description = "删除状态: 0未删除 1已删除", hidden = true)
    private Boolean deletedFlag;

    @Schema(description = "申请人ID", hidden = true)
    private Long createUserId;
}
