/**
 * 
 * @param id 需要验证的文本域id
 * @param min 最小长度，-1表示不限
 * @param max 最大长度，-1表示不限
 * @param message 不为空时验证失败会调用alert提示
 * @returns {Boolean}
 */
/***********************基础验证**********************/
function iLength(id,min,max,message){
	var length=$("#"+id).val().trim().length;
	if((min!=-1&&length<min)||(max!=-1&&length>max)){
		$("#"+id).focus();
		if(message!='')
			alert(message);
		return false;
	}
	return true;
}
function iCompare(id,min,max,message){
	if(!iDouble(id,"输入只能为数字"))
		return false;
	var value=$("#"+id).val();
	if((value<min)||(value>max)){
		$("#"+id).focus();
		if(message!='')
			alert(message);
		return false;
	}
	return true;
}
/**
 * 当传入的值为空时，返回true
 * @param id
 * @param message 不为空时验证失败会提调用alert提示
 * @returns {Boolean}
 */
function iShuzi(id,message){
	if($("#"+id).val().length==0)
		return true;
	if(isNaN($('#'+id).val())){
		$("#"+id).focus();
		if(message!='')
			alert(message);
		return false;
	}
	return true;
}
function iDouble(id,message){
	try{
		var number = $('#'+id).val();
		if(parseFloat(number)!=number){
			$("#"+id).focus();
			if(message!='')
				alert(message);
			return false;
		}else{
			$('#'+id).val(parseFloat(number));
			return true;
		}
	} catch (ex) {
		$("#"+id).focus();
		if(message!='')
			alert(message);
		return false;
	}
}
function iInt(id,message){
	try{
		var number = $('#'+id).val();
		if(parseInt(number)!=number){
			$("#"+id).focus();
			if(message!='')
				alert(message);
			return false;
		}else{
			$('#'+id).val(parseInt(number));
			return true;
		}
	} catch (ex) {
		$("#"+id).focus();
		if(message!='')
			alert(message);
		return false;
	}
}

function equals(str1,str2){
	if(str1==str2){
		return true;
	}else{
		return false;
	}
}
/*非空判断 */
this.isNull = function(str){
	if(str==""){
		return true;
	}
	var regu = /^[\s'　']*$/;
	var re = new RegExp(regu);
	return re.test(str);
};
/***********************************************/
var ue;//百度编辑器
function addArticleValidate(){
	if(!iLength("add_article_title",5,100,"文章标题不能为空，长度范围为5-100，保存失败"))
		return false;
	if(!iLength("add_article_author",2,25,"作者不能为空，长度范围为2-25，保存失败"))
		return false;
	if(!iLength("add_article_summary",10,200,"文章摘要不能为空，长度范围为10-200，保存失败"))
		return false;
	if(!iLength("add_article_content",1,32767,"")){
		try{
			if(ue.getContent().trim().length<1){
				alert("正文不能为空，保存失败!");
				return false;
			}else if(ue.getContent().trim().length>32767){
				alert("正文长度不能超过32767，保存失败!");
				return false;
			}
		}catch(e){
			alert("正文不能为空，保存失败!");
			return false;
		}
	}
	return true;
}
function addArticleShareValidate(){
	if(!iLength("add_share_name",2,50,"封皮名称不能为空，长度范围为2-50，保存失败"))
		return false;
	if(!iLength("add_share_title",5,100,"分享标题不能为空，长度范围为5-100，保存失败"))
		return false;
	if(!iLength("add_share_summary",5,100,"摘要不能为空，长度范围为5-100，保存失败"))
		return false;
	var summary = $("#add_share_summary").val().replace(/[\r\n|'|"]/g,"");
	$("#add_share_summary").val(summary);
	$("#add_share_title").val($("#add_share_title").val().replace(/['|"]/g,""));
	$("#add_share_name").val($("#add_share_name").val().replace(/['|"]/g,""));
	return true;
}
function addCorpValidate(_confId_){
	if(!iLength("add_corpName"+_confId_,2,50,"企业名称不能为空，长度为2-50"))
		return false;
	if(!iLength("add_corpCode"+_confId_,2,32,"企业Code不能为空，长度为2-32"))
		return false;
	if(!iCompare("add_corpRebates"+_confId_,0,100,"企业返点不能为空，范围为0-100"))
		return false;
	if(!iCompare("add_newUserRebates"+_confId_,0,100,"用户返点不能为空，范围为0-100"))
		return false;
	if(!iLength("add_startDay"+_confId_,10,10,"合同开始时间格式输入有误"))
		return false;
	if(!iLength("add_endDay"+_confId_,10,10,"合同结束时间格式输入有误"))
		return false;
	if($("#add_startDay"+_confId_).val()>$("#add_endDay"+_confId_).val()){
		alert("合同结束时间必须大于开始时间");
		return false;
	}
	return true;
}
function updateCorpValidate(_confId_){
	if(!iLength("update_startDay"+_confId_,10,10,"合同开始时间输入有误"))
		return false;
	if(!iLength("update_endDay"+_confId_,10,10,"合同结束时间输入有误"))
		return false;
	if($("#update_startDay"+_confId_).val()>$("#update_endDay"+_confId_).val()){
		alert("合同结束时间必须大于开始时间");
		return false;
	}
	var endDay = $("#update_endDay"+_confId_).val();
	var currDate = new Date();
	var year = currDate.getFullYear();
    var month = currDate.getMonth() + 2;
    if (month == 13) {
    	year = parseInt(year) + 1;
        month = 1;
    }
    if (month < 10) {
        month = '0' + month;
    }
	if(endDay<=(year+"-"+month+"-01")){
		alert("修改无效!合同结束时间必须大于下个月1号");
		return false;
	}
	if(!iLength("update_updateBy"+_confId_,1,25,"修改人不能为空，长度为1-25"))
		return false;
	if(!iLength("update_updateDesc"+_confId_,1,128,"备忘不能为空，长度为1-128"))
		return false;
	return true;
}

function updateRebatesValidate(_confId_){
	if(!iLength("update_corpRebates"+_confId_,1,-1,"企业返点比例不能为空！"))
		return false;
	if(!iLength("update_newUserRebates"+_confId_,1,-1,"用户返点比例不能为空！"))
		return false;
	if(!iCompare("update_corpRebates"+_confId_,0,100,"企业返点范围为0-100"))
		return false;
	if(!iCompare("update_newUserRebates"+_confId_,0,100,"用户返点范围为0-100"))
		return false;
	if(!iLength("update_updateBy"+_confId_,1,25,"修改人不能为空，长度为1-25"))
		return false;
	if(!iLength("update_updateDesc"+_confId_,1,128,"备忘不能为空，长度为1-128"))
		return false;
	return true;
}
function addIncomeUserValidate(){
	if(!iLength("add_incomeUser_name",2,20,"用户名不能为空，长度为2-20")){
		return false;
	}
	return true;
}
function loginValidate(){
	if(!iLength("userName",2,20,"")){
		showMessage("warnMessage","用户名不能为空，长度为2-20",true,3);
		return false;
	}
	if(!iLength("userPassword",5,20,"")){
		showMessage("warnMessage","密码不能为空，长度为5-20",true,3);
		return false;
	}
	return true;
}

