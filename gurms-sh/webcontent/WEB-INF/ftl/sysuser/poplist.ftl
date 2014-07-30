<@c.html title="用户列表">
	<form id="mainForm" name="mainForm" action="${base}/sysuser/list" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>用户ID:</td>
                    <td><input type="text" name="filter_EQS_userid" value="${EQS_userid}"></td>
                    <td>用户姓名:</td>
                    <td><input type="text" name="filter_LIKES_username" value="${LIKES_username}"></td>
                    <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["用户ID","用户姓名","所属机构","用户角色","登录日期","登录时间","用户状态"]>
	    	<#assign props=["userid","username","sysorg","userrole","logindate","logintime","userstatus"]>
	    	<@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as user>
					<tr onclick="clickrow(this)">
						<td>${user_index+1}</td>
						<td>${user.userid}</a></td>
						<td>${user.username}</td>
						<td>${(user.sysorg.shortname)!}</td>
						<td>
							<#if user.sysroles??>
                        		<#list user.sysroles as role>
                        			${role.rolename},
                        		</#list>
							</#if>
						</td>
						<td>${user.logindate}</td>
						<td>${user.logintime}</td>
						<td><@c.dictdesc dictcode="5" dictitem="${user.userstatus}" /></td>
					</tr>
				</#list>
	    	</@c.listtable>

			
            <@c.pagebuttons />
        </div>
			
	</form>
</@c.html>
