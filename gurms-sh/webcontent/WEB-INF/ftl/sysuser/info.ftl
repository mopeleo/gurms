<@c.html title="用户信息">
	<script type="text/javascript">	
		$(document).ready(function(){
			$.get('${base}/'+VALID_URL,
				  {className:'org.gurms.entity.system.SysUser',formId:'ajaxform',props:'userid,username'},
				  function(data){
					$("head").append(data); //alert(data);
				  }
			);
		})
	</script>
	
	<form method="post" id="mainform" action="${base}/sysuser/userinfo">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

            <div class="messagetable">
                <table>
                    <tr>
                        <td class="tdwidth2">用户ID</td>
                        <td><input type="text" name="userid" value="${session_user.userid}" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户姓名</td>
                        <td><input type="text" name="username" value="${session_user.username}" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">手机</td>
                        <td><input type="text" name="mobile" value="${(result.mobile)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">固定电话</td>
                        <td><input type="text" name="linktel" value="${(result.linktel)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">传真号码</td>
                        <td><input type="text" name="faxno" value="${(result.faxno)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">通信地址</td>
                        <td><input type="text" name="address" value="${(result.address)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">邮政编码</td>
                        <td><input type="text" name="postcode" value="${(result.postcode)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">电子邮件</td>
                        <td><input type="text" name="email" value="${(result.email)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">性别</td>
                        <td><input type="text" name="sex" value="${(result.sex)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">出生年月</td>
                        <td><input type="text" name="birthday" value="${(result.birthday)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所在省份</td>
                        <td><input type="text" name="province" value="${(result.province)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所在城市</td>
                        <td><input type="text" name="city" value="${(result.city)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">教育程度</td>
                        <td><input type="text" name="education" value="${(result.education)!!}" /></td>
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

        </div>
	</form>  

</@c.html>
