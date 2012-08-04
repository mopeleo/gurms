package ${project}.web.controller<#if model?exists>.${model}</#if>;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import ${project}.entity<#if model?exists>.${model}</#if>.${entity};
import ${project}.service<#if model?exists>.${model}</#if>.${entity}Service;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

<#assign service = entity?uncap_first + "Service">
@Controller
public class ${entity}Controller extends BaseController {

	public static final String ${entity?upper_case}_LIST = "/${entity?lower_case}/list";

	@Autowired
	private ${entity}Service ${service};	

	@RequestMapping
	public void list(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<${entity}> result = ${service}.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}

	@RequestMapping
	public void detail(String id, Model model){
		if(StringUtils.isNotBlank(id)){
			${entity} entity = ${service}.get(id);
			model.addAttribute(WebConstants.KEY_RESULT, entity);
		}
	}
	
	@RequestMapping
	public String save(HttpServletRequest request, ${entity} entity){
		${service}.save(entity);		
		return redirect(${entity?upper_case}_LIST);
	}
	
	@RequestMapping
	public String delete(String id){
		${service}.delete(id);
		return redirect(${entity?upper_case}_LIST);
	}

	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(${entity} entity){
		PageResult page = null;
		try{
			page = ${service}.save(entity);
		}catch(Exception e){
			page = processException(e, "保存数据出错");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(String id){
		PageResult page = null;
		try{
			page = ${service}.delete(id);
		}catch(Exception e){
			page = processException(e, "删除数据出错");
		}
		return page;
	}
}
