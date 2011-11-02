<@c.html title="机构信息">
	<@c.validscript classname="org.gurms.entity.system.SysOrg" formid="ajaxform"/>
	<@c.ajaxform action="${base}/sysorg/ajaxSave">
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
                        <td class="tdwidth2">机构联系人</td>
                        <td><input type="text" name="linkman" value="${result.linkman}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">联系电话</td>
                        <td><input type="text" name="linktel" value="${result.linktel}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">传真号码</td>
                        <td><input type="text" name="faxno" value="${result.faxno}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">通信地址</td>
                        <td><input type="text" name="address" value="${result.address}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">邮政编码</td>
                        <td><input type="text" name="postcode" value="${result.postcode}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">电子邮件</td>
                        <td><input type="text" name="email" value="${result.email}" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">上级机构</td>
		                <td><@c.popuptree id="parentorg.orgid" type="${base}/sysorg/ajaxOrgTree" display="${(result.parentorg.shortname)!!}" actual="${(result.parentorg.orgid)!!}" endnode="${(result.orgid)!!}" dynamic=true /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构类型</td>
                        <td>
        					<@c.dict id="orgtype" default="${result.orgtype}" dicttype="0002"/>
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
                        <td class="tdwidth2">传真号码</td>
                        <td><input type="text" name="faxno" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">通信地址</td>
                        <td><input type="text" name="address" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">邮政编码</td>
                        <td><input type="text" name="postcode" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">电子邮件</td>
                        <td><input type="text" name="email" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">上级机构</td>
		                <td><@c.popuptree id="parentorg.orgid" type="${base}/sysorg/ajaxOrgTree" dynamic=true /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">机构类型</td>
						<td><@c.dict id="orgtype" dicttype="0002"/></td>
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

                <@c.afterreturn forward="${base}/sysorg/list" />
            </#if>
            </div>                   
            <div class="messagetable messageOK">
                <table>
                    <tr>
                        <td class="tdwidth2"></td>
                        <td>
							<input type="submit" class="button" value="保存" />
							<input type="button" class="button" onclick="forward('${base}/sysorg/list')" value="返回" />
							<input type="reset" class="button" value="重置"/>
						</td>
					</tr>
				</table>
            </div>
        </div>
	</@c.ajaxform>  

</@c.html>
