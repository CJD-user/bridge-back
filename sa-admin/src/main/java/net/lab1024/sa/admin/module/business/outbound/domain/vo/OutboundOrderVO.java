package net.lab1024.sa.admin.module.business.outbound.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 出库申请单 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class OutboundOrderVO {

    @Schema(description = "出库单ID")
    private Long outboundOrderId;

    @Schema(description = "出库单号")
    private String outboundOrderNo;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "出库类型: 1-领用出库 2-调拨出库 3-报废出库")
    private Integer outboundType;

    @Schema(description = "出库类型名称")
    private String outboundTypeName;

    @Schema(description = "出库日期")
    private LocalDate outboundDate;

    @Schema(description = "领用人ID")
    private Long receiverId;

    @Schema(description = "领用人姓名")
    private String receiverName;

    @Schema(description = "领用人电话")
    private String receiverPhone;

    @Schema(description = "施工班组")
    private String teamName;

    @Schema(description = "使用部位")
    private String usePosition;

    @Schema(description = "出库总数量")
    private BigDecimal totalQuantity;

    @Schema(description = "出库总金额")
    private BigDecimal totalAmount;

    @Schema(description = "审批状态: 0-待提交 1-审批中 2-已通过 3-已驳回 4-已撤回")
    private Integer approvalStatus;

    @Schema(description = "审批状态名称")
    private String approvalStatusName;

    @Schema(description = "审批流程ID")
    private Long approvalFlowId;

    @Schema(description = "当前审批节点序号")
    private Integer currentApprovalNode;

    @Schema(description = "状态: 0-待审批 1-已审批待出库 2-已出库 3-已作废")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "申请人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "出库明细列表")
    private List<OutboundOrderItemVO> itemList;
}
