package org.gurms.web.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysRole;
import org.gurms.entity.system.SysUser;
import org.gurms.service.system.SysRoleService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysRoleController extends BaseController {
	
	public static final String ROLE_PULBIC = "/sysrole/publics";
	public static final String ROLE_PRIVATE = "/sysrole/privates";

	@Autowired
	private SysRoleService roleService;
	
	@RequestMapping
	public void publics(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		requestMap.put("EQ_roletype", GlobalParam.DICT_ROLETYPE_PUBLIC);
		PageResult<SysRole> result = roleService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void privates(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
		requestMap.put("EQ_roletype", GlobalParam.DICT_ROLETYPE_PRIVATE);
		requestMap.put("EQ_creater", user.getUserid());
		PageResult<SysRole> result = roleService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void publicDetail(String roleid, Model model){
		info(roleid, model);
	}
	
	@RequestMapping
	public void privateDetail(String roleid, Model model){
		info(roleid, model);
	}
	
	@RequestMapping
	public void info(String roleid, Model model){
		if(StringUtils.isNotBlank(roleid)){
			SysRole role = roleService.get(roleid);
			model.addAttribute(WebConstants.KEY_RESULT, role);
		}
	}
	
	@RequestMapping
	public String save(SysRole role){
		roleService.save(role);
		return redirect(ROLE_PULBIC);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(SysRole role){
		PageResult page = null;
		try{
			page = roleService.save(role);
		}catch(Exception e){
			page = processException(e, "保存角色信息出错");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxInsert(SysRole role){
		PageResult page = null;
		try{
			page = roleService.insert(role);
		}catch(Exception e){
			page = processException(e, "新增角色信息出错");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(String roleid){
		PageResult page = null;
		try{
			roleService.delete(roleid);
			page = new PageResult();
		}catch(Exception e){
			page = processException(e, "新增角色信息出错");
		}
		return page;
	}
	
	@RequestMapping
	public String delete(String roleid){
		roleService.delete(roleid);
		return redirect(ROLE_PULBIC);
	}
}
