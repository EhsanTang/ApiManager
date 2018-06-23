
/**
 * 前后台共有的路由
 */
app.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('frontArticleDetail', {
		url : '/:projectId/article/detail/:moduleId/:type/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					if($stateParems.type != 'DICTIONARY')
						return 'resources/html/visitor/articleDetail_ARTICLE.tpl.html?v=v8.0.2';
					else
						return 'resources/html/visitor/articleDetail_'+$stateParems.type+'.tpl.html?v=v8.0.2';
				}
			},'addComment@frontArticleDetail' : {
				templateUrl : 'resources/html/visitor/addComment.tpl.html?v=v8.0.2'
			},'page@frontArticleDetail' : {
				templateUrl : 'resources/html/visitor/page_xs.tpl.html?v=v8.0.2'
			}
		}
	}).state('frontArticleList', {
		url : '/:projectId/article/list/:moduleId/:type/:category/:name/:status',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					if($stateParems.type != "DICTIONARY")
						return 'resources/html/visitor/articleList_ARTICLE.tpl.html?v=v8.0.2';
					else
						return 'resources/html/visitor/articleList_'+$stateParems.type+'.tpl.html?v=v8.0.2';
				}
			},'page@frontArticleList' : {
				templateUrl : 'resources/html/visitor/page.tpl.html?v=v8.0.2'
			}
		}
	}).state('frontSourceDetail', {
		url : '/:projectId/source/detail/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/visitor/sourceDetail.tpl.html?v=v8.0.2';
				}
			}
		}
	})
});