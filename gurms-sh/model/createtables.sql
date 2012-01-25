/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2012/1/25 17:44:33                           */
/*==============================================================*/


drop table SYS_DICT_INDEX cascade constraints;

drop table SYS_DICT_VALUE cascade constraints;

drop table SYS_LOG_LOGIN cascade constraints;

drop table SYS_LOG_OPERATE cascade constraints;

drop table SYS_MENU cascade constraints;

drop table SYS_ORG cascade constraints;

drop table SYS_PARAM cascade constraints;

drop table SYS_PARAM_EXT cascade constraints;

drop table SYS_ROLE cascade constraints;

drop table SYS_ROLE_MENU cascade constraints;

drop table SYS_SERIALNO cascade constraints;

drop table SYS_USER cascade constraints;

drop table SYS_USER_CONFIG cascade constraints;

drop table SYS_USER_INFO cascade constraints;

drop table SYS_USER_ROLE cascade constraints;

drop sequence SYS_SEQ;

create sequence SYS_SEQ
increment by 1
start with 1
 maxvalue 999999999999
 minvalue 1;

/*==============================================================*/
/* Table: SYS_DICT_INDEX                                        */
/*==============================================================*/
create table SYS_DICT_INDEX  (
   dictcode             INT                             not null,
   dictname             VARCHAR2(32)                    not null,
   dicttype             CHAR(1)                         not null,
   editflag             CHAR(1)                         not null,
   constraint PK_SYS_DICT_INDEX primary key (dictcode)
);

comment on table SYS_DICT_INDEX is
'字典索引';

comment on column SYS_DICT_INDEX.dictcode is
'字典代码';

comment on column SYS_DICT_INDEX.dictname is
'字典名称';

comment on column SYS_DICT_INDEX.dicttype is
'所属字典分组';

comment on column SYS_DICT_INDEX.editflag is
'编辑标志';

/*==============================================================*/
/* Table: SYS_DICT_VALUE                                        */
/*==============================================================*/
create table SYS_DICT_VALUE  (
   dictcode             INT                             not null,
   dictitem             VARCHAR2(8)                     not null,
   itemname             VARCHAR2(32)                    not null,
   dictorder            INT                            default 0 not null,
   constraint PK_SYS_DICT_VALUE primary key (dictitem, dictcode)
);

comment on table SYS_DICT_VALUE is
'数据字典表';

comment on column SYS_DICT_VALUE.dictcode is
'字典代码';

comment on column SYS_DICT_VALUE.dictitem is
'字典项';

comment on column SYS_DICT_VALUE.itemname is
'字典项名称';

comment on column SYS_DICT_VALUE.dictorder is
'显示顺序';

/*==============================================================*/
/* Table: SYS_LOG_LOGIN                                         */
/*==============================================================*/
create table SYS_LOG_LOGIN  (
   LOGID                CHAR(32)                        not null,
   USERID               VARCHAR2(16)                    not null,
   LOGINDATE            CHAR(8),
   LOGINTIME            CHAR(6),
   LOGINPASSWORD        VARCHAR2(32),
   LOGINIP              VARCHAR2(16),
   SUCCESS              CHAR(1),
   constraint PK_SYS_LOG_LOGIN primary key (LOGID)
);

comment on table SYS_LOG_LOGIN is
'系统登录日志表';

comment on column SYS_LOG_LOGIN.LOGID is
'id';

comment on column SYS_LOG_LOGIN.USERID is
'用户ID';

comment on column SYS_LOG_LOGIN.LOGINDATE is
'登录日期';

comment on column SYS_LOG_LOGIN.LOGINTIME is
'登录时间';

comment on column SYS_LOG_LOGIN.LOGINPASSWORD is
'登录密码（密文）';

comment on column SYS_LOG_LOGIN.LOGINIP is
'登录IP';

comment on column SYS_LOG_LOGIN.SUCCESS is
'是否登录成功。1，是；0否';

