<@c.html title="角色列表">
	<form id="mainForm" name="mainForm" action="${base}/sysrole/publics" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>角色名称:</td>
                    <td><input type="text" name="filter_LIKES_rolename" value="${LIKES_rolename}"></td>
                    <td><input type="button" onclick="search()" class="button" value="查询"></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["角色名称","角色状态","生效日期","失效日期","启用标志"]>
	    	<#assign props=["rolename","rolestatus","startdate","enddate","enable"]>
	    	<@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as role>
					<tr onclick="clickrow(this)">
						<td>${role_index+1}</td>
						<td>${role.rolename}</td>
						<td><@c.dictdesc dictcode="2" dictitem="${role.rolestatus}" /></td>
						<td>${role.startdate}</td>
						<td>${role.enddate}</td>
						<td><@c.dictdesc dictcode="8" dictitem="${role.enable}" /></td>
						<input type="hidden" id="roleid" value="${role.roleid}" />
					</tr>
				</#list>
	    	</@c.listtable>
			
        	<@c.bottomdiv params="roleid" />
        </div>
			
	</form>
</@c.html>
