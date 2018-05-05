/****************密码访问*****************/
function propUpPsswordDiv(obj){
	var msg = obj.textContent;
	if(msg.indexOf(INVALID_PASSWORD_CODE)>=0 || msg.indexOf(NEED_PASSWORD_CODE)>=0){
		lookUp('passwordDiv', '', 300, 300 ,6,'');
		showMessage('passwordDiv','false',false,-1);
		showMessage('fade','false',false,-1);
		changeimg('imgCode','verificationCode');
		$("#password").val('');
		$("#password").focus();
	}
}

/****************数据字典****************/
function addOneField(name, type, notNull,flag, def, remark, rowNum) {
	if (!rowNum || rowNum == '') {
		var mydate = new Date();
		rowNum = mydate.getTime();
	}
		$("#content")
				.append("<tr>"
						+"<td><input class='form-control' type='text' name='name' value='"+ name + "' placeholder=\"字段名\"></td>"
						+"<td><input class='form-control' type='text' name='type' value='"+ type + "' placeholder=\"类型\"></td>"
						+"<td><select name='notNull' class='form-control'><option value='true'"+ (notNull=='true' ? " selected":"") + ">true</option><option value='false'"+ (notNull=='true' ? "":" selected") +">false</option></select></td>"
						+"<td><input class='form-control' type='text' name='def' value='"+ def + "' placeholder=\"默认值\"></td>"
						+"<td><select name='flag' class='form-control'><option value='common'"+ ( flag == 'common'  ? " selected":"") + ">普通</option><option value='primary'"+ ( flag == 'primary'  ? " selected":"") + ">主键</option><option value='foreign'"+ (flag == 'foreign' ? " selected":"") +">外键</option><option value='associate'"+ ( flag == 'associate'  ? " selected":"") + ">关联</option></select></td>"
						+"<td><input class='form-control' type='text' name='remark' value='"+ remark + "' placeholder=\"注释\"></td>"
						+"<td class='cursor text-danger'>"
						+		"<i class='iconfont' onclick='deleteOneParam(this)'>&#xe60e;</i>&nbsp;&nbsp; "
						+		"<i class='iconfont' onclick='upward(this)'>&#xe623;</i>&nbsp;&nbsp;"
						+		"<i class='iconfont' onclick='downward(this)'>&#xe624;</i>"
						+"</td>"
						+"</tr>");
}

function deleteOneParam(nowTr) {
	$(nowTr).parent().parent().remove();
}

function upward(nowTr){
	var $tr = $(nowTr).parent().parent(); 
    if ($tr.index() != 0) { 
      $tr.fadeOut(1).fadeIn(600); 
      $tr.prev().fadeOut(1).fadeIn(1000); 
      $tr.prev().before($tr); 
    } 
}
function downward(nowTr){
	var $tr = $(nowTr).parent().parent(); 
      $tr.fadeOut(1).fadeIn(600);  
      $tr.next().fadeOut(1).fadeIn(1000); 
      $tr.next().after($tr); 
}
/****************End****************/

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
		$(this).find('td').find('select').each(function(i, val) {
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
    if (getRootScope().error && getRootScope().error.indexOf(NEED_PASSWORD_CODE) > 0){
        getRootScope().error = "";
	}
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
	for (var i = 0; i < length; i++) {
		if (pickRadio == 'true') {
			if (document.getElementsByName('cid')[i].checked == true) {
				rootScope.$apply(function() {
					if(pickTagName){
						$("#"+pickTagName).val($(".cidName")[i].textContent);
						if(rootScope.model) {
                            rootScope.model[pickTagName] = $(".cidName")[i].textContent;
                        }
					}
					$("#"+pickTag).val(document.getElementsByName('cid')[i].value);
					if(rootScope.model) {
                        rootScope.model[pickTag] = document.getElementsByName('cid')[i].value;
                    }
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
		if(window.editorId)
			window.oldEditorId = window.editorId;
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
// 保存markdown
function saveMarkdown(markdown,content){
	var rootScope = getRootScope();
	rootScope.$apply(function () {    
	    rootScope.model[markdown] = getMarkdownText( $(window.frames["markdownFrame"].document).find('.ace_text-layer').html() );
	    rootScope.model[content] = $(window.frames["markdownFrame"].document).find('#preview').html();
	});
	closeMyDialog('markdownDialog');
}
// 重建索引
function rebuildIndex(obj){
	if (myConfirm("确定重建索引？")) {
		selectButton(obj,'menu-a');
		callAjaxByName('iUrl=back/rebuildIndex.do|iLoading=PROPUPFLOAT重建索引中，刷新页面可以查看实时进度...|ishowMethod=updateDivWithImg');
	}
}

function loginOut(){
    callAjaxByName("iUrl=back/loginOut.do|isHowMethod=updateDiv|iLoading=false|ishowMethod=doNothing|iAsync=false");
    location.reload();
}
//刷新缓存
function flushDB(obj){
	if (myConfirm("确定刷新缓存？")) {
		selectButton(obj,'menu-a');
		callAjaxByName('iUrl=back/flushDB.do|iLoading=TIPFLOAT刷新中，请稍后...|ishowMethod=updateDivWithImg');
	}
}
