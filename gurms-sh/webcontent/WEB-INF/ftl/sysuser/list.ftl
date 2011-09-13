<@c.html title="用户列表">
	<form id="mainForm" name="mainForm" action="${base}/sysuser/list" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>用户ID:</td>
                            <td><input type="text" name="filter_EQS_userid" value="${EQS_userid}"></td>
                            <td>用户名:</td>
                            <td><input type="text" name="filter_EQS_username" value="${EQS_username}"></td>
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
						<td>序号</td>
						<td>用户ID</td>
						<td>用户姓名</td>
						<td>所属机构</td>
						<td>用户角色</td>
						<td>登录日期</td>
						<td>登录时间</td>
						<td>用户状态</td>
					</tr>
					<#list result.result as user>
						<tr onclick="checklist(this)">
							<td>${user_index+1}</td>
							<td><a href="${base}/sysuser/detail?userid=${user.userid}" >${user.userid}</a></td>
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
							<td><@c.dictdesc dicttype="0005" dictcode="${user.userstatus}" /></td>
						</tr>
					</#list>
					<@c.filltable rows=result.result?size cols=8 />
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
                <div class="pager">
                    <input type="button" class="button" value="新增" onclick="forward('${base}/sysuser/detail')"/>
                    <!-- <input type="button" class="button" value="密码重置" onclick="forward('${base}/sysuser/detail')"/> -->
                </div>
            </div>
        </div>
			
	</form>
</@c.html>
