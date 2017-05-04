/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/5/3 星期三 9:41:39                         */
/*==============================================================*/


drop table if exists bank_card;

drop table if exists data_dictionary;

drop table if exists data_dictionary_value;

drop table if exists deposit;

drop table if exists financial_transaction;

drop table if exists `order`;

drop table if exists product;

drop table if exists sku;

drop table if exists system_config;

drop table if exists user;

drop table if exists withdrawal;

/*==============================================================*/
/* Table: bank_card                                             */
/*==============================================================*/
create table bank_card
(
   id                   bigint not null auto_increment,
   user_id              bigint(20) comment '用户ID',
   card_type            integer(2) comment '默认选中储蓄卡，目前仅“储蓄卡”一个项',
   account_name         varchar(20) comment '法人银行账户：显示认证时填写的法人姓名，企业',
   id_number            varchar(40) comment '法人银行账户：显示认证时填写的法人身份证号，',
   subbranch            varchar(40) comment '选填，允许5-50位汉字、字母和数字，不允许特殊',
   card_number          varchar(30) comment '允许填写8-30位纯数字',
   bank                 integer(2) comment '数据字典:中国银行，建设银行，招商银行',
   is_default           integer(2) comment '1:是, 0：否',
   add_time             datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: data_dictionary                                       */
/*==============================================================*/
create table data_dictionary
(
   id                   bigint not null auto_increment,
   code                 varchar(50),
   name                 varchar(50),
   remark               varchar(1000),
   created              datetime,
   modified             datetime,
   yn                   int,
   primary key (id)
);

alter table data_dictionary comment '数据字典
例:
系统图片:IMAGE_CODE';

/*==============================================================*/
/* Table: data_dictionary_value                                 */
/*==============================================================*/
create table data_dictionary_value
(
   id                   bigint not null auto_increment,
   dd_id                bigint,
   sort                 int,
   code                 varchar(255),
   value                varchar(30),
   remark               varchar(1000),
   period_begin         datetime,
   period_end           datetime,
   created              datetime,
   modified             datetime,
   yn                   int,
   primary key (id)
);

alter table data_dictionary_value comment '数据字典值';

/*==============================================================*/
/* Table: deposit                                               */
/*==============================================================*/
create table deposit
(
   id                   bigint not null auto_increment,
   user_id              bigint not null,
   deposite_number      varchar(20) not null comment '充值编号',
   status               int not null comment '1:待付款,2:付款成功;3:付款失败',
   price                bigint not null comment '充值金额',
   deposite_time        datetime comment '充值时间',
   received_time        datetime comment '到帐时间',
   primary key (id)
);

alter table deposit comment '充值必须要记录订单，用于支付宝回调验证订单号';

/*==============================================================*/
/* Table: financial_transaction                                 */
/*==============================================================*/
create table financial_transaction
(
   id                   bigint not null auto_increment,
   transaction_number   varchar(20) comment '交易编号,如订单编号',
   transaction_type     integer(2) comment '交易类型(1:充值;2:转帐)',
   user_id              bigint comment '所属用户ID',
   transaction_amount   bigint comment '打款金额，单位分',
   balance              bigint comment '交易当时账户的余额，单位分',
   payment_time         datetime comment '打款时间',
   payment_pattern      integer(2) comment '支付方式: 1:支付宝;2:微信',
   target_user_id       bigint comment '目标用户id',
   primary key (id)
);

alter table financial_transaction comment '交易流水记录表';

/*==============================================================*/
/* Table: `order`                                               */
/*==============================================================*/
create table `order`
(
   id                   bigint not null auto_increment,
   user_id              bigint not null,
   sku_id               bigint,
   product_id           bigint comment '产品id，冗余数据，便于查询',
   order_number         varchar(20) not null comment '订单编号',
   status               int comment '1:众筹中;2:众筹成功;3众筹失败',
   price                bigint comment '投资金额',
   transaction_type     int comment '1:钱;2:产品;  众筹成功后可以选产品',
   start_time           datetime comment '下单时间',
   end_time             datetime comment '结束时间',
   primary key (id)
);

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product
(
   id                   bigint not null auto_increment,
   name                 varchar(40) comment '名称',
   image                varchar(40) comment '从数据字典值表的code取，字典编码为IMAGE_CODE',
   details              varchar(4095) comment '详情,富文本内容',
   type                 varchar(40) comment '文字说明，如:食品，电器，医疗等',
   publish_time         datetime comment '简单处理，发布时间就是创建时间',
   cutoff_time          datetime comment '众筹成功的截止时间',
   amount               bigint comment '众筹成功要求的金额',
   drops_time           datetime comment '下架时间，下架时更新',
   status               int comment '1:上架,2:过期下架,3:手动下架',
   seller_name          varchar(40) comment '项目发起人',
   company_desc         varchar(40) comment '企业类型',
   refund_rate          int default 0 comment '赔偿费率百分比:如120即这120%',
   commission_rate      int comment '平台抽成比例百分比:如3即这3%',
   modify_time          datetime comment '修改产品时更新',
   yn                   int comment '0:删除,,1:可用',
   primary key (id)
);

alter table product comment '反正是平台发布，卖家信息直接放这里了';

/*==============================================================*/
/* Table: sku                                                   */
/*==============================================================*/
create table sku
(
   id                   bigint not null auto_increment,
   product_id           bigint comment '商品id',
   details              varchar(4095) comment '详情',
   price                datetime comment '价格',
   modify_time          datetime comment '修改产品时更新',
   yn                   int default 1 comment '0:删除,,1:可用',
   quota                int comment '最大购买份数(限额)',
   freight              bigint comment '配送费用',
   image_code           varchar(120) comment '只有一张图,从数据字典值表的code取，字典编码为IMAGE_CODE',
   quantity             int comment '当前已购买份数',
   primary key (id)
);

alter table sku comment '最小销售单位';

/*==============================================================*/
/* Table: system_config                                         */
/*==============================================================*/
create table system_config
(
   id                   bigint not null auto_increment,
   name                 varchar(255),
   code                 varchar(255),
   value                varchar(255),
   `desc`               varchar(255),
   created              datetime,
   modified             datetime,
   yn                   int,
   primary key (id)
);

alter table system_config comment '配置系统变量,
系统配置一般是随系统启动时加载一次，修改后需要重启系统。当然部分属性可以按客户需求动态调整。';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null auto_increment,
   name                 varchar(20) comment '用户名',
   password             varchar(20) comment '密码',
   payment_code         varchar(20) comment '支付密码',
   referrer             bigint,
   balance              bigint default 0 comment '可用余额,单位分',
   email                varchar(40) comment '邮箱',
   phone                varchar(40),
   type                 int comment '1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人',
   referral_code        varchar(10) comment '推荐码,邀请码',
   primary key (id)
);

/*==============================================================*/
/* Table: withdrawal                                            */
/*==============================================================*/
create table withdrawal
(
   id                   bigint not null auto_increment,
   transaction_number   varchar(20) comment '提现编号',
   balance              bigint(10) comment '提现时余额,单位(分)',
   withdrawal_amount    bigint(20) comment '提现申请金额',
   withdrawal_charge    integer(10) comment '提现手续费',
   withdrawal_state     integer(10) comment '待处理，已处理',
   application_time     datetime comment '提现申请时间',
   finish_time          datetime comment '提现完成时间',
   bank_card_id         bigint(20) comment '银行卡id',
   user_id              bigint comment '提现人id',
   primary key (id)
);

