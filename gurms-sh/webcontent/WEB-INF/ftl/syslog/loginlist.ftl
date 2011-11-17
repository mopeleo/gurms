<@c.html title="用户登录日志列表">
	<form id="mainForm" name="mainForm" action="${base}/syslog/loginlist" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>用户ID:</td>
                    <td><input type="text" name="filter_EQ_userid" value="${EQ_userid}"></td>
                    <td>登录起始日期:</td>
                    <td><@c.calendar id="filter_GE_logindate" default="${GE_logindate}" nextdate="filter_LT_logindate"/></td>
                    <td>登录截止日期:</td>
                    <td><@c.calendar id="filter_LT_logindate" default="${LT_logindate}" predate="filter_GE_logindate"/></td>
                    <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["用户ID","登录日期","登录时间","登录密码","登录IP","是否成功"]>
	    	<#assign props=["userid","logindate","logintime","loginpassword","loginip","success"]>
	 	    <@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as log>
					<tr onclick="clickrow(this)">
						<td>${log_index+1}</td>
						<td>${log.userid}</td>
						<td>${log.logindate}</td>
						<td>${log.logintime}</td>
						<td>${log.loginpassword}</td>
						<td>${log.loginip}</td>
						<td><@c.dictdesc dicttype="0001" dictcode="${log.success}"/></td>
					</tr>
				</#list>
	    	</@c.listtable>
	 
            <@c.bottomdiv/>
        </div>
			
	</form>
</@c.html>
