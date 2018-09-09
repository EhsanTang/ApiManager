/**
 * 接口相关Controller
 */
userInterModule.controller('userInterCtrl', function($rootScope,$scope, $http, $state, $stateParams ,httpService) {
    $scope.getRequestExam = function(editerId,targetId,item,tableId) {
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
    };
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
    		
    	}else if(tableId=='editResponseParamTable'){
    		$rootScope.model.responseParams = eval("("+item.responseParam+")");
    	}else if(tableId=='editHeaderTable'){
    		$rootScope.model.headers = eval("("+item.header+")");
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
    	}
    	else if(type=="responseParam"){
    		var json = getParamFromTable('editResponseParamTable');
    		try{
       		 eval("("+json+")");
       		}catch(e){
       			alert("输入有误，json解析出错："+e);
       			return;
       		}
    		item.responseParam = json;
    	}else if(type=="header"){
    		var json = getParamFromTable('editHeaderTable');
    		try{
       		 eval("("+json+")");
       		}catch(e){
       			alert("输入有误，json解析出错："+e);
       			return;
       		}
    		item.header = json;
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

    /***********添加参数********/
    $scope.addOneParam = function($event,field){
        if($($event.target).val() != ''){
            var tr = $($event.target).parent().parent();
            if( tr.hasClass("last") ){
                $rootScope[field][$rootScope[field].length] = getOneParam();
                tr.removeClass("last");
            }
        }
    }
    $scope.insertOneParam = function($event,field, index){
        var tr = $($event.target).parent().parent();
        $rootScope[field].splice(index + 1, 0, getOneParam());
    }
    $scope.deleteOneParam = function($event, field, index) {
        $rootScope[field].splice(index,1);
        var i;
    }

    /***********添加嵌套参数**************/
    $scope.addOneParamByParent = function(field,deep,parentIndex){
    	var newObj=new Object();
    	newObj.type="string";
    	newObj.necessary="true";
    	if(parentIndex || parentIndex==0){
    		// 兼容历史数据
        	if(!deep){
        		deep = 0;
        		$rootScope.model[field][parentIndex].deep=0;
        	}
        	newObj.deep=deep*1+1;
        	$rootScope.model[field].splice(parentIndex + 1, 0, newObj);
    	}else{
    		newObj.deep = 0*1;
    		$rootScope.model[field][$rootScope.model[field].length]=newObj;
    	}
    }
    
    $scope.deleteOneParamByParent = function(field,parentIndex,deep){
    	// 兼容历史数据
    	if(!deep){
    		deep = 0;
    		$rootScope.model[field][parentIndex].deep=0;
    	}
    	var needDelete = 1;
    	for(var i=parentIndex+1; i<$rootScope.model[field].length; i++){
    		if($rootScope.model[field][i].deep>deep){
    			needDelete ++;
    		}else{
    			break;
    		}
    	}
    	$rootScope.model[field].splice(parentIndex, needDelete);
    }
    $scope.importParams = function(field){
    	var jsonText = jsonToDiv($rootScope.model.importJson);
    	if(jsonText.length > 0){
    		$rootScope.model[field] = eval("("+jsonText+")");
    		if(field == 'responseParams'){
    			changeDisplay('responseEditorDiv','responseImportDiv');
    			changeDisplay('responseEparam','responseParam');
    		}else if(field == 'paramRemarks'){
    			changeDisplay('paramEditorDiv','paramImportDiv');
    			changeDisplay('eparamRemark','paramRemark');
    		}
    	}
	}
    /****************End:返回参数***************/
});

function getOneParam() {
    var newObj=new Object();
    newObj.deep=0;
    newObj.type="string";
    newObj.necessary="true";
    newObj.inUrl="false";
    return newObj;
}

var fixHelperModified = function(e, tr) {
        var $originals = tr.children();
        var $helper = tr.clone();
        $helper.children().each(function(index) {
            $(this).width($originals.eq(index).width() + 2)
        });
        return $helper;
    }
var updateIndex = function(e, ui) {

};
function getNewArray(id, index) {
    var newIndex = 0;
    $("#"+ id).find('tr').each(function(i) {
        if (index == $(this).attr("index")){
            newIndex = i;
        }
    });
    return newIndex;
}

/**function getNewArray(id) {
    var newArray = [];
    $('#' + id).find('tbody').find('tr').each(function() {
        var newObj=new Object();
        $(this).find('td').find('input').each(function(i, val) {
            newObj[val.name] = val.value;
        });
        $(this).find('td').find('select').each(function(i, val) {
            newObj[val.name] = val.value;
        });
        newArray.push(newObj);
    });
    return newArray;
}**/



