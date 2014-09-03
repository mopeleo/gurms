<@c.html title="详细信息">
	<@c.validscript classname="org.gurms.entity.ef.EfFlow" formid="ajaxform"/>
	<form method="post" id="ajaxform" action="${base}/efflow/ajaxSave">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

            <div class="messagetable">
			<#if result?exists>
                <table>
                    <tr>
                        <td class="tdwidth2">流程名</td>
                        <td><input type="text" name="flowname" id="flowname" value="${result.flowname}" />
                        <input type="hidden" name="flowid" id="flowid" value="${result.flowid}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">启用标志</td>
                        <td><@c.dict id="flowstatus" default="${result.flowstatus}" dictcode="8"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">说明</td>
                        <td><input type="text" name="remark" id="remark" value="${result.remark}" />
                        <input type="hidden" name="userid" id="userid" value="${result.userid}" /></td>
                    </tr>
                </table>
			<#else>
                <table>
                    <tr>
                        <td class="tdwidth2">流程名</td>
                        <td><input type="text" name="flowname" id="flowname"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">启用标志</td>
                        <td><@c.dict id="flowstatus" dictcode="8"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">说明</td>
                        <td><textarea id="remark" name="remark"></textarea></td>
                    </tr>
                </table>

                <@c.afterreturn forward="${base}/efflow/list" />
            </#if>
            </div>                   
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="forward('${base}/efflow/list')" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>
        </div>
	</form>  

</@c.html>
