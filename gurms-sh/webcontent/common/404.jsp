<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>404 - 页面不存在</title>
    <%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/resources/css/global.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		totalIFrameSize();
	</script>
</head>

<body class="rightbodybg">
	<div class="rightstyle">
        	<div class="warnning">
           	  <div class="warnning_bg">
               	  <div class="warntitle">错误信息提示</div>
                  <div class="warntxt"><h1>页面不存在.</h1></div>
                  <div class="warnok"><input type="button" onclick="history.back();" class="button" value="返　回" /></div>
                </div> 
            </div>
    </div>
</body>
</html>