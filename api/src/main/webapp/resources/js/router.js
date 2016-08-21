
/**
 * 前后台共有的路由
 */
app.config(function($stateProvider, $urlRouterProvider) {
	/*********************后台*******************/
	$stateProvider.state('webPageDetail', {
		url : '/:projectId/webPage/detail/:type/:id',
		views : {
			'main' :{
				templateUrl : function($stateParems){
					return 'resources/html/frontHtml/webPageDetail_'+$stateParems.type+'.tpl.html';
				}
			},'addComment@webPageDetail' : {
				templateUrl : 'resources/html/frontHtml/addComment.tpl.html'
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