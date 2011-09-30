<@c.html title="用户操作日志列表">
	<form id="mainForm" name="mainForm" action="${base}/syslog/operatelist" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>用户ID:</td>
                            <td><input type="text" name="filter_EQ_userid" value="${EQ_userid}"></td>
                            <td>操作日期:</td>
                            <td><@c.calendar id="filter_EQ_operatedate" default="${EQ_operatedate}"/></td>
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
						<th>用户ID</th>
						<th>操作日期</th>
						<th>操作时间</th>
						<th>操作类型</th>
						<th>操作对象</th>
						<th>操作对象ID</th>
					</tr>
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
					<@c.filltable rows=result.result?size cols=7 />
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
            </div>
        </div>
			
	</form>
</@c.html>
