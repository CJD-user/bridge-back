package net.lab1024.sa.admin.module.business.warning.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存预警 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_inventory_warning")
public class InventoryWarningEntity {

    @TableId(type = IdType.AUTO)
    private Long warningId;

    private Long inventoryId;

    private Long materialId;

    private String materialCode;

    private String materialName;

    private Integer warningType;

    private Integer warningLevel;

    private BigDecimal currentQuantity;

    private BigDecimal thresholdQuantity;

    private LocalDate expirationDate;

    private String warningContent;

    private Integer handleStatus;

    private Long handleUserId;

    private String handleUserName;

    private LocalDateTime handleTime;

    private String handleRemark;

    private Boolean notificationSent;

    private LocalDateTime notificationTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
