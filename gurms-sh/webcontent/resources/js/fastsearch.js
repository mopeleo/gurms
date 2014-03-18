
function bindFastSearch(inputId, data) {
	$("#" + inputId).keyup(function(e) {
		var inputId = $(this).attr("id");
		var dynamicDivId = inputId + "_div";
		var dynamicDiv = document.getElementById(dynamicDivId);
		var dynamicDivHidden = true;
		if (dynamicDiv) {
			dynamicDivHidden = $(dynamicDiv).is(":hidden");
		}

		if (e.srcElement.value.length < 2) {
			if (!dynamicDivHidden) {
				hideDivSelect(inputId);
			}
			return;
		} else {
			if (dynamicDivHidden) {
				if (!dynamicDiv) {
					drawDivSelect(inputId, data);
				}
				showDivSelect(inputId);
			}
		}
		var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
		var maxIndex = $(dynamicDiv).children("ul").children("li").length - 1;
		var minIndex = 0;
		var selectIndex = $(this).attr("seletedIndex") || -1;
		if (keyCode == 40) { // down
			if (selectIndex < maxIndex) {
				selectIndex++;
				selectedLi(inputId, selectIndex);
			}
		} else if (keyCode == 38) { // up
			if (selectIndex > minIndex) {
				selectIndex--;
				selectedLi(inputId, selectIndex);
			}
		} else if (keyCode == 13 || keyCode == 27) { // enter or esc
			hideDivSelect(inputId);
		}

	});
}
function drawDivSelect(inputId, showdata) {
	var dynamiceDivId = inputId + "_div";
	var div = document.getElementById(dynamiceDivId);
	if (div) {
		div.empty();
	} else {
		div = $("<div id='" + dynamiceDivId + "'></div>");
	}
	var ul = $("<ul></ul>");
	for ( var i = 0; i < showdata.length; i++) {
		ul.append("<li>" + showdata[i].display + "<input type='hidden' value='"
				+ showdata[i].relvalue + "'></li>");
	}
	div.append(ul);
	$("#" + inputId).after(div);
}

function showDivSelect(inputId) {
	var inputObj = $("#" + inputId);
	var inputOffset = inputObj.offset();
	var t = inputOffset.top + 22;
	var l = inputOffset.left;
	var dynamiceDiv = $("#" + inputId + "_div");
	dynamiceDiv.removeClass().addClass('checktitle_box');
	dynamiceDiv.show();
	dynamiceDiv.offset({
		top : t,
		left : l
	});
	inputObj.attr("seletedIndex", -1);
	/*
	 * 鼠标事件待定
	 * $("#hint").children("ul").children("li").bind("mouseenter",function(){
	 * var indx = $(this).index(); selectedLi(e.srcElement,indx); });
	 * $("#hint").children("ul").children("li").bind("click",function(){
	 * hideDivSelect(inputId); });
	 */
}
function hideDivSelect(inputId) {
	var dynamiceDiv = $("#" + inputId + "_div");
	dynamiceDiv.children("ul").children("li").css({
		backgroundColor : ''
	});
	dynamiceDiv.hide();
	$("#" + inputId).attr("seletedIndex", -1);
}

function selectedLi(inputId, selectIndex) {
	var dynamiceDiv = $("#" + inputId + "_div");
	dynamiceDiv.children("ul").children("li").css({
		backgroundColor : ''
	});
	dynamiceDiv.children("ul").children("li").eq(selectIndex).css({
		backgroundColor : '#DDDDDD'
	});
	var selectValue = dynamiceDiv.children("ul").children("li").eq(selectIndex).text();
	$("#" + inputId).attr("seletedIndex", selectIndex);
	$("#" + inputId).val(selectValue);

}
