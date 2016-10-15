
/**
 * 前后台共有的路由
 */
app.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('frontArticleDetail', {
		url : '/:projectId/article/detail/:type/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					if($stateParems.type == 'PROJECTARTICLE')
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
		url : '/:projectId/webPage/list/:type/:search',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/webPageList_'+$stateParems.type+'.tpl.html';
				}
			},'page@frontArticleList' : {
				templateUrl : 'resources/html/frontHtml/page.tpl.html'
			}
		}
	}).state('sourceDetail', {
		url : '/front/source/detail/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/sourceDetail.tpl.html';
				}
			}
		}
	})
});