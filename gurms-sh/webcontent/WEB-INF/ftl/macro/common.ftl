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


<#macro ajaxform action>  
	<form method="post" id="ajaxform" action="${action}">
		<input type="hidden" name="operator" id="operator" value="${session_user.userid}">
        <#nested/>
    </form>
</#macro> 


<#macro filltable rows cols>
	<#assign pagerows=statics["org.gurms.common.config.GlobalConfig"].MIN_PAGESIZE>
	<#if rows lt pagerows>
		<#list 1..(pagerows-rows) as x>
			<tr onclick="checklist(this)">
				<#list 1..cols as y>
					<td>&nbsp;</td>
				</#list>
			</tr>
		</#list>
	</#if>
</#macro>


<#macro validscript classname formid props="">  
	<script type="text/javascript">	
		$(document).ready(function(){
			$.get('${base}/'+VALID_URL,
				  {className:'${classname}',formId:'${formid}',props:'${props}'},
				  function(data){
					$("head").append(data); //alert(data);
				  }
			);
		})
	</script>
</#macro> 


<#macro select id options key value default="" nullable=false> 
	<#assign keySource = "$"+"{option.${key}}"> 
	<#assign keyTemplate = keySource?interpret>
	<#assign valueSource = "$"+"{option.${value}}"> 
	<#assign valueTemplate = valueSource?interpret>
	<#assign judgeSource = "<#if option.${key}=default>selected=selected</#if>"> 
	<#assign judgeTemplate = judgeSource?interpret>
	<select class="selectstyle" name="${id}" id="${id}">
		<#if nullable>
			<option value="">-请选择-</option>
		</#if>
		<#list options as option>
			<option value="<@keyTemplate/>" <@judgeTemplate/>><@keyTemplate/> - <@valueTemplate/></option>
		</#list>
	</select>
</#macro> 


<#macro dictdesc dicttype dictcode>  
	<#list context_dict[dicttype] as dict>
		<#if dict.dictcode=dictcode>${dict.dictvalue}</#if>
	</#list>
</#macro> 


<#macro dict id dicttype default="" nullable=false>  
	<select class="selectstyle" name="${id}" id="${id}">
		<#if nullable>
			<option value="">-请选择-</option>
		</#if>
		<#list context_dict[dicttype] as dict>
			<option value="${dict.dictcode}" <#if dict.dictcode=default>selected="selected"</#if>>${dict.dictcode} - ${dict.dictvalue}</option>
		</#list>
	</select>
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


<#macro calendar id default="" predate="" nextdate="">
	<input id="${id}" name="${id}" type="text" value="${default}" readonly="true">&nbsp;<img id="_img${id}" src="${base}/img/icon_time.gif"/>
	<script type="text/javascript">
		$(function(){ 
		    $('#_img${id}').calendar({
		    	id:'${id}',
		    	btnBar:false,
		    	<#if predate != "">minDate:'#${predate}',</#if>
		    	<#if nextdate != "">maxDate:'#${nextdate}',</#if>
		    	format:'yyyyMMdd',
		    	targetFormat:'yyyyMMdd'
		    }); 
		}); 	
	</script>
</#macro>


<#macro recorg orgnode><li><span id="${orgnode.orgid}" class="<#if orgnode.suborgs?size != 0>folder<#else>file</#if>">${orgnode.shortname}</span><#if orgnode.suborgs?size != 0><ul><#list orgnode.suborgs as org><@c.recorg org /></#list></ul></#if></li></#macro> 


<#macro recmenu menunode><li><span id="${menunode.menuid}" class="<#if menunode.menutype='0'>folder<#else>file</#if>">${menunode.menuname}</span><#if menunode.menutype ='0'><ul><#list menunode.submenus as menu><@c.recmenu menu/></#list></ul></#if></li></#macro> 


<#-- 树  id:表单中input的ID,node:动态树为根节点对象,静态树为要查询的节点ID,type:动态树为请求url,静态树为根节点对象类型,actual:隐藏的真实的值,display:页面显示的值,dynamic:是否动态树,checkable:是否有checkbox-->
<#macro tree id type node="" display="" actual="" endnode="" checkable=false dynamic=false>
	<#assign disid="dis_"+id>
	<script type="text/javascript">
	 	$(document).ready(function(){
		 	$("#__tree").treeview({
		 		<#if checkable>
			 		checkboxid:'${id}',
				 	checkvalue: '${actual}',
		 		<#else>
			 		clickext:function(obj){
			 			document.getElementById("${disid}").value=$(obj).text();
			 			document.getElementById("${id}").value=obj.id;
		 			},
	 			</#if>
	 			<#if dynamic>
		 			url:'${type}',
		 			data:{root:'${node}'<#if endnode != "">,endnode:'${endnode}'</#if>},
	 			</#if>
				 	checkable: ${checkable?string("true","false")}
		 	});
		});
	</script>
	
	<#if !checkable>
		<input type="text" onclick="createTree()" id="${disid}" name="${disid}" value="${display}" readonly="readonly"/>
		<input type="hidden" id="${id}" name="${id}" value="${actual}" />
	</#if>
	
	<ul id="__tree" class="filetree">
		<#if !dynamic>
			<#if type=1>
				<@c.recmenu node/>
			<#elseif type=2>
				<@c.recorg node />
			</#if>
		</#if>
	</ul>

</#macro> 


<#-- 弹出树  id:表单中input的ID,node:动态树为根节点对象,静态树为要查询的节点ID,type:动态树为请求url,静态树为根节点对象类型,actual:隐藏的真实的值,display:页面显示的值,dynamic:是否动态树,checkable:是否有checkbox-->
<#macro popuptree id type node="" display="" actual="" endnode="" checkable=false dynamic=false>
	<#assign disid="dis_"+id>
	<script type="text/javascript">
		var _dialog;    
	    function createTree(){
	    	if(_dialog && _dialog != null){
	    	}else{
	    		var treestring = '<ul id="__tree" class="filetree"><#if type=1><@c.recmenu node/><#elseif type=2><@c.recorg node /></#if></ul>';
	    		
				_dialog = new Dialog(treestring);					
			 	$("#__tree").treeview({
			 		<#if checkable>
				 		checkboxid:'${id}',
					 	checkvalue: '${actual}',
			 		<#else>
				 		clickext:function(obj){
				 			document.getElementById("${disid}").value=$(obj).text();
				 			document.getElementById("${id}").value=obj.id;
			 			},
		 			</#if>
		 			<#if dynamic>
			 			url:'${type}',
		 				data:{root:'${node}'<#if endnode != "">,endnode:'${endnode}'</#if>},
		 			</#if>
					 	checkable: ${checkable?string("true","false")}
			 	});
	    	}
			_dialog.show();
	    }	    
	</script>
	
	<#if !checkable>
		<input type="text" onclick="createTree()" id="${disid}" name="${disid}" value="${display}" readonly="readonly"/>
		<input type="hidden" id="${id}" name="${id}" value="${actual}" />
	</#if>
	
</#macro> 








