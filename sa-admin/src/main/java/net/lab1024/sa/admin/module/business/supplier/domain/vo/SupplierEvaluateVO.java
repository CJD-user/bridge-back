package net.lab1024.sa.admin.module.business.supplier.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 供应商评价 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class SupplierEvaluateVO {

    @Schema(description = "评价ID")
    private Long evaluateId;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "供应商编码")
    private String supplierCode;

    @Schema(description = "供应商名称")
    private String supplierName;

    @Schema(description = "评价类型: 1-季度评价 2-年度评价 3-项目评价")
    private Integer evaluateType;

    @Schema(description = "评价类型名称")
    private String evaluateTypeName;

    @Schema(description = "评价日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate evaluateDate;

    @Schema(description = "质量评分(1-5分)")
    private BigDecimal qualityScore;

    @Schema(description = "交付评分(1-5分)")
    private BigDecimal deliveryScore;

    @Schema(description = "服务评分(1-5分)")
    private BigDecimal serviceScore;

    @Schema(description = "价格评分(1-5分)")
    private BigDecimal priceScore;

    @Schema(description = "综合评分")
    private BigDecimal totalScore;

    @Schema(description = "评价内容")
    private String evaluateContent;

    @Schema(description = "附件")
    private String attachment;

    @Schema(description = "评价人ID")
    private Long createUserId;

    @Schema(description = "评价人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
