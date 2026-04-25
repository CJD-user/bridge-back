package net.lab1024.sa.admin.module.business.material.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 材料 分页查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class MaterialQueryForm extends PageParam {

    @Schema(description = "材料分类ID")
    private Long materialCategoryId;

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "材料编码")
    @Length(max = 50, message = "材料编码最多50字符")
    private String materialCode;

    @Schema(description = "材料名称")
    @Length(max = 200, message = "材料名称最多200字符")
    private String materialName;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;

    @Schema(hidden = true)
    private Boolean deletedFlag;
}
