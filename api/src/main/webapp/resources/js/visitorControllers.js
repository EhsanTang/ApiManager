/**
 * 前端controller
 */
var visitorModule = angular.module("visitorModule", []);
//以html形式输出
visitorModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});
visitorModule.filter("encodeURL",function($sce){
	 return function (input){ 
		 if(input){
		 input = replaceAll(input,"\\/", "CA_FXG");
		 input = replaceAll(input,"\\+", "CA_ADD");
		 input = replaceAll(input,"\\-", "CA_DES");
		 input = replaceAll(input,"\\&", "CA_AND");
		 input = replaceAll(input,"\\|", "CA_HZ");
		 input = replaceAll(input,"\\{", "CA_DKHS");
		 input = replaceAll(input,"\\}", "CA_DKHE");
		 input = replaceAll(input,"\\?", "CA_WH");
		 input = replaceAll(input,"\\*", "CA_XH");
		 input = replaceAll(input,"\\#", "CA_JH");
		 input = replaceAll(input,"\\:", "CA_MH");
		 input = replaceAll(input,"\\.", "CA_DH");
		 }
		 return input;
	} ;
});
visitorModule.filter("decodeURL",function($sce){
		 return function (input){
			 if(input){
			 input = replaceAll(input, "CA_FXG","\/");
			 input = replaceAll(input, "CA_ADD","\+");
			 input = replaceAll(input, "CA_DES","\-");
			 input = replaceAll(input, "CA_AND","\&");
			 input = replaceAll(input, "CA_HZ","\|");
			 input = replaceAll(input, "CA_DKHS","\{");
			 input = replaceAll(input, "CA_DKHE","\}");
			 input = replaceAll(input, "CA_WH","\?");
			 input = replaceAll(input, "CA_XH","\*");
			 input = replaceAll(input, "CA_JH","\#");
			 input = replaceAll(input, "CA_MH","\:");
			 input = replaceAll(input, "CA_DH","\.");
			}
		 return input;
	 } ;
});
visitorModule.filter("removeLast",function(){
	return function (value) {
		if(!value)
			return "";
		else{
			return value.substring(0,value.length-1);
		}
	}
});
// 数据库表flag转中文
visitorModule.filter("directoryFlagName",function(){
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
// 背景色
visitorModule.filter("directoryBG",function(){
	return function (value) {
		if(value=='primary')
			return "bg-danger";
		else if(value=='foreign')
			return "bg-success";
		else if(value=='associate')
			return "bg-info";
		else
			return "";
	}
});
/***
 * 设置一个空的Controller，该Controller下的数据直接调用app.js中$rootScope 中的方法
 * 初始化不需要加载数据
 */
visitorModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,httpService) {
});
visitorModule.controller('visitorSearchCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		if(!$stateParams.keyword)
			$stateParams.keyword ="";
		params = "iUrl=visitorSearch.do|iPost=POST|iLoading=FLOAT|iParams=&keyword="+ $stateParams.keyword;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************错误码列表****************************/
userModule.controller('errorCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params ="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		params = "iUrl=visitor/error/list.do|iLoading=FLOAT|iPost=true|iParams=&projectId=" +
			$stateParams.projectId +"&errorMsg=" + $stateParams.errorMsg+"&errorCode=" + $stateParams.errorCode + params;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/*************************数据库表列表******************************/
userModule.controller('visitorDictCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=visitor/article/diclist.do|iLoading=FLOAT|iPost=POST|iParams=&moduleId="+$stateParams.moduleId+"&name="+$stateParams.search;
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************article列表****************************/
userModule.controller('fontArticleCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=visitor/article/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=" + $stateParams.type
		+"&moduleId="+$stateParams.moduleId+"&name="+$stateParams.name+"&category="+$stateParams.category+"&status=" + $stateParams.status;
		if(setPwd) setPassword();
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**
 * article详情（数据库表，网站页面，文章）
 */
visitorModule.controller('articleDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iLoading=FLOAT|iUrl=visitor/article/detail.do?id="+$stateParams.id+"&currentPage="+page;
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
				 $rootScope.webpage = result.data;
				 $rootScope.page = result.page;
				 $rootScope.model = result.others.comment;//初始化评论对象
				 $rootScope.comments = result.others.comments;//评论列表
				 $rootScope.commentCode = result.others.commentCode;//游客评论是否需要输入验证码
				 $rootScope.others = result.others;//导航路径
				 //如果是数据库表，则将内容转为json
				 if($rootScope.webpage.content!=''&&$rootScope.webpage.type=="DICTIONARY"){
					 $rootScope.dictionary = eval("("+$rootScope.webpage.content+")");
			     }
			 }
		});
    };
    $scope.getData(1);
});
/**
 * 文件列表
 */
