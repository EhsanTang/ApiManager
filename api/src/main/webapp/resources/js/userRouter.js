/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
var commonUrlParam = 'projectId&projectName&moduleId&moduleName&menu_a&menu_b&dataType&pageName';
app.config(function($stateProvider, $urlRouterProvider) {
	// 文章
    $stateProvider.state('userArticleList', {
        url : '/user/article/list?currentPage&name&category&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleList.tpl.html?v=v8.0.5';
                }
            },
            'page@userArticleList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('userEditArticle', {
        url : '/user/article/edit?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleEdit.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('userArticleDetail', {
        url : '/user/article/detail?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/articleDetail.tpl.html?v=v8.0.5';
                }
            }
        }
    });

    // 数据库表
    $stateProvider.state('userDictionaryList', {
        url : '/user/dictionary/list?currentPage&name&category&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/dictionaryList.tpl.html?v=v8.0.5';
                }
            },
            'page@userDictionaryList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('userEditDictionary', {
        url : '/user/dictionary/edit?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/dictionaryEdit.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('userDictionaryDetail', {
        url : '/user/dictionary/detail?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/dictionaryDetail.tpl.html?v=v8.0.5';
                }
            }
        }
    });

    // 接口
    $stateProvider.state('userInterList', {
        url : '/user/interface/list?&interfaceName&url&currentPage&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/interfaceList.tpl.html?v=v8.0.5'
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceCopy.tpl.html?v=v8.0.5';
                }
            }, 'page@userInterList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('userInterfaceEdit', {
        url : '/user/interface/edit?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceEdit.tpl.html?v=v8.0.5';
                }
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/interEditDialog.tpl.html?v=v8.0.5';
                }
            }, 'interBaseEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interBaseEdit.tpl.html?v=v8.0.5'
            }, 'interResParamEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interResParamEdit.tpl.html?v=v8.0.5'
            }, 'interParamEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interParamEdit.tpl.html?v=v8.0.5'
            }, 'interHeaderEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interHeaderEdit.tpl.html?v=v8.0.5'
            }, 'interExampleEdit@userInterfaceEdit' : {
                templateUrl : 'resources/html/subTpl/interExampleEdit.tpl.html?v=v8.0.5'
            }
        }
    }).state('userInterfaceDetail', {
        url : '/user/interface/detail?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceDetail.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('userInterfaceDebug', {
        url : '/user/interface/debug?id&' + commonUrlParam,
        views : {
            'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }, 'main' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/interfaceDebug.tpl.html?v=v8.0.5';
                }
            }, 'interParamEdit@userInterfaceDebug' : {
                templateUrl : 'resources/html/subTpl/interParamEdit.tpl.html?v=v8.0.5'
            }, 'interHeaderEdit@userInterfaceDebug' : {
                templateUrl : 'resources/html/subTpl/interHeaderEdit.tpl.html?v=v8.0.5'
            }
        }
    });


	$stateProvider.state('loginOrRegister', {
		url : '/login',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/login.tpl.html?v=v8.0.5'
			}
		}
	}).state('register', {
		url : '/register',
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/register.tpl.html?v=v8.0.5'
			}
		}
	}).state('findPwd', {
		url : '/findPwd',
		views : {
			'main' : {
				templateUrl : 'resources/html/user/findPwd.tpl.html?v=v8.0.5'
			}
		}
	}).state('menuList', {
		url : '/admin/menu/list?parentId&type&menuName&' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/menuList.tpl.html?v=v8.0.5'
			},
			'page@menuList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/admin/menuDetail.tpl.html?v=v8.0.5';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuMenu.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('projectList', {
		url : '/user/project/list?projectShowType&type&' + commonUrlParam,
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/user/projectList.tpl.html?v=v8.0.5';
				}
			},'page@projectList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/projectDetail.tpl.html?v=v8.0.5';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuProject.tpl.html?v=v8.0.5';
                }
            }

		}
	}).state('project', {
        url : '/user/project?' + commonUrlParam,
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    return 'resources/html/user/project.tpl.html?v=v8.0.5';
                }
            },'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/projectDetail.tpl.html?v=v8.0.5';
                }
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }

        }
    }).state('moduleList', {
		url : '/user/module/list?name&' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/user/moduleList.tpl.html?v=v8.0.5'
			},
			'page@moduleList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/moduleDetail.tpl.html?v=v8.0.5';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('errorList', {
		url : '/user/error/list?' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/user/errorList.tpl.html?v=v8.0.5'
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            },
			'page@errorList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/errorDetail.tpl.html?v=v8.0.5';
				}
			}
		}
	}).state('projectUserList', {
        url : '/user/projectUser/list?name&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/projectUserList.tpl.html?v=v8.0.5'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            },
            'page@projectUserList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/projectUserDetail.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('logList', {
        url : '/user/log/list?identy&' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/user/logList.tpl.html?v=v8.0.5'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            },
            'page@logList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
            },
            'detail' : {
                templateUrl : function($stateParems){
                    return 'resources/html/user/logDetail.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('sourceList', {
		url : '/user/source/list?' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/user/sourceList.tpl.html?v=v8.0.5'
			},
			'page@sourceList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            },'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/sourceDetail.tpl.html?v=v8.0.5';
				}
			}
		}
	}).state('settingList', {
		url : '/admin/setting/list?key&' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/settingList.tpl.html?v=v8.0.5'
			},
			'page@settingList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('settingDetail', {
		url : '/admin/setting/detail?type&id&' + commonUrlParam,
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/admin/settingDetail_'+$stateParems.type+'.tpl.html?v=v8.0.5';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('hotSearchList', {
        url : '/admin/hotSearch/list?' + commonUrlParam,
        views : {
            'main' : {
                templateUrl : 'resources/html/admin/hotSearchList.tpl.html?v=v8.0.5'
            },
            'page@hotSearchList' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
            },'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=v8.0.5';
                }
            }
        }
    }).state('configProperties', {
		url : '/admin/config/properties?' + commonUrlParam,
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/admin/config.properties.html';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('dictionaryImoprtFromSql', {
		url : '/user/article/dictionary/importFromSql?' + commonUrlParam,
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/user/dictionaryImportFromSql.tpl.html?v=v8.0.5';
				}
			}
		}
	}).state('userList', {
		url : '/admin/user/list?' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/userList.tpl.html?v=v8.0.5'
			},
			'page@userList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/admin/userDetail.tpl.html?v=v8.0.5';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('commentList', {
		url : '/user/comment/list?articleId&' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/user/commentList.tpl.html?v=v8.0.5'
			},
			'page@commentList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/user/commentDetail.tpl.html?v=v8.0.5';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuModule.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('roleList', {
		url : '/admin/role/list?' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/roleList.tpl.html?v=v8.0.5'
			},
			'page@roleList' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=v8.0.5'
			},
			'detail' : {
				templateUrl : function($stateParems){
					return 'resources/html/admin/roleDetail.tpl.html?v=v8.0.5';
				}
			},'subMenu' :{
                templateUrl : function($stateParems){
                    return 'resources/html/subTpl/subMenuSetting.tpl.html?v=v8.0.5';
                }
            }
		}
	}).state('profile', {
		url : '/profile?' + commonUrlParam,
		views : {
			'main' : {
				templateUrl : 'resources/html/admin/userDetail.tpl.html?v=v8.0.5'
			}
		}
	})
});