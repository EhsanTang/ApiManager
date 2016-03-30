var app = angular.module('app', [ 'ui.router', 'mainModule' ]);
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
		var mywidth = getValue(params,'mywidth');
		var myheight = getValue(params,'myheight');
		var radio = getValue(params,'radio');
		var tag = getValue(params,'tag');
		var code = getValue(params,'code');
		var type = getValue(params,'type');
		var iparams = getValue(params,'params');
		var showType = getValue(params,'showType');
		var def = getValue(params,'def');
		var tagShow = getValue(params,'tagShow');
		$rootScope.loadPick(event,mywidth,myheight,radio,tag,code,type,def,iparams,showType,iCallBack,iCallBackParam,tagShow);
	}
	$rootScope.loadPick = function loadPick(event,mywidth,myheight,radio,tag,code,type,def,params,showType,iCallBack,iCallBackParam,tagShow) { 
		/***********加载选择对话框********************/
		if(!params)
			params='';
		if(showType!='0'){
			if(!showType||showType=='')
				showType=5;
		}
			
		//事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
		callAjaxByName("iUrl=pick.do|isHowMethod=updateDiv|iParams=&type="
				+type+"&radio="+radio+"&code="+code+"&tag="+tag+"&tagShow="+tagShow+"&def="+def+params,iCallBack,iCallBackParam);
		//将需要手动同步的model.key记录到数组
		if(tagShow)
			$rootScope.pick.push(tagShow);
		else
			$rootScope.pick.push(tag);
		lookUp('lookUp', event, myheight, mywidth ,showType,tag);
		showMessage('lookUp','false',false,-1);
	}
	$rootScope.getBaseData = function($scope,$http,params,page) {
		if(page) $scope.currentPage = page;
		if($scope.currentPage) params += "&currentPage="+$scope.currentPage;
		httpService.callHttpMethod($http,params).success(function(result) {
			httpSuccess(result,'iLoading=FLOAT','0')
			if(!isJson(result)&&result.indexOf('[ERROR]') >= 0){
				 $rootScope.error = result.replace('[ERROR]', '');
				 $rootScope.list = null;
			 }else{
				 $rootScope.list = result.data;
				 $rootScope.page = result.page;
			 }
		});
    };
	$rootScope.detail = function(title,iwidth,iurl,currentId) {
			openModal(title,iwidth);
			var params = "iUrl="+iurl+"|iLoading=FLOAT";
			if(currentId)
				params += "|iParams=&currentId="+currentId;
			httpService.callHttpMethod($http,params).success(function(result) {
				httpSuccess(result,'iLoading=FLOAT')
				if(!isJson(result)&&result.indexOf('[ERROR]') >= 0){
					 $rootScope.error = result.replace('[ERROR]', '');
					 $rootScope.model = null;
				 }else{
					 $rootScope.model = result.data;
				 }
			});
	};
	$rootScope.del = function(iUrl,id,title){
		title = title? title:"确认要删除'"+id+"'？";
		if (confirm(title)) {
			var params = "iUrl="+iUrl+"|iLoading=PROPUP";
			httpService.callHttpMethod($http,params).success(function(result) {
				httpSuccess(result,'iLoading=PROPUP')
				if(!isJson(result)&&result.indexOf('[ERROR]') >= 0){
					 $rootScope.error = result.replace('[ERROR]', '');
				 }else{
					 /**
					  * 回调刷新当前页面数据
					  */
					 $timeout(function() {
						 $("#refresh").click();
	                 })
				 }
			});
	    }
	};
//	$rootScope.search = function(iurl){
//		var params = "iUrl="+iurl+"|iLoading=FLOAT|iPost=POST|iParams=&"+$.param($rootScope.searchModel);
//		params += "&currentPage=1";
//		httpService.callHttpMethod($http,params).success(function(result) {
//			httpSuccess(result,'iLoading=FLOAT','0')
//			if(!isJson(result)&&result.indexOf('[ERROR]') >= 0){
//				 $rootScope.error = result.replace('[ERROR]', '');
//				 $rootScope.list = null;
//			 }else{
//				 $rootScope.list = result.data.list;
//				 $rootScope.page = result.page;
//				 $rootScope.searchModel = result.data.model;
//			 }
//		});
//	};
	$rootScope.submitForm = function(iurl){
		/*
		 * 模型双向绑定，但是pick选择的数据无法绑定
		 * 同步通过value="" 赋值的model.key
		 */
		for(var i=0;i<$rootScope.pick.length;i++){
			var key = $rootScope.pick[i];
			$rootScope.model[key] = $("#"+key).val();
		}
		$rootScope.pick = [];
		var params = "iUrl="+iurl+"|iLoading=PROPUPFLOAT|iPost=POST|iParams=&"+$.param($rootScope.model);
		httpService.callHttpMethod($http,params).success(function(result) {
			httpSuccess(result,'iLoading=PROPUPFLOAT')
			if(!isJson(result)&&result.indexOf('[ERROR]') >= 0){
				 $rootScope.error = result.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else if(result.success==1){
				 $rootScope.model = result.data;
				 /**
				  * 回调刷新当前页面数据
				  */
				 closeModeal();
				 $timeout(function() {
					 $("#refresh").click();
                 })
			 }
		});
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
	//待删除
	$rootScope.needHiddenModule = function (dataType){
		iShow("roleModuleId");
		if(dataType=="SHOWMENU"||dataType=="USER"||dataType=="MENU"||dataType=="ROLE"){
			return true;
		}else{
			return false;
		}
	}
	$rootScope.getDate = function(str){
		return new Date(str.split(".")[0].replace("-", "/").replace("-", "/"));
	}
	
	$rootScope.setValueForModel = function(id,transform){
		alert(transform);
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
});

