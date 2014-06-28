<#macro html title="">  
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


<#macro searchdiv>  
    <div class="search">
        <fieldset>
            <legend>查询条件</legend>
            <div class="search_table">
	            <#nested/>
            </div>
        </fieldset>
    </div><!--search end -->
</#macro> 


<#macro bottomdiv params="">
    <div class="page_kz">
    	<#include "common/page.ftl" />
    	
	    <div class="pager">
			<@c.buttons params=params>
			    <#nested/>			
			</@c.buttons>
	    </div>
    </div>
</#macro>


<#macro pagebuttons>
    <div class="page_kz">
    	<#include "common/page.ftl" />
    </div>
</#macro>


<#macro buttons params="">
	<#if frameid?exists>
		<script type="text/javascript">
			function addObj(frameid){
				if(_R.size() == 0){
					new Dialog("请选中要添加的数据!").show();
					return false;
				}
				var parentctx = $(window.parent.document);
				var setobjid = $("#set_" + frameid, parentctx).val();
				var getprops = $("#get_" + frameid, parentctx).val();

				var sets = setobjid.split(",");
				var gets = getprops.split(",");
				
				for(var i = 0; i < sets.length; i++){
					var setval = $("#" + sets[i], parentctx).val();
					var getval = _R.get(gets[i]);
					if(setval == ''){
						setval = getval;
					}else if(setval.indexOf(getval) == -1){
						setval = setval + "," + getval;
					}
					
					$("#" + sets[i], parentctx).val(setval);
				}
			}
		</script>
		<input name="frameid" type="hidden" value="${frameid}">
		<input type="button" class="button" value="添加" onclick="addObj('${frameid}')">
	<#else>
		<#list button as menu>
			<input type="button" class="button" value="${menu.menuname}" onclick="<#if menu.confirmed == "1">confirmDialog(buttonforward,<#else>buttonforward(</#if>{urlstring:'${base}/${menu.menuurl}',optname:'${menu.menuname}',isajax:'${menu.ajaxmode}',ischeck:'${menu.checked}'<#if params!= "">,keys:'${params}'</#if>})"/>
	    </#list>
	</#if>  
    <#nested/>    
</#macro> 


<#-- 列表表单  titles:表单中的标题,props:表单中每个单元格的属性ID,rows:表单有多少行-->
<#macro listtable titles props id="chooselist" rows=0>
	<script type="text/javascript">
		$(document).ready(function(){
			var outdiv = false;
			$("#${id}").mouseout(function(){
				outdiv = true;
			});
			$("#${id}").mouseover(function(){
				outdiv = false;
			});
			$("#${id}").blur(function(){
				if(outdiv){
					$(this).removeClass().addClass("displayNone");
				}
			});
		});
		
	</script>
	  
    <div class="table1">	        
	    <div id="${id}" class="displayNone">
	    	<ul>
	    		<#list titles as title >
	            	<li><input type="checkbox" onclick="changecol('${props[title_index]}')" checked="true"/>${title}</li>
	            </#list>
	        </ul>
	    </div>
	    
    	<div class="zoomdiv">
			<table id="listtable" cellpadding="0">
				<tr class="tr1">
					<th class="tdwidth1" id="_index">序号<span class="checktitle"><img onclick="showlist('${id}')" src="${base}/resources/img/checktitle.png" onmouseover="this.src='${base}/resources/img/checktitle1.png'" onmouseout="this.src='${base}/resources/img/checktitle.png'" /></span></th>
		    		<#list titles as title >
		            	<th id="${props[title_index]}">${title}</th>
		            </#list>
				</tr>
	        	<#nested/>
				<@c.filltable rows=rows cols=titles?size+1 />
			</table>
		</div>
	</div>
</#macro> 


<#-- 填充表单  rows:表单有多少行,cols:表单有多少列-->
<#macro filltable rows cols>
	<#assign pagerows=statics["org.gurms.common.config.GlobalConfig"].MIN_PAGESIZE>
	<#if result.pageSize?exists>
		<#assign pagerows=result.pageSize>
	</#if>
	<#if rows lt pagerows>
		<#list 1..(pagerows-rows) as x>
			<tr onclick="clickrow(this)">
				<#list 1..cols as y>
					<td></td>
				</#list>
			</tr>
		</#list>
	</#if>
</#macro>


