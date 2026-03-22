package net.lab1024.sa.admin.module.business.material.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 材料 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class MaterialAddForm {

    @Schema(description = "材料编码")
    @NotBlank(message = "材料编码不能为空")
    @Size(max = 50, message = "材料编码最多50字符")
    private String materialCode;

    @Schema(description = "材料名称")
    @NotBlank(message = "材料名称不能为空")
    @Size(max = 200, message = "材料名称最多200字符")
    private String materialName;

    @Schema(description = "材料分类ID")
    private Long materialCategoryId;

    @Schema(description = "规格型号")
    @Size(max = 200, message = "规格型号最多200字符")
    private String specificationModel;

    @Schema(description = "计量单位")
    @Size(max = 20, message = "计量单位最多20字符")
    private String unit;

    @Schema(description = "参考单价")
    @DecimalMin(value = "0", message = "参考单价不能小于0")
    private BigDecimal unitPrice;

    @Schema(description = "安全库存阈值")
    @DecimalMin(value = "0", message = "安全库存阈值不能小于0")
    private BigDecimal safetyStockThreshold;

    @Schema(description = "保质期(天)")
    private Integer shelfLifeDays;

    @Schema(description = "品牌")
    @Size(max = 100, message = "品牌最多100字符")
    private String brand;

    @Schema(description = "生产厂家")
    @Size(max = 200, message = "生产厂家最多200字符")
    private String manufacturer;

    @Schema(description = "材料图片")
    @Size(max = 500, message = "材料图片路径最多500字符")
    private String materialImage;

    @Schema(description = "附件(多个fileKey逗号分隔)")
    @Size(max = 1000, message = "附件最多1000字符")
    private String attachment;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注最多500字符")
    private String remark;
}
