<@c.html title="详细信息">
	<script type="text/javascript">
		function addLink(){
			var link = '<tr><td class="tdwidth2">环节名</td><td><input type="text" name="linkname" id="linkname"/><input type="button" class="button" onclick="delLink(this)" value="删除" /></td></tr>';
			link += '<tr><td class="tdwidth2">环节值</td><td><input type="text" name="linkvalue" id="linkvalue"/>';
			link += '<input type="hidden" name="linkflag" id="linkflag" value="1"/>';
			link += '</td></tr>';
			$("#middleLink").append(link);
		}
		
		function delLink(o){
			var tr = $(o).parents("tr");
			var nexttr = tr.next().remove();
			tr.remove();
		}
	</script>
	<@c.validscript classname="org.gurms.entity.ef.EfFlow" formid="ajaxform"/>
	<form method="post" id="mainform" action="${base}/efflow/save">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

			<#if result?exists>
            <div class="messagetable">
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
            </div>                   
            <div class="title_bg">
                <span>开始环节</span>
            </div>
            <div class="messagetable">
                <table>
                <#list result.links as obj>
                	<#if obj.linkflag == "0">
                    <tr>
                        <td class="tdwidth2">环节名</td>
                        <td><input type="text" name="linkname" id="linkname" value="${obj.linkname}"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">环节值</td>
                        <td><input type="text" name="linkvalue" id="linkvalue" value="${obj.linkvalue}"/>
                        	<input type="hidden" name="linkflag" id="linkflag" value="0"/>
                        </td>
                    </tr>
                    </#if>
                </#list>
                </table>
            </div>                   
            <div class="title_bg">
                <span>中间环节</span><span><input type="button" class="button" onclick="addLink()" value="添加" /></span>
            </div>
            <div class="messagetable">
                <table id="middleLink">
                <#list result.links as obj>
                	<#if obj.linkflag == "1">
                    <tr>
                        <td class="tdwidth2">环节名</td>
                        <td><input type="text" name="linkname" id="linkname" value="${obj.linkname}"/><input type="button" class="button" onclick="delLink(this)" value="删除" /></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">环节值</td>
                        <td><input type="text" name="linkvalue" id="linkvalue" value="${obj.linkvalue}"/>
                        	<input type="hidden" name="linkflag" id="linkflag" value="1"/>
                        </td>
                    </tr>
                    </#if>
                </#list>
                </table>
            </div>                   
            <div class="title_bg">
                <span>结束环节</span>
            </div>
            <div class="messagetable">
                <table>
                <#list result.links as obj>
                	<#if obj.linkflag == "2">
                    <tr>
                        <td class="tdwidth2">环节名</td>
                        <td><input type="text" name="linkname" id="linkname" value="${obj.linkname}"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">环节值</td>
                        <td><input type="text" name="linkvalue" id="linkvalue" value="${obj.linkvalue}"/>
                        	<input type="hidden" name="linkflag" id="linkflag" value="2"/>
                        </td>
                    </tr>
                    </#if>
                </#list>
                </table>
            </div>                   
			<#else>
            <div class="messagetable">
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
            </div>                   
            <div class="title_bg">
                <span>开始环节</span>
            </div>
            <div class="messagetable">
                <table>
                    <tr>
                        <td class="tdwidth2">环节名</td>
                        <td><input type="text" name="linkname" id="linkname"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">环节值</td>
                        <td><input type="text" name="linkvalue" id="linkvalue"/><input type="hidden" name="linkflag" id="linkflag" value="0"/></td>
                    </tr>
                </table>
            </div>                   
            <div class="title_bg">
                <span>中间环节</span><span><input type="button" class="button" onclick="addLink()" value="添加" /></span>
            </div>
            <div class="messagetable">
                <table id="middleLink">
                </table>
            </div>                   
            <div class="title_bg">
                <span>结束环节</span>
            </div>
            <div class="messagetable">
                <table>
                    <tr>
                        <td class="tdwidth2">环节名</td>
                        <td><input type="text" name="linkname" id="linkname"/></td>
                    </tr>
                    <tr>
                        <td class="tdwidth2">环节值</td>
                        <td><input type="text" name="linkvalue" id="linkvalue"/><input type="hidden" name="linkflag" id="linkflag" value="2"/></td>
                    </tr>
                </table>
            </div>                   
            </#if>



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
