<@c.html title="附件列表">
	<form id="mainForm" name="mainForm" action="${base}/sysaccessory/publics" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>资料名称:</td>
                    <td><input type="text" name="filter_LIKES_accessoryname" value="${LIKES_accessoryname}"></td>
                    <td><input type="button" onclick="search()" class="button" value="查询"></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["资料名称","上传用户","上传日期","资料大小","下载次数"]>
	    	<#assign props=["accessoryname","userid","uploaddate","accessorysize","accessorytype","downtimes"]>
	    	<@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as accessory>
					<tr onclick="clickrow(this)">
						<td>${accessory_index+1}</td>
						<td><a href="${base}/sysaccessory/download?accessoryid=${accessory.accessoryid}">${accessory.accessoryname}</a></td>
						<td>${accessory.userid}</td>
						<td>${accessory.uploaddate}</td>
						<td>${accessory.accessorysize}</td>
						<td>${accessory.downtimes}</td>
					</tr>
				</#list>
	    	</@c.listtable>
			
        	<@c.bottomdiv params="accessoryid" />
        </div>
			
	</form>
</@c.html>
