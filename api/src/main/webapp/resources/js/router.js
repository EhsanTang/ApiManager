
/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/webInterface/list/0/接口列表');
	$stateProvider.state('menuList', {
		url : '/menu/list/:parentId/:menuName',
		views : {
			'main' : {
				templateUrl : 'resources/html/menuList.tpl.html'
			},
			'page@menuList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/menuDetail.tpl.html';
				}
			}
		}
	}).state('interfaceList', {
		url : '/interface/list/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/html/interfaceList.tpl.html'
			},
			'page@interfaceList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/interfaceDetail.tpl.html';
				}
			}
		}
	}).state('settingList', {
		url : '/setting/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/settingList.tpl.html'
			},
			'page@settingList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/settingDetail.tpl.html';
				}
			}
		}
	}).state('userList', {
		url : '/user/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/userList.tpl.html'
			},
			'page@userList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/userDetail.tpl.html';
				}
			}
		}
	}).state('roleList', {
		url : '/role/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/roleList.tpl.html'
			},
			'page@roleList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/roleDetail.tpl.html';
				}
			}
		}
	}).state('errorList', {
		url : '/error/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/errorList.tpl.html'
			},
			'page@errorList' : {
				templateUrl : 'resources/html/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/errorDetail.tpl.html';
				}
			}
		}
	}).state('webError', {
		url : '/webError/list/:moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/webHtml/errorList.tpl.html'
			},
			'page@webError' : {
				templateUrl : 'resources/webHtml/page.tpl.html'
			}
		}
	}).state('webInterfaceCtrl', {
		url : '/webInterface/list/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/webHtml/interfaceList.tpl.html'
			},
			'page@webInterfaceCtrl' : {
				templateUrl : 'resources/webHtml/page.tpl.html'
			}
		}
	}).state('webInterfaceDetailCtrl', {
		url : '/webInterfaceDetail/:moduleId/:moduleName/:id',
		views : {
			'main' : {
				templateUrl : 'resources/webHtml/interfaceDetail.tpl.html'
			}
		}
	})
});