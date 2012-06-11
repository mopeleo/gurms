package org.gurms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.gurms.common.config.GlobalConfig;
import org.gurms.entity.PageResult;
import org.gurms.web.spring.IntPropertyBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected String redirect(String url) {
		return "redirect:" + url;
	}

	protected String forward(String url) {
		return "forward:" + url;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(int.class, new IntPropertyBinder());
	}
	
	protected PageResult processException(Exception e, String returnmsg){
		logger.warn(returnmsg, e);
		PageResult page = new PageResult();
		page.setSuccess(false);
		page.setReturnmsg(returnmsg);
		return page;
	}
	
	protected String getPath(HttpServletRequest request){
		String path = GlobalConfig.FILE_DIR;
		if(GlobalConfig.FILE_DIR_MODE_RELATIVE.equals(GlobalConfig.FILE_DIR_MODE)){
			// 获取路径
			String ctxPath = request.getSession().getServletContext().getRealPath("/");
			path = ctxPath + path;
		}
		return path;
	}
}
