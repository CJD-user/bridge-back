package net.lab1024.sa.admin.module.business.inbound.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 入库记录 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InboundRecordAddForm {

    @Schema(description = "材料ID")
    @NotNull(message = "材料ID不能为空")
    private Long materialId;

    @Schema(description = "批次号")
    @Size(max = 100, message = "批次号最多100字符")
    private String batchNo;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    private LocalDate expirationDate;

    @Schema(description = "入库数量")
    @NotNull(message = "入库数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "供应商名称")
    @Size(max = 200, message = "供应商名称最多200字符")
    private String supplierName;

    @Schema(description = "存放位置")
    @Size(max = 100, message = "存放位置最多100字符")
    private String storageLocation;

    @Schema(description = "入库类型: 1-采购入库 2-退货入库 3-其他")
    private Integer inboundType;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    @Size(max = 200, message = "项目名称最多200字符")
    private String projectName;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注最多500字符")
    private String remark;
}
