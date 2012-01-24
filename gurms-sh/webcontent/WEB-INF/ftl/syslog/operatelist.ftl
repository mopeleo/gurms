<@c.html title="用户操作日志列表">
	<form id="mainForm" name="mainForm" action="${base}/syslog/operatelist" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>用户ID:</td>
                    <td><input type="text" name="filter_EQS_userid" value="${EQS_userid}"></td>
                    <td>操作起始日期:</td>
                    <td><@c.calendar id="filter_GES_operatedate" default="${GES_operatedate}" nextdate="filter_LTS_operatedate"/></td>
                    <td>操作截止日期:</td>
                    <td><@c.calendar id="filter_LTS_operatedate" default="${LTS_operatedate}" predate="filter_GES_operatedate"/></td>
                    <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["用户ID","操作日期","操作时间","操作类型","操作对象","操作对象ID"]>
	    	<#assign props=["userid","operatedate","operatetime","operatetype","operatetable","recordid"]>
	 	    <@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as log>
					<tr onclick="clickrow(this)">
						<td>${log_index+1}</td>
						<td>${log.userid}</td>
						<td>${log.operatedate}</td>
						<td>${log.operatetime}</td>
						<td>${log.operatetype}</td>
						<td>${log.operatetable}</td>
						<td>${log.recordid}</td>
					</tr>
				</#list>
	    	</@c.listtable>

            <@c.bottomdiv/>
        </div>
			
	</form>
</@c.html>
