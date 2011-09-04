<@c.html title="用户登录日志列表">
	<form id="mainForm" name="mainForm" action="${base}/syslog/loginlist" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>用户ID:</td>
                            <td><input type="text" name="filter_EQS_userid" value="${EQS_userid}"></td>
                            <td>登录日期:</td>
                            <td><@c.calendar id="filter_EQS_logindate" default="${EQS_logindate}"/></td>
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
						<td>登录日期</td>
						<td>登录时间</td>
						<td>登录密码</td>
						<td>登录IP</td>
						<td>是否成功</td>
					</tr>
					<#list result.result as log>
						<tr>
							<td>${log.userid}</td>
							<td>${log.logindate}</td>
							<td>${log.logintime}</td>
							<td>${log.loginpassword}</td>
							<td>${log.loginip}</td>
							<td><@c.dictdesc dicttype="0003" dictcode="${log.success}"/></td>
						</tr>
					</#list>
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
            </div>
        </div>
			
	</form>
</@c.html>
