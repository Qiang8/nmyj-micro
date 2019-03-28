drop table if exists sys_business_log;

drop table if exists sys_dic_group;

drop table if exists sys_dic_group_items;

drop table if exists sys_menu;

drop table if exists sys_org;

drop table if exists sys_role;

drop table if exists sys_role_menus;

drop table if exists sys_role_type_menu;

drop table if exists sys_user;

drop table if exists sys_user_profile;

drop table if exists sys_user_roles;

drop table if exists sys_business_log;

/*==============================================================*/
/* Table: sys_business_log                                      */
/*==============================================================*/
create table sys_business_log
(
   id                   varchar(36) not null comment 'ID',
   type                 varchar(2) comment '日志类型',
   create_time          datetime comment '记录时间',
   content              varchar(4000) comment '日志内容',
   operate_by           varchar(36) comment '操作人',
   operator             varchar(50) comment '操作人名称',
   primary key (id)
);


/*==============================================================*/
/* Table: sys_dic_group                                         */
/*==============================================================*/
create table sys_dic_group
(
   id                   varchar(36) not null comment 'ID',
   dic_name             varchar(50) comment '类别名称',
   dic_code             varchar(50) comment '类别编码',
   type                 varchar(36) comment '0:列表，1:树型',
   orders               int(11) comment '排序',
   status               char(1) comment '0:启用,1:禁用',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   create_by            varchar(36) comment '创建人',
   update_by            varchar(36) comment '更新人',
   primary key (id)
);


/*==============================================================*/
/* Table: sys_dic_group_items                                   */
/*==============================================================*/
create table sys_dic_group_items
(
   id                   varchar(36) not null comment 'ID',
   dic_code             varchar(50) comment '类别编码',
   item_name            varchar(100) comment '字典名称',
   parent_id            varchar(36) comment '父节点',
   type                 char(1) comment '冗余字段，标明列表或者树型结构',
   orders               int(11) comment '排序',
   item_code            varchar(100) comment '字典编码',
   del_flag             char(1) comment '删除标识',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   create_by            varchar(36) comment '创建人',
   update_by            varchar(36) comment '更新人',
   primary key (id)
);

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   id                   varchar(36) not null comment 'ID',
   menu_name            varchar(50) comment '菜单名称',
   permission           varchar(50) comment '权限标识',
   parent_id            varchar(36) comment '父节点编码',
   path                 varchar(128) comment '路径',
   redirect             varchar(128) comment '重定向',
   icon                 varchar(50) comment '图标',
   component            varchar(100) comment '组件',
   orders               int(11) comment '排序',
   type                 char(1) comment '0:菜单,1:按钮',
   del_flag             char(1) comment '删除标识',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   create_by            varchar(50) comment '创建人',
   update_by            varchar(50) comment '更新人',
   primary key (id),
   unique key ak_k_u_sys_authority (permission, menu_name)
);


/*==============================================================*/
/* Table: sys_org                                               */
/*==============================================================*/
create table sys_org
(
   id                   varchar(36) not null comment 'ID',
   org_name             varchar(100) comment '机构名称',
   parent_id            varchar(36) comment '父节点',
   org_code             varchar(30) comment '机构编码',
   address              varchar(100) comment '机构地址',
   orders               int(11) comment '排序',
   description          varchar(200) comment '机构描述',
   short_name           varchar(50) comment '机构简称',
   del_flag             char(1) comment '删除标识',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   create_by            varchar(36) comment '创建人',
   update_by            varchar(36) comment '更新人',
   primary key (id)
);


/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   varchar(36) not null comment 'ID',
   role_name            varchar(50) comment '角色名称',
   role_code            varchar(50) comment '角色编码',
   role_type            varchar(36) comment '角色类型',
   description          varchar(500) comment '角色描述',
   orders               int(11) comment '角色排序',
   del_flag             char(1) comment '0:正常,1:删除',
   status               char(1) comment '禁用标识',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   create_by            varchar(36) comment '创建人',
   update_by            varchar(36) comment '更新人',
   primary key (id)
);



/*==============================================================*/
/* Table: sys_role_menus                                        */
/*==============================================================*/
create table sys_role_menus
(
   role_id              varchar(36) not null comment '角色ID',
   menu_id              varchar(36) not null comment '菜单ID',
   primary key (role_id, menu_id)
);


/*==============================================================*/
/* Table: sys_role_type_menu                                    */
/*==============================================================*/
create table sys_role_type_menu
(
   id                   varchar(36) not null comment 'ID',
   menu_id              varchar(36) not null comment '菜单ID',
   role_type            varchar(36) not null comment '角色类型',
   primary key (id)
);


/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   varchar(36) not null comment 'ID',
   account              varchar(50) comment '用户',
   password             varchar(50) comment '密码',
   profile_id           varchar(36) comment '详情编码',
   fault_num            int(11) comment '登录失败次数',
   del_flag             char(1) comment '删除标识',
   status               char(1) comment '禁用标识',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   create_by            varchar(36) comment '创建人',
   update_by            varchar(36) comment '更新人',
   primary key (id),
   unique key ak_k_u_sys_user_username (account)
);


/*==============================================================*/
/* Table: sys_user_profile                                      */
/*==============================================================*/
create table sys_user_profile
(
   id                   varchar(36) not null comment 'ID',
   name                 varchar(50) comment '姓名',
   avatar               varchar(100) comment '头像',
   email                varchar(50) comment '邮箱',
   mobile               varchar(50) comment '手机',
   sex                  varchar(36) comment '性别',
   position             varchar(36) comment '单位职务',
   role_name            varchar(100) comment '冗余用户角色名称，多个以分号隔开',
   org_id               varchar(36) comment '所属机构',
   org_name             varchar(100) comment '机构名称',
   primary key (id)
);


/*==============================================================*/
/* Table: sys_user_roles                                        */
/*==============================================================*/
create table sys_user_roles
(
   user_id              varchar(36) not null comment '用户ID',
   role_id              varchar(36) not null comment '角色ID',
   primary key (user_id, role_id)
);

insert into sys_user (id, account, password,profile_id,fault_num,del_flag, status,create_time, update_time,create_by,update_by) values ('1', 'admin', 'admin',null,0,'1', '1',to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'),'sys','sys');

insert into sys_role (id,role_name,role_code,role_type,description,orders,del_flag,status,create_time,update_time,create_by,update_by)values('1','超级管理员','ROLE_SUPER_ADMIN','1','超级管理员',1,'1','1',to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'),'sys','sys');
insert into sys_role (id,role_name,role_code,role_type,description,orders,del_flag,status,create_time,update_time,create_by,update_by)values('2','管理员','ROLE_ADMIN','2','管理员',2,'1','1',to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'),'sys','sys');
insert into sys_role (id,role_name,role_code,role_type,description,orders,del_flag,status,create_time,update_time,create_by,update_by)values('3','用户','ROLE_USER','3','管理员',3,'1','1',to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-26-2018 14:46:41', 'dd-mm-yyyy hh24:mi:ss'),'sys','sys');

insert into sys_user_roles (user_id, role_id) values ('1', '1')