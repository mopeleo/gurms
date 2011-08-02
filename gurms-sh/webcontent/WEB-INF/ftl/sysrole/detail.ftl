<@c.html title="角色信息">
	<form method="post" id="mainForm" action="${base}/sysrole/save">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

		<#if result?exists>
            <div class="messagetable">
                <table>
                    <tr>
                        <td class="tdwidth2">角色ID</td>
                        <td><input type="text" name="roleid" value="${result.roleid}" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">角色名称</td>
                        <td><input type="text" name="rolename" value="${result.rolename}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">角色状态</td>
                        <td><input type="text" name="rolestatus" value="${result.rolestatus}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">角色类型</td>
                        <td><input type="text" name="roletype" value="${result.roletype}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">生效日期</td>
                        <td><input type="text" name="startdate" value="${result.startdate}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">失效日期</td>
                        <td><input type="text" name="enddate" value="${result.enddate}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户权限</td>
                        <td><input type="text" name="sysmenuids" 
                        	value="<#if result.sysmenus??><#list result.sysmenus as menu>${menu.menuid},</#list></#if>" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">备注</td>
                        <td><input type="text" name="remark" value="${result.remark}" /></td>
                    </tr>
                </table>
            </div><!--2 end -->                    
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="submiturl('mainForm','${base}/sysrole/delete')" value="删除" />
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
                        <td class="tdwidth2">角色名称</td>
                        <td><input type="text" name="rolename" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">角色状态</td>
                        <td><input type="text" name="rolestatus" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">角色类型</td>
                        <td><input type="text" name="roletype" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">生效日期</td>
                        <td><input type="text" name="startdate" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">失效日期</td>
                        <td><input type="text" name="enddate" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">用户权限</td>
                        <td><input type="text" name="sysmenuids" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">备注</td>
                        <td><input type="text" name="remark" /></td>
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
