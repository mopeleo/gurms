<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>

<body>
	<span>${bundle("0001",session_user.userid,session_user.logincount,session_user.logindate+session_user.logintime)}</span>
	<span>${bundle("9000")}[${statics["org.gurms.common.config.GlobalParam"].STRING_SEPARATOR}]</span>
</body>
</html>