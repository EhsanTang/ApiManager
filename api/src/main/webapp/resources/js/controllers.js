/**
 * 后台controller
 */
var mainModule = angular.module("mainModule", []);
//以html形式输出
mainModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});
mainModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,$http ,httpService) {
	 
});
/**************************左边菜单栏***************************/
mainModule.controller('lefMenuCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.menu = function() {
		var params = "iUrl=menu/menu.do|iLoading=FLOAT";
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				$scope.menus = null;
			 }else{
				$rootScope.error = null;
				$scope.menus = result.data;
			 }
		});
	};
	/***********************判断菜单中的roleIds是否包含用户角色中的任意一个role************/
	$scope.canSeeMenu = function(menuId,type){
		if(!menuId||menuId==""||type!="BACK")
			return false;
		var auth = $("#sessionAuth").val();
		if((","+auth+",").indexOf(","+menuId+",")>=0)
			return true;
		return false;
	}
	$scope.menu();
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
		var params = "iUrl=webPage/list.do|iLoading=FLOAT|iParams=&type=" + $stateParams.type+"&moduleId="+$("#searchModuleId").val()
		+"&name="+$("#searchName").val();;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************系统设置列表****************************/
mainModule.controller('settingCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=setting/list.do|iLoading=FLOAT|iParams=&remark=" + $("#searchRemark").val();;
		$rootScope.getBaseData($scope,$http,params,page);
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
mainModule.controller('interfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
    $scope.getRequestExam = function(editerId,targetId,item,tableId) {
    	var params = "iUrl=interface/getRequestExam.do|iLoading=FLOAT|iParams=&"+$.param($rootScope.model);
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
    		params = eval("("+item.param+")");
    	}else if(tableId=='editResponseParamTable'&&item.responseParam!=''){
    		params = eval("("+item.responseParam+")");
    	}
    	$("#"+editerId).find("tbody").find("tr").remove();
    	if(params!=null&&params!=""){
	    	var i=0;
	    	$.each(params, function (n, value) {
	    		i++;
	    		addOneParam(value.name,value.necessary,value.type,value.parameterType,value.remark,i,tableId)
	        });  
    	}
		$("#"+editerId).removeClass('none');
		$("#"+targetId).addClass('none');
    };
    $scope.modifyParam = function(editerId,targetId,item,type) {
    	if(type=='param')
    		item.param = getParamFromTable('editParamTable');
    	else if(type="responseParam")
    		item.responseParam = getParamFromTable('editResponseParamTable');
    	$("#"+editerId).addClass('none');
		$("#"+targetId).removeClass('none');
    };
});
