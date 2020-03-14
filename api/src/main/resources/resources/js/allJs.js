var app = angular.module('app', [ 'ui.router', 'adminModule', 'userModule', 'bugModule', 'projectMetaModule', 'commonModule', 'commentModule', 'visitorModule']);
var visitorModule = angular.module("visitorModule", []);
var userModule = angular.module("userModule", []);
var adminModule = angular.module("adminModule", []);
var commentModule = angular.module("commentModule", []);
var bugModule = angular.module("bugModule", []);
var projectMetaModule = angular.module("projectMetaModule", []);
var commonModule = angular.module("commonModule", []);


var NEED_PASSWORD_CODE = "E000007";
var NEED_LOGIN = "E000021";

app.filter("removeLast",function(){
    return function (value, needRemoveChar) {
        if(!value) {
            return "";
        }else if (needRemoveChar){
            if (value.endsWith(needRemoveChar)){
                return value.substring(0,value.length-1);
            }else {
                return value;
            }
        }else {
            return value.substring(0,value.length-1);
        }
    }
});
//以html形式输出
app.filter("trustHtml",function($sce){
    return function (input){ return $sce.trustAsHtml(input); } ;
});

// 背景色
app.filter("getClass",function(){
    return function (value) {
        if(value=='primary') {
            return "bg-danger";
        }
        else if(value=='foreign') {
            return "bg-success";
        }
        else if(value=='associate') {
            return "bg-info";
        }
        else {
            return "";
        }
    }
});

// flag等转中文
app.filter("toChinese",function(){
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

app.filter("getUrl",function($stateParams){
    return function (value) {
        if (!value){
            return "";
        }
        var url = URL_LIST[value];
        if (!url){
            return "";
        }
        return url;
    }
});

/**
 * 由于整个应用都会和路由打交道，所以这里把$state和$stateParams这两个对象放到$rootScope上，方便其它地方引用和注入。
 * 这里的run方法只会在angular启动的时候运行一次。
 * @param  {[type]} $rootScope
 * @param  {[type]} $state
 * @param  {[type]} $stateParams
 * @return {[type]}
 */
app.run(function($rootScope, $state, $stateParams, $location, $http, $timeout,httpService) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.pick = [];
    $rootScope.stopPropagation = function (e) {
        e.stopPropagation();
        return false;
    }
    $rootScope.goBack = function goBack(){
        history.back(-1);
    }

    $rootScope.reload = function reload(){
        location.reload();
    }

    /**
     * 带前缀的跳转会失败，如：index.do#/user....
     * @param href
     */
    $rootScope.go = function (href) {
        var href = replaceParamFromUrl(href, 'timestamp', new Date().getTime());
        $location.url(href);
    }

    $rootScope.goAbsoluteUrl = function (href) {
        var href = replaceParamFromUrl(href, 'timestamp', new Date().getTime());
        location.href=href
    }

    /***
     * 废弃，采用对象
     * @deprecated
     * @param params
     * @param event
     * @param iCallBack
     * @param iCallBackParam
     */
    $rootScope.loadPickByName = function loadPick(params,event,iCallBack,iCallBackParam) {
        var iwidth = getValue(params,'iwidth');
        var iheight = getValue(params,'iheight');
        var radio = getValue(params,'radio');
        var tag = getValue(params,'tag');
        var code = getValue(params,'code');
        var type = getValue(params,'type');
        var iparams = getValue(params,'params');
        var showType = getValue(params,'showType');
        var def = getValue(params,'def');
        var tagName = getValue(params,'tagName');
        var iUrl = getValue(params,'iUrl');
        $rootScope.loadPick(event,iwidth,iheight,radio,tag,code,type,def,iparams,showType,iCallBack,iCallBackParam,tagName,iUrl);
    }
    $rootScope.loadPick = function loadPick(event,iwidth,iheight,radio,tag,code,type,def,params,showType,iCallBack,iCallBackParam,tagName,iUrl) {
        /***********加载选择对话框********************/
        if(!iUrl) {
            iUrl = "pick.do";
        }
        if(!params) {
            params = '';
        }
        if(!tagName) {
            tagName = '';
        }
        if(showType!='0'){
            if(!showType||showType=='') {
                showType = 5;
            }
        }
        $("#pickContent").html(loadText);
        //事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
        callAjaxByName("iUrl="+iUrl+"|isHowMethod=updateDiv|iPost=POST|iParams=&type="
            +type+"&radio="+radio+"&code="+code+"&tag="+tag+"&tagName="+tagName+"&def="+def+params,iCallBack,iCallBackParam);
        if(tagName) {
            lookUp('lookUp', event, iheight, iwidth, showType, tagName);
        }else {
            lookUp('lookUp', event, iheight, iwidth, showType, tag);
        }
        showMessage('lookUp','false',false,-1);
    }

    $rootScope.getBaseData = function($scope,$http,params,page) {
        if(page) {
            params += "&currentPage=" + page;
        }

        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading=FLOAT','0');
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
                $rootScope.list = null;
            }else{
                $rootScope.error = null;
                $rootScope.list = result.data;
                $rootScope.page = result.page;
                $rootScope.others=result.others;
                $rootScope.deleteIds = ",";
            }
        }).error(function(result) {
            lookUp('lookUp','',100,300,3);
            closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
            $rootScope.error = result;

        });;
    };

    $rootScope.getBaseDataToDataKey = function($scope,$http,params,page,dataKey,callBack) {
        if(!page) {
            page = 1;
        }
        params += "&currentPage=" + page;
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading=FLOAT','0');
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
            }else{
                $rootScope.error = null;
                if (dataKey && dataKey != null){
                    $rootScope[dataKey] = result.data;
                    if (!$rootScope.page){
                        $rootScope.page = {};
                    }
                    $rootScope.page[dataKey] = result.page;
                }

                if (callBack){
                    callBack();
                }
            }
        }).error(function(result) {
            lookUp('lookUp','',100,300,3);
            closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
            $rootScope.error = result;

        });
    };

    $rootScope.detail = function(title,iwidth,iurl,iParams,callBack) {
        //打开编辑对话框
        openMyDialog(title,iwidth);
        var params = "iUrl="+iurl+"|iLoading=FLOAT";
        if(iParams)
            params += "|iParams="+iParams;
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading=FLOAT');
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
                $rootScope.model = null;
            }else{
                $rootScope.model = result.data;
                $rootScope.error = null;
                $rootScope.deleteIds = ",";
                if(callBack)
                    callBack();
            }
        }).error(function(result) {
            lookUp('lookUp','',100,300,3);
            closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
            $rootScope.error = result;

        });;
    };
    //点击拷贝接口详情回调
    $rootScope.copyInterface = function() {
        changeDisplay('copyInterFace','interFaceDetail');
    };
    $rootScope.changeDisplay = function(id1, id2) {
        changeDisplay(id1,id2);
    }
    $rootScope.del = function(iUrl,id,title){
        title = title? title:"确认要删除【"+id+"】？";
        if (myConfirm(title)) {
            var params = "iUrl="+iUrl+"|iLoading=TIP";
            httpService.callHttpMethod($http,params).success(function(result) {
                var isSuccess = httpSuccess(result,'iLoading=TIP')
                if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                    $rootScope.error = isSuccess.replace('[ERROR]', '');
                }else{
                    /**
                     * 回调刷新当前页面数据
                     */
                    $rootScope.error = null;
                    $timeout(function() {
                        $("#refresh").click();
                    })
                }
            }).error(function(result) {
                closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP', 3);
                $rootScope.error = result;

            });;
        }
    };
    // 选中某个选项
    $rootScope.checkboxSelect = function(checkValues,value1, value2){
        var value = value1;
        if (value2){
            value = value + "_CA_SEPARATOR_" + value2;
        }
        if (!$rootScope[checkValues] ){
            $rootScope[checkValues] = value+",";
        }else if($rootScope[checkValues].indexOf(","+value+",")>=0 ){
            $rootScope[checkValues] = $rootScope[checkValues].replace(value+",","");
        }else{
            $rootScope[checkValues] = $rootScope[checkValues]+value+","
        }
    }
    /**
     * 全选、不选
     * @param id 全选按钮
     * @param name 列表项
     * @param list 数据集
     * @param field 选着的数据集字段
     */
    $rootScope.selectAll = function(id,name,list,field1, field2){
        if (!field1){
            field1 = "id";
        }
        selectAll(id, name);
        if($("#"+id).prop("checked")==true){
            $rootScope[name] = ",";
            for (var i=0;i<list.length;i++){
                var value = list[i][field1];
                if (field2){
                    value = value + "_CA_SEPARATOR_" + list[i][field2];
                }
                $rootScope[name] = $rootScope[name] + value + "," ;
            }
        }else{
            $rootScope[name] = ",";
        }
    }

    $rootScope.submitForm = function(iurl,callBack,myLoading, afterCallBack){
        /**
         * 回调刷新当前页面数据
         */
        if(callBack){
            callBack();
        }
        iLoading = "TIPFLOAT";
        if(myLoading){
            iLoading = myLoading;
        }
        var params = "iUrl="+iurl+"|iLoading="+iLoading+"|iPost=POST|iParams=&"+$.param($rootScope.model);
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading='+iLoading);
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
            }else if(result.success==1){
                $rootScope.error = null;
                $rootScope.model = result.data;
                //关闭编辑对话框
                closeMyDialog('myDialog');
                if(afterCallBack){
                    afterCallBack();
                } else {
                    $timeout(function() {
                        $("#refresh").click();
                    })
                }
            }
        }).error(function(result) {
            closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading='+iLoading, 3);
            $rootScope.error = result;

        });
    }

    /**
     * 发送请求工具方法
     */
    $rootScope.sendRequest = function(url,myLoading){
        var iLoading = "FLOATTIP";
        if(myLoading){
            iLoading = myLoading;
        }
        var params = "iUrl="+url+"|iLoading="+iLoading+"|iPost=POST";
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading='+iLoading)
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
            }else if(result.success==1){
                $rootScope.error = null;
                $timeout(function() {
                    $("#refresh").click();
                })
            }
        }).error(function(result) {
            lookUp('lookUp','',100,300,3);
            closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=PROPUP_FLOAT', 3);
            $rootScope.error = result;

        });
    }
    /***********************是否显示操作按钮************/

    $rootScope.hasError = function(error,id){
        if(error && error!=''){
            $("#"+id).removeClass("ndis");
            return true;
        }else{
            if(!$("#"+id).hasClass("ndis")) {
                $("#" + id).addClass("ndis");
            }
            return false;
        }
    }
    $rootScope.showOperation = function(dataType){
        var adminPermission = $rootScope.adminPermission
        if((","+adminPermission+",").indexOf(",SUPER,")>=0){
            return true;
        }
        if((","+adminPermission+",").indexOf(","+dataType+",")>=0){
            return true;
        }
        return false;
    }

    $rootScope.getDate = function(str){
        if(str && (str+"").indexOf(".")>0) {
            return new Date(str.split(".")[0].replace("-", "/").replace("-", "/"));
        }
    }
    /**
     * 发布文档评论回调
     */
    $rootScope.changeimg = function () {
        changeimg('imgCode2','verificationCode');
    }
    /**
     * 提交数据库表时回调将表格数据转换为json
     */
    $rootScope.preAddDictionary = function(){
        var content = getParamFromTable("content", 'name');
        $rootScope.model.content = content;
    }
    /**
     * 查看日志详情回调，格式化数据
     */
    $rootScope.logDetailFormat = function(){
        $rootScope.model.content  = format($rootScope.model.content);
    }

    $rootScope.jsonformat = function(id,tiperror){
        var result = format($rootScope.model[id],tiperror);
        if(result){
            $rootScope.model[id] = result;
        }
    }
    $rootScope.callAjaxByName = function(iurl){
        callAjaxByName(iurl);
    }

    $rootScope.iClose = function(id) {
        iClose(id);
    };
    /******静态化****************/
    $rootScope.staticize= function (id){
        callAjaxByName('iUrl=user/staticize/staticize.do?projectId='+id+'|iLoading=TIPFLOAT静态化中，请稍后...|ishowMethod=updateDivWithImg|iFormId=staticize-form');
    }
    $rootScope.downloadStaticize= function (id){
        var params = "iUrl=user/staticize/downloadStaticize.do?projectId="+id+"|iLoading=TIPFLOAT静态化中，请稍后...|iPost=POST";
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading=TIPFLOAT')
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                $rootScope.error = isSuccess.replace('[ERROR]', '');
            }else if(result.success==1){
                $("#downloadUrl").html("操作成功，将自动下载，3s后若无反应，请点击：<a href='"+ result.data +"' target='_blank'>下载</a> 手动下载");
                window.open(result.data);
            }
        }).error(function(result) {
            lookUp('lookUp','',100,300,3);
            closeTip('[ERROR]未知异常，请联系开发人员查看日志', 'iLoading=TIPFLOAT', 3);
            $rootScope.error = result;
        });
    }

    // 添加接口
    $rootScope.addInterfaceCallBack= function () {
        var headJson = getParamFromTable('editHeaderTable', 'name');
        try{
            eval("("+headJson+")");
        }catch(e){
            alert("请求头输入有误，json解析出错："+e);
            return;
        }
        $rootScope.model.header = headJson;

        var responseJson = getParamFromTable('editResponseParamTable', 'name');
        try{
            eval("("+responseJson+")");
        }catch(e){
            alert("返回参数输入有误，json解析出错："+e);
            return;
        }
        $rootScope.model.responseParam = responseJson;

        if($rootScope.model.paramType == 'FORM') {
            var paramJson = getParamFromTable('editParamTable', 'name');
            try {
                eval("(" + paramJson + ")");
            } catch (e) {
                alert("请求参数输入有误，json解析出错：" + e);
                return;
            }
            $rootScope.model.param = paramJson;
        }
    }
});


