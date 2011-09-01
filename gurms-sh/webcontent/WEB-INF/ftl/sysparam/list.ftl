<@c.html title="字典信息">
	<form method="post" id="mainForm" action="${base}/sysparam/save">
        <div class="messagelist">
            <div class="title_bg">
                <span>基本信息</span>
            </div>

            <div class="messagetable">
                <table>
				<#list result?chunk(2) as row>
                    <tr>
                    	<#list row as param>
	                        <td class="longtd">${param.paramext.paramtitle}：</td>
	                    	<#if param.paramext.distype = "1">
	                    		<td>
	                        	<input type="hidden" name="paramid" value="${param.paramid}" />
	                        	<input type="text" name="paramvalue" value="${param.paramvalue}" />
	                        	</td>
	                    	<#elseif param.paramext.distype = "2">
	                    		<td>
	                        	<input type="hidden" name="paramid" value="${param.paramid}" />
	                        	<@c.dict id="paramvalue" dicttype="${param.paramext.dicttype}" default="${param.paramvalue}"/>
	                        	</td>
	                    	<#else>
	                        	<td style="text-align:left">${param.paramvalue}</td>
	                    	</#if>
	                    </#list>
                    </tr>
		        </#list>
                </table>
            </div><!--2 end -->                    
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
        </div>
	</form>  

</@c.html>
