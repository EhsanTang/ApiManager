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
webModule.controller('frontSearchCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		if(!$stateParams.keyword)
			$stateParams.keyword ="";
		params = "iUrl=frontSearch.do|iPost=POST|iLoading=FLOAT|iParams=&keyword="+ $stateParams.keyword;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************错误码列表****************************/
mainModule.controller('errorCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=front/error/list.do|iLoading=FLOAT|iParams=&moduleId=" +
			$stateParams.moduleId +"&errorMsg=" + $stateParams.errorMsg+"&errorCode=" + $stateParams.errorCode;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/*************************数据字典列表******************************/
mainModule.controller('frontDictCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=front/webPage/diclist.do|iLoading=FLOAT|iPost=POST|iParams=&moduleId="+$stateParams.projectId+"&name="+$stateParams.search;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************WebPage列表****************************/
mainModule.controller('fontWebPageCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=front/webPage/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=" + $stateParams.type+"&moduleId="+$stateParams.searchModuleId+"&category="+$stateParams.search;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**
 * webPage详情（数据字典，网站页面，文章）
 */
webModule.controller('webPageDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iLoading=FLOAT|iUrl=front/webPage/detail.do?id="+$stateParams.id;
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
/**
 * 接口列表
 */
webModule.controller('frontInterfaceCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "&interfaceName=" + $stateParams.interfaceName + "&url="+ $stateParams.url + "&moduleId="+ $stateParams.moduleId;
		
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		params = "iUrl=front/interface/list.do|iLoading=FLOAT|iParams="+params;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});


/**
 * 接口详情
 * 不需要打开模态框，所以不能调用$rootScope中的getBaseData()
 */
webModule.controller('interfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iUrl=front/interface/detail.do|iLoading=FLOAT|iParams=&id="+$stateParams.id;
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
    	var params = "iUrl=front/interface/debug.do|iLoading=FLOAT|iPost=POST|iParams=&"+$.param($rootScope.model);
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

mainModule.controller('frontProjectMenuCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http,$location,httpService) {
	$scope.getData = function(page) {
		var url = $location.absUrl();
		var projectId = url.substr(url.indexOf("#")+2,url.length).split("/")[0];
		var params = "iUrl=front/project/menu.do|iLoading=FLOAT|iPost=POST|iParams=&moduleId="+projectId;
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.source = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.project = result.data.project;
				 $rootScope.modules = result.data.modules;
			 }
		});

    };
    $scope.getData();
});


webModule.controller('sourceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iLoading=FLOAT|iUrl=back/source/webDetail.do?id="+$stateParams.id;
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
webModule.controller('frontSourceCtrl', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iUrl=back/source/webList.do|iLoading=FLOAT|iParams=&directoryName="+$stateParams.directoryName+"&directoryId="+$stateParams.directoryId;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});



/***
 * 前端页面初始化，加载系统设置，菜单等
 */
webModule.controller('fontInit', function($rootScope,$scope, $http, $state, $stateParams,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=front/init.do|iLoading=FLOAT"; //  表示查询所有
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
		callAjaxByName("iUrl=back/loginOut.do|isHowMethod=updateDiv|iLoading=false|ishowMethod=doNothing|iAsync=false");
		location.reload();
	}
    $scope.getData();
});





