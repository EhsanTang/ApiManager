
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
                templateUrl : 'resources/html/visitor/notFound.tpl.html?v=v8.0.5'
            }
        }
    }).state('visitorSearchCtrl', {
		url : '/visitorSearch?keyword',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/visitor/searchResult.tpl.html?v=v8.0.5';
				}
			},
			'page@visitorSearchCtrl' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
			}
		}
	}).state('visitorError', {
		url : '/error/list?projectId',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/errorList.tpl.html?v=v8.0.5'
			},
			'page@visitorError' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
			}
		}
	}).state('visitorModuleCtrl', {
		url : '/module/list?projectId',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/moduleList.tpl.html?v=v8.0.5'
            },
            'page@visitorModuleCtrl' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
            }
		}
	}).state('visitorInterfaceCtrl', {
		url : '/interface/list?projectId&moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/interfaceList.tpl.html?v=v8.0.5'
			},
			'page@visitorInterfaceCtrl' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
			}
		}
	}).state('visitorInterfaceDetailCtrl', {
		url : '/interface/detail?projectId&id',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/interfaceDetail.tpl.html?v=v8.0.5'
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
				templateUrl : 'resources/html/visitor/sourceList.tpl.html?v=v8.0.5'
			},
			'page@visitorSourceList' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
			}
		}
	}).state('visitorProjectList', {
		url : '/project/list?projectShowType&type',
		views : {
			'main' : {
				templateUrl : 'resources/html/visitor/projectList.tpl.html?v=v8.0.5'
			},
			'page@visitorProjectList' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
			}
		}
	})
	/*********************前端项目主页*******************/
});