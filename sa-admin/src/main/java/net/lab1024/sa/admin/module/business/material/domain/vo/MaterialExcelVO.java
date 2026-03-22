package net.lab1024.sa.admin.module.business.material.domain.vo;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 材料 Excel导出VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialExcelVO {

    @ExcelProperty("材料编码")
    private String materialCode;

    @ExcelProperty("材料名称")
    private String materialName;

    @ExcelProperty("材料分类")
    private String categoryName;

    @ExcelProperty("规格型号")
    private String specificationModel;

    @ExcelProperty("计量单位")
    private String unit;

    @ExcelProperty("参考单价")
    private BigDecimal unitPrice;

    @ExcelProperty("安全库存阈值")
    private BigDecimal safetyStockThreshold;

    @ExcelProperty("保质期(天)")
    private Integer shelfLifeDays;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("生产厂家")
    private String manufacturer;

    @ExcelProperty("备注")
    private String remark;
}
