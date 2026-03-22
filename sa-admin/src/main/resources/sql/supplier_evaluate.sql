-- =============================================
-- 桥梁器械材料管理系统 - 供应商评价模块
-- =============================================

-- ----------------------------
-- 供应商评价表
-- ----------------------------
DROP TABLE IF EXISTS `b_supplier_evaluate`;
CREATE TABLE `b_supplier_evaluate` (
    `evaluate_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
    `evaluate_type` INT NULL COMMENT '评价类型: 1-季度评价 2-年度评价 3-项目评价',
    `evaluate_date` DATE NULL COMMENT '评价日期',
    `quality_score` DECIMAL(3, 1) NULL COMMENT '质量评分(1-5分)',
    `delivery_score` DECIMAL(3, 1) NULL COMMENT '交付评分(1-5分)',
    `service_score` DECIMAL(3, 1) NULL COMMENT '服务评分(1-5分)',
    `price_score` DECIMAL(3, 1) NULL COMMENT '价格评分(1-5分)',
    `total_score` DECIMAL(3, 1) NULL COMMENT '综合评分',
    `evaluate_content` VARCHAR(1000) NULL COMMENT '评价内容',
    `attachment` VARCHAR(1000) NULL COMMENT '附件',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT NULL COMMENT '评价人ID',
    `create_user_name` VARCHAR(50) NULL COMMENT '评价人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`evaluate_id`),
    KEY `idx_supplier_id` (`supplier_id`),
    KEY `idx_evaluate_date` (`evaluate_date`),
    KEY `idx_evaluate_type` (`evaluate_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商评价表';

-- ----------------------------
-- 初始化示例数据
-- ----------------------------
INSERT INTO `b_supplier_evaluate` (`supplier_id`, `evaluate_type`, `evaluate_date`, `quality_score`, `delivery_score`, `service_score`, `price_score`, `total_score`, `evaluate_content`, `create_user_name`) VALUES
(1, 1, '2024-01-15', 4.5, 4.0, 4.5, 4.0, 4.3, '本季度供货质量稳定，交付及时，服务态度良好。', '管理员'),
(1, 2, '2024-01-01', 4.8, 4.5, 4.8, 4.2, 4.6, '年度综合评价优秀，建议继续合作。', '管理员'),
(2, 1, '2024-01-15', 4.0, 3.5, 4.0, 4.5, 4.0, '本季度供货基本正常，交付略有延迟。', '管理员');
