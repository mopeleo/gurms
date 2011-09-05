package org.gurms.common.config;

/**
 *  系统默认参数和数据库中的字典、参数
 */
public class GlobalParam {

	//---------------------系统参数------------------------
	//系统字符串分隔符
	public static final String STRING_SEPARATOR = ",";
	public static final String SYSTEM_ROLE = "system";
	public static final String MENU_ROOTID = "root";
	public static final String ORG_ROOTID  = "root";
	
	//数据库参数
	public static final String PARAM_ERRORCOUNT = "0003";
	public static final String PARAM_LOCKTIME = "0004";
	
	//数据库字典
	public static final String DICT_YESORNO = "0001";
	public static final String DICT_YESORNO_YES = "1";
	public static final String DICT_YESORNO_NO  = "0";

	public static final String DICT_ONLINEFLAG = "0003";
	public static final String DICT_ONLINEFLAG_YES = "1";
	public static final String DICT_ONLINEFLAG_NO  = "0";

	public static final String DICT_USERSTATUS = "0005";
	public static final String DICT_USERSTATUS_NORMAL = "1";
	public static final String DICT_USERSTSTUS_PWLOCK = "2";
	
	private GlobalParam(){}
}
