<#macro html title>  
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	    <head>  
	        <#include "common/meta.ftl" />
	        <title>${title?html}</title>  
	    </head>  
	  
	    <body class="rightbodybg">
			<div class="rightstyle">
	            <#nested/>
	        </div>
	    </body>  
	  
	</html>  
</#macro> 


<#macro select id dicttype="" default="" nullable=false>  
	<select class="selectstyle" name="${id}" id="${id}">
		<#if nullable>
			<option>-请选择-</option>
		</#if>
		<#if dicttype="">
			<#list context_dict?keys as dict>
				<option value="${dict}" <#if dict=default>selected="selected"</#if>>${dict}</option>
			</#list>
		<#else>
			<#list context_dict[dicttype] as dict>
				<option value="${dict.dictcode}" <#if dict.dictcode=default>selected="selected"</#if>>${dict.dictvalue}</option>
			</#list>
		</#if>
	</select>
</#macro> 


<#macro dict dicttype dictcode>  
	<#list context_dict[dicttype] as dict>
		<#if dict.dictcode=dictcode>${dict.dictvalue}</#if>
	</#list>
</#macro> 


<#macro recOrg orgnode>
	treestring += '<li><span class="<#if orgnode.suborgs?size != 0>folder<#else>file</#if>">${orgnode.shortname}</span>';
	treestring += '<input type="hidden" value="${orgnode.orgid}">';
	<#if orgnode.suborgs?size != 0>
		treestring += '<ul>';
		<#list orgnode.suborgs as org>
			<@c.recOrg org />
		</#list>
		treestring += '</ul>';
	</#if>
	treestring += '</li>';
</#macro> 


<#macro recMenu menunode>
	treestring += '<li><span class="<#if menunode.menutype='0'>folder<#else>file</#if>">${menunode.menuname}</span>';
	<#if menunode.menutype ='0'>
		treestring += '<ul>';
		<#list menunode.submenus as menu>
			<@c.recMenu menu />
		</#list>
		treestring += '</ul>';
	</#if>
	treestring += '</li>';
</#macro> 


<#macro tree id treeData type defaultValue="" defaultDisp="">
	<script type="text/javascript">
		var _dialog;    
	    function createTree(){
	    	if(_dialog && _dialog != null){
	    	}else{
	    		var treestring = '<ul id="__tree" class="filetree">';
			    <#if type=1>
					<@c.recMenu treeData />
				<#elseif type=2>
					<@c.recOrg treeData />
				</#if>
	    		treestring += '</ul>';
				_dialog = new Dialog(treestring);
	    	}
			_dialog.show();
		 	$("#__tree").treeview({
		 		clickext:function(obj){
					$("#displayarea").val($(obj).text());
					$("#${id}").val($(obj).next("input").val());
		 		}
		 	});
	    }	    
	</script>
	<input type="text" id="displayarea" onclick="createTree()" value="${defaultDisp}" readonly="readonly"/>
	<input type="hidden" id="${id}" name="${id}" value="${defaultValue}" />
</#macro> 


<#-- 动态树  id:表单中input的ID,nodeid:树的根节点,actual:隐藏的真实的值,display:页面显示的值-->
<#macro dynamictree id ajaxurl nodeid="" actual="" display="">
	<script type="text/javascript">
		var _dialog_dynamicTree;    
	    function dynamicTree(){
	    	if(_dialog_dynamicTree && _dialog_dynamicTree != null){
	    	}else{
				_dialog_dynamicTree = new Dialog('<ul id="__tree" class="filetree"></ul>');
			 	$("#__tree").treeview({
		 			url:'${ajaxurl}',
		 			data:{root:'${nodeid}'},
			 		clickext:function(obj){
						$("#displayarea").val($(obj).text());
						$("#displayarea").next("input").val($(obj).parent().attr("id"));
		 		}
			 	});
	    	}
			_dialog_dynamicTree.show();			
	    }	    
	</script>
	<input type="text" id="displayarea" onclick="dynamicTree()" value="${display}" readonly="readonly"/>
	<input type="hidden" id="${id}" name="${id}" value="${actual}" />
</#macro> 







