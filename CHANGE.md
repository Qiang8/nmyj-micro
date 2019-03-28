# 20190116 by dangxb
## 一、组织机构过滤问题
为了实现通过组织机构对本级、下属机构所属资源的查询管理，需要开启使用SYS_ORG表中ORG_CODE字段的应用。
ORG_CODE字段 3位表示一个层级，比如：一级目录code为101，直接下级节点依次为101001,101002,101003...

### 需要调整内容

1. 数据库`sys_user_profile`表添加`org_code varchar(30)`
2. sys微服务调整 UserProfile、UserProfileVo,添加orgCode

## 二、角色主页初始化
为了实现根据用户定制化主页，需要根据角色定制组件，用户登录后，根据角色设置选择主页进行显示，如果角色
未设置主页，使用默认主页。

### 需调整内容
1. 数据库`sys_role`表添加`home_page varchar(100)`字段
2. 


## 三、消息中心


## 四、通讯录


















## 1 、数据库表更新
1.  `MSG_NOTICE`表添加`EXTRA`附加信息字段，字段类型`varchar(4000)`，用于存储额外字段。
2.  `MSG_NOTICE_RECORD`表添加`ORG_ID,ORG_NAME`字段，用于存储接收人部门编码及部门名称。

## 接口更新
