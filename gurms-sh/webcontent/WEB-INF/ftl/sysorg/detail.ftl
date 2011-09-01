<@c.html title="机构信息">
<script type="text/javascript">	
	$(document).ready(function(){
		$.get('${base}/validscript',
			  {className:'org.gurms.entity.system.SysOrg',formId:'ajaxform'},
			  function(data){
				$("head").append(data); //alert(data);
			  }
		);
	})
</script>
	<form method="post" id="ajaxform" action="${base}/sysorg/ajaxSave">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

            <div class="messagetable">
			<#if result?exists>
                <table>
                    <tr>
                        <td class="tdwidth2">机构简称<input type="hidden" name="orgid" value="${result.orgid}"  /></td>
                        <td><input type="text" name="shortname" value="${result.shortname}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构全称</td>
                        <td><input type="text" name="fullname" value="${result.fullname}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构负责人</td>
                        <td><input type="text" name="linkman" value="${result.linkman}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">联系电话</td>
                        <td><input type="text" name="linktel" value="${result.linktel}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">上级机构</td>
		                <td><@c.popuptree id="parentorg.orgid" type="${base}/sysorg/ajaxOrgTree" display="${(result.parentorg.shortname)!!}" actual="${(result.parentorg.orgid)!!}" endnode="${(result.orgid)!!}" dynamic=true /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构状态</td>
                        <td>
        					<@c.dict id="orgstatus" default="${result.orgstatus}" dicttype="${statics['org.gurms.common.config.GlobalParam'].DICT_EFFECT}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">排序号</td>
                        <td><input type="text" name="orgorder" value="${result.orgorder}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">备注</td>
                        <td><input type="text" name="remark" value="${result.remark}" /></td>
                    </tr>
                </table>
			<#else>
                <table>
                    <tr>
                        <td class="tdwidth2">机构简称</td>
                        <td><input type="text" name="shortname" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构全称</td>
                        <td><input type="text" name="fullname" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构负责人</td>
                        <td><input type="text" name="linkman"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">联系电话</td>
                        <td><input type="text" name="linktel" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">上级机构</td>
		                <td><@c.popuptree id="parentorg.orgid" type="${base}/sysorg/ajaxOrgTree" dynamic=true /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构状态</td>
						<td><@c.dict id="orgstatus" dicttype="${statics['org.gurms.common.config.GlobalParam'].DICT_EFFECT}"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">排序号</td>
                        <td><input type="text" name="orgorder" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">备注</td>
                        <td><input type="text" name="remark" /></td>
                    </tr>
                </table>
            </#if>
            </div>                   
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<#if result?exists>
							<input type="button" class="button" onclick="submiturl('ajaxform','${base}/sysorg/delete')" value="删除" />
							</#if>
							<input type="button" class="button" onclick="history.go(-1)" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>
        </div>
	</form>  

</@c.html>