<#-- 效验JS代码 classname:要效验的实体对象类名,formid:对象处于的表单ID,props:要效验的属性-->
<#macro validscript classname formid props="">  
	<script type="text/javascript">	
		$(document).ready(function(){
			$.get('${base}/'+_CONSTANT.VALID_URL,
				  {className:'${classname}',formId:'${formid}',props:'${props}'},
				  function(data){
					$("head").append(data); //alert(data);
				  }
			);
		})
	</script>
</#macro> 


<#-- ajax调用执行后调用的代码 forwardurl:执行成功后跳转的页面-->
<#macro afterreturn forward>  
    <script type="text/javascript">
    	function afterReturn(result, status){
    		var flag = result['success'];
    		if(typeof(flag) == 'boolean' && flag){
				new Dialog(result['returnmsg'],{fresh:true,forwardurl:'${forward}'}).show();
    		}else{
				new Dialog(result['returnmsg']).show();
    		}
    	}                
    </script>
</#macro> 


<#macro queryinput id queryfunc value="" >
	<input type="text" id="dis_${id}" name="dis_${id}" value="${value}" readonly="readonly">
	<span class="deleted" onclick="cleardata('${id}')"></span><span class="query" onclick="${queryfunc}"></span>
	<input type="hidden" id="${id}" name="${id}" value="${value}">
	<#nested/>
</#macro>


<#macro selectdiv id dicttype default="" multi=false> 
	<script type="text/javascript">
		$(document).ready(function(){
			var outdiv = false;
			$("#ul_${id}").mouseout(function(){
				outdiv = true;
			});
			$("#ul_${id}").mouseover(function(){
				outdiv = false;
			});
			$("#ul_${id}").blur(function(){
				if(outdiv){
					$(this).removeClass().addClass("displayNone");
				}
			});
			<#if multi>
				var alldisval = "";
				$("#ul_${id}").find("input:checked").each(function(){
					alldisval += $(this).closest("li").text() + ",";
				});
				
				if(alldisval != ""){
					alldisval = alldisval.substring(0, alldisval.length-1);
					document.getElementById("dis_${id}").value = alldisval;
				}
			<#else>
				var val = $("#${id}").val();
				var alldisval = $("#ul_${id}").find("input[value='" + val + "']").closest("li").text();
				document.getElementById("dis_${id}").value = alldisval;
			</#if>
			
		});
		
	</script>

	<div class="select_div" id="selectDivId">
	 	<input type="text" class="select_input" id="dis_${id}" name="dis_${id}" readonly="readonly"/>
	 	<input type="hidden" id="${id}" name="${id}" value="${default}" />
	 	<span class="xiala_tu" onclick="showselectdiv(this)"></span>
	  	<div id="ul_${id}" class="displayNone">
		  	<ul>
				<#if multi>
					<#list context_dict[dicttype] as dict>
						<li>
							<input type="checkbox" onclick="multiselect(this)" value="${dict.dictcode}" <#if default?contains(dict.dictcode)>checked</#if> >${dict.dictcode} - ${dict.dictvalue}
						</li>
					</#list>
				<#else>
					<#list context_dict[dicttype] as dict>
						<li onclick="singleselect(this)">
							<input type="hidden" value="${dict.dictcode}">${dict.dictcode} - ${dict.dictvalue}
						</li>
					</#list>
				</#if>
		    </ul>
	    </div>
	</div>                    
</#macro> 


<#macro select id options key value default="" nullable=false> 
	<#assign keySource = "$"+"{option.${key}}"> 
	<#assign keyTemplate = keySource?interpret>
	<#assign valueSource = "$"+"{option.${value}}"> 
	<#assign valueTemplate = valueSource?interpret>
	<#assign judgeSource = "<#if option.${key}==default>selected=selected</#if>"> 
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


<#macro dictdesc dictcode dictitem><#list context_dict[dictcode] as dict><#if dict.dictitem==dictitem>${dict.itemname}<#break></#if></#list></#macro> 


<#macro dict id dictcode default="" nullable=false event="">  
	<select class="selectstyle" name="${id}" id="${id}" ${event}>
		<#if nullable>
			<option value="">-请选择-</option>
		</#if>
		<#list context_dict[dictcode] as dict>
			<option value="${dict.dictitem}" <#if dict.dictitem==default>selected="selected"</#if>>${dict.dictitem} - ${dict.itemname}</option>
		</#list>
	</select>
</#macro> 


<#-- 多重选择控制 left 左边面板的结果集，right 右边面板的结果集-->
<#macro multiselect id left right=[]>  
	<script src="${base}/resources/js/jquery.multiselect2side.js" type="text/javascript"></script>
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
	<input id="${id}" name="${id}" type="text" value="${default}" readonly="true">&nbsp;<img id="_img${id}" src="${base}/resources/img/icon_time.gif"/>
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


