package org.gurms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysMenu;
import org.gurms.entity.system.SysUser;
import org.gurms.service.system.SysMenuService;
import org.gurms.service.system.SysUserService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SysLoginController extends BaseController {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping(value="/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/userlogin")
	public String userLogin(HttpServletRequest request, SysUser user){
		user.setLoginip(request.getRemoteAddr());
		PageResult<SysUser> result = sysUserService.login(user);
		if (result.isSuccess()) {
			SysUser sessionUser = result.getResult().get(0);
			setSession(request, sessionUser);
			
			return redirect("login");
		}
		
		return redirect(WebConstants.URL_INDEX);
	}		

	@RequestMapping(value="/ajaxlogin")
	@ResponseBody
	public PageResult<SysUser> ajaxLogin(HttpServletRequest request, SysUser user){
		user.setLoginip(request.getRemoteAddr());
		PageResult<SysUser> result = sysUserService.login(user);
		if (result.isSuccess()) {
			SysUser sessionUser = result.getResult().get(0);
			setSession(request, sessionUser);
		}
		
		return result;
	}		

	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return redirect(WebConstants.URL_INDEX);
	}	
	
	@RequestMapping(value="/welcome")
	public void welcome(){}
	
	private void setSession(HttpServletRequest request, SysUser user){
		SysMenu root = null;
		if(sysUserService.isAdmin(user.getUserid())){
			root = (SysMenu)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_MENU);
		}else{
			root = sysMenuService.getUserMenuTree(user.getUserid(), root);
		}
		
		request.getSession().setAttribute(WebConstants.S_KEY_USER, user);
		request.getSession().setAttribute(WebConstants.S_KEY_MENU, root);
	}
}
