package net.lab1024.sa.admin.module.business.supplier.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 供应商 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_supplier")
public class SupplierEntity {

    @TableId(type = IdType.AUTO)
    private Long supplierId;

    private String supplierCode;

    private String supplierName;

    private String shortName;

    private String unifiedSocialCreditCode;

    private String qualificationLevel;

    private String businessScope;

    private String contactPerson;

    private String contactPhone;

    private String email;

    private String address;

    private Integer province;

    private String provinceName;

    private Integer city;

    private String cityName;

    private String bankName;

    private String bankAccount;

    private String businessLicense;

    private String qualificationFile;

    private Integer rating;

    private Integer totalSupplyCount;

    private BigDecimal totalSupplyAmount;

    private String remark;

    private Boolean disabledFlag;

    private Boolean deletedFlag;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
