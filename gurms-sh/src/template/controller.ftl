package ${package}.web.controller<#if model?exists>.${model}</#if>;

@Controller
public class ${model.className}Controller extends BaseController {

	@RequestMapping
	public PageResult<${model.entityName}> save(${model.entityName} obj);

	@RequestMapping
	public PageResult<${model.entityName}> insert(${model.entityName} obj);

	@RequestMapping
	public ${model.entityName} get(String id);
	
	@RequestMapping
	public PageResult<${model.entityName}> query(Map<String, Object> request, PageRequest page);
	
	@RequestMapping
	public PageResult<${model.entityName}> delete(String id);
}
