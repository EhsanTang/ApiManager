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

