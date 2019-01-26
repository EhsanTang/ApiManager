/**
 * bug管理系统 controller
 */
var bugModule = angular.module("bugModule", []);
var VO_NAME = 'bugVO';
var VO_LIST_NAME = 'bugVOList';
// bug列表
userModule.controller('bugCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
    // 公用分页方法
    $scope.pageMethod = function(callBackMethod, page, updateUrl) {
        $scope[callBackMethod](page, updateUrl);
    };
    /**
     * 列表
     * @param page
     * @param updateUrl
     */
    $scope.queryBugList = function (page, updateUrl) {
        var params = "iUrl=user/bug/list.do|iLoading=FLOAT|iPost=POST|iParams="
            + "&moduleId=" + $stateParams.moduleId
            + "&projectId=" + $stateParams.projectId;
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
        root[VO_NAME].content = html;
    }
}