/**
 * 主module
 * @type {[type]}
 */
var mainModule = angular.module("mainModule", []);
//以html形式输出
mainModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});
mainModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,$http ,httpService) {
	 
});
/**************************左边菜单栏***************************/
mainModule.controller('menuCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.menu = function() {
		var params = "iUrl=menuInfo/menu.do|iLoading=FLOAT";
		httpService.callHttpMethod($http,params).success(function(result) {
			httpSuccess(result,'iLoading=FLOAT','0')
			if(!isJson(result)&&result.indexOf('[ERROR]') >= 0){
				$scope.error = result.replace('[ERROR]', '');
				$scope.menus = null;
			 }else{
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
mainModule.controller('menuInfoCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=menuInfo/list.do|iLoading=FLOAT|iParams=";
		if($("#menuName").val()!=null&&$("#menuName").val()!=''){
			params += "&menuName=" + $("#menuName").val();
			$stateParams.searchMenuName=$("#menuName").val();
		}else{
			params +="&parentId="+ $stateParams.parentId;
		}
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************用户列表****************************/
mainModule.controller('userInfoCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=userInfo/list.do|iLoading=FLOAT|iParams=";
		if($("#trueName").val()!=null&&$("#trueName").val()!=''){
			params += "&trueName=" + $("#trueName").val();
			$stateParams.searchUserName=$("#trueName").val();
		}
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************角色列表****************************/
mainModule.controller('roleInfoCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=roleInfo/list.do|iLoading=FLOAT|iParams=";
		if($("#roleName").val()!=null&&$("#roleName").val()!=''){
			params += "&roleName=" + $("#roleName").val();
			$stateParams.searchRoleName=$("#roleName").val();
		}
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});

/**************************错误码列表****************************/
mainModule.controller('errorInfoCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=errorInfo/list.do|iLoading=FLOAT|iParams=";
		if($("#searchMsg").val()!=null&&$("#searchMsg").val()!=''){
			params += "&errorMsg=" + $("#searchMsg").val();
			$stateParams.searchMsg=$("#searchMsg").val();
		}
		if($("#searchCode").val()!=null&&$("#searchCode").val()!=''){
			params += "&errorCode=" + $("#searchCode").val();
			$stateParams.searchCode=$("#searchCode").val();
		}
		if($("#searchModuleId").val()!=null&&$("#searchModuleId").val()!=''){
			params += "&moduleId=" + $("#searchModuleId").val();
			$stateParams.searchModuleId=$("#searchModuleId").val();
		}else if($stateParams.moduleId){
			$stateParams.searchModuleId=$stateParams.moduleId;
			params += "&moduleId=" + $stateParams.moduleId;
		}
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************后端、前段接口列表****************************/
mainModule.controller('interfaceInfoCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "";
		if($("#interfaceName").val()!=null&&$("#interfaceName").val()!=''){
			params += "&interfaceName=" + $("#interfaceName").val();
			$stateParams.searchMenuName=$("#interfaceName").val();
		}
		if($("#url").val()!=null&&$("#url").val()!=''){
			params += "&url=" + $("#url").val();
			$stateParams.searchUrl=$("#url").val();
		}
		if(params==""){
			params +="&moduleId="+ $stateParams.moduleId;
		}
		params = "iUrl=interfaceInfo/list.do|iLoading=FLOAT|iParams="+params;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
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
	    		addOneParam(value.name,value.necessary,value.type,value.remark,i,tableId)
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
/**************************前段接口详情:不需要打开模态框，所以不能调用getBaseData()****************************/
mainModule.controller('webInterfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function() {
		var params = "iUrl=interfaceInfo/webDetail.do|iLoading=FLOAT|iParams=&id="+$stateParams.id;
		httpService.callHttpMethod($http,params).success(function(result) {
			httpSuccess(result,'iLoading=FLOAT')
			if(!isJson(result)&&result.indexOf('[ERROR]') >= 0){
				$scope.error = result.replace('[ERROR]', '');
				$scope.model = null;
			 }else{
				 $scope.model = result.data;
				 $scope.errors = eval("("+result.data.errors+")");
				 $scope.params = eval("("+result.data.param+")");
				 $scope.responseParams = eval("("+result.data.responseParam+")");
			 }
		});
    };
    $scope.getData();
});
