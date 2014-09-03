/**
 * Dialog
 *
 * @author    caixw <http://www.caixw.com>
 * @copyright Copyright (C) 2010, http://www.caixw.com
 * @license   FreeBSD license
 */


/**
 * jQuery的Dialog插件。
 *
 * @param object content
 * @param object options 选项。
 * @return 
 */
function Dialog(content, options)
{
    var defaults = { // 默认值。 
        title:'',       // 标题文本，若不想显示title请通过CSS设置其display为none 
        showTitle:true,     // 是否显示标题栏。
        closeText:'', // 关闭按钮文字，若不想显示关闭按钮请通过CSS设置其display为none 
        draggable:true,     // 是否移动 
        modal:true,         // 是否是模态对话框 
//        center:true,        // 是否居中。 
        fixed:true,         // 是否跟随页面滚动。
        removeContent:true, //关闭窗口是否删除内容
        confirmButton:true,  //是否添加确定按钮
        fresh:false, //点击确定按钮是否刷新页面
        forwardurl:'', //刷新跳转的页面
        confirmMode:false, //是否确认对话框
        confirmFunc:null, //确认函数
        confirmParam:{}, //确认函数的入参
        time:0,             // 自动关闭时间，为0表示不会自动关闭。 
        id:false            // 对话框的id，若为false，则由系统自动产生一个唯一id。 
    };
    var options = $.extend(defaults, options);
    options.id = options.id ? options.id : 'dialog-' + Dialog.__count; // 唯一ID
    var overlayId = options.id + '-overlay'; // 遮罩层ID
    var timeId = null;  // 自动关闭计时器 
    var isShow = false;
    var isIe = $.browser.msie;
    var isIe6 = $.browser.msie && ('6.0' == $.browser.version);

    /* 对话框的布局及标题内容。*/
    var barHtml = !options.showTitle ? '' :
        '<div class="bar"><span class="title">' + options.title + '</span><a class="close">' + options.closeText + '</a></div>';
    var buttomHtml = '';
    if(options.confirmMode == true){
    	buttomHtml = '<div id="_buttom" style="background:#eceffb; text-align:center; padding:5px 0; border-top:1px solid #a2b0ee;"><input id="_confirm_button" type="button" class="button" value="确定">  <input id="_cancel_button" type="button" class="button" value="取消"></div>';
    }else{
        if(options.confirmButton == true){
        	buttomHtml = '<div id="_buttom" style="background:#eceffb; text-align:center; padding:5px 0; border-top:1px solid #a2b0ee;"><input id="_dialog_button" type="button" class="button" value="确定"></div>';
        }
    }
    var dialog = $('<div id="' + options.id + '" class="dialog">' + barHtml 
    	+ '<div class="content"></div>' + buttomHtml + '</div>').hide();
    
    $('body').append(dialog);


    /**
     * 重置对话框的位置。
     *
     * 主要是在需要居中的时候，每次加载完内容，都要重新定位
     *
     * @return void
     */
    var resetPos = function()
    {
        var left = ($(window).width() - dialog.width()) / 2;
        var top = ($(window).height() - dialog.height()) / 2;
//        var top = '100px';
        if(!isIe6 && options.fixed){   
        	dialog.css({top:top,left:left});   
        }else{   
        	dialog.css({top:top,left:left+$(document).scrollLeft()});   
        }
    }

    //隐藏时是否刷新页面
    var forward = function(){
        if(options.fresh){
        	if(options.forwardurl && options.forwardurl.length > 1){
                window.location.href = options.forwardurl;
        	}else{
                window.location.reload();
        	}
        }
    }
    
    /**
     * 初始化位置及一些事件函数。
     *
     * 其中的this表示Dialog对象而不是init函数。
     */
    var init = function()
    {
        /* 是否需要初始化背景遮罩层 */
        if(options.modal)
        {
            $('body').append('<div id="' + overlayId + '" class="dialog-overlay"></div>');
            $('#' + overlayId).css({'left':0, 'top':0,
                    /*'width':$(document).width(),*/
                    'width':'100%',
                    /*'height':'100%',*/
                    'height':$(document).height(),
                    'z-index':++Dialog.__zindex,
                    'position':'absolute'})
                .hide();
        }

        dialog.css({'z-index':++Dialog.__zindex, 'position':options.fixed ? 'fixed' : 'absolute'});

		/*  IE6 兼容fixed代码 */
        if(isIe6 && options.fixed)
        {
            dialog.css('position','absolute');
            resetPos();
            var top = parseInt(dialog.css('top')) - $(document).scrollTop();
            var left = parseInt(dialog.css('left')) - $(document).scrollLeft();
            $(window).scroll(function(){
                dialog.css({'top':$(document).scrollTop() + top,'left':$(document).scrollLeft() + left});
            });
        }

        /* 以下代码处理框体是否可以移动 */
        var mouse={x:0,y:0};
        function moveDialog(event)
        {
            var e = window.event || event;
            var top = parseInt(dialog.css('top')) + (e.clientY - mouse.y);
            var left = parseInt(dialog.css('left')) + (e.clientX - mouse.x);
            dialog.css({top:top,left:left});
            mouse.x = e.clientX;
            mouse.y = e.clientY;
        };
        dialog.find('.bar').mousedown(function(event){
            if(!options.draggable){  return; }

            var e = window.event || event;
            mouse.x = e.clientX;
            mouse.y = e.clientY;
            $(document).bind('mousemove',moveDialog);
        });
        $(document).mouseup(function(event){
            $(document).unbind('mousemove', moveDialog);
        });

        /* 绑定一些相关事件。 */
        if(options.removeContent){
            dialog.find('.close').bind('click', this.close);
        }else{
            dialog.find('.close').bind('click', this.hide);
        }
        if(options.confirmButton){
            dialog.find('#_dialog_button').bind('click', this.hide);
        }
        if(options.confirmMode){
            dialog.find('#_confirm_button').bind('click', this.confirm);
            dialog.find('#_cancel_button').bind('click', this.hide);
        }
        dialog.bind('mousedown', function(){  dialog.css('z-index', ++Dialog.__zindex); });

        // 自动关闭 
        if(0 != options.time){  timeId = setTimeout(this.close, options.time);    }
    }


    /**
     * 设置对话框的内容。 
     *
     * @param string c 可以是HTML文本。
     * @return void
     */
    this.setContent = function(c)
    {
        var div = dialog.find('.content');
        if('object' == typeof(c))
        {
        	var complexProp = {
//        		height: 200,
        		width : 500
        	};
            var _options = $.extend(complexProp, c);
        	dialog.css("width", _options.width);
        	div.css("height", _options.height);
            switch(c.type.toLowerCase())
            {
            case 'id': // 将ID的内容复制过来，原来的还在。
                div.html($('#' + c.value).text());
                break;
            case 'img':
                div.html('加载中...');
                $('<img alt="" />').load(function(){div.empty().append($(this));resetPos();})
                    .attr('src',c.value);
                break;
            case 'url':
                div.html('加载中...');
                $.ajax({url:c.value,
                        success:function(html){div.html(html);resetPos();},
                        error:function(xml,textStatus,error){div.html('出错啦')}
                });
                break;
            case 'iframe':
            	var fid = "_iframeid";
            	if(c.iframeid){
            		fid = c.iframeid;
            	}
                div.append($('<iframe id="' + fid + '" frameborder="0" src="' + c.value + '" />'));
                div.css("height",402);
        	    dialog.css("width",800);
        	    dialog.css("height",450);
//        	    dialog.find("#_buttom").hide();
                break;
            case 'text':
            default:
                div.html(c.value);
                break;
            }
        }
        else
        {   div.html(c); }
    }

    /**
     * 显示对话框
     */
    this.show = function()
    {
        if(undefined != options.beforeShow && !options.beforeShow())
        {   return;  }

        /**
         * 获得某一元素的透明度。IE从滤境中获得。
         *
         * @return float
         */
        var getOpacity = function(id)
        {
            if(!isIe)
            {   return $('#' + id).css('opacity');    }

            var el = document.getElementById(id);
            return (undefined != el
                    && undefined != el.filters
                    && undefined != el.filters.alpha
                    && undefined != el.filters.alpha.opacity)
                ? el.filters.alpha.opacity / 100 : 1;
        }
        /* 是否显示背景遮罩层 */
        if(options.modal)
        {   $('#' + overlayId).fadeTo('slow', getOpacity(overlayId));   }
        dialog.fadeTo('slow', getOpacity(options.id), function(){
            if(undefined != options.afterShow){   options.afterShow(); }
            isShow = true;
        });
        // 自动关闭 
        if(0 != options.time){  timeId = setTimeout(this.close, options.time);    }

        resetPos();
    }


    /**
     * 隐藏对话框。但并不取消窗口内容。
     */
    this.hide = function()
    {
        if(!isShow){ return; }

        if(undefined != options.beforeHide && !options.beforeHide())
        {   return;  }

        dialog.fadeOut('slow',function(){
            if(undefined != options.afterHide){   options.afterHide(); }
        });
        if(options.modal){
        	$('#' + overlayId).fadeOut('slow');
        }

        isShow = false;
        forward();
    }

    this.confirm = function(){
        if(!isShow){ return; }

        if(undefined != options.beforeHide && !options.beforeHide())
        {   return;  }

    	if(typeof(options.confirmFunc) != 'undefined' && options.confirmFunc instanceof Function){
    		var funcres = options.confirmFunc(options.confirmParam);
    		if(funcres == false){
    			return false;
    		}
    	}
    	
        dialog.fadeOut('slow',function(){
            if(undefined != options.afterHide){   options.afterHide(); }
        });
        if(options.modal){
        	$('#' + overlayId).fadeOut('slow');
        }

        isShow = false;
        
        forward();
    }
    
    /**
     * 关闭对话框 
     *
     * @return void
     */
    this.close = function()
    {
        if(undefined != options.beforeClose && !options.beforeClose())
        {   return;  }

        dialog.fadeOut('slow', function(){
            $(this).remove();
            isShow = false;
            if(undefined != options.afterClose){   options.afterClose(); }
        });
        if(options.modal)
        {   $('#'+overlayId).fadeOut('slow', function(){$(this).remove();}); }
        clearTimeout(timeId);
        
        forward();
    }
    

    init.call(this);
    this.setContent(content);
    
    Dialog.__count++;
    Dialog.__zindex++;
}
Dialog.__zindex = 500;
Dialog.__count = 1;
Dialog.version = '1.0 beta';

function dialog(content, options)
{
	var dlg = new Dialog(content, options);
	dlg.show();
	return dlg;
}

function dAlert(msg){
	dialog(msg);
}

function dConfirm(msg, jumpUrl, formId){
	var params;
	if(formId){
		params = {url:jumpUrl,form:formId};
	}else{
		params = {url:jumpUrl};
	}
	dialog(msg, {confirmMode:true,confirmFunc:__jump, confirmParam:params});
}

function dPopWindow(openUrl){
	dialog({type:'iframe',value:openUrl},{confirmButton:false, fresh:true});	
}

function dLoading(picUrl){
	var pic = 'img/loading.gif';
	if(picUrl){
		pic = picUrl;
	}
	dialog({type:'img',value:pic});
}

function __jump(params){
	if(params.form){
		var form = document.getElementById(params.form);
		if(params.url && params.url != null){
			form.action = params.url;
		}	
		form.submit();
	}else{
		window.location.href=params.url;
	}
}
