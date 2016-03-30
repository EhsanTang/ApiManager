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

/**********打开model******************/
function openModal(title,iwidth){
	 title = title? title:"编辑";
	 if(iwidth){
		 $("#modal-dialog").width(iwidth);
	 }else{
		 $("#modal-dialog").width(400);
	 }
	 $(".modal-title").html(title);
	 $("#myModal").modal("show");
}
function closeModeal(){
	$(".modal-header>.close").click();
}

function loadPick(event,mywidth,myheight,radio,tag,code,type,def,params,showType,iCallBack,iCallBackParam) { 
	/***********加载选择对话框********************/
	if(!params)
		params='';
	if(showType!=0&&!showType)
		showType=5
	//事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
	callAjaxByName("iUrl=pick.do|isHowMethod=updateDiv|iParams=&type="
			+type+"&radio="+radio+"&code="+code+"&tag="+tag+"&def="+def+params,iCallBack,iCallBackParam);
	lookUp('lookUp', event, myheight, mywidth ,showType,tag);
	showMessage('lookUp','false',false,-1);
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
     if(fileSize>size*1024){       
          alert("图片不能大于"+size+"kb,约"+(Math.round(size*1000/1024)/1000)+"M"); 
          return false; 
     } 
	lookUp('lookUp',event,100,350,0); 
	$("#lookUpContent").html("上传中，请稍后...");
	showMessage('lookUp', 'false', false, -1);
	form.submit();
}
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
/*************************js调用anjularjs 获取$rootScope****************/
function getRootScope(){
	var $body = angular.element(document.body);
	return $body.scope().$root;
}








function addCookieToParams(key,value){
	var params = $.cookie('params');
	params= addParams(params, key , value);
	$.cookie('params', params);
}
function clearCookie(params){
	$.cookie(params, "");
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
 * 图片id 验证码输入框id
 */
function changeimg(imgId,inputId){
	document.getElementById(imgId).src='authImg.img?'+Math.random(); 
	try{
		document.getElementById(inputId).focus();
	}catch(ex){}
	return false;
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