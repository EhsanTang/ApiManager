/**
 * bug管理系统 controller
 */
var bugModule = angular.module("bugModule", []);

// bug列表
userModule.controller('bugCtrl', function($rootScope,$scope, $http, $state,$location,$stateParams,httpService) {
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
        $rootScope.getBaseDataToDataKey($scope, $http, params, page, "bugs");
    };

    $scope.getBugDetail = function (isEdit) {
        var params = "iUrl=user/bug/detail.do|iLoading=FLOAT|iPost=POST|iParams=&id=" + $stateParams.id +
            "&moduleId=" + $stateParams.moduleId + "&projectId=" + $stateParams.projectId;
        $rootScope.getBaseDataToDataKey($scope,$http,params,null,'bugVO', function () {
            // 存在 article-editor 则初始化
            if (!isEdit) {
                return;
            }
            $rootScope.bugVO.oldName = $rootScope.bugVO.name;
            createWangEditor("bug-editor", $rootScope.bugVO.content, initBugEditor, "300px");
        });
    };

    // 更新状态changeBug
    $scope.updateBugName = function() {
        var name = $rootScope.bugVO.name;
        if ($rootScope.bugVO.oldName != name){
            var params = "iUrl=user/bug/changeBug.do|iLoading=FLOAT|iPost=POST|iParams=&type=name&value=" +name +
            "&id=" +$rootScope.bugVO.id ;
            $rootScope.getBaseDataToDataKey($scope,$http,params,null,'bugVO', function () {
                $rootScope.bugVO.oldName = name;
            });
        }
    }
});


function loadBugPick($this, $event, iwidth, iheight,type) {
    /***********加载选择对话框********************/
    var obj = $($this);
    var tag = obj.attr('id');
    $("#pickContent").html(loadText);
    callAjaxByName("iUrl=user/bug/pick.do|isHowMethod=updateDiv|iPost=POST|iParams=&type="+type+"&tag="+ tag +
        "&def=" + obj.attr("crap-def"));
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
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'justify',  // 对齐方式
        'undo',  // 撤销
        'redo'  // 重复
    ];
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        root.model.content = html;
    }
}