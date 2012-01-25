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
'�ֵ�����';

comment on column SYS_DICT_INDEX.dictcode is
'�ֵ����';

comment on column SYS_DICT_INDEX.dictname is
'�ֵ�����';

comment on column SYS_DICT_INDEX.dicttype is
'�����ֵ����';

comment on column SYS_DICT_INDEX.editflag is
'�༭��־';

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
'�����ֵ��';

comment on column SYS_DICT_VALUE.dictcode is
'�ֵ����';

comment on column SYS_DICT_VALUE.dictitem is
'�ֵ���';

comment on column SYS_DICT_VALUE.itemname is
'�ֵ�������';

comment on column SYS_DICT_VALUE.dictorder is
'��ʾ˳��';

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
'ϵͳ��¼��־��';

comment on column SYS_LOG_LOGIN.LOGID is
'id';

comment on column SYS_LOG_LOGIN.USERID is
'�û�ID';

comment on column SYS_LOG_LOGIN.LOGINDATE is
'��¼����';

comment on column SYS_LOG_LOGIN.LOGINTIME is
'��¼ʱ��';

comment on column SYS_LOG_LOGIN.LOGINPASSWORD is
'��¼���루���ģ�';

comment on column SYS_LOG_LOGIN.LOGINIP is
'��¼IP';

comment on column SYS_LOG_LOGIN.SUCCESS is
'�Ƿ��¼�ɹ���1���ǣ�0��';

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
'ϵͳ��־��';

comment on column SYS_LOG_OPERATE.logid is
'��־ID';

comment on column SYS_LOG_OPERATE.userid is
'������';

comment on column SYS_LOG_OPERATE.operatedate is
'��������';

comment on column SYS_LOG_OPERATE.operatetime is
'����ʱ��';

comment on column SYS_LOG_OPERATE.operatetype is
'��������';

comment on column SYS_LOG_OPERATE.operatetable is
'��������';

comment on column SYS_LOG_OPERATE.recordid is
'��������ID';

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
'ϵͳ�˵���';

comment on column SYS_MENU.menuid is
'�˵�ID';

comment on column SYS_MENU.menuname is
'�˵���';

comment on column SYS_MENU.menuurl is
'�˵�URL';

comment on column SYS_MENU.parentmenuid is
'�ϼ��˵�';

comment on column SYS_MENU.menutype is
'0��Ŀ¼��1���˵���2����ť';

comment on column SYS_MENU.ajaxmode is
'�Ƿ�ajax��ʽ����0����1����';

comment on column SYS_MENU.checked is
'�Ƿ�ѡ�����ݣ�0����1����';

comment on column SYS_MENU.confirmed is
'��תǰ�Ƿ�ȷ�ϣ�0����1����';

comment on column SYS_MENU.menuorder is
'�����';

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
'��֯������';

comment on column SYS_ORG.orgid is
'����ID';

comment on column SYS_ORG.shortname is
'�������';

comment on column SYS_ORG.fullname is
'����ȫ��';

comment on column SYS_ORG.linkman is
'����������';

comment on column SYS_ORG.linktel is
'��ϵ�绰';

comment on column SYS_ORG.faxno is
'����';

comment on column SYS_ORG.address is
'ͨ�ŵ�ַ';

comment on column SYS_ORG.postcode is
'��������';

comment on column SYS_ORG.email is
'�����ʼ�';

comment on column SYS_ORG.parentorgid is
'�ϼ�����ID';

comment on column SYS_ORG.orgtype is
'�������ͣ�0����Ч��1����Ч';

comment on column SYS_ORG.orgorder is
'�����';

comment on column SYS_ORG.remark is
'��ע';

/*==============================================================*/
/* Table: SYS_PARAM                                             */
/*==============================================================*/
create table SYS_PARAM  (
   paramid              INT                             not null,
   paramvalue           VARCHAR2(32)                    not null,
   constraint PK_SYS_PARAM primary key (paramid)
);

comment on table SYS_PARAM is
'ϵͳ������';

comment on column SYS_PARAM.paramid is
'����ID';

comment on column SYS_PARAM.paramvalue is
'����ֵ';

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
'ϵͳ������չ��';

comment on column SYS_PARAM_EXT.paramid is
'����ID';

comment on column SYS_PARAM_EXT.paramtitle is
'��������';

comment on column SYS_PARAM_EXT.paramtail is
'������β';

comment on column SYS_PARAM_EXT.disptype is
'0 text �޷��޸ģ�1 input �����޸ģ�2 select �����޸�';

comment on column SYS_PARAM_EXT.dictcode is
'��Ӧ��ֵ�б�Ϊ��֪�����ֵ䣬������valuelist';

comment on column SYS_PARAM_EXT.valuelist is
'�ؼ���ʾ��ֵ�б��硰0|��;1|�ǡ�';