visitorModule.controller('visitorProjectCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=visitor/project/list.do|iLoading=FLOAT|iPost=true|iParams=&myself="+$stateParams.myself+"&name="+$stateParams.name;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**
 * 模块列表
 */
visitorModule.controller('visitorModuleCtrl', function($rootScope,$scope, $http, $state, $stateParams,$location,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		
		var url = $location.absUrl();
		var projectId = getParamFromUrl(url, 'projectId');
		var params = "&projectId=" + projectId + "&currentPage="+page;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		params = "iUrl=visitor/module/list.do|iLoading=FLOAT|iParams="+params;
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.moduleList = null;
			 }else{
				 $rootScope.moduleList = result.data;
				 $rootScope.others = result.others;
				 $rootScope.project = result.others.project;
				 $rootScope.page = result.page;
			 }
		});
    };
    $scope.getData();
});
/**
 * 模块列表
 */
visitorModule.controller('visitorModuleMenuCtrl', function($rootScope,$scope, $http, $state, $stateParams,$location,$http ,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		
		var url = $location.absUrl();
		var projectId = getParamFromUrl(url, "projectId");
		var params = "iUrl=visitor/module/menu.do|iLoading=FLOAT|iParams="+ "&projectId=" + projectId;
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.projectMenu = null;
				 $rootScope.project = null;
			 }else{
				 $rootScope.projectMenu = result.data;
				 $rootScope.project = result.others.project;
			 }
		});
    };
    $scope.getData();
});


/**
 * 接口列表
 */
visitorModule.controller('visitorInterfaceCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "&interfaceName=" + $stateParams.interfaceName + "&url="+ $stateParams.url + "&moduleId="+ $stateParams.moduleId;
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		params = "iUrl=visitor/interface/list.do|iLoading=FLOAT|iPost=true|iParams="+params;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});


/**
 * 接口详情
 * 不需要打开模态框，所以不能调用$rootScope中的getBaseData()
 */
visitorModule.controller('interfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iUrl=visitor/interface/detail.do|iLoading=FLOAT|iParams=&id="+$stateParams.id;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.model = result.data;
				 $rootScope.model.fullUrl = $rootScope.model.moduleUrl +  $rootScope.model.url;
				 $rootScope.versions = result.others.versions;
				 $rootScope.errors = eval("("+result.data.errors+")");
				 
				 // 如果param以form=开头，表示为form表单参数
				 if(result.data.param.length>5 && result.data.param.substring(0,5)=="form="){
					 $rootScope.formParams = eval("("+result.data.param.substring(5)+")");
				 }else{
					 $rootScope.model.customParams = result.data.param;
					 $rootScope.formParams = null;
				 }
				 
				 $rootScope.headers = eval("("+result.data.header+")");
				 
				 $rootScope.responseParams = eval("("+result.data.responseParam+")");
				 $rootScope.paramRemarks = eval("("+result.data.paramRemark+")");
				 $rootScope.others = result.others;
				 if(result.data.method)// 调试页面默认显示method中第一个
					 $rootScope.model.debugMethod = result.data.method.split(",")[0];
			 }
		});
    };
    $scope.getDebugResult= function() {
    	$rootScope.model.headers = getParamFromTable("debugHeader");
		$rootScope.model.params =getParamFromTable("debugParams");
    	var params = "iUrl=visitor/interface/debug.do|iLoading=FLOAT|iPost=POST|iParams=&"+$.param($rootScope.model);
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.model.debugResult = result.data.debugResult;
				 
				 $rootScope.jsonformat("debugResult",false);
				 $rootScope.others = result.others;
			 }
		});
    };
    $scope.getData();
});


visitorModule.controller('visitorSourceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iLoading=FLOAT|iUrl=visitor/source/detail.do?id="+$stateParams.id;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.source = null;
			 }else{
				 $rootScope.source = result.data;
			 }
		});
    };
    $scope.getData();
});
/**
 * 文件列表
 */
visitorModule.controller('visitorSourceCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		//setPwd不为空，表示用户输入了密码，需要记录至cookie中
		if(setPwd) setPassword();
		var params = "iUrl=visitor/source/list.do|iLoading=FLOAT|iParams=&moduleId="+$stateParams.moduleId;
		params +="&password="+unescapeAndDecode('password');
		params +="&visitCode="+unescapeAndDecode('visitCode');
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});



/***
 * 前端页面初始化，加载系统设置，菜单等
 */
visitorModule.controller('fontInit', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=visitor/init.do|iLoading=FLOAT"; //  表示查询所有
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
    $scope.getData();
});





