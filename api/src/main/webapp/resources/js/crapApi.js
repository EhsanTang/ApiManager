/****************密码访问*****************/
function propUpPasswordDiv(obj){
	var msg = obj.textContent;
	if(msg.indexOf(NEED_PASSWORD_CODE)>=0){
        var obj = document.getElementById('passwordDiv');
        if (obj){
            lookUp('passwordDiv', '', 300, 300 ,6,'');
            showMessage('passwordDiv','false',false,-1);
            showMessage('fade','false',false,-1);
            changeimg('imgCode','verificationCode');
            $("#password").val('');
            $("#password").focus();
		}
	}else if(msg.indexOf(NEED_LOGIN)>=0 ){
		openPage('loginOrRegister.do#/login');
	}
}

// 数据字典改为拖动
// function upward(nowTr){
// 	var $tr = $(nowTr).parent().parent();
//     if ($tr.index() != 0) {
//       $tr.fadeOut(1).fadeIn(600);
//       $tr.prev().fadeOut(1).fadeIn(1000);
//       $tr.prev().before($tr);
//     }
// }
// function downward(nowTr){
// 	var $tr = $(nowTr).parent().parent();
//       $tr.fadeOut(1).fadeIn(600);
//       $tr.next().fadeOut(1).fadeIn(1000);
//       $tr.next().after($tr);
// }
/****************End****************/
function startLoadImg(){
    $('.img-lazy').not('[data-isLoading]').each(function () {
    	loadImg($(this));
    })
}
// 加载图片的函数，就是把自定义属性data-src 存储的真正的图片地址，赋值给src
function loadImg($img){
    if ($img == null){
        return;
    }
    $img.attr('src', $img.attr('img-src'));

    // 已经加载的图片，我给它设置一个属性，值为1，作为标识
    // 弄这个的初衷是因为，每次滚动的时候，所有的图片都会遍历一遍，这样有点浪费，所以做个标识，滚动的时候只遍历哪些还没有加载的图片
    $img.attr('data-isLoading',1);
}

function unescapeAndDecode(name){
	var value = $.cookie(name);
	if(value){
		return unescape($.base64.decode(value));
	}
	return "";
}
function getParamFromTable(tableId, requiredName) {
	var json = "[";
	var i = 0;
	var j = 0;
	$('#' + tableId).find('tbody').find('tr').each(function() {
        // 查看必填项是否填写，没填写则忽略该行
        var ignore = false;
        $(this).find('td').find('input').each(function(i, val) {
            if (val.name == requiredName && val.value == ''){
                ignore = true;
            }
        });
        if (!ignore){
            i = i + 1;
            j = 0;
            if (i != 1) {
                json += ",";
            }
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
        }
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
/** ***************pick控件搜索：暂时废弃*************** */
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
            $("#pickContent div").removeClass("main-color");
			$(obj).addClass("pickSelect");
            $(obj).addClass("main-color");
			select = 1;
		}
	}
}

// 重建索引
function rebuildIndex(obj){
	if (myConfirm("确定重建索引（安全，不会影响系统运行）？")) {
		selectButton(obj,'menu_a');
		callAjaxByName('iUrl=admin/rebuildIndex.do|iLoading=PROPUPFLOAT重建索引中，刷新页面可以查看实时进度...|ishowMethod=updateDivWithImg');
	}
}

function loginOut(){
    callAjaxByName("iUrl=user/loginOut.do|isHowMethod=updateDiv|iLoading=false|ishowMethod=doNothing|iAsync=false");
    location.reload();
}
//刷新缓存
function flushDB(obj){
	if (myConfirm("确定刷新缓存（安全，不会影响系统运行）？")) {
		selectButton(obj,'menu_a');
		callAjaxByName('iUrl=admin/flushDB.do|iLoading=TIPFLOAT刷新中，请稍后...|ishowMethod=updateDivWithImg');
	}
}

//删除30天前的日志
function cleanLog(obj){
    if (myConfirm("确定删除30天的日志（文档、接口等日志，删除后无法恢复）？")) {
        selectButton(obj,'menu_a');
        callAjaxByName('iUrl=admin/cleanLog.do|iLoading=TIPFLOAT删除中，请稍后...|ishowMethod=updateDivWithImg');
    }
}

//压缩css
function compress(obj){
	selectButton(obj,'menu_a');
	callAjaxByName('iUrl=admin/compress.do|iLoading=TIPFLOAT压缩中，请稍后...|ishowMethod=updateDivWithImg');
}
