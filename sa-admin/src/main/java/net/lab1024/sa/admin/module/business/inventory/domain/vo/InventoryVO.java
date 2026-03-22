package net.lab1024.sa.admin.module.business.inventory.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryVO {

    @Schema(description = "库存ID")
    private Long inventoryId;

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

    @Schema(description = "库存数量")
    private BigDecimal quantity;

    @Schema(description = "锁定数量")
    private BigDecimal lockedQuantity;

    @Schema(description = "可用数量")
    private BigDecimal availableQuantity;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "存放位置")
    private String storageLocation;

    @Schema(description = "预警状态: 0-正常 1-库存不足 2-即将过期 3-已过期")
    private Integer warningStatus;

    @Schema(description = "预警状态名称")
    private String warningStatusName;

    @Schema(description = "最后入库时间")
    private LocalDateTime lastInboundTime;

    @Schema(description = "最后出库时间")
    private LocalDateTime lastOutboundTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
