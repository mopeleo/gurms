<#macro length column><#if column.length?exists>{rule: 'Length', maximum: ${column.length}, msg: '${column.name}不能超过${column.length}位'}</#if></#macro>
<#macro presence column><#if column.mandatory?exists && column.mandatory=="1">,{rule: 'Presence', msg: '${column.name}不能为空'}</#if></#macro>
<#macro number column><#if column.datatype=="INT" || column.precision != "">,{rule: 'Numericality', msg: '${column.name}只能为数字'}</#if></#macro>

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
${project}.entity<#if model?exists>.${model}</#if>.${entity}.${column.code}=[<@length column=column/><@presence column=column/><@number column=column/>]
	</#list>
	
</#list>