/**
 * 站点url
 * @type {{my-project: string}}
 */
var MY_PROJECT = "my-project";
var URL_LIST = {
    "my-project": "#user/project/list?projectShowType=3&menu_b=menu_create_join&type=-1&menu_a=menu-project&pageName=我的项目",
    "my-create-project" : "#user/project/list?projectShowType=1&menu_b=menu_create&type=-1&menu_a=menu-project&pageName=我的创建的项目",
    "my-join-project" : "#user/project/list?projectShowType=2&menu_b=menu_join&type=-1&menu_a=menu-project&pageName=我的加入的项目",
    "all-project" : "#user/project/list?projectShowType=0&menu_b=menu_all_project&type=-1&menu_a=menu-project&pageName=所有用户项目",
    "profile" : "#/profile?menu_a=menu-profile&pageName=个人资料"
}

app.factory('httpService', [ '$http', function($http) {
    return {
        callHttpMethod : function($http, params, iCallBack, iCallBackParam) {
            return callHttpName($http, params, iCallBack, iCallBackParam);
        }
    };
} ]);
function callHttpName($http, params, iCallBack, iCallBackParam) {
    var iUrl = getValue(params, 'iUrl');
    var iFormId = getValue(params, 'iFormId');
    var iPost = getValue(params, 'iPost');
    var iLoading = getValue(params, 'iLoading');
    var iTarget = getValue(params, 'iTarget');
    var iParams = getValue(params, 'iParams');
    return callHttp($http, iUrl, iFormId, iPost, iLoading, iTarget, iParams);
}

function callHttp($http, iUrl, iFormId, iPost, iLoading, iTarget, iParams) {
    iUrl=iUrl;
    iParams=iParams;
    iTarget = iTarget ? iTarget : 'lookUpContent';
    var xParams = "";
    if (iFormId) {
        xParams = $('#' + iFormId).serialize();
    }
    showTip(iTarget, iLoading);
    if (iPost) {
        return $http({
            method : 'POST',
            data : xParams + iParams,
            url : iUrl,
            async : true,
            headers : {
                'Content-Type' : 'application/x-www-form-urlencoded'
            }
        });
    } else {
        if(iUrl.indexOf("?")>0)
            iUrl = iUrl +"&";
        else
            iUrl = iUrl +"?";
        return $http({
            method : 'GET',
            url : iUrl + xParams + iParams
        });
    }

}
function httpSuccess(data, iLoading, tipTime) {
    if(data.success==0){
        if(data.error.code == NEED_PASSWORD_CODE){
            lookUp('passwordDiv', '', 300, 300 ,6,'');
            showMessage('passwordDiv','false',false,-1);
            showMessage('fade','false',false,-1);
            changeimg('imgCode','verificationCode');
            $("#password").val('');
            $("#password").focus();
            data = "[ERROR][" + NEED_PASSWORD_CODE + "] "+data.error.message+"，点击请输入访问密码";
        }else if(data.error.code == NEED_LOGIN ){
            data = "[ERROR][" + NEED_LOGIN + "] "+data.error.message;
        }else{
            data = "[ERROR]"+data.error.message;
        }
    }
    else if (isJson(data)) {
        data = "[OK]";
    } else {
        data = data.responseText;
        /*************未登录或发生未知错误********************/
        if (data.indexOf('[ERRORPAGE]') >= 0) {
            data = "[ERROR]抱歉，系统繁忙，请稍后再试！";
        }
        if (data.indexOf('[LOGINPAGE]') >= 0) {
            data = "[ERROR]尚未登录，请登录后再试！";
        }
    }
    closeTip(data, iLoading, tipTime);
    return data;
}
function isJson(obj) {
    return (typeof (obj) == "object"
        && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length);
}

/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * url : 中支持 currentPage时，修改currentPage ，调用$rootScope.go 才会有效
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
var commonUrlParam = 'projectId&projectName&moduleId&moduleName&menu_a&menu_b&dataType&pageName';
app.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('userBugList', {
        url : '/user/bug/list?currentPage&name&creator&executor&tracer&tester&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl: 'resources/html/user/bugList.tpl.html?v=V8.2.0_0630'
            }, 'page@userBugList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('userEditBug', {
        url : '/user/bug/edit?id&type&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630'
            }, 'main' : {
                templateUrl : 'resources/html/user/bugEdit.tpl.html?v=V8.2.0_0630'
            },'comment@userEditBug' : {
                templateUrl : 'resources/html/subTpl/bugComment.tpl.html?v=V8.2.0_0630'
            },'zoomImg@userEditBug' : {
                templateUrl : 'resources/html/subTpl/zoomImg.tpl.html?v=V8.2.0_0630'
            },'bugLog@userEditBug' : {
                templateUrl : 'resources/html/subTpl/bugLog.tpl.html?v=V8.2.0_0630'
            },'page@userEditBug' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    });

    // 搜索
    $stateProvider.state('userSearchList', {
        url : '/user/search/list?keyword&currentPage&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/searchList.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@userSearchList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    });

    // 文档
    $stateProvider.state('userArticleList', {
        url : '/user/article/list?currentPage&name&category&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleList.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@userArticleList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('userEditArticle', {
        url : '/user/article/edit?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleEdit.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('userArticleDetail', {
        url : '/user/article/detail?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    });

    // 数据库表
    $stateProvider.state('userDictionaryList', {
        url : '/user/dictionary/list?currentPage&name&category&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/dictionaryList.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@userDictionaryList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('userEditDictionary', {
        url : '/user/dictionary/edit?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/dictionaryEdit.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('userDictionaryDetail', {
        url : '/user/dictionary/detail?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/dictionaryDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    });

    // 接口
    $stateProvider.state('userInterList', {
        url : '/user/interface/list?&interfaceName&url&currentPage&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/interfaceList.tpl.html?v=V8.2.0_0630'
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceCopy.tpl.html?v=V8.2.0_0630';
                }
            }, 'page@userInterList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('userInterfaceEdit', {
        url : '/user/interface/edit?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceEdit.tpl.html?v=V8.2.0_0630';
                }
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/interEditDialog.tpl.html?v=V8.2.0_0630';
                }
            }, 'interBaseEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interBaseEdit.tpl.html?v=V8.2.0_0630'
            }, 'interResParamEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interResParamEdit.tpl.html?v=V8.2.0_0630'
            }, 'interParamEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interParamEdit.tpl.html?v=V8.2.0_0630'
            }, 'interHeaderEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interHeaderEdit.tpl.html?v=V8.2.0_0630'
            }, 'interExampleEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interExampleEdit.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('userInterfaceDetail', {
        url : '/user/interface/detail?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('userInterfaceDebug', {
        url : '/user/interface/debug?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceDebug.tpl.html?v=V8.2.0_0630';
                }
            }, 'interParamEdit@userInterfaceDebug' : {
                templateUrl : 'resources/html/subTpl/interParamEdit.tpl.html?v=V8.2.0_0630'
            }, 'interHeaderEdit@userInterfaceDebug' : {
                templateUrl : 'resources/html/subTpl/interHeaderEdit.tpl.html?v=V8.2.0_0630'
            }
        }
    });


    $stateProvider.state('loginOrRegister', {
        url : '/login',
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/login.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('register', {
        url : '/register',
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/register.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('findPwd', {
        url : '/findPwd',
        views : {
            'main' : {
                templateUrl : 'resources/html/user/findPwd.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('menuList', {
        url : '/admin/menu/list?parentId&type&menuName&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/menuList.tpl.html?v=V8.2.0_0630'
            },
            'page@menuList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/admin/menuDetail.tpl.html?v=V8.2.0_0630';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuMenu.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('projectList', {
        url : '/user/project/list?projectShowType&type&' + commonUrlParam,
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/user/projectList.tpl.html?v=V8.2.0_0630';
                }
            },'page@projectList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/projectDetail.tpl.html?v=V8.2.0_0630';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuProject.tpl.html?v=V8.2.0_0630';
                }
            }

        }
    }).state('project', {
        url : '/user/project?' + commonUrlParam,
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/user/project.tpl.html?v=V8.2.0_0630';
                }
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/projectDetail.tpl.html?v=V8.2.0_0630';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }

        }
    }).state('moduleList', {
        url : '/user/module/list?name&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/moduleList.tpl.html?v=V8.2.0_0630'
            },
            'page@moduleList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/moduleDetail.tpl.html?v=V8.2.0_0630';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('errorList', {
        url : '/user/error/list?' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/errorList.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@errorList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/errorDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('projectUserList', {
        url : '/user/projectUser/list?name&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/projectUserList.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@projectUserList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/projectUserDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('projectMetaList', {
        url : '/user/projectMeta/list?type&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/projectMetaList.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@projectMetaList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/projectMetaEdit.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('logList', {
        url : '/user/log/list?identy&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/logList.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@logList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/logDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('sourceList', {
        url : '/user/source/list?' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/sourceList.tpl.html?v=V8.2.0_0630'
            },
            'page@sourceList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/sourceDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('settingList', {
        url : '/admin/setting/list?key&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/settingList.tpl.html?v=V8.2.0_0630'
            },
            'page@settingList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('settingDetail', {
        url : '/admin/setting/detail?type&id&' + commonUrlParam,
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/admin/settingDetail_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('hotSearchList', {
        url : '/admin/hotSearch/list?' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/hotSearchList.tpl.html?v=V8.2.0_0630'
            },
            'page@hotSearchList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('configProperties', {
        url : '/admin/config/properties?' + commonUrlParam,
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/admin/config.properties.html';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('introduce', {
        url : '/user/introduce?' + commonUrlParam,
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/user/introduce.html';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('dictionaryImoprtFromSql', {
        url : '/user/article/dictionary/importFromSql?' + commonUrlParam,
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/user/dictionaryImportFromSql.tpl.html?v=V8.2.0_0630';
                }
            },'subMenu' :{
                templateUrl : 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('userList', {
        url : '/admin/user/list?' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/userList.tpl.html?v=V8.2.0_0630'
            },
            'page@userList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/admin/userDetail.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('commentList', {
        url : '/user/comment/list?type&targetId&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/commentList.tpl.html?v=V8.2.0_0630'
            },
            'page@commentList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/commentDetail.tpl.html?v=V8.2.0_0630';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    }).state('profile', {
        url : '/profile?' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/userDetail.tpl.html?v=V8.2.0_0630'
            }
        }
    })
});


/**
 * 前后台共有的路由
 */
app.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('visitorArticleDetailOld', {
        url : '/:projectId/article/detail/:moduleId/:type/:id',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != 'DICTIONARY')
                        return 'resources/html/visitor/articleDetail_ARTICLE.tpl.html?v=V8.2.0_0630';
                    else
                        return 'resources/html/visitor/articleDetail_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
                }
            },'comment@visitorArticleDetailOld' : {
                templateUrl : 'resources/html/subTpl/comment.tpl.html?v=V8.2.0_0630'
            },'page@visitorArticleDetailOld' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorArticleDetail', {
        url : '/article/detail?projectId&moduleId&type&id',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != 'DICTIONARY')
                        return 'resources/html/visitor/articleDetail_ARTICLE.tpl.html?v=V8.2.0_0630';
                    else
                        return 'resources/html/visitor/articleDetail_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
                }
            },'comment@visitorArticleDetail' : {
                templateUrl : 'resources/html/subTpl/comment.tpl.html?v=V8.2.0_0630'
            },'page@visitorArticleDetail' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorArticleListOld', {
        url : '/:projectId/article/list/:&moduleId/:type/:category/:name/:status',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != "DICTIONARY")
                        return 'resources/html/visitor/articleList_ARTICLE.tpl.html?v=V8.2.0_0630';
                    else
                        return 'resources/html/visitor/articleList_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
                }
            },'page@visitorArticleList' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorArticleList', {
        url : '/article/list?projectId&moduleId&type&category&name&status',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != "DICTIONARY")
                        return 'resources/html/visitor/articleList_ARTICLE.tpl.html?v=V8.2.0_0630';
                    else
                        return 'resources/html/visitor/articleList_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
                }
            },'page@visitorArticleList' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorSourceDetail', {
        url : '/source/detail?projectId&id',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/visitor/sourceDetail' +
                        '.tpl.html?v=V8.2.0_0630';
                }
            }
        }
    })
});

