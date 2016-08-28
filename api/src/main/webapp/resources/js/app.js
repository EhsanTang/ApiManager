var app = angular.module('app', [ 'ui.router', 'mainModule','webModule','textAngular']);
/**
 * 由于整个应用都会和路由打交道，所以这里把$state和$stateParams这两个对象放到$rootScope上，方便其它地方引用和注入。
 * 这里的run方法只会在angular启动的时候运行一次。
 * @param  {[type]} $rootScope
 * @param  {[type]} $state
 * @param  {[type]} $stateParams
 * @return {[type]}
 */
app.run(function($rootScope, $state, $stateParams, $http, $timeout,httpService) {
	$rootScope.$state = $state;
	$rootScope.$stateParams = $stateParams;
	$rootScope.pick = [];
	
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
		if(!iUrl)
			iUrl = "pick.do";
		if(!params)
			params='';
		if(!tagName)
			tagName='';
		if(showType!='0'){
			if(!showType||showType=='')
				showType=5;
		}
		$("#pickContent").html(loadText);
		//事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
		callAjaxByName("iUrl="+iUrl+"|isHowMethod=updateDiv|iParams=&type="
				+type+"&radio="+radio+"&code="+code+"&tag="+tag+"&tagName="+tagName+"&def="+def+params,iCallBack,iCallBackParam);
		if(tagName)
			lookUp('lookUp', event, iheight, iwidth ,showType,tagName);
		else
			lookUp('lookUp', event, iheight, iwidth ,showType,tag);
		showMessage('lookUp','false',false,-1);
	}
	$rootScope.getBaseData = function($scope,$http,params,page) {
		if(page) $scope.currentPage = page;
		if($scope.currentPage) params += "&currentPage="+$scope.currentPage;
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
			 }
		}).error(function(result) {
			lookUp('lookUp','',100,300,3);
			closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
			$rootScope.error = result;
			 
		});;
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
					 if(callBack)
						 callBack();
				 }
			}).error(function(result) {
				lookUp('lookUp','',100,300,3);
				closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
				$rootScope.error = result;
				 
			});;
	};
	//点击详情回调，清除编辑缓存页面的table
	$rootScope.initEditInterFace = function (){
		changeDisplay('interFaceDetail','copyInterFace');
		$("#eparam").addClass('none');
		$("#param").removeClass('none');
		$("#eheader").addClass('none');
		$("#header").removeClass('none');
		$("#responseEparam").addClass('none');
		$("#responseParam").removeClass('none');
	}
	//点击拷贝接口详情回调
	$rootScope.copyInterface = function() {
		changeDisplay('copyInterFace','interFaceDetail');
	};
	$rootScope.del = function(iUrl,id,title){
		title = title? title:"确认要删除【"+id+"】？";
		if (confirm(title)) {
			var params = "iUrl="+iUrl+"|iLoading=PROPUP";
			httpService.callHttpMethod($http,params).success(function(result) {
				var isSuccess = httpSuccess(result,'iLoading=PROPUP')
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

	$rootScope.submitForm = function(iurl,callBack,myLoading){
		/**
		  * 回调刷新当前页面数据
		  */
		if(callBack){
			callBack();
		}
		iLoading = "PROPUPFLOAT";
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
				 $timeout(function() {
					 $("#refresh").click();
                 })
			 }
		}).error(function(result) {
			closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading='+iLoading, 3);
			$rootScope.error = result;
			 
		});
	}
	$rootScope.changeSequence = function(model,id,changeId){
		var params = "iUrl=back/"+model+"/changeSequence.do|iLoading=FLOAT|iPost=POST|iParams=&id="+id+"&changeId="+changeId;
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT')
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
			 }else if(result.success==1){
				 $rootScope.error = null;
				 //关闭编辑对话框
				 $timeout(function() {
					 $("#refresh").click();
                 })
			 }
		}).error(function(result) {
			lookUp('lookUp','',100,300,3);
			closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
			$rootScope.error = result;
			 
		});;
	}
	/***********************是否显示操作按钮************/
	$rootScope.showOperation = function(dataType,moduleId){
		var userRole = $("#sessionRoleIds").val();
		if((","+userRole+",").indexOf(",super,")>=0){
			return true;
		}
		var needAuth = dataType;
		if(moduleId)
			needAuth = needAuth+"_"+moduleId;
		var sessionAuth = $("#sessionAuth").val();
		if((","+sessionAuth+",").indexOf(","+needAuth+",")>=0){
			return true;
		}
		return false;
	}
	
	$rootScope.getDate = function(str){
		if(str && str.indexOf(".")>0)
			return new Date(str.split(".")[0].replace("-", "/").replace("-", "/"));
	}
	/**
	 * 提交数据字典时回调将表格数据转换为json
	 */
	$rootScope.preAddDictionary = function(){
		var content = getParamFromTable("content");
		$rootScope.model.content = content;
	}
	/**
	 * 查看日志详情回调，格式化数据
	 */
	$rootScope.logDetailFormat = function(){
		$rootScope.model.content  = format($rootScope.model.content);
	}
	/**
	 * 数据字典、文章编辑回调
	 */
	$rootScope.getFields = function() {
    		// 切换为默认编辑器
    		changeDisplay('defEditor','kindEditor');
	    	var content = "";
	    	if($rootScope.model.content!=''){
	    		// 如果是文章，eval会报错
	    		try{
	    			content = eval("("+$rootScope.model.content+")");
	    		}catch(e){}
	    	}
	    	$("#content").find("tbody").find("tr").remove();
	    	if(content!=null&&content!=""){
		    	var i=0;
		    	$.each(content, function (n, value) {
		    		i++;
		    		addOneField(value.name, value.type, value.notNull,value.def, value.remark, value.rowNum);
		        });  
	    	}
	    };
	$rootScope.setValueForModel = function(id,transform){
		var result= $("#"+id).val();
		if(transform){
			result = format(result);
		}
		if(result){
			if(id=="falseExam"){
				$rootScope.model.falseExam = result;
			}else if(id=="trueExam"){
				$rootScope.model.trueExam = result;
			}if(id=="settingValue"){
				$rootScope.model.value = result;
			}
		}
	}
	/**************markdown*************/
	$rootScope.markdownEtitor = function(href){
		$("#markdownDialog").css('display','block'); 
		document.getElementById("markdownFrame").src=href;
	}
	 $rootScope.iClose = function(id) {
	    	iClose(id);
	    };
});

