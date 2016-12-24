
/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/web/article/detail/web/PAGE/ADMINHELP');
	/*********************后台*******************/
	$stateProvider.state('loginOrRegister', {
		url : '/login',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/login.tpl.html'
			}
		}
	}).state('register', {
		url : '/register',
		views : {
			'main' : {
				templateUrl : 'resources/html/backHtml/register.tpl.html'
			}
		}
	}).state('findPwd', {
		url : '/findPwd',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/findPwd.tpl.html'
			}
		}
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
	}).state('projectList', {
		url : '/user/project/list/:myself/:type',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/user/projectList.tpl.html';
				}
			},'page@projectList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/projectDetail.tpl.html';
				}
			}
		}
	}).state('moduleList', {
		url : '/user/module/list/:projectId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/moduleList.tpl.html'
			},
			'page@moduleList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/moduleDetail.tpl.html';
				}
			}
		}
	}).state('interfaceList', {
		url : '/user/interface/list/:projectId/:moduleId/:moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/interfaceList.tpl.html'
			},
			'page@interfaceList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/interfaceDetail.tpl.html';
				}
			},
			'interResEditorDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interResEditorDiv.tpl.html'
			},
			'interFormParamDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interFormParamDiv.tpl.html'
			},
			'interHeaderDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interHeaderDiv.tpl.html'
			},
			'interParamRemakDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interParamRemakDiv.tpl.html'
			}
			
		}
	}).state('errorList', {
		url : '/user/error/list/:projectId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/errorList.tpl.html'
			},
			'page@errorList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/errorDetail.tpl.html';
				}
			}
		}
	}).state('articleList', {
		url : '/user/article/list/:projectId/:moduleId/:type',
		views : {
			'main' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/articleList.tpl.html';
				}
			},
			'page@articleList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/articleDetail_'+$stateParems.type+'.tpl.html';
				}
			}
		}
	}).state('sourceList', {
		url : '/user/source/list/:projectId/:moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/sourceList.tpl.html'
			},
			'page@sourceList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/sourceDetail.tpl.html';
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
	}).state('configProperties', {
		url : '/config/properties',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/backHtml/config.properties.html';
				}
			}
		}
	}).state('dictionaryImoprtFromSql', {
		url : '/article/dictionary/importFromSql',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/user/dictionaryImportFromSql.tpl.html';
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
	}).state('projectUserList', {
		url : '/user/projectUser/list/:projectId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/projectUserList.tpl.html'
			},
			'page@projectUserList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/projectUserDetail.tpl.html';
				}
			}
		}
	}).state('commentList', {
		url : '/:projectId/comment/list/:articleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/commentList.tpl.html'
			},
			'page@commentList' : {
				templateUrl : 'resources/html/backHtml/page.tpl.html'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/commentDetail.tpl.html';
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