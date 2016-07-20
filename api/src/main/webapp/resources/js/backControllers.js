/**
 * 后台controller
 */
var mainModule = angular.module("mainModule", []);
//以html形式输出
mainModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});

// 显示长度 wordwise：切字方式- 如果是 true，只切單字
mainModule.filter('cut', function () {
	  return function (value, wordwise, max, tail) {
	    if (!value) return '';

	    max = parseInt(max, 10);
	    if (!max) return value;
	    if (value.length <= max) return value;

	    value = value.substr(0, max);
	    if (wordwise) {
	      var lastspace = value.lastIndexOf(' ');
	      if (lastspace != -1) {
	        value = value.substr(0, lastspace);
	      }
	    }
	    return value + (tail || ' …');
	  };
});

mainModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,$http ,httpService) {
	
});

/***
 * 后台初始化，加载系统设置，菜单等
 */
mainModule.controller('backInit', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=backInit.do|iLoading=fase"; //  表示查询所有
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=false');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				alert("系统初始化异常："+isSuccess.replace('[ERROR]', ''));
			}else{
				$rootScope.settings = result.data.settingMap;
				$rootScope.backMenus = result.data.menuList;
				$rootScope.sessionAdminName = result.data.sessionAdminName;
				$rootScope.sessionAdminAuthor = result.data.sessionAdminAuthor;
				$rootScope.sessionAdminName = result.data.sessionAdminName;
				$rootScope.sessionAdminRoleIds = result.data.sessionAdminRoleIds;
				$rootScope.sessionAdminId =result.data.sessionAdminId;
			}
		});
    };
    // 判断是否是最高管理员
    $scope.isSupperAdmin = function (){
    	if($rootScope.sessionAdminRoleIds){
    		var roles = ","+$rootScope.sessionAdminRoleIds+",";
    		if(roles.indexOf(',super,')>=0){
    			return true;
    		}
    	}
    	return false;
    }
    /***********************判断菜单中的roleIds是否包含用户角色中的任意一个role************/
	$scope.canSeeMenu = function(id,type){
		if(!id||id==""||type!="BACK")
			return false;
		var auth = $("#sessionAuth").val();
		if((","+auth+",").indexOf(","+id+",")>=0)
			return true;
		return false;
	}
	$scope.profile = function(id){
		var params = "iUrl=user/detail.do?id="+id+"|iLoading=FLOAT";
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.model = result.data;
				 $rootScope.error = null;
			 }
		});
	}
	$scope.loginOut = function(){
		callAjaxByName("iUrl=loginOut.do|isHowMethod=updateDiv|iLoading=false|ishowMethod=doNothing|iAsync=false");
		location.href="web.do#/webWebPage/detail/PAGE/WELCOME";
	}
	$scope.createEditor = function(id,field){
		createKindEditor(id,field);
	}
    $scope.getData();
});
/**************************后端接口列表****************************/
mainModule.controller('preLoginCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function() {
		if($rootScope.model && $rootScope.model.sessionAdminName){
			window.location.href="index.do";
		}else if($rootScope.model && $rootScope.model.tipMessage){
			showMessage('warnMessage', $rootScope.model.tipMessage,true,3);
		}else{
			var params = "iUrl=preLogin.do|iLoading=FLOAT";
			httpService.callHttpMethod($http,params).success(function(result) {
				var isSuccess = httpSuccess(result,'iLoading=FLOAT','0');
				if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
					alert(isSuccess.replace('[ERROR]', ''));
				 }else{
					 $rootScope.model = result.data.model;
					 $rootScope.fontSettings = result.data.settingMap;
					 showMessage('warnMessage', $rootScope.model.tipMessage,true,3);
				 }
			});
		}
    };
    $scope.changeRadio = function(value){
    	$rootScope.model.remberPwd = value;
    }
    $scope.getData();
});

