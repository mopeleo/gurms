package org.gurms.web;

public abstract class WebConstants {
	
	//-- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	// session中的key
	public static final String S_KEY_MENU = "session_menu";	
	public static final String S_KEY_FASTMENU = "session_fastmenu";	
	public static final String S_KEY_USER = "session_user";	
	public static final String S_KEY_USERCONFIG = "session_userconfig";	
	public static final String S_KEY_VALIDCODE = "session_validcode";
	
	//context中的key
	public static final String C_KEY_DICT = "context_dict";
	public static final String C_KEY_DICTTYPE = "context_dicttype";
	public static final String C_KEY_PARAM = "context_param";
	public static final String C_KEY_MENU = "context_menu";
	public static final String C_KEY_ORG = "context_org";
	
	// request/model 中结果集的KEY
	public static final String KEY_RESULT = "result";
	
	// 页面中查询条件的前缀
	public static final String PREFIX_QUERY = "filter_";
	
	// 首页URL
	public static final String URL_INDEX = "index.jsp";	

}
