package net.lab1024.sa.admin.module.business.inbound.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 入库记录 查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class InboundRecordQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "材料ID")
    private Long materialId;

    @Schema(description = "入库类型: 1-采购入库 2-退货入库 3-其他")
    private Integer inboundType;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "删除状态", hidden = true)
    private Boolean deletedFlag;
}
