package net.lab1024.sa.admin.module.business.outbound.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 出库申请单明细 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_outbound_order_item")
public class OutboundOrderItemEntity {

    @TableId(type = IdType.AUTO)
    private Long outboundOrderItemId;

    private Long outboundOrderId;

    private Long materialId;

    private String materialCode;

    private String materialName;

    private String specificationModel;

    private String unit;

    private BigDecimal requestQuantity;

    private BigDecimal approvedQuantity;

    private BigDecimal actualQuantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private String batchNo;

    private String storageLocation;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
