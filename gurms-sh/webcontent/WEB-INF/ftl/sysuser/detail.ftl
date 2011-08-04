<@c.html title="用户信息">
	<form method="post" id="mainForm" action="${base}/sysuser/save">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

		<#if result?exists>
            <div class="messagetable">
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
                        <td class="tdwidth2">用户状态</td>
                        <td><input type="text" name="userstatus" value="${result.userstatus}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所属部门</td>
                        <td><@c.dynamictree id="sysorg.orgid" ajaxurl="${base}/sysorg/ajaxOrgTree" display="${(result.sysorg.shortname)!!}" actual="${(result.sysorg.orgid)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户角色</td>
                        <td><@c.multiselect id="id" left=allroles right=result.sysroles />
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="loginpassword" value="${result.loginpassword}"/>
            </div><!--2 end -->                    
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="submiturl('mainForm','${base}/sysuser/delete')" value="删除" />
							<input type="button" class="button" onclick="history.go(-1)" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>
		<#else>
            <div class="messagetable">
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
                        <td class="tdwidth2">登录密码</td>
                        <td><input type="password" name="loginpassword" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户状态</td>
                        <td><input type="text" name="userstatus" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">所属部门</td>
                        <td><@c.dynamictree id="sysorg.orgid" ajaxurl="${base}/sysorg/ajaxOrgTree" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户角色</td>
                        <td><@c.multiselect id="id" left=allroles /></td>
                    </tr>
                </table>
            </div><!--2 end -->                    
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="history.go(-1)" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>
		</#if>
        </div>
	</form>  

</@c.html>
