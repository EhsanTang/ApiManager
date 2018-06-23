
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
                templateUrl : 'resources/html/visitor/notFound.tpl.html?v=v8.0.2'
            }
        }
    }).state('frontSearchCtrl', {
		url : '/frontSearch/:keyword',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/visitor/searchResult.tpl.html?v=v8.0.2';
				}
			},
			'page@frontSearchCtrl' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.2'
			}
		}
	}).state('frontError', {
		url : '/:projectId/error/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/errorList.tpl.html?v=v8.0.2'
			},
			'page@frontError' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.2'
			}
		}
	}).state('frontModuleCtrl', {
		url : '/:projectId/module/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/moduleList.tpl.html?v=v8.0.2'
            },
            'page@frontModuleCtrl' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.2'
            }
		}
	}).state('frontInterfaceCtrl', {
		url : '/:projectId/interface/list/:moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/interfaceList.tpl.html?v=v8.0.2'
			},
			'page@frontInterfaceCtrl' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.2'
			}
		}
	}).state('frontInterfaceDetailCtrl', {
		url : '/:projectId/front/interfaceDetail/:id',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/interfaceDetail.tpl.html?v=v8.0.2'
			}
		}
	}).state('frontInterfaceDebugCtrl', {
		url : '/:projectId/front/interface/debug/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/visitor/interfaceDebug.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('frontSourceList', {
		url : '/:projectId/source/list/:moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/sourceList.tpl.html?v=v8.0.2'
			},
			'page@frontSourceList' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.2'
			}
		}
	}).state('frontProjectList', {
		url : '/project/list/:myself/NULL',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/projectList.tpl.html?v=v8.0.2'
			},
			'page@frontProjectList' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.2'
			}
		}
	})
	/*********************前端项目主页*******************/
});