/**
 * 后台controller
 */

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
        var projectId = $stateParams.projectId;
        // 缓存，如果已经加载过数据，第二次则不加载数据
        if ($rootScope.queryTop50ModuleCacheKey == projectId){
            return;
        }
        var params = "iUrl=user/module/list.do|iLoading=FLOAT|iPost=true|iParams=&pageSize=50&&projectId="+ projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"top50Module", function () {
            $rootScope.queryTop50ModuleCacheKey = projectId;
        });
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

    // 文档列表
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
        $scope.initProjectDetail();
        var params = "iUrl=user/" +
            "project/moreInfo.do|iLoading=FLOAT|iPost=true|iParams=&id="+$stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"projectMoreInfo");
    };
    // 后端项目页面初始化项目信息：权限等
    $scope.initProjectDetail = function() {
        var projectId = $stateParams.projectId;
        if ($rootScope.projectPermissionCacheKey == projectId){
            return;
        }
        var params = "iUrl=user/project/detail.do|iLoading=FLOAT|iPost=true|iParams=&id="+projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"projectDetail", function () {
            $rootScope.projectPermission=$rootScope.projectDetail.projectPermission;
            $rootScope.projectPermissionCacheKey = projectId;
        });
    };

    // 接口详情
    $scope.getInterfaceDetail = function (isEdit, isDebug) {
        $rootScope.debugShowParam = true;
        $rootScope.interfaceDialog = 'header';
        var params = "iUrl=user/interface/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id +
            "&projectId=" + $stateParams.projectId + "&moduleId=" + $stateParams.moduleId +
            "&envId=" + $.cookie('projectEnv:' + $stateParams.projectId);
        $rootScope.getBaseDataToDataKey($scope,$http,params,null,'model', function () {
            if (isEdit) {
                createWangEditor("interface-editor", $rootScope.model.remark, initInterfaceEditor, 150);
            }
            $rootScope.errors = eval("("+$rootScope.model.errors+")")
            $rootScope.model.fullUrl = $rootScope.model.fullUrl;
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
                var envName = $.cookie('projectEnvName:' + $rootScope.model.projectId);
                if (envName){
                    $("#env-name").html(envName);
                }
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
                createEditorMe('markdown-editor', $rootScope.model.markdown);
            }
            createWangEditor("article-editor", $rootScope.model.content, initArticleEditor, "500px");
        });
    };
    $scope.createEditorMe = function () {
        createEditorMe('markdown-editor', $rootScope.model.markdown);
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
    $scope.adminInit = function() {
        var params = "iUrl=admin/init.do|iLoading=fase"; //  表示查询所有
        httpService.callHttpMethod($http,params).success(function(result) {
            var isSuccess = httpSuccess(result,'iLoading=false');
            if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
                alert("系统初始化异常："+isSuccess.replace('[ERROR]', ''));
            }else{
                $rootScope.settings = result.data.settingMap;
                $rootScope.sessionAdminName = result.data.sessionAdminName;
                $rootScope.adminPermission = result.data.adminPermission;
                $rootScope.sessionAdminName = result.data.sessionAdminName;
                $rootScope.sessionAdminId =result.data.sessionAdminId;
                $rootScope.errorTips = result.data.errorTips;
            }
        });
    };

    $scope.isProjectUser = function (needAuth){
        var hasPermission = $scope.isAdmin(null, "PROJECT");
        if (hasPermission) return true;

        hasPermission =  (","+ $rootScope.projectPermission +",").indexOf(",myData,")>=0;
        if (hasPermission) return true;

        hasPermission =  (","+ $rootScope.projectPermission +",").indexOf("," + needAuth + ",")>=0;
        if (hasPermission) return true;

        return false;
    }

    // 判断是不是管理员
    $scope.isAdmin = function (id, needAuth){
        var hasPermission = $scope.isSupperAdmin(id);
        if (hasPermission) return true;

        hasPermission =  (","+ $rootScope.adminPermission +",").indexOf(",ADMIN,")>=0;
        if (needAuth){
            hasPermission = hasPermission && (","+ $rootScope.adminPermission +",").indexOf("," + needAuth + ",")>=0;
        }
        $scope.checkPermission(id, hasPermission);
        return hasPermission;
    }

    // 判断是否是最高管理员
    $scope.isSupperAdmin = function (id){
        var hasPermission = (","+ $rootScope.adminPermission +",").indexOf(",SUPER,")>=0;
        return $scope.checkPermission(id, hasPermission);
    }

    $scope.checkPermission= function(id, hasPermission){
        if(hasPermission){
            if(id) {
                $("#" + id).removeClass("ndis");
            }
            return true;
        } else{
            if(id){
                if(!$("#"+id).hasClass("ndis")) {
                    $("#" + id).addClass("ndis");
                }
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
});
/*** 导入数据库表 ***/
userModule.controller('dictionaryInportFromSqlCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $rootScope.model = {};
    $rootScope.model.isMysql="true";
    $rootScope.model.moduleId = $stateParams.moduleId;
    $rootScope.model.moduleName = $stateParams.moduleId;
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

/**
 userModule.controller('userTop50ProjectListCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
    $scope.getData = function() {
        var params = "iUrl=user/project/list.do|iLoading=FLOAT|iPost=true|iParams=&pageSize=50&projectShowType="+$stateParams.projectShowType;
        $rootScope.getBaseDataToDataKey($scope,$http,params,1,"top50Project");
    };
    $scope.getData();
});**/










/**
 * bug管理系统 controller
 */
// bug列表
bugModule.controller('userBugCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    var VO_NAME = 'bugVO';
    var VO_LIST_NAME = 'bugVOList';

    // 公用分页方法
    $scope.pageMethod = function(callBackMethod, page, updateUrl) {
        $scope[callBackMethod](page, updateUrl);
    };
    /**
     * 列表
     * @param page
     * @param updateUrl
     */
    $scope.setFilter= function (name, value) {
        $stateParams[name] = value;
        $scope.queryBugList(1);
    }

    $scope.queryBugList = function (page, updateUrl) {
        var params = "iUrl=user/bug/list.do|iLoading=FLOAT|iPost=POST|iParams="
            + "&moduleId=" + $stateParams.moduleId
            + "&projectId=" + $stateParams.projectId
            + "&name=" + $stateParams.name
            + "&creator=" + $stateParams.creator
            + "&executor=" + $stateParams.executor
            + "&tracer=" + $stateParams.tracer
            + "&tester=" + $stateParams.tester;
        if (updateUrl) {
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = url.substr(1, url.length - 1);
            $rootScope.go(url);
            return;
        }
        if (!page) {
            page = $stateParams.currentPage;
        }
        $rootScope.getBaseDataToDataKey($scope, $http, params, page, VO_LIST_NAME);
    };

    $scope.queryBugLogList = function () {
        var params = "iUrl=user/bugLog/list.do|iLoading=FLOAT|iPost=POST|iParams="
            + "&bugId=" + $stateParams.id
            + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope, $http, params, 1, 'bugLogVOList');
    };

    /**
     * 详情
     */
    var bugEdit;
    $scope.getBugDetail = function () {
        var params = "iUrl=user/bug/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id +
            "&moduleId=" + $stateParams.moduleId + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null, VO_NAME, function () {
            bugEdit = createWangEditor("bug-editor", $rootScope[VO_NAME].content, initBugEditor, "300px");
            $rootScope[VO_NAME].oldName = $rootScope[VO_NAME].name;
            $rootScope[VO_NAME].oldContent = $rootScope[VO_NAME].content;
            $rootScope[VO_NAME].isEdit = false;
            $rootScope[VO_NAME].isAdd = false;
            if ($stateParams.id == 'NULL' || $stateParams.id == 'null'){
                $("#bug-name").focus();
                $rootScope[VO_NAME].isEdit = true;
                $rootScope[VO_NAME].isAdd = true;
            }
            return;
        });
    };

    /**
     * 名称
     */
    $scope.updateBugName = function() {
        var id = $rootScope[VO_NAME].id;
        if (!id || id == null || id == 'null' || id == 'NULL'){
            return;
        }
        var name = $rootScope[VO_NAME].name;
        if ($rootScope[VO_NAME].oldName != name){
            var params = "iUrl=user/bug/changeBug.do|iLoading=FLOAT|iPost=POST|iParams=&type=name&value=" +name +
                "&id=" + id;
            $rootScope.getBaseDataToDataKey($scope,$http,params,null, null, function () {
                $rootScope[VO_NAME].oldName = name;
                $scope.queryBugLogList();
            });
        }
    }

    /**
     * 内容
     */
    $scope.updateBugContent = function() {
        var content = $rootScope[VO_NAME].content;
        var params = "iUrl=user/bug/changeBug.do|iLoading=FLOAT|iPost=POST|iParams=&type=content&value=" +content +
            "&id=" +$rootScope[VO_NAME].id ;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null, null, function () {
            $rootScope[VO_NAME].isEdit = false;
            $rootScope[VO_NAME].oldContent = $rootScope[VO_NAME].content;
            $scope.queryBugLogList();
        });
    }
    /**
     * 取消编辑
     */
    $scope.cancelBugContent = function() {
        $rootScope[VO_NAME].content = $rootScope[VO_NAME].oldContent;
        bugEdit.txt.html($rootScope[VO_NAME].content);
        $rootScope[VO_NAME].isEdit = false;
    }

    $scope.addBug = function() {
        var params = "iUrl=user/bug/add.do|iLoading=FLOAT|iPost=POST|iParams=&" + $.param($rootScope[VO_NAME]);
        $rootScope.getBaseDataToDataKey($scope, $http, params, null, null, function () {
            goBack();
        });
    }
});


function loadBugPick($this, $event, iwidth, iheight, type) {
    /***********加载选择对话框********************/
    var obj = $($this);
    var tag = obj.attr('id');
    $("#pickContent").html(loadText);
    callAjaxByName("iUrl=user/bug/pick.do|isHowMethod=updateDiv|iPost=POST|iParams=&type="+type+"&tag="+ tag +
        "&def=" + obj.attr("crap-def") + "&pickParam=" + obj.attr('crap-pick-param'));
    lookUp('lookUp', $event, iheight, iwidth, 5, tag);
    showMessage('lookUp','false',false,-1);
}

function initBugEditor(editor) {
    var VO_NAME = 'bugVO';
    var root = getRootScope();
    // 配置菜单
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',
        'strikeThrough',
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',
        'justify',  // 对齐方式
        'quote',
        'undo',  // 撤销
        'redo',  // 重复
        'image',
        'table'
    ];
    editor.customConfig.uploadImgMaxLength = 1;
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024; // 3M
    editor.customConfig.uploadImgServer = 'user/file/upload.do';
    editor.customConfig.uploadFileName = 'img';
    editor.customConfig.zIndex = 999;
    editor.customConfig.uploadImgHooks = {
        fail: function (xhr, editor, result) {
            $("#lookUpContent").html(err1 + "&nbsp; " + result.errorMessage + "" + err2);
            showMessage('lookUp', 'false', false, 3);
        }
    }
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        root.$apply(function () {
            root[VO_NAME].content = html;
        });
    }
}

/**
 * projectMeta管理系统 controller
 */
projectMetaModule.controller('userProjectMetaCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    var VO_NAME = 'projectMetaVO';
    var VO_LIST_NAME = 'projectMetaVOList';

    // 公用分页方法
    $scope.pageMethod = function(callBackMethod, page, updateUrl) {
        $scope[callBackMethod](page, updateUrl);
    };

    $scope.queryProjectMetaList = function (page, updateUrl) {
        var params = "iUrl=user/projectMeta/list.do|iLoading=FLOAT|iPost=POST|iParams="
            + "&moduleId=" + $stateParams.moduleId
            + "&projectId=" + $stateParams.projectId
            + "&name=" + $stateParams.name
            + "&type=" + $stateParams.type;
        if (updateUrl) {
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = url.substr(1, url.length - 1);
            $rootScope.go(url);
            return;
        }
        if (!page) {
            page = $stateParams.currentPage;
        }
        $rootScope.getBaseDataToDataKey($scope, $http, params, page, VO_LIST_NAME);
    };
});


/**
 */
commonModule.controller('userCommonCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    // var VO_NAME = 'projectMetaVO';
    var VO_LIST_NAME = 'searchResults';
    var BASE_URL = "user/search/list";

    // 公用分页方法
    $scope.pageMethod = function(callBackMethod, page, updateUrl) {
        $scope[callBackMethod](null, page, updateUrl);
    };

    $scope.search = function (event, page, updateUrl) {
        if (event != null && event.keyCode != 13){
            return;
        }
        var params ="moduleId=" + $stateParams.moduleId
            + "&projectId=" + $stateParams.projectId
            + "&keyword=" + $stateParams.keyword
            + "&projectName=" + $stateParams.projectName;
        if ($location.url().indexOf(BASE_URL) < 0){
            $rootScope.go(BASE_URL + "?" +  params);
            return;
        }

        var params = "iUrl=user/search.do|iLoading=FLOAT|iPost=POST|iParams=" + "&" + params;
        if (updateUrl) {
            var url = replaceParamFromUrl($location.url(), 'currentPage', page);
            url = url.substr(1, url.length - 1);
            $rootScope.go(url);
            return;
        }
        if (!page) {
            page = $stateParams.currentPage;
        }
        $rootScope.getBaseDataToDataKey($scope, $http, params, page, VO_LIST_NAME);
    };
});

/**
 * 评论 controller
 */

/**
 * 前端页面 controller
 */
