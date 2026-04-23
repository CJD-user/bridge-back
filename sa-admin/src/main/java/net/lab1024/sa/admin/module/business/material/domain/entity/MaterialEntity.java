package net.lab1024.sa.admin.module.business.material.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 材料 实体类
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Data
@TableName("b_material")
public class MaterialEntity {

    /**
     * 材料ID
     */
    @TableId(type = IdType.AUTO)
    private Long materialId;

    /**
     * 材料编码
     */
    private String materialCode;

    /**
     * 材料名称
     */
    private String materialName;

    /**
     * 材料分类ID
     */
    private Long materialCategoryId;

    /**
     * 规格型号
     */
    private String specificationModel;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 参考单价
     */
    private BigDecimal unitPrice;

    /**
     * 安全库存阈值
     */
    private BigDecimal safetyStockThreshold;

    private BigDecimal currentStock;

    private BigDecimal minWarningQuantity;

    private Integer shelfLifeDays;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 材料图片
     */
    private String materialImage;

    /**
     * 附件(多个fileKey逗号分隔)
     */
    private String attachment;

    /**
     * 备注
     */
    private String remark;

    /**
     * 禁用状态: 0启用 1禁用
     */
    private Boolean disabledFlag;

    /**
     * 删除状态: 0未删除 1已删除
     */
    private Boolean deletedFlag;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
