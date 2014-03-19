<@c.html title="用户登录日志列表">
	<script type="text/javascript">
		$(document).ready(function(){
				bindFastSearch("filter_EQS_userid", "${base}/common/genDictData", { dicttype: 11 });
		})
	</script>

	<form id="mainForm" name="mainForm" action="${base}/syslog/loginlist" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>用户ID:</td>
                    <td>
                    	<input type="text" id="filter_EQS_userid" name="filter_EQS_userid" value="${EQS_userid}" onclick="showlist('baiduselect')">
                    </td>
                    <td>登录起始日期:</td>
                    <td><@c.calendar id="filter_GES_logindate" default="${GES_logindate}" nextdate="filter_LTS_logindate"/></td>
                    <td>登录截止日期:</td>
                    <td><@c.calendar id="filter_LTS_logindate" default="${LTS_logindate}" predate="filter_GES_logindate"/></td>
                    <td>
                    	<input type="button" onclick="search()" class="button" value="查询" />
                    	<input type="button" onclick="submiturl('mainForm','${base}/syslog/pdf')" class="button" value="PDF" />
                    	<input type="button" onclick="submiturl('mainForm','${base}/syslog/excel')" class="button" value="EXCEL" />
                    	<input type="button" onclick="saveAsExcel('listtable')" class="button" value="导出" />
                    </td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=bundle("5000").split(",")>
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
						<td><@c.dictdesc dictcode="1" dictitem="${log.success}"/></td>
					</tr>
				</#list>
	    	</@c.listtable>
	 
            <@c.bottomdiv/>
        </div>
			
	</form>
</@c.html>
