package ${project}.service<#if model?exists>.${model}</#if>;

import java.util.List;
import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import ${project}.entity<#if model?exists>.${model}</#if>.${entity};

public interface ${entity}Service{

	public List<${entity}> query(Map<String, Object> request);
	
	public PageResult<${entity}> query(Map<String, Object> request, PageRequest page);
	
	public ${entity} get(String id);
	
	public PageResult<${entity}> save(${entity} obj);

	public PageResult<${entity}> delete(String id);
}
