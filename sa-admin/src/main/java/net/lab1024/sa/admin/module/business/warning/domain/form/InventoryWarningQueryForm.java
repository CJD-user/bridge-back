package net.lab1024.sa.admin.module.business.warning.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 库存预警 查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryWarningQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "材料名称")
    @Length(max = 200, message = "材料名称最多200字符")
    private String materialName;

    @Schema(description = "预警类型: 1-库存不足 2-即将过期 3-已过期")
    private Integer warningType;

    @Schema(description = "预警级别: 1-一般 2-重要 3-紧急")
    private Integer warningLevel;

    @Schema(description = "处理状态: 0-未处理 1-已处理 2-已忽略")
    private Integer handleStatus;
}
