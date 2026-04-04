package net.lab1024.sa.admin.module.business.purchase.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购申请单 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_purchase_order")
public class PurchaseOrderEntity {

    @TableId(type = IdType.AUTO)
    private Long purchaseOrderId;

    private String purchaseOrderNo;

    private Long projectId;

    private String projectName;

    private Integer purchaseType;

    private String purchaseReason;

    private BigDecimal totalAmount;

    private LocalDate expectedArrivalDate;

    private Integer approvalStatus;

    private Long approvalFlowId;

    private Integer currentApprovalNode;

    private String remark;

    private Boolean deletedFlag;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
