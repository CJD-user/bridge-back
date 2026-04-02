package net.lab1024.sa.admin.module.business.project.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 项目信息 VO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
public class ProjectVO {

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "项目类型: 1-桥梁工程 2-道路工程 3-隧道工程 4-其他")
    private Integer projectType;

    @Schema(description = "项目类型名称")
    private String projectTypeName;

    @Schema(description = "项目状态: 0-筹备中 1-进行中 2-已完工 3-已暂停 4-已取消")
    private Integer projectStatus;

    @Schema(description = "项目状态名称")
    private String projectStatusName;

    @Schema(description = "项目地址")
    private String projectAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "计划开始日期")
    private LocalDate startDate;

    @Schema(description = "计划结束日期")
    private LocalDate endDate;

    @Schema(description = "实际开始日期")
    private LocalDate actualStartDate;

    @Schema(description = "实际结束日期")
    private LocalDate actualEndDate;

    @Schema(description = "项目经理ID")
    private Long managerId;

    @Schema(description = "项目经理姓名")
    private String managerName;

    @Schema(description = "项目经理电话")
    private String managerPhone;

    @Schema(description = "项目预算金额")
    private BigDecimal budgetAmount;

    @Schema(description = "合同金额")
    private BigDecimal contractAmount;

    @Schema(description = "委托单位")
    private String clientName;

    @Schema(description = "委托方联系人")
    private String clientContact;

    @Schema(description = "委托方联系电话")
    private String clientPhone;

    @Schema(description = "项目描述")
    private String description;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
