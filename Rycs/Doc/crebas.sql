/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/9/20 1:11:01                            */
/*==============================================================*/


drop table if exists cus_bank_account;

drop table if exists cus_contacter;

drop table if exists cus_fee;

drop table if exists cus_info;

drop table if exists cus_license;

drop table if exists cus_tax;

drop table if exists cus_tax_account;

drop table if exists sys_data_right;

drop table if exists sys_dept;

drop table if exists sys_dictionary;

drop table if exists sys_post;

drop table if exists sys_right;

drop table if exists sys_role;

drop table if exists sys_role_right;

drop table if exists sys_user;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: cus_bank_account                                      */
/*==============================================================*/
create table cus_bank_account
(
   id                   bigint not null auto_increment,
   cus_id               bigint not null,
   name                 varchar(50) not null,
   account              varchar(50) not null,
   type                 varchar(8) not null,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: cus_contacter                                         */
/*==============================================================*/
create table cus_contacter
(
   id                   bigint not null auto_increment,
   cus_id               bigint not null,
   contacter_type       varchar(8),
   contacter_name       varchar(50),
   contacter_tel        varchar(20),
   primary key (id)
);

/*==============================================================*/
/* Table: cus_fee                                               */
/*==============================================================*/
create table cus_fee
(
   id                   bigint not null auto_increment,
   cus_id               bigint not null,
   user_id              bigint not null,
   cash_name            varchar(8) not null,
   cash_date            date not null,
   cash_amount          numeric(8,2) not null,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: cus_info                                              */
/*==============================================================*/
create table cus_info
(
   id                   bigint not null auto_increment,
   user_id              bigint not null,
   name                 varchar(50) not null,
   short_name           varchar(50),
   established_date     date,
   type                 varchar(8),
   registered_capital   double,
   real_capital         double,
   insurcode            varchar(50),
   legal_name           varchar(50),
   legal_phone          varchar(20),
   legal_id_card        varchar(18),
   scope                varchar(100),
   end_date             date,
   registered_address   varchar(100),
   real_address         varchar(100),
   UPD_USR_ID           bigint,
   UPD_DT               datetime,
   CRTD_USR_ID          bigint,
   CRTD_DT              datetime,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: cus_license                                           */
/*==============================================================*/
create table cus_license
(
   id                   bigint not null auto_increment,
   cus_id               bigint not null,
   name                 varchar(50) not null,
   end_date             date not null,
   primary key (id)
);

/*==============================================================*/
/* Table: cus_tax                                               */
/*==============================================================*/
create table cus_tax
(
   id                   bigint not null auto_increment,
   cus_id               bigint not null,
   name                 varchar(8),
   register_no          varchar(50),
   computer_no          varchar(50),
   pay_type             varchar(8),
   manager_name         varchar(50),
   dept_name            varchar(50),
   telphone             varchar(20),
   primary key (id)
);

/*==============================================================*/
/* Table: cus_tax_account                                       */
/*==============================================================*/
create table cus_tax_account
(
   id                   bigint not null auto_increment,
   cus_id               bigint not null,
   name                 varchar(8) not null,
   type                 varchar(8) not null,
   amount               real not null,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: sys_data_right                                        */
/*==============================================================*/
create table sys_data_right
(
   id                   bigint not null auto_increment,
   sys_id               bigint not null,
   sys_user_id          bigint not null,
   primary key (id)
);

/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   id                   bigint not null auto_increment,
   name                 varchar(50) not null,
   parent_id            bigint not null,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: sys_dictionary                                        */
/*==============================================================*/
create table sys_dictionary
(
   id                   bigint not null auto_increment,
   data_type            varchar(4) not null,
   data_code            varchar(8) not null,
   data_name            varchar(50),
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: sys_post                                              */
/*==============================================================*/
create table sys_post
(
   id                   bigint not null auto_increment,
   name                 varchar(50) not null,
   parent_id            bigint not null,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: sys_right                                             */
/*==============================================================*/
create table sys_right
(
   id                   bigint not null auto_increment,
   name                 varchar(50) not null,
   right_url            varchar(200) not null,
   parent_id            bigint not null,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   bigint not null auto_increment,
   name                 varchar(50) not null,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: sys_role_right                                        */
/*==============================================================*/
create table sys_role_right
(
   id                   bigint not null auto_increment,
   role_id              bigint not null,
   right_id             bigint not null,
   primary key (id)
);

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   bigint not null auto_increment,
   post_id              bigint not null,
   dept_id              bigint not null,
   name                 varchar(50) not null,
   password             varchar(20) not null,
   china_name           varchar(50),
   age                  smallint,
   sex                  varchar(8),
   phone                varchar(20),
   address              varchar(100),
   last_login_time      datetime,
   UPD_USR_ID           bigint,
   UPD_DT               datetime,
   CRTD_USR_ID          bigint,
   CRTD_DT              datetime,
   remark               varchar(250),
   primary key (id)
);

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   bigint not null auto_increment,
   role_id              bigint not null,
   user_id              bigint not null,
   primary key (id)
);

alter table cus_bank_account add constraint FK_Relationship_11 foreign key (cus_id)
      references cus_info (id) on delete restrict on update restrict;

alter table cus_contacter add constraint FK_Relationship_15 foreign key (cus_id)
      references cus_info (id) on delete restrict on update restrict;

alter table cus_fee add constraint FK_Relationship_14 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

alter table cus_fee add constraint FK_Relationship_16 foreign key (cus_id)
      references cus_info (id) on delete restrict on update restrict;

alter table cus_info add constraint FK_Relationship_9 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

alter table cus_license add constraint FK_Relationship_13 foreign key (cus_id)
      references cus_info (id) on delete restrict on update restrict;

alter table cus_tax add constraint FK_Relationship_10 foreign key (cus_id)
      references cus_info (id) on delete restrict on update restrict;

alter table cus_tax_account add constraint FK_Relationship_12 foreign key (cus_id)
      references cus_info (id) on delete restrict on update restrict;

alter table sys_data_right add constraint FK_Relationship_5 foreign key (sys_id)
      references sys_user (id) on delete restrict on update restrict;

alter table sys_role_right add constraint FK_Relationship_6 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_role_right add constraint FK_Relationship_7 foreign key (right_id)
      references sys_right (id) on delete restrict on update restrict;

alter table sys_user add constraint FK_Relationship_1 foreign key (dept_id)
      references sys_dept (id) on delete restrict on update restrict;

alter table sys_user add constraint FK_Relationship_2 foreign key (post_id)
      references sys_post (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_Relationship_3 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_Relationship_4 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

