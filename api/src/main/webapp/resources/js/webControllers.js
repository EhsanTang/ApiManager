/**
 * 前端controller
 */
var webModule = angular.module("webModule", []);
//以html形式输出
webModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});
/***
 * 设置一个空的Controller，该Controller下的数据直接调用app.js中$rootScope 中的方法
 * 初始化不需要加载数据
 */
webModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,$http ,httpService) {
});

/**
 * webPage详情（数据字典，网站页面，文章）
 */
webModule.controller('webPageDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
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
				 //如果是数据字典，则将内容转为json
				 if($rootScope.model.content!=''&&$rootScope.model.type=="DICTIONARY"){
					 $rootScope.dictionary = eval("("+$rootScope.model.content+")");
			     }
			 }
		});
    };
    $scope.getData();
});
/**
 * 接口详情
 * 不需要打开模态框，所以不能调用$rootScope中的getBaseData()
 */
webModule.controller('webInterfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
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
    /**
     * 根据参数中的paramterType判断该参数是否还有请求头或请求参数
     * 如果没有，前端显示无，不显示table
     */
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
/**
 * 接口列表
 */
webModule.controller('webInterfaceCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
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

