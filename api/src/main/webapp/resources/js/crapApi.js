/****************密码访问*****************/
function propUpPsswordDiv(obj){
	var msg = obj.textContent;
	if(msg.indexOf("[000007]")>=0){
		lookUp('passwordDiv', '', 300, 300 ,6,'');
		showMessage('passwordDiv','false',false,-1);
		showMessage('fade','false',false,-1);
		changeimg('imgCode','verificationCode');
		$("#password").val('');
		$("#password").focus();
	}
}
/*****************接口添加参数**************/
function addOneParam(name, necessary, type, def, remark, rowNum, tableId) {
	if (!rowNum || rowNum == '') {
		var mydate = new Date();
		rowNum = mydate.getTime();
	}
	if(!def) def = "";
	if (tableId == 'editHeaderTable') {
		$("#editHeaderTable")
				.append(
						"<tr><td><input class='form-control' type='text' name='name' value='"
								+ name
								+ "' placeholder=\"参数名：必填\"></td>"
								+ "<td><input class='form-control' type='text' name='necessary' id='necessary"
								+ rowNum
								+ "' value='"
								+ necessary
								+ "'"
								+ "onfocus=\"loadPick(event,200,250,'true','necessary"
								+ rowNum
								+ "','TRUEORFALSE','','"
								+ necessary
								+ "','',5);\" placeholder=\"点击输入框选择\"></td>"
								+ "<td><input class='form-control' type='text' name='type' value='"
								+ type
								+ "' placeholder=\"类型：必填\"></td>"
								+ "<td><input class='form-control' type='text' name='def' value='"
								+ def
								+ "' placeholder=\"默认值\"></td>"
								+ "<td><input class='form-control' type='text' name='remark' value='"
								+ remark
								+ "'></td>"
								+ "<td class='cursor text-danger'><i class='iconfont' onclick='deleteOneParam(this)'>&#xe60e;</i></td>"
								+ "</tr>");
	}else if (tableId == 'editParamTable') {
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
								+ "onfocus=\"loadPick(event,200,250,'true','necessary"
								+ rowNum
								+ "','TRUEORFALSE','','"
								+ necessary
								+ "','',5);\" placeholder=\"点击输入框选择\"></td>"
								+ "<td><input class='form-control' type='text' name='type' value='"
								+ type
								+ "' placeholder=\"类型：必填\"></td>"
								+ "<td><input class='form-control' type='text' name='def' value='"
								+ def
								+ "' placeholder=\"默认值\"></td>"
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
/****************添加数据字典****************/
function addOneField(name, type, notNull,def, remark, rowNum) {
	if (!rowNum || rowNum == '') {
		var mydate = new Date();
		rowNum = mydate.getTime();
	}
		$("#content")
				.append("<tr>"
						+"<td><input class='form-control' type='text' name='name' value='"+ name + "' placeholder=\"字段名\"></td>"
						+"<td><input class='form-control' type='text' name='type' value='"+ type + "' placeholder=\"类型\"></td>"
						+"<td><input class='form-control' type='text' name='notNull' value='"+ notNull + "' placeholder=\"是否可为空\"></td>"
						+"<td><input class='form-control' type='text' name='def' value='"+ def + "' placeholder=\"默认值\"></td>"
						+"<td><input class='form-control' type='text' name='remark' value='"+ remark + "' placeholder=\"注释\"></td>"
						+ "<td class='cursor text-danger'><i class='iconfont' onclick='deleteOneParam(this)'>&#xe60e;</i></td>"
						+"</tr>");
}

/****************End****************/
function deleteOneParam(nowTr) {
	$(nowTr).parent().parent().remove();
}

function unescapeAndDecode(name){
	var value = $.cookie(name);
	if(value){
		return unescape($.base64.decode(value));
	}
	return "";
}
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
		$(this).find('td').find('input').each(function(i, val) {
				j = j + 1;
				if (j != 1)
					json += ",";
				json += "\"" + val.name + "\":\"" + replaceAll(val.value,'"','\\"') + "\""
		});
		json += "}"
	});
	json += "]";
	return json;
}
/************输入接口访问密码***********/
function setPassword(){
	$.cookie('password', $.base64.encode(escape($("#password").val())));
	$.cookie('visitCode', $.base64.encode(escape($("#visitCode").val())));
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
						if (navigateText.trim().length == 0) {
							$("#pickTip").css("display", "none");
						}
						var tHandler = "pickScroll('" + navigateText + "')";
						setTimeout(tHandler, 500);
						return false;// return false表示该事件不再往下传递
					} else if (event.keyCode != 13) {
						navigateText += String.fromCharCode(event.keyCode);
						if (lookUp.style.display != 'block')
							navigateText = "";
						navigateText = navigateText.trim();
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
	if (navigateText.trim().length > 0) {
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
	var stateParams = getStateParams();
	for (var i = 0; i < length; i++) {
		if (pickRadio == 'true') {
			if (document.getElementsByName('cid')[i].checked == true) {
				rootScope.$apply(function() {
					if(pickTagName){
						$("#"+pickTagName).val($(".cidName")[i].textContent);
						if(rootScope.model)
							rootScope.model[pickTagName] = $(".cidName")[i].textContent;
					}
					$("#"+pickTag).val(document.getElementsByName('cid')[i].value);
					stateParams[pickTag] = document.getElementsByName('cid')[i].value;
					if(rootScope.model)
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
// 创建kindEditory
// 子页面加载一次，需要初始化编辑器（点击左边菜单是更新editorId）
function createKindEditor(id,modelField){
	var root = getRootScope();
	if(window.oldEditorId != window.editorId || window.editor == null){
		oldEditorId = editorId;
		window.editor =  KindEditor.create('#'+id,{
	        uploadJson : 'file/upload.do',
	        filePostName: 'img',
	        allowFileManager : true,
	        afterBlur: function () { 
	        	editor.sync();
	        	root.model[modelField] = $('#'+id).val();
	        }
		});
	}
	window.editor.html(root.model[modelField]);
	changeDisplay('kindEditor','defEditor')
}
