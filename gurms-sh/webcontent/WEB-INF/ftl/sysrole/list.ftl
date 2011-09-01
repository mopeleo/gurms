<@c.html title="角色列表">
	<form id="mainForm" name="mainForm" action="${base}/sysrole/list" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>角色ID:</td>
                            <td><input type="text" name="filter_EQS_roleid" value="${EQS_roleid}"></td>
                            <td>角色名称:</td>
                            <td><input type="text" name="filter_EQS_rolename" value="${EQS_rolename}"></td>
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
						<td>角色名称</td>
						<td>角色类型</td>
						<td>角色状态</td>
						<td>生效日期</td>
						<td>失效日期</td>
					</tr>
					<#list result.result as role>
						<#if role.roleid != statics["org.gurms.common.config.GlobalParam"].SYSTEM_ROLE>
						<tr>
							<td><a href="${base}/sysrole/detail?roleid=${role.roleid}" >${role.rolename}</a></td>
							<td>${role.roletype}</td>
							<td>${role.rolestatus}</td>
							<td>${role.startdate}</td>
							<td>${role.enddate}</td>
						</tr>
						</#if>
					</#list>
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
                <div class="pager">
                    <input type="button" class="button" value="新增" onclick="forward('${base}/sysrole/detail')"/>
                </div>
            </div>
        </div>
			
	</form>
</@c.html>
