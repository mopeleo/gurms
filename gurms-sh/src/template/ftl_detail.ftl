<${'@'}c.html title="详细信息">
	<${'@'}c.validscript classname="${project}.entity<#if model?exists>.${model}</#if>.${entity}" formid="ajaxform"/>
	<form method="post" id="ajaxform" action="${'$'}{base}/${entity?lower_case}/ajaxSave">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

            <div class="messagetable">
			<${'#'}if result?exists>
                <table>
					<#list table.columns as column>
                    <tr>
                        <td class="tdwidth2">${column.comment}</td>
                        <td><input type="text" name="${column.code}" id="${column.code}" value="${'$'}{result.${column.code}}" /></td>
                    </tr>
					</#list>
                </table>
			<${'#'}else>
                <table>
					<#list table.columns as column>
                    <tr>
                        <td class="tdwidth2">${column.comment}</td>
                        <td><input type="text" name="${column.code}" id="${column.code}"/></td>
                    </tr>
					</#list>
                </table>

                <${'@'}c.afterreturn forward="${'$'}{base}/${entity?lower_case}/list" />
            </${'#'}if>
            </div>                   
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="forward('${'$'}{base}/${entity?lower_case}/list')" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>
        </div>
	</form>  

</${'@'}c.html>
