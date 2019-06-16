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