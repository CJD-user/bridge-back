-- =============================================
-- 桥梁器械材料管理系统 - 项目管理模块
-- =============================================

-- ----------------------------
-- 项目信息表
-- ----------------------------
DROP TABLE IF EXISTS `b_project`;
CREATE TABLE `b_project` (
    `project_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    `project_no` VARCHAR(50) NOT NULL COMMENT '项目编号',
    `project_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `project_type` INT DEFAULT 1 COMMENT '项目类型: 1-桥梁工程 2-道路工程 3-隧道工程 4-其他',
    `project_status` INT DEFAULT 0 COMMENT '项目状态: 0-筹备中 1-进行中 2-已完工 3-已暂停 4-已取消',
    `project_address` VARCHAR(500) NULL COMMENT '项目地址',
    `longitude` DECIMAL(10, 6) NULL COMMENT '经度',
    `latitude` DECIMAL(10, 6) NULL COMMENT '纬度',
    `start_date` DATE NULL COMMENT '计划开始日期',
    `end_date` DATE NULL COMMENT '计划结束日期',
    `actual_start_date` DATE NULL COMMENT '实际开始日期',
    `actual_end_date` DATE NULL COMMENT '实际结束日期',
    `manager_id` BIGINT NULL COMMENT '项目经理ID',
    `manager_name` VARCHAR(50) NULL COMMENT '项目经理姓名',
    `manager_phone` VARCHAR(20) NULL COMMENT '项目经理电话',
    `budget_amount` DECIMAL(15, 2) NULL COMMENT '项目预算金额',
    `contract_amount` DECIMAL(15, 2) NULL COMMENT '合同金额',
    `client_name` VARCHAR(200) NULL COMMENT '委托单位',
    `client_contact` VARCHAR(50) NULL COMMENT '委托方联系人',
    `client_phone` VARCHAR(20) NULL COMMENT '委托方联系电话',
    `description` TEXT NULL COMMENT '项目描述',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT NULL COMMENT '创建人ID',
    `create_user_name` VARCHAR(50) NULL COMMENT '创建人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`project_id`),
    UNIQUE KEY `uk_project_no` (`project_no`),
    KEY `idx_project_name` (`project_name`),
    KEY `idx_project_type` (`project_type`),
    KEY `idx_project_status` (`project_status`),
    KEY `idx_manager_id` (`manager_id`),
    KEY `idx_start_date` (`start_date`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目信息表';
