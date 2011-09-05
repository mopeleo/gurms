<@c.html title="用户操作日志列表">
	<form id="mainForm" name="mainForm" action="${base}/syslog/operatelist" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>用户ID:</td>
                            <td><input type="text" name="filter_EQS_userid" value="${EQS_userid}"></td>
                            <td>操作日期:</td>
                            <td><@c.calendar id="filter_EQS_operatedate" default="${EQS_operatedate}"/></td>
                            <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                        </tr>
                    </table>
                </div>
            </fieldset>
        </div><!--search end -->

		
	    <div class="contect">
	        <div class="table1">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr class="tr1">
						<td>用户ID</td>
						<td>操作日期</td>
						<td>操作时间</td>
						<td>操作类型</td>
						<td>操作对象</td>
						<td>操作对象ID</td>
					</tr>
					<#list result.result as log>
						<tr onclick="checklist(this)">
							<td>${log.userid}</td>
							<td>${log.operatedate}</td>
							<td>${log.operatetime}</td>
							<td>${log.operatetype}</td>
							<td>${log.operatetable}</td>
							<td>${log.recordid}</td>
						</tr>
					</#list>
					<@c.filltable rows=result.result?size cols=6 />
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
            </div>
        </div>
			
	</form>
</@c.html>
