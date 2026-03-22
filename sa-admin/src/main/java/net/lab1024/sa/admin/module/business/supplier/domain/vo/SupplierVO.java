package net.lab1024.sa.admin.module.business.supplier.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 供应商 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class SupplierVO {

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "供应商编码")
    private String supplierCode;

    @Schema(description = "供应商名称")
    private String supplierName;

    @Schema(description = "简称")
    private String shortName;

    @Schema(description = "统一社会信用代码")
    private String unifiedSocialCreditCode;

    @Schema(description = "资质等级")
    private String qualificationLevel;

    @Schema(description = "经营范围")
    private String businessScope;

    @Schema(description = "联系人")
    private String contactPerson;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "省份编码")
    private Integer province;

    @Schema(description = "省份名称")
    private String provinceName;

    @Schema(description = "城市编码")
    private Integer city;

    @Schema(description = "城市名称")
    private String cityName;

    @Schema(description = "开户银行")
    private String bankName;

    @Schema(description = "银行账号")
    private String bankAccount;

    @Schema(description = "营业执照附件")
    private String businessLicense;

    @Schema(description = "资质文件附件")
    private String qualificationFile;

    @Schema(description = "供应商评级(1-5星)")
    private Integer rating;

    @Schema(description = "累计供货次数")
    private Integer totalSupplyCount;

    @Schema(description = "累计供货金额")
    private BigDecimal totalSupplyAmount;

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
