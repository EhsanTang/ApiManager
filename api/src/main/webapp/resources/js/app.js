var app = angular.module('app', [ 'ui.router', 'adminModule', 'userModule', 'bugModule', 'projectMetaModule', 'commonModule', 'commentModule', 'visitorModule']);
var visitorModule = angular.module("visitorModule", []);
var userModule = angular.module("userModule", []);
var adminModule = angular.module("adminModule", []);
var commentModule = angular.module("commentModule", []);
var bugModule = angular.module("bugModule", []);
var projectMetaModule = angular.module("projectMetaModule", []);
var commonModule = angular.module("commonModule", []);


var NEED_PASSWORD_CODE = "E000007";
var NEED_LOGIN = "E000021";

app.filter("removeLast",function(){
    return function (value, needRemoveChar) {
        if(!value) {
            return "";
        }else if (needRemoveChar){
            if (value.endsWith(needRemoveChar)){
                return value.substring(0,value.length-1);
			}else {
                return value;
			}
        }else {
            return value.substring(0,value.length-1);
		}
    }
});
//以html形式输出
app.filter("trustHtml",function($sce){
    return function (input){ return $sce.trustAsHtml(input); } ;
});

// 背景色
app.filter("getClass",function(){
    return function (value) {
        if(value=='primary') {
            return "bg-danger";
        }
        else if(value=='foreign') {
            return "bg-success";
        }
        else if(value=='associate') {
            return "bg-info";
        }
        else {
            return "";
        }
    }
});

// flag等转中文
app.filter("toChinese",function(){
    return function (value) {
        if(value=='primary')
            return "主键";
        else if(value=='foreign')
            return "外键";
        else if(value=='associate')
            return "关联";
        else
            return "普通";
    }
});

app.filter("getUrl",function($stateParams){
	return function (value) {
	    if (!value){
	        return "";
        }
        var url = URL_LIST[value];
	    if (!url){
	        return "";
        }
	    return url;
	}
});

/**
 * 由于整个应用都会和路由打交道，所以这里把$state和$stateParams这两个对象放到$rootScope上，方便其它地方引用和注入。
 * 这里的run方法只会在angular启动的时候运行一次。
 * @param  {[type]} $rootScope
 * @param  {[type]} $state
 * @param  {[type]} $stateParams
 * @return {[type]}
 */