/*==============================================================*/
/* Table: SYS_LOG_OPERATE                                       */
/*==============================================================*/
create table SYS_LOG_OPERATE  (
   logid                CHAR(32)                        not null,
   userid               VARCHAR2(16)                    not null,
   operatedate          CHAR(8)                         not null,
   operatetime          CHAR(6)                         not null,
   operatetype          VARCHAR2(8)                     not null,
   operatetable         VARCHAR2(16)                    not null,
   recordid             VARCHAR2(64)                    not null,
   constraint PK_SYS_LOG_OPERATE primary key (logid)
);

comment on table SYS_LOG_OPERATE is
'系统日志表';

comment on column SYS_LOG_OPERATE.logid is
'日志ID';

comment on column SYS_LOG_OPERATE.userid is
'操作人';

comment on column SYS_LOG_OPERATE.operatedate is
'操作日期';

comment on column SYS_LOG_OPERATE.operatetime is
'操作时间';

comment on column SYS_LOG_OPERATE.operatetype is
'操作类型';

comment on column SYS_LOG_OPERATE.operatetable is
'操作对象';

comment on column SYS_LOG_OPERATE.recordid is
'操作对象ID';

/*==============================================================*/
/* Table: SYS_MENU                                              */
/*==============================================================*/
create table SYS_MENU  (
   menuid               VARCHAR2(16)                    not null,
   menuname             VARCHAR2(32)                    not null,
   menuurl              VARCHAR2(64)                    not null,
   parentmenuid         VARCHAR2(16)                    not null,
   menutype             CHAR(1)                        default '1' not null,
   ajaxmode             CHAR(1)                        default '0' not null,
   checked              CHAR(1)                        default '0' not null,
   confirmed            CHAR(1)                        default '0' not null,
   menuorder            INT                            default 0 not null,
   constraint PK_SYS_MENU primary key (menuid)
);

comment on table SYS_MENU is
'系统菜单表';

comment on column SYS_MENU.menuid is
'菜单ID';

comment on column SYS_MENU.menuname is
'菜单名';

comment on column SYS_MENU.menuurl is
'菜单URL';

comment on column SYS_MENU.parentmenuid is
'上级菜单';

comment on column SYS_MENU.menutype is
'0，目录；1，菜单；2，按钮';

comment on column SYS_MENU.ajaxmode is
'是否ajax方式请求，0，否；1，是';

comment on column SYS_MENU.checked is
'是否选中数据，0，否；1，是';

comment on column SYS_MENU.confirmed is
'跳转前是否确认，0，否；1，是';

comment on column SYS_MENU.menuorder is
'排序号';

/*==============================================================*/
/* Table: SYS_ORG                                               */
/*==============================================================*/
create table SYS_ORG  (
   orgid                VARCHAR2(16)                    not null,
   shortname            VARCHAR2(32)                    not null,
   fullname             VARCHAR2(128),
   linkman              VARCHAR2(32),
   linktel              VARCHAR2(32),
   faxno                VARCHAR2(32),
   address              VARCHAR(64),
   postcode             VARCHAR2(16),
   email                VARCHAR2(64),
   parentorgid          VARCHAR2(16)                    not null,
   orgtype              CHAR(1)                        default '1' not null,
   orgorder             INT                            default 0 not null,
   remark               VARCHAR2(64),
   constraint PK_SYS_ORG primary key (orgid)
);

comment on table SYS_ORG is
'组织机构表';

comment on column SYS_ORG.orgid is
'机构ID';

comment on column SYS_ORG.shortname is
'机构简称';

comment on column SYS_ORG.fullname is
'机构全称';

comment on column SYS_ORG.linkman is
'机构负责人';

comment on column SYS_ORG.linktel is
'联系电话';

comment on column SYS_ORG.faxno is
'传真';

comment on column SYS_ORG.address is
'通信地址';

comment on column SYS_ORG.postcode is
'邮政编码';

comment on column SYS_ORG.email is
'电子邮件';

comment on column SYS_ORG.parentorgid is
'上级机构ID';

comment on column SYS_ORG.orgtype is
'机构类型，0，无效；1，有效';

comment on column SYS_ORG.orgorder is
'排序号';

comment on column SYS_ORG.remark is
'备注';

/*==============================================================*/
/* Table: SYS_PARAM                                             */
/*==============================================================*/
create table SYS_PARAM  (
   paramid              INT                             not null,
   paramvalue           VARCHAR2(32)                    not null,
   constraint PK_SYS_PARAM primary key (paramid)
);

