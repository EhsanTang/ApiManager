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
