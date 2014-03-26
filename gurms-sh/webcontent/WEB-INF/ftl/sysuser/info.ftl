<@c.html title="用户信息">
	<@c.validscript classname="org.gurms.entity.system.SysUserInfo" formid="ajaxform"/>
	
	<script type="text/javascript">
		$(document).ready(function(){
				bindFastSearch("_province", "${base}/common/genDictData", { dicttype: 16 });
		})
	</script>
	
	<form method="post" id="ajaxform" action="${base}/sysuser/userinfo">
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
                        <td><@c.dict id="sex" default="${(result.sex)!!}" dictcode="4"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">出生年月</td>
                        <td><@c.calendar id="birthday" default="${(result.birthday)!!}"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所在省份</td>
                        <td>
                        	<input type="text" name="_province" id="_province" value="<@c.dictdesc dictcode="16" dictitem="${(result.province)!!}"/>" />
                        	<input type="hidden" name="province" id="province" value="${(result.province)!!}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所在城市</td>
                        <td><input type="text" name="city" value="${(result.city)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">教育程度</td>
                        <td><@c.dict id="education" default="${(result.education)!!}" dictcode="15"/></td>
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
