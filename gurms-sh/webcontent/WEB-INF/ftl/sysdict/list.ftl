<@c.html title="字典列表">
	<form id="mainForm" name="mainForm" action="${base}/sysdict/list" method="post">
		<@c.searchdiv>
            <table>
                <tr>
                    <td>字典类别:</td>
                    <td><@c.select id="filter_EQ_dicttype" options=context_dicttype key="dicttype" value="dictvalue" default="${EQ_dicttype}" nullable=true /></td>
                    <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                </tr>
            </table>
		</@c.searchdiv>
		
	    <div class="contect">
	    	<#assign titles=["字典类别","字典代码","字典值","显示顺序","说明"]>
	    	<#assign props=["dicttype","dictcode","dictvalue","dictorder","remark"]>
	    	<@c.listtable titles=titles props=props rows=result.result?size>
				<#list result.result as org>
					<tr onclick="clickrow(this)">
						<td>${dict_index+1}</td>
						<td>${dict.dicttype}</td>
						<td>${dict.dictcode}</td>
						<td>${dict.dictvalue}</td>
						<td>${dict.dictorder}</td>
						<td>${dict.remark}</td>
					</tr>
				</#list>
	    	</@c.listtable>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
                <div class="pager">
                    <input type="button" class="button" value="新增" onclick="new Dialog({type:'id',value:'new_dict'},{confirmButton:false}).show()"/>
                </div>
            </div>
        </div>
	</form>

<p style="display:none"><textarea id="new_dict" rows="0" cols="0">
	<form id="mainForm" name="mainForm" action="${base}/sysdict/save" method="post">
        <div class="messagetable">
            <table>
                <tr>
                    <td class="tdwidth2">字典类别</td>
                    <td class="dialogtd"><@c.select id="dicttype" options=context_dicttype key="dicttype" value="dictvalue" nullable=true /></td>
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
                <tr>
                    <td class="tdwidth2">说明</td>
                    <td><input type="text" name="remark" /></td>
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
	</form>
</textarea></p>

</@c.html>
