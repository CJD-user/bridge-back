package net.lab1024.sa.admin.module.business.supplier.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 供应商 添加表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class SupplierAddForm {

    @Schema(description = "供应商编码")
    @NotBlank(message = "供应商编码不能为空")
    @Size(max = 50, message = "供应商编码最多50字符")
    private String supplierCode;

    @Schema(description = "供应商名称")
    @NotBlank(message = "供应商名称不能为空")
    @Size(max = 200, message = "供应商名称最多200字符")
    private String supplierName;

    @Schema(description = "简称")
    @Size(max = 100, message = "简称最多100字符")
    private String shortName;

    @Schema(description = "统一社会信用代码")
    @Size(max = 50, message = "统一社会信用代码最多50字符")
    private String unifiedSocialCreditCode;

    @Schema(description = "资质等级")
    @Size(max = 50, message = "资质等级最多50字符")
    private String qualificationLevel;

    @Schema(description = "经营范围")
    @Size(max = 500, message = "经营范围最多500字符")
    private String businessScope;

    @Schema(description = "联系人")
    @Size(max = 50, message = "联系人最多50字符")
    private String contactPerson;

    @Schema(description = "联系电话")
    @Size(max = 20, message = "联系电话最多20字符")
    private String contactPhone;

    @Schema(description = "邮箱")
    @Size(max = 100, message = "邮箱最多100字符")
    private String email;

    @Schema(description = "详细地址")
    @Size(max = 300, message = "详细地址最多300字符")
    private String address;

    @Schema(description = "省份编码")
    private Integer province;

    @Schema(description = "省份名称")
    @Size(max = 50, message = "省份名称最多50字符")
    private String provinceName;

    @Schema(description = "城市编码")
    private Integer city;

    @Schema(description = "城市名称")
    @Size(max = 50, message = "城市名称最多50字符")
    private String cityName;

    @Schema(description = "开户银行")
    @Size(max = 100, message = "开户银行最多100字符")
    private String bankName;

    @Schema(description = "银行账号")
    @Size(max = 50, message = "银行账号最多50字符")
    private String bankAccount;

    @Schema(description = "营业执照附件")
    @Size(max = 500, message = "营业执照附件路径最多500字符")
    private String businessLicense;

    @Schema(description = "资质文件附件")
    @Size(max = 500, message = "资质文件附件路径最多500字符")
    private String qualificationFile;

    @Schema(description = "供应商评级(1-5星)")
    @Min(value = 1, message = "评级最小为1星")
    @Max(value = 5, message = "评级最大为5星")
    private Integer rating;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注最多500字符")
    private String remark;
}
