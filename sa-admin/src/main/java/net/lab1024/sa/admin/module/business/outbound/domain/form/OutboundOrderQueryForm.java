package net.lab1024.sa.admin.module.business.outbound.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 出库申请单 查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class OutboundOrderQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "出库单号")
    @Length(max = 50, message = "出库单号最多50字符")
    private String outboundOrderNo;

    @Schema(description = "项目名称")
    @Length(max = 200, message = "项目名称最多200字符")
    private String projectName;

    @Schema(description = "出库类型: 1-领用出库 2-调拨出库 3-报废出库")
    private Integer outboundType;

    @Schema(description = "审批状态: 0-待提交 1-审批中 2-已通过 3-已驳回 4-已撤回")
    private Integer approvalStatus;

    @Schema(description = "状态: 0-待审批 1-已审批待出库 2-已出库 3-已作废")
    private Integer status;

    @Schema(description = "删除状态: 0未删除 1已删除", hidden = true)
    private Boolean deletedFlag;

    @Schema(description = "申请人ID", hidden = true)
    private Long createUserId;
}
