// 修复ie8对trim不支持
String.prototype.trim = function () {
    return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

//回顶部
$("#go_top").click(function (){
	$("html, body").animate({ scrollTop: 0 }, 400);
});

//将指定id的控件滚动到浏览器顶部，如：接口详情页目录
function scrollToId(id){
	$("html, body").animate({ scrollTop: $("#"+id).offset().top }, 400);
}
function getMarkdownText(html){
	// 从markdown编辑器中提取文本
	return replaceAll(html,"ace_line_group","\">\n<\"").replace(/<[^>]+>/g,"") ;
}

/**
 * 替换字符串中自定的字符
 * @param originalStr 原字符串
 * @param oldStr 待替换的字符串
 * @param newStr 替换后的字符串
 */
function replaceAll(originalStr,oldStr,newStr){
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
function loadPick(event,iwidth,iheight,radio,tag,code,type,def,params,showType,iCallBack,iCallBackParam) { 
	/***********加载选择对话框********************/
	if(!params)
		params='';
	if(showType!=0&&!showType)
		showType=5
	//事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
	callAjaxByName("iUrl=pick.do|isHowMethod=updateDiv|iParams=&type="
			+type+"&radio="+radio+"&code="+code+"&tag="+tag+"&def="+def+params,iCallBack,iCallBackParam);
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
function uploadImgCallBack(msg, url) {
	if (msg.indexOf("[OK]") >= 0) {
		$("#image").attr("src", url + "");
		$("#image").removeClass("ndis");
		showMessage('lookUp', 'false', false, 0);
		if (url!= undefined) {
			//修改setting中的value
			var rootScope = getRootScope();
			rootScope.$apply(function () {          
			    rootScope.model.value = url;
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
//文章页面上传图片回调方法
function acticleUploadImgCallBack(msg, url) {
	if (msg.indexOf("[OK]") >= 0) {
		showMessage('lookUp', 'false', false, 0);
		if (url!= undefined) {
			//修改setting中的value
			var rootScope = getRootScope();
			rootScope.$apply(function () {  
				if(rootScope.model.content)
					rootScope.model.content =  rootScope.model.content + "<div class='tc'><img src='"+url+"' /></div>";
				else
					rootScope.model.content =  "<div class='tc'><img src='"+url+"' /></div>";
					
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
function selectButton(obj,className){
	window.editorId = new Date().getTime();
	var objs = $("."+className);
	objs.removeClass("iactive");
	$(obj).addClass("iactive");
}