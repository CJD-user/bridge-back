package net.lab1024.sa.admin.module.business.supplier.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 供应商 分页查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class SupplierQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "供应商编码")
    @Length(max = 50, message = "供应商编码最多50字符")
    private String supplierCode;

    @Schema(description = "供应商名称")
    @Length(max = 200, message = "供应商名称最多200字符")
    private String supplierName;

    @Schema(description = "联系人")
    @Length(max = 50, message = "联系人最多50字符")
    private String contactPerson;

    @Schema(description = "省份编码")
    private Integer province;

    @Schema(description = "城市编码")
    private Integer city;

    @Schema(description = "供应商评级")
    private Integer rating;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;

    @Schema(hidden = true)
    private Boolean deletedFlag;
}
