package net.lab1024.sa.admin.module.business.supplier.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 供应商评价 分页查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class SupplierEvaluateQueryForm extends PageParam {

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "评价类型: 1-季度评价 2-年度评价 3-项目评价")
    private Integer evaluateType;

    @Schema(description = "评价日期-开始")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate evaluateDateStart;

    @Schema(description = "评价日期-结束")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate evaluateDateEnd;

    @Schema(hidden = true)
    private Boolean deletedFlag;
}
