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

