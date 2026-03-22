-- =============================================
-- 桥梁器械材料管理系统 - 材料分类管理模块
-- =============================================

-- ----------------------------
-- 材料分类表
-- ----------------------------
DROP TABLE IF EXISTS `b_material_category`;
CREATE TABLE `b_material_category` (
    `material_category_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `category_name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `category_code` VARCHAR(50) NULL COMMENT '分类编码',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父级分类ID',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `disabled_flag` TINYINT(1) DEFAULT 0 COMMENT '禁用状态: 0启用 1禁用',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`material_category_id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_category_code` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='材料分类表';

-- ----------------------------
-- 初始化示例数据
-- ----------------------------
INSERT INTO `b_material_category` (`category_name`, `category_code`, `parent_id`, `sort`, `remark`) VALUES
('钢材类', 'GC', 0, 1, '各类钢材材料'),
('水泥类', 'SN', 0, 2, '各类水泥材料'),
('砂石类', 'SS', 0, 3, '各类砂石材料'),
('螺栓类', 'LS', 0, 4, '各类螺栓紧固件');

INSERT INTO `b_material_category` (`category_name`, `category_code`, `parent_id`, `sort`, `remark`) VALUES
('螺纹钢', 'GC-LWG', 1, 1, '螺纹钢'),
('钢板', 'GC-GB', 1, 2, '钢板材料');
