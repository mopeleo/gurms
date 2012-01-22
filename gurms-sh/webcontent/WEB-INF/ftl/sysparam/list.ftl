<@c.html title="参数信息">
	<form method="post" id="ajaxform" action="${base}/sysparam/ajaxSave">
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
	                        <td>
	                    	<#if param.paramext.disptype = "1">
	                        	<input type="hidden" name="paramid" value="${param.paramid}" />
	                        	<#if param.paramext.paramlength &lt; 1 || param.paramext.paramlength &gt; 32>
	                        		<input type="text" name="paramvalue" value="${param.paramvalue}" maxlength="32"/>
	                        	<#else>
	                        		<input type="text" name="paramvalue" value="${param.paramvalue}" maxlength="${param.paramext.paramlength}"/>
	                        	</#if>
	                    	<#elseif param.paramext.disptype = "2">
	                        	<input type="hidden" name="paramid" value="${param.paramid}" />
	                        	<@c.dict id="paramvalue" dicttype="${param.paramext.dictcode}" default="${param.paramvalue}"/>
	                    	<#else>
	                        	${param.paramvalue}
	                    	</#if>
	                    	<#if param.paramext.tail?exists>${param.paramext.paramtail}</#if>
	                    	</td>
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
