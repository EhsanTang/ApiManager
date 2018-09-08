/**
 * 后台controller
 */
var userModule = angular.module("userModule", []);
var adminModule = angular.module("adminModule", []);

//以html形式输出
userModule.filter("trustHtml",function($sce){
	 return function (input){ return $sce.trustAsHtml(input); } ;
});

// 显示长度 wordwise：切字方式- 如果是 true，只切單字
userModule.filter('cut', function () {
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

userModule.controller('detailCtrl', function($scope, $http, $state, $stateParams,httpService) {});


/***
 * 后台初始化，加载系统设置，菜单等
 */
userModule.controller('userCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    // 公用分页方法
    $scope.pageMethod = function(callBackMethod, page, updateUrl) {
        $scope[callBackMethod](page, updateUrl);
    };
    /*********************************** 列表 *********************************/
    $scope.querySettingList = function(page) {
        var params = "iUrl=admin/setting/list.do|iLoading=FLOAT|iPost=true|iParams=&remark=" + $("#searchRemark").val()+"&key="+$stateParams.key;
        $rootScope.getBaseDataToDataKey($scope, $http, params, page, "settings");
    };
    // 项目列表
    $scope.queryProjectList = function(page) {
        var params = "iUrl=user/project/list.do|iLoading=FLOAT|iPost=true|iParams=&name="+$stateParams.name+"&type="+$stateParams.type+"&projectShowType="+$stateParams.projectShowType;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "projects");
    };

    // 错误码
    $scope.queryErrorList = function(page) {
        var params = "iUrl=user/error/list.do|iLoading=FLOAT|iPost=true|iParams=";
        params += "&projectId=" + $stateParams.projectId;
        params += "&errorMsg=" + $stateParams.errorMsg;
        params += "&errorCode=" + $stateParams.errorCode;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "errors");
    };

    // 项目成员
    $scope.queryProjectUserList = function(page) {
        var params = "iUrl=user/projectUser/list.do|iLoading=FLOAT|iParams=&projectId=" +$stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "projectUsers");
    };

    // 模块列表
    $scope.queryModuleList = function(page) {
        var params = "iUrl=user/module/list.do|iLoading=FLOAT|iParams=&projectId="+$stateParams.projectId+"&name="+$stateParams.name;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "modules");
    };

    // 查询用户列表
    $scope.queryUserList = function(page) {
        var params = "iUrl=user/list.do|iLoading=FLOAT|iPost=true|iParams=&trueName="
            + $stateParams.trueName + "&userName=" + $stateParams.userName + "&email=" + $stateParams.email;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "users");
    };

    // 接口列表
    $scope.queryInterfaceList = function(page) {
        var params = "";
        if($("#interfaceName").val()!=null&&$("#interfaceName").val()!=''){
            params += "&interfaceName=" + $("#interfaceName").val();
        }
        if($("#url").val()!=null&&$("#url").val()!=''){
            params += "&url=" + $("#url").val();
        }
        params +="&moduleId="+ $stateParams.moduleId;
        params +="&projectId="+ $stateParams.projectId;
        params = "iUrl=user/interface/list.do|iLoading=FLOAT|iPost=true|iParams="+params;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "interfaces");
    };

    $scope.queryArticleList = function(page, updateUrl) {
        var params = "iUrl=user/article/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=ARTICLE"
            + "&moduleId="+$stateParams.moduleId
            + "&projectId=" + $stateParams.projectId
            + "&category="+ $stateParams.category
            + "&name="+$stateParams.name
            + "&currentPage=" + $stateParams.currentPage;
        if (updateUrl){
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = replaceParamFromUrl(url, 'name', $("#searchName").val());
            url = replaceParamFromUrl(url, 'category', $("#searchCategory").val());
            url = url.substr(1,url.length-1);
            $rootScope.go(url);
            return;
        }
        $rootScope.getBaseDataToDataKey($scope, $http,params, page, "articles");
    };

    // 评论列表
    $scope.queryCommentList = function(page) {
        var params = "iUrl=user/comment/list.do|iLoading=FLOAT|iPost=POST|iParams=&articleId="+$stateParams.articleId + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "comments");
    };

    // 操作日志
    $scope.queryLogList = function(page) {
        var params = "iUrl=user/log/list.do|iLoading=FLOAT|iPost=true|iParams=&modelName="+$("#modelName").val()+"&identy="+$stateParams.identy;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "logs");
    };

    $scope.querySourceList = function(page) {
        var params = "iUrl=user/source/list.do|iLoading=FLOAT|iPost=true|iParams=&name="+$stateParams.name+"&moduleId="+$stateParams.moduleId;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "sources");
    };

    /*********************************** 详情 *********************************/
    // 项目详情
    $scope.getProjectDetail = function() {
        var params = "iUrl=user/project/detail.do|iLoading=FLOAT|iPost=true|iParams=&id="+$stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"projectDetail");

        params = "iUrl=user/project/moreInfo.do|iLoading=FLOAT|iPost=true|iParams=&id="+$stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"projectMoreInfo");
    };

    $scope.articleDetail = function () {
        var params = "iUrl=user/article/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id + "&type=ARTICLE" +
            "&moduleId=" + $stateParams.moduleId + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null,'model', function () {
        	// 存在 article-editor 则初始化
			if (document.getElementById("article-editor")) {
                markdownEditor = null;
                createWangEditor("article-editor", "content", initArticleEditor, "500px");
            }
        });
    };

    /*********************************** 回调方法 *********************************/
    // 保存markdown
    $rootScope.saveArticleCallBack = function () {
        if (!userMarkdown){
            return;
        }
        $rootScope.model.markdown = markdownEditor.getMarkdown();
        $rootScope.model.content = markdownEditor.getHTML()
    }


    $scope.clearDonwloadUrl = function(){
        $("#downloadUrl").html("");
    }
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
			if(id) {
                $("#" + id).removeClass("ndis");
            }
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
    $scope.getData();
});
/*** 导入数据库表 ***/
userModule.controller('dictionaryInportFromSqlCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
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
/**************************后端接口列表****************************/
userModule.controller('preLoginCtrl', function($rootScope,$scope, $http, $state, $stateParams,$timeout,httpService) {
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

userModule.controller('preRegisterCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
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
adminModule.controller('adminCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    // 系统属性
    $scope.getProperty = function() {
        var params = "iUrl=property.do|iLoading=FLOAT";
        $rootScope.getBaseDataToDataKey($scope,$http,params, null, "property");
    };

    // 菜单列表
	$scope.queryMenuList = function(page) {
		if($("#searchType").val()!=null&&$("#searchType").val()!=''){
			$stateParams.type = $("#searchType").val();
		}
		var params = "iUrl=menu/list.do|iLoading=FLOAT|iParams=&type="+$stateParams.type;
		if($("#menuName").val()!=null&&$("#menuName").val()!=''){
			params += "&menuName=" + $("#menuName").val();
		}else{
			params +="&parentId="+ $stateParams.parentId;
		}
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "menus");
    };

});

/************************hotSearchCtrl********/
userModule.controller('hotSearchCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $scope.getData = function(page) {
        var params = "iUrl=admin/hotSearch/list.do|iLoading=FLOAT|iParams=";
        $rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
userModule.controller('settingDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
	$scope.getData = function() {
		var params = "iLoading=FLOAT|iUrl=admin/setting/detail.do?id="+$stateParams.id+"&key="+$stateParams.key+"&type="+$stateParams.type;
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
userModule.controller('roleCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
	$scope.getData = function(page) {
		var params = "iUrl=admin/role/list.do|iLoading=FLOAT|iPost=true|iParams=&roleName=" + $("#roleName").val();;
		$rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});


userModule.controller('backInterfaceDetailCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
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

/**
userModule.controller('userTop50ProjectListCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $scope.getData = function() {
        var params = "iUrl=user/project/list.do|iLoading=FLOAT|iPost=true|iParams=&pageSize=50&projectShowType="+$stateParams.projectShowType;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"top50Project");
    };
    $scope.getData();
});**/

userModule.controller('userTop50ModuleListCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $scope.getData = function() {
        var params = "iUrl=user/module/list.do|iLoading=FLOAT|iPost=true|iParams=&pageSize=50&&projectId="+$stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"top50Module");
    };
});

/**************************数据库表列表****************************/
userModule.controller('userDictionaryCtrl', function($rootScope, $scope, $http, $state, $stateParams,$location,httpService) {
    $scope.getData = function(page) {
        var params = "iUrl=user/article/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=DICTIONARY"
            + "&moduleId="+$stateParams.moduleId
            + "&projectId=" + $stateParams.projectId
            + "&name="+$stateParams.name
            + "&currentPage=" + $stateParams.currentPage;
        if (page){
            params += "&currentPage=" + page;
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = replaceParamFromUrl(url, 'name', $("#searchName").val());
            url = url.substr(1,url.length-1);
            $rootScope.go(url);
            return;
        }
        $rootScope.getBaseDataToDataKey($scope,$http,params, null, "dictionaries");
    };

    $scope.dictionaryDetail = function () {
        var params = "iUrl=user/article/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id + "&type=DICTIONARY" +
            "&moduleId=" + $stateParams.moduleId + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null,'model', function () {
            // 如果是文章，eval会报错
            if (!$rootScope.model.content) {
                return;
            }
            var content = eval("(" + $rootScope.model.content + ")");
            $("#content").find("tbody").find("tr").remove();
            if (content != null && content != "") {
                var i = 0;
                $.each(content, function (n, value) {
                    i++;
                    addOneField(value.name, value.type, value.notNull, value.flag, value.def, value.remark, value.rowNum);
                });
            }
        });
    }
});







