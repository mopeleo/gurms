package org.gurms.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysRole;
import org.gurms.entity.system.SysUser;
import org.gurms.entity.system.SysUserConfig;
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
	public static final String USER_INFO ="/sysuser/info";
	public static final String USER_CONFIG ="/sysuser/config";

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
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("EQ_roletype", GlobalParam.DICT_ROLETYPE_PUBLIC);
		List<SysRole> publics = roleService.query(roleMap);		
		model.addAttribute("allroles", publics);
		
		if(StringUtils.isNotBlank(userid)){
			SysUser vo = userService.get(userid);
			model.addAttribute(WebConstants.KEY_RESULT, vo);
		}
	}
	
	@RequestMapping
	public void info(HttpServletRequest request, Model model){
		SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
		SysUserInfo vo = userService.getUserInfo(user.getUserid());
		model.addAttribute(WebConstants.KEY_RESULT, vo);
	}
	
	@RequestMapping
	public String userinfo(SysUserInfo userinfo){
		userService.saveUserInfo(userinfo);
		return redirect(USER_INFO);
	}
	
	@RequestMapping
	public String save(SysUser user){
		userService.save(user);
		return redirect(USER_LIST);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(SysUser user){
		PageResult page = null;
		try{
			page = userService.save(user);
		}catch(Exception e){
			page = processException(e, "保存用户信息出错");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxInsert(SysUser user){
		PageResult page = null;
		try{
			page =  userService.insert(user);
		}catch(Exception e){
			page = processException(e, "新增用户信息出错");
		}
		return page;
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
	public PageResult setPassword(SysUser user){
		PageResult page = null;
		try{
			page = userService.setPassword(user);
		}catch(Exception e){
			page = processException(e, "修改密码出错");
		}
		return page;
	}
	
	@RequestMapping
	public void config(HttpServletRequest request, Model model){
		SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
		SysUserConfig vo = userService.getUserConfig(user.getUserid());
		model.addAttribute(WebConstants.KEY_RESULT, vo);
	}
	
	@RequestMapping
	public String setConfig(SysUserConfig config){
		userService.saveUserConfig(config);
		return redirect(USER_CONFIG);
	}
}
