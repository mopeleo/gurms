<@c.html title="角色列表">
	<form id="mainForm" name="mainForm" action="${base}/sysrole/privates" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>角色名称:</td>
                    <td><input type="text" name="filter_EQ_rolename" value="${EQ_rolename}"></td>
                    <td><input type="button" onclick="search()" class="button" value="查询"></td>
                </tr>
            </table>
		</@c.searchdiv>

		
	    <div class="contect">
	    	<#assign titles=["角色名称","角色状态","生效日期","失效日期"]>
	    	<#assign props=["rolename","rolestatus","startdate","enddate"]>
	    	<@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as role>
					<tr onclick="clickrow(this)">
						<td>${role_index+1}</td>
						<td>${role.rolename}</td>
						<td><@c.dictdesc dicttype="0002" dictcode="${role.rolestatus}" /></td>
						<td>${role.startdate}</td>
						<td>${role.enddate}</td>
						<input type="hidden" id="roleid" value="${role.roleid}" />
					</tr>
				</#list>
	    	</@c.listtable>
			
        	<@c.buttons params="roleid" />
        </div>
			
	</form>
</@c.html>
