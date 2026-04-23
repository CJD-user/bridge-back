-- =============================================
-- 桥梁器械材料管理系统 - 库存预警与入库记录
-- =============================================

-- ----------------------------
-- 材料表新增字段
-- ----------------------------
ALTER TABLE b_material ADD COLUMN current_stock DECIMAL(12, 2) DEFAULT 0.00 COMMENT '当前库存数量' AFTER safety_stock_threshold;
ALTER TABLE b_material ADD COLUMN min_warning_quantity DECIMAL(12, 2) DEFAULT 0.00 COMMENT '最低预警数量' AFTER current_stock;

-- ----------------------------
-- 库存预警记录表
-- ----------------------------
DROP TABLE IF EXISTS `b_inventory_warning`;
CREATE TABLE `b_inventory_warning` (
    `warning_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '预警ID',
    `inventory_id` BIGINT NULL COMMENT '库存ID',
    `material_id` BIGINT NOT NULL COMMENT '材料ID',
    `material_code` VARCHAR(50) NULL COMMENT '材料编码',
    `material_name` VARCHAR(200) NULL COMMENT '材料名称',
    `warning_type` INT NOT NULL COMMENT '预警类型: 1-库存不足 2-即将过期 3-已过期',
    `warning_level` INT NULL COMMENT '预警级别: 1-一般 2-重要 3-紧急',
    `current_quantity` DECIMAL(12, 2) NULL COMMENT '当前数量',
    `threshold_quantity` DECIMAL(12, 2) NULL COMMENT '阈值数量',
    `expiration_date` DATE NULL COMMENT '有效期至',
    `warning_content` VARCHAR(500) NULL COMMENT '预警内容',
    `handle_status` INT DEFAULT 0 COMMENT '处理状态: 0-未处理 1-已处理 2-已忽略',
    `handle_user_id` BIGINT NULL COMMENT '处理人ID',
    `handle_user_name` VARCHAR(50) NULL COMMENT '处理人姓名',
    `handle_time` DATETIME NULL COMMENT '处理时间',
    `handle_remark` VARCHAR(500) NULL COMMENT '处理备注',
    `notification_sent` TINYINT(1) DEFAULT 0 COMMENT '是否已发送通知: 0-否 1-是',
    `notification_time` DATETIME NULL COMMENT '通知发送时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`warning_id`),
    KEY `idx_material_id` (`material_id`),
    KEY `idx_warning_type` (`warning_type`),
    KEY `idx_handle_status` (`handle_status`),
    KEY `idx_inventory_id` (`inventory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存预警记录表';

-- ----------------------------
-- 入库记录表
-- ----------------------------
DROP TABLE IF EXISTS `b_inbound_record`;
CREATE TABLE `b_inbound_record` (
    `inbound_record_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '入库记录ID',
    `material_id` BIGINT NOT NULL COMMENT '材料ID',
    `material_code` VARCHAR(50) NULL COMMENT '材料编码',
    `material_name` VARCHAR(200) NULL COMMENT '材料名称',
    `specification_model` VARCHAR(200) NULL COMMENT '规格型号',
    `unit` VARCHAR(20) NULL COMMENT '计量单位',
    `batch_no` VARCHAR(100) NULL COMMENT '批次号',
    `production_date` DATE NULL COMMENT '生产日期',
    `expiration_date` DATE NULL COMMENT '有效期至',
    `quantity` DECIMAL(12, 2) NOT NULL COMMENT '入库数量',
    `unit_price` DECIMAL(12, 2) NULL COMMENT '单价',
    `total_price` DECIMAL(15, 2) NULL COMMENT '总价',
    `supplier_id` BIGINT NULL COMMENT '供应商ID',
    `supplier_name` VARCHAR(200) NULL COMMENT '供应商名称',
    `storage_location` VARCHAR(100) NULL COMMENT '存放位置',
    `inbound_type` INT DEFAULT 1 COMMENT '入库类型: 1-采购入库 2-退货入库 3-其他',
    `project_id` BIGINT NULL COMMENT '项目ID',
    `project_name` VARCHAR(200) NULL COMMENT '项目名称',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT NULL COMMENT '操作人ID',
    `create_user_name` VARCHAR(50) NULL COMMENT '操作人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`inbound_record_id`),
    KEY `idx_material_id` (`material_id`),
    KEY `idx_supplier_id` (`supplier_id`),
    KEY `idx_project_id` (`project_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入库记录表';
