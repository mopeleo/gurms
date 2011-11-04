//所有JS常量在这里添加
var _CONSTANT = {
	VALID_URL : "validscript"
};

$(document).ready(function(){
	$("#ajaxform").ajaxForm({
		beforeSubmit:_beforeSubmit,
		success:_processResponse
	});

	$("input").keypress(function (e) {
		var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
		if (keyCode == 13){
			var i;
			for (i = 0; i < this.form.elements.length; i++){
				if (this == this.form.elements[i])
					break;
			}
			i = (i + 1) % this.form.elements.length;
			this.form.elements[i].focus();
			return false;
		}else{
			return true;
		}
	});
});

//列表页面一行数据;
(function(){
    var _row = function(){
        this._length = 0;
        this._entity = {};
    };
    
    _row.prototype = {
        size: function(){
            return this._length;
        },
        
        contains: function(key){
            if(this._length>0 && this._entity[key]) 
            	return true;
            return false;
        },
            
        get: function(key){
            if(this.contains(key)){
                return this._entity[key]; 
            }else{
            	return "";
            }
        },
        
        put: function(key,value){
            if(!this.contains(key)){
                this._length++ ;
            };
            this._entity[key] = value;  //设置属性
        },
        
        remove: function(key){
            if(this.contains(key)){
                delete this._entity[key];
                this._length--;
            }
        },
        
        clear: function(){
            this._length = 0;
            this._entity = {};
        }
    };
    
    window._R = new _row();
})();


function clickrow(row){
	$(".trcoloryellow").removeClass();
	$(row).addClass("trcoloryellow");
	
	_R.clear();
	var idx = $(row).children().eq(0).text();
	if(idx && idx != ''){
		var th = $(row).parent().find("th");
		$(row).children().each(function(){
			var td = $(this);
			if(td.is("td")){
				_R.put(th.get(td.index()).id, td.text());
			}
		});
		
		$(row).find("input:hidden").each(function(){
			_R.put($(this).attr("id"), $(this).val());
		});
	}
	
//	for(var p in _R._entity){
//		alert(p + " : " + _R.get(p));
//	}
}

function changecol(colid){
	var col = $("#" + colid).index("th");
	var ctx = $("#" + colid).closest("table");
	$("tr", ctx).each(function(){
		var td = $(this).children().get(col);
		$(td).toggle();
	});
	$('#chooselist').focus();
}

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

function submiturl(formid, urlstring){
	var form = document.getElementById(formid);
	if(urlstring && urlstring != null){
		form.action = urlstring;
	}	
	form.submit();
}

function ajaxsubmiturl(formid, urlstring){
	$("#"+formid).ajaxSubmit({
		url: urlstring,
		beforeSubmit: _beforeSubmit,
		success: _processResponse
	});
	return false;
}

function confirmDialog(func, params){
	if(params.keys && params.keys.length > 0){
		if(_R.size() == 0){
			new Dialog("请选中要" + params.optname + "的数据!").show();
			return false;
		}
	}
	var txt;
	if(params.optname){
		txt = "确定" + params.optname + "数据?";
	}else{
		txt = "确定执行此操作?";
	}
	new Dialog(txt, {confirmMode:true,confirmFunc:func, confirmParam:params}).show();
}

function forward(urlstring){
	window.location.href=urlstring;
}

function buttonforward(params){
	if(params.keys && params.keys.length > 0){
		if(_R.size() == 0){
			new Dialog("请选中要" + params.optname + "的数据!").show();
			return false;
		}
		var urlstring = params.urlstring + "?1=1";
		var keys = params.keys.split(",");
		for(var i = 0; i < keys.length; i++){
			var val = _R.get(keys[i]);
			if(val){
				urlstring += '&' + keys[i] + '=' + val;
			}
		}
	}
//	alert(urlstring);
	if(params.isajax == '1'){
		$.get(urlstring, function(data){new Dialog(data['returnmsg'],{fresh:true}).show();});
	}else{
		forward(urlstring);
	}
}

/*
function buttonforward(urlstring, optname, isajax, confirmed, params){
	if(params && params.length > 0){
		if(_R.size() == 0){
			new Dialog("请选中要" + optname + "的数据!").show();
			return false;
		}
		urlstring += "?1=1";
		var keys = params.split(",");
		for(var i = 0; i < keys.length; i++){
			var val = _R.get(keys[i]);
			if(val){
				urlstring += '&' + keys[i] + '=' + val;
			}
		}
	}
//	alert(urlstring);
	if(isajax == '1'){
		$.get(urlstring, function(data){new Dialog(data['returnmsg'],{fresh:true}).show();});
	}else{
		forward(urlstring);
	}
}
*/

function jumpPage(pageNo) {
	if(pageNo){
		$("#pageNo").val(pageNo);
	}
	$("#pageNo").parents("form:first").submit();
}

function search() {
	$("#order").val("");
	$("#orderBy").val("");
	$("#pageNo").val("1");

	$("#pageNo").parents("form:first").submit();
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

