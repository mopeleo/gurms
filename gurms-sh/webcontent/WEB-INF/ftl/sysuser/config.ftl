<@c.html title="用户信息">
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

	<form method="post" id="ajaxform" action="${base}/sysuser/setConfig">
        <div class="messagelist">
            <div class="title_bg">
                <span>个人设置</span>
            </div>

            <div class="messagetable">
                <div class="messageall">
			        <ul id="tabtitle">
			            <li class="messageaion">基本信息</li>
			            <li class="messageaiout">快捷菜单</li>
			        </ul>
	            <div class="messagedivblock">
	                <table>
	                    <tr>
	                        <td class="tdwidth2">每页条数</td>
	                        <td>
		                        <input type="hidden" name="userid" value="${session_user.userid}"/>
		                        <input type="text" name="pagesize" />
	                        </td>
	                    </tr>
	                </table>
	            </div>                    
		        <div class="messagedivnone">
		        	<table>
		        	<tr><td>
					<@c.tree id="sysmenuids" type=1 node=session_menu actual="${(result.fastmenu)??}" checkable=true />
		        	</td></tr>
		        	</table>
		        </div>
            </div><!--2 end -->                    
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
        </div>
	</form>  

</@c.html>
