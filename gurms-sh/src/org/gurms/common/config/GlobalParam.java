package org.gurms.common.config;

/**
 *  系统默认参数和数据库中的字典、参数
 */
public class GlobalParam {

	//---------------------系统参数------------------------
	//系统字符串分隔符
	public static final String ENCODING_UTF8 = "utf-8";
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
	public static final String SERIAL_EF_LINK = "ef_link";
	
	//数据库参数
	public static final int PARAM_SYSVERSION = 1;
	public static final int PARAM_USECACHE = 2;
	public static final int PARAM_ERRORCOUNT = 3;
	public static final int PARAM_LOCKTIME = 4;
	public static final int PARAM_COOKIEDAYS = 5;
	
	//数据库字典
	public static final int DICT_YESORNO = 1;
	public static final String DICT_YESORNO_YES = "1";
	public static final String DICT_YESORNO_NO  = "0";

	public static final int DICT_VALID = 2;
	public static final String DICT_VALID_YES = "1";
	public static final String DICT_VALID_NO  = "0";

	public static final int DICT_ONLINEFLAG = 3;
	public static final String DICT_ONLINEFLAG_YES = "1";
	public static final String DICT_ONLINEFLAG_NO  = "0";

	public static final int DICT_USERSTATUS = 5;
	public static final String DICT_USERSTATUS_DELETE = "0";
	public static final String DICT_USERSTATUS_NORMAL = "1";
	public static final String DICT_USERSTSTUS_PWLOCK = "2";
	
	public static final int DICT_ROLETYPE = 6;
	public static final String DICT_ROLETYPE_PUBLIC = "0";
	public static final String DICT_ROLETYPE_PRIVATE = "1";
	
	public static final int DICT_MENUTYPE = 7;
	public static final String DICT_MENUTYPE_FLODER = "0";
	public static final String DICT_MENUTYPE_MENU = "1";
	public static final String DICT_MENUTYPE_BUTTON = "2";
	
	public static final int DICT_ENABLEFLAG = 8;
	public static final String DICT_ENABLEFLAG_STOP = "0";
	public static final String DICT_ENABLEFLAG_ENABLE = "1";
	
	public static final int DICT_ORGTYPE = 9;
	public static final String DICT_ORGTYPE_ROOT = "0";
	public static final String DICT_ORGTYPE_BRANCH = "1";
	public static final String DICT_ORGTYPE_DEPARTMENT = "2";
	
	public static final int DICT_DISPTYPE = 10;
	public static final String DICT_DISPTYPE_TEXT = "0";
	public static final String DICT_DISPTYPE_INPUT = "1";
	public static final String DICT_DISPTYPE_SELECT = "2";
	
	private GlobalParam(){}
}
