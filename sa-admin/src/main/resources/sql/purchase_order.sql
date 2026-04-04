-- =============================================
-- 桥梁器械材料管理系统 - 采购申请管理模块
-- =============================================

-- ----------------------------
-- 采购申请单主表
-- ----------------------------
DROP TABLE IF EXISTS `b_purchase_order`;
CREATE TABLE `b_purchase_order` (
    `purchase_order_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '采购申请单ID',
    `purchase_order_no` VARCHAR(50) NOT NULL COMMENT '采购单号',
    `project_id` BIGINT NULL COMMENT '项目ID',
    `project_name` VARCHAR(200) NULL COMMENT '项目名称',
    `purchase_type` INT NULL COMMENT '采购类型: 1-常规采购 2-紧急采购 3-补货采购',
    `purchase_reason` VARCHAR(500) NULL COMMENT '采购原因',
    `total_amount` DECIMAL(15, 2) NULL COMMENT '采购总金额',
    `expected_arrival_date` DATE NULL COMMENT '期望到货日期',
    `approval_status` INT DEFAULT 0 COMMENT '审批状态: 0-待提交 1-审批中 2-已通过 3-已驳回 4-已撤回',
    `approval_flow_id` BIGINT NULL COMMENT '审批流程ID',
    `current_approval_node` INT NULL COMMENT '当前审批节点序号',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT NULL COMMENT '申请人ID',
    `create_user_name` VARCHAR(50) NULL COMMENT '申请人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`purchase_order_id`),
    UNIQUE KEY `uk_purchase_order_no` (`purchase_order_no`),
    KEY `idx_project_id` (`project_id`),
    KEY `idx_approval_status` (`approval_status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购申请单主表';

-- ----------------------------
-- 采购申请单明细表
-- ----------------------------
DROP TABLE IF EXISTS `b_purchase_order_item`;
CREATE TABLE `b_purchase_order_item` (
    `purchase_order_item_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '明细ID',
    `purchase_order_id` BIGINT NOT NULL COMMENT '采购申请单ID',
    `material_id` BIGINT NOT NULL COMMENT '材料ID',
    `material_code` VARCHAR(50) NULL COMMENT '材料编码',
    `material_name` VARCHAR(200) NULL COMMENT '材料名称',
    `specification_model` VARCHAR(200) NULL COMMENT '规格型号',
    `unit` VARCHAR(20) NULL COMMENT '计量单位',
    `purchase_quantity` DECIMAL(12, 2) NOT NULL COMMENT '采购数量',
    `unit_price` DECIMAL(12, 2) NULL COMMENT '单价',
    `total_price` DECIMAL(15, 2) NULL COMMENT '总价',
    `supplier_id` BIGINT NULL COMMENT '建议供应商ID',
    `supplier_name` VARCHAR(200) NULL COMMENT '建议供应商名称',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`purchase_order_item_id`),
    KEY `idx_purchase_order_id` (`purchase_order_id`),
    KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购申请单明细表';
