package org.gurms.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.ValidateCodeGenerater;
import org.gurms.common.validate.GurmsValidator;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController extends BaseController {

	@ResponseBody
	public void ajaxValid(){
		
	}

	// 生成验证码
	@RequestMapping("/validatecode")
	public void genValidateCodeImg(HttpSession session, HttpServletResponse response) {
		try {
			response.setContentType(ServletUtil.IMG_TYPE);
			String code = ValidateCodeGenerater.generate(response.getOutputStream());
			session.setAttribute(WebConstants.S_KEY_VALIDCODE, code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 生成前段效验的JS代码
	@RequestMapping("/validscript")
	public void getValidScript(HttpServletRequest request, HttpServletResponse response){
		response.setContentType(ServletUtil.TEXT_TYPE);
//		String js = "<script>function test(){alert("+className+");}test();</script>";
		String className = request.getParameter("className");
		String formId = request.getParameter("formId");
		String prop = request.getParameter("props");
		String[] props = null;
		if(StringUtils.isNotBlank(prop)){
			props = StringUtils.split(prop, GlobalParam.STRING_SEPARATOR);
		}
		String js = GurmsValidator.script(className, formId, props);
		try {
			response.getOutputStream().write(js.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping
	public void welcome(){}
}
