package org.gurms.web.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.util.CommonUtil;
import org.gurms.entity.system.SysMenu;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ControllerInterceptor extends HandlerInterceptorAdapter {
	
	private String ignoreSession;
	
	public String getIgnoreSession() {
		return ignoreSession;
	}

	public void setIgnoreSession(String ignoreSession) {
		this.ignoreSession = ignoreSession;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String requestUrl = request.getServletPath();
		String[] ignoreSuffix = null;
		if(!StringUtils.isBlank(ignoreSession)){
			ignoreSuffix = ignoreSession.split(",");
		}
		//不在过滤后缀列表中的要效验session和检查菜单权限
		if(!CommonUtil.existSuffix(requestUrl,ignoreSuffix)){
			Object user = request.getSession().getAttribute(WebConstants.S_KEY_USER);
			if(user == null){
				return false;
			}
		}

		// TODO 菜单、请求的权限
		SysMenu root = (SysMenu)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_MENU);
		//请求的url首先必须在配置的权限管理返回之内
		if(hasPrivilege(requestUrl, root)){
			SysMenu privilege = (SysMenu)request.getSession().getAttribute(WebConstants.S_KEY_MENU);
			//再判断用户是否有权限
			if(!hasPrivilege(requestUrl, privilege)){
				return false;
			}
		}			
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			ModelAndView modelandview) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			Exception exception) throws Exception {
	}

	private boolean hasPrivilege(String url, SysMenu menus){
		if(menus == null){
			return false;
		}
		
		if("0".equals(menus.getMenutype())){
			for(SysMenu m : menus.getSubmenus()){
				if(hasPrivilege(url, m)){
					return true;
				}
			}
		}else{
			return url.endsWith(menus.getMenuurl());
		}
		return false;
	}
}
