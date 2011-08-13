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


<#macro recorg orgnode><li><span id="${orgnode.orgid}" class="<#if orgnode.suborgs?size != 0>folder<#else>file</#if>">${orgnode.shortname}</span><#if orgnode.suborgs?size != 0><ul><#list orgnode.suborgs as org><@c.recorg org /></#list></ul></#if></li></#macro> 


<#macro recmenu menunode><li><span id="${menunode.menuid}" class="<#if menunode.menutype='0'>folder<#else>file</#if>">${menunode.menuname}</span><#if menunode.menutype ='0'><ul><#list menunode.submenus as menu><@c.recmenu menu/></#list></ul></#if></li></#macro> 


<#-- 静态树  id:表单中input的ID,nodeid:树的根节点,actual:隐藏的真实的值,display:页面显示的值,popup:是否弹出,checkable:是否有checkbox-->
<#macro tree id node type actual="" display="" checkable=false popup=true>
	<#if popup>
	
		<script type="text/javascript">
			var _dialog;    
		    function createTree(){
		    	if(_dialog && _dialog != null){
		    	}else{
		    		var treestring = '<ul id="__tree" class="filetree"><#if type=1><@c.recmenu node/><#elseif type=2><@c.recorg node /></#if></ul>';
		    		
					_dialog = new Dialog(treestring);					
				 	$("#__tree").treeview({
				 		checkboxId:'${id}',
				 		checkable: ${checkable?string("true","false")},
				 		clickext:function(obj){
							$("#displayarea").val($(obj).text());
							$("#displayarea").next("input").val(obj.id);
				 		}
				 	});
		    	}
				_dialog.show();
		    }	    
		</script>
		<input type="text" id="displayarea" onclick="createTree()" value="${display}" readonly="readonly"/>
		<input type="text" id="${id}" name="${id}" value="${actual}" />
	
	<#else>
	
		<script type="text/javascript">
		 	$(document).ready(function(){
			 	$("#__tree").treeview({
			 		checkboxId:'${id}',
			 		checkable: ${checkable?string("true","false")}
			 	});
			});
		</script>
		<ul id="__tree" class="filetree"><#if type=1><@c.recmenu node/><#elseif type=2><@c.recorg node /></#if></ul>

	</#if>	
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
						$("#displayarea").next("input").val(obj.id);
		 		}
			 	});
	    	}
			_dialog_dynamicTree.show();			
	    }	    
	</script>
	<input type="text" id="displayarea" onclick="dynamicTree()" value="${display}" readonly="readonly"/>
	<input type="hidden" id="${id}" name="${id}" value="${actual}" />
</#macro> 


<#-- 多重选择控制 left 左边面板的结果集，right 右边面板的结果集-->
<#macro multiselect id left right=[]>  
	<script src="${base}/js/jquery.multiselect2side.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#${id}').multiselect2side({
				search: '过滤:',
				selectedPosition: 'right',
				moveOptions: false,
				labelsx: '',
				labeldx: '已选择:'
			});
		});
	</script>
	<select name="${id}" id="${id}" multiple="multiple">
		<#list left as obj>
			<option value="${obj.roleid}" <#if right?seq_contains(obj)>selected="selected"</#if> >${obj.rolename}</option>
		</#list>
	</select>
</#macro> 




