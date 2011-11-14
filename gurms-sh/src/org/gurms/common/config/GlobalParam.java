package org.gurms.common.config;

/**
 *  系统默认参数和数据库中的字典、参数
 */
public abstract class GlobalParam {

	//---------------------系统参数------------------------
	//系统字符串分隔符
	public static final String STRING_SEPARATOR = ",";
	public static final int SYSTEM_ROLE = 0;
	public static final String MENU_ROOTID = "root";
	public static final String ORG_ROOTID  = "root";
	//存储过程执行成功的返回码，其他都为异常
	public static final int    SP_SUCCESS_INT = 1;
	public static final String SP_SUCCESS_STR = "0000";
	
	//自动增长序列号 类型
	public static final String SERIAL_SYS_ORG = "sys_org";
	public static final String SERIAL_SYS_ROLE = "sys_role";
	
	//数据库参数
	public static final String PARAM_ERRORCOUNT = "0003";
	public static final String PARAM_LOCKTIME = "0004";
	
	//数据库字典
	public static final String DICT_YESORNO = "0001";
	public static final String DICT_YESORNO_YES = "1";
	public static final String DICT_YESORNO_NO  = "0";

	public static final String DICT_VALID = "0002";
	public static final String DICT_VALID_YES = "1";
	public static final String DICT_VALID_NO  = "0";

	public static final String DICT_ONLINEFLAG = "0003";
	public static final String DICT_ONLINEFLAG_YES = "1";
	public static final String DICT_ONLINEFLAG_NO  = "0";

	public static final String DICT_USERSTATUS = "0005";
	public static final String DICT_USERSTATUS_NORMAL = "1";
	public static final String DICT_USERSTSTUS_PWLOCK = "2";
	
	public static final String DICT_ROLETYPE = "0006";
	public static final String DICT_ROLETYPE_PUBLIC = "0";
	public static final String DICT_ROLETYPE_PRIVATE = "1";
	
	public static final String DICT_MENUTYPE = "0007";
	public static final String DICT_MENUTYPE_FLODER = "0";
	public static final String DICT_MENUTYPE_MENU = "1";
	public static final String DICT_MENUTYPE_BUTTON = "2";
	
	public static final String DICT_ENABLEFLAG = "0008";
	public static final String DICT_ENABLEFLAG_STOP = "0";
	public static final String DICT_ENABLEFLAG_ENABLE = "1";
	
}
