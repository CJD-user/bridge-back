package net.lab1024.sa.admin.module.business.inbound.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 入库记录 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InboundRecordVO {

    @Schema(description = "入库记录ID")
    private Long inboundRecordId;

    @Schema(description = "材料ID")
    private Long materialId;

    @Schema(description = "材料编码")
    private String materialCode;

    @Schema(description = "材料名称")
    private String materialName;

    @Schema(description = "规格型号")
    private String specificationModel;

    @Schema(description = "计量单位")
    private String unit;

    @Schema(description = "批次号")
    private String batchNo;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    private LocalDate expirationDate;

    @Schema(description = "入库数量")
    private BigDecimal quantity;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "总价")
    private BigDecimal totalPrice;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "供应商名称")
    private String supplierName;

    @Schema(description = "存放位置")
    private String storageLocation;

    @Schema(description = "入库类型: 1-采购入库 2-退货入库 3-其他")
    private Integer inboundType;

    @Schema(description = "入库类型名称")
    private String inboundTypeName;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "操作人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
