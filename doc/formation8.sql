/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/27 ������ 9:30:26                        */
/*==============================================================*/


drop table if exists bank_card;

drop table if exists data_dictionary;

drop table if exists data_dictionary_value;

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
   id                   bigint not null,
   user_id              bigint(20) comment '�û�ID',
   card_type            integer(2) comment 'Ĭ��ѡ�д����Ŀǰ���������һ����',
   account_name         varchar(20) comment '���������˻�����ʾ��֤ʱ��д�ķ�����������ҵ',
   id_number            varchar(40) comment '���������˻�����ʾ��֤ʱ��д�ķ������֤�ţ�',
   subbranch            varchar(40) comment 'ѡ�����5-50λ���֡���ĸ�����֣�����������',
   card_number          varchar(30) comment '������д8-30λ������',
   bank                 integer(2) comment '�����ֵ�:�й����У��������У���������',
   is_default           integer(2) comment '1:��, 0����',
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

alter table data_dictionary comment '�����ֵ�
��:
ϵͳͼƬ:IMAGE_CODE';

/*==============================================================*/
/* Table: data_dictionary_value                                 */
/*==============================================================*/
create table data_dictionary_value
(
   id                   bigint not null,
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

alter table data_dictionary_value comment '�����ֵ�ֵ';

/*==============================================================*/
/* Table: financial_transaction                                 */
/*==============================================================*/
create table financial_transaction
(
   id                   bigint not null auto_increment,
   transaction_number   varchar(20) comment '���ױ��,�綩�����',
   transaction_type     integer(2) comment '��������(1:��ֵ;2:ת��)',
   user_id              bigint comment '�����û�ID',
   transaction_amount   bigint comment '������λ��',
   balance              bigint comment '���׵�ʱ�˻�������λ��',
   payment_time         datetime comment '���ʱ��',
   payment_pattern      integer(2) comment '֧����ʽ: 1:֧����;2:΢��',
   target_user_id       bigint comment 'Ŀ���û�id',
   primary key (id)
);

alter table financial_transaction comment '������ˮ��¼��';

/*==============================================================*/
/* Table: `order`                                               */
/*==============================================================*/
create table `order`
(
   id                   bigint not null,
   user_id              bigint,
   sku_id               bigint,
   product_id           bigint comment '��Ʒid���������ݣ����ڲ�ѯ',
   `varchar(20)`        bigint comment '�������',
   status               int comment '1:�ڳ���;2:�ڳ�ɹ�;3�ڳ�ʧ��',
   price                bigint comment 'Ͷ�ʽ��',
   transaction_type     int comment '1:Ǯ;2:��Ʒ;  �ڳ�ɹ������ѡ��Ʒ',
   primary key (id)
);

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product
(
   id                   bigint not null,
   name                 varchar(40) comment '����',
   image                varchar(40) comment '�������ֵ�ֵ���codeȡ���ֵ����ΪIMAGE_CODE',
   details              varchar(4095) comment '����,���ı�����',
   type                 varchar(40) comment '����˵������:ʳƷ��������ҽ�Ƶ�',
   publish_time         datetime comment '�򵥴�������ʱ����Ǵ���ʱ��',
   cutoff_time          datetime comment '�ڳ�ɹ��Ľ�ֹʱ��',
   amount               bigint comment '�ڳ�ɹ�Ҫ��Ľ��',
   drops_time           datetime comment '�¼�ʱ�䣬�¼�ʱ����',
   status               int comment '1:�ϼ�,2:�����¼�,3:�ֶ��¼�',
   seller_name          varchar(40) comment '��Ŀ������',
   company_desc         varchar(40) comment '��ҵ����',
   refund_rate          int default 0 comment '�⳥���ʰٷֱ�:��120����120%',
   commission_rate      int comment 'ƽ̨��ɱ����ٷֱ�:��3����3%',
   modify_time          datetime comment '�޸Ĳ�Ʒʱ����',
   yn                   int comment '0:ɾ��,,1:����'
);

alter table product comment '������ƽ̨������������Ϣֱ�ӷ�������';

/*==============================================================*/
/* Table: sku                                                   */
/*==============================================================*/
create table sku
(
   id                   bigint not null,
   product_id           bigint comment '��Ʒid',
   details              varchar(4095) comment '����',
   price                datetime comment '�۸�',
   modify_time          datetime comment '�޸Ĳ�Ʒʱ����',
   yn                   int default 1 comment '0:ɾ��,,1:����',
   quota                int comment '��������(�޶�)',
   freight              bigint comment '���ͷ���',
   image_code           varchar(120) comment 'ֻ��һ��ͼ,�������ֵ�ֵ���codeȡ���ֵ����ΪIMAGE_CODE',
   quantity             int comment '��ǰ�ѹ������'
);

alter table sku comment '��С���۵�λ';

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

alter table system_config comment '����ϵͳ����,
ϵͳ����һ������ϵͳ����ʱ����һ�Σ��޸ĺ���Ҫ����ϵͳ����Ȼ�������Կ��԰��ͻ�����̬������';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null,
   name                 varchar(20),
   password             varchar(20),
   referrer             bigint,
   �Ƿ���Ͷ                 int,
   �Ƿ�ƽ̨�û�               int,
   balance              bigint default 0 comment '�������,��λ��',
   email                varchar(40) comment '����',
   phone                varchar(40),
   type                 int comment '1:��ͨ�û�,2:ƽ̨��ǰ�ֽ�,3:ƽ̨���,4:�ɶ�,5:������',
   referral_code		varchar(10) comment '�Ƽ���,������',
   primary key (id)
);

/*==============================================================*/
/* Table: withdrawal                                            */
/*==============================================================*/
create table withdrawal
(
   id                   bigint not null auto_increment,
   transaction_number   varchar(20) comment '���ֱ��',
   balance              bigint(10) comment '����ʱ���,��λ(��)',
   withdrawal_amount    bigint(20) comment '����������',
   withdrawal_charge    integer(10) comment '����������',
   withdrawal_state     integer(10) comment '�������Ѵ���',
   application_time     datetime comment '��������ʱ��',
   finish_time          datetime comment '�������ʱ��',
   bank_card_id         bigint(20) comment '���п�id',
   user_id              bigint comment '������id',
   primary key (id)
);

