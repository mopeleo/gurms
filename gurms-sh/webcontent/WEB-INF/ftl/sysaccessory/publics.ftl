<@c.html title="附件列表">
	<form id="mainForm" name="mainForm" action="${base}/sysaccessory/publics" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>附件名称:</td>
                    <td><input type="text" name="filter_LIKES_accessoryname" value="${LIKES_accessoryname}"></td>
                    <td><input type="button" onclick="search()" class="button" value="查询"></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["附件名称","上传人","上传日期","附件大小","附件类型","下载次数"]>
	    	<#assign props=["accessoryname","userid","uploaddate","accessorysize","accessorytype","downtimes"]>
	    	<@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as accessory>
					<tr onclick="clickrow(this)">
						<td>${accessory_index+1}</td>
						<td>${accessory.accessoryname}</td>
						<td>${accessory.userid}</td>
						<td>${accessory.uploaddate}</td>
						<td>${accessory.accessorysize}</td>
						<td><@c.dictdesc dictcode="8" dictitem="${accessory.accessorysize}" /></td>
						<td>${accessory.downtimes}</td>
						<input type="hidden" id="accessoryid" value="${accessory.accessoryid}" />
					</tr>
				</#list>
	    	</@c.listtable>
			
        	<@c.bottomdiv params="accessoryid" />
        </div>
			
	</form>
</@c.html>
