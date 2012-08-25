<#list tables as t>
	<#assign entity="">
	<#assign position = t.code?index_of("_", 0)>
	<#if position != -1>
		<#assign model = t.code?substring(0, position)>
		<#assign arr = t.code?split("_")>
		<#list arr as a>
			<#assign entity=entity + a?cap_first>
		</#list>
	<#else>
		<#assign entity=t.code?cap_first>
	</#if>
	<#list t.columns as column>
${project}.entity<#if model?exists>.${model}</#if>.${entity}.${column.code}=[{rule: 'Presence', msg: '${column.name}不能为空'}]
	</#list>
	
</#list>