<@c.html title="附件列表">
	<form id="mainForm" name="mainForm" action="${base}/sysaccessory/privates"  method="post">
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
			
        	<@c.bottomdiv params="accessoryid">
        	  <input type="button" class="button" value="上传" onclick="new Dialog({type:'id',value:'new_accessory'},{confirmButton:false}).show()"/>
        	</@c.bottomdiv>
        </div>
			
	</form>

<p style="display:none"><textarea id="new_accessory" rows="0" cols="0">
	<form action="${base}/sysaccessory/multiUpload" method="post" enctype="multipart/form-data">
        <div class="messagetable">
            <table>
                <tr>
                    <td class="tdwidth2">类型</td>
                    <td class="dialogtd"><@c.dict id="status" dictcode="6"/></td>
                </tr>
                <tr>
                    <td class="tdwidth2">附件</td>
                    <td><input type="file" name="filename" class="longinput"></td>
                </tr>
            </table>
        </div>                    
        <div class="messagetable messageOK">
            <table>
                <tr>
                    <td class="tdwidth2"></td>
                    <td>
						<input type="submit" class="button" value="保存" />
						<input type="reset" class="button" value="重置"/>
					</td>
				</tr>
			</table>
        </div>
	</form>
</textarea></p>
	
</@c.html>
