<@c.html title="角色列表">
	<script type="text/javascript">	
		function testt(){
			alert(_R.size());
			alert(_R.get("index"));
			alert(_R.get("roleid"));
			alert(_R.get("rolestatus"));
		}
	</script>

	<form id="mainForm" name="mainForm" action="${base}/sysrole/publics" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>角色ID:</td>
                            <td><input type="text" name="filter_EQ_roleid" value="${EQ_roleid}"></td>
                            <td>角色名称:</td>
                            <td><input type="text" name="filter_EQ_rolename" value="${EQ_rolename}"></td>
                            <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                        </tr>
                    </table>
                </div>
            </fieldset>
        </div><!--search end -->

		
	    <div class="contect">
	    	<#assign titles=["角色名称","角色状态","生效日期","失效日期"]>
	    	<#assign props=["rolename","rolestatus","startdate","enddate"]>
	    	<@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as role>
					<tr onclick="clickrow(this)">
						<td>${role_index+1}</td>
						<td><a href="${base}/sysrole/detail?roleid=${role.roleid}" >${role.rolename}</a></td>
						<td><@c.dictdesc dicttype="0002" dictcode="${role.rolestatus}" /></td>
						<td>${role.startdate}</td>
						<td>${role.enddate}</td>
					</tr>
				</#list>
	    	</@c.listtable>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
                <div class="pager">
                    <input type="button" class="button" value="新增" onclick="forward('${base}/sysrole/detail')"/>
                    <input type="button" class="button" value="详细" onclick="testt()"/>
                </div>
            </div>
        </div>
			
	</form>
</@c.html>
