<@c.html title="机构列表">
	<form id="mainForm" name="mainForm" action="${base}/sysorg/list" method="post">
        <div class="search">
            <fieldset>
                <legend>查询条件</legend>
                <div class="search_table">
                    <table>
                        <tr>
                            <td>机构ID:</td>
                            <td><input type="text" name="filter_EQ_orgid" value="${EQ_orgid}"></td>
                            <td>机构简称:</td>
                            <td><input type="text" name="filter_EQ_shortname" value="${EQ_shortname}"></td>
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
						<td>序号</td>
						<td>机构简称</td>
						<td>机构全称</td>
						<td>机构联系人</td>
						<td>联系电话</td>
						<td>联系地址</td>
						<td>状态</td>
						<td>上级机构</td>
					</tr>
					<#list result.result as org>
						<tr onclick="checklist(this)">
							<td>${org_index+1}</td>
							<td><a href="${base}/sysorg/detail?orgid=${org.orgid}" >${org.shortname}</a></td>
							<td>${org.fullname}</td>
							<td>${org.linkman}</td>
							<td>${org.linktel}</td>
							<td>${org.address}</td>
							<td><@c.dictdesc dicttype="0002" dictcode="${org.orgstatus}"/></td>
							<td><#if org.parentorg != null>${org.parentorg.shortname}</#if></td>
						</tr>
					</#list>
					<@c.filltable rows=result.result?size cols=8 />
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
