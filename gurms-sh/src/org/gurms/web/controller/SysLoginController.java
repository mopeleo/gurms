package org.gurms.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysMenu;
import org.gurms.entity.system.SysParam;
import org.gurms.entity.system.SysUser;
import org.gurms.entity.system.SysUserConfig;
import org.gurms.service.system.SysMenuService;
import org.gurms.service.system.SysParamService;
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
	
	@Autowired
	private SysParamService sysParamService;
	
	@RequestMapping(value="/userlogin")
	public String userLogin(HttpServletRequest request, HttpServletResponse response, SysUser user){
		if(checkValidcode(request)){
			user.setLoginip(request.getRemoteAddr());
			PageResult<SysUser> result = sysUserService.login(user);
			if (result.isSuccess()) {
				SysUser sessionUser = result.getResult().get(0);
				setUserSession(request, sessionUser);
				setCookie(request, response, sessionUser);
				
				return redirect("login");
			}
		}
		
		return redirect(WebConstants.URL_LOGIN);
	}		

	@RequestMapping
	public String autoLogin(HttpServletRequest request, HttpServletResponse response){
		//1.首先判断用户信息有没有保存到COOKIE
        Cookie[] cookies = request.getCookies();
        if (null == cookies)
            return redirect(WebConstants.URL_LOGIN);
        SysUser cookieUser = new SysUser();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(WebConstants.COOKIE_USERID)) {
            	cookieUser.setUserid(cookie.getValue());
            }
            if (cookie.getName().equals(WebConstants.COOKIE_PASSWORD)) {
            	cookieUser.setLoginpassword(cookie.getValue());
            }
        }
        if(StringUtils.isBlank(cookieUser.getUserid()) || StringUtils.isBlank(cookieUser.getLoginpassword())){
            return redirect(WebConstants.URL_LOGIN);
        }
        //2.判断session有没有过期，若没有过期，不用登录
        HttpSession session = request.getSession();
    	SysUser sessionUser = (SysUser)session.getAttribute(WebConstants.S_KEY_USER);
    	if(sessionUser != null){
        	if(cookieUser.getUserid().equals(sessionUser.getUserid()) && cookieUser.getLoginpassword().equals(sessionUser.getLoginpassword())){
    			return redirect("/login");
        	}
    	}
        //3.重新登录
        cookieUser.setLoginip(request.getRemoteAddr());
		PageResult<SysUser> result = sysUserService.login(cookieUser);
		if (result.isSuccess()) {
			SysUser dbUser = result.getResult().get(0);
			setUserSession(request, dbUser);			
			return redirect("/login");
		}
		
		return redirect(WebConstants.URL_LOGIN);
	}		

	@RequestMapping
	@ResponseBody
	public PageResult<SysUser> ajaxLogin(HttpServletRequest request, HttpServletResponse response, SysUser user){
		PageResult<SysUser> result = null;
		try{
			if(checkValidcode(request)){
				user.setLoginip(request.getRemoteAddr());
				result = sysUserService.login(user);
				if (result.isSuccess()) {
					SysUser sessionUser = result.getResult().get(0);
					setUserSession(request, sessionUser);
					setCookie(request, response, sessionUser);
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
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		ServletUtil.deleteCookie(request, response);
		return redirect("/");
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
		
		SysUserConfig config = sysUserService.getUserConfigById(user.getUserid());
		if(config == null){
			config = new SysUserConfig();
		}
		session.setAttribute(WebConstants.S_KEY_USERCONFIG, config);
		String fast = config.getFastmenu();
		if(StringUtils.isNotBlank(fast)){
			String[] ids = StringUtils.split(fast, GlobalParam.STRING_SEPARATOR);
			List<String> idList = Arrays.asList(ids);
			List<SysMenu> fastmenu = sysMenuService.get(idList);
			session.setAttribute(WebConstants.S_KEY_FASTMENU, fastmenu);
		}
	}
	
	private void setCookie(HttpServletRequest request, HttpServletResponse response, SysUser user){
		String remember = request.getParameter("remember");
		if("1".equals(remember)){
			SysParam cookiedays = sysParamService.getParamById(GlobalParam.PARAM_COOKIEDAYS);
			int cookieAge = Integer.parseInt(cookiedays.getParamvalue())*24*3600;
			Cookie userid = new Cookie(WebConstants.COOKIE_USERID, user.getUserid());
			userid.setPath("/");
			userid.setMaxAge(cookieAge);
			response.addCookie(userid);
			
			Cookie password = new Cookie(WebConstants.COOKIE_PASSWORD, user.getLoginpassword());
			password.setPath("/");
			password.setMaxAge(cookieAge);
			response.addCookie(password);
			
			//把sessionid也保存到cookie
			Cookie sessionid = new Cookie("JSESSIONID", request.getSession().getId());
			sessionid.setPath("/");
			sessionid.setMaxAge(cookieAge);
			response.addCookie(sessionid);
		}else{
			ServletUtil.deleteCookie(request, response);
		}
	}

}
