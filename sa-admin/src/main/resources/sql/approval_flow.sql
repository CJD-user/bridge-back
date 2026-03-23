-- =============================================
-- 桥梁器械材料管理系统 - 审批流程管理模块
-- =============================================

-- ----------------------------
-- 审批流程配置表
-- ----------------------------
DROP TABLE IF EXISTS `b_approval_flow`;
CREATE TABLE `b_approval_flow` (
    `approval_flow_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '审批流程ID',
    `flow_name` VARCHAR(100) NOT NULL COMMENT '流程名称',
    `flow_code` VARCHAR(50) NOT NULL COMMENT '流程编码',
    `business_type` INT NOT NULL COMMENT '业务类型: 1-采购申请 2-出库申请',
    `description` VARCHAR(500) NULL COMMENT '流程描述',
    `status` INT DEFAULT 1 COMMENT '状态: 0-停用 1-启用',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT NULL COMMENT '创建人ID',
    `create_user_name` VARCHAR(50) NULL COMMENT '创建人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`approval_flow_id`),
    UNIQUE KEY `uk_flow_code` (`flow_code`),
    KEY `idx_business_type` (`business_type`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批流程配置表';

-- ----------------------------
-- 审批节点配置表
-- ----------------------------
DROP TABLE IF EXISTS `b_approval_node`;
CREATE TABLE `b_approval_node` (
    `approval_node_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '审批节点ID',
    `approval_flow_id` BIGINT NOT NULL COMMENT '审批流程ID',
    `node_name` VARCHAR(100) NOT NULL COMMENT '节点名称',
    `node_code` VARCHAR(50) NULL COMMENT '节点编码',
    `node_order` INT NOT NULL COMMENT '节点顺序',
    `approver_type` INT NOT NULL COMMENT '审批人类型: 1-指定人员 2-部门负责人 3-角色 4-发起人自选',
    `approver_ids` VARCHAR(500) NULL COMMENT '审批人ID列表(逗号分隔)',
    `approver_names` VARCHAR(500) NULL COMMENT '审批人姓名列表(逗号分隔)',
    `role_id` BIGINT NULL COMMENT '角色ID(当approver_type=3时)',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`approval_node_id`),
    KEY `idx_approval_flow_id` (`approval_flow_id`),
    KEY `idx_node_order` (`node_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批节点配置表';

-- ----------------------------
-- 初始化示例数据
-- ----------------------------
INSERT INTO `b_approval_flow` (`flow_name`, `flow_code`, `business_type`, `description`, `status`, `create_user_name`) VALUES
('采购申请审批流程', 'PURCHASE_APPROVAL', 1, '采购申请审批流程', 1, '管理员'),
('出库申请审批流程', 'OUTBOUND_APPROVAL', 2, '出库申请审批流程', 1, '管理员');

INSERT INTO `b_approval_node` (`approval_flow_id`, `node_name`, `node_code`, `node_order`, `approver_type`, `approver_ids`, `approver_names`, `role_id`, `remark`) VALUES
(1, '部门主管审批', 'NODE_1', 1, 2, NULL, NULL, NULL, '部门负责人自动审批'),
(1, '采购经理审批', 'NODE_2', 2, 3, NULL, NULL, 1, '角色审批'),
(1, '总经理审批', 'NODE_3', 3, 4, NULL, NULL, NULL, '发起人自选审批人'),
(2, '仓库管理员审批', 'NODE_1', 1, 1, '3', '王五', NULL, '仓库管理员审批'),
(2, '部门主管审批', 'NODE_2', 2, 2, NULL, NULL, NULL, '部门负责人审批');