<#-- <#macro recmenu menunode><li><span id="${menunode.menuid}" class="<#if menunode.submenus?size != 0>folder<#else>file</#if>">${menunode.menuname}</span><#if menunode.submenus?size != 0><ul><#list menunode.submenus as menu><@c.recmenu menu/></#list></ul></#if></li></#macro> --> 
<#macro recmenu menunode><#assign level=statics["org.gurms.common.config.GlobalConfig"].PRIVILEGE_LEVEL><#assign folder = true><#if menunode.menutype == "2" || menunode.submenus?size == 0 ||(menunode.menutype == "1" && menunode.submenus?size > 0 && level == "1")><#assign folder = false></#if><li><span id="${menunode.menuid}" class="<#if folder>folder<#else>file</#if>">${menunode.menuname}</span><#if folder><ul><#list menunode.submenus as menu><@c.recmenu menu/></#list></ul></#if></li></#macro> 


<#-- 树  id:表单中input的ID,node:动态树为根节点对象,静态树为要查询的节点ID,type:动态树为请求url,静态树为根节点对象类型,actual:隐藏的真实的值,display:页面显示的值,dynamic:是否动态树,checkable:是否有checkbox-->
<#macro tree id type node="" display="" actual="" endnode="" checkable=false readonly=false dynamic=false clickfunc="" input=false>
	<#assign disid="dis_"+id>
	<script type="text/javascript">
	 	$(document).ready(function(){
	 		var treeobj = document.getElementById("__tree_${id}");
		 	$(treeobj).treeview({
		 		<#if checkable>
			 		checkboxid:'${id}',
				 	checkvalue: '${actual}',
				 	readonly: ${readonly?string("true","false")},
	 			</#if>
	 			<#if clickfunc == "">
			 		<#if input>
				 		clickext:function(obj){
				 			document.getElementById("${disid}").value=$(obj).text();
				 			document.getElementById("${id}").value=obj.id;
			 			},
		 			</#if>
		 		<#else>
			 		clickext:${clickfunc},
		 		</#if>
	 			<#if dynamic>
		 			url:'${type}',
		 			data:{root:'${node}'<#if endnode != "">,endnode:'${endnode}'</#if>},
	 			</#if>
				 	checkable: ${checkable?string("true","false")}
		 	});
		});
	</script>
	
	<#if input>
		<input type="text" id="${disid}" name="${disid}" value="${display}" readonly="readonly"/>
		<input type="hidden" id="${id}" name="${id}" value="${actual}" />
	</#if>
	
	<ul id="__tree_${id}" class="filetree">
		<#if !dynamic>
			<#if type==1>
				<@c.recmenu node/>
			<#elseif type==2>
				<@c.recorg node />
			</#if>
		</#if>
	</ul>

</#macro> 


<#-- 弹出树  id:表单中input的ID,node:动态树为根节点对象,静态树为要查询的节点ID,type:动态树为请求url,静态树为根节点对象类型,actual:隐藏的真实的值,display:页面显示的值,dynamic:是否动态树,checkable:是否有checkbox-->
<#macro popuptree id type node="" display="" actual="" endnode="" checkable=false readonly=false dynamic=false clickfunc="" input=true>
	<#assign disid="dis_"+id>
	<script type="text/javascript">
		var _dialog;    
	    function createTree(){
	    	if(_dialog && _dialog != null){
	    	}else{
	    		var treestring = '<ul id="__tree_${id}" class="filetree"><#if type==1><@c.recmenu node/><#elseif type==2><@c.recorg node /></#if></ul>';
	    		
				_dialog = new Dialog(treestring);					
	 			var treeobj = document.getElementById("__tree_${id}");
			 	$(treeobj).treeview({
			 		<#if checkable>
				 		checkboxid:'${id}',
					 	checkvalue: '${actual}',
				 		readonly: ${readonly?string("true","false")},
		 			</#if>
		 			<#if clickfunc == "">
				 		<#if input>
					 		clickext:function(obj){
					 			document.getElementById("${disid}").value=$(obj).text();
					 			document.getElementById("${id}").value=obj.id;
				 			},
			 			</#if>
			 		<#else>
				 		clickext:${clickfunc},
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
	
	<#if input>
		<input type="text" onclick="createTree()" id="${disid}" name="${disid}" value="${display}" readonly="readonly"/>
		<input type="hidden" id="${id}" name="${id}" value="${actual}" />
	</#if>
	
</#macro> 








