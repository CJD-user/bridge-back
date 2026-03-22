-- =============================================
-- 桥梁器械材料管理系统 - 供应商管理模块
-- =============================================

-- ----------------------------
-- 供应商信息表
-- ----------------------------
DROP TABLE IF EXISTS `b_supplier`;
CREATE TABLE `b_supplier` (
    `supplier_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
    `supplier_code` VARCHAR(50) NOT NULL COMMENT '供应商编码',
    `supplier_name` VARCHAR(200) NOT NULL COMMENT '供应商名称',
    `short_name` VARCHAR(100) NULL COMMENT '简称',
    `unified_social_credit_code` VARCHAR(50) NULL COMMENT '统一社会信用代码',
    `qualification_level` VARCHAR(50) NULL COMMENT '资质等级',
    `business_scope` VARCHAR(500) NULL COMMENT '经营范围',
    `contact_person` VARCHAR(50) NULL COMMENT '联系人',
    `contact_phone` VARCHAR(20) NULL COMMENT '联系电话',
    `email` VARCHAR(100) NULL COMMENT '邮箱',
    `address` VARCHAR(300) NULL COMMENT '详细地址',
    `province` INT NULL COMMENT '省份编码',
    `province_name` VARCHAR(50) NULL COMMENT '省份名称',
    `city` INT NULL COMMENT '城市编码',
    `city_name` VARCHAR(50) NULL COMMENT '城市名称',
    `bank_name` VARCHAR(100) NULL COMMENT '开户银行',
    `bank_account` VARCHAR(50) NULL COMMENT '银行账号',
    `business_license` VARCHAR(500) NULL COMMENT '营业执照附件',
    `qualification_file` VARCHAR(500) NULL COMMENT '资质文件附件',
    `rating` INT DEFAULT 0 COMMENT '供应商评级: 1-5星',
    `total_supply_count` INT DEFAULT 0 COMMENT '累计供货次数',
    `total_supply_amount` DECIMAL(15, 2) DEFAULT 0.00 COMMENT '累计供货金额',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `disabled_flag` TINYINT(1) DEFAULT 0 COMMENT '禁用状态: 0启用 1禁用',
    `deleted_flag` TINYINT(1) DEFAULT 0 COMMENT '删除状态: 0未删除 1已删除',
    `create_user_id` BIGINT NULL COMMENT '创建人ID',
    `create_user_name` VARCHAR(50) NULL COMMENT '创建人姓名',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`supplier_id`),
    UNIQUE KEY `uk_supplier_code` (`supplier_code`),
    KEY `idx_supplier_name` (`supplier_name`),
    KEY `idx_contact_person` (`contact_person`),
    KEY `idx_province_city` (`province`, `city`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商信息表';

-- ----------------------------
-- 初始化示例数据
-- ----------------------------
INSERT INTO `b_supplier` (`supplier_code`, `supplier_name`, `short_name`, `unified_social_credit_code`, `qualification_level`, `contact_person`, `contact_phone`, `province`, `province_name`, `city`, `city_name`, `address`, `bank_name`, `bank_account`, `rating`, `remark`) VALUES
('SUP001', '北京钢铁集团有限公司', '北京钢铁', '91110000123456789X', 'A级', '张三', '13800138001', 110000, '北京市', 110100, '北京市', '北京市朝阳区建国路88号', '中国工商银行', '6222021234567890123', 5, '优质供应商'),
('SUP002', '上海水泥建材有限公司', '上海水泥', '91310000123456789Y', 'B级', '李四', '13800138002', 310000, '上海市', 310100, '上海市', '上海市浦东新区张江路100号', '中国建设银行', '6222021234567890124', 4, '长期合作供应商');
