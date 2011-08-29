<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>金证信托综合业务管理平台</title>
<%@ include file="/common/meta.jsp" %>

<link rel="stylesheet" type="text/css" href="css/login.css"/>
<link rel="stylesheet" type="text/css" href="css/livevalidation.css"/>
<script src="js/jquery-1.6.2.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/md5.js" type="text/javascript"></script>
<script src="js/common.js" type="text/javascript"></script>
<script src="js/livevalidation_standalone.js" type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$.get('validscript',
			  {className:'org.gurms.entity.system.SysUser',formId:'ajaxform',props:'userid,loginpassword,validcode'},
			  function(data){
				$("head").append(data); //alert(data);
			  }
		);
	});

	function preSubmit(formData){
		for(var i = 0; i < formData.length; i++){
			if(formData[i]['name'] == 'loginpassword'){
				var pw = formData[i]['value'];
				formData[i]['value'] = get_md5_string(pw);
				break;
			}
		}

		return true;
	}
	
	function afterReturn(result, status){
		if(result['success']){
			forward('login');
		}else{
			$("span.orange").text(result['returnmsg']);
			var imgsrc = 'validcode?' + new Date().getTime();
			$("#validcode_img").attr("src",imgsrc);
		}
	}
	
</script> 
</head>

<body>

<div id="main">
	<div id="maincontect">
    	<div class="message">
    	<form id="ajaxform" action="ajaxlogin" method="post">
        	<table>
            	<tr><td colspan="2"><span class="orange">&nbsp;</span></td></tr>
            	<tr>
                	<td>用户名：</td>
                    <td><input type="text" id="userid" name="userid"/></td>
                </tr>
                <tr>
                	<td>密　码：</td>
                    <td><input type="password" id="loginpassword" name="loginpassword"/></td>
                </tr>
                <tr>
                	<td>验证码：</td>
                    <td><input type="text" id="validcode" name="validcode"/></td>
                </tr>
                <tr>
                	<td></td>
                    <td><img onclick="this.src='validcode'+'?'+new Date().getTime();" id="validcode_img" src="validcode" style="cursor:pointer;border:1px solid #555"/><p>点击换一张</p></td>
                </tr>
                <tr>
                    <td colspan="2">
                    	<input type="submit" class="button" value="登录" />
						<input type="button" class="button" value="重置" />
					</td>
                </tr>
            </table>
        </form>
        </div>
    </div>
</div>

</body>
</html>
