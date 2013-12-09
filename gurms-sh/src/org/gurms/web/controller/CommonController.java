package org.gurms.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.ValidCodeGenerator;
import org.gurms.common.validate.GurmsValid.FilterType;
import org.gurms.common.validate.GurmsValidator;
import org.gurms.web.MediaTypes;
import org.gurms.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController extends BaseController {

	// 生成验证码
	@RequestMapping
	public void validcode(HttpSession session, HttpServletResponse response) {
		try {
			response.setContentType(MediaTypes.TYPE_IMG);
			String code = ValidCodeGenerator.generate(response.getOutputStream());
//			System.out.println("-----------"+code+"---------------");
			session.setAttribute(WebConstants.S_KEY_VALIDCODE, code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 生成前段效验的JS代码
	@RequestMapping
	public void validscript(HttpServletRequest request, HttpServletResponse response){
		response.setContentType(MediaTypes.TEXT_PLAIN);
//		String js = "<script>function test(){alert("+className+");}test();</script>";
		String className = request.getParameter("className");
		String formId = request.getParameter("formId");
		String prop = request.getParameter("props");
		String filter = request.getParameter("filter");
		FilterType filterType = FilterType.INCLUDE;
		if(StringUtils.isNotBlank(filter)){
			filterType = Enum.valueOf(FilterType.class, filter.toUpperCase());
		}
		String[] props = null;
		if(StringUtils.isNotBlank(prop)){
			props = StringUtils.split(prop, GlobalParam.STRING_SEPARATOR);
		}
		String js = GurmsValidator.script(className, formId, props, filterType);
		try {
			response.getOutputStream().write(js.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
