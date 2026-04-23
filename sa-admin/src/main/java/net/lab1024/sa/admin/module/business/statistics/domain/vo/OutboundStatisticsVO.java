package net.lab1024.sa.admin.module.business.statistics.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 出库统计 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class OutboundStatisticsVO {

    @Schema(description = "日期")
    private String date;

    @Schema(description = "出库金额")
    private BigDecimal totalAmount;

    @Schema(description = "出库单数量")
    private Integer orderCount;

    @Schema(description = "出库材料种类数")
    private Integer materialCount;
}
