<${'@'}c.html title="列表信息">
	<form id="mainForm" name="mainForm" action="${'$'}{base}/${entity?lower_case}/list" method="post">
		<${'@'}c.searchdiv>
            <table>
                <tr>
					<#list table.keys as key>
                    <td>${key.comment}:</td>
                    <td><input type="text" name="filter_EQS_${key.code}" value="${'$'}{EQS_${key.code}}"></td>
					</#list>
                    <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                </tr>
            </table>
		</${'@'}c.searchdiv>
		
	    <div class="contect">
	    	<${'#'}assign titles=[<#list table.keys as column>"${column.comment}",</#list><#list table.columns as column>"${column.comment}"<#if (column_index+1)!=table.columns?size>,</#if></#list>]>
	    	<${'#'}assign props=[<#list table.keys as column>"${column.code}",</#list><#list table.columns as column>"${column.code}"<#if (column_index+1)!=table.columns?size>,</#if></#list>]>
	 	    <${'@'}c.listtable titles=titles props=props rows=result.result?size>
				<${'#'}list result.result as obj>
					<tr onclick="clickrow(this)">
						<td>${'$'}{obj_index+1}</td>
						<#list table.columns as column>
						<td>${'$'}{obj.${column.code}}</td>
						</#list>
						<#list table.keys as key>
						<input type="hidden" id="${key.code}" name="${key.code}" value="${'$'}{obj.${key.code}}">
						</#list>						
					</tr>
				</${'#'}list>
	    	</${'@'}c.listtable>
	 
            <${'@'}c.bottomdiv params="<#list table.keys as key>${key.code}<#if (key_index+1)!=table.keys?size>,</#if></#list>"/>
        </div>
			
	</form>
</${'@'}c.html>
