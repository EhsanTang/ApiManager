
/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('user/project/list?myself=true&type=-1');
	/*********************后台*******************/
	$stateProvider.state('loginOrRegister', {
		url : '/login',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/login.tpl.html?v=v8.0.2'
			}
		}
	}).state('register', {
		url : '/register',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/register.tpl.html?v=v8.0.2'
			}
		}
	}).state('findPwd', {
		url : '/findPwd',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/findPwd.tpl.html?v=v8.0.2'
			}
		}
	}).state('menuList', {
		url : '/admin/menu/list?parentId&type&menuName',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/menuList.tpl.html?v=v8.0.2'
			},
			'page@menuList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/admin/menuDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('projectList', {
		url : '/user/project/list?myself&type',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/user/projectList.tpl.html?v=v8.0.2';
				}
			},'page@projectList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/projectDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('moduleList', {
		url : '/user/module/list?projectId&name',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/moduleList.tpl.html?v=v8.0.2'
			},
			'page@moduleList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/moduleDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('interfaceList', {
		url : '/user/interface/list?projectId&moduleId&moduleName',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/interfaceList.tpl.html?v=v8.0.2'
			},
			'page@interfaceList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/interfaceDetail.tpl.html?v=v8.0.2';
				}
			},
			'interResEditorDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interResEditorDiv.tpl.html?v=v8.0.2'
			},
			'interFormParamDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interFormParamDiv.tpl.html?v=v8.0.2'
			},
			'interHeaderDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interHeaderDiv.tpl.html?v=v8.0.2'
			},
			'interParamRemakDiv@interfaceList' : {
				templateUrl : 'resources/html/subTpl/interParamRemakDiv.tpl.html?v=v8.0.2'
			}
			
		}
	}).state('errorList', {
		url : '/user/error/list?projectId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/errorList.tpl.html?v=v8.0.2'
			},
			'page@errorList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/errorDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('articleList', {
        url : '/user/article/list?projectId&moduleId&type&moduleName',
        views : {
            'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleList.tpl.html?v=v8.0.2';
                }
            },
            'page@articleList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleDetail_'+$stateParems.type+'.tpl.html?v=v8.0.2';
                }
            }
        }
    }).state('sourceList', {
		url : '/user/source/list?projectId&moduleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/sourceList.tpl.html?v=v8.0.2'
			},
			'page@sourceList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/sourceDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('settingList', {
		url : '/admin/setting/list?key',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/settingList.tpl.html?v=v8.0.2'
			},
			'page@settingList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			}
		}
	}).state('settingDetail', {
		url : '/admin/setting/detail?type&id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/admin/settingDetail_'+$stateParems.type+'.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('hotSearchList', {
        url : '/admin/hotSearch/list',
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/hotSearchList.tpl.html?v=v8.0.2'
            },
            'page@hotSearchList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
            }
        }
    }).state('configProperties', {
		url : '/admin/config/properties',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/admin/config.properties.html';
				}
			}
		}
	}).state('dictionaryImoprtFromSql', {
		url : '/user/article/dictionary/importFromSql',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/user/dictionaryImportFromSql.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('userList', {
		url : '/user/user/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/userList.tpl.html?v=v8.0.2'
			},
			'page@userList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/admin/userDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('projectUserList', {
		url : '/user/projectUser/list?projectId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/projectUserList.tpl.html?v=v8.0.2'
			},
			'page@projectUserList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/projectUserDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('commentList', {
		url : '/user/comment/list?projectId&articleId',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/commentList.tpl.html?v=v8.0.2'
			},
			'page@commentList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/commentDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('roleList', {
		url : '/admin/role/list',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/roleList.tpl.html?v=v8.0.2'
			},
			'page@roleList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/admin/roleDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	}).state('profile', {
		url : '/profile',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/userDetail.tpl.html?v=v8.0.2'
			}
		}
	}).state('logList', {
		url : '/user/log/list?identy',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/logList.tpl.html?v=v8.0.2'
			},
			'page@logList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.2'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/admin/logDetail.tpl.html?v=v8.0.2';
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