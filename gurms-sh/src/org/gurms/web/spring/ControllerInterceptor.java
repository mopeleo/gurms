package org.gurms.web.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalConfig;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.exception.GurmsException;
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
			ignoreSuffix = ignoreSession.split(GlobalParam.STRING_SEPARATOR);
		}
		//不在过滤后缀列表中的要效验session和检查菜单权限
		if(!CommonUtil.existSuffix(requestUrl,ignoreSuffix)){
			Object user = request.getSession().getAttribute(WebConstants.S_KEY_USER);
			if(user == null){
				throw new GurmsException("用户信息不存在，请重新登录");
			}
		}

		// TODO 菜单、请求的权限
		SysMenu root = (SysMenu)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_MENU);
		//请求的url首先必须在配置的权限管理返回之内
		SysMenu currentMenu = getMenuFromURL(requestUrl, root);
		if(currentMenu != null){
			SysMenu privilege = (SysMenu)request.getSession().getAttribute(WebConstants.S_KEY_MENU);
			//再判断用户是否有权限
			SysMenu currUserMenu = getMenuFromURL(requestUrl, privilege);
			//URL类型 是菜单
			if(GlobalParam.DICT_MENUTYPE_MENU.equals(currentMenu.getMenutype())){
				if(currUserMenu == null){
					throw new GurmsException("您没有此权限");
				}
				
				//如果是菜单，要把菜单ID传到页面，生成页面按钮权限
				//权限控制到按钮
				if(GlobalConfig.PRIVILEGE_LEVEL_BUTTON.equals(GlobalConfig.PRIVILEGE_LEVEL)){
					request.setAttribute("button", currUserMenu.getSubmenus());
				}else{
					request.setAttribute("button", currentMenu.getSubmenus());
				}
				
			//URL类型 是按钮
			}else if(GlobalParam.DICT_MENUTYPE_BUTTON.equals(currentMenu.getMenutype())){
				//权限控制到按钮
				if(GlobalConfig.PRIVILEGE_LEVEL_BUTTON.equals(GlobalConfig.PRIVILEGE_LEVEL)){
					if(currUserMenu == null){
						throw new GurmsException("您没有此权限");
					}
				}
			}
			
			//把查询列表改为弹出选择框
			String select = request.getParameter("frameid");
			if(StringUtils.isNotBlank(select)){
				request.setAttribute("frameid", select);
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
		
		if(url.endsWith(menus.getMenuurl())){
			return true;
		}
		
		if(menus.getSubmenus().size() > 0){
			for(SysMenu m : menus.getSubmenus()){
				if(hasPrivilege(url, m)){
					return true;
				}
			}
		}

		return false;
	}
	
	private SysMenu getMenuFromURL(String url, SysMenu menus){
		if(menus == null || url.endsWith(menus.getMenuurl())){
			return menus;
		}
		
		if(menus.getSubmenus().size() > 0){
			for(SysMenu m : menus.getSubmenus()){
				SysMenu t = getMenuFromURL(url, m);
				if(t != null){
					return t;
				}
			}
		}
		
		return null;
	}
}