commentModule.controller('commentCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    var VO_NAME = 'commentVO';
    var VO_LIST_NAME = 'commentVOList';
    // 公用分页方法
    $scope.pageMethod = function (callBackMethod, page, updateUrl) {
        $scope[callBackMethod](page, updateUrl);
    };

    /**
     * 列表
     * @param page
     * @param updateUrl
     */
    $scope.queryCommentList = function (page, updateUrl) {
        var params = "iUrl=comment/list.do|iLoading=FLOAT|iPost=POST|iParams="
            + "&targetId=" + $stateParams.id
            + "&type=" + $stateParams.type;
        if (!page) {
            page = $stateParams.currentPage;
        }
        $rootScope.getBaseDataToDataKey($scope, $http, params, page, VO_LIST_NAME);
    };

    /**
     * 详情
     */
    $scope.preAddComment = function () {
        var params = "iUrl=comment/preAdd.do|iLoading=FALSE|iPost=POST|iParams=&type=" + $stateParams.type +
            "&targetId=" + $stateParams.id;
        $rootScope.getBaseDataToDataKey($scope, $http, params, null, VO_NAME, function () {});
    };

    $scope.addComment = function() {
        var params = "iUrl=comment/add.do|iLoading=FLOAT|iPost=POST|iParams=&" + $.param($rootScope[VO_NAME]);
        $rootScope.getBaseDataToDataKey($scope, $http, params, null, null, function () {
            changeimg('comment-img-code','comment-img-input');
            $scope.queryCommentList(1,true);
        });
    }
});

/**
 * 后端页面 controller
 */
userModule.controller('userCommentCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    // 公用分页方法
    $scope.pageMethod = function(callBackMethod, page, updateUrl) {
        $scope[callBackMethod](page, updateUrl);
    };

    $scope.queryUserCommentList = function(page) {
        var params = "iUrl=user/comment/list.do|iLoading=FLOAT|iPost=POST|iParams=&targetId="+$stateParams.targetId
            + "&projectId=" + $stateParams.projectId
            + "&type=" + $stateParams.type;
        $rootScope.getBaseDataToDataKey($scope,$http,params, page, "comments");
    };
});

/**
 * 前端controller
 */
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
        params = "iUrl=search.do|iPost=POST|iLoading=FLOAT|iParams=&keyword="+ $stateParams.keyword;
        $rootScope.getBaseData($scope,$http,params,page);
    };
    $scope.getData();
});
/**************************错误码列表****************************/
visitorModule.controller('errorCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
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
visitorModule.controller('visitorDictCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
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
visitorModule.controller('fontArticleCtrl', function($rootScope,$scope, $http, $state, $stateParams,httpService) {
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
 * article详情（数据库表，网站页面，文档）
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
                $rootScope.article = null;
                $rootScope.model = null;//初始化评论对象
            }else{
                $rootScope.article = result.data;
                $rootScope.others = result.others;//导航路径
                //如果是数据库表，则将内容转为json
                if($rootScope.article.content!=''&&$rootScope.article.type=="DICTIONARY"){
                    $rootScope.dictionary = eval("("+$rootScope.article.content+")");
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
        var params = "iUrl=visitor/project/list.do|iLoading=FLOAT|iPost=true|iParams=&projectShowType="+$stateParams.projectShowType+"&name="+$stateParams.name;
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

                $rootScope.paramRemarks = eval("("+result.data.paramRemark+")");
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







/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/notFound');
    $stateProvider.state('notFound', {
        url : '/notFound',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/notFound.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorSearchCtrl', {
        url : '/visitorSearch?keyword',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/visitor/searchResult.tpl.html?v=V8.2.0_0630';
                }
            },
            'page@visitorSearchCtrl' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorError', {
        url : '/error/list?projectId',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/errorList.tpl.html?v=V8.2.0_0630'
            },
            'page@visitorError' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorModuleCtrl', {
        url : '/module/list?projectId',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/moduleList.tpl.html?v=V8.2.0_0630'
            },
            'page@visitorModuleCtrl' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorInterfaceCtrl', {
        url : '/interface/list?projectId&moduleId',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/interfaceList.tpl.html?v=V8.2.0_0630'
            },
            'page@visitorInterfaceCtrl' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorInterfaceDetailCtrl', {
        url : '/interface/detail?projectId&id',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/interfaceDetail.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('frontInterfaceDetailCtrlOld', {
        url : '/:projectId/front/interfaceDetail/:id',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/interfaceDetail.tpl.html'
            }
        }
    }).state('visitorSourceList', {
        url : '/source/list?projectId&moduleId',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/sourceList.tpl.html?v=V8.2.0_0630'
            },
            'page@visitorSourceList' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorProjectList', {
        url : '/project/list?projectShowType&type',
        views : {
            'main' : {
                templateUrl : 'resources/html/visitor/projectList.tpl.html?v=V8.2.0_0630'
            },
            'page@visitorProjectList' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    })
    /*********************前端项目主页*******************/
});

var succ1 = '<div class=\"text-success h tc\"><i class="iconfont">&#xe6b1;</i>&nbsp;&nbsp;';
var succ2 = '</div>';
var err1 = '<div class=\"text-danger h tc\"><i class="iconfont">&#xe6b7;</i>&nbsp;&nbsp;';
var err2 = '</div>';
var loadText = "<div class='tc h tip'><p><img src=\"resources/images/loading.gif\" />&nbsp;&nbsp;努力加载中，请稍后...</p></div>";
function openPage(link){
    location.href = link;
}
function voidFunction(){}
function goBack(){
    history.back(-1);
}
/*******************************************************************************
 *callAjax传递函数时直接写函数名即可，不需要加引号
 ******************************************************************************/
/**
 * 根据键值对调用callAjax
 */
function callAjaxByName(params,iCallBack,iCallBackParam) {
    var iUrl = getValue(params,'iUrl');
    var iFormId = getValue(params,'iFormId');
    var iPost = getValue(params,'iPost');
    var isHowMethod = getValue(params,'isHowMethod');
    var iLoading = getValue(params,'iLoading');
    var iTarget = getValue(params,'iTarget');
    var iParams = getValue(params,'iParams');
    var iAsync = getValue(params,'iAsync');
    var tipTime = getValue(params,'tipTime');
    return callAjax(iUrl, iFormId, iPost, isHowMethod, iLoading, iTarget,
        iParams, iCallBack, iCallBackParam, iAsync,tipTime) ;
}
function getValue(params, key)
{
    var splitstr= new Array();
    splitstr=params.split("|");
    for(var i=0;i<splitstr.length;i++)
    {
        var indexNum = splitstr[i].indexOf('=');
        var key2 = splitstr[i].substr(0,indexNum);
        if(key2 == key){
            return splitstr[i].replace(key+"=","");
        }

    }
    return '';
}
/**
 *
 * @param iurl
 *            地址
 * @param iFormId
 *            表单id
 * @param iPost
 *            发送方式
 * @param isHowMethod
 *            返回数据显示方式:(0:doNothing).什么都不做 (1:updateInput).更新input文本域中的值 (2:updateDivWithImg).更新div中的html，带图标
 *            (3:updateDiv).更新div中的html，不带图标 (4:html).返回html页面 (5:replaceDiv).替换div
 *            (6:deleteDiv).删除div (7:return).返回数据 (100:custom).调用自定义回调函数刷新数据
 * @param iLoading
 *            加载提示文字,false表示不显示提示，包含PROPUP表示弹出提示
 * @param iTarget
 *            刷新数据的div
 * @param iparams
 *            传递的参数
 * @param iCallBack
 *            回调函数
 * @param iCallBackParam
 *            回调函数参数，用于刷新数据
 * @param iAsync 是否异步
 * @param tipTime propup关闭时间，-1时将不会提示成功，需要使用iTarget更新数据
 *
 * js中undefined,0,null,'',"",false均代表false
 */
// ----------------------------------//
function callAjax(iUrl, iFormId, iPost, isHowMethod, iLoading, iTarget,
                  iParams, iCallBack, iCallBackParam, iAsync, tipTime) {
    //存储ajax返回的数据
    var idata = '';
    var aAsync = (iAsync=='false') ? false : true;
    var aPost = iPost ? 'POST' : 'GET';
    iTarget = iTarget ? iTarget : 'lookUpContent';

    // 获取参数
    var xParams = "";
    if(iFormId){
        xParams = $('#'+iFormId).serialize();
    }

    // 显示提示语句，只有异步请求才显示提示框
    if(aAsync) {
        showTip(iTarget, iLoading);
    }

    xParams = xParams + '&CPTS=' + new Date().getTime();
    $.ajax({
        type : aPost,
        url : iUrl,
        async : aAsync,
        timeout: 3000,
        data : xParams + iParams,
        complete : function(responseData, textStatus) {
            var data = responseData.responseText;
            if (textStatus == "timeout") {
                data = "[ERROR]抱歉，网络异常，请稍后再试！Status:" + responseData.status + "，StatusText:" + responseData.statusText;
            }
            if (textStatus == "error" || data == null){
                data = "[ERROR]抱歉，系统繁忙，请稍后再试！Status:" + responseData.status + "，StatusText:" + responseData.statusText;
            }
            /*************未登录或发生未知错误********************/
            if(data.indexOf('[ERRORPAGE]') >= 0){
                data = "[ERROR]抱歉，系统繁忙，请稍后再试！";
            }
            if(data.indexOf('[LOGINPAGE]') >= 0 || data.indexOf('"code":"000021"') >= 0){
                data = "[ERROR]尚未登录，请登录后再试！";
            }
            if(data.indexOf('"success":0') >= 0){
                try{
                    data = "[ERROR]"+ eval("(" + data + ")").error.message;
                }catch (e) {}
            }
            if(data.indexOf('"success":1') >= 0){
                var json = eval("(" + data + ")");
                if (json.tipMessage != null && json.tipMessage !=''){
                    data = "[OK]" + json.tipMessage;
                }else {
                    data = "[OK]操作成功！";
                }
            }

            //当返回失败页面时需将data替换成提示语句
            if (isHowMethod == '1' || isHowMethod == 'updateInput') {
                $("#"+iTarget).val(data.replace('[OK]', '').replace('[ERROR]', ''));
            } else if (isHowMethod == '2' || isHowMethod == 'updateDivWithImg') {
                if (data.indexOf('[OK]') >= 0) {
                    $("#" + iTarget).html(succ1 + data.replace('[OK]', '') + succ2);
                }
                else {
                    $("#" + iTarget).html(err1 + data.replace('[ERROR]', '') + err2);
                }
            }else if (isHowMethod == '3' || isHowMethod == 'updateDiv') {
                $("#"+iTarget).html(data.replace('[OK]', '').replace('[ERROR]', ''));
            } else if (isHowMethod == '4' || isHowMethod == 'html') {
                if (data.indexOf('[ERROR]') < 0){
                    if(data.trim().length==0) {
                        $("#" + iTarget).html("<div class='tc pt10'>没有数据！</div>");
                    } else {
                        $("#" + iTarget).html(data);
                    }
                }else{
                    $("#"+iTarget).html(err1+data.replace('[ERROR]', '')+ err2);
                }
            } else if (isHowMethod == '5' || isHowMethod == 'replaceDiv') {
                if (data.indexOf('[ERROR]') < 0)
                    $("#"+iTarget).replaceWith(data);
                else{
                    lookUp('lookUp','',100,300,3);
                    $("#lookUpContent").html(err1+data.replace('[ERROR]', '')+err2);
                    showMessage('lookUp','false',false,-1);
                }

            } else if (isHowMethod == '6' || isHowMethod == 'deleteDiv') {
                if (data.indexOf('[ERROR]') < 0)
                    $("#"+iTarget).fadeOut(300);
                else{
                    lookUp('lookUp','',100,300,3);
                    $("#lookUpContent").html(err1+data.replace('[ERROR]', '')+err2);
                    showMessage('lookUp','false',false,-1);
                }

            } else if (isHowMethod == '7' || isHowMethod == 'return') {
                idata = data;
            }
            if (iCallBack) {
                if (iCallBackParam) {
                    iCallBack(data,iCallBackParam);
                } else {
                    iCallBack(data);
                }
            }
            //100需自行处理提示信息
            if (isHowMethod != '100' && isHowMethod != 'custom') {
                closeTip(data, iLoading, tipTime);
            }
        }
    });
    return idata;
}
/**
 *
 * @param iTarget
 * @param iLoading //fase:不提示，propup:弹窗提示
 */
