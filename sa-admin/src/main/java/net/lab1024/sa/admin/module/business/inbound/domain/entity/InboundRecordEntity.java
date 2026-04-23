package net.lab1024.sa.admin.module.business.inbound.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 入库记录 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_inbound_record")
public class InboundRecordEntity {

    @TableId(type = IdType.AUTO)
    private Long inboundRecordId;

    private Long materialId;

    private String materialCode;

    private String materialName;

    private String specificationModel;

    private String unit;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expirationDate;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private Long supplierId;

    private String supplierName;

    private String storageLocation;

    private Integer inboundType;

    private Long projectId;

    private String projectName;

    private String remark;

    private Boolean deletedFlag;

    private Long createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
