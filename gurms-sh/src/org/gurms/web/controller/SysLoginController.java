package org.gurms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gurms.common.util.EncryptUtil;
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
		SysUser u = sysUserService.get(user.getUserid());
		String pw = EncryptUtil.md5Encode(user.getLoginpassword());
		if (u != null && u.getLoginpassword().equalsIgnoreCase(pw)) {
			SysMenu root = (SysMenu)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_MENU);
			if(!sysUserService.isAdmin(u.getUserid())){
				root = sysMenuService.getUserMenuTree(u.getUserid(), root);
			}
			
			request.getSession().setAttribute(WebConstants.S_KEY_USER, u);
			request.getSession().setAttribute(WebConstants.S_KEY_MENU, root);
			
			return redirect("login");
		}
		
		return redirect(WebConstants.URL_INDEX);
	}		

	@RequestMapping(value="/ajaxlogin")
	@ResponseBody
	public PageResult ajaxLogin(HttpServletRequest request, SysUser user){
		SysUser u = sysUserService.get(user.getUserid());
		String pw = EncryptUtil.md5Encode(user.getLoginpassword());
		PageResult result = new PageResult();
		if (u != null && u.getLoginpassword().equals(pw)) {
			SysMenu root = (SysMenu)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_MENU);
			if(!sysUserService.isAdmin(u.getUserid())){
				root = sysMenuService.getUserMenuTree(u.getUserid(), root);
			}
			
			request.getSession().setAttribute(WebConstants.S_KEY_USER, u);
			request.getSession().setAttribute(WebConstants.S_KEY_MENU, root);
			
		}else{
			result.setSuccess(false);
			result.setReturnmsg("用户名或密码错误");
		}
		return result;
	}		

	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return redirect(WebConstants.URL_INDEX);
	}	
}
