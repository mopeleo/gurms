<@c.html title="机构信息">
<script type="text/javascript">	
	function afterReturn(result, status){
		new Dialog(result['returnmsg']).show();
//		alert(result['returnmsg']);
	}
	
	$(document).ready(function(){
		$.get('${base}/validscript',
			  {className:'org.gurms.entity.system.SysOrg',formId:'ajaxSubmitForm'},
			  function(data){
				$("head").append(data); //alert(data);
			  }
		);
	})
</script>
	<form method="post" id="ajaxSubmitForm" action="${base}/sysorg/ajaxSave">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

		<#if result?exists>
            <div class="messagetable">
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
		                <td><@c.dynamictree id="parentorg.orgid" ajaxurl="${base}/sysorg/ajaxOrgTree" display="${(result.parentorg.shortname)!!}" actual="${(result.parentorg.orgid)!!}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构状态</td>
                        <td>
        					<@c.select id="orgstatus" default="${result.orgstatus}" dicttype="${statics['org.gurms.common.config.GlobalParam'].DICT_EFFECT}"/>
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
            </div><!--2 end -->                    
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="submiturl('ajaxSubmitForm','${base}/sysorg/delete')" value="删除" />
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
		                <td><@c.dynamictree id="parentorg.orgid" ajaxurl="${base}/sysorg/ajaxOrgTree" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构状态</td>
						<td><@c.select id="orgstatus" dicttype="${statics['org.gurms.common.config.GlobalParam'].DICT_EFFECT}"/></td>
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
