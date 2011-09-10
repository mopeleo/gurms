package org.gurms.web.controller;

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
	
	protected PageResult processException(Exception e){
		logger.warn(e.getMessage(), e);
		PageResult page = new PageResult();
		page.setSuccess(false);
		page.setReturnmsg(e.getMessage());
		return page;
	}
}
