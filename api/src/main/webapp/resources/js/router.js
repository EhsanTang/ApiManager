
/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/webInterfaceInfo/list/0/接口列表');
	$stateProvider.state('menuInfoList', {
		url : '/menuInfo/list/:parentId/:menuName',
		views : {
			'main' : {
				templateUrl : 'resources/html/menuInfoList.tpl.html'
			},
			'page@menuInfoList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/menuInfoDetail.tpl.html';
				}
			}
		}
	}).state('interfaceInfoList', {
		url : '/interfaceInfo/list/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/html/interfaceInfoList.tpl.html'
			},
			'page@interfaceInfoList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/interfaceInfoDetail.tpl.html';
				}
			}
		}
	}).state('userInfoList', {
		url : '/userInfo/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/userInfoList.tpl.html'
			},
			'page@userInfoList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/userInfoDetail.tpl.html';
				}
			}
		}
	}).state('roleInfoList', {
		url : '/roleInfo/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/roleInfoList.tpl.html'
			},
			'page@roleInfoList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/roleInfoDetail.tpl.html';
				}
			}
		}
	}).state('errorInfoList', {
		url : '/errorInfo/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/errorInfoList.tpl.html'
			},
			'page@errorInfoList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/errorInfoDetail.tpl.html';
				}
			}
		}
	}).state('webError', {
		url : '/webError/list/:moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/webHtml/errorInfoList.tpl.html'
			},
			'page@webError' : {
				templateUrl : 'resources/webHtml/page.tpl.html'
			}
		}
	}).state('webInterfaceInfoCtrl', {
		url : '/webInterfaceInfo/list/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/webHtml/interfaceInfoList.tpl.html'
			},
			'page@webInterfaceInfoCtrl' : {
				templateUrl : 'resources/webHtml/page.tpl.html'
			}
		}
	}).state('webInterfaceDetailCtrl', {
		url : '/webInterfaceDetail/:moduleId/:moduleName/:id',
		views : {
			'main' : {
				templateUrl : 'resources/webHtml/interfaceInfoDetail.tpl.html'
			}
		}
	})
});