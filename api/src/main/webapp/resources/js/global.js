// 修复ie8对trim不支持
String.prototype.trim = function () {
    return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

//回顶部
function goTop(){
	$("html, body").animate({ scrollTop: 0 }, 400);
}

//将指定id的控件滚动到浏览器顶部，如：接口详情页目录
function scrollToId(id) {
    $("html, body").animate({scrollTop: $("#" + id).offset().top}, 400);
}
function scrollToIdByParentId(parentId, id, adjustPix) {
    var mainContainer = $('#' + parentId);
    var scrollToContainer =  $('#' + id);
    mainContainer.animate({scrollTop: scrollToContainer.offset().top - mainContainer.offset().top + mainContainer.scrollTop() + adjustPix}, 200);
}
function tooltip(id){
	 $("[data-toggle='tooltip']").tooltip(); 
	 $("#"+id).tooltip('show');
}
// 获取url中的指定参数
function getParamFromUrl(url, name) {
	if (url.indexOf('?') <=0 ){
		return null;
	}
    url = url.substring(url.indexOf('?') + 1);
    var parameters = url.split('&');
    for (var i = 0; i < parameters.length; i++) {
    	if(parameters[i].split('=').length == 2 && parameters[i].split('=')[0] == name){
    		return parameters[i].split('=')[1];
		}
    }
    return null;
}
// 替换url中的指定参数
function replaceParamFromUrl(url, name, value) {
    var oldValue = getParamFromUrl(url, name);
    if (oldValue){
    	url = replaceAll(url, name + "=" + oldValue, name + "=" + value);
	} else if (url.indexOf('?') <=0 ){
        url = url + "?" + name + "=" + value;
    } else {
        url = url + "&" + name + "=" + value;
    }
    return url;
}

/**
 * 替换字符串中自定的字符
 * @param originalStr 原字符串
 * @param oldStr 待替换的字符串
 * @param newStr 替换后的字符串
 */
function replaceAll(originalStr,oldStr,newStr){
	if (!newStr){
		newStr = '';
	}
	var regExp = new RegExp(oldStr,"gm");
	return originalStr.replace(regExp,newStr)
}

/***********启用bootstrap提示*********************/
$(function () {
	  $('[data-toggle="tooltip"]').tooltip();
});
/************显示id1，隐藏id2*********************/
function changeDisplay(id1, id2, id3, id4) {
	$("#" + id2).addClass('none');
	$("#" + id1).removeClass('none');
	if(id3) $("#" + id3).addClass('none');
	if(id4) $("#" + id4).addClass('none');
};
/**********打开Dialog******************/
function openMyDialog(title,iwidth){
	if(!iwidth){
		iwidth = 400;
	}
	//对话框最高为浏览器的百分之80
	lookUp('myDialog', '', '', iwidth ,7,'');
	$("#myDialogContent").css("max-height",($(document).height()*0.8)+'px');
	showMessage('myDialog','false',false,-1);
	showMessage('fade','false',false,-1);
	title = title? title:"编辑";
	$("#myDialog-title").html(title);
}
function closeMyDialog(tagDiv){
	iClose(tagDiv);
	iClose('fade');
}


var dialogOldTop;
var dialogOldLeft;
var dialogOldHeight;
var dialogOldWidth;
var oldMaxHeight;
function fullMyDialog(tagDiv,tagDivContent){
	var target = $("#"+tagDiv);
	if( target.css('top') != '0px'){
		dialogOldTop = target.css('top');
		dialogOldLeft = target.css('left');
		dialogOldHeight = target.css('height');
		dialogOldWidth = target.css('width');
		$("#"+tagDiv).css("top","0px");
		$("#"+tagDiv).css("left","0px");
		$("#"+tagDiv).css("height","100%");
		$("#"+tagDiv).css("width","100%");
        $("#"+tagDiv).css("width","100%");
        if (tagDivContent) {
            oldMaxHeight = $("#" + tagDivContent).maxHeight;
            $("#" + tagDivContent).css("max-height", "100%");
        }
	}else{
		$("#"+tagDiv).css("top",dialogOldTop);
		$("#"+tagDiv).css("left",dialogOldLeft);
		$("#"+tagDiv).css("height",dialogOldHeight);
		$("#"+tagDiv).css("width",dialogOldWidth);
        if (tagDivContent) {
            $("#" + tagDivContent).css("max-height", oldMaxHeight);
        }
	}
}
function loadPick(event,iwidth,iheight,radio,tag,code,def,params,showType,iCallBack,iCallBackParam) {
	/***********加载选择对话框********************/
	if(!params)
		params='';
	if(showType!=0&&!showType)
		showType=5
	//事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
	callAjaxByName("iUrl=pick.do|isHowMethod=updateDiv|iParams=&radio="+radio+"&code="+code+"&tag="+tag+"&def="+def+params,iCallBack,iCallBackParam);
	lookUp('lookUp', event, iheight, iwidth ,showType,tag);
	showMessage('lookUp','false',false,-1);
}

/**
 * size 单位为kb
 * @param event
 * @param id
 * @param size
 * @returns {Boolean}
 */
function uploadImage(id,size,form){
	 if(!iLength(id,1,-1,"未选着图片，上传失败")){
		return false;
	 }
     var fileSize =document.getElementById(id).files[0].size;
     if(size > 0 && fileSize>size*1024){       
          alert("图片不能大于"+size+"kb,约"+(Math.round(size*1000/1024)/1000)+"M"); 
          return false; 
     } 
	lookUp('lookUp','',100,350,0); 
	$("#lookUpContent").html("上传中，请稍后...");
	showMessage('lookUp', 'false', false, -1);
	form.submit();
}
//上传图片非编辑器默认回调方法
function uploadImgCallBack(msg, url, property) {
	if (msg.indexOf("[OK]") >= 0) {
		$("#image").attr("src", url + "");
		$("#image").removeClass("ndis");
		showMessage('lookUp', 'false', false, 0);
		if (url!= undefined) {
			//修改setting中的value
			var rootScope = getRootScope();
			rootScope.$apply(function () {  
				rootScope.model[property] = url;
			});
		}
	}else {
		$("#lookUpContent").html(err1 + "&nbsp; " + url + "" + err2);
		showMessage('lookUp', 'false', false, 3);
	}
}

//文档管理上传文件回调方法
function uploadFileCallBack(msg, url) {
	if (msg.indexOf("[OK]") >= 0) {
		showMessage('lookUp', 'false', false, 0);
		if (url!= undefined) {
			//修改source中的filePath
			var rootScope = getRootScope();
			rootScope.$apply(function () {          
			    rootScope.model.filePath = url;
			    // 获取文件原名
			    if(!rootScope.model.name){
			    	rootScope.model.name = $("#filePath").val().substring($("#filePath").val().lastIndexOf("\\")+1);
			    }
			});
		}
	}else {
		$("#lookUpContent").html(err1 + "&nbsp; " + url + "" + err2);
		showMessage('lookUp', 'false', false, 3);
	}
}

/**
 * 图片id 验证码输入框id
 */
function changeimg(imgId,inputId){
	try{
		document.getElementById(imgId).src='getImgCode.do?'+Math.random(); 
		document.getElementById(inputId).focus();
	}catch(ex){}
	return false;
}
/*************************js调用anjularjs 获取$rootScope****************/
function getRootScope(){
	var $body = angular.element(document.body);
	return $body.scope().$root;
}
function getStateParams(){
	var $body = angular.element(document.body);
	return $body.scope().$stateParams;
}




function initDatePicker(id){
	if($('#'+id)){
		$('#'+id).datetimepicker({
			language:  'zh-CN',
	        weekStart: 1,
			autoclose: 1,
			startView: 2,
			minView: 2,
			forceParse: 0,
		    format: 'yyyy-mm-dd'
		});
	}
}
function initDatePicker2(id){
	if($('#'+id)){
		$('#'+id).datetimepicker({
			language:  'zh-CN',
	        weekStart: 1,
			autoclose: 1,
			startView: 2,
			minView: 1,
			forceParse: 0,
		    format: 'yyyy-mm-dd hh'
		});
	}
}

function addCookieToParams(key,value){
	var params = $.cookie('params');
	params= addParams(params, key , value);
	$.cookie('params', params);
}
function clearCookie(params){
	$.cookie(params, "");
}

function addCookie(key,value){
	$.cookie(key, value);
}
function getCookie(key){
	return $.cookie(key);
}





/** ************刷新验证吗*********** */
/*******************清空表单，除了nots(,#id)*****************/
function clearForm(id,nots){
	$(':input','#'+id)  
	.not(':button, :submit, :reset'+nots)  
	.val('')  
	.removeAttr('checked')  
	.removeAttr('selected');  
}


/**
 * 单选
 */
/** ********************************** */
function selectRadio(className,id,radioId){
	var objs = $("."+className);
	objs.removeClass("active");
	var obj = $("#"+id);
	var cobj = $("#"+radioId);
	if(!obj.hasClass("active")){
		obj.addClass("active"); 
		$(cobj).prop("checked",true);
	}
}

/** ********************************** */
function selectButton(obj,className, activeCss){
	if (!activeCss){
        activeCss = "iactive";
	}
	window.editorId = new Date().getTime();
	var objs = $("."+className);
	objs.removeClass(activeCss);
	$(obj).addClass(activeCss);
}

function myConfirm(message){
    var begin = Date.now();
    var result = window.confirm(message);
    var end = Date.now();
    if (end - begin < 10) {
    	$("#global-error").text("Please do not disable popups,it's dangerous!「请勿禁用【确认】弹窗，直接操作非常危险」");
    	$("#global-error").removeClass("ndis");
        return true;
    }
    return result;
}
function sleep(numberMillis) { 
	   var now = new Date();
	   var exitTime = now.getTime() + numberMillis;  
	   while (true) { 
	       now = new Date(); 
	       if (now.getTime() > exitTime)    return;
	    }
}

var tipMessage = "        .----.\n" +
    "       _.'__    `.\n" +
    "   .--($$)($$)--/#\\\n" +
    " .' @          /###\\\n" +
    " :         ,   #####\n" +
    "  `-..__.-' _.-###/\n" +
    "        `:_:    `\"'\n" +
    "      .'\"\"\"\"\"'.\n" +
    "     //  CRAP \\\\\n" +
    "     //  API!  \\\\\n" +
    "    `-._______.-'\n" +
    "    ___`. | .'___\n" +
    "   (______|______)\n";
/**
 * 立即运行函数，用来检测控制台是否打开
 */
!function () {
    // 创建一个对象
    var foo = /./;
    var i = 0;
    // 将其打印到控制台上，实际上是一个指针
    console.info(foo);
    foo.toString =  function () {
        i++;
        var tip = '';
        for (var time=1; time < i; time++){
            tip = tip + '又';
        }
        if (i==1) {
            console.info('~ 想查看源代码？来这啊↩ ~');
            console.info('~ https://github.com/EhsanTang/ApiManager ~');
            console.info('~ https://gitee.com/CrapApi/CrapApi ~');
            console.info('~ 完全开源、免费，记得star、fork再走哦 ~');
            console.info('~ 视频介绍、安装部署...，请前往官网 http://api.crap.cn ~');
        }else{
            console.info('~ 你' + tip + '来了，star、fork了吗 ~');
        }
        return tipMessage;
    };
    // 要在第一次打印完之后再重写toString方法
}();

// 进入默认地址
(function () {
    var href = location.href;
    if (href.indexOf("#") <= 0 && href.indexOf("admin.do") > 0){
        location.href = href + URL_LIST[MY_PROJECT];
    }
})();

// 百度统计
var _hmt = _hmt || [];
(function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?500545bbc75c658703f93ac984e1d0e6";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();
