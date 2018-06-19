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

mainModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,httpService) {});


/***
 * 后台初始化，加载系统设置，菜单等
 */
mainModule.controller('backInit', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=back/init.do|iLoading=fase"; //  表示查询所有
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=false');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				alert("系统初始化异常："+isSuccess.replace('[ERROR]', ''));
			}else{
				$rootScope.settings = result.data.settingMap;
				$rootScope.sessionAdminName = result.data.sessionAdminName;
				$rootScope.sessionAdminAuthor = result.data.sessionAdminAuthor;
				$rootScope.sessionAdminName = result.data.sessionAdminName;
				$rootScope.sessionAdminRoleIds = result.data.sessionAdminRoleIds;
				$rootScope.sessionAdminId =result.data.sessionAdminId;
				$rootScope.errorTips = result.data.errorTips;
			}
		});
    };
    // 判断是不是管理员
    $scope.isAdmin = function (id, needAuth){
		var auth = $("#sessionAuth").val();
		var hasAuth = false;
		// 最高管理员
		if( (","+auth+",").indexOf(",super,")>=0){
			hasAuth = true;
		}
		// 拥有权限的管理员
		if( (","+auth+",").indexOf(",ADMIN,")>=0){
			if(needAuth){
				if( (","+auth+",").indexOf(","+needAuth+",")>=0){
					hasAuth = true;
				}
			}else{
				hasAuth = true;
			}
		}
		if(hasAuth){
			if(id)
				$("#"+id).removeClass("ndis");
			return true;
		}else{
			if(id){
				if(!$("#"+id).hasClass("ndis"))
					$("#"+id).addClass("ndis");
			}
			return false;
		}
    }
    // 判断是否是最高管理员
    $scope.isSupperAdmin = function (id){
    	var auth = $("#sessionAuth").val();
    	if( (","+auth+",").indexOf(",super,")>=0){
			if(id)
				$("#"+id).removeClass("ndis");
			return true;
		}
    	else{
			if(id){
				if(!$("#"+id).hasClass("ndis"))
    				$("#"+id).addClass("ndis");
			}
			return false;
		}
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
	$scope.closeErrorTips = function(){
		var params = "iUrl=back/closeErrorTips.do|iLoading=FLOAT";
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
			 }else{
				 $rootScope.error = null;
				 $rootScope.errorTips = null;
			 }
		});
	}
	$scope.loginOut = function(){
		callAjaxByName("iUrl=back/loginOut.do|iLoading=false|ishowMethod=doNothing");
	}
	$scope.createEditor = function(id,field){
		createKindEditor(id,field);
	}
    $scope.getData();
});
/**************************后端接口列表****************************/
mainModule.controller('preLoginCtrl', function($rootScope,$scope, $http, $state, $stateParams,$timeout,httpService) {
	$scope.getData = function() {
		if($rootScope.model && $rootScope.model.sessionAdminName){
			window.location.href="admin.do";
		}else if($rootScope.model && $rootScope.model.tipMessage){
			showMessage('warnMessage', $rootScope.model.tipMessage,true,5);
		}else{
			var params = "iUrl=back/preLogin.do|iLoading=FLOAT";
			httpService.callHttpMethod($http,params).success(function(result) {
				var isSuccess = httpSuccess(result,'iLoading=FLOAT','0');
				if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
					alert(isSuccess.replace('[ERROR]', ''));
				 }else{
					 $rootScope.model = result.data.model;
					 if( $rootScope.model.remberPwd != 'NO'){
						 $timeout(function() {
							 $("#remberPwdYes").click();
		                 })
					 }else{
						 $timeout(function() {
							 $("#remberPwdNo").click();
		                 })
					 }
					 // 已经登陆成功，跳转至后台主页
					 if($rootScope.model && $rootScope.model.sessionAdminName){
							window.location.href="admin.do";
					}
					 showMessage('warnMessage', $rootScope.model.tipMessage,true,3);
				 }
			});
		}
    };
    $scope.changeRadio = function(value){
    	$rootScope.model.remberPwd = value;
    }

    $scope.login = function(iurl,myLoading){
		var iLoading = "TIPFLOAT";
		if(myLoading){
			iLoading = myLoading;
		}
		var params = "iUrl="+iurl+"|iLoading="+iLoading+"|iPost=POST|iParams=&"+ $('#loginForm').serialize();
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
    $scope.getData();
});

