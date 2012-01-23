<@c.html title="角色信息">
	<@c.validscript classname="org.gurms.entity.system.SysRole" formid="ajaxform" props="rolename" />
	
	<script type="text/javascript">	
		$(document).ready(function(){
			var idx = 0;
			$("#tabtitle").children("li").each(function(){
				$(this).click(function(){
					if($(this).index() != idx){
						$("#tabtitle").children("li").addClass("messageaiout");
						$(this).removeClass().addClass("messageaion");
						$("#tabtitle").parent().children("div").addClass("messagedivnone");
						var clickdiv = $("#tabtitle").parent().children("div").eq($(this).index());
						clickdiv.removeClass().addClass("messagedivblock");
						idx = $(this).index();
					}
				});
			})
		})
	</script>

	<form method="post" id="ajaxform" action="${base}/sysrole/ajaxSave">
        <div class="messagelist">
            <div class="title_bg">
                <span>角色维护</span>
            </div>

            <div class="messagetable">
                <div class="messageall">
			        <ul id="tabtitle">
			            <li class="messageaion">基本信息</li>
			            <li class="messageaiout">角色权限</li>
			        </ul>
			        
			<#if result?exists>
	            <div class="messagedivblock">
	                <table>
	                    <tr>
	                        <td class="tdwidth2">角色名称</td>
	                        <td>
		                        <input type="hidden" name="roleid" value="${result.roleid}" />
		                        <input type="text" name="rolename" id="rolename" value="${result.rolename}" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">生效日期</td>
	                        <td><@c.calendar id="startdate" default="${result.startdate}" nextdate="enddate"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">失效日期</td>
	                        <td><@c.calendar id="enddate" default="${result.enddate}" predate="startdate"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">启用标志</td>
	                        <td><@c.dict id="enable" default="${result.enable}" dictcode="8"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">备注</td>
	                        <td>
	                        	<input type="hidden" name="roletype" value="${result.roletype}" />
	                        	<input type="hidden" name="creator" value="${result.creator}" />
	                        	<input type="text" name="remark" value="${result.remark}" />
	                        </td>
	                    </tr>
	                </table>
	            </div>                    
	        	<#assign listmenus="">
	        	<#assign listmenusname="">
	        	<#if result.sysmenus??>
	        		<#list result.sysmenus as menu>
	        			<#assign listmenus = listmenus + menu.menuid + ",">
	        		</#list>
	        	</#if>
		        <div class="messagedivnone">
		        	<table>
		        	<tr><td>
					<@c.tree id="sysmenuids" type=1 node=session_menu actual=listmenus checkable=true/>
		        	</td></tr>
		        	</table>
		        </div>
		    <#else>
		        <div class="messagedivblock">
	                <table>
	                    <tr>
	                        <td class="tdwidth2">角色名称</td>
	                        <td><input type="text" name="rolename" id="rolename" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">生效日期</td>
	                        <td><@c.calendar id="startdate" nextdate="enddate"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">失效日期</td>
	                        <td><@c.calendar id="enddate" predate="startdate"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">启用标志</td>
	                        <td><@c.dict id="enable" dictcode="8"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">备注</td>
	                        <td>
	                        	<input type="text" name="remark" />
	                        	<input type="hidden" name="roletype" value="1"/>
	                        	<input type="hidden" name="creator" value="${session_user.userid}" />
	                        </td>
	                    </tr>
	                </table>
		        </div>
		        <div class="messagedivnone">
		        	<table>
		        	<tr><td>
					<@c.tree id="sysmenuids" type=1 node=session_menu checkable=true />
		        	</td></tr>
		        	</table>
		        </div>

                <@c.afterreturn forward="${base}/sysrole/privates" />
			</#if>
		        </div>
		    </div>
		  
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<#if result?exists>
								<input type="submit" class="button" value="保存" />
							<#else>
								<input type="button" class="button" onclick="ajaxsubmiturl('ajaxform','${base}/sysrole/ajaxInsert')" value="保存" />
							</#if>
							<input type="button" class="button" onclick="forward('${base}/sysrole/privates')" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>

        </div>
	</form>  

</@c.html>
