<#list tables as table>
	<#assign entity="">
	<#assign position = table.code?index_of("_", 0)>
	<#if position != -1>
		<#assign arr = table.code?split("_")>
		<#list arr as a>
			<#assign entity=entity + a>
		</#list>
	<#else>
		<#assign entity=t.code>
	</#if>

insert into ${table.code} (MENUID, MENUNAME, MENUURL, CHECKED, CONFIRMED, OPENMODE, PARENTMENUID, MENUTYPE, MENUORDER)
values ('200${table_index+1}001', '${table.comment}', '${entity}/list', '0', '0', '0', '200${table_index+1}', '1', 200${table_index+1}001);
insert into ${table.code} (MENUID, MENUNAME, MENUURL, CHECKED, CONFIRMED, OPENMODE, PARENTMENUID, MENUTYPE, MENUORDER)
values ('200${table_index+1}001001', '新增', '${entity}/detail', '0', '0', '2', '200${table_index+1}001', '2', 1);
insert into ${table.code} (MENUID, MENUNAME, MENUURL, CHECKED, CONFIRMED, OPENMODE, PARENTMENUID, MENUTYPE, MENUORDER)
values ('200${table_index+1}001003', '修改', '${entity}/detail', '1', '0', '2', '200${table_index+1}001', '2', 3);
insert into ${table.code} (MENUID, MENUNAME, MENUURL, CHECKED, CONFIRMED, OPENMODE, PARENTMENUID, MENUTYPE, MENUORDER)
values ('200${table_index+1}001005', '删除', '${entity}/ajaxDelete', '1', '1', '1', '200${table_index+1}001', '2', 5);
</#list>