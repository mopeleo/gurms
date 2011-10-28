<@c.html title="用户信息">
	<script type="text/javascript">	
		$(document).ready(function(){
			$.get('${base}/'+_CONSTANT.VALID_URL,
				  {className:'org.gurms.entity.system.SysUser',formId:'ajaxform',props:'userid,username'},
				  function(data){
					$("head").append(data); //alert(data);
				  }
			);
		})
		
	</script>
	
	<form method="post" id="ajaxform" action="${base}/sysuser/ajaxSave">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

            <div class="messagetable">
			<#if result?exists>
                <table>
                    <tr>
                        <td class="tdwidth2">用户ID</td>
                        <td><input type="text" name="userid" value="${result.userid}" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户姓名</td>
                        <td><input type="text" name="username" value="${result.username}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所属部门</td>
                        <td><@c.popuptree id="sysorg.orgid" type="${base}/sysorg/ajaxOrgTree" display="${(result.sysorg.shortname)!!}" actual="${(result.sysorg.orgid)!!}" dynamic=true/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户角色</td>
                        <td><@c.multiselect id="sysroleids" left=allroles right=result.sysroles />
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="loginpassword" value="${result.loginpassword}"/>
			<#else>
                <table>
                    <tr>
                        <td class="tdwidth2">用户ID</td>
                        <td><input type="text" name="userid" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户姓名</td>
                        <td><input type="text" name="username" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所属部门</td>
                        <td><@c.popuptree id="sysorg.orgid" type="${base}/sysorg/ajaxOrgTree" dynamic=true /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户角色</td>
                        <td><@c.multiselect id="sysroleids" left=allroles /></td>
                    </tr>
                </table>
                
                <script type="text/javascript">
                	function afterReturn(result, status){
						new Dialog(result['returnmsg'],{fresh:true,forwardurl:'${base}/sysuser/list'}).show();
                	}                
                </script>
	        </#if>            
            </div>
                             
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<#if result?exists>
							<input type="submit" class="button" value="保存" />
							<#else>
							<input type="button" class="button" onclick="ajaxsubmiturl('ajaxform','${base}/sysuser/ajaxInsert')" value="保存" />
							</#if>
							<input type="button" class="button" onclick="forward('${base}/sysuser/list')" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>

        </div>
	</form>  

</@c.html>
