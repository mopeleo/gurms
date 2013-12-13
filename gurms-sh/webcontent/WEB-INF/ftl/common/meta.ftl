<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="windows-Target" contect="_top">

<script src="${base}/resources/js/jquery.min.js" type="text/javascript"></script>

<link href="${base}/resources/css/global.css" type="text/css" rel="stylesheet"/>
<script src="${base}/resources/js/common.js" type="text/javascript"></script>

<link href="${base}/resources/css/jquery.multiselect2side.css" type="text/css" rel="stylesheet"/>

<link href="${base}/resources/css/treeview/jquery.treeview.css" type="text/css" rel="stylesheet"/>
<script src="${base}/resources/js/jquery.treeview.js" type="text/javascript"></script>
<script src="${base}/resources/js/jquery.treeview.edit.js" type="text/javascript"></script>
<script src="${base}/resources/js/jquery.treeview.async.js" type="text/javascript"></script>

<link href="${base}/resources/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="${base}/resources/js/dialog.js" type="text/javascript"></script>

<link href="${base}/resources/css/lhgcalendar/lhgcalendar.css" type="text/css" rel="stylesheet"/>
<script src="${base}/resources/js/lhgcalendar.min.js" type="text/javascript"></script>

<link href="${base}/resources/css/livevalidation.css" type="text/css" rel="stylesheet"/>
<script src="${base}/resources/js/livevalidation_standalone.js" type="text/javascript"></script>
<script src="${base}/resources/js/jquery.form.js" type="text/javascript"></script>

<script type="text/javascript">
$.ajaxSetup({cache: false});

/**
解决办法:
方法一：把type改成post，并随便设置设置一个参数data: 'a=b'（一定要设置参数，否则仍然会被cache）
方法二：type仍然使用get，但设置cache: false
方法三：通过ajaxSetup来全局设置get方法下的cache
$.ajaxSetup ({cache: false});
*/
</script>