var floatTimes = 0;
function showTip(iTarget,iLoading) {
    var oldLoadText = iLoading;
    var floatOrPropUp = false;
    if(oldLoadText.toUpperCase().indexOf('PROPUP') >= 0){
        iLoading = iLoading.toUpperCase().replace('PROPUP', '').replace('FLOAT', '');
        if(iLoading==""){
            iLoading = "努力加载中，请稍后...";
        }
        $("#lookUpContent").html(loadText.replace("努力加载中，请稍后...", iLoading));
        lookUp('lookUp','',100,300,3);
        showMessage('lookUp','false',false,-1);
        floatOrPropUp = true;
    }
    if(oldLoadText.toUpperCase().indexOf('FLOAT') >= 0){
        showMessage('float','false',false,-1);
        floatOrPropUp = true;
        if( floatTimes < 0 ){
            floatTimes = 0;
        }
        floatTimes = floatTimes + 1;
    }
    if (!floatOrPropUp && document.getElementById(iTarget)&&document.getElementById(iTarget).tagName != "INPUT") {
        if (iLoading.toUpperCase() != "FALSE"){
            //传递的参数含有图片，表示不以div的形式显示提示内容
            if(iLoading.indexOf("<img")>=0){
                $("#"+iTarget).html(iLoading);
            }else{
                if(iLoading=="")
                    iLoading = "努力加载中，请稍后...";
                $("#"+iTarget).html(loadText.replace("努力加载中，请稍后...", iLoading));
            }
        }
    }
}
function closeTip(data,iLoading,tipTime){
    tipTime = tipTime?tipTime:5;
    if (data.indexOf('[OK]')>=0 || data.indexOf('[ERROR]') < 0){
        tipTime = 2;
    }

    var tipMessage = tipTime+ "秒后自动关闭";
    if(tipTime==-1){//不关闭
        tipMessage = '';
    }
    if(data.indexOf('[OK]')>=0){
        if( data.replace('[OK]','')!='' )
            tipMessage = data.replace('[OK]','') + '，' + tipMessage;
        else
            tipMessage = "操作成功" + '，' + tipMessage;
    }
    if(data.indexOf('[ERROR]')>=0){
        tipMessage = data.replace('[ERROR]', '') + "<br>" + tipMessage;
    }

    if(iLoading.toUpperCase().indexOf('PROPUP') >= 0){
        //返回结果有提示
        if (data.indexOf('[OK]')>=0){
            $("#lookUpContent").html(succ1 + tipMessage +succ2);
        }
        //返回结果没有提示
        else if(data.indexOf('[ERROR]') < 0){
            $("#lookUpContent").html(succ1+tipMessage+succ2);
        }
        else{
            $("#lookUpContent").html(err1+tipMessage +err2);
        }
        showMessage('lookUp','false',false,tipTime);
    }
    if(iLoading.toUpperCase().indexOf('FLOAT') >= 0){
        floatTimes = floatTimes - 1;
        if( floatTimes == 0){
            showMessage("float",'false',false,0);
        }
    }
    if(iLoading.toUpperCase().indexOf('TIP') >= 0){
        if(tipMessage!="" && tipMessage!="false" && tipMessage!=false){
            if (tipMessage.length < 10){
                $("#tip-div").width(100)
            } else if (tipMessage.length < 20){
                $("#tip-div").width(200)
            } else if (tipMessage.length < 50){
                $("#tip-div").width(400)
            }else{
                $("#tip-div").width(600)
            }
        }
        // +50 padding宽度
        $("#tip-div").css("left",  ($(window).width()/2 - $("#tip-div").width()/2 + 50) +"px");
        showMessage("tip-div",tipMessage,false,tipTime);
    }
}
function showTipWithTime(message, times) {
    if (message == null || message == ''){
        return;
    }
    if (message.length < 10){
        $("#tip-div").width(100)
    } else if (message.length < 20){
        $("#tip-div").width(200)
    } else if (message.length < 50){
        $("#tip-div").width(400)
    }else{
        $("#tip-div").width(600)
    }

    $("#tip-div").html(message);
    $("#tip-div").css("left",  ($(window).width()/2 - $("#tip-div").width()/2 + 50) +"px");
    showMessage("tip-div", message, false, times);
}
/** *********************页面提示信息显示方法************************* */
/**
 * 显示的div，提示信息，是否晃动，自动隐藏时间：-1为不隐藏，其它为隐藏时间（单位秒) message
 * 为false时表示不需要提示信息，仅需要显示div即可
 */
function showMessage(id,message,ishake,time){
    if(message!=""){
        if(message!="false"&&message!=false) {
            $("#" + id).html(message);
        }
        $("#"+id).fadeIn(300);
        if(ishake){
            shake(id);
        }
        if(time!=-1){
            if(isNaN(time)) {
                time = 2000;
            }else if(time>0) {
                time = time * 1000;
            }
            setTimeout(function(){
                if(time!=0){
                    $("#"+id).fadeOut(500);
                }
                else{
                    $("#"+id).fadeOut(300);
                }
                $("#"+id).hide("fast");
            },time);
        }
    }
}
// 晃动div
function shake(o){
    var $panel = $("#"+o);
    var box_left =0;
    $panel.css({'left': box_left});
    for(var i=1; 4>=i; i++){
        $panel.animate({left:box_left-(8-2*i)},50);
        $panel.animate({left:box_left+2*(8-2*i)},50);
    }
}
/*******************************************************************************
 * 根据点击位置设置div左边
 *
 * @param id
 * @param e
 *            为空时，局浏览器中部
 * @param lHeight
 * @param lWidth
 * @param onMouse
 *            div是否覆盖点击的点:(0).不覆盖，div居浏览器中部 (1).X轴居中 (2).Y轴居中 (3).X、Y轴均居中
 *            (4).右下方,(5).id左下方 6:居中，不需要考虑浏览器滚动 7：居中，高度不定，最大不超过浏览器80%
 */
function lookUp(id, e, lHeight, lWidth ,onMouse, positionId) {
    var lObj = self.document.getElementById(id);
    var lTop;
    var lLeft;
    //居中，高度不定，最大不超过浏览器80%
    if(onMouse==7){
        lLeft=$(window).width()/2 - (lWidth/2);
        lObj.style.top = '30px';
        lObj.style.width = lWidth + 'px';
        lObj.style.height = "auto";
        lObj.style.left = lLeft + 'px';
        return;
    }

    //如果传入了event
    if(e && e.clientY && onMouse&&onMouse!=0){
        lTop = e.clientY;
        lLeft = e.clientX;
        if(onMouse==1){
            lLeft = lLeft - (lWidth/2);
        }else if(onMouse==2){
            lTop = lTop - (lHeight/2);
        }
        else if(onMouse==3){
            lTop = lTop - (lHeight/2);
            lLeft = lLeft - (lWidth/2);
        }else if(onMouse==4){
            lTop = e.clientY;
            lLeft = e.clientX;
        }
    }else{
        lTop=$(window).height()/2 - (lHeight/2);
        lLeft=$(window).width()/2 - (lWidth/2);
    }
    if(onMouse==5){
        lTop = $("#"+positionId).offset().top+$("#"+positionId).outerHeight()-1;
        lLeft = $("#"+positionId).offset().left-1;
    }
    if (lLeft < 0) lLeft = 5;
    if ((lLeft + lWidth*1) > $(window).width()) lLeft = $(window).width() - lWidth - 20;
    if ((lTop + lHeight*1) > $(window).height()) lTop =  $(window).height() - lHeight - 70;

    lObj.style.width = lWidth + 'px';
    lObj.style.left = (lLeft + document.documentElement.scrollLeft) + 'px';

    lObj.style.height = lHeight + 'px';
    lObj.style.top =  lTop + 'px';
}

/**************************** 隐藏div *******************************/
function iClose(id){
    $("#"+id).fadeOut(300);
}
function iShow(id){
    $("#"+id).fadeIn(300);
}

/**
 * 全选、全不选
 *
 * @param id
 *            点击多选框
 * @param name
 *            需要全选的多选框名称
 */
function selectAll(id,name){
    if($("#"+id).prop("checked")==true)
        $("input[name='"+name+"']").prop("checked",true);
    else
        $("input[name='"+name+"']").prop("checked",false);
}

/*******************************************************************************
 *callAjax传递函数时直接写函数名即可，不需要加引号
 ******************************************************************************/
/**
 * @param params
 * @param iCallBack
 * @param iCallBackParam 原样返回至回调方法
 * @returns {string}
 */
function callAjaxNew(config, params, iCallBack, iCallBackParam) {
    var result = null;
    $.ajax({
        type : config.type,
        url : config.url,
        async : config.async,
        timeout: 3000,
        data : params,
        complete : function(data) {
            data = data.responseText;
            result = data;
            if (iCallBack) {
                if (iCallBackParam) {
                    iCallBack(data,iCallBackParam);
                } else {
                    iCallBack(data);
                }
            } else {
                defAjaxCallBack(config, data);
            }
        }
    });
}

/********** 请求配置 ********/
function getAjaxConfig(id, url, loadingText, loading) {
    return {async: true, url:url, type:'POST', timeout: 3000, loading:loading, loadingText: loadingText, id:id, tipTime:3,
        width:100, height:200, position: 3 };
}
function getFastAjaxConfig(url, id) {
    return {async: true, url:url, type:'POST', timeout: 3000, loading:'PROP_UP', loadingText: '努力加载中，请稍后...', id:id,
        tipTime:3, width:100, height:200, position: 3};
}

function showTipNew(config, e) {
    // 弹窗提示
    if(config.loading == 'PROP_UP'){
        $("#lookUpContent").html(config.loadingText);
        // 计算弹窗位置
        lookUp('lookUp', e, config.height, config.width, config.position, config.tag);
        showMessage('lookUp','false', false, -1);
    }

    // 浮层提示
    else if(config.loading == 'FLOAT'){
        showMessage('float','false', false, -1);
    }

    // 当前ID提示
    else if (config.loading == "ID") {
        if (document.getElementById(config.id).tagName != "INPUT"){
            $("#"+id).val(config.loadingText);
        } else{
            $("#"+id).html(config.loadingText);
        }
    }

    else if (config.loading == "TIP") {
        showTipWithTime(config.loadingText, -1)
    }

    // 不需要提示
    else if (config.loading == "FALSE") {}
}

function closeTipNew(config){
    var tipTime;
    if (config.success){
        config.loadingText = succ1 + config.loadingText +succ2;
        tipTime = 2;
    } else {
        config.loadingText = err1 + tipMessage + err2;
        tipTime = 5;
    }

    // 弹窗提示
    if(config.loading == 'PROP_UP'){
        $("#lookUpContent").html(config.loadingText);
        showMessage('lookUp', 'false', false, tipTime);
    }

    // 浮层提示
    else if(config.loading == 'FLOAT'){
        showMessage('float', 'false', false, 0);
    }

    // 当前ID，不需要关闭
    else if (config.loading == "ID") {}

    else if (config.loading == "TIP") {
        showTipWithTime(config.loadingText, tipTime)
    }

    // 不需要提示
    else if (config.loading == "FALSE") {}
}

/********* 回调方法 *********/
// 默认回调方法
function defAjaxCallBack(config, data) {
    if (config.loading == 'PROP_UP'){
        $("#lookUpContent").html(data);
        return;
    }

    try {
        var json = eval("(" + data + ")");
        config.success = (json.success == 1);
    } catch (e) {
        console.error(e);
        config.success = false;
    }
    closeTipNew(config);
}