comment on column SYS_PARAM_EXT.paramgroup is
'��������';

comment on column SYS_PARAM_EXT.paramlength is
'�������볤��';

comment on column SYS_PARAM_EXT.paramorder is
'������ʾ��˳��';

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
'ϵͳ��ɫ��';

comment on column SYS_ROLE.roleid is
'��ɫID';

comment on column SYS_ROLE.rolename is
'��ɫ����';

comment on column SYS_ROLE.rolestatus is
'��ɫ״̬��0����Ч��1����Ч';

comment on column SYS_ROLE.roletype is
'��ɫ���ͣ�0��������1��˽��';

comment on column SYS_ROLE.enable is
'��ɫ���ñ�־��0��ͣ�ã�1������';

comment on column SYS_ROLE.startdate is
'��Ч����';

comment on column SYS_ROLE.enddate is
'ʧЧ����';

comment on column SYS_ROLE.creator is
'������';

comment on column SYS_ROLE.remark is
'��ע';

/*==============================================================*/
/* Table: SYS_ROLE_MENU                                         */
/*==============================================================*/
create table SYS_ROLE_MENU  (
   roleid               NUMBER(12,0)                    not null,
   menuid               VARCHAR2(16)                    not null,
   constraint PK_SYS_ROLE_MENU primary key (roleid, menuid)
);

comment on table SYS_ROLE_MENU is
'��ɫ�˵���';

comment on column SYS_ROLE_MENU.roleid is
'��ɫID';

comment on column SYS_ROLE_MENU.menuid is
'�˵�ID';

/*==============================================================*/
/* Table: SYS_SERIALNO                                          */
/*==============================================================*/
create table SYS_SERIALNO  (
   serialtype           VARCHAR2(16)                    not null,
   prevalue             NUMBER(12,0)                    not null,
   constraint PK_SYS_SERIALNO primary key (serialtype)
);

comment on table SYS_SERIALNO is
'���к�';

comment on column SYS_SERIALNO.serialtype is
'ID����';

comment on column SYS_SERIALNO.prevalue is
'��һֵ';

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
'ϵͳ�û���';

comment on column SYS_USER.userid is
'�û�ID';

comment on column SYS_USER.username is
'�û�����';

comment on column SYS_USER.orgid is
'�û���������ID';

comment on column SYS_USER.loginpassword is
'��½ϵͳ������';

comment on column SYS_USER.logindate is
'��¼�ɹ�����';

comment on column SYS_USER.logintime is
'��½�ɹ�ʱ��';

comment on column SYS_USER.onlineflag is
'�Ƿ����߱�־��0�����ߣ�1����';

comment on column SYS_USER.userstatus is
'�û�״̬��1��������0����ɾ����2������';

comment on column SYS_USER.logincount is
'��½�ɹ�����';

comment on column SYS_USER.errorcount is
'����������';

comment on column SYS_USER.errordate is
'��һ�������������';

comment on column SYS_USER.errortime is
'��һ���������ʱ��';

comment on column SYS_USER.uptpwdate is
'�޸���������';

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
'�û����ñ�';

comment on column SYS_USER_CONFIG.USERID is
'�û�ID';

comment on column SYS_USER_CONFIG.PAGESIZE is
'�б�����';

comment on column SYS_USER_CONFIG.FASTMENU is
'��ݲ˵�';

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
'�û���Ϣ��';

comment on column SYS_USER_INFO.userid is
'�û�ID';

comment on column SYS_USER_INFO.mobile is
'�ֻ�';

comment on column SYS_USER_INFO.linktel is
'��ϵ�绰';

comment on column SYS_USER_INFO.faxno is
'����';

comment on column SYS_USER_INFO.address is
'ͨ�ŵ�ַ';

comment on column SYS_USER_INFO.postcode is
'��������';

comment on column SYS_USER_INFO.email is
'�����ʼ�';

comment on column SYS_USER_INFO.sex is
'�Ա�';

comment on column SYS_USER_INFO.birthday is
'��������';

comment on column SYS_USER_INFO.province is
'����ʡ��';

comment on column SYS_USER_INFO.city is
'���ڳ���';

comment on column SYS_USER_INFO.education is
'�����̶�';

comment on column SYS_USER_INFO.createdate is
'��������';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
create table SYS_USER_ROLE  (
   roleid               NUMBER(12,0)                    not null,
   userid               VARCHAR2(16)                    not null,
   constraint PK_SYS_USER_ROLE primary key (roleid, userid)
);

comment on table SYS_USER_ROLE is
'�û���ɫ��';

comment on column SYS_USER_ROLE.roleid is
'��ɫID';

comment on column SYS_USER_ROLE.userid is
'�û�ID';

