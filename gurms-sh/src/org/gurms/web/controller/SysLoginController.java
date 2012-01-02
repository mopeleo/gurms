package org.gurms.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysMenu;
import org.gurms.entity.system.SysUser;
import org.gurms.entity.system.SysUserConfig;
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
	
	@RequestMapping(value="/userlogin")
	public String userLogin(HttpServletRequest request, SysUser user){
		user.setLoginip(request.getRemoteAddr());
		PageResult<SysUser> result = sysUserService.login(user);
		if (result.isSuccess()) {
			SysUser sessionUser = result.getResult().get(0);
			setUserSession(request, sessionUser);
			
			return redirect("login");
		}
		
		return redirect(WebConstants.URL_INDEX);
	}		

	@RequestMapping
	@ResponseBody
	public PageResult<SysUser> ajaxLogin(HttpServletRequest request, SysUser user){
		PageResult<SysUser> result = null;
		try{
			if(checkValidcode(request)){
				user.setLoginip(request.getRemoteAddr());
				result = sysUserService.login(user);
				if (result.isSuccess()) {
					SysUser sessionUser = result.getResult().get(0);
					setUserSession(request, sessionUser);
				}
				result.setResult(null);
			}else{
				result = new PageResult<SysUser>();
				result.setSuccess(false);
				result.setReturnmsg("验证码错误");
			}
		}catch(Exception e){
			processException(e, "用户登录异常");
		}
		
		return result;
	}		

	@RequestMapping
	public String logout(HttpSession session) {
		session.invalidate();
		return redirect(WebConstants.URL_INDEX);
	}	
	
	private boolean checkValidcode(HttpServletRequest request){
		String validcode = (String)request.getSession().getAttribute(WebConstants.S_KEY_VALIDCODE);
		String inputvalidcode = request.getParameter("validcode");
		return validcode.equalsIgnoreCase(inputvalidcode);
	}
	
	private void setUserSession(HttpServletRequest request, SysUser user){
		SysMenu root = null;
		if(sysUserService.isAdmin(user.getUserid())){
			root = (SysMenu)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_MENU);
		}else{
			root = sysMenuService.getUserMenuTree(user.getUserid());
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(WebConstants.S_KEY_USER, user);
		session.setAttribute(WebConstants.S_KEY_MENU, root);
		
		SysUserConfig config = sysUserService.getUserConfig(user.getUserid());
		if(config == null){
			config = new SysUserConfig();
		}
		session.setAttribute(WebConstants.S_KEY_USERCONFIG, config);
		String fast = config.getFastmenu();
		if(StringUtils.isNotBlank(fast)){
			String[] ids = StringUtils.split(fast, GlobalParam.STRING_SEPARATOR);
			List idList = Arrays.asList(ids);
			List<SysMenu> fastmenu = sysMenuService.get(idList);
			session.setAttribute(WebConstants.S_KEY_FASTMENU, fastmenu);
		}
	}
}
