-- =============================================
-- 桥梁器械材料管理系统 - 库存管理模块
-- =============================================

-- ----------------------------
-- 库存表
-- ----------------------------
DROP TABLE IF EXISTS `b_inventory`;
CREATE TABLE `b_inventory` (
    `inventory_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '库存ID',
    `material_id` BIGINT NOT NULL COMMENT '材料ID',
    `material_code` VARCHAR(50) NULL COMMENT '材料编码',
    `material_name` VARCHAR(200) NULL COMMENT '材料名称',
    `specification_model` VARCHAR(200) NULL COMMENT '规格型号',
    `unit` VARCHAR(20) NULL COMMENT '计量单位',
    `batch_no` VARCHAR(100) NULL COMMENT '批次号',
    `production_date` DATE NULL COMMENT '生产日期',
    `expiration_date` DATE NULL COMMENT '有效期至',
    `quantity` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '库存数量',
    `locked_quantity` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '锁定数量(已申请待出库)',
    `available_quantity` DECIMAL(12, 2) DEFAULT 0.00 COMMENT '可用数量',
    `unit_price` DECIMAL(12, 2) NULL COMMENT '单价',
    `storage_location` VARCHAR(100) NULL COMMENT '存放位置',
    `warning_status` INT DEFAULT 0 COMMENT '预警状态: 0-正常 1-库存不足 2-即将过期 3-已过期',
    `last_inbound_time` DATETIME NULL COMMENT '最后入库时间',
    `last_outbound_time` DATETIME NULL COMMENT '最后出库时间',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`inventory_id`),
    UNIQUE KEY `uk_material_batch` (`material_id`, `batch_no`),
    KEY `idx_material_id` (`material_id`),
    KEY `idx_expiration_date` (`expiration_date`),
    KEY `idx_warning_status` (`warning_status`),
    KEY `idx_batch_no` (`batch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

-- ----------------------------
-- 初始化示例数据
-- ----------------------------
INSERT INTO `b_inventory` (`material_id`, `material_code`, `material_name`, `specification_model`, `unit`, `batch_no`, `production_date`, `expiration_date`, `quantity`, `locked_quantity`, `available_quantity`, `unit_price`, `storage_location`, `warning_status`, `last_inbound_time`, `remark`) VALUES
(1, 'M001', '螺纹钢', 'HRB400-16', '吨', 'B20240101', '2024-01-01', '2025-01-01', 100.00, 0.00, 100.00, 4500.00, 'A区-01货架', 0, NOW(), '常规库存'),
(1, 'M001', '螺纹钢', 'HRB400-16', '吨', 'B20240201', '2024-02-01', '2025-02-01', 50.00, 10.00, 40.00, 4600.00, 'A区-02货架', 0, NOW(), '部分锁定'),
(2, 'M002', '水泥', 'P.O42.5', '吨', 'C20240301', '2024-03-01', '2024-09-01', 200.00, 0.00, 200.00, 450.00, 'B区-仓库', 2, NOW(), '即将过期');
