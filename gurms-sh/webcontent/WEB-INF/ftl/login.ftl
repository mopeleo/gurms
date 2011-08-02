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
				if(i == leftindex){
					document.getElementById(id).className = "leftblock";
					document.getElementById(id_a).className = "onbg";
				}else{
				    document.getElementById(id).className = "leftnone";
					document.getElementById(id_a).className = "outbg";
				}
			}
		}
		
		function middlechange(middleindex,middlesize){
			for(var i = 0; i < middlesize; i++){
				var id = "middle_"+i;
				var id_top = "middle_top_"+i;
				var id_contect = "middle_contect_"+i;
				var id_bottom = "middle_bottom_"+i;
				if(i == middleindex){
					document.getElementById(id).className = "middle middleblock";
					document.getElementById(id_top).className = "leftdaohang_topon";
					document.getElementById(id_contect).className = "leftdaohang_contecton";
					document.getElementById(id_bottom).className = "leftdaohang_bottomon";
				}else{
				    document.getElementById(id).className = "middle middlenone";
					document.getElementById(id_top).className = "leftdaohang_top";
					document.getElementById(id_contect).className = "leftdaohang_contect";
					document.getElementById(id_bottom).className = "leftdaohang_bottom";
				}
			}
		}

		function folderChange(folderid){
			var clz = document.getElementById(folderid).className;
			if(clz == "menuliblock"){
				document.getElementById(folderid).className = 'menulinone';
			}else{
				document.getElementById(folderid).className = 'menuliblock';
			}
		}

		function menuChange(obj){
			$("div.secondmenu a").removeClass("menuon");
			$(obj).addClass("menuon");
		}
	</script>
</head>  
  
<body>

<div id="main">
	<div id="head">
    	<div class="logo"><img src="img/logo.jpg" width="462" height="50" /></div>
        <div class="logo_r">
        	<div class="logo_rt">
            	<a href="${base}/">首页</a><span>|</span>
            	<a href="#">桌面快捷</a><span>|</span>
            	<a href="sysuser/password" target="rightiframe">修改密码</a><span>|</span>
            	<a href="${base}/logout">注销</a><span>|</span>
            	<a href="#" onclick="window.close();">退出</a>
            </div>
            
            <div class="clear"></div>
            <div class="logo_rb">
            	<ul id="systemcontrol">
                    <#assign leftsize = session_menu.submenus?size>	                                  	  
            		<#list session_menu.submenus as menu>
                		<li onclick="leftchange(${menu_index},${leftsize})">
                			<a id="left_a_${menu_index}" class="<#if menu_index=0>onbg<#else>outbg</#if>" href="#">${menu.menuname}</a>
                		</li>
            		</#list>
                </ul>
            </div>
        </div>
    </div><!--head end -->
    
    <div class="clear"></div>
    <div id="changeweb">
    	<div class="left"><a href="#"><img src="img/left_jiantou.gif" width="27" height="30" /></a></div>
        <div class="middle"></div>
        <div class="right">
            <ul class="weball">
               <li>
				  <div class="changebodyleftout"></div>
                  <div class="changebodymiddleout" onclick="">首页</div>
                  <div class="changebodyrightout" title="关闭当前窗口"></div>
               </li>
               <li>
				  <div class="changebodylefton"></div>
                  <div class="changebodymiddleon" onclick="">科目信息</div>
                  <div class="changebodyrighton" title="关闭当前窗口"></div>
               </li>
          </ul>
          <div class="window">
          	  <ul class="window_kz">
              	  <li class="closewindow"><a href="#">关闭</a></li>
                  <li class="fullsrceen"><a href="#">全屏</a></li>
              </ul>
          </div>
       </div>
    </div><!--changeweb end -->
    
    <div class="clear"></div>    
    <div id="main_body">
    	<table class="maintable" cellpadding="0" cellspacing="0" border="0">
        	<tr>
            	<td class="lefttdwidth" width="178">
                    <div id="left_all">
                        <div id="mainleft">
                        
                        <#list session_menu.submenus as menus> 
	                        <div class="<#if menus_index=0>leftblock<#else>leftnone</#if>" id="left_${menus_index}">
	                            <div class="left">
	                                <div class="left_top">
	                                    <ul>
	                                        <#assign middlesize = menus.submenus?size>	                                  	  
	                              	        <#list menus.submenus as menu>
	                                  	    <li>
	                                        <div class="leftdaohang_top" id="middle_top_${menu_index}"></div>
	                                        <div class="leftdaohang_contect" id="middle_contect_${menu_index}" onclick="middlechange(${menu_index},${middlesize})">${menu.menuname}</div>
	                                        <div class="leftdaohang_bottom" id="middle_bottom_${menu_index}"></div>
	                                        <div class="secondmenu">	
	                                            <div class="middle <#if menu_index=0>middleblock<#else>middlenone</#if>" id="middle_${menu_index}">
	                                          	    <#list menu.submenus as leafs>
	                                          	   	    <#if leafs.menutype="0">
			                                                <div class="menuitem">
			                                                    <p onclick="folderChange()"><span>${leafs.menuname}</span></p>
			                                                    <ul class="menulinone">
			                                               	        <#list leafs.submenus as leaf>
			                                                            <li><a class="menulion" target="rightiframe" href="${leaf.menuurl}">${leaf.menuname}</a></li>
			                                               	        </#list>
			                                                    </ul>
			                                                </div>
	                                                    </#if>
	                                                     
	                                                    <#if leafs.menutype="1">	                                              
			                                                <ul class="menublock" id="menu">
			                                                    <li><a onclick="menuChange(this)" target="rightiframe" class="menuout" href="${leafs.menuurl}">${leafs.menuname}</a></li>
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
	                                    <div class="pre_jiantou"><a href="#"><img src="img/shang_sanjiao.gif" width="10" height="6" /></a></div>
	                                    <div class="next_jiantou"><a href="#"><img src="img/xia_sanjiao.gif" width="10" height="5" /></a></div>
	                                </div>   
	                            </div><!--main_body left end -->
	                            <div class="middle"></div>
	                        </div><!--left4 end -->
                        </#list>                     
 
						</div><!--mainleft end -->
                    </div><!--left_all end -->
        		</td>
        		<td id="dynamicDiv">
                    <div class="right_all" id="testdynamicdiv">
                        <iframe class="iframestyle" src="common/welcome.ftl" name="rightiframe"  frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
                    </div>
            	</td>
            </tr>
        </table>
    </div><!--main_body end -->
    
    <div id="foot">
    	<div class="foot_div">
        	<ul>
            	<li>${bundle("0001",session_user.username)}</li>
                <li>交易日期：<span>2011年4月19日10:27:30</span></li>
                <li class="foot_divli3">金证科技股份有限公司  <span>金证信托综合业务平台  </span><span>版本号：3.0</span></li>
            </ul>
        </div>
        <div class="help">
        	<div>
        	    <span>
        	        <a href="#"><img src="img/helpBall.gif" width="15" /></a></span><span><a href="#"><img src="img/helpBall_arrow.gif" onmousemove="this.src='img/helpBall_arrow2.gif'" onmouseout="this.src='img/helpBall_arrow.gif'" width="9" /></a>
        	    </span>
        	</div>
        </div>
    </div><!--foot end -->
</div><!--main end -->

</body>  
</html>  
