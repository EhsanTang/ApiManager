/**
 * 前端controller
 */
var webModule = angular.module("webModule", []);
//以html形式输出
webModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});
webModule.filter("removeLast",function(){
	return function (value) {
		if(!value)
			return "";
		else{
			return value.substring(0,value.length-1);
		}
	}
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
				 $rootScope.webpage = null;
				 $rootScope.model = null;//初始化评论对象
			 }else{
				 $rootScope.error = null;
				 $rootScope.webpage = result.data;
				 $rootScope.model = result.others.comment;//初始化评论对象
				 $rootScope.comments = result.others.comments;//评论列表
				 $rootScope.commentCode = result.others.commentCode;//游客评论是否需要输入验证码
				 $rootScope.others = result.others;//导航路径
				 //如果是数据字典，则将内容转为json
				 if($rootScope.webpage.content!=''&&$rootScope.webpage.type=="DICTIONARY"){
					 $rootScope.dictionary = eval("("+$rootScope.webpage.content+")");
			     }
			 }
		});
    };
    $scope.getData();
});


webModule.controller('sourceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iLoading=FLOAT|iUrl=source/webDetail.do?id="+$stateParams.id;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.source = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.source = result.data;
			 }
		});
    };
    $scope.getData();
});
/**
 * 资源列表
 */
webModule.controller('webSourceCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iUrl=source/webList.do|iLoading=FLOAT|iParams=&directoryName="+$stateParams.directoryName+"&directoryId="+$stateParams.directoryId;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		$rootScope.getBaseData($scope,$http,params,page);
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
				 $rootScope.versions = result.others.versions;
				 $rootScope.errors = eval("("+result.data.errors+")");
				 
				 // 如果param以form=开头，表示为form表单参数
				 if(result.data.param.length>5 && result.data.param.substring(0,5)=="form="){
					 $rootScope.formParams = eval("("+result.data.param.substring(5)+")");
				 }else{
					 $rootScope.model.customParams = result.data.param;
				 }
				 
				 $rootScope.headers = eval("("+result.data.header+")");
				 
				 $rootScope.responseParams = eval("("+result.data.responseParam+")");
				 $rootScope.others = result.others;
				 if(result.data.method)// 调试页面默认显示method中第一个
					 $rootScope.model.debugMethod = result.data.method.split(",")[0];
			 }
		});
    };
    $scope.getDebugResult= function() {
    	$rootScope.model.headers = getParamFromTable("debugHeader");
		$rootScope.model.params =getParamFromTable("debugParams");
    	var params = "iUrl=interface/debug.do|iLoading=FLOAT|iPost=POST|iParams=&"+$.param($rootScope.model);
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.model.debugResult = result.data.debugResult;
				 $rootScope.others = result.others;
			 }
		});
    };
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

/***
 * 前端页面初始化，加载系统设置，菜单等
 */
webModule.controller('fontInit', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=frontInit.do|iLoading=FLOAT"; //  表示查询所有
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				alert("系统初始化异常："+isSuccess.replace('[ERROR]', ''));
			 }
			$rootScope.settings = result.data.settingMap;
			$rootScope.sessionAdminName = result.data.sessionAdminName;
			$rootScope.fontMenus = result.data.menuList;
		});
    };
    $scope.loginOut = function(){
		callAjaxByName("iUrl=loginOut.do|isHowMethod=updateDiv|iLoading=false|ishowMethod=doNothing|iAsync=false");
		location.reload();
	}
    $scope.getData();
});
webModule.controller('frontSearchCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		if(!$stateParams.keyword)
			$stateParams.keyword ="";
		params = "iUrl=frontSearch.do|iPost=POST|iLoading=FLOAT|iParams=&keyword="+ $stateParams.keyword;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});


