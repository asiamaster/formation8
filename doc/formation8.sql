-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.20 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 formation8 的数据库结构
CREATE DATABASE IF NOT EXISTS `formation8` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `formation8`;

-- 导出  表 formation8.bank_card 结构
CREATE TABLE IF NOT EXISTS `bank_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `card_type` int(2) DEFAULT NULL COMMENT '默认选中储蓄卡，目前仅“储蓄卡”一个项',
  `account_name` varchar(20) DEFAULT NULL COMMENT '法人银行账户：显示认证时填写的法人姓名，企业',
  `id_number` varchar(40) DEFAULT NULL COMMENT '法人银行账户：显示认证时填写的法人身份证号，',
  `subbranch` varchar(40) DEFAULT NULL COMMENT '选填，允许5-50位汉字、字母和数字，不允许特殊',
  `card_number` varchar(30) DEFAULT NULL COMMENT '允许填写8-30位纯数字',
  `bank` int(2) DEFAULT NULL COMMENT '数据字典:中国银行，建设银行，招商银行',
  `is_default` int(2) DEFAULT NULL COMMENT '1:是, 0：否',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  formation8.bank_card 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `bank_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_card` ENABLE KEYS */;

-- 导出  表 formation8.biz_number 结构
CREATE TABLE IF NOT EXISTS `biz_number` (
  `id` bigint(20) NOT NULL,
  `type` varchar(50) NOT NULL,
  `value` bigint(20) NOT NULL DEFAULT '1',
  `memo` varchar(100) DEFAULT NULL,
  `version` bigint(20) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  formation8.biz_number 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `biz_number` DISABLE KEYS */;
INSERT INTO `biz_number` (`id`, `type`, `value`, `memo`, `version`) VALUES
	(1, 'DEPOSIT_ORDER_CODE', 20170428105350, '充值订单编号', 1),
	(2, 'PRODUCT_ORDER_CODE', 20170508000250, '产品订单编号', 1),
	(3, 'TRANSFER_ORDER_CODE', 20170504000050, '转帐订单编号', 1);
/*!40000 ALTER TABLE `biz_number` ENABLE KEYS */;

-- 导出  表 formation8.data_dictionary 结构
CREATE TABLE IF NOT EXISTS `data_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `yn` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='数据字典\r\n例:\r\n系统图片:IMAGE_CODE';

-- 正在导出表  formation8.data_dictionary 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `data_dictionary` DISABLE KEYS */;
INSERT INTO `data_dictionary` (`id`, `code`, `name`, `remark`, `created`, `modified`, `yn`) VALUES
	(1, 'IMAGE_CODE', '图片编码', '图片编码', '2017-05-03 10:45:59', '2017-05-03 10:46:01', 1);
/*!40000 ALTER TABLE `data_dictionary` ENABLE KEYS */;

