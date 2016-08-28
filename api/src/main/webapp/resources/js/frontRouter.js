
/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
app.config(function($stateProvider, $urlRouterProvider) {
	if($("#projectId").length>0){// 项目主页
		$urlRouterProvider.otherwise('/project');
	}else{
		$urlRouterProvider.otherwise('/top/webPage/detail/PAGE/WELCOME');
	}
	
	$stateProvider.state('frontSearchCtrl', {
		url : '/frontSearch/:keyword',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/searchResult.tpl.html';
				}
			},
			'page@frontSearchCtrl' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('webError', {
		url : '/:moduleId/error/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/errorList.tpl.html'
			},
			'page@webError' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('frontInterfaceCtrl', {
		url : '/:projectId/interface/list/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/interfaceList.tpl.html'
			},
			'page@frontInterfaceCtrl' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('frontInterfaceDetailCtrl', {
		url : '/:projectId/front/interfaceDetail/:id',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/interfaceDetail.tpl.html'
			}
		}
	}).state('frontInterfaceDebugCtrl', {
		url : '/:projectId/front/interface/debug/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/interfaceDebug.tpl.html';
				}
			}
		}
	}).state('frontSourceList', {
		url : '/front/source/list/:directoryId/:directoryName',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/sourceList.tpl.html'
			},
			'page@frontSourceList' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	})
	/*********************前端项目主页*******************/
});