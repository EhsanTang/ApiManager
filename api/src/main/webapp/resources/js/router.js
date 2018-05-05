
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
						return 'resources/html/frontHtml/articleDetail_ARTICLE.tpl.html';
					else
						return 'resources/html/frontHtml/articleDetail_'+$stateParems.type+'.tpl.html';
				}
			},'addComment@frontArticleDetail' : {
				templateUrl : 'resources/html/frontHtml/addComment.tpl.html'
			},'page@frontArticleDetail' : {
				templateUrl : 'resources/html/frontHtml/page_xs.tpl.html'
			}
		}
	}).state('frontArticleList', {
		url : '/:projectId/article/list/:moduleId/:type/:category/:name/:status',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					if($stateParems.type != "DICTIONARY")
						return 'resources/html/frontHtml/articleList_ARTICLE.tpl.html';
					else
						return 'resources/html/frontHtml/articleList_'+$stateParems.type+'.tpl.html';
				}
			},'page@frontArticleList' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('frontSourceDetail', {
		url : '/:projectId/source/detail/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/sourceDetail.tpl.html';
				}
			}
		}
	})
});