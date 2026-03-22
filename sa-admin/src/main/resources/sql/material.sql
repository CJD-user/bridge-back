-- =============================================
-- 桥梁器械材料管理系统 - 材料信息管理模块
-- =============================================

-- ----------------------------
-- 材料信息表
-- ----------------------------
DROP TABLE IF EXISTS `b_material`;
CREATE TABLE `b_material` (
    `material_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '材料ID',
    `material_code` VARCHAR(50) NOT NULL COMMENT '材料编码',
    `material_name` VARCHAR(200) NOT NULL COMMENT '材料名称',
    `material_category_id` BIGINT COMMENT '材料分类ID',
    `specification_model` VARCHAR(200) COMMENT '规格型号',
    `unit` VARCHAR(20) COMMENT '计量单位',
    `unit_price` DECIMAL(12,2) COMMENT '参考单价',
    `safety_stock_threshold` DECIMAL(12,2) COMMENT '安全库存阈值',
    `shelf_life_days` INT COMMENT '保质期(天)',
    `brand` VARCHAR(100) COMMENT '品牌',
    `manufacturer` VARCHAR(200) COMMENT '生产厂家',
    `material_image` VARCHAR(500) COMMENT '材料图片',
    `attachment` VARCHAR(1000) COMMENT '附件(多个fileKey逗号分隔)',
    `remark` VARCHAR(500) COMMENT '备注',
    `disabled_flag` TINYINT(1) DEFAULT 0 COMMENT '禁用状态: 0启用 1禁用',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT COMMENT '创建人ID',
    `create_user_name` VARCHAR(50) COMMENT '创建人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`material_id`),
    UNIQUE KEY `uk_material_code` (`material_code`),
    KEY `idx_category_id` (`material_category_id`),
    KEY `idx_material_name` (`material_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='材料信息表';

-- 注意：数据库中实际表名为 b_material
