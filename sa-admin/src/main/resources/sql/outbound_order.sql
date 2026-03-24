-- =============================================
-- 桥梁器械材料管理系统 - 出库申请管理模块
-- =============================================

-- ----------------------------
-- 出库申请单主表
-- ----------------------------
DROP TABLE IF EXISTS `b_outbound_order`;
CREATE TABLE `b_outbound_order` (
    `outbound_order_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '出库单ID',
    `outbound_order_no` VARCHAR(50) NOT NULL COMMENT '出库单号',
    `project_id` BIGINT NULL COMMENT '项目ID',
    `project_name` VARCHAR(200) NULL COMMENT '项目名称',
    `outbound_type` INT NULL COMMENT '出库类型: 1-领用出库 2-调拨出库 3-报废出库',
    `outbound_date` DATE NULL COMMENT '出库日期',
    `receiver_id` BIGINT NULL COMMENT '领用人ID',
    `receiver_name` VARCHAR(50) NULL COMMENT '领用人姓名',
    `receiver_phone` VARCHAR(20) NULL COMMENT '领用人电话',
    `team_name` VARCHAR(100) NULL COMMENT '施工班组',
    `use_position` VARCHAR(200) NULL COMMENT '使用部位',
    `total_quantity` DECIMAL(12, 2) NULL COMMENT '出库总数量',
    `total_amount` DECIMAL(15, 2) NULL COMMENT '出库总金额',
    `approval_status` INT DEFAULT 0 COMMENT '审批状态: 0-待提交 1-审批中 2-已通过 3-已驳回 4-已撤回',
    `approval_flow_id` BIGINT NULL COMMENT '审批流程ID',
    `current_approval_node` INT NULL COMMENT '当前审批节点序号',
    `status` INT DEFAULT 0 COMMENT '状态: 0-待审批 1-已审批待出库 2-已出库 3-已作废',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT NULL COMMENT '申请人ID',
    `create_user_name` VARCHAR(50) NULL COMMENT '申请人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`outbound_order_id`),
    UNIQUE KEY `uk_outbound_order_no` (`outbound_order_no`),
    KEY `idx_project_id` (`project_id`),
    KEY `idx_outbound_type` (`outbound_type`),
    KEY `idx_approval_status` (`approval_status`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出库申请单主表';

-- ----------------------------
-- 出库申请单明细表
-- ----------------------------
DROP TABLE IF EXISTS `b_outbound_order_item`;
CREATE TABLE `b_outbound_order_item` (
    `outbound_order_item_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '明细ID',
    `outbound_order_id` BIGINT NOT NULL COMMENT '出库单ID',
    `material_id` BIGINT NOT NULL COMMENT '材料ID',
    `material_code` VARCHAR(50) NULL COMMENT '材料编码',
    `material_name` VARCHAR(200) NULL COMMENT '材料名称',
    `specification_model` VARCHAR(200) NULL COMMENT '规格型号',
    `unit` VARCHAR(20) NULL COMMENT '计量单位',
    `request_quantity` DECIMAL(12, 2) NOT NULL COMMENT '申请数量',
    `approved_quantity` DECIMAL(12, 2) NULL COMMENT '审批数量',
    `actual_quantity` DECIMAL(12, 2) NULL COMMENT '实际出库数量',
    `unit_price` DECIMAL(12, 2) NULL COMMENT '单价',
    `total_price` DECIMAL(15, 2) NULL COMMENT '总价',
    `batch_no` VARCHAR(100) NULL COMMENT '出库批号',
    `storage_location` VARCHAR(100) NULL COMMENT '存放位置',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`outbound_order_item_id`),
    KEY `idx_outbound_order_id` (`outbound_order_id`),
    KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出库申请单明细表';

-- ----------------------------
-- 审批记录表
-- ----------------------------
DROP TABLE IF EXISTS `b_approval_record`;
CREATE TABLE `b_approval_record` (
    `approval_record_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '审批记录ID',
    `approval_flow_id` BIGINT NOT NULL COMMENT '审批流程ID',
    `approval_node_id` BIGINT NULL COMMENT '审批节点ID',
    `business_type` INT NOT NULL COMMENT '业务类型: 1-采购申请 2-出库申请',
    `business_id` BIGINT NOT NULL COMMENT '业务单据ID',
    `business_no` VARCHAR(50) NULL COMMENT '业务单据编号',
    `node_order` INT NULL COMMENT '节点顺序',
    `approver_id` BIGINT NULL COMMENT '审批人ID',
    `approver_name` VARCHAR(50) NULL COMMENT '审批人姓名',
    `approval_status` INT NULL COMMENT '审批状态: 1-通过 2-打回',
    `approval_opinion` VARCHAR(500) NULL COMMENT '审批意见',
    `attachment` VARCHAR(1000) NULL COMMENT '附件',
    `approval_time` DATETIME NULL COMMENT '审批时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`approval_record_id`),
    KEY `idx_business` (`business_type`, `business_id`),
    KEY `idx_approver_id` (`approver_id`),
    KEY `idx_approval_flow_id` (`approval_flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批记录表';
