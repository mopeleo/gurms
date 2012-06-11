package org.gurms.web.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysAccessory;
import org.gurms.entity.system.SysUser;
import org.gurms.service.system.SysAccessoryService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysAccessoryController extends BaseController {
	
	public static final String ACCESSORY_PULBIC = "/sysaccessory/publics";
	public static final String ACCESSORY_PRIVATE = "/sysaccessory/privates";

	@Autowired
	private SysAccessoryService accessoryService;
	
	@RequestMapping
	public void publics(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		requestMap.put("EQS_status", GlobalParam.DICT_ROLETYPE_PUBLIC);
		PageResult<SysAccessory> result = accessoryService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void privates(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
		requestMap.put("EQS_status", GlobalParam.DICT_ROLETYPE_PRIVATE);
		requestMap.put("EQS_userid", user.getUserid());
		PageResult<SysAccessory> result = accessoryService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void detail(String roleid, Model model){
		info(roleid, model);
	}
	
	@RequestMapping
	public void info(String accessoryid, Model model){
		if(StringUtils.isNotBlank(accessoryid)){
			SysAccessory role = accessoryService.get(accessoryid);
			model.addAttribute(WebConstants.KEY_RESULT, role);
		}
	}
	
	@RequestMapping
	public String save(SysAccessory accessory){
		accessoryService.save(accessory);
		return redirect(ACCESSORY_PULBIC);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(SysAccessory accessory){
		PageResult page = null;
		try{
			page = accessoryService.save(accessory);
		}catch(Exception e){
			page = processException(e, "保存附件出错");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(String accessoryid){
		PageResult page = null;
		try{
			accessoryService.delete(accessoryid);
			page = new PageResult();
		}catch(Exception e){
			page = processException(e, "删除附件出错");
		}
		return page;
	}
	
	@RequestMapping
	public String delete(String accessoryid){
		accessoryService.delete(accessoryid);
		return redirect(ACCESSORY_PULBIC);
	}
}
