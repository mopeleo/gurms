<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>  
    <#include "common/meta.ftl" />
    <title>${title?html}</title>  
    
	<script type="text/javascript">
	
		totalPageSize();
		
		function leftchange(leftindex,leftsize){
			for(var i = 0; i < leftsize; i++){
				var id = "left_"+i;
				var id_a = "left_a_"+i;
				var id_b = "left_b_"+i;
				if(i == leftindex){
					document.getElementById(id).className = "leftblock";
					document.getElementById(id_a).className = "topmenuLefton";
					document.getElementById(id_b).className = "topmenuRighton";
				}else{
				    document.getElementById(id).className = "leftnone";
					document.getElementById(id_a).className = "topmenuLeftout";
					document.getElementById(id_b).className = "topmenuRightout";
				}
			}
		}
		
		function middlechange(obj){
			var ctx=$(obj).parents("ul");
			$(".leftdaohang_topon", ctx).removeClass("leftdaohang_topon").addClass("leftdaohang_top");
			$(".leftdaohang_contecton", ctx).removeClass("leftdaohang_contecton").addClass("leftdaohang_contect");
			$(".leftdaohang_bottomon", ctx).removeClass("leftdaohang_bottomon").addClass("leftdaohang_bottom");
			$(".middleblock", ctx).removeClass("middleblock").addClass("middlenone");

			$(obj).removeClass("leftdaohang_contect").addClass("leftdaohang_contecton");
			$(obj).prev().removeClass("leftdaohang_top").addClass("leftdaohang_topon");
			$(obj).next().removeClass("leftdaohang_bottom").addClass("leftdaohang_bottomon");
			$(obj).siblings("div .secondmenu").find(".middlenone").removeClass("middlenone").addClass("middleblock");
		}

		function folderChange(folderid){
			var clz = document.getElementById(folderid).className;
			if(clz == "menuliblock"){
				document.getElementById(folderid).className = 'menulinone';
			}else{
				document.getElementById(folderid).className = 'menuliblock';
			}
		}

		var windowCount = 1;
		var maxWindow = 10;
		function openwindow(obj, divid, divtitle, url){

			$("div.secondmenu a").removeClass("menuon").removeClass("menulion");
			if($(obj).closest("ul").hasClass("menuliblock")){
				$(obj).addClass("menulion");
			}else if($(obj).closest("ul").hasClass("menublock")){
				$(obj).addClass("menuon");			
			}

			if($("#dynamicIframe #" + divid + "_div").length == 1){
				$("#dynamicIframe div").hide();
				$("#dynamicIframe #" + divid + "_div").show();
				if($("#dynamicTitle #" + divid + "_li:not(:hidden)").length == 1){
					if($("#dynamicTitle #" + divid + "_li div").eq(0).hasClass("changebodylefton")){
						$("#dynamicIframe #" + divid + "_div").children().eq(0).attr("src", url);
					}
				}
	
				$("#dynamicTitle .changebodylefton").removeClass().addClass("changebodyleftout");
				$("#dynamicTitle .changebodymiddleon").removeClass().addClass("changebodymiddleout");
				$("#dynamicTitle .changebodyrighton").removeClass().addClass("changebodyrightout");

				$("#dynamicTitle #" + divid + "_li div").eq(0).removeClass().addClass("changebodylefton");
				$("#dynamicTitle #" + divid + "_li div").eq(1).removeClass().addClass("changebodymiddleon");
				$("#dynamicTitle #" + divid + "_li div").eq(2).removeClass().addClass("changebodyrighton");
				$("#dynamicTitle #" + divid + "_li").show();
			}else{
				if(windowCount == maxWindow){
					$("#dynamicTitle li").eq(1).remove();
					$("#dynamicIframe div").eq(1).remove();
					windowCount--;
				}
				$("#dynamicIframe div").hide();
				$("#dynamicIframe").append('<div id="' + divid + '_div" class="right_all"><iframe class="iframestyle" src="' + url + '" name="rightiframe" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe></div>');
	
				$("#dynamicTitle .changebodylefton").removeClass().addClass("changebodyleftout");
				$("#dynamicTitle .changebodymiddleon").removeClass().addClass("changebodymiddleout");
				$("#dynamicTitle .changebodyrighton").removeClass().addClass("changebodyrightout");
				$("#dynamicTitle").append('<li id="' + divid + '_li"><div class="changebodylefton"/><div class="changebodymiddleon"  onclick="changewindow(' + divid + ')">' + divtitle + '</div><div class="changebodyrighton" onclick="closewindow(' + divid + ')" title="关闭当前窗口"/></li>');
	
				windowCount++;
			}
		}
		
		function closewindow(divid){
			var currTitle;
			var currFrame;
			if(!divid){
				currTitle = $("#dynamicTitle div.changebodylefton").parent();
				currFrame = $("#dynamicIframe div:not(:hidden)");
			}else{
				currTitle = $("#dynamicTitle #" + divid + "_li");
				currFrame = $("#dynamicIframe #" + divid + "_div");
			}
			
			currTitle.hide();
			currFrame.hide();
			
			var lastli = $("#dynamicTitle li:not(:hidden)").last();
			var lastliid = lastli.attr("id");
			var frameid = lastliid.split("_")[0] + "_div";
			lastli.children().eq(0).removeClass().addClass("changebodylefton");
			lastli.children().eq(1).removeClass().addClass("changebodymiddleon");
			lastli.children().eq(2).removeClass().addClass("changebodyrighton");
			$("#dynamicIframe #" + frameid).show();
		}
		
		function changewindow(divid){
			$("#dynamicIframe div").hide();
			$("#dynamicIframe #" + divid + "_div").show();

			$("#dynamicTitle .changebodylefton").removeClass().addClass("changebodyleftout");
			$("#dynamicTitle .changebodymiddleon").removeClass().addClass("changebodymiddleout");
			$("#dynamicTitle .changebodyrighton").removeClass().addClass("changebodyrightout");

			$("#dynamicTitle #" + divid + "_li div").eq(0).removeClass().addClass("changebodylefton");
			$("#dynamicTitle #" + divid + "_li div").eq(1).removeClass().addClass("changebodymiddleon");
			$("#dynamicTitle #" + divid + "_li div").eq(2).removeClass().addClass("changebodyrighton");
		}

		function hiddenleft(){
			 $("#lefttd").toggle();
			 $("#topmiddle").toggle();
			 if($("#lefttu").hasClass("left2")){
				 $("#lefttu").removeClass("left2").addClass("left");
			 }
			 else{
				 $("#lefttu").removeClass("left").addClass("left2");
			 }
			 if($("#topright").hasClass("right2")){
				$("#topright").removeClass("right2").addClass("right");	 
			 }else{
				$("#topright").removeClass("right").addClass("right2");
			 }
		}

	</script>
