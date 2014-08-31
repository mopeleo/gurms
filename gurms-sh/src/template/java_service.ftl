package ${project}.service<#if model?exists>.${model}</#if>;

import java.util.List;
import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import ${project}.entity<#if model?exists>.${model}</#if>.${entity};
<#if (table.keys?size > 1) >
import ${project}.entity<#if model?exists>.${model}</#if>.${entity}Id;
</#if>

<#macro type datatype precision><#if datatype?contains("CHAR")>String<#elseif datatype=="INT">Integer<#elseif precision != "">Double<#else>Long</#if></#macro>
public interface ${entity}Service{

	public List<${entity}> query(Map<String, Object> request);
	
	public PageResult<${entity}> query(Map<String, Object> request, PageRequest page);
	
	public ${entity} getById(<#if (table.keys?size > 1) >${entity}Id id<#else><@type datatype=table.keys[0].datatype precision=table.keys[0].precision /> ${table.keys[0].code}</#if>);
	
	public PageResult<${entity}> save(${entity} obj);

	public PageResult<${entity}> deleteById(<#if (table.keys?size > 1) >${entity}Id id<#else><@type datatype=table.keys[0].datatype precision=table.keys[0].precision /> ${table.keys[0].code}</#if>);
}
