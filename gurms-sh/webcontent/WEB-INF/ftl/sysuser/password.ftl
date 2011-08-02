<@c.html title="用户信息">
	<script type="text/javascript">
		$(document).ready(function(){
			$.get('${base}/validscript',
				  {className:'org.gurms.entity.system.SysUser',formId:'ajaxform',props:'userid,loginpassword,confirmpassword'},
				  function(data){
					$("head").append(data); //alert(data);
				  }
			);
		});
		
		function afterReturn(result, status){
			new Dialog(result['returnmsg']).show();
		}
		
	</script> 

	<form method="post" id="ajaxform" action="${base}/sysuser/password">
        <div class="messagelist">
            <div class="title_bg">
                <span>密码修改</span>
            </div>

            <div class="messagetable">
                <table>
                    <tr>
                        <td class="tdwidth2">原密码</td>
                        <td>
	                        <input type="hidden" name="userid" value="${session_user.userid}"/>
	                        <input type="password" name="oldpassword" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">新密码</td>
                        <td><input type="password" name="loginpassword" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">确认密码</td>
                        <td><input type="password" name="confirmpassword" /></td>
                    </tr>
                </table>
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
