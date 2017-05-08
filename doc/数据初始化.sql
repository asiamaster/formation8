##业务编号
insert into biz_number values('1', 'DEPOSIT_ORDER_CODE', '201704270050', '充值订单编号', '1');
insert into biz_number values('2', 'PRODUCT_ORDER_CODE', '201704270050', '产品订单编号', '1');
insert into biz_number values('3', 'TRANSFER_ORDER_CODE', '201704270050', '转帐订单编号', '1');

##系统配置
insert into system_config (name, code,value, `desc`, created, modified, yn) values
('提现费率','WITHDRAW_DEPOSIT_RATE','3','提现费率百分比', now(), now(),1);
insert into system_config (name, code,value, `desc`, created, modified, yn) values
('提现金额下限','WITHDRAW_DEPOSIT_AMOUNT_FLOOR','100','提现金额下限', now(), now(),1);
insert into system_config (name, code,value, `desc`, created, modified, yn) values
('引领费率','REFERRAL_RATE1','8','引领反钱费率百分比', now(), now(),1);
insert into system_config (name, code,value, `desc`, created, modified, yn) values
('引荐1费率','REFERRAL_RATE2','4','引荐1反钱费率百分比', now(), now(),1);
insert into system_config (name, code,value, `desc`, created, modified, yn) values
('引荐2费率','REFERRAL_RATE3','5','引荐2反钱费率百分比', now(), now(),1);

##数据字典
INSERT INTO `data_dictionary` (`id`, `code`, `name`, `remark`, `created`, `modified`, `yn`) VALUES
	(1, 'IMAGE_CODE', '图片编码', '图片编码', now(), now(), 1);
INSERT INTO `data_dictionary_value` (`id`, `dd_id`, `sort`, `code`, `value`, `remark`, `period_begin`, `period_end`, `created`, `modified`, `yn`) VALUES
	(1, 1, 1, 'default_head', '/images/default_head.png', '用户默认头像', NULL, NULL, now(), now(), 1);

##用户
insert into user
(id, name, password, payment_code, referrer, balance, email, phone, type, referral_code,address)
values(1, 'admin', 'fmt81234','fmt85678',null, 0, '','',3,'','')

##产品
INSERT INTO `product` (`id`, `name`, `image`, `details`, `type`, `publish_time`, `cutoff_time`, `success_amount`, `current_amount`, `drops_time`, `status`, `seller_name`, `company_desc`, `refund_rate`, `commission_rate`, `modify_time`, `yn`) VALUES (1, 'VR眼镜', NULL, 'VR眼镜Detail', '电器', '2017-05-08 09:11:26', '2017-06-08 09:11:32', 500000, 0, NULL, 1, '博瑞天辰科技', '博瑞天辰科技有限公司', 4, 2, '2017-05-08 09:14:00', 1);

##SKU
INSERT INTO `sku` (`id`, `product_id`, `details`, `price`, `modify_time`, `yn`, `quota`, `quantity`, `daily_quota`, `daily_quantity`, `current_date`, `freight`, `image_code`) VALUES (1, 1, '感谢您对博瑞的支持， 您可以获得VR手机眼镜一套 （由博瑞天辰发货）。', 200, '2017-05-08 09:20:01', 1, 50, 0, 10, 0, '2017-05-08', 0, NULL);
INSERT INTO `sku` (`id`, `product_id`, `details`, `price`, `modify_time`, `yn`, `quota`, `quantity`, `daily_quota`, `daily_quantity`, `current_date`, `freight`, `image_code`) VALUES (2, 1, '感谢您对博瑞的支持， 您可以获得VR眼镜显示器一套 （由博瑞天辰发货）。', 4000, '2017-05-08 09:23:05', 1, 50, 0, 10, 0, '2017-05-08', 0, NULL);
INSERT INTO `sku` (`id`, `product_id`, `details`, `price`, `modify_time`, `yn`, `quota`, `quantity`, `daily_quota`, `daily_quantity`, `current_date`, `freight`, `image_code`) VALUES (3, 1, '感谢您对博瑞的支持， 您可以获得VR顶配眼镜显示器一套 （由博瑞天辰发货）。', 6000, '2017-05-08 09:24:47', 1, 50, 0, 10, 0, '2017-05-08', 0, NULL);
