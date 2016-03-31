function addOneParam(name, necessary, type,parameterType, remark, rowNum, tableId) {
	if (!rowNum || rowNum == '') {
		var mydate = new Date();
		rowNum = mydate.getMilliseconds();
	}
	if (tableId == 'editParamTable') {
		$("#editParamTable")
				.append(
						"<tr><td><input class='form-control' type='text' name='name' value='"
								+ name
								+ "' placeholder=\"参数名：必填\"></td>"
								+ "<td><input class='form-control' type='text' name='necessary' id='necessary"
								+ rowNum
								+ "' value='"
								+ necessary
								+ "'"
								+ "onclick=\"loadPick(event,200,250,'true','necessary"
								+ rowNum
								+ "','TRUEORFALSE','','"
								+ necessary
								+ "','',5);\" placeholder=\"点击输入框选择\"></td>"
								+ "<td><input class='form-control' type='text' name='type' value='"
								+ type
								+ "' placeholder=\"类型：必填\"></td>"
								+ "<td><input class='form-control' type='text' name='parameterType' id='parameterType"
								+ rowNum
								+ "' value='"
								+ parameterType
								+ "'"
								+ "onclick=\"loadPick(event,200,250,'true','parameterType"
								+ rowNum
								+ "','PARAMETERTYPE','','"
								+ parameterType
								+ "','',5);\" placeholder=\"参数类型：请求头/参数\"></td>"
								+ "<td><input class='form-control' type='text' name='remark' value='"
								+ remark
								+ "'></td>"
								+ "<td class='cursor text-danger'><i class='iconfont' onclick='deleteOneParam(this)'>&#xe60e;</i></td>"
								+ "</tr>");
	} else if (tableId == 'editResponseParamTable') {
		$("#editResponseParamTable")
				.append(
						"<tr><td><input class='form-control' type='text' name='name' value='"
								+ name
								+ "' placeholder=\"参数名：必填\"></td>"
								+ "<td><input class='form-control' type='text' name='type' value='"
								+ type
								+ "' placeholder=\"类型：必填\"></td>"
								+ "<td><input class='form-control' type='text' name='remark' value='"
								+ remark
								+ "'></td>"
								+ "<td class='cursor text-danger'><i class='iconfont' onclick='deleteOneParam(this)'>&#xe60e;</i></td>"
								+ "</tr>");
	}
}
function deleteOneParam(nowTr) {
	$(nowTr).parent().parent().remove();
}
function goJsonPage(editerId, targetId, editerId2, targetId2) {
	$("#" + editerId).addClass('none');
	$("#" + targetId).removeClass('none');
	if (editerId2)
		$("#" + editerId2).addClass('none');
	if (targetId2)
		$("#" + targetId2).removeClass('none');
};
function getParamFromTable(tableId) {
	var json = "[";
	var i = 0;
	var j = 0;
	$('#' + tableId).find('tbody').find('tr').each(function() {
		i = i + 1;
		j = 0;
		if (i != 1)
			json += ","
		json += "{";
		$(this).find('td').each(function() {
			j = j + 1;
			$(this).find('input').each(function(i, val) {
				if (j != 1)
					json += ","
				json += "\"" + val.name + "\":\"" + val.value + "\""
			});
		});
		json += "}"
	});
	json += "]";
	return json;
}
/** ***************pick控件搜索*************** */
var navigateText = "";
var deep = 0;
var select = 0;
var hasLoad = 0;
function keyMonitor() {
	hasLoad = 1;
	var lookUp = document.getElementById('lookUp');
	$(document).keydown(
			function(event) {
				try {
					if (event.keyCode == 8 && lookUp.style.display == 'block') {
						if (navigateText.length >= 1) {
							navigateText = navigateText.substring(0,
									navigateText.length - 1)
						}
						if (navigateText.length == 0) {
							$("#pickTip").css("display", "none");
						}
						var tHandler = "pickScroll('" + navigateText + "')";
						setTimeout(tHandler, 500);
						return false;// return false表示该事件不再往下传递
					} else if (event.keyCode != 13) {
						navigateText += String.fromCharCode(event.keyCode);
						if (lookUp.style.display != 'block')
							navigateText = "";
						var tHandler = "pickScroll('" + navigateText + "')";
						setTimeout(tHandler, 500);
					}
				} catch (e) {
					alert(e);
				}
			});
}
function pickScroll(oldNavigateText) {
	$("#pickTip").html(navigateText);
	if (navigateText.length > 0) {
		$("#pickTip").css("display", "block");
	}
	if (oldNavigateText != navigateText) {
		return;
	}
	deep = oldNavigateText.length;
	var lookUp = document.getElementById('lookUp');
	if (lookUp.style.display == 'block') {
		select = 0;
		jQuery.each($("#pickContent").find("div"), function() {
			var span = jQuery(this).find("span");
			var checkBox = jQuery(this).find("input");
			if (select == 0) {
				checkText(this, oldNavigateText, span, checkBox, 1);
			}
		});
	}
}
function checkText(obj, oldNavigateText, span, checkBox, length) {
	if (span.text().substring(length - 1, length).toUpperCase() == oldNavigateText
			.substring(length - 1, length)) {
		if (length < deep) {
			checkText(obj, oldNavigateText, span, checkBox, length + 1);
		} else {
			var container = $('#lookUpContent'), scrollTo = span;
			container.scrollTop(scrollTo.offset().top - container.offset().top
					+ container.scrollTop() - 100);
			$("#pickContent div").removeClass("pickSelect");
			$(obj).addClass("pickSelect");
			select = 1;
		}
	}
}
// pick 确认
function setPick() {
	var length = document.getElementsByName('cid').length;
	var checkBoxValue = "";
	var checkBoxName = "";
	var rootScope = getRootScope();
	for (var i = 0; i < length; i++) {
		if (pickRadio == 'true') {
			if (document.getElementsByName('cid')[i].checked == true) {
				rootScope.$apply(function() {
					if(pickTagName){
						$("#"+pickTagName).val(document.getElementsByName('cidName')[i].value);
						rootScope.model[pickTagName] = $(".cidName")[i].textContent;
					}
					$("#"+pickTag).val(document.getElementsByName('cid')[i].value);
					rootScope.model[pickTag] = document.getElementsByName('cid')[i].value;
				});
				break;
			}
		} else {
			if (document.getElementsByName('cid')[i].checked == true) {
				checkBoxValue = checkBoxValue + document.getElementsByName('cid')[i].value + ',';
				checkBoxName = checkBoxName + $(".cidName")[i].textContent + ',';
			}
		}
	}
	if (pickRadio == 'false') {
			//同时跟新控件的值和模型的值，有些控件没有使用模型，如接口参数
			rootScope.$apply(function() {
				$("#"+pickTag).val(checkBoxValue);
				rootScope.model[pickTag] = checkBoxValue;
				if(pickTagName){
					checkBoxName = replaceAll(checkBoxName, "-", "");
					checkBoxName = replaceAll(checkBoxName, " ", "");
					$("#"+pickTagName).val(checkBoxName);
					rootScope.model[pickTagName] = checkBoxName;
				}
			});
	}
	// 回调函数
	if (pickCallBack) {
		if (pickCallBackParam) {
			pickCallBack(pickCallBackParam);
		} else {
			pickCallBack();
		}
	}
	// 关闭对话框
	iClose('lookUp');
}
/** *************选中显示菜单权限则回调隐藏模块*************** */
// 待删除
function needHiddenModule() {
	if ($("#type").val() == "SHOWMENU" || $("#type").val() == "USER"
			|| $("#type").val() == "MENU" || $("#type").val() == "ROLE") {
		iClose("roleModuleId");
	} else {
		iShow("roleModuleId");
	}
}