comment on table SYS_PARAM is
'系统参数表';

comment on column SYS_PARAM.paramid is
'参数ID';

comment on column SYS_PARAM.paramvalue is
'参数值';

/*==============================================================*/
/* Table: SYS_PARAM_EXT                                         */
/*==============================================================*/
create table SYS_PARAM_EXT  (
   paramid              INT                             not null,
   paramtitle           VARCHAR2(64)                    not null,
   paramtail            VARCHAR2(8),
   disptype             CHAR(1)                        default '0' not null,
   dictcode             INT,
   valuelist            VARCHAR2(64),
   paramgroup           CHAR(1),
   paramlength          INT                            default 0,
   paramorder           INT                            default 0 not null,
   constraint PK_SYS_PARAM_EXT primary key (paramid)
);

comment on table SYS_PARAM_EXT is
'系统参数扩展表';

comment on column SYS_PARAM_EXT.paramid is
'参数ID';

comment on column SYS_PARAM_EXT.paramtitle is
'参数描述';

comment on column SYS_PARAM_EXT.paramtail is
'参数结尾';

comment on column SYS_PARAM_EXT.disptype is
'0 text 无法修改；1 input 可以修改；2 select 可以修改';

comment on column SYS_PARAM_EXT.dictcode is
'对应的值列表为已知数据字典，优先于valuelist';

comment on column SYS_PARAM_EXT.valuelist is
'控件显示的值列表，如“0|否;1|是”';

comment on column SYS_PARAM_EXT.paramgroup is
'参数分组';

comment on column SYS_PARAM_EXT.paramlength is
'参数输入长度';

comment on column SYS_PARAM_EXT.paramorder is
'参数显示的顺序';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
create table SYS_ROLE  (
   roleid               NUMBER(12,0)                    not null,
   rolename             VARCHAR2(32)                    not null,
   rolestatus           CHAR(1)                         not null,
   roletype             CHAR(1)                         not null,
   enable               CHAR(1)                        default '1' not null,
   startdate            CHAR(8),
   enddate              CHAR(8),
   creator              VARCHAR2(16)                    not null,
   remark               VARCHAR2(64),
   constraint PK_SYS_ROLE primary key (roleid)
);

comment on table SYS_ROLE is
'系统角色表';

comment on column SYS_ROLE.roleid is
'角色ID';

comment on column SYS_ROLE.rolename is
'角色名称';

comment on column SYS_ROLE.rolestatus is
'角色状态，0，无效；1，有效';

comment on column SYS_ROLE.roletype is
'角色类型，0，公共；1，私有';

comment on column SYS_ROLE.enable is
'角色启用标志，0，停用，1，启用';

comment on column SYS_ROLE.startdate is
'生效日期';

comment on column SYS_ROLE.enddate is
'失效日期';

comment on column SYS_ROLE.creator is
'创建人';

comment on column SYS_ROLE.remark is
'备注';

/*==============================================================*/
/* Table: SYS_ROLE_MENU                                         */
/*==============================================================*/
create table SYS_ROLE_MENU  (
   roleid               NUMBER(12,0)                    not null,
   menuid               VARCHAR2(16)                    not null,
   constraint PK_SYS_ROLE_MENU primary key (roleid, menuid)
);

comment on table SYS_ROLE_MENU is
'角色菜单表';

comment on column SYS_ROLE_MENU.roleid is
'角色ID';

comment on column SYS_ROLE_MENU.menuid is
'菜单ID';

/*==============================================================*/
/* Table: SYS_SERIALNO                                          */
/*==============================================================*/
create table SYS_SERIALNO  (
   serialtype           VARCHAR2(16)                    not null,
   prevalue             NUMBER(12,0)                    not null,
   constraint PK_SYS_SERIALNO primary key (serialtype)
);

comment on table SYS_SERIALNO is
'序列号';

comment on column SYS_SERIALNO.serialtype is
'ID类型';

