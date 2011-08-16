/*
 * Async Treeview 0.1 - Lazy-loading extension for Treeview
 * 
 * http://bassistance.de/jquery-plugins/jquery-plugin-treeview/
 *
 * Copyright (c) 2007 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id$
 *
 */

;(function($) {

function load(settings, root, child, container) {
	function createNode(parent) {
		var current = $("<li/>").attr("id", this.id || "").html("<span>" + this.text + "</span>").appendTo(parent);
		if (this.classes) {
			current.children("span").addClass(this.classes);
		}
		if (this.expanded) {
			current.addClass("open");
		}
		
		//add begin by huangyh
		if( settings.checkable ){
			var checkboxId = settings.checkboxid || "_checkid";
			if( settings.checkvalue ){
				var checkedv = ',' + settings.checkvalue + ',';
				if(checkedv.indexOf((',' + this.id + ',')) != -1){
					current.children("span").prepend("<input type='checkbox' id='" + checkboxId + "' name='" + checkboxId + "' value='" + this.id + "' checked='true'>");
				}else{
					current.children("span").prepend("<input type='checkbox' id='" + checkboxId + "' name='" + checkboxId + "' value='" + this.id + "'>");
				}
			}else{
				current.children("span").prepend("<input type='checkbox' id='" + checkboxId + "' name='" + checkboxId + "' value='" + this.id + "'>");
			}
		}
		$(":checkbox", current).click(function(){
			var checkboxInLi = $(this).closest("li").find(":checkbox");
			var allLi = $(this).parents("li");
			if($(this).attr("checked")){
				if(checkboxInLi.length > 1){
					checkboxInLi.attr("checked", true);
				}
				allLi.find(":checkbox:first").attr("checked", true);	
			}else{
				if(checkboxInLi.length > 1){
					checkboxInLi.removeAttr("checked");
				}
				allLi.find(":checkbox:first").removeAttr("checked");
				allLi.find(":checkbox[checked='true']").parents("li").find(":checkbox:first").attr("checked", true);
			}
		});
		// add end huangyh 20110816
		

		if (this.hasChildren || this.children && this.children.length) {
			var branch = $("<ul/>").appendTo(current);
			if (this.hasChildren) {
				current.addClass("hasChildren");
				createNode.call({
					classes: "placeholder",
					text: "&nbsp;",
					children:[]
				}, branch);
			}
			if (this.children && this.children.length) {
				$.each(this.children, createNode, [branch])
			}
		}
	}

	$.ajax($.extend(true, {
		url: settings.url,
		dataType: "json",
		data: {
			root: root
		},
		success: function(response) {
			child.empty();
			$.each(response, createNode, [child]);
	        $(container).treeview({add: child});
	    }
	}, settings.ajax));
	/*
	$.getJSON(settings.url, {root: root}, function(response) {
		function createNode(parent) {
			var current = $("<li/>").attr("id", this.id || "").html("<span>" + this.text + "</span>").appendTo(parent);
			if (this.classes) {
				current.children("span").addClass(this.classes);
			}
			if (this.expanded) {
				current.addClass("open");
			}
			if (this.hasChildren || this.children && this.children.length) {
				var branch = $("<ul/>").appendTo(current);
				if (this.hasChildren) {
					current.addClass("hasChildren");
					createNode.call({
						classes: "placeholder",
						text: "&nbsp;",
						children:[]
					}, branch);
				}
				if (this.children && this.children.length) {
					$.each(this.children, createNode, [branch])
				}
			}
		}
		child.empty();
		$.each(response, createNode, [child]);
        $(container).treeview({add: child});
    });
    */
}

var proxied = $.fn.treeview;
$.fn.treeview = function(settings) {
	if (!settings.url) {
		return proxied.apply(this, arguments);
	}
	var container = this;
	if (!container.children().size()){
		load(settings, "", this, container);
	}
	var userToggle = settings.toggle;
	return proxied.call(this, $.extend({}, settings, {
		collapsed: true,
		toggle: function() {
			var $this = $(this);
			if ($this.hasClass("hasChildren")) {
				var childList = $this.removeClass("hasChildren").find("ul");
				load(settings, this.id, childList, container);
			}
			if (userToggle) {
				userToggle.apply(this, arguments);
			}
		}
	}));
};

})(jQuery);