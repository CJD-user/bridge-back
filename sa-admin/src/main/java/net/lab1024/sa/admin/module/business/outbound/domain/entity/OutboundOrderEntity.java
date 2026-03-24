package net.lab1024.sa.admin.module.business.outbound.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 出库申请单 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_outbound_order")
public class OutboundOrderEntity {

    @TableId(type = IdType.AUTO)
    private Long outboundOrderId;

    private String outboundOrderNo;

    private Long projectId;

    private String projectName;

    private Integer outboundType;

    private LocalDate outboundDate;

    private Long receiverId;

    private String receiverName;

    private String receiverPhone;

    private String teamName;

    private String usePosition;

    private BigDecimal totalQuantity;

    private BigDecimal totalAmount;

    private Integer approvalStatus;

    private Long approvalFlowId;

    private Integer currentApprovalNode;

    private Integer status;

    private String remark;

    private Boolean deletedFlag;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
