package ${project}.service<#if model?exists>.${model}</#if>.impl;

import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import ${project}.entity<#if model?exists>.${model}</#if>.${entity};
import ${project}.service<#if model?exists>.${model}</#if>.${entity}Service;
import ${project}.dao.hibernate<#if model?exists>.${model}</#if>.${entity}Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#assign dao = entity?uncap_first + "Dao">
@Service
@Transactional
public class ${entity}ServiceImpl implements ${entity}Service {

	@Autowired
	private ${entity}Dao ${dao};
	
	public ${entity}Dao get${entity}Dao() {
		return ${dao};
	}

	public void set${entity}Dao(${entity}Dao ${dao}) {
		this.${dao} = ${dao};
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<${entity}> query(Map<String, Object> request, PageRequest page){
		return ${dao}.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}
	
	@Override
	@Transactional(readOnly = true)
	public ${entity} get(String id){
		return ${dao}.get(id);
	}
	
	@Override
	public PageResult<${entity}> save(${entity} entity){
		${dao}.save(entity);
	}

	@Override
	public PageResult<${entity}> delete(String id){
		${dao}.delete(id);
	}
}
