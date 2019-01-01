/**
 * 后台controller
 */
var userModule = angular.module("userModule", []);
var adminModule = angular.module("adminModule", []);

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

userModule.controller('loginOrRegisterCtrl', function($rootScope, $scope, $http, $state, $stateParams,httpService) {
    $scope.loginOrRegister = function(iurl, callBack, submitForm){
        var params = "iUrl=" + iurl + "|iLoading=FLOAT|iPost=POST";
        if (submitForm) {
            params = params + "|iParams=&"+ $.param($rootScope.model);
        }
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading=FLOAT');
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
            } else if(result.success==1){
                if (result.data) {
                    $rootScope.model = result.data;
                }
                if (callBack){
                    callBack();
                }
            } else {
                $rootScope.error = result.error.message;
            }
        }).error(function(result) {
            $rootScope.error = "未知异常，请联系开发人员查看日志";
        });
    }
    $scope.loginSuccess = function () {
        window.location.href="admin.do";
    }
    $scope.registerSuccess = function () {
        $rootScope.error = "注册成功，请登录";
        $rootScope.go("login");
    }
});

/***
 * 后台初始化，加载系统设置，菜单等
 */
userModule.controller('userCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    // 公用分页方法
    $scope.pageMethod = function(callBackMethod, page, updateUrl) {
        $scope[callBackMethod](page, updateUrl);
    };

    /*********************************** 列表 *********************************/
	// 左侧菜单，前50个模块
    $scope.queryTop50Module = function() {
        var params = "iUrl=user/module/list.do|iLoading=FLOAT|iPost=true|iParams=&pageSize=50&&projectId="+$stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"top50Module");
    };
    // 系统设置列表
    $scope.querySettingList = function(page) {
        var params = "iUrl=admin/setting/list.do|iLoading=FLOAT|iPost=true|iParams=&remark=" + $("#searchRemark").val()+"&key="+$stateParams.key;
        $rootScope.getBaseDataToDataKey($scope, $http, params, page, "settingList");
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
    $scope.queryInterfaceList = function(page, updateUrl) {
        var params = "iUrl=user/interface/list.do|iLoading=FLOAT|iPost=true|iParams=";
        params += "&interfaceName=" + $stateParams.interfaceName;
        params += "&url=" + $stateParams.url;
        params += "&moduleId="+ $stateParams.moduleId;
        params += "&projectId="+ $stateParams.projectId;
        if (updateUrl){
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = replaceParamFromUrl(url, 'interfaceName', $("#interfaceName").val());
            url = replaceParamFromUrl(url, 'url', $("#url").val());
            url = url.substr(1,url.length-1);
            $rootScope.go(url);
            return;
        }
        if (!page){
            page = $stateParams.currentPage;
        }
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "interfaces");
    };

    // 文章列表
    $scope.queryArticleList = function(page, updateUrl) {
        var params = "iUrl=user/article/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=ARTICLE"
            + "&moduleId="+$stateParams.moduleId
            + "&projectId=" + $stateParams.projectId
            + "&category="+ $stateParams.category
            + "&name="+$stateParams.name;
        if (updateUrl){
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = replaceParamFromUrl(url, 'name', $("#searchName").val());
            url = replaceParamFromUrl(url, 'category', $("#searchCategory").val());
            url = url.substr(1,url.length-1);
            $rootScope.go(url);
            return;
        }
        if (!page){
            page = $stateParams.currentPage;
        }
        $rootScope.getBaseDataToDataKey($scope, $http,params, page, "articles");
    };

    // 数据库表列表
    $scope.queryDictionaryList = function(page, updateUrl) {
        var params = "iUrl=user/article/list.do|iLoading=FLOAT|iPost=POST|iParams=&type=DICTIONARY"
            + "&moduleId="+$stateParams.moduleId
            + "&projectId=" + $stateParams.projectId
            + "&name="+$stateParams.name;
        if (updateUrl){
            params += "&currentPage=" + page;
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = replaceParamFromUrl(url, 'name', $("#searchName").val());
            url = url.substr(1,url.length-1);
            $rootScope.go(url);
            return;
        }
        if (!page){
            page = $stateParams.currentPage;
        }
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "dictionaries");
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
        var params = "iUrl=user/source/list.do|iLoading=FLOAT|iPost=true|iParams=&name="+$stateParams.name+"&moduleId="+$stateParams.moduleId+ "&projectId=" + $stateParams.projectId;
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

    // 接口详情
    $scope.getInterfaceDetail = function (isEdit, isDebug) {
        $rootScope.debugShowParam = true;
        $rootScope.interfaceDialog = 'header';
        var params = "iUrl=user/interface/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id + "&projectId=" + $stateParams.projectId + "&moduleId=" + $stateParams.moduleId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null,'model', function () {
            if (isEdit) {
                createWangEditor("interface-editor", "remark", initInterfaceEditor, 150);
            }
            $rootScope.errors = eval("("+$rootScope.model.errors+")")
            $rootScope.model.fullUrl = $rootScope.model.moduleUrl +  $rootScope.model.url;
            // $rootScope.paramRemarkList = eval("("+$rootScope.model.paramRemark+")");
            if($rootScope.model.method) {// 调试页面默认显示method中第一个
                $rootScope.model.debugMethod = $rootScope.model.method.split(",")[0];
            }

            if (isDebug){
                var val = $("#hasInstallPlug").val();
                if (val == "true"){
                    $("#has-plug").removeClass("none");
                    $("#no-plug").addClass("none");
                    $("#crap-debug-send-new").removeClass("none");
                    $("#crap-debug-send").addClass("none");
                }

                $("#editHeaderTable").find("tbody").find("tr").remove();
                $("#editParamTable").find("tbody").find("tr").remove();
                $.each($rootScope.model.crShowHeaderList, function (n, value) {
                    addOneDebugTr('editHeaderTable',null, value);
                });
                if ($rootScope.model.crShowParamList){
                    $.each($rootScope.model.crShowParamList, function (n, value) {
                        addOneDebugTr('editParamTable', null, value);
                    });
                }

                addOneDebugTr('editHeaderTable');
                addOneDebugTr('editParamTable');
            }

            if (isEdit) {
                $("#editResponseParamTable").find("tbody").find("tr").remove();
                $("#editHeaderTable").find("tbody").find("tr").remove();
                $("#editParamTable").find("tbody").find("tr").remove();

                $.each($rootScope.model.crShowHeaderList, function (n, value) {
                    addOneInterHeadTr(null, value, isDebug);
                });
                if ($rootScope.model.crShowParamList){
                    $.each($rootScope.model.crShowParamList, function (n, value) {
                        addOneInterParamTr(null, value);
                    });
                }
                $.each($rootScope.model.crShowResponseParamList, function (n, value) {
                    addOneInterRespTr(null, value);
                });

                addOneInterRespTr();
                addOneInterHeadTr();
                addOneInterParamTr();

                initDragTable("editHeaderTable");
                initDragTable("editParamTable");
                initDragTable("editResponseParamTable");
                $("#interfaceName").focus();
            }
        });
    };

    $scope.getArticleDetail = function (isEdit) {
        var params = "iUrl=user/article/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id + "&type=ARTICLE" +
            "&moduleId=" + $stateParams.moduleId + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null,'model', function () {
        	// 存在 article-editor 则初始化
			if (!isEdit) {
                return;
            }
            markdownEditor = null;
            if ($rootScope.model.useMarkdown){
                createEditorMe('markdown-editor', getRootScope().model.markdown);
            }
            createWangEditor("article-editor", "content", initArticleEditor, "500px");
        });
    };
    $scope.createEditorMe = function () {
        createEditorMe('markdown-editor', getRootScope().model.markdown);
    };
    $scope.getDictionaryDetail = function (isEdit) {
        var params = "iUrl=user/article/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id + "&type=DICTIONARY" +
            "&moduleId=" + $stateParams.moduleId + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null,'model', function () {
            // 编辑
            if (isEdit){
                if (!$rootScope.model.content) {
                    $rootScope.model.content = '{}';
                }

                var content = eval("(" + $rootScope.model.content + ")");
                $("#content").find("tbody").find("tr").remove();
                $.each(content, function (n, value) {
                    addOneDictionaryTr(null, value);
                });
                addOneDictionaryTr();
                initDragTable("content");
            }else if($rootScope.model.content!=''){
                $rootScope.model.dictionaries = eval("("+$rootScope.model.content+")");
            }
        });
    }

    // 接口调试
    $scope.debugInterface = function() {
        $rootScope.model.header = getParamFromTable('editHeaderTable', 'name');
        if($rootScope.model.paramType == 'FORM') {
            $rootScope.model.param = getParamFromTable('editParamTable', 'name');
        }
        var params = "iUrl=user/interface/debug.do|iLoading=FLOAT|iPost=POST|iParams=&"+$.param($rootScope.model);
        $rootScope.getBaseDataToDataKey($scope,$http,params, 0, "debug", function () {
            $rootScope.debugResult = format($rootScope.debug.debugResult, false);
        });
    };

    // 代码生成
    $scope.generateCode = function(type) {
        var params = "iUrl=user/dictionary/generateCode.do|iLoading=FLOAT|iPost=POST|iParams=&fieldNames=" + $rootScope.selectFields + "&type=" + type;
        $rootScope.getBaseDataToDataKey($scope,$http,params, 0, "generateCodeResult");
    };

    $scope.openInterfaceDialog = function(id, title, width){
        $rootScope.interfaceDialog = id;
        openMyDialog(title, width);
    };
    /*********************************** 回调方法 *********************************/
    // 保存markdown
    $rootScope.saveArticleCallBack = function () {
        if ($rootScope.model.useMarkdown == false){
            return;
        }
        $rootScope.model.useMarkdown = true;
        $rootScope.model.markdown = markdownEditor.getMarkdown();
        $rootScope.model.content = markdownEditor.getHTML()
    }


    $scope.clearDonwloadUrl = function(){
        $("#downloadUrl").html("");
    }
	$scope.getData = function(page,setPwd) {
		var params = "iUrl=admin/init.do|iLoading=fase"; //  表示查询所有
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
		var params = "iUrl=admin/closeErrorTips.do|iLoading=FLOAT";
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
		callAjaxByName("iUrl=user/loginOut.do|iLoading=false|ishowMethod=doNothing");
	}
    $scope.getData();
});
/*** 导入数据库表 ***/
userModule.controller('dictionaryInportFromSqlCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $rootScope.model = {};
    $rootScope.model.isMysql="true";
    $rootScope.error = null;
});


/**************************菜单列表****************************/
adminModule.controller('adminCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    // 系统属性
    $scope.getProperty = function() {
        var params = "iUrl=admin/property.do|iLoading=FLOAT";
        $rootScope.getBaseDataToDataKey($scope,$http,params, null, "property");
    };

    // 菜单列表
	$scope.queryMenuList = function(page) {
		if($("#searchType").val()!=null&&$("#searchType").val()!=''){
			$stateParams.type = $("#searchType").val();
		}
		var params = "iUrl=admin/menu/list.do|iLoading=FLOAT|iParams=&type="+$stateParams.type;
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

/**
userModule.controller('userTop50ProjectListCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $scope.getData = function() {
        var params = "iUrl=user/project/list.do|iLoading=FLOAT|iPost=true|iParams=&pageSize=50&projectShowType="+$stateParams.projectShowType;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"top50Project");
    };
    $scope.getData();
});**/









