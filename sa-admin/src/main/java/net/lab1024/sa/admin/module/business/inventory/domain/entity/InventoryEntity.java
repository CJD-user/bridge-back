package net.lab1024.sa.admin.module.business.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_inventory")
public class InventoryEntity {

    @TableId(type = IdType.AUTO)
    private Long inventoryId;

    private Long materialId;

    private String materialCode;

    private String materialName;

    private String specificationModel;

    private String unit;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expirationDate;

    private BigDecimal quantity;

    private BigDecimal lockedQuantity;

    private BigDecimal availableQuantity;

    private BigDecimal unitPrice;

    private String storageLocation;

    private Integer warningStatus;

    private LocalDateTime lastInboundTime;

    private LocalDateTime lastOutboundTime;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
