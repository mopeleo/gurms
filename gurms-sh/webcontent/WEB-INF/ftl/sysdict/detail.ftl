<@c.html title="字典信息">
	<form method="post" id="mainForm" action="${base}/sysdict/save">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

		<#if result?exists>
            <div class="messagetable">
                <table>
                    <tr>
                        <td class="tdwidth2">字典类别</td>
                        <td><input type="text" name="dicttype" value="${result.dicttype}" readonly="true" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">字典代码</td>
                        <td><input type="text" name="dictcode" value="${result.dictcode}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">字典值</td>
                        <td><input type="text" name="dictvalue" value="${result.dictvalue}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">显示顺序</td>
                        <td><input type="text" name="dictorder" value="${result.dictorder}" /></td>
                    </tr>
                </table>
            </div><!--2 end -->                    
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="submiturl('mainForm','${base}/sysdict/delete')" value="删除" />
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
                        <td class="tdwidth2">字典类别</td>
                        <td><input type="text" name="dicttype" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">字典代码</td>
                        <td><input type="text" name="dictcode" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">字典值</td>
                        <td><input type="text" name="dictvalue" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">显示顺序</td>
                        <td><input type="text" name="dictorder" /></td>
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
