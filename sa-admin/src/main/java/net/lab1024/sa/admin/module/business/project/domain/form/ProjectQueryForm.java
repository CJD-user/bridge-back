package net.lab1024.sa.admin.module.business.project.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * 项目信息 查询表单
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ProjectQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 100, message = "搜索词最多100字符")
    private String searchWord;

    @Schema(description = "项目编号")
    @Length(max = 50, message = "项目编号最多50字符")
    private String projectNo;

    @Schema(description = "项目名称")
    @Length(max = 200, message = "项目名称最多200字符")
    private String projectName;

    @Schema(description = "项目类型: 1-桥梁工程 2-道路工程 3-隧道工程 4-其他")
    private Integer projectType;

    @Schema(description = "项目状态: 0-筹备中 1-进行中 2-已完工 3-已暂停 4-已取消")
    private Integer projectStatus;

    @Schema(description = "项目经理姓名")
    @Length(max = 50, message = "项目经理姓名最多50字符")
    private String managerName;

    @Schema(description = "计划开始日期-开始")
    private LocalDate startDateBegin;

    @Schema(description = "计划开始日期-结束")
    private LocalDate startDateEnd;

    @Schema(description = "删除状态: 0未删除 1已删除", hidden = true)
    private Boolean deletedFlag;
}
