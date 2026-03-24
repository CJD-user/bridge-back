package net.lab1024.sa.admin.module.business.approval.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审批记录 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_approval_record")
public class ApprovalRecordEntity {

    @TableId(type = IdType.AUTO)
    private Long approvalRecordId;

    private Long approvalFlowId;

    private Long approvalNodeId;

    private Integer businessType;

    private Long businessId;

    private String businessNo;

    private Integer nodeOrder;

    private Long approverId;

    private String approverName;

    private Integer approvalStatus;

    private String approvalOpinion;

    private String attachment;

    private LocalDateTime approvalTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
