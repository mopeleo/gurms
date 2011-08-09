$(document).ready(function(){
	$("#ajaxform").ajaxForm({
		beforeSubmit:_beforeSubmit,
		success:_processResponse
	});
});

var loadingDialog;
function _beforeSubmit(formData, jqForm, options){
	// 显示进度条
//	loadingDialog = new Dialog({type:'img',value:'img/loading.gif'});
//	loadingDialog.show();
	
	var preResult = true;
	if(typeof(preSubmit) != 'undefined' && preSubmit instanceof Function){
		preResult = preSubmit(formData, options);
	}
	if(typeof(preResult) == 'boolean' && preResult){
		if(typeof(_validForm) != 'undefined' && _validForm instanceof Function){
			return _validForm();
		}else{
			return true;
		}			
	}
	return false;
}

function _processResponse(responseText, statusText){
//	if(loadingDialog && loadingDialog != null){
//		loadingDialog.hide();
//	}
	if(typeof(afterReturn) != 'undefined' && afterReturn instanceof Function){
		afterReturn(responseText, statusText);
	}else{
		new Dialog(responseText['returnmsg']).show();
	}
}

Array.prototype.contains=function(obj){
    var arrayStr = "\x0f" + this.join("\x0f") + "\x0f";
    if(arrayStr.indexOf("\x0f" + obj + "\x0f")>=0) return true;
    return false;
}

String.prototype.trim=function(){
    return this.replace(/(^"s+)|("s+$)/g, "");
}

String.prototype.endWith=function(str){
    if(str==null||str==""||this.length==0||str.length>this.length)
        return false;
    if(this.substring(this.length-str.length)==str)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith=function(str){
    if(str==null||str==""||this.length==0||str.length>this.length)
        return false;
    if(this.substr(0,str.length)==str)
        return true;
    else
        return false;
    return true;
}

function submiturl(formid, urlstring){
	var form = document.getElementById(formid);
	if(urlstring && urlstring != null){
		form.action = urlstring;
	}	
	form.submit();
}

function forward(urlstring){
	window.location.href=urlstring;
}

function jumpPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#mainForm").submit();
}

function search() {
	$("#order").val("");
	$("#orderBy").val("");
	$("#pageNo").val("1");

	$("#mainForm").submit();
}

/** 计算 整个页面的大小 */
function totalPageSize(){
	var height = document.documentElement.clientHeight;
	var winheight = height - 106;
	var foottop = height - 26;
	var left_topheight = winheight-25;
    document.write("<style type='text/css'>#main_body{height:"+winheight+"px;} .lefttdwidth{height:"+winheight+"px;} #foot{top:"+foottop+"px;} .right_all{width:100%; height:"+winheight+"px;}.left_top{height:"+left_topheight+"px;} #mainleft{height:"+winheight+"px;}</style>");
    if(screen.width<1024){
    	document.write("<style type='text/css'>.right_kz{width:auto;} body{overflow:auto;} html{overflow:auto;}</style>");
    }
}

/** 计算 内嵌IFRAME页面的大小 */
function totalIFrameSize(){
	var height = document.documentElement.clientHeight;
	var rightkzheight = height-32;
	document.write("<style type='text/css'>.right_kz{width:98%; height:"+rightkzheight+"px;} #right_web{width:100%; height:"+height+"px;}</style>");
	if(screen.width<1024){
		document.write("<style type='text/css'>.right_kz{width:auto;} body{overflow:auto;} html{overflow:auto;}</style>");
	}
}
