package net.lab1024.sa.admin.module.business.approval.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审批流程 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_approval_flow")
public class ApprovalFlowEntity {

    @TableId(type = IdType.AUTO)
    private Long approvalFlowId;

    private String flowName;

    private String flowCode;

    private Integer businessType;

    private String description;

    private Integer status;

    private Boolean deletedFlag;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
