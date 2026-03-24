package net.lab1024.sa.admin.module.business.outbound.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 出库申请单 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class OutboundOrderAddForm {

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    @Size(max = 200, message = "项目名称最多200字符")
    private String projectName;

    @Schema(description = "出库类型: 1-领用出库 2-调拨出库 3-报废出库")
    @NotNull(message = "出库类型不能为空")
    private Integer outboundType;

    @Schema(description = "出库日期")
    @NotNull(message = "出库日期不能为空")
    private LocalDate outboundDate;

    @Schema(description = "领用人ID")
    private Long receiverId;

    @Schema(description = "领用人姓名")
    @Size(max = 50, message = "领用人姓名最多50字符")
    private String receiverName;

    @Schema(description = "领用人电话")
    @Size(max = 20, message = "领用人电话最多20字符")
    private String receiverPhone;

    @Schema(description = "施工班组")
    @Size(max = 100, message = "施工班组最多100字符")
    private String teamName;

    @Schema(description = "使用部位")
    @Size(max = 200, message = "使用部位最多200字符")
    private String usePosition;

    @Schema(description = "审批流程ID")
    @NotNull(message = "审批流程不能为空")
    private Long approvalFlowId;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注最多500字符")
    private String remark;

    @Schema(description = "出库明细列表")
    @Valid
    @NotNull(message = "出库明细不能为空")
    @Size(min = 1, message = "至少需要一条出库明细")
    private List<OutboundOrderItemForm> itemList;

    @Data
    public static class OutboundOrderItemForm {

        @Schema(description = "材料ID")
        @NotNull(message = "材料ID不能为空")
        private Long materialId;

        @Schema(description = "申请数量")
        @NotNull(message = "申请数量不能为空")
        private BigDecimal requestQuantity;

        @Schema(description = "批号")
        @Size(max = 100, message = "批号最多100字符")
        private String batchNo;

        @Schema(description = "存放位置")
        @Size(max = 100, message = "存放位置最多100字符")
        private String storageLocation;

        @Schema(description = "备注")
        @Size(max = 500, message = "备注最多500字符")
        private String remark;
    }
}