comment on column SYS_SERIALNO.prevalue is
'上一值';

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
create table SYS_USER  (
   userid               VARCHAR2(16)                    not null,
   username             VARCHAR2(32)                    not null,
   orgid                VARCHAR2(16)                    not null,
   loginpassword        VARCHAR2(32)                    not null,
   logindate            CHAR(8),
   logintime            CHAR(6),
   onlineflag           CHAR(1)                        default '0' not null,
   userstatus           CHAR(1)                        default '1' not null,
   logincount           INT                            default 0 not null,
   errorcount           INT                            default 0 not null,
   errordate            CHAR(8),
   errortime            CHAR(6),
   uptpwdate            CHAR(8),
   constraint PK_SYS_USER primary key (userid)
);

comment on table SYS_USER is
'系统用户表';

comment on column SYS_USER.userid is
'用户ID';

comment on column SYS_USER.username is
'用户姓名';

comment on column SYS_USER.orgid is
'用户所属机构ID';

comment on column SYS_USER.loginpassword is
'登陆系统的密码';

comment on column SYS_USER.logindate is
'登录成功日期';

comment on column SYS_USER.logintime is
'登陆成功时间';

comment on column SYS_USER.onlineflag is
'是否在线标志，0不在线，1在线';

comment on column SYS_USER.userstatus is
'用户状态，1，正常；0，已删除；2，锁定';

comment on column SYS_USER.logincount is
'登陆成功次数';

comment on column SYS_USER.errorcount is
'密码错误次数';

comment on column SYS_USER.errordate is
'第一次密码错误日期';

comment on column SYS_USER.errortime is
'第一次密码错误时间';

comment on column SYS_USER.uptpwdate is
'修改密码日期';

/*==============================================================*/
/* Table: SYS_USER_CONFIG                                       */
/*==============================================================*/
create table SYS_USER_CONFIG  (
   USERID               VARCHAR2(16)                    not null,
   PAGESIZE             INT,
   FASTMENU             VARCHAR2(256),
   constraint PK_SYS_USER_CONFIG primary key (USERID)
);

comment on table SYS_USER_CONFIG is
'用户设置表';

comment on column SYS_USER_CONFIG.USERID is
'用户ID';

comment on column SYS_USER_CONFIG.PAGESIZE is
'列表行数';

comment on column SYS_USER_CONFIG.FASTMENU is
'快捷菜单';

/*==============================================================*/
/* Table: SYS_USER_INFO                                         */
/*==============================================================*/
create table SYS_USER_INFO  (
   userid               VARCHAR2(16)                    not null,
   mobile               VARCHAR2(16),
   linktel              VARCHAR2(32),
   faxno                VARCHAR2(32),
   address              VARCHAR(64),
   postcode             VARCHAR2(8),
   email                VARCHAR2(32),
   sex                  CHAR(1),
   birthday             CHAR(8),
   province             VARCHAR2(8),
   city                 VARCHAR2(8),
   education            CHAR(2),
   createdate           CHAR(8),
   constraint PK_SYS_USER_INFO primary key (userid)
);

comment on table SYS_USER_INFO is
'用户信息表';

comment on column SYS_USER_INFO.userid is
'用户ID';

comment on column SYS_USER_INFO.mobile is
'手机';

comment on column SYS_USER_INFO.linktel is
'联系电话';

comment on column SYS_USER_INFO.faxno is
'传真';

comment on column SYS_USER_INFO.address is
'通信地址';

comment on column SYS_USER_INFO.postcode is
'邮政编码';

comment on column SYS_USER_INFO.email is
'电子邮件';

comment on column SYS_USER_INFO.sex is
'性别';

comment on column SYS_USER_INFO.birthday is
'出生年月';

comment on column SYS_USER_INFO.province is
'所在省份';

comment on column SYS_USER_INFO.city is
'所在城市';

comment on column SYS_USER_INFO.education is
'教育程度';

comment on column SYS_USER_INFO.createdate is
'创建日期';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
create table SYS_USER_ROLE  (
   roleid               NUMBER(12,0)                    not null,
   userid               VARCHAR2(16)                    not null,
   constraint PK_SYS_USER_ROLE primary key (roleid, userid)
);

comment on table SYS_USER_ROLE is
'用户角色表';

comment on column SYS_USER_ROLE.roleid is
'角色ID';

comment on column SYS_USER_ROLE.userid is
'用户ID';

