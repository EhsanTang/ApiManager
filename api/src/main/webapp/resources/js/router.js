
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
						return 'resources/html/visitor/articleDetail_ARTICLE.tpl.html?v=v8.0.5';
					else
						return 'resources/html/visitor/articleDetail_'+$stateParems.type+'.tpl.html?v=v8.0.5';
				}
			},'addComment@visitorArticleDetailOld' : {
				templateUrl : 'resources/html/visitor/addComment.tpl.html?v=v8.0.5'
			},'page@visitorArticleDetailOld' : {
				templateUrl : 'resources/html/visitor/page_xs.tpl.html?v=v8.0.5'
			}
		}
	}).state('visitorArticleDetail', {
        url : '/article/detail?projectId&moduleId&type&id',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != 'DICTIONARY')
                        return 'resources/html/visitor/articleDetail_ARTICLE.tpl.html?v=v8.0.5';
                    else
                        return 'resources/html/visitor/articleDetail_'+$stateParems.type+'.tpl.html?v=v8.0.5';
                }
            },'addComment@visitorArticleDetail' : {
                templateUrl : 'resources/html/visitor/addComment.tpl.html?v=v8.0.5'
            },'page@visitorArticleDetail' : {
                templateUrl : 'resources/html/visitor/page_xs.tpl.html?v=v8.0.5'
            }
        }
    }).state('visitorArticleListOld', {
        url : '/:projectId/article/list/:&moduleId/:type/:category/:name/:status',
        views : {
            'main' :{
                templateUrl : function($stateParems){
                    if($stateParems.type != "DICTIONARY")
                        return 'resources/html/visitor/articleList_ARTICLE.tpl.html?v=v8.0.5';
                    else
                        return 'resources/html/visitor/articleList_'+$stateParems.type+'.tpl.html?v=v8.0.5';
                }
            },'page@visitorArticleList' : {
                templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
            }
        }
    }).state('visitorArticleList', {
		url : '/article/list?projectId&moduleId&type&category&name&status',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					if($stateParems.type != "DICTIONARY")
						return 'resources/html/visitor/articleList_ARTICLE.tpl.html?v=v8.0.5';
					else
						return 'resources/html/visitor/articleList_'+$stateParems.type+'.tpl.html?v=v8.0.5';
				}
			},'page@visitorArticleList' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.5'
			}
		}
	}).state('visitorSourceDetail', {
		url : '/source/detail?projectId&id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/visitor/sourceDetail' +
						'.tpl.html?v=v8.0.5';
				}
			}
		}
	})
});