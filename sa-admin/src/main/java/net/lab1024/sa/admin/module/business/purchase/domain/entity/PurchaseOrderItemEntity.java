package net.lab1024.sa.admin.module.business.purchase.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购申请单明细 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_purchase_order_item")
public class PurchaseOrderItemEntity {

    @TableId(type = IdType.AUTO)
    private Long purchaseOrderItemId;

    private Long purchaseOrderId;

    private Long materialId;

    private String materialCode;

    private String materialName;

    private String specificationModel;

    private String unit;

    private BigDecimal purchaseQuantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private Long supplierId;

    private String supplierName;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
