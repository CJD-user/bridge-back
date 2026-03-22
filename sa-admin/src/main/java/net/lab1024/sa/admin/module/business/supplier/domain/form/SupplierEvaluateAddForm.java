package net.lab1024.sa.admin.module.business.supplier.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 供应商评价 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class SupplierEvaluateAddForm {

    @Schema(description = "供应商ID")
    @NotNull(message = "供应商ID不能为空")
    private Long supplierId;

    @Schema(description = "评价类型: 1-季度评价 2-年度评价 3-项目评价")
    @NotNull(message = "评价类型不能为空")
    @Min(value = 1, message = "评价类型值无效")
    @Max(value = 3, message = "评价类型值无效")
    private Integer evaluateType;

    @Schema(description = "评价日期")
    @NotNull(message = "评价日期不能为空")
    private LocalDate evaluateDate;

    @Schema(description = "质量评分(1-5分)")
    @DecimalMin(value = "1.0", message = "质量评分最小为1分")
    @DecimalMax(value = "5.0", message = "质量评分最大为5分")
    private BigDecimal qualityScore;

    @Schema(description = "交付评分(1-5分)")
    @DecimalMin(value = "1.0", message = "交付评分最小为1分")
    @DecimalMax(value = "5.0", message = "交付评分最大为5分")
    private BigDecimal deliveryScore;

    @Schema(description = "服务评分(1-5分)")
    @DecimalMin(value = "1.0", message = "服务评分最小为1分")
    @DecimalMax(value = "5.0", message = "服务评分最大为5分")
    private BigDecimal serviceScore;

    @Schema(description = "价格评分(1-5分)")
    @DecimalMin(value = "1.0", message = "价格评分最小为1分")
    @DecimalMax(value = "5.0", message = "价格评分最大为5分")
    private BigDecimal priceScore;

    @Schema(description = "评价内容")
    @Size(max = 1000, message = "评价内容最多1000字符")
    private String evaluateContent;

    @Schema(description = "附件")
    @Size(max = 1000, message = "附件路径最多1000字符")
    private String attachment;
}
