<@c.html title="用户信息">

	<form method="post" id="ajaxform" action="${base}/sysuser/setConfig">
        <div class="messagelist">
            <div class="title_bg">
                <span>个人设置</span>
            </div>

	        <div class="messagedivblock">
	            <div class="messagetable">
		        	<table>
		        	<tr><td>
					<@c.tree id="sysmenuids" type=1 node=session_menu actual="${(result.fastmenu)??}" checkable=true />
		        	</td></tr>
		        	</table>
	            </div><!--2 end --> 
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
        </div>
	</form>  

</@c.html>