// 修复ie8对trim不支持
String.prototype.trim = function () {
    return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

//回顶部
function goTop(){
    $("html, body").animate({ scrollTop: 0 }, 400);
}

//将指定id的控件滚动到浏览器顶部，如：接口详情页目录
function scrollToId(id) {
    $("html, body").animate({scrollTop: $("#" + id).offset().top}, 400);
}
function scrollToIdByParentId(parentId, id, adjustPix) {
    var mainContainer = $('#' + parentId);
    var scrollToContainer =  $('#' + id);
    mainContainer.animate({scrollTop: scrollToContainer.offset().top - mainContainer.offset().top + mainContainer.scrollTop() + adjustPix}, 200);
}
function tooltip(id){
    $("[data-toggle='tooltip']").tooltip();
    $("#"+id).tooltip('show');
}
// 获取url中的指定参数
function getParamFromUrl(url, name) {
    if (url.indexOf('?') <=0 ){
        return null;
    }
    url = url.substring(url.indexOf('?') + 1);
    var parameters = url.split('&');
    for (var i = 0; i < parameters.length; i++) {
        if(parameters[i].split('=').length == 2 && parameters[i].split('=')[0] == name){
            return parameters[i].split('=')[1];
        }
    }
    return null;
}
// 替换url中的指定参数
function replaceParamFromUrl(url, name, value) {
    var oldValue = getParamFromUrl(url, name);
    if (oldValue){
        url = replaceAll(url, name + "=" + oldValue, name + "=" + value);
    } else if (url.indexOf('?') <=0 ){
        url = url + "?" + name + "=" + value;
    } else {
        url = url + "&" + name + "=" + value;
    }
    return url;
}

/**
 * 替换字符串中自定的字符
 * @param originalStr 原字符串
 * @param oldStr 待替换的字符串
 * @param newStr 替换后的字符串
 */
function replaceAll(originalStr,oldStr,newStr){
    if (!newStr){
        newStr = '';
    }
    var regExp = new RegExp(oldStr,"gm");
    return originalStr.replace(regExp,newStr)
}

/***********启用bootstrap提示*********************/
$(function () {
    $('[data-toggle="tooltip"]').tooltip();
});
/************显示id1，隐藏id2*********************/
function changeDisplay(id1, id2, id3, id4) {
    $("#" + id2).addClass('none');
    $("#" + id1).removeClass('none');
    if(id3) $("#" + id3).addClass('none');
    if(id4) $("#" + id4).addClass('none');
};
/**********打开Dialog******************/
function openMyDialog(title,iwidth){
    if(!iwidth){
        iwidth = 400;
    }
    //对话框最高为浏览器的百分之80
    lookUp('myDialog', '', '', iwidth ,7,'');
    $("#myDialogContent").css("max-height",($(document).height()*0.8)+'px');
    showMessage('myDialog','false',false,-1);
    showMessage('fade','false',false,-1);
    title = title? title:"编辑";
    $("#myDialog-title").html(title);
}
function closeMyDialog(tagDiv){
    iClose(tagDiv);
    iClose('fade');
}


var dialogOldTop;
var dialogOldLeft;
var dialogOldHeight;
var dialogOldWidth;
var oldMaxHeight;
function fullMyDialog(tagDiv,tagDivContent){
    var target = $("#"+tagDiv);
    if( target.css('top') != '0px'){
        dialogOldTop = target.css('top');
        dialogOldLeft = target.css('left');
        dialogOldHeight = target.css('height');
        dialogOldWidth = target.css('width');
        $("#"+tagDiv).css("top","0px");
        $("#"+tagDiv).css("left","0px");
        $("#"+tagDiv).css("height","100%");
        $("#"+tagDiv).css("width","100%");
        $("#"+tagDiv).css("width","100%");
        if (tagDivContent) {
            oldMaxHeight = $("#" + tagDivContent).maxHeight;
            $("#" + tagDivContent).css("max-height", "100%");
        }
    }else{
        $("#"+tagDiv).css("top",dialogOldTop);
        $("#"+tagDiv).css("left",dialogOldLeft);
        $("#"+tagDiv).css("height",dialogOldHeight);
        $("#"+tagDiv).css("width",dialogOldWidth);
        if (tagDivContent) {
            $("#" + tagDivContent).css("max-height", oldMaxHeight);
        }
    }
}
function loadPick(event,iwidth,iheight,radio,tag,code,def,params,showType,iCallBack,iCallBackParam) {
    /***********加载选择对话框********************/
    if(!params)
        params='';
    if(showType!=0&&!showType)
        showType=5
    //事件，宽度，高度，是否为单选，html元素id，查询的code，查询的type，默认值，其他参数，回调函数，回调参数
    callAjaxByName("iUrl=pick.do|isHowMethod=updateDiv|iParams=&radio="+radio+"&code="+code+"&tag="+tag+"&def="+def+params,iCallBack,iCallBackParam);
    lookUp('lookUp', event, iheight, iwidth ,showType,tag);
    showMessage('lookUp','false',false,-1);
}

/**
 * size 单位为kb
 * @param event
 * @param id
 * @param size
 * @returns {Boolean}
 */
function uploadImage(id,size,form){
    if(!iLength(id,1,-1,"未选着图片，上传失败")){
        return false;
    }
    var fileSize =document.getElementById(id).files[0].size;
    if(size > 0 && fileSize>size*1024){
        alert("图片不能大于"+size+"kb,约"+(Math.round(size*1000/1024)/1000)+"M");
        return false;
    }
    lookUp('lookUp','',100,350,0);
    $("#lookUpContent").html("上传中，请稍后...");
    showMessage('lookUp', 'false', false, -1);
    form.submit();
}
//上传图片非编辑器默认回调方法
function uploadImgCallBack(msg, url, property) {
    if (msg.indexOf("[OK]") >= 0) {
        $("#image").attr("src", url + "");
        $("#image").removeClass("ndis");
        showMessage('lookUp', 'false', false, 0);
        if (url!= undefined) {
            //修改setting中的value
            var rootScope = getRootScope();
            rootScope.$apply(function () {
                rootScope.model[property] = url;
            });
        }
    }else {
        $("#lookUpContent").html(err1 + "&nbsp; " + url + "" + err2);
        showMessage('lookUp', 'false', false, 3);
    }
}

//文档管理上传文件回调方法
function uploadFileCallBack(msg, url) {
    if (msg.indexOf("[OK]") >= 0) {
        showMessage('lookUp', 'false', false, 0);
        if (url!= undefined) {
            //修改source中的filePath
            var rootScope = getRootScope();
            rootScope.$apply(function () {
                rootScope.model.filePath = url;
                // 获取文件原名
                if(!rootScope.model.name){
                    rootScope.model.name = $("#filePath").val().substring($("#filePath").val().lastIndexOf("\\")+1);
                }
            });
        }
    }else {
        $("#lookUpContent").html(err1 + "&nbsp; " + url + "" + err2);
        showMessage('lookUp', 'false', false, 3);
    }
}

/**
 * 图片id 验证码输入框id
 */
function changeimg(imgId,inputId){
    try{
        document.getElementById(imgId).src='getImgCode.do?'+Math.random();
        document.getElementById(inputId).focus();
    }catch(ex){}
    return false;
}
/*************************js调用anjularjs 获取$rootScope****************/
function getRootScope(){
    var $body = angular.element(document.body);
    return $body.scope().$root;
}
function getStateParams(){
    var $body = angular.element(document.body);
    return $body.scope().$stateParams;
}




function initDatePicker(id){
    if($('#'+id)){
        $('#'+id).datetimepicker({
            language:  'zh-CN',
            weekStart: 1,
            autoclose: 1,
            startView: 2,
            minView: 2,
            forceParse: 0,
            format: 'yyyy-mm-dd'
        });
    }
}
function initDatePicker2(id){
    if($('#'+id)){
        $('#'+id).datetimepicker({
            language:  'zh-CN',
            weekStart: 1,
            autoclose: 1,
            startView: 2,
            minView: 1,
            forceParse: 0,
            format: 'yyyy-mm-dd hh'
        });
    }
}

function addCookieToParams(key,value){
    var params = $.cookie('params');
    params= addParams(params, key , value);
    $.cookie('params', params);
}
function clearCookie(params){
    $.cookie(params, "");
}

function addCookie(key,value){
    $.cookie(key, value);
}
function getCookie(key){
    return $.cookie(key);
}





/** ************刷新验证吗*********** */
/*******************清空表单，除了nots(,#id)*****************/
function clearForm(id,nots){
    $(':input','#'+id)
        .not(':button, :submit, :reset'+nots)
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
}


/**
 * 单选
 */
/** ********************************** */
function selectRadio(className,id,radioId){
    var objs = $("."+className);
    objs.removeClass("active");
    var obj = $("#"+id);
    var cobj = $("#"+radioId);
    if(!obj.hasClass("active")){
        obj.addClass("active");
        $(cobj).prop("checked",true);
    }
}

/** ********************************** */
function selectButton(obj,className, activeCss){
    if (!activeCss){
        activeCss = "iactive";
    }
    window.editorId = new Date().getTime();
    var objs = $("."+className);
    objs.removeClass(activeCss);
    $(obj).addClass(activeCss);
}

function myConfirm(message){
    var begin = Date.now();
    var result = window.confirm(message);
    var end = Date.now();
    if (end - begin < 10) {
        $("#global-error").text("Please do not disable popups,it's dangerous!「请勿禁用【确认】弹窗，直接操作非常危险」");
        $("#global-error").removeClass("ndis");
        return true;
    }
    return result;
}
function sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)    return;
    }
}

var tipMessage = "        .----.\n" +
    "       _.'__    `.\n" +
    "   .--($$)($$)--/#\\\n" +
    " .' @          /###\\\n" +
    " :         ,   #####\n" +
    "  `-..__.-' _.-###/\n" +
    "        `:_:    `\"'\n" +
    "      .'\"\"\"\"\"'.\n" +
    "     //  CRAP \\\\\n" +
    "     //  API!  \\\\\n" +
    "    `-._______.-'\n" +
    "    ___`. | .'___\n" +
    "   (______|______)\n";
/**
 * 立即运行函数，用来检测控制台是否打开
 */
!function () {
    // 创建一个对象
    var foo = /./;
    var i = 0;
    // 将其打印到控制台上，实际上是一个指针
    console.info(foo);
    foo.toString =  function () {
        i++;
        var tip = '';
        for (var time=1; time < i; time++){
            tip = tip + '又';
        }
        if (i==1) {
            console.info('~ 想查看源代码？来这啊↩ ~');
            console.info('~ https://github.com/EhsanTang/ApiManager ~');
            console.info('~ https://gitee.com/CrapApi/CrapApi ~');
            console.info('~ 完全开源、免费，记得star、fork再走哦 ~');
            console.info('~ 视频介绍、安装部署...，请前往官网 http://api.crap.cn ~');
        }else{
            console.info('~ 你' + tip + '来了，star、fork了吗 ~');
        }
        return tipMessage;
    };
    // 要在第一次打印完之后再重写toString方法
}();

// 进入默认地址
(function () {
    var href = location.href;
    if (href.indexOf("#") <= 0 && href.indexOf("admin.do") > 0){
        location.href = href + URL_LIST[MY_PROJECT];
    }
})();

