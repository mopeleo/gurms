<@c.html title="机构列表">
	<form id="mainForm" name="mainForm" action="${base}/sysorg/list" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>机构ID:</td>
                            <td><input type="text" name="filter_EQS_orgid" value="${EQS_orgid}"></td>
                            <td>机构简称:</td>
                            <td><input type="text" name="filter_EQS_shortname" value="${EQS_shortname}"></td>
                            <td><input type="button" onclick="search()" class="button" value="查询" /></td>
                        </tr>
                    </table>
                </div>
            </fieldset>
        </div><!--search end -->

		
	    <div class="contect">
	        <div class="table1">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr class="tr1">
						<td>机构简称</td>
						<td>机构全称</td>
						<td>机构负责人</td>
						<td>联系电话</td>
						<td>上级机构</td>
						<td>状态</td>
					</tr>
					<#list result.result as org>
						<tr>
							<td><a href="${base}/sysorg/detail?orgid=${org.orgid}" >${org.shortname}</a></td>
							<td>${org.fullname}</td>
							<td>${org.linkman}</td>
							<td>${org.linktel}</td>
							<td><#if org.parentorg != null>${org.parentorg.shortname}</#if></td>
							<td><@c.dict dicttype="${statics['org.gurms.common.config.GlobalParam'].DICT_EFFECT}" dictcode="${org.orgstatus}"/></td>
						</tr>
					</#list>
				</table>
			</div>
			
            <div class="page_kz">
            	<#include "common/page.ftl" />
                <div class="pager">
                    <input type="button" class="button" value="新增" onclick="forward('${base}/sysorg/detail')"/>
                </div>
            </div>
        </div>
			
	</form>
</@c.html>
