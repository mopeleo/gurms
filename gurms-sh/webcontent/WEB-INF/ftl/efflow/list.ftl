<@c.html title="列表信息">
	<script type="text/javascript">
		function toAdd(params){
			dPopWindow(params.urlstring);
		}
		
		function toEdit(params){
			var urlstring = params.urlstring;			
			if(params.ischeck == '1'){
				if(_R.size() == 0){
					dAlert("请选中要" + params.optname + "的数据!");
					return false;
				}
			
				if(params.keys && params.keys.length > 0){
					urlstring += "?1=" + new Date().getTime();
					var keys = params.keys.split(",");
					for(var i = 0; i < keys.length; i++){
						var val = _R.get(keys[i]);
						if(val){
							urlstring += '&' + keys[i] + '=' + val;
						}
					}
				}
			}
			
			dPopWindow(urlstring);
		}
		
	</script>
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
