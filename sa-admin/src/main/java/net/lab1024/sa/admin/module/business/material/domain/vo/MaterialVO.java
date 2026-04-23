package net.lab1024.sa.admin.module.business.material.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 材料 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class MaterialVO {

    @Schema(description = "材料ID")
    private Long materialId;

    @Schema(description = "材料编码")
    private String materialCode;

    @Schema(description = "材料名称")
    private String materialName;

    @Schema(description = "材料分类ID")
    private Long materialCategoryId;

    @Schema(description = "材料分类名称")
    private String materialCategoryName;

    @Schema(description = "规格型号")
    private String specificationModel;

    @Schema(description = "计量单位")
    private String unit;

    @Schema(description = "参考单价")
    private BigDecimal unitPrice;

    @Schema(description = "安全库存阈值")
    private BigDecimal safetyStockThreshold;

    @Schema(description = "当前库存数量")
    private BigDecimal currentStock;

    @Schema(description = "最低预警数量")
    private BigDecimal minWarningQuantity;

    @Schema(description = "保质期(天)")
    private Integer shelfLifeDays;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "生产厂家")
    private String manufacturer;

    @Schema(description = "材料图片")
    private String materialImage;

    @Schema(description = "附件")
    private String attachment;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;

    @Schema(description = "创建人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
