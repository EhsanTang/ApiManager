app.factory('httpService', [ '$http', function($http) {
	return {
		callHttpMethod : function($http, params, iCallBack, iCallBackParam) {
			return callHttpName($http, params, iCallBack, iCallBackParam);
		}
	};
} ]);
function callHttpName($http, params, iCallBack, iCallBackParam) {
	var iUrl = getValue(params, 'iUrl');
	var iFormId = getValue(params, 'iFormId');
	var iPost = getValue(params, 'iPost');
	var iLoading = getValue(params, 'iLoading');
	var iTarget = getValue(params, 'iTarget');
	var iParams = getValue(params, 'iParams');
	return callHttp($http, iUrl, iFormId, iPost, iLoading, iTarget, iParams);
}

function callHttp($http, iUrl, iFormId, iPost, iLoading, iTarget, iParams) {
	iUrl=iUrl;
	iParams=iParams;
	iTarget = iTarget ? iTarget : 'lookUpContent';
	var xParams = "";
	if (iFormId) {
		xParams = $('#' + iFormId).serialize();
	}
	showTip(iTarget, iLoading);
	if (iPost) {
		return $http({
			method : 'POST',
			data : xParams + iParams,
			url : iUrl,
            async : true,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		});
	} else {
		if(iUrl.indexOf("?")>0)
			iUrl = iUrl +"&";
		else
			iUrl = iUrl +"?";
		return $http({
			method : 'GET',
			url : iUrl + xParams + iParams
		});
	}

}
function httpSuccess(data, iLoading, tipTime) {
	if(data.success==0){
		if(data.error.code == NEED_PASSWORD_CODE){
			lookUp('passwordDiv', '', 300, 300 ,6,'');
			showMessage('passwordDiv','false',false,-1);
			showMessage('fade','false',false,-1);
			changeimg('imgCode','verificationCode');
			$("#password").val('');
			$("#password").focus();
			data = "[ERROR][" + NEED_PASSWORD_CODE + "] "+data.error.message+"，点击请输入访问密码";
		}else if(data.error.code == NEED_LOGIN ){
            data = "[ERROR][" + NEED_LOGIN + "] "+data.error.message;
        }else{
			data = "[ERROR]"+data.error.message;
		}
	}
	else if (isJson(data)) {
		data = "[OK]";
	} else {
		data = data.responseText;
		/*************未登录或发生未知错误********************/
		if (data.indexOf('[ERRORPAGE]') >= 0) {
			data = "[ERROR]抱歉，系统繁忙，请稍后再试！";
		}
		if (data.indexOf('[LOGINPAGE]') >= 0) {
			data = "[ERROR]尚未登录，请登录后再试！";
		}
	}
	closeTip(data, iLoading, tipTime);
	return data;
}
function isJson(obj) {
	return (typeof (obj) == "object"
			&& Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length);
}