-- 导出  表 formation8.data_dictionary_value 结构
CREATE TABLE IF NOT EXISTS `data_dictionary_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dd_id` bigint(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `value` varchar(30) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `period_begin` datetime DEFAULT NULL,
  `period_end` datetime DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `yn` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='数据字典值';

-- 正在导出表  formation8.data_dictionary_value 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `data_dictionary_value` DISABLE KEYS */;
INSERT INTO `data_dictionary_value` (`id`, `dd_id`, `sort`, `code`, `value`, `remark`, `period_begin`, `period_end`, `created`, `modified`, `yn`) VALUES
	(1, 1, 1, 'default_head', '/images/default_head.png', '用户默认头像', NULL, NULL, '2017-05-03 11:00:51', '2017-05-03 11:00:52', 1);
/*!40000 ALTER TABLE `data_dictionary_value` ENABLE KEYS */;

-- 导出  表 formation8.deposit 结构
CREATE TABLE IF NOT EXISTS `deposit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `deposite_number` varchar(20) NOT NULL COMMENT '充值编号',
  `status` int(11) NOT NULL COMMENT '1:待付款,2:付款成功;3:付款失败',
  `price` bigint(20) NOT NULL COMMENT '充值金额',
  `deposite_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '充值时间',
  `received_time` datetime DEFAULT NULL COMMENT '到帐时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值必须要记录订单，用于支付宝回调验证订单号';

-- 正在导出表  formation8.deposit 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `deposit` DISABLE KEYS */;
/*!40000 ALTER TABLE `deposit` ENABLE KEYS */;

-- 导出  表 formation8.financial_transaction 结构
CREATE TABLE IF NOT EXISTS `financial_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_number` varchar(20) DEFAULT NULL COMMENT '交易编号,如订单编号',
  `transaction_type` int(2) DEFAULT NULL COMMENT '交易类型(1:充值;2:转帐)',
  `user_id` bigint(20) DEFAULT NULL COMMENT '所属用户ID',
  `transaction_amount` bigint(20) DEFAULT NULL COMMENT '打款金额，单位分',
  `balance` bigint(20) DEFAULT NULL COMMENT '交易当时账户的余额，单位分',
  `payment_time` datetime DEFAULT NULL COMMENT '打款时间',
  `payment_pattern` int(2) DEFAULT NULL COMMENT '支付方式: 1:支付宝;2:微信',
  `target_user_id` bigint(20) DEFAULT NULL COMMENT '目标用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水记录表';

-- 正在导出表  formation8.financial_transaction 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `financial_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `financial_transaction` ENABLE KEYS */;

-- 导出  函数 formation8.getParentReferrer 结构
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `getParentReferrer`(
	`in_id` bigint,
	`in_level` int
) RETURNS bigint(20)
BEGIN
##返回值
DECLARE result bigint;
##临时id变量
DECLARE temp bigint;
##循环变量
DECLARE i bigint;
SET i=in_level;
SELECT referrer INTO result FROM user WHERE id = in_id;
WHILE i > 1 DO
set temp = result;
SELECT referrer INTO result FROM user WHERE id = temp;
set i=i-1;
END WHILE;
RETURN result;
END//
DELIMITER ;

-- 导出  表 formation8.order 结构
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `sku_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品id，冗余数据，便于查询',
  `count` int(11) DEFAULT NULL COMMENT '购买数量',
  `order_number` varchar(20) NOT NULL COMMENT '订单编号',
  `status` int(11) DEFAULT NULL COMMENT '1:众筹中;2:众筹成功;3众筹失败',
  `price` bigint(20) DEFAULT NULL COMMENT '投资金额',
  `transaction_type` int(11) DEFAULT NULL COMMENT '1:钱;2:产品;  众筹成功后可以选产品',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 正在导出表  formation8.order 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- 导出  表 formation8.product 结构
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL COMMENT '名称',
  `image` varchar(40) DEFAULT NULL COMMENT '从数据字典值表的code取，字典编码为IMAGE_CODE',
  `details` varchar(4095) DEFAULT NULL COMMENT '详情,富文本内容',
  `type` varchar(40) DEFAULT NULL COMMENT '文字说明，如:食品，电器，医疗等',
  `publish_time` datetime DEFAULT NULL COMMENT '简单处理，发布时间就是创建时间',
  `cutoff_time` datetime DEFAULT NULL COMMENT '众筹成功的截止时间',
  `success_amount` bigint(20) DEFAULT NULL COMMENT '众筹成功要求的金额',
  `current_amount` bigint(20) DEFAULT NULL COMMENT '已筹金额',
  `drops_time` datetime DEFAULT NULL COMMENT '下架时间，下架时更新',
  `status` int(11) DEFAULT NULL COMMENT '1:上架,2:过期下架,3:手动下架',
  `seller_name` varchar(40) DEFAULT NULL COMMENT '项目发起人',
  `company_desc` varchar(40) DEFAULT NULL COMMENT '企业类型',
  `refund_rate` int(11) DEFAULT '0' COMMENT '赔偿费率百分比:如7天赔偿3%',
  `commission_rate` int(11) DEFAULT NULL COMMENT '平台抽成比例百分比:如3即这3%',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改产品时更新',
  `yn` int(11) DEFAULT NULL COMMENT '0:删除,1:可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='反正是平台发布，卖家信息直接放这里了';

-- 正在导出表  formation8.product 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `name`, `image`, `details`, `type`, `publish_time`, `cutoff_time`, `success_amount`, `current_amount`, `drops_time`, `status`, `seller_name`, `company_desc`, `refund_rate`, `commission_rate`, `modify_time`, `yn`) VALUES
	(1, 'VR眼镜', NULL, 'VR眼镜Detail', '电器', '2017-05-08 09:11:26', '2017-06-08 09:11:32', 500000, 40000, NULL, 1, '博瑞天辰科技', '博瑞天辰科技有限公司', 4, 2, '2017-05-08 09:14:00', 1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- 导出  表 formation8.sku 结构
CREATE TABLE IF NOT EXISTS `sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `details` varchar(4095) DEFAULT NULL COMMENT '详情',
  `price` bigint(20) DEFAULT NULL COMMENT '价格',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改产品时更新',
  `yn` int(11) DEFAULT '1' COMMENT '0:删除,,1:可用',
  `quota` int(11) DEFAULT NULL COMMENT '最大购买份数(限额)',
  `quantity` int(11) DEFAULT '0' COMMENT '当前已购买总份数',
  `daily_quota` int(11) DEFAULT NULL COMMENT '每日限额xxx份',
  `daily_quantity` int(11) DEFAULT '0' COMMENT '每日购买xxx份',
  `current_date_str` varchar(10) DEFAULT NULL COMMENT '当前购买日期串,用于计算每日购买份数,如:2017-05-05',
  `freight` bigint(20) DEFAULT '0' COMMENT '配送费用',
  `image_code` varchar(120) DEFAULT NULL COMMENT '只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='最小销售单位';

-- 正在导出表  formation8.sku 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `sku` DISABLE KEYS */;
INSERT INTO `sku` (`id`, `product_id`, `details`, `price`, `modify_time`, `yn`, `quota`, `quantity`, `daily_quota`, `daily_quantity`, `current_date_str`, `freight`, `image_code`) VALUES
	(1, 1, '感谢您对博瑞的支持， 您可以获得VR手机眼镜一套 （由博瑞天辰发货）。', 200, '2017-05-08 09:20:01', 1, 50, 0, 10, 0, '2017-05-08', 0, NULL),
	(2, 1, '感谢您对博瑞的支持， 您可以获得VR眼镜显示器一套 （由博瑞天辰发货）。', 4000, '2017-05-08 16:01:23', 1, 50, 0, 10, 0, '2017-05-08', 0, NULL),
	(3, 1, '感谢您对博瑞的支持， 您可以获得VR顶配眼镜显示器一套 （由博瑞天辰发货）。', 6000, '2017-05-08 09:24:47', 1, 50, 0, 10, 0, '2017-05-08', 0, NULL);
/*!40000 ALTER TABLE `sku` ENABLE KEYS */;

-- 导出  表 formation8.system_config 结构
CREATE TABLE IF NOT EXISTS `system_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `yn` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='配置系统变量,\r\n系统配置一般是随系统启动时加载一次，修改后需要重启系统。当然部分属性可以按客户需求动态调整。';

-- 正在导出表  formation8.system_config 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;
INSERT INTO `system_config` (`id`, `name`, `code`, `value`, `desc`, `created`, `modified`, `yn`) VALUES
	(1, '提现费率', 'WITHDRAW_DEPOSIT_RATE', '3', '提现费率百分比', '2017-05-03 15:20:46', '2017-05-03 15:20:46', 1),
	(2, '提现金额下限', 'WITHDRAW_DEPOSIT_AMOUNT_FLOOR', '100', '提现金额下限', '2017-05-03 15:24:08', '2017-05-03 15:24:08', 1),
	(3, '引领费率', 'REFERRAL_RATE1', '8', '引领反钱费率百分比', '2017-05-03 15:29:25', '2017-05-03 15:29:25', 1),
	(4, '引荐1费率', 'REFERRAL_RATE2', '4', '引荐1反钱费率百分比', '2017-05-03 15:29:25', '2017-05-03 15:29:25', 1),
	(5, '引荐2费率', 'REFERRAL_RATE3', '5', '引荐2反钱费率百分比', '2017-05-03 15:29:25', '2017-05-03 15:29:25', 1);
/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;

-- 导出  表 formation8.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `payment_code` varchar(20) DEFAULT NULL COMMENT '支付密码',
  `referrer` bigint(20) DEFAULT NULL,
  `balance` bigint(20) DEFAULT '0' COMMENT '可用余额,单位分',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '1' COMMENT '1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人',
  `referral_code` varchar(10) DEFAULT NULL COMMENT '推荐码,邀请码',
  `address` varchar(120) DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  formation8.user 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`, `password`, `payment_code`, `referrer`, `balance`, `email`, `phone`, `type`, `referral_code`, `address`) VALUES
	(1, 'admin', 'fmt81234', 'fmt85678', NULL, 100000000, NULL, NULL, 3, NULL, NULL),
	(2, 'wangmi', '123456', NULL, 1, 1000000, NULL, NULL, 1, 'emUFVf', NULL),
	(3, 'hujun', '123456', NULL, 2, 1000000, NULL, NULL, 1, NULL, NULL),
	(4, 'laozhang', '123456', NULL, 3, 1000000, NULL, NULL, 1, NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 导出  表 formation8.withdrawal 结构
CREATE TABLE IF NOT EXISTS `withdrawal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_number` varchar(20) NOT NULL COMMENT '提现编号',
  `balance` bigint(10) DEFAULT NULL COMMENT '提现时余额,单位(分)',
  `withdrawal_amount` bigint(20) NOT NULL COMMENT '提现申请金额',
  `withdrawal_charge` bigint(20) DEFAULT NULL COMMENT '提现手续费',
  `withdrawal_state` int(10) DEFAULT NULL COMMENT '待处理，已处理',
  `application_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '提现申请时间',
  `finish_time` datetime DEFAULT NULL COMMENT '提现完成时间',
  `card_number` varchar(40) NOT NULL COMMENT '银行卡号',
  `user_id` bigint(20) NOT NULL COMMENT '提现人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  formation8.withdrawal 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `withdrawal` DISABLE KEYS */;
/*!40000 ALTER TABLE `withdrawal` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