mainModule.controller('preRegisterCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function() {
		if($rootScope.model && $rootScope.model.id){
			$rootScope.model.tipMessage = "注册成功，请登录";
			$rootScope.verificationCode = "";
			window.location.href="loginOrRegister.do#/login";
		}else if($rootScope.model && $rootScope.model.tipMessage){
			showMessage('warnMessage', $rootScope.model.tipMessage,true,5);
		}else{
			var params = "iUrl=back/preRegister.do|iLoading=FLOAT";
			httpService.callHttpMethod($http,params).success(function(result) {
				var isSuccess = httpSuccess(result,'iLoading=FLOAT','0');
				if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
					alert(isSuccess.replace('[ERROR]', ''));
				 }else{
					 $rootScope.model = result.data;
				 }
			});
		}
    };
    $scope.getData();
});

/**************************菜单列表****************************/
mainModule.controller('menuCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
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
mainModule.controller('userCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/list.do|iLoading=FLOAT|iPost=true|iParams=&trueName=" 
			+ $stateParams.trueName + "&userName=" + $stateParams.userName + "&email=" + $stateParams.email;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************项目成员**************************/
mainModule.controller('userProjectUserCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/projectUser/list.do|iLoading=FLOAT|iParams=&projectId=" +$stateParams.projectId;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************article列表****************************/
mainModule.controller('userArticleCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/article/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=" 
			+ $stateParams.type+"&moduleId="+$stateParams.moduleId+
			"&category="+$("#searchCategory").val()+"&name="+$stateParams.name;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
mainModule.controller('userCommentCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/comment/list.do|iLoading=FLOAT|iPost=POST|iParams=&articleId="+$stateParams.articleId;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/*************************系统属性************/
mainModule.controller('propertyCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=property.do|iLoading=FLOAT";
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/************************hotSearchCtrl********/
mainModule.controller('hotSearchCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $scope.getData = function(page) {
        var params = "iUrl=hotSearch/list.do|iLoading=FLOAT";
        $rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************系统设置列表****************************/
mainModule.controller('settingCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=setting/list.do|iLoading=FLOAT|iPost=true|iParams=&remark=" + $("#searchRemark").val()+"&key="+$stateParams.key;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
mainModule.controller('settingDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
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
mainModule.controller('roleCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=role/list.do|iLoading=FLOAT|iPost=true|iParams=&roleName=" + $("#roleName").val();;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});

mainModule.controller('sourceCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/source/list.do|iLoading=FLOAT|iPost=true|iParams=&name="+$stateParams.name+"&moduleId="+$stateParams.moduleId;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
  });

mainModule.controller('backInterfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
    $scope.getRequestExam = function(editerId,targetId,item,tableId) {
    	var params = "iUrl=user/interface/getRequestExam.do|iLoading=FLOAT|iPost=true|iParams=&"+$.param($rootScope.model);
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
    	if(tableId=='editParamTable'&&item.param!=''){
    		
    		// 如果param为空，或者以form=开头，表示为form表单参数，否则表示为自定义参数
    		if(item.param.length<5 || item.param.substring(0,5)!="form="){
    			if(myConfirm("参数格式有误，将丢失所有参数，是否切换至表单模式？")){
    				$rootScope.model.params = eval("([])");
    			}else{
    				return;
    			}
    		}else{
    			// 将param转换为json数据
        		try{
        			$rootScope.model.params = eval("("+item.param.substring(5)+")");
        		}catch(e){
        			if(myConfirm("参数格式有误，将丢失所有参数，是否切换至表单模式？")){
        				$rootScope.model.params = eval("([])");
        			}else{
        				return;
        			}
        		}
    		}
    		
    	}else if(tableId=='editResponseParamTable'){
    		$rootScope.model.responseParams = eval("("+item.responseParam+")");
    	}else if(tableId=='editHeaderTable'){
    		$rootScope.model.headers = eval("("+item.header+")");
    	}else if(tableId=='eparamRemarkTable'){
    		$rootScope.model.paramRemarks = eval("("+item.paramRemark+")");
    	}
		$("#"+editerId).removeClass('none');
		$("#"+targetId).addClass('none');
    };

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
    	}else if(type=="paramRemark"){
    		var json = getParamFromTable('eparamRemarkTable');
    		try{
       		 eval("("+json+")");
       		}catch(e){
       			alert("输入有误，json解析出错："+e);
       			return;
       		}
    		item.paramRemark = json;
    	}
    	$("#"+editerId).addClass('none');
		$("#"+targetId).removeClass('none');
    };
    /***********添加参数********/
    $scope.addOneParam = function(field){
    	var newObj=new Object();
    	newObj.deep=0;
    	newObj.type="string";
    	newObj.necessary="true";
        newObj.inUrl="false";
    	$rootScope.model[field][$rootScope.model[field].length] = newObj;
    }
    /***********添加嵌套参数**************/
    $scope.addOneParamByParent = function(field,deep,parentIndex){
    	var newObj=new Object();
    	newObj.type="string";
    	newObj.necessary="true";
    	if(parentIndex || parentIndex==0){
    		// 兼容历史数据
        	if(!deep){
        		deep = 0;
        		$rootScope.model[field][parentIndex].deep=0;
        	}
        	newObj.deep=deep*1+1;
        	$rootScope.model[field].splice(parentIndex + 1, 0, newObj);
    	}else{
    		newObj.deep = 0*1;
    		$rootScope.model[field][$rootScope.model[field].length]=newObj;
    	}
    }
    
    $scope.deleteOneParamByParent = function(field,parentIndex,deep){
    	// 兼容历史数据
    	if(!deep){
    		deep = 0;
    		$rootScope.model[field][parentIndex].deep=0;
    	}
    	var needDelete = 1;
    	for(var i=parentIndex+1; i<$rootScope.model[field].length; i++){
    		if($rootScope.model[field][i].deep>deep){
    			needDelete ++;
    		}else{
    			break;
    		}
    	}
    	$rootScope.model[field].splice(parentIndex, needDelete);
    }
    $scope.importParams = function(field){
    	var jsonText = jsonToDiv($rootScope.model.importJson);
    	if(jsonText.length > 0){
    		$rootScope.model[field] = eval("("+jsonText+")");
    		if(field == 'responseParams'){
    			changeDisplay('responseEditorDiv','responseImportDiv');
    			changeDisplay('responseEparam','responseParam');
    		}else if(field == 'paramRemarks'){
    			changeDisplay('paramEditorDiv','paramImportDiv');
    			changeDisplay('eparamRemark','paramRemark');
    		}
    	}
	}
    /****************End:返回参数***************/
});
/**************************日志列表****************************/
mainModule.controller('logCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=log/list.do|iLoading=FLOAT|iPost=true|iParams=&modelName="+$("#modelName").val()+"&identy="+$stateParams.identy;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/*************************项目列表************************/
mainModule.controller('userProjectListCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/project/list.do|iLoading=FLOAT|iPost=true|iParams=&name="+$stateParams.name+"&type="+$stateParams.type+"&myself="+$stateParams.myself;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.clearDonwloadUrl = function(){
    	$("#downloadUrl").html("");
    }
    $scope.getData();
});
/*************************模块列表**********************/
mainModule.controller('userModuleListCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/module/list.do|iLoading=FLOAT|iParams=&projectId="+$stateParams.projectId+"&name="+$stateParams.name;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************后端接口列表****************************/
mainModule.controller('userInterfaceCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
	$scope.getData = function(page) {
		var params = "";
		if($("#interfaceName").val()!=null&&$("#interfaceName").val()!=''){
			params += "&interfaceName=" + $("#interfaceName").val();
		}
		if($("#url").val()!=null&&$("#url").val()!=''){
			params += "&url=" + $("#url").val();
		}
		params +="&moduleId="+ $stateParams.moduleId;
		params = "iUrl=user/interface/list.do|iLoading=FLOAT|iPost=true|iParams="+params;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************错误码列表****************************/
mainModule.controller('userErrorCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=user/error/list.do|iLoading=FLOAT|iPost=true|iParams=";
		params += "&projectId=" + $stateParams.projectId;
		params += "&errorMsg=" + $stateParams.errorMsg;
		params += "&errorCode=" + $stateParams.errorCode;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
mainModule.controller('dictionaryInportFromSqlCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	var params = "iUrl=user/article/detail.do?id=NULL|iLoading=FLOAT";
	httpService.callHttpMethod($http,params).success(function(result) {
		var isSuccess = httpSuccess(result,'iLoading=FLOAT');
		if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
			 $rootScope.error = isSuccess.replace('[ERROR]', '');
			 $rootScope.model = null;
		 }else{
			 $rootScope.model = result.data;
			 $rootScope.model.isMysql="true";
			 $rootScope.error = null;
		 }
	}).error(function(result) {
		closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
		$rootScope.error = result;
		 
	});
});



