package org.gurms.web.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysUser;
import org.gurms.entity.system.SysUserInfo;
import org.gurms.service.system.SysRoleService;
import org.gurms.service.system.SysUserService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysUserController extends BaseController {

	public static final String USER_LIST ="/sysuser/list";
	public static final String USER_SELF ="/sysuser/self";

	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysRoleService roleService;
	
	@RequestMapping
	public void list(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<SysUser> result = userService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void detail(String userid, Model model){
		model.addAttribute("allroles", roleService.getAll());
		if(StringUtils.isNotBlank(userid)){
			SysUser vo = userService.get(userid);
			model.addAttribute(WebConstants.KEY_RESULT, vo);
		}
	}
	
	@RequestMapping
	public void self(HttpServletRequest request, Model model){
		SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
		SysUserInfo vo = userService.getUserInfo(user.getUserid());
		model.addAttribute(WebConstants.KEY_RESULT, vo);
	}
	
	@RequestMapping
	public String userinfo(SysUserInfo userinfo){
		userService.saveUserInfo(userinfo);
		return redirect(USER_SELF);
	}
	
	@RequestMapping
	public String save(SysUser user){
		userService.save(user);
		return redirect(USER_LIST);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult<SysUser> ajaxSave(SysUser user){
		return userService.save(user);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult<SysUser> ajaxInsert(SysUser user){
		PageResult<SysUser> r =  userService.insert(user);
		return r;
	}
	
	@RequestMapping
	public String delete(String userid){
		userService.delete(userid);
		return redirect(USER_LIST);
	}
	
	@RequestMapping
	public void password(){}
	
	@RequestMapping
	@ResponseBody
	public PageResult<SysUser> setPassword(SysUser user){
		return userService.setPassword(user);
	}
}
