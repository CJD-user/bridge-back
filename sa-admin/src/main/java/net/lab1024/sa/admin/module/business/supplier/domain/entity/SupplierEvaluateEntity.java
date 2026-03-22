package net.lab1024.sa.admin.module.business.supplier.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 供应商评价 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_supplier_evaluate")
public class SupplierEvaluateEntity {

    @TableId(type = IdType.AUTO)
    private Long evaluateId;

    private Long supplierId;

    private Integer evaluateType;

    private LocalDate evaluateDate;

    private BigDecimal qualityScore;

    private BigDecimal deliveryScore;

    private BigDecimal serviceScore;

    private BigDecimal priceScore;

    private BigDecimal totalScore;

    private String evaluateContent;

    private String attachment;

    private Boolean deletedFlag;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
