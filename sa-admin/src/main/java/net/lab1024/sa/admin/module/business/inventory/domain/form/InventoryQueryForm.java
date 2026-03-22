package net.lab1024.sa.admin.module.business.inventory.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 库存 分页查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InventoryQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "材料ID")
    private Long materialId;

    @Schema(description = "材料编码")
    @Length(max = 50, message = "材料编码最多50字符")
    private String materialCode;

    @Schema(description = "材料名称")
    @Length(max = 200, message = "材料名称最多200字符")
    private String materialName;

    @Schema(description = "批次号")
    @Length(max = 100, message = "批次号最多100字符")
    private String batchNo;

    @Schema(description = "预警状态: 0-正常 1-库存不足 2-即将过期 3-已过期")
    private Integer warningStatus;

    @Schema(description = "存放位置")
    @Length(max = 100, message = "存放位置最多100字符")
    private String storageLocation;
}
