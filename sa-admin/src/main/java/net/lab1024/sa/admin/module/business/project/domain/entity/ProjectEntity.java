package net.lab1024.sa.admin.module.business.project.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 项目信息 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_project")
public class ProjectEntity {

    @TableId(type = IdType.AUTO)
    private Long projectId;

    private String projectNo;

    private String projectName;

    private Integer projectType;

    private Integer projectStatus;

    private String projectAddress;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    private Long managerId;

    private String managerName;

    private String managerPhone;

    private BigDecimal budgetAmount;

    private BigDecimal contractAmount;

    private String clientName;

    private String clientContact;

    private String clientPhone;

    private String description;

    private String remark;

    private Boolean deletedFlag;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
