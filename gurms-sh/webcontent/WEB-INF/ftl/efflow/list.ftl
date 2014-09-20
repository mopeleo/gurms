<@c.html title="列表信息">
	<form id="mainForm" name="mainForm" action="${base}/efflow/list" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>流程名:</td>
                    <td><input type="text" name="filter_LIKES_flowname" value="${LIKES_flowname}"></td>
                    <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["流程名","流程状态","制定人","说明"]>
	    	<#assign props=["flowname","flowstatus","userid","remark"]>
	 	    <@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as obj>
					<tr onclick="clickrow(this)">
						<td>${obj_index+1}</td>
						<td>${obj.flowname}</td>
						<td><@c.dictdesc dictcode="8" dictitem="${obj.flowstatus}" /></td>
						<td>${obj.userid}</td>
						<td>${obj.remark}</td>
						<input type="hidden" id="flowid" value="${obj.flowid}">
					</tr>
				</#list>
	    	</@c.listtable>
	 
            <@c.bottomdiv params="flowid"/>
        </div>
			
	</form>
</@c.html>