</head>  
  
<body>

<div id="main">
	<div id="head">
    	<div class="logo"><img src="${base}/resources/img/logo.jpg" width="462" height="50" /></div>
        <div class="logo_r">
        	<div class="logo_rt">
            	<a href="${base}/">首页</a><span>|</span>
            	<a href="#">桌面快捷</a><span>|</span>
            	<a onclick="openwindow(this,'1003009','密码修改','${base}/sysuser/password')" target="rightiframe">修改密码</a><span>|</span>
            	<a href="${base}/syslogin/logout">注销</a><span>|</span>
            	<a href="#" onclick="window.close();">退出</a>
            </div>
            
            <div class="clear"></div>
            <div class="logo_rb">
            	<ul id="systemcontrol">
                    <#assign leftsize = session_menu.submenus?size>	                                  	  
                    <#assign leftindex = 0>
                    <#if session_fastmenu?exists>
                		<#assign leftsize = leftsize + 1>
                		<li onclick="leftchange(${leftindex},${leftsize})">
                			<div id="left_a_${leftindex}" class="topmenuLefton"></div>
                			<div id="left_b_${leftindex}" class="topmenuRighton">快捷</div>
                			<!-- <a id="left_a_${leftindex}" class="onbg" href="#">快捷</a> -->
                		</li>
                		<#assign leftindex = leftindex + 1>
                    </#if>	                                  	  
            		<#list session_menu.submenus as menu>
                		<li onclick="leftchange(${leftindex},${leftsize})">
                			<#if leftindex=0>
	                			<div id="left_a_${leftindex}" class="topmenuLefton"></div>
	                			<div id="left_b_${leftindex}" class="topmenuRighton">${menu.menuname}</div>
                			<#else>
	                			<div id="left_a_${leftindex}" class="topmenuLeftout"></div>
	                			<div id="left_b_${leftindex}" class="topmenuRightout">${menu.menuname}</div>
                			</#if>
                			<!--
                			<a id="left_a_${leftindex}" class="<#if leftindex=0>onbg<#else>outbg</#if>" href="#">${menu.menuname}</a>
                			-->
                		</li>
                		<#assign leftindex = leftindex + 1>
            		</#list>
                </ul>
            </div>
        </div>
    </div><!--head end -->
    
    <div class="clear"></div>
    <div id="changeweb">
    	<div class="left" id="lefttu" onclick="hiddenleft()"></div>
        <div class="middle" id="topmiddle"></div>
        <div class="right" id="topright">
            <ul id="dynamicTitle" class="weball">
               <li id="index_li">
				  <div class="changebodylefton"></div>
                  <div class="changebodymiddleon" onclick="changewindow('index')">首页</div>
                  <div class="changebodyrighton" title="关闭当前窗口"></div>
               </li>
          </ul>
          <div class="window">
          	  <ul class="window_kz">
                  <li class="closewindow"><a href="#" onclick="closewindow()">关闭</a></li>
              </ul>
          </div>
       </div>
    </div><!--changeweb end -->
    
    <div class="clear"></div>    
    <div id="main_body">
    	<table class="maintable" cellpadding="0" cellspacing="0" border="0">
        	<tr>
            	<td class="lefttdwidth" width="178" id="lefttd">
                    <div id="left_all">
                        <div id="mainleft">
                        
                    	<#assign menuindex = 0>
                        <#if session_fastmenu?exists> 
	                        <div class="leftblock" id="left_${menuindex}">
	                            <div class="left">
	                                <div class="left_top">
	                                    <ul>
	                                  	    <li>
	                                        <div class="leftdaohang_top"></div>
	                                        <div class="leftdaohang_contect" onclick="middlechange(this)">常用</div>
	                                        <div class="leftdaohang_bottom"></div>
	                                        <div class="secondmenu">	
	                                            <div class="middle middleblock">
	                                          	    <#list session_fastmenu as leafs>
	                                                    <#if leafs.menutype="1">	                                              
			                                                <ul class="menublock" id="menu">
			                                                    <li><a class="menuout" target="rightiframe" onclick="openwindow(this,'${leafs.menuid}','${leafs.menuname}','${base}/${leafs.menuurl}')">${leafs.menuname}</a></li>
			                                                </ul>
	                                                    </#if>
	                                                </#list>
	                                            </div>
	                                        </div>
	                                  	    </li>
	                                    </ul>
	                                </div><!--left_top end -->
	                                
	                                <div class="left_botom">
	                                    <div class="pre_jiantou"><a href="#"><img src="${base}/resources/img/shang_sanjiao.gif" width="10" height="6" /></a></div>
	                                    <div class="next_jiantou"><a href="#"><img src="${base}/resources/img/xia_sanjiao.gif" width="10" height="5" /></a></div>
	                                </div>   
	                            </div><!--main_body left end -->
	                            <div class="middle"></div>
	                        </div><!--left4 end -->
                			<#assign menuindex = menuindex + 1>
                        </#if>                     

                        <#list session_menu.submenus as menus> 
	                        <div class="<#if menuindex=0>leftblock<#else>leftnone</#if>" id="left_${menuindex}">
	                            <div class="left">
	                                <div class="left_top">
	                                    <ul>
	                              	        <#list menus.submenus as menu>
	                                  	    <li>
	                                        <div class="leftdaohang_top"></div>
	                                        <div class="leftdaohang_contect" onclick="middlechange(this)">${menu.menuname}</div>
	                                        <div class="leftdaohang_bottom"></div>
	                                        <div class="secondmenu">	
	                                            <div class="middle <#if menu_index=0>middleblock<#else>middlenone</#if>">
	                                          	    <#list menu.submenus as leafs>
	                                          	   	    <#if leafs.menutype="0">
			                                                <div class="menuitem">
			                                                    <p onclick="folderChange()"><span>${leafs.menuname}</span></p>
			                                                    <ul class="menuliblock">
			                                               	        <#list leafs.submenus as leaf>
			                                                            <li><a class="menuliout" target="rightiframe" onclick="openwindow(this,'${leaf.menuid}','${leaf.menuname}','${base}/${leaf.menuurl}')">${leaf.menuname}</a></li>
			                                               	        </#list>
			                                                    </ul>
			                                                </div>
	                                                    </#if>
	                                                     
	                                                    <#if leafs.menutype="1">	                                              
			                                                <ul class="menublock" id="menu">
			                                                    <li><a class="menuout" target="rightiframe" onclick="openwindow(this,'${leafs.menuid}','${leafs.menuname}','${base}/${leafs.menuurl}')">${leafs.menuname}</a></li>
			                                                </ul>
	                                                    </#if>
	                                                </#list>
	                                            </div>
	                                        </div>
	                                  	    </li>
	                                        </#list>	                                  
	                                    </ul>
	                                </div><!--left_top end -->
	                                
	                                <div class="left_botom">
	                                    <div class="pre_jiantou"><a href="#"><img src="${base}/resources/img/shang_sanjiao.gif" width="10" height="6" /></a></div>
	                                    <div class="next_jiantou"><a href="#"><img src="${base}/resources/img/xia_sanjiao.gif" width="10" height="5" /></a></div>
	                                </div>   
	                            </div><!--main_body left end -->
	                            <div class="middle"></div>
	                        </div><!--left4 end -->
                			<#assign menuindex = menuindex + 1>
                        </#list>                     
 
						</div><!--mainleft end -->
                    </div><!--left_all end -->
        		</td>
        		<td id="dynamicIframe">
                    <div class="right_all" id="index_div">
                        <iframe class="iframestyle" src="${base}/welcome" name="rightiframe" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
                    </div>
            	</td>
            </tr>
        </table>
    </div><!--main_body end -->
    
    <div id="foot">
    	<div class="foot_div">
        	<ul>
            	<li>${bundle("0001",session_user.username,session_user.logincount,session_user.logindate+session_user.logintime)}</li>
                <li class="foot_divli3">金证科技股份有限公司  <span>金证信托综合业务平台  </span><span>版本号：3.0</span></li>
            </ul>
        </div>
        <div class="help">
        	<div>
        	    <span><a href="#"><img src="${base}/resources/img/helpBall.gif" width="15" /></a></span>
        	    <span><a href="#"><img src="${base}/resources/img/helpBall_arrow.gif" onmousemove="this.src='${base}/resources/img/helpBall_arrow2.gif'" onmouseout="this.src='${base}/resources/img/helpBall_arrow.gif'" width="9" /></a></span>
        	</div>
        </div>
    </div><!--foot end -->
</div><!--main end -->

</body>  
</html>  
