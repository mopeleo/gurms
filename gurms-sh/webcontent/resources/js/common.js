//所有JS常量在这里添加
var _CONSTANT = {
	VALID_URL : "common/validscript"
};

/**
解决IE缓存URL的办法:
方法一：把type改成post，并随便设置设置一个参数data: 'a=b'（一定要设置参数，否则仍然会被cache）
方法二：type仍然使用get，但设置cache: false（貌似不行）
方法三：通过ajaxSetup来全局设置get方法下的cache：$.ajaxSetup ({cache: false});
方法四：动态url，例如这样:URL+"&"+"t="+Math.random();或者new Date().getTime();
*/
//禁止IE缓存URL
$.ajaxSetup({cache: false});

$(document).ready(function(){
	
	$("#ajaxform").ajaxForm({
		beforeSubmit:_beforeSubmit,
		success:_processResponse
	});

	//input输入框回车键跳转
	$("input,select").keypress(function (e) {
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

	//屏蔽页面退格键
	$(document).keydown(function (e) {
	    var doPrevent = true;
		var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
	    if (keyCode == 8) {
	    	var d = (e.srcElement || e.target);
	        var tag = d.tagName.toUpperCase();
	        if (tag == 'INPUT' || tag == 'TEXTAREA') {
	            doPrevent = d.readOnly || d.disabled;
	        } else {
	            doPrevent = true;
	        }
	    } else{
	        doPrevent = false;
	    }
	    if (doPrevent)
	        e.preventDefault();
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

function showselectdiv(obj){
	var selectdiv = $(obj).parent("div").find(".displayNone");
	selectdiv.removeClass().addClass("select_contects").focus();
}

function showlist(listdivid){
	$('#'+listdivid).removeClass().addClass('checktitle_box').focus();
}

function cleardata(objid){
	$("#" + objid).val("");
	if($("#dis_" + objid).length > 0){
		$("#dis_" + objid).val("");
	}
}

var loadingDialog;
function _beforeSubmit(formData, jqForm, options){
	// 显示进度条
//	loadingDialog = dLoading();
	
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
		dAlert(responseText['returnmsg']);
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

function confirmDialog(params){
	if(params.ischeck == '1'){
		if(_R.size() == 0){
			dAlert("请选中要" + params.optname + "的数据!");
			return false;
		}
	}
	var txt;
	if(params.optname){
		txt = "确定" + params.optname + "数据?";
	}else{
		txt = "确定执行此操作?";
	}
	dialog(txt, {confirmMode:true,confirmFunc:buttonforward, confirmParam:params});
}

function forward(urlstring){
	window.location.href=urlstring;
}

function buttonforward(params){
	var urlstring = params.urlstring;
	if(params.ischeck == '1'){
		if(_R.size() == 0){
			dAlert("请选中要" + params.optname + "的数据!");
			return false;
		}
		if(params.keys && params.keys.length > 0){
			urlstring += "?1=" + new Date().getTime();
			var keys = params.keys.split(",");
			for(var i = 0; i < keys.length; i++){
				var val = _R.get(keys[i]);
				if(val){
					urlstring += '&' + keys[i] + '=' + val;
				}
			}
		}
	}
	
//	alert(urlstring);
	var formid = params.formid;
	if(params.openmode == '1'){
		if(formid){
			ajaxsubmiturl(formid, urlstring);
		}else{
			$.get(urlstring, function(data){dialog(data['returnmsg'],{fresh:true});});
		}
	}else if(params.openmode == '2'){
		dPopWindow(urlstring);
	}else{
		if(formid){
			submiturl(formid, urlstring);
		}else{
			forward(urlstring);
		}
	}
}

function jumpPage(pageNo) {
	if(pageNo){
		$("#pageNo").val(pageNo);
	}
	$("form").eq(0).submit();
}

function search() {
	if($("#order").length > 0){
		$("#order").val("");
	}
	if($("#orderBy").length > 0){
		$("#orderBy").val("");
	}
	if($("#pageNo").length > 0){
		$("#pageNo").val("1");
	}

	$("form").eq(0).submit();
//	$("#pageNo").parents("form:first").submit();
}

function multiselect(obj){
	var divid = $(obj).closest("div").get(0).id;
	var splitflag = divid.indexOf("_");
	var inputid = divid.substring(splitflag+1);

	var allcheckval = "";
	var alldisval = "";
	$(obj).closest("div").find("input:checked").each(function(){
		allcheckval += $(this).val() + ",";
		alldisval += $(this).closest("li").text() + ",";
	});
	
	if(allcheckval != ""){
		allcheckval = allcheckval.substring(0, allcheckval.length-1);
		alldisval = alldisval.substring(0, alldisval.length-1);
	}

	document.getElementById("dis_"+inputid).value = alldisval;
	document.getElementById(inputid).value = allcheckval;
	$(obj).closest("div").focus();
}

function singleselect(obj){
	var divid = $(obj).closest("div").get(0).id;
	var splitflag = divid.indexOf("_");
	var inputid = divid.substring(splitflag+1);

	document.getElementById("dis_"+inputid).value = $(obj).closest("li").text();
	document.getElementById(inputid).value = $(obj).find("input").val();
	$(obj).closest("div").removeClass().addClass("displayNone");
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

function setDocumentCharset(charset){
	document.charset=charset;
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

