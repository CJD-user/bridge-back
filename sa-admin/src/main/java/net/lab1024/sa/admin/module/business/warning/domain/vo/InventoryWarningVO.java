package net.lab1024.sa.admin.module.business.warning.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存预警 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryWarningVO {

    @Schema(description = "预警ID")
    private Long warningId;

    @Schema(description = "库存ID")
    private Long inventoryId;

    @Schema(description = "材料ID")
    private Long materialId;

    @Schema(description = "材料编码")
    private String materialCode;

    @Schema(description = "材料名称")
    private String materialName;

    @Schema(description = "预警类型: 1-库存不足 2-即将过期 3-已过期")
    private Integer warningType;

    @Schema(description = "预警类型名称")
    private String warningTypeName;

    @Schema(description = "预警级别: 1-一般 2-重要 3-紧急")
    private Integer warningLevel;

    @Schema(description = "预警级别名称")
    private String warningLevelName;

    @Schema(description = "当前数量")
    private BigDecimal currentQuantity;

    @Schema(description = "阈值数量")
    private BigDecimal thresholdQuantity;

    @Schema(description = "有效期至")
    private LocalDate expirationDate;

    @Schema(description = "预警内容")
    private String warningContent;

    @Schema(description = "处理状态: 0-未处理 1-已处理 2-已忽略")
    private Integer handleStatus;

    @Schema(description = "处理状态名称")
    private String handleStatusName;

    @Schema(description = "处理人姓名")
    private String handleUserName;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理备注")
    private String handleRemark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
