package ${project}.web.controller<#if model?exists>.${model}</#if>;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import ${project}.entity<#if model?exists>.${model}</#if>.${entity};
<#if (table.keys?size > 1) >
import ${project}.entity<#if model?exists>.${model}</#if>.${entity}Id;
</#if>
import ${project}.service<#if model?exists>.${model}</#if>.${entity}Service;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

<#macro type datatype precision><#if datatype?contains("CHAR")>String<#elseif datatype=="INT">Integer<#elseif precision != "">Double<#else>Long</#if></#macro>
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
	public void detail(<#list table.keys as column><@type datatype=column.datatype precision=column.precision /> ${column.code}, </#list>Model model){
	<#if (table.keys?size > 1) >
		${entity}Id id = new ${entity}Id();
		<#list table.keys as column>
		id.set${column.code?cap_first}(${column.code});
		</#list>
		if(!id.isNull()){
			${entity} entity = ${service}.getById(id);
			model.addAttribute(WebConstants.KEY_RESULT, entity);
		}
	<#else>
		if(${table.keys[0].code} != null){
			${entity} entity = ${service}.getById(${table.keys[0].code});
			model.addAttribute(WebConstants.KEY_RESULT, entity);
		}
	</#if>
	}
	
	@RequestMapping
	public String save(HttpServletRequest request, ${entity} entity){
		${service}.save(entity);		
		return redirect(${entity?upper_case}_LIST);
	}
	
	@RequestMapping
	public String delete(<#list table.keys as column><@type datatype=column.datatype precision=column.precision /> ${column.code}<#if (column_index+1)!=table.keys?size>, </#if></#list>){
	<#if (table.keys?size > 1) >
		${entity}Id id = new ${entity}Id();
		<#list table.keys as column>
		id.set${column.code?cap_first}(${column.code});
		</#list>
		${service}.deleteById(id);
	<#else>
		${service}.deleteById(<#list table.keys as column>${column.code}<#if (column_index+1)!=table.keys?size>, </#if></#list>);
	</#if>
		return redirect(${entity?upper_case}_LIST);
	}

	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(${entity} entity){
		PageResult page = null;
		try{
			page = ${service}.save(entity);
		}catch(Exception e){
			page = processException(e, "保存数据出错:"+e.getMessage());
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(<#list table.keys as column><@type datatype=column.datatype precision=column.precision /> ${column.code}<#if (column_index+1)!=table.keys?size>, </#if></#list>){
		PageResult page = null;
		try{
		<#if (table.keys?size > 1) >
			${entity}Id id = new ${entity}Id();
			<#list table.keys as column>
			id.set${column.code?cap_first}(${column.code});
			</#list>
			page = ${service}.deleteById(id);
		<#else>
			page = ${service}.deleteById(<#list table.keys as column>${column.code}<#if (column_index+1)!=table.keys?size>, </#if></#list>);
		</#if>
		}catch(Exception e){
			page = processException(e, "删除数据出错:"+e.getMessage());
		}
		return page;
	}
}