// 百度统计
var _hmt = _hmt || [];
(function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?500545bbc75c658703f93ac984e1d0e6";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();

/**
 *
 * @param id 需要验证的文本域id
 * @param min 最小长度，-1表示不限
 * @param max 最大长度，-1表示不限
 * @param message 不为空时验证失败会调用alert提示
 * @returns {Boolean}
 */
/***********************基础验证**********************/
function iLength(id,min,max,message){
    var length=$("#"+id).val().trim().length;
    if((min!=-1&&length<min)||(max!=-1&&length>max)){
        $("#"+id).focus();
        if(message!='')
            alert(message);
        return false;
    }
    return true;
}
function iCompare(id,min,max,message){
    if(!iDouble(id,"输入只能为数字"))
        return false;
    var value=$("#"+id).val();
    if((value<min)||(value>max)){
        $("#"+id).focus();
        if(message!='')
            alert(message);
        return false;
    }
    return true;
}
/**
 * 当传入的值为空时，返回true
 * @param id
 * @param message 不为空时验证失败会提调用alert提示
 * @returns {Boolean}
 */
function iShuzi(id,message){
    if($("#"+id).val().length==0)
        return true;
    if(isNaN($('#'+id).val())){
        $("#"+id).focus();
        if(message!='')
            alert(message);
        return false;
    }
    return true;
}
function iDouble(id,message){
    try{
        var number = $('#'+id).val();
        if(parseFloat(number)!=number){
            $("#"+id).focus();
            if(message!='')
                alert(message);
            return false;
        }else{
            $('#'+id).val(parseFloat(number));
            return true;
        }
    } catch (ex) {
        $("#"+id).focus();
        if(message!='')
            alert(message);
        return false;
    }
}
function iInt(id,message){
    try{
        var number = $('#'+id).val();
        if(parseInt(number)!=number){
            $("#"+id).focus();
            if(message!='')
                alert(message);
            return false;
        }else{
            $('#'+id).val(parseInt(number));
            return true;
        }
    } catch (ex) {
        $("#"+id).focus();
        if(message!='')
            alert(message);
        return false;
    }
}

function equals(str1,str2){
    if(str1==str2){
        return true;
    }else{
        return false;
    }
}
/*非空判断 */
this.isNull = function(str){
    if(str==""){
        return true;
    }
    var regu = /^[\s'　']*$/;
    var re = new RegExp(regu);
    return re.test(str);
};

function loginValidate(){
    if(!iLength("userName",2,20,"")){
        showMessage("warnMessage","用户名不能为空，长度为2-20",true,3);
        return false;
    }
    if(!iLength("userPassword",5,20,"")){
        showMessage("warnMessage","密码不能为空，长度为5-20",true,3);
        return false;
    }
    return true;
}


/****************密码访问*****************/
function propUpPasswordDiv(obj){
    var msg = obj.textContent;
    if(msg.indexOf(NEED_PASSWORD_CODE)>=0){
        var obj = document.getElementById('passwordDiv');
        if (obj){
            lookUp('passwordDiv', '', 300, 300 ,6,'');
            showMessage('passwordDiv','false',false,-1);
            showMessage('fade','false',false,-1);
            changeimg('imgCode','verificationCode');
            $("#password").val('');
            $("#password").focus();
        }
    }else if(msg.indexOf(NEED_LOGIN)>=0 ){
        openPage('loginOrRegister.do#/login');
    }
}

// 数据字典改为拖动
// function upward(nowTr){
// 	var $tr = $(nowTr).parent().parent();
//     if ($tr.index() != 0) {
//       $tr.fadeOut(1).fadeIn(600);
//       $tr.prev().fadeOut(1).fadeIn(1000);
//       $tr.prev().before($tr);
//     }
// }
// function downward(nowTr){
// 	var $tr = $(nowTr).parent().parent();
//       $tr.fadeOut(1).fadeIn(600);
//       $tr.next().fadeOut(1).fadeIn(1000);
//       $tr.next().after($tr);
// }
/****************End****************/

function unescapeAndDecode(name){
    var value = $.cookie(name);
    if(value){
        return unescape($.base64.decode(value));
    }
    return "";
}
function getParamFromTable(tableId, requiredName) {
    var json = "[";
    var i = 0;
    var j = 0;
    $('#' + tableId).find('tbody').find('tr').each(function() {
        // 查看必填项是否填写，没填写则忽略该行
        var ignore = false;
        $(this).find('td').find('input').each(function(i, val) {
            if (val.name == requiredName && val.value == ''){
                ignore = true;
            }
        });
        if (!ignore){
            i = i + 1;
            j = 0;
            if (i != 1) {
                json += ",";
            }
            json += "{";
            $(this).find('td').find('input').each(function(i, val) {
                j = j + 1;
                if (j != 1)
                    json += ",";
                json += "\"" + val.name + "\":\"" + replaceAll(val.value,'"','\\"') + "\""
            });
            $(this).find('td').find('select').each(function(i, val) {
                j = j + 1;
                if (j != 1)
                    json += ",";
                json += "\"" + val.name + "\":\"" + replaceAll(val.value,'"','\\"') + "\""
            });
            json += "}"
        }
    });
    json += "]";
    return json;
}
/************输入接口访问密码***********/
function setPassword(){
    $.cookie('password', $.base64.encode(escape($("#password").val())));
    $.cookie('visitCode', $.base64.encode(escape($("#visitCode").val())));
    if (getRootScope().error && getRootScope().error.indexOf(NEED_PASSWORD_CODE) > 0){
        getRootScope().error = "";
    }
}
/** ***************pick控件搜索：暂时废弃*************** */
var navigateText = "";
var deep = 0;
var select = 0;
var hasLoad = 0;
function keyMonitor() {
    hasLoad = 1;
    var lookUp = document.getElementById('lookUp');
    $(document).keydown(
        function(event) {
            try {
                if (event.keyCode == 8 && lookUp.style.display == 'block') {
                    if (navigateText.length >= 1) {
                        navigateText = navigateText.substring(0,
                            navigateText.length - 1)
                    }
                    if (navigateText.trim().length == 0) {
                        $("#pickTip").css("display", "none");
                    }
                    var tHandler = "pickScroll('" + navigateText + "')";
                    setTimeout(tHandler, 500);
                    return false;// return false表示该事件不再往下传递
                } else if (event.keyCode != 13) {
                    navigateText += String.fromCharCode(event.keyCode);
                    if (lookUp.style.display != 'block')
                        navigateText = "";
                    navigateText = navigateText.trim();
                    var tHandler = "pickScroll('" + navigateText + "')";
                    setTimeout(tHandler, 500);
                }
            } catch (e) {
                alert(e);
            }
        });
}
function pickScroll(oldNavigateText) {
    $("#pickTip").html(navigateText);
    if (navigateText.trim().length > 0) {
        $("#pickTip").css("display", "block");
    }
    if (oldNavigateText != navigateText) {
        return;
    }
    deep = oldNavigateText.length;
    var lookUp = document.getElementById('lookUp');
    if (lookUp.style.display == 'block') {
        select = 0;
        jQuery.each($("#pickContent").find("div"), function() {
            var span = jQuery(this).find("span");
            var checkBox = jQuery(this).find("input");
            if (select == 0) {
                checkText(this, oldNavigateText, span, checkBox, 1);
            }
        });
    }
}
function checkText(obj, oldNavigateText, span, checkBox, length) {
    if (span.text().substring(length - 1, length).toUpperCase() == oldNavigateText
        .substring(length - 1, length)) {
        if (length < deep) {
            checkText(obj, oldNavigateText, span, checkBox, length + 1);
        } else {
            var container = $('#lookUpContent'), scrollTo = span;
            container.scrollTop(scrollTo.offset().top - container.offset().top
                + container.scrollTop() - 100);
            $("#pickContent div").removeClass("pickSelect");
            $("#pickContent div").removeClass("main-color");
            $(obj).addClass("pickSelect");
            $(obj).addClass("main-color");
            select = 1;
        }
    }
}

// 重建索引
function rebuildIndex(obj){
    if (myConfirm("确定重建索引（安全，不会影响系统运行）？")) {
        selectButton(obj,'menu_a');
        callAjaxByName('iUrl=admin/rebuildIndex.do|iLoading=PROPUPFLOAT重建索引中，刷新页面可以查看实时进度...|ishowMethod=updateDivWithImg');
    }
}

function loginOut(){
    callAjaxByName("iUrl=user/loginOut.do|isHowMethod=updateDiv|iLoading=false|ishowMethod=doNothing|iAsync=false");
    location.reload();
}
//刷新缓存
function flushDB(obj){
    if (myConfirm("确定刷新缓存（安全，不会影响系统运行）？")) {
        selectButton(obj,'menu_a');
        callAjaxByName('iUrl=admin/flushDB.do|iLoading=TIPFLOAT刷新中，请稍后...|ishowMethod=updateDivWithImg');
    }
}

//删除30天前的日志
function cleanLog(obj){
    if (myConfirm("确定删除30天的日志（文档、接口等日志，删除后无法恢复）？")) {
        selectButton(obj,'menu_a');
        callAjaxByName('iUrl=admin/cleanLog.do|iLoading=TIPFLOAT删除中，请稍后...|ishowMethod=updateDivWithImg');
    }
}

//压缩css
function compress(obj){
    selectButton(obj,'menu_a');
    callAjaxByName('iUrl=admin/compress.do|iLoading=TIPFLOAT压缩中，请稍后...|ishowMethod=updateDivWithImg');
}

function format(txt, tiperror){
    try {
        var txtObj = JSON.parse(txt);
        return JSON.stringify(txtObj, null, 5);
    }catch (e){
        if (tiperror){
            alert("格式化异常，请检查json格式是否有误" + e);
        }
        return txt;
    }
}

/**
 * 待删除，已经被替换
 * @param txt
 * @param tiperror
 * @param compress
 * @returns {string}
 */
function format_delete(txt, tiperror, compress/*是否为压缩模式*/) {/* 格式化JSON源码(对象转换为JSON文本) */
    var indentChar = '    ';
    if (/^\s*$/.test(txt)) {
        if (tiperror)
            alert('数据为空,无法格式化! ');
        return;
    }
    // 替换\r\n 换行
    txt=txt.replace(/\\r/g,"CRAPAPI_R");
    txt=txt.replace(/\\n/g,"CRAPAPI_N");
    txt=txt.replace(/\\t/g,"CRAPAPI_T");
    var data;
    try {
        data=$.parseJSON(txt);
    } catch (e) {
        if (tiperror)
            alert('数据源语法错误,格式化失败! 错误信息: ' + e.description, 'err');
        return;
    }
    ;
    var draw = [], last = false, This = this, line = compress ? '' : '\n', nodeCount = 0, maxDepth = 0;

    var notify = function(name, value, isLast, indent/*缩进*/, formObj) {
        nodeCount++;/*节点计数*/
        for (var i = 0, tab = ''; i < indent; i++)
            tab += indentChar;/* 缩进HTML */
        tab = compress ? '' : tab;/*压缩模式忽略缩进*/
        maxDepth = ++indent;/*缩进递增并记录*/
        if (value && value.constructor == Array) {/*处理数组*/
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '[' + line);/*缩进'[' 然后换行*/
            for (var i = 0; i < value.length; i++)
                notify(i, value[i], i == value.length - 1, indent, false);
            draw.push(tab + ']' + (isLast ? line : (',' + line)));/*缩进']'换行,若非尾元素则添加逗号*/
        } else if (value && typeof value == 'object') {/*处理对象*/
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line);/*缩进'{' 然后换行*/
            var len = 0, i = 0;
            for ( var key in value)
                len++;
            for ( var key in value)
                notify(key, value[key], ++i == len, indent, true);
            draw.push(tab + '}' + (isLast ? line : (',' + line)));/*缩进'}'换行,若非尾元素则添加逗号*/
        } else {
            if (typeof value == 'string') {
                value = value.replace(/\"/gm, '\\"');
                // 替换\r\n 换行
                value=value.replace(/CRAPAPI_R/g,"\\r");
                value=value.replace(/CRAPAPI_N/g,"\\n");
                value=value.replace(/CRAPAPI_T/g,"\\t");

                value = '"' + value + '"';
            }
            draw.push(tab + (formObj ? ('"' + name + '":') : '') + value
                + (isLast ? '' : ',') + line);
        }
        ;
    };
    var isLast = true, indent = 0;
    notify('', data, isLast, indent, false);
    return draw.join('');
}

function jsonToDiv(txt) {/* 格式化JSON源码(对象转换为JSON文本) */
    var indentChar = '-';
    if (/^\s*$/.test(txt)) {
        alert('数据为空,无法格式化! ');
        return "";
    }
    try {
        var data = eval('(' + txt + ')');
    } catch (e) {
        alert('数据源语法错误,格式化失败! 错误信息: ' + e.description, 'err');
        return "";
    }

    var draw = [], line = ',', nodeCount = 0;
    var paramSeparator = "->";
    var notify = function(parentName, name, value, indent) {
        nodeCount++;/*节点计数*/

        for (var i = 0, tab = ''; i < indent; i++) {
            tab += indentChar;
            /* 缩进HTML */
        }
        indent ++;
        if (value && value.constructor == Array) {/*处理数组*/
            if( value.length > 0 ){
                // 判断数组类型
                var type = "";
                if( value[0] && typeof value[0] == 'object'){
                    type = 'object';
                }else if (typeof value == 'string') {
                    type= 'string';
                }else if (typeof value == 'number') {
                    type = 'number';
                }else if (typeof value == 'boolean') {
                    type = 'boolean';
                }

                if( name != ''){
                    if (parentName && parentName != ''){
                        parentName = parentName + paramSeparator + name;
                    } else {
                        parentName = name;
                    }
                    draw.push('{"deep":"'+tab.length+'","name":"'+ parentName +'","remark":"","type":"array['+type+']","necessary":"true"}' + line);
                }else{
                    indent = indent -1;
                }

                // 数组只需要记录第一个就行
                notify(parentName, "", value[0], indent);
            }

        } else if (value && typeof value == 'object') {/*处理对象*/
            // 数组中的元素，没有名称
            if( name != ''){
                if (parentName && parentName != ''){
                    parentName = parentName + paramSeparator + name;
                } else {
                    parentName = name;
                }
                // 将对象名放入队列
                draw.push('{"deep":"'+tab.length+'","name":"'+ parentName +'","remark":"","type":"object","necessary":"true"}' + line);
            }else{
                indent = indent -1;// 名称为空，无需缩进
            }

            for ( var key in value) {
                notify(parentName, key, value[key], indent);
            }
        } else {
            var type;
            if (typeof value == 'string') {
                value = value.replace(/\"/gm, '\\"');
                value = '"' + value + '"';
                type = 'string';
            }else if (typeof value == 'number') {
                type = 'number';
            }else if (typeof value == 'boolean') {
                type = 'boolean';
            }

            if(name != ""){// 数组中的字符等没有名称，基础数组不需要记录
                if (parentName && parentName != ''){
                    parentName = parentName + paramSeparator + name;
                } else {
                    parentName = name;
                }
                draw.push('{"deep":"'+tab.length+'","name":"'+parentName+'","remark":"","type":"'+type+'","necessary":"true"}' + line);
            }
        }
    };

    var indent = 0;
    notify('', '', data, indent);
    var result = draw.join('');
    if(result.length > 0){
        result = result.substring(0,result.length-1);
    }
    return "["+result+"]";
}

/**
 * 编辑器：富文本wang编辑器，markdown editorMe编辑器
 * 数据字典，接口参数编辑方法
 */
    // 标识是否初始化过，display = none时，初始化样式有问题
var markdownEditor;
function createEditorMe(id, markdownContent) {
    if (markdownEditor != null){
        return;
    }
    markdownEditor = editormd(id, {
        path: "./resources/framework/editormd-1.5.0/lib/", // Autoload modules mode, codemirror, marked... dependents libs path
        width: "100%",
        height: 500,
        theme: "default", // "default | dark"
        previewTheme: "default",
        // editorTheme : "pastel-on-dark",
        codeFold: true,
        toolbarIcons: function () {
            // return editormd.toolbarModes["mini"]; // full, simple, mini
            // Or return editormd.toolbarModes[name]; // full, simple, mini
            // Using "||" set icons align right.
            // "emoji", "html-entities" json数据被拦截
            return ["undo", "redo", "|", "bold", "del", "italic", "quote", "|"
                , "h1", "h2", "h3", "h4", "h5", "h6", "|",
                "list-ul", "list-ol", "hr", "|",
                "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "pagebreak", "|",
                "goto-line", "watch", "preview", "fullscreen", "clear", "search", "|", "help"];
        },
        //syncScrolling : false,
        saveHTMLToTextarea: true,    // 保存 HTML 到 Textarea
        searchReplace: true,
        //watch : false,                // 关闭实时预览
        htmlDecode: "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        //toolbar  : false,             //关闭工具栏
        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
        emoji: true,
        taskList: true,
        tocm: true,         // Using [TOCM]
        tex: true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart: true,             // 开启流程图支持，默认关闭
        sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
        //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: "user/markdown/upload.do",
        onload: function () {
            console.log('onload', this);
            //this.fullscreen();
            this.unwatch();
            //this.watch().fullscreen();

            this.setMarkdown(markdownContent);
            //this.width("100%");
            //this.height(480);
            //this.resize("100%", 640);
        }, onfullscreen: function () {
            $("#"+id).css("z-index", 101);
        }, onfullscreenExit: function () {
            $("#"+id).css("z-index", 99);
        }
    });
}

/**
 * 初始化富文本编辑器
 * @param id
 * @param editorHtml
 * @param init
 * @param height
 */
function createWangEditor(id, editorHtml, init, height) {
    var E = window.wangEditor;
    var editor = new E(document.getElementById(id));
    init(editor);
    editor.create();
    if (height) {
        $(".w-e-text-container").css("height", height);
    }
    $(".w-e-text-container").css("z-index", 98);
    $(".w-e-menu").css("z-index", 99);
    if (!editorHtml){
        editorHtml = "";
    }
    editor.txt.html(editorHtml);
    return editor;
}

function initArticleEditor(editor) {
    var root = getRootScope();
    editor.customConfig.uploadImgMaxLength = 1;
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024; // 3M
    editor.customConfig.uploadImgServer = 'user/file/upload.do';
    editor.customConfig.uploadFileName = 'img';
    editor.customConfig.zIndex = 999;
    editor.customConfig.uploadImgHooks = {
        fail: function (xhr, editor, result) {
            $("#lookUpContent").html(err1 + "&nbsp; " + result.errorMessage + "" + err2);
            showMessage('lookUp', 'false', false, 3);
        }
    }
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        root.model.content = html;
    }
}

