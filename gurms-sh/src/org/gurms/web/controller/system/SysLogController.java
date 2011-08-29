package org.gurms.web.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysLogLogin;
import org.gurms.service.system.SysLogService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysLogController extends BaseController {

	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping
	public void loginlist(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<SysLogLogin> result = sysLogService.queryLogin(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
	}
	
	@RequestMapping
	public void operatelist(HttpServletRequest request, PageRequest page, Model model){
		
	}
}
