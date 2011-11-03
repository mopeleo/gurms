<@c.html>
	<#if session_menu.submenus?size == 0>
	    您没有任何权限或权限已过期，请与管理员联系！
	<#else>
	

	<span>${bundle("0001",session_user.userid,session_user.logincount,session_user.logindate+session_user.logintime)}</span>
	<span>${bundle("9000")}[${statics["org.gurms.common.config.GlobalParam"].STRING_SEPARATOR}]</span>
	</#if>
</@c.html>