app.run(function($rootScope, $state, $stateParams, $location, $http, $timeout,httpService) {
	$rootScope.$state = $state;
	$rootScope.$stateParams = $stateParams;
	$rootScope.pick = [];
    $rootScope.stopPropagation = function (e) {
        e.stopPropagation();
       return false;
    }
    $rootScope.goBack = function goBack(){
        history.back(-1);
    }
    
    $rootScope.reload = function reload(){
        location.reload();
    }

    /**
     * 带前缀的跳转会失败，如：index.do#/user....
     * @param href
     */
    $rootScope.go = function (href) {
        var href = replaceParamFromUrl(href, 'timestamp', new Date().getTime());
        $location.url(href);
    }

    $rootScope.goAbsoluteUrl = function (href) {
        var href = replaceParamFromUrl(href, 'timestamp', new Date().getTime());
        location.href=href;
    }

    /***
	 * 废弃，采用对象
	 * @deprecated
     * @param params
     * @param event
     * @param iCallBack
     * @param iCallBackParam
     */
	$rootScope.loadPickByName = function loadPick(params,event,iCallBack,iCallBackParam) { 
		var iwidth = getValue(params,'iwidth');
		var iheight = getValue(params,'iheight');
		var radio = getValue(params,'radio');
		var tag = getValue(params,'tag');
		var code = getValue(params,'code');
		var type = getValue(params,'type');
		var iparams = getValue(params,'params');
		var showType = getValue(params,'showType');
		var def = getValue(params,'def');
		var tagName = getValue(params,'tagName');
		var iUrl = getValue(params,'iUrl');
		$rootScope.loadPick(event,iwidth,iheight,radio,tag,code,type,def,iparams,showType,iCallBack,iCallBackParam,tagName,iUrl);
	}
	$rootScope.loadPick = function loadPick(event,iwidth,iheight,radio,tag,code,type,def,params,showType,iCallBack,iCallBackParam,tagName,iUrl) { 
		/***********加载选择对话框********************/
		if(!iUrl) {
            iUrl = "pick.do";
        }
		if(!params) {
            params = '';
        }
		if(!tagName) {
            tagName = '';
        }
		if(showType!='0'){
			if(!showType||showType=='') {
                showType = 5;
            }
		}
		$("#pickContent").html(loadText);
		//事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
		callAjaxByName("iUrl="+iUrl+"|isHowMethod=updateDiv|iPost=POST|iParams=&type="
				+type+"&radio="+radio+"&code="+code+"&tag="+tag+"&tagName="+tagName+"&def="+def+params,iCallBack,iCallBackParam);
		if(tagName) {
            lookUp('lookUp', event, iheight, iwidth, showType, tagName);
        }else {
            lookUp('lookUp', event, iheight, iwidth, showType, tag);
        }
		showMessage('lookUp','false',false,-1);
	}

	$rootScope.getBaseData = function($scope,$http,params,page) {
		if(page) {
            params += "&currentPage=" + page;
        }

		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT','0');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.list = null;
			 }else{
				 $rootScope.error = null;
                 $rootScope.list = result.data;
				 $rootScope.page = result.page;
				 $rootScope.others=result.others;
				 $rootScope.deleteIds = ",";
			 }
		}).error(function(result) {
			lookUp('lookUp','',100,300,3);
			closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
			$rootScope.error = result;

		});;
    };

    $rootScope.getBaseDataToDataKey = function($scope,$http,params,page,dataKey,callBack) {
        if(!page) {
            page = 1;
        }
        params += "&currentPage=" + page;
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading=FLOAT','0');
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
            }else{
                $rootScope.error = null;
                if (dataKey && dataKey != null){
                    $rootScope[dataKey] = result.data;
                    if (!$rootScope.page){
                        $rootScope.page = {};
                    }
                    $rootScope.page[dataKey] = result.page;
				}

                if (callBack){
                    callBack();
                }
            }
        }).error(function(result) {
            lookUp('lookUp','',100,300,3);
            closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
            $rootScope.error = result;

        });
    };

	$rootScope.detail = function(title,iwidth,iurl,iParams,callBack) {
			//打开编辑对话框
			openMyDialog(title,iwidth);
			var params = "iUrl="+iurl+"|iLoading=FLOAT";
			if(iParams)
				params += "|iParams="+iParams;
			httpService.callHttpMethod($http,params).success(function(result) {
				var isSuccess = httpSuccess(result,'iLoading=FLOAT');
				if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
					 $rootScope.error = isSuccess.replace('[ERROR]', '');
					 $rootScope.model = null;
				 }else{
					 $rootScope.model = result.data;
					 $rootScope.error = null;
					 $rootScope.deleteIds = ",";
					 if(callBack)
						 callBack();
				 }
			}).error(function(result) {
				lookUp('lookUp','',100,300,3);
				closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
				$rootScope.error = result;
				 
			});;
	};
	//点击拷贝接口详情回调
	$rootScope.copyInterface = function() {
		changeDisplay('copyInterFace','interFaceDetail');
	};
	$rootScope.changeDisplay = function(id1, id2) {
		changeDisplay(id1,id2);
	}
	$rootScope.del = function(iUrl,id,title){
		title = title? title:"确认要删除【"+id+"】？";
		if (myConfirm(title)) {
			var params = "iUrl="+iUrl+"|iLoading=TIP";
			httpService.callHttpMethod($http,params).success(function(result) {
				var isSuccess = httpSuccess(result,'iLoading=TIP')
				if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
					 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 }else{
					 /**
					  * 回调刷新当前页面数据
					  */
					 $rootScope.error = null;
					 $timeout(function() {
						 $("#refresh").click();
	                 })
				 }
			}).error(function(result) {
				closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP', 3);
				$rootScope.error = result;
				 
			});;
	    }
	};
	// 选中某个选项
	$rootScope.checkboxSelect = function(checkValues,value1, value2){
        var value = value1;
        if (value2){
            value = value + "_CA_SEPARATOR_" + value2;
        }
		if (!$rootScope[checkValues] ){
            $rootScope[checkValues] = value+",";
		}else if($rootScope[checkValues].indexOf(","+value+",")>=0 ){
			$rootScope[checkValues] = $rootScope[checkValues].replace(value+",","");
		}else{
			$rootScope[checkValues] = $rootScope[checkValues]+value+","
		}
	}
    /**
	 * 全选、不选
     * @param id 全选按钮
     * @param name 列表项
     * @param list 数据集
     * @param field 选着的数据集字段
     */
	$rootScope.selectAll = function(id,name,list,field1, field2){
		if (!field1){
            field1 = "id";
		}
		selectAll(id, name);
		if($("#"+id).prop("checked")==true){ 
			$rootScope[name] = ",";
			for (var i=0;i<list.length;i++){
				var value = list[i][field1];
				if (field2){
                    value = value + "_CA_SEPARATOR_" + list[i][field2];
				}
				$rootScope[name] = $rootScope[name] + value + "," ;
			}
		}else{
			$rootScope[name] = ",";
		}
	}
	
	$rootScope.submitForm = function(iurl,callBack,myLoading, afterCallBack){
		/**
		  * 回调刷新当前页面数据
		  */
		if(callBack){
			callBack();
		}
		iLoading = "TIPFLOAT";
		if(myLoading){
			iLoading = myLoading;
		}
		var params = "iUrl="+iurl+"|iLoading="+iLoading+"|iPost=POST|iParams=&"+$.param($rootScope.model);
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading='+iLoading);
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
			 }else if(result.success==1){
				 $rootScope.error = null;
				 $rootScope.model = result.data;
				 //关闭编辑对话框
				 closeMyDialog('myDialog');
                if(afterCallBack){
                    afterCallBack();
                } else {
                    $timeout(function() {
                        $("#refresh").click();
                    })
				}
			 }
		}).error(function(result) {
			closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading='+iLoading, 3);
			$rootScope.error = result;
			 
		});
	}

	/**
	 * 发送请求工具方法
	 */
	$rootScope.sendRequest = function(url,myLoading){
		var iLoading = "FLOATTIP";
		if(myLoading){
			iLoading = myLoading;
		}
		var params = "iUrl="+url+"|iLoading="+iLoading+"|iPost=POST";
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading='+iLoading)
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
			 }else if(result.success==1){
				 $rootScope.error = null;
				 $timeout(function() {
					 $("#refresh").click();
                 })
			 }
		}).error(function(result) {
			lookUp('lookUp','',100,300,3);
			closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
			$rootScope.error = result;
			 
		});
	}
	/***********************是否显示操作按钮************/
	
	$rootScope.hasError = function(error,id){
		if(error && error!=''){
			$("#"+id).removeClass("ndis");
			return true;
		}else{
			if(!$("#"+id).hasClass("ndis")) {
                $("#" + id).addClass("ndis");
            }
			return false;
		}
	}
	$rootScope.showOperation = function(dataType){
		var adminPermission = $rootScope.adminPermission
		if((","+adminPermission+",").indexOf(",SUPER,")>=0){
			return true;
		}
		if((","+adminPermission+",").indexOf(","+dataType+",")>=0){
			return true;
		}
		return false;
	}
	
	$rootScope.getDate = function(str){
		if(str && (str+"").indexOf(".")>0) {
            return new Date(str.split(".")[0].replace("-", "/").replace("-", "/"));
        }
	}
    /**
	 * 发布文档评论回调
     */
    $rootScope.changeimg = function () {
        changeimg('imgCode2','verificationCode');
    }
	/**
	 * 提交数据库表时回调将表格数据转换为json
	 */
	$rootScope.preAddDictionary = function(){
		var content = getParamFromTable("content", 'name');
		$rootScope.model.content = content;
	}
	/**
	 * 查看日志详情回调，格式化数据
	 */
	$rootScope.logDetailFormat = function(){
		$rootScope.model.content  = format($rootScope.model.content);
	}

	$rootScope.jsonformat = function(id,tiperror){
		var result = format($rootScope.model[id],tiperror);
		if(result){
			$rootScope.model[id] = result;
		}
	}
	$rootScope.callAjaxByName = function(iurl){
		callAjaxByName(iurl);
	}

	 $rootScope.iClose = function(id) {
	    	iClose(id);
	 };
	 /******静态化****************/
	 $rootScope.staticize= function (id){
			callAjaxByName('iUrl=user/staticize/staticize.do?projectId='+id+'|iLoading=TIPFLOAT静态化中，请稍后...|ishowMethod=updateDivWithImg|iFormId=staticize-form');
	 }
	 $rootScope.downloadStaticize= function (id){
		 var params = "iUrl=user/staticize/downloadStaticize.do?projectId="+id+"|iLoading=TIPFLOAT静态化中，请稍后...|iPost=POST";
			httpService.callHttpMethod($http,params).success(function(result) {
				var isSuccess = httpSuccess(result,'iLoading=TIPFLOAT')
				if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
					 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 }else if(result.success==1){
					 $("#downloadUrl").html("操作成功，将自动下载，3s后若无反应，请点击：<a href='"+ result.data +"' target='_blank'>下载</a> 手动下载");
					 window.open(result.data);
				 }
			}).error(function(result) {
				lookUp('lookUp','',100,300,3);
				closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=TIPFLOAT', 3);
				$rootScope.error = result;
			});
	 }

	 // 添加接口
    $rootScope.addInterfaceCallBack= function () {
        var headJson = getParamFromTable('editHeaderTable', 'name');
        try{
            eval("("+headJson+")");
        }catch(e){
            alert("请求头输入有误，json解析出错："+e);
            return;
        }
        $rootScope.model.header = headJson;

        var responseJson = getParamFromTable('editResponseParamTable', 'name');
        try{
            eval("("+responseJson+")");
        }catch(e){
            alert("返回参数输入有误，json解析出错："+e);
            return;
        }
        $rootScope.model.responseParam = responseJson;

        if($rootScope.model.paramType == 'FORM') {
            var paramJson = getParamFromTable('editParamTable', 'name');
            try {
                eval("(" + paramJson + ")");
            } catch (e) {
                alert("请求参数输入有误，json解析出错：" + e);
                return;
            }
            $rootScope.model.param = paramJson;
        }
    }
});