/**************************菜单列表****************************/
mainModule.controller('menuCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		if($("#searchType").val()!=null&&$("#searchType").val()!=''){
			$stateParams.type = $("#searchType").val();
		}
		var params = "iUrl=menu/list.do|iLoading=FLOAT|iParams=&type="+$stateParams.type;
		if($("#menuName").val()!=null&&$("#menuName").val()!=''){
			params += "&menuName=" + $("#menuName").val();
		}else{
			params +="&parentId="+ $stateParams.parentId;
		}
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************用户列表****************************/
mainModule.controller('userCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/list.do|iLoading=FLOAT|iParams=&trueName=" + $("#trueName").val();
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************WebPage列表****************************/
mainModule.controller('webPageCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=webPage/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=" + $stateParams.type+"&moduleId="+$stateParams.searchModuleId+"&category="+$stateParams.searchCategory+"&name="+$stateParams.searchName;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************系统设置列表****************************/
mainModule.controller('settingCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=setting/list.do|iLoading=FLOAT|iParams=&remark=" + $("#searchRemark").val()+"&key="+$stateParams.key;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
mainModule.controller('settingDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function() {
		var params = "iLoading=FLOAT|iUrl=setting/detail.do?id="+$stateParams.id+"&key="+$stateParams.key+"&type="+$stateParams.type;
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.model = result.data;
			 }
		});
    };
    $scope.getData();
});
/**************************角色列表****************************/
mainModule.controller('roleCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=role/list.do|iLoading=FLOAT|iParams=&roleName=" + $("#roleName").val();;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});

/**************************错误码列表****************************/
mainModule.controller('errorCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=error/list.do|iLoading=FLOAT|iParams=&errorMsg=" + $("#searchMsg").val()+"&errorCode=" + $("#searchCode").val();
		if($("#searchModuleId").val()!=null&&$("#searchModuleId").val()!=''){
			params += "&moduleId=" + $("#searchModuleId").val();
			$stateParams.searchModuleId = $("#searchModuleId").val();
		}else if($stateParams.moduleId){
			$stateParams.searchModuleId=$stateParams.moduleId;
			params += "&moduleId=" + $stateParams.moduleId;
		}
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************后端接口列表****************************/
mainModule.controller('interfaceCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "";
		if($("#interfaceName").val()!=null&&$("#interfaceName").val()!=''){
			params += "&interfaceName=" + $("#interfaceName").val();
		}
		if($("#url").val()!=null&&$("#url").val()!=''){
			params += "&url=" + $("#url").val();
		}
		if(params==""){
			params +="&moduleId="+ $stateParams.moduleId;
		}
		params = "iUrl=interface/list.do|iLoading=FLOAT|iParams="+params;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});

mainModule.controller('sourceCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=source/list.do|iLoading=FLOAT|iParams=&name="+$stateParams.name+"&directoryId="+$stateParams.directoryId;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
  });

mainModule.controller('interfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
    $scope.getRequestExam = function(editerId,targetId,item,tableId) {
    	var params = "iUrl=interface/getRequestExam.do|iLoading=FLOAT|iPost=true|iParams=&"+$.param($rootScope.model);
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.model.requestExam = result.data.requestExam;
			 }
		});
    };
    $scope.editerParam = function(editerId,targetId,item,tableId) {
    	var params = "";
    	if(tableId=='editParamTable'&&item.param!=''){
    		
    		// 如果param为空，或者以form=开头，表示为form表单参数，否则表示为自定义参数
    		if(item.param.length<5 || item.param.substring(0,5)!="form="){
    			alert("参数格式有误，无法解析，请点击【Custom】自定义参数");
    			return;
    		}else{
    			item.iparam = item.param.substring(5);
    		}
    		
    		// 将param转换为json数据
    		try{
    			$rootScope.model.params = eval("("+item.iparam+")");
    		}catch(e){
    			alert("参数格式有误，无法解析，请点击【Custom】自定义参数");
    			return;
    		}
    		
    	}else if(tableId=='editResponseParamTable'){
    		$rootScope.model.responseParams = eval("("+item.responseParam+")");
    	}else if(tableId=='editHeaderTable'){
    		$rootScope.model.headers = eval("("+item.header+")");
    	}
    	
    	
//    	$("#"+editerId).find("tbody").find("tr").remove();
//    	if(params!=null&&params!=""){
//	    	var i=0;
//	    	$.each(params, function (n, value) {
//	    		i++;
//	    		addOneParam(value.name,value.necessary,value.type, value.def,value.remark,i,tableId)
//	        });  
//    	}
		$("#"+editerId).removeClass('none');
		$("#"+targetId).addClass('none');
    };
    $scope.addOneHeard = function(){
    	$rootScope.model.headers[$rootScope.model.headers.length] = "{}";
    }
    $scope.addOneParam = function(){
    	$rootScope.model.params[$rootScope.model.params.length] = "{}";
    }
    $scope.addOneResponseParams = function(){
    	$rootScope.model.responseParams[$rootScope.model.responseParams.length] =  "{}";
    }
    $scope.modifyParam = function(editerId,targetId,item,type) {
    	if(type=='param'){
    		var json = getParamFromTable('editParamTable');
    		try{
    		 eval("("+json+")");
    		}catch(e){
    			alert("输入有误，json解析出错："+e);
    			return;
    		}
    		item.param = "form="+json	
    	}
    	else if(type=="responseParam"){
    		var json = getParamFromTable('editResponseParamTable');
    		try{
       		 eval("("+json+")");
       		}catch(e){
       			alert("输入有误，json解析出错："+e);
       			return;
       		}
    		item.responseParam = json;
    	}else if(type=="header"){
    		var json = getParamFromTable('editHeaderTable');
    		try{
       		 eval("("+json+")");
       		}catch(e){
       			alert("输入有误，json解析出错："+e);
       			return;
       		}
    		item.header = json;
    	}
    	$("#"+editerId).addClass('none');
		$("#"+targetId).removeClass('none');
    };
});
/**************************日志列表****************************/
mainModule.controller('logCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=log/list.do|iLoading=FLOAT|iParams=&modelName="+$stateParams.modelName;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
