<@c.html title="用户登录日志列表">
	<form id="mainForm" name="mainForm" action="${base}/syslog/loginlist" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>用户ID:</td>
                            <td><input type="text" name="filter_EQ_userid" value="${EQ_userid}"></td>
                            <td>登录日期:</td>
                            <td><@c.calendar id="filter_EQ_logindate" default="${EQ_logindate}"/></td>
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
						<th>登录日期</th>
						<th>登录时间</th>
						<th>登录密码</th>
						<th>登录IP</th>
						<th>是否成功</th>
					</tr>
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
					<@c.filltable rows=result.result?size cols=7 />
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
            </div>
        </div>
			
	</form>
</@c.html>
