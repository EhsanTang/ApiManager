
/**
 * 前后台共有的路由
 */
app.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('visitorArticleDetailOld', {
		url : '/:projectId/article/detail/:moduleId/:type/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					if($stateParems.type != 'DICTIONARY')
						return 'resources/html/visitor/articleDetail_ARTICLE.tpl.html?v=V8.2.0_0630';
					else
						return 'resources/html/visitor/articleDetail_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
				}
			},'comment@visitorArticleDetailOld' : {
				templateUrl : 'resources/html/subTpl/comment.tpl.html?v=V8.2.0_0630'
			},'page@visitorArticleDetailOld' : {
				templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
			}
		}
	}).state('visitorArticleDetail', {
        url : '/article/detail?projectId&moduleId&type&id',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != 'DICTIONARY')
                        return 'resources/html/visitor/articleDetail_ARTICLE.tpl.html?v=V8.2.0_0630';
                    else
                        return 'resources/html/visitor/articleDetail_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
                }
            },'comment@visitorArticleDetail' : {
                templateUrl : 'resources/html/subTpl/comment.tpl.html?v=V8.2.0_0630'
            },'page@visitorArticleDetail' : {
                templateUrl : 'resources/html/admin/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorArticleListOld', {
        url : '/:projectId/article/list/:&moduleId/:type/:category/:name/:status',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != "DICTIONARY")
                        return 'resources/html/visitor/articleList_ARTICLE.tpl.html?v=V8.2.0_0630';
                    else
                        return 'resources/html/visitor/articleList_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
                }
            },'page@visitorArticleList' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
            }
        }
    }).state('visitorArticleList', {
		url : '/article/list?projectId&moduleId&type&category&name&status',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					if($stateParems.type != "DICTIONARY")
						return 'resources/html/visitor/articleList_ARTICLE.tpl.html?v=V8.2.0_0630';
					else
						return 'resources/html/visitor/articleList_'+$stateParems.type+'.tpl.html?v=V8.2.0_0630';
				}
			},'page@visitorArticleList' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=V8.2.0_0630'
			}
		}
	}).state('visitorSourceDetail', {
		url : '/source/detail?projectId&id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/visitor/sourceDetail' +
						'.tpl.html?v=V8.2.0_0630';
				}
			}
		}
	})
});