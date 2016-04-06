/**
 * 前端controller
 */
var webModule = angular.module("webModule", []);
//以html形式输出
webModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});
webModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,$http ,httpService) {
	 
});
webModule.controller('webPageDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		if(setPwd){
			setPassword();
		}
		var params = "iLoading=FLOAT|iUrl=webPage/webDetail.do?id="+$stateParams.id;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.dictionary = null;
				 $rootScope.model = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.model = result.data;
				 if($rootScope.model.content!=''){
					 $rootScope.dictionary = eval("("+$rootScope.model.content+")");
			     }
			 }
		});
    };
    $scope.getData();
});
/**************************前段接口详情:不需要打开模态框，所以不能调用getBaseData()****************************/
webModule.controller('webInterfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		if(setPwd){
			setPassword();
		}
		var params = "iUrl=interface/webDetail.do|iLoading=FLOAT|iParams=&id="+$stateParams.id;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.model = result.data;
				 $rootScope.errors = eval("("+result.data.errors+")");
				 $rootScope.params = eval("("+result.data.param+")");
				 $rootScope.responseParams = eval("("+result.data.responseParam+")");
			 }
		});
    };
    $scope.hasRequestHeader = function(params,type){
    	if(params.length>0){
    		for (var i=0;i<params.length;i++){
    	         if(type==params[i].parameterType){
    	        	 return true;
    	         }
    	    }
    	}
    	return false;
    }
    $scope.getData();
});
webModule.controller('webInterfaceCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		if(setPwd){
			setPassword();
		}
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
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		params = "iUrl=interface/webList.do|iLoading=FLOAT|iParams="+params;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
webModule.controller('settingDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function() {
		var params = "iLoading=FLOAT|iUrl=setting/detail.do?id="+$stateParams.id+"&key="+$stateParams.key;
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

