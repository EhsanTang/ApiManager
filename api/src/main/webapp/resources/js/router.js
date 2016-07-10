
/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
app.config(function($stateProvider, $urlRouterProvider) {
	if($("#sessionAuth").length>0){
		$urlRouterProvider.otherwise('/webWebPage/detail/PAGE/ADMINHELP');
	}else{
		$urlRouterProvider.otherwise('/webWebPage/detail/PAGE/WELCOME');
	}
	/*********************后台*******************/
	$stateProvider.state('preLogin', {
		url : '/preLogin',
	}).state('menuList', {
		url : '/menu/list/:parentId/:type/:menuName',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/menuList.tpl.html'
			},
			'page@menuList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/menuDetail.tpl.html';
				}
			}
		}
	}).state('interfaceList', {
		url : '/interface/list/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/interfaceList.tpl.html'
			},
			'page@interfaceList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/interfaceDetail.tpl.html';
				}
			}
		}
	}).state('sourceList', {
		url : '/source/list/:directoryId/:directoryName',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/sourceList.tpl.html'
			},
			'page@sourceList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/sourceDetail.tpl.html';
				}
			}
		}
	}).state('webSourceList', {
		url : '/webSource/list/:directoryId/:directoryName',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/webSourceList.tpl.html'
			},
			'page@webourceList' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('sourceDetail', {
		url : '/webSource/detail/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/sourceDetail.tpl.html';
				}
			}
		}
	}).state('settingList', {
		url : '/setting/list/:key',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/settingList.tpl.html'
			},
			'page@settingList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			}
		}
	}).state('settingDetail', {
		url : '/setting/detail/:type/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/settingDetail_'+$stateParems.type+'.tpl.html';
				}
			}
		}
	}).state('userList', {
		url : '/user/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/userList.tpl.html'
			},
			'page@userList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/userDetail.tpl.html';
				}
			}
		}
	}).state('webPageList', {
		url : '/webPage/list/:type',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/webPageList.tpl.html'
			},
			'page@webPageList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/webPageDetail_'+$stateParems.type+'.tpl.html';
				}
			}
		}
	}).state('roleList', {
		url : '/role/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/roleList.tpl.html'
			},
			'page@roleList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/roleDetail.tpl.html';
				}
			}
		}
	}).state('errorList', {
		url : '/error/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/errorList.tpl.html'
			},
			'page@errorList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/errorDetail.tpl.html';
				}
			}
		}
	}).state('profile', {
		url : '/profile',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/userDetail.tpl.html'
			}
		}
	}).state('logList', {
		url : '/log/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/logList.tpl.html'
			},
			'page@logList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/logDetail.tpl.html';
				}
			}
		}
	})
	/*********************前端*******************/
	$stateProvider.state('webSettingDetail', {
		url : '/webSetting/detail/:key',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/settingDetail.tpl.html';
				}
			}
		}
	}).state('webWebPageList', {
		url : '/webWebPage/list/:type/:searchCategory',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/webPageList_'+$stateParems.type+'.tpl.html';
				}
			},'page@webWebPageList' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('webWebPageDetail', {
		url : '/webWebPage/detail/:type/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/webPageDetail_'+$stateParems.type+'.tpl.html';
				}
			},'addComment@webWebPageDetail' : {
				templateUrl : 'resources/html/frontHtml/addComment.tpl.html'
			}
		}
	}).state('webError', {
		url : '/webError/list/:moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/errorList.tpl.html'
			},
			'page@webError' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('webInterfaceCtrl', {
		url : '/webInterface/list/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/interfaceList.tpl.html'
			},
			'page@webInterfaceCtrl' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('webInterfaceDetailCtrl', {
		url : '/webInterfaceDetail/:id',
		views : {
			'main' : {
				templateUrl : 'resources/html/frontHtml/interfaceDetail.tpl.html'
			}
		}
	}).state('webInterfaceDebugCtrl', {
		url : '/webInterface/debug/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/interfaceDebug.tpl.html';
				}
			}
		}
	}).state('frontSearchCtrl', {
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
	}).state('markdown', {
		url : '/markdown',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/markdown/markdown.html';
				}
			}
		}
	})
});