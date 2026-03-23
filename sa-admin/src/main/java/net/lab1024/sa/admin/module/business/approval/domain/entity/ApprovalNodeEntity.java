package net.lab1024.sa.admin.module.business.approval.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审批节点 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_approval_node")
public class ApprovalNodeEntity {

    @TableId(type = IdType.AUTO)
    private Long approvalNodeId;

    private Long approvalFlowId;

    private String nodeName;

    private String nodeCode;

    private Integer nodeOrder;

    private Integer approverType;

    private String approverIds;

    private String approverNames;

    private Long roleId;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
