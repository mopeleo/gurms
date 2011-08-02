<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%response.setStatus(200);%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>500 - 系统内部错误</title>
	<%@ include file="/common/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/global.css"/>
	<script src="js/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		totalIFrameSize();
	</script>
</head>
<body>

	<div id="right_web" class="right_web">
        <div class="right_kz">
        	<div class="warnning">
           	  <div class="warnning_bg">
               	  <div class="warntitle">错误信息提示</div>
                  <div class="warntxt">
<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
	
	out.println(ex.getMessage());
%>
                  </div>
                  <div class="warnok"><input type="button" onclick="history.back();" class="button" value="返　回" /></div>
                </div> 
            </div>
        </div><!--right_kz end -->
    </div>

</body>
</html>
