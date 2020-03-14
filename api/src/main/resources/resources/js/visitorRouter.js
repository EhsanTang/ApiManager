
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