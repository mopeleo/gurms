<@c.html title="角色列表">
	<form id="mainForm" name="mainForm" action="${base}/sysrole/list" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>角色ID:</td>
                            <td><input type="text" name="filter_EQ_roleid" value="${EQ_roleid}"></td>
                            <td>角色名称:</td>
                            <td><input type="text" name="filter_EQ_rolename" value="${EQ_rolename}"></td>
                            <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                        </tr>
                    </table>
                </div>
            </fieldset>
        </div><!--search end -->

		
	    <div class="contect">
	        <div class="table1">
				<table cellpadding="0">
					<tr class="tr1">
						<th>序号</th>
						<th>角色名称</th>
						<th>角色状态</th>
						<th>生效日期</th>
						<th>失效日期</th>
					</tr>
					<#list result.result as role>
						<tr onclick="checklist(this)">
							<td id="index">${role_index+1}</td>
							<td id="roleid"><a href="${base}/sysrole/detail?roleid=${role.roleid}" >${role.rolename}</a></td>
							<td id="rolestatus"><@c.dictdesc dicttype="0002" dictcode="${role.rolestatus}" /></td>
							<td id="startdate">${role.startdate}</td>
							<td id="enddate">${role.enddate}</td>
						</tr>
					</#list>
					<@c.filltable rows=result.result?size cols=5 />
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
                <div class="pager">
                    <input type="button" class="button" value="新增" onclick="forward('${base}/sysrole/detail')"/>
                </div>
            </div>
        </div>
			
	</form>
</@c.html>
