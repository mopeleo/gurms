<@c.html title="机构列表">
   <script type="text/javascript">
   		function ajaxGet(obj){
   			$.get('${base}/sysorg/ajaxGet',{orgid:obj.id},function(data){
   				var result = data['result'][0];
   				for(var prop in result){
   					var jqueryObj = $('#'+prop);
   					// alert(prop + ":["+result[prop]+"]");
   					if(jqueryObj.length > 0){
   						jqueryObj.val(result[prop]);
   					}
   				}
   			});
   			
   			var parentli = $(obj).closest("ul").parent("li");
   			if(parentli.length > 0){
   				var parentspan = parentli.find("span").get(0);
	 			document.getElementById("parentorg.orgid").value=$(parentspan).text();
   			}else{
	 			document.getElementById("parentorg.orgid").value='-';
   			}
 			
 			_R.clear();
 			_R.put("orgid", obj.id);
   		}
   </script>
   
   <div class="messageLeft">
   	  <div class="leftDivborder">
	   	  <div class="borderdiv">
		      <@c.tree id="orgtree" type="${base}/sysorg/ajaxOrgTree" dynamic=true clickfunc="ajaxGet"/>
		  </div>
      </div>
   </div><!--messageLeft end -->
   
   <div class="messageRight">
   	  <div class="rightDivborder">
   	  <div class="borderdiv">
        <@c.afterreturn forward="${base}/sysorg/list" />
		<form method="post" id="ajaxform" action="${base}/sysorg/detail">
	        <div class="messagelist">
	            <div class="title_bg">
	                <span>基本信息</span>
	            </div>
	
	            <div class="messagetable">
	                <table>
	                    <tr>
	                        <td class="tdwidth2">机构简称</td>
	                        <td>
	                        	<input type="hidden" id="orgid" name="orgid" />
	                        	<input type="text" id="shortname" name="shortname" readonly="readonly"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">机构全称</td>
	                        <td><input type="text" name="fullname" id="fullname" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">机构负责人</td>
	                        <td><input type="text" name="linkman" id="linkman" readonly="readonly"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">联系电话</td>
	                        <td><input type="text" name="linktel" id="linktel" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">传真号码</td>
	                        <td><input type="text" name="faxno" id="faxno" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">通信地址</td>
	                        <td><input type="text" name="address" id="address" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">邮政编码</td>
	                        <td><input type="text" name="postcode" id="postcode" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">电子邮件</td>
	                        <td><input type="text" name="email" id="email" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">上级机构</td>
			                <td><input type="text" name="parentorg.orgid" id="parentorg.orgid" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">机构类型</td>
							<td><input id="orgtype" name="orgtype" type="text" readonly="readonly"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">排序号</td>
	                        <td><input type="text" name="orgorder" id="orgorder" readonly="readonly" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">备注</td>
	                        <td><input type="text" name="remark" id="remark" readonly="readonly" /></td>
	                    </tr>
	                </table>
	
	            </div>                   
                
	            <div class="messagetable messageOK">
	                <table>
	                    <tr>
	                        <td class="tdwidth2"></td>
	                        <td>
	                        	<@c.buttons params="orgid"/>
							</td>
						</tr>
					</table>
	            </div>
	        </div>
		</form>  
	  </div>
      </div>
   </div><!--messageRight end -->

</@c.html>
