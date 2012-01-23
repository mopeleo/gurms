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

    <div class="messagelist">
        <div class="title_bg">
            <span>角色信息</span>
        </div>

        <div class="messagetable">
            <div class="messageall">
		        <ul id="tabtitle">
		            <li class="messageaion">基本信息</li>
		            <li class="messageaiout">角色权限</li>
		        </ul>
		        
	            <div class="messagedivblock">
	                <table>
	                    <tr>
	                        <td class="tdwidth2">角色名称:</td>
	                        <td>
		                        ${result.rolename}<input type="hidden" name="roleid" value="${result.roleid}" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">角色状态:</td>
	                        <td><@c.dictdesc dictcode="2" dictitem="${result.rolestatus}"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">生效日期:</td>
	                        <td>${result.startdate}</td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">失效日期:</td>
	                        <td>${result.enddate}</td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">启用标志:</td>
	                        <td><@c.dictdesc dictcode="8" dictitem="${result.enable}"/></td>
	                    </tr>
	                    <tr>
	                        <td class="tdwidth2">备注:</td>
	                        <td>${result.remark}
	                        	<input type="hidden" name="roletype" value="${result.roletype}" />
	                        	<input type="hidden" name="creator" value="${result.creator}" />
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
					<@c.tree id="sysmenuids" type=1 node=context_menu actual=listmenus checkable=true readonly=true/>
		        	</td></tr>
		        	</table>
		        </div>
	        </div>
	    </div>
	  
        <div class="messagetable messageOK">
            <table>
                <tr>
                    <td class="tdwidth2"></td>
                    <td>
						<input type="button" class="button" onclick="history.go(-1)" value="返回" />
					</td>
				</tr>
			</table>
        </div>

    </div>

</@c.html>