function initInterfaceEditor(editor) {
    var root = getRootScope();
    // 配置菜单
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'justify',  // 对齐方式
        'undo',  // 撤销
        'redo'  // 重复
    ];
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        root.model.remark = html;
    }
}

/********************** end:markdown 富文本 **********************/
function initDragTable(id) {
    $("#" + id + " tbody").sortable({
        cursor: "move",
        // revert: true,                      //释放时，增加动画
        // revertDuration: 200, // 还原（revert）动画的持续时间，以毫秒计。如果 revert 选项是 false 则忽略。
        containment: "parent", // 约束拖拽范围的边界，不能超过父对象
        delay: 100, //鼠标按下后直到拖拽开始为止的时间，以毫秒计。该选项可以防止点击在某个元素上时不必要的拖拽。
        distance: 0, // 鼠标按下后拖拽开始前必须移动的距离，以像素计。该选项可以防止点击在某个元素上时不必要的拖拽
        cancel: "button", // 指令的空间不支持拖拽，可以是class、id等
        axis: "y", // 只能在y轴拖拽
        handle: "span", // 只有span才支持拖拽
        items: ".drag",                       //只是tr可以拖动
        opacity: 1.0,                      //拖动时，透明度为0.6
        update: function(event, ui) {      //更新排序之后
            // var tr = ui.item; //当前拖动的元素
            // var index = tr.attr("index"); //当前元素的顺序
            // var header = $rootScope.headerList.splice(index, 1);
            // // 新的序号计算
            // var newIndex = getNewArray('editHeaderTable');
            // $rootScope.headerList.splice(newIndex - 1, 0, header[0]);
            // $rootScope.$apply();
            //$rootScope.headerList = getNewArray('editHeaderTable');
            //$rootScope.$apply();
        }
    });
    $("#" + id + " tbody").sortable({
        connectToSortable : "#body",  //目标区域列表div的dom
        helper: fixHelperModified,
        stop: updateIndex
    }).disableSelection()
}
/**
 * 表格拖动
 * @param e
 * @param tr
 */
var fixHelperModified = function(e, tr) {
    var $originals = tr.children();
    var $helper = tr.clone();
    $helper.children().each(function(index) {
        $(this).width($originals.eq(index).width() + 6)
    });
    $helper.width(tr.width() + 1);
    $helper.css("margin-left", "-1px");
    return $helper;
}
var updateIndex = function(e, ui) {};
/********************** end:表格拖动 ********************/

/**********************接口、表格公用编辑方法 ************/
/**
 * 删除表格中的一行
 * @param nowTr
 */
function deleteOneTr(nowTr) {
    $(nowTr).parent().parent().parent().remove();
}

/**
 * 判断是最后一个tr
 * @param target
 * @param model
 * @returns {boolean}
 */
function isLast(target, model) {
    if (target){
        if ( $(target).val() == ''){
            return false;
        }
        var tr = $(target).parent().parent();
        var totalTrNum = tr.parent().children().length;
        if( tr.index() + 1 != totalTrNum){
            return false;
        }
    }
    return true;
}

/********************** end: 接口、表格公用编辑方法 ************/
var deleteButton = "<button class='cursor btn btn-xs btn-default' type='button'><i class='iconfont text-danger' onclick='deleteOneTr(this)'>&#xe69d;</i> </button>";
var moverSpan = "<span class='cursor btn btn-xs btn-default' style='cursor: move;'><i class='iconfont'>&#xe6fd;</i></span>";
var insertButton = "<button class='cursor btn btn-xs btn-default C999' type='button' onclick='ADD_ONE_TR(this, null, true)'>插入</button>";

var interNameHtml = "<td><input class='form-control C555 fw500' type='text' name='name' value='INTER_NAME' " +
    "placeholder='参数名必填，多级参数请使用\"->\"分割，如：f1->f2' autocomplete='off' " +
    "onkeyup='ADD_ONE_TR(this)'></td>";
var remarkHtml = "<td><input class='form-control C000' type='text' name='remark' autocomplete='off' value='REMARK' onkeyup='ADD_ONE_TR(this)'></td> ";
var defHtml = "<td> <input class='form-control C000' type= 'text' name='def' autocomplete='off' onkeyup='ADD_ONE_TR(this)' value='DEF' placeholder='默认值'></td>";
var interOperateHtml = "<td class='tc BGFFF'>" + moverSpan + "&nbsp;" + deleteButton + "</td>";
var interResOperateHtml = "<td class='tc BGFFF'>" + insertButton + "&nbsp;" + moverSpan + "&nbsp;" + deleteButton + "</td>";
var interNecessaryHtml = "<td> <select name='necessary' class='form-control'>"
    + "<option value='true' true_select>是</option>"
    + "<option value='false' false_select>否</option>"
    + "</select></td>";
var interTypeHtml = "<td> <select name='type' class='form-control'>"
    + "<option value='string' string_select>string</option>"
    + "<option value='int' int_select>int</option>"
    + "<option value='float' float_select>float</option>"
    + "<option value='long' long_select>long</option>"
    + "<option value='byte' byte_select>byte</option>"
    + "<option value='double' double_select>double</option>"
    + "<option value='number' number_select>number</option>"
    + "<option value='boolean' boolean_select>boolean</option>"
    + "<option value='object' object_select>object</option>"
    + "<option value='array' array_select>array</option>"
    + "<option value='array[int]' array[int]_select>array[int]</option>"
    + "<option value='array[float]' array[float]_select>array[float]</option>"
    + "<option value='array[long]' array[long]_select>array[long]</option>"
    + "<option value='array[byte]' array[byte]_select>array[byte]</option>"
    + "<option value='array[double]' array[double]_select>array[double]</option>"
    + "<option value='array[number]' array[number]_select>array[number]</option>"
    + "<option value='array[boolean]' array[boolean]_select>array[boolean]</option>"
    + "<option value='array[string]' array[string]_select>array[string]</option>"
    + "<option value='array[object]' array[object]_select>array[object]</option>"
    + "<option value='file' file_select>file</option>"
    + "</select> </td>";
/*
 * 数据字典方法
 */
/**************** 数据库表 ****************/
var dictNameHtml = "<td><input class='form-control' autocomplete='off' type='text' name='name' value='DICT_NAME' placeholder='必填，不填将被过滤' onkeyup='addOneDictionaryTr(this)'></td>";
var dictTypeHtml = "<td><input class='form-control' type='text' name='type' value='DICT_TYPE' placeholder='类型'></td>";
var dictNotNullHtml = "<td><select name='notNull' class='form-control'>" +
    "<option value='true' true_select>true</option><option value='false' false_select>false</option>" +
    "</select></td>";
var dictFlagHtml = "<td><select name='flag' class='form-control'>" +
    "<option value='common' common_select>普通</option><option value='primary' primary_select>主键</option>" +
    "<option value='foreign' foreign_select>外键</option><option value='associate' associate_select>关联</option>" +
    "</select></td>";
/**
 * 请求参数
 */
var interParamPositionHtml = "<td> <select name='inUrl' class='form-control'>"
    + "<option value='false' false_select>普通参数</option>"
    + "<option value='true' true_select>URL路径</option>"
    + "</select></td>";

function addOneDictionaryTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#content").append("<tr class='drag'>"
        + replaceAll(dictNameHtml, 'DICT_NAME', model.name)
        + replaceAll(dictTypeHtml, 'DICT_TYPE', model.type)
        + replaceAll(dictNotNullHtml, model.notNull+'_select', ' selected ')
        + replaceAll(replaceAll(defHtml, 'DEF', model.def), "ADD_ONE_TR", "addOneDictionaryTr")
        + replaceAll(dictFlagHtml, model.flag + '_select', ' selected ')
        + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneDictionaryTr")
        + interOperateHtml
        +"</tr>");
}

/************** 接口 ******************/
function addOneInterHeadTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#editHeaderTable").append("<tr class='drag'>"
        + replaceAll(replaceAll(interNameHtml, 'INTER_NAME', model.name), "ADD_ONE_TR", "addOneInterHeadTr")
        + replaceAll(interNecessaryHtml, model.necessary+'_select', ' selected ')
        + replaceAll(interTypeHtml, model.type+'_select', ' selected ')
        + replaceAll(replaceAll(defHtml, 'DEF', model.def), "ADD_ONE_TR", "addOneInterHeadTr")
        + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneInterHeadTr")
        + interOperateHtml
        +"</tr>");
}

var debugValueHtml = "<td> <input class='form-control C000 fw500' type= 'text' name='def' autocomplete='off' onkeyup=\"ADD_ONE_TR('TABLE_ID',this)\" value='DEBUG_VALUE' placeholder='值'></td>";
var debugNameHtml = "<td><input class='form-control C000 fw500' type='text' name='name' value='DEBUG_NAME' placeholder='参数名' autocomplete='off' onkeyup=\"ADD_ONE_TR('TABLE_ID',this)\"></td>";

function addOneDebugTr(id, target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#" + id).append("<tr class='drag'>"
        + replaceAll(replaceAll(replaceAll(debugNameHtml, 'DEBUG_NAME', model.realName), "ADD_ONE_TR", "addOneDebugTr"), 'TABLE_ID', id)
        + replaceAll(replaceAll(replaceAll(debugValueHtml, 'DEBUG_VALUE', model.def), "ADD_ONE_TR", "addOneDebugTr"), 'TABLE_ID', id)
        + interOperateHtml
        +"</tr>");
}

function addOneInterParamTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#editParamTable").append("<tr class='drag'>"
        + replaceAll(replaceAll(interNameHtml, 'INTER_NAME', model.name), "ADD_ONE_TR", "addOneInterParamTr")
        + replaceAll(interNecessaryHtml, model.necessary+'_select', ' selected ')
        + replaceAll(interParamPositionHtml, model.inUrl+'_select', ' selected ')
        + replaceAll(interTypeHtml, model.type+'_select', ' selected ')
        + replaceAll(replaceAll(defHtml, 'DEF', model.def), "ADD_ONE_TR", "addOneInterParamTr")
        + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneInterParamTr")
        + interOperateHtml
        +"</tr>");
}

// 返回字段
function addOneInterRespTr(target, model, isInsert) {
    // 自动添加下一行:最后一行，且不是插入
    if (!isLast(target, model) && (!isInsert)){
        return;
    }
    if (!model){
        model = new Object();
    }

    var trContent = "<tr class='drag'>"
        + replaceAll(replaceAll(interNameHtml, 'INTER_NAME', model.name), "ADD_ONE_TR", "addOneInterRespTr")
        + replaceAll(interNecessaryHtml, model.necessary+'_select', ' selected ')
        + replaceAll(interTypeHtml, model.type+'_select', ' selected ')
        + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneInterRespTr")
        + replaceAll(interResOperateHtml, "ADD_ONE_TR", "addOneInterRespTr")
        +"</tr>";
    if (!isInsert){
        $("#editResponseParamTable").append(trContent);
        if (target){
            // 自动追加一行，不需要选中
            return;
        }
        // 选中最后一行
        $("#editResponseParamTable").find('tr').last().find('td').find('input').first().focus();
        return;
    }

    // 插入子参数
    var $tr = $(target).parent().parent();
    $tr.after(trContent);
    var resParamName = $tr.find('td').find('input').first().val();
    if (resParamName && resParamName.length > 0){
        resParamName = resParamName + "->";
    }
    // 选中新增加的行
    $tr.next().find('td').find('input').first().val('').focus().val(resParamName);
}

// 根据json，导入至参数
function importJson(type){
    var jsonText = jsonToDiv($("#importResponseParam").val());
    if(jsonText.length > 0){
        if(type == 'responseParam'){
            $.each( eval("("+jsonText+")"), function (n, value) {
                addOneInterRespTr(null, value);
            });
        } else if(type == 'header'){
            $.each( eval("("+jsonText+")"), function (n, value) {
                addOneInterHeadTr(null, value);
            });
        }else if(type == 'param'){
            $.each( eval("("+jsonText+")"), function (n, value) {
                addOneInterParamTr(null, value);
            });
        }
    }
    closeMyDialog('myDialog');
}

// 接口头信息

/**if (!rowNum || rowNum == '') {
        var mydate = new Date();
        rowNum = mydate.getTime();
}**/

