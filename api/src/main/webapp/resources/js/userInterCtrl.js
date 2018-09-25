/**
 * 接口相关Controller
 */
userInterModule.controller('userInterCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
    /**$scope.getRequestExam = function(editerId,targetId,item,tableId) {
    	var params = "iUrl=user/interface/getRequestExam.do|iLoading=FLOAT|iPost=true|iParams=&"+$.param($rootScope.model);
		httpService.callHttpMethod($http,params).success(function(result) {
			var isSuccess = httpSuccess(result,'iLoading=FLOAT');
			if(!isJson(result)||isSuccess.indexOf('[ERROR]') >= 0){
				 $rootScope.error = isSuccess.replace('[ERROR]', '');
				 $rootScope.model = null;
			 }else{
				 $rootScope.error = null;
				 $rootScope.model.requestExam = result.data.requestExam;
			 }
		});
    };**/
    $scope.editerParam = function(editerId,targetId,item,tableId) {
    	if(tableId=='editParamTable'&&item.param!=''){
    		
    		// 如果param为空，或者以form=开头，表示为form表单参数，否则表示为自定义参数
    		if(item.param.length<5 || item.param.substring(0,5)!="form="){
    			if(myConfirm("参数格式有误，将丢失所有参数，是否切换至表单模式？")){
    				$rootScope.model.params = eval("([])");
    			}else{
    				return;
    			}
    		}else{
    			// 将param转换为json数据
        		try{
        			$rootScope.model.params = eval("("+item.param.substring(5)+")");
        		}catch(e){
        			if(myConfirm("参数格式有误，将丢失所有参数，是否切换至表单模式？")){
        				$rootScope.model.params = eval("([])");
        			}else{
        				return;
        			}
        		}
    		}
    		
    	}else if(tableId=='eparamRemarkTable'){
    		$rootScope.model.paramRemarks = eval("("+item.paramRemark+")");
    	}
		$("#"+editerId).removeClass('none');
		$("#"+targetId).addClass('none');
    };

    $scope.modifyParam = function(editerId,targetId,item,type) {
    	if(type=='param'){
    		var json = getParamFromTable('editParamTable');
    		try{
    		 eval("("+json+")");
    		}catch(e){
    			alert("输入有误，json解析出错："+e);
    			return;
    		}
    		item.param = "form="+json	
    	}else if(type=="paramRemark"){
    		var json = getParamFromTable('eparamRemarkTable');
    		try{
       		 eval("("+json+")");
       		}catch(e){
       			alert("输入有误，json解析出错："+e);
       			return;
       		}
    		item.paramRemark = json;
    	}
    	$("#"+editerId).addClass('none');
		$("#"+targetId).removeClass('none');
    };
    /****************End:返回参数***************/
});

// function getNewArray(id, index) {
//     var newIndex = 0;
//     $("#"+ id).find('tr').each(function(i) {
//         if (index == $(this).attr("index")){
//             newIndex = i;
//         }
//     });
//     return newIndex;
// }

// function getNewArray(id) {
//     var newArray = [];
//     $('#' + id).find('tbody').find('tr').each(function() {
//         var newObj=new Object();
//         $(this).find('td').find('input').each(function(i, val) {
//             newObj[val.name] = val.value;
//         });
//         $(this).find('td').find('select').each(function(i, val) {
//             newObj[val.name] = val.value;
//         });
//         newArray.push(newObj);
//     });
//     return newArray;
// }



