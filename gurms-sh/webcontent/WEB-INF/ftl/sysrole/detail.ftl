<@c.html title="角色信息">
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

	<form method="post" id="mainForm" action="${base}/sysrole/save">
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
		                        <input type="text" name="rolename" value="${result.rolename}" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">角色状态</td>
	                        <td><@c.dict id="rolestatus" dicttype="0002" default="${result.rolestatus}" nullable=false /></td>
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
	                        <td class="tdwidth2">备注</td>
	                        <td>
	                        	<input type="hidden" name="roletype" value="0"/>
	                        	<input type="hidden" name="creater" value="${result.creater}" />
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
					<@c.tree id="sysmenuids" type=1 node=context_menu actual=listmenus checkable=true/>
		        	</td></tr>
		        	</table>
		        </div>
		    <#else>
		        <div class="messagedivblock">
	                <table>
	                    <tr>
	                        <td class="tdwidth2">角色名称</td>
	                        <td><input type="text" name="rolename" /></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">角色状态</td>
	                        <td><@c.dict id="rolestatus" dicttype="0002" nullable=false /></td>
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
	                        <td class="tdwidth2">备注</td>
	                        <td>
	                        	<input type="text" name="remark" />
	                        	<input type="hidden" name="roletype" value="0"/>
	                        	<input type="hidden" name="creater" value="${session_user.userid}" />
	                        </td>
	                    </tr>
	                </table>
		        </div>
		        <div class="messagedivnone">
		        	<table>
		        	<tr><td>
					<@c.tree id="sysmenuids" type=1 node=context_menu checkable=true />
		        	</td></tr>
		        	</table>
		        </div>
			</#if>
		        </div>
		    </div>
		  
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<#if result?exists>
							<input type="button" class="button" onclick="submiturl('mainForm','${base}/sysrole/delete')" value="删除" />
							</#if>
							<input type="button" class="button" onclick="history.go(-1)" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>

        </div>
	</form>  

</@c.html>
