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
  `bank` varchar(50) DEFAULT NULL COMMENT '数据字典:中国银行，建设银行，招商银行',
  `is_default` int(2) DEFAULT NULL COMMENT '1:是, 0：否',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 formation8.biz_number 结构
CREATE TABLE IF NOT EXISTS `biz_number` (
  `id` bigint(20) NOT NULL,
  `type` varchar(50) NOT NULL,
  `value` bigint(20) NOT NULL DEFAULT '1',
  `memo` varchar(100) DEFAULT NULL,
  `version` bigint(20) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
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

-- 数据导出被取消选择。
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

-- 数据导出被取消选择。
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

-- 数据导出被取消选择。
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='交易流水记录表';

-- 数据导出被取消选择。
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
  `withdraw_time` datetime DEFAULT NULL COMMENT '用户提款时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 formation8.product 结构
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL COMMENT '名称',
  `image` varchar(40) DEFAULT NULL COMMENT '从数据字典值表的code取，字典编码为IMAGE_CODE',
  `details` varchar(4095) DEFAULT NULL COMMENT '详情,富文本内容',
  `type` varchar(40) DEFAULT NULL COMMENT '文字说明，如:食品，电器，医疗等',
  `publish_time` datetime DEFAULT NULL COMMENT '简单处理，发布时间就是创建时间',
  `cutoff_time` datetime DEFAULT NULL COMMENT '众筹成功的截止时间',
  `period` int(8) DEFAULT NULL COMMENT '产品周期(天)',
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

-- 数据导出被取消选择。
-- 导出  表 formation8.schedule_job 结构
CREATE TABLE IF NOT EXISTS `schedule_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `job_name` varchar(40) DEFAULT NULL,
  `job_group` varchar(40) DEFAULT NULL,
  `job_status` int(11) DEFAULT NULL COMMENT '是否启动任务,1:运行中,0:停止',
  `job_data` varchar(1000) DEFAULT NULL COMMENT 'json',
  `cron_expression` varchar(40) DEFAULT NULL,
  `repeat_interval` int(11) DEFAULT NULL COMMENT '简单调度，默认以秒为单位',
  `start_delay` int(11) DEFAULT NULL COMMENT '启动调度器后，多少秒开始执行调度',
  `description` varchar(200) DEFAULT NULL COMMENT '调度器描述',
  `bean_class` varchar(100) DEFAULT NULL COMMENT '任务执行时调用类的全名，用于反射',
  `spring_id` varchar(40) DEFAULT NULL COMMENT 'spring的beanId，直接从spring中获取',
  `url` varchar(100) DEFAULT NULL COMMENT '支持远程调用restful url',
  `is_concurrent` int(11) DEFAULT NULL COMMENT '1：并发; 0:同步',
  `method_name` varchar(40) DEFAULT NULL COMMENT 'bean_class和spring_id需要配置方法名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
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

-- 数据导出被取消选择。
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

-- 数据导出被取消选择。
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
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

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
