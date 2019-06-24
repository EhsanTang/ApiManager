/*******************************************************************************
 *callAjax传递函数时直接写函数名即可，不需要加引号
 ******************************************************************************/
/**
 * @param params
 * @param iCallBack
 * @param iCallBackParam 原样返回至回调方法
 * @returns {string}
 */
function callAjaxNew(config, params, iCallBack, iCallBackParam) {
	var result = null;
    $.ajax({
        type : config.type,
        url : config.url,
        async : config.async,
        timeout: 3000,
        data : params,
        complete : function(data) {
            data = data.responseText;
            result = data;
            if (iCallBack) {
                if (iCallBackParam) {
                    iCallBack(data,iCallBackParam);
                } else {
                    iCallBack(data);
                }
            } else {
                defAjaxCallBack(config, data);
            }
        }
    });
}

/********** 请求配置 ********/
function getAjaxConfig(id, url, loadingText, loading) {
	return {async: true, url:url, type:'POST', timeout: 3000, loading:loading, loadingText: loadingText, id:id, tipTime:3,
        width:100, height:200, position: 3 };
}
function getFastAjaxConfig(url, id) {
    return {async: true, url:url, type:'POST', timeout: 3000, loading:'PROP_UP', loadingText: '努力加载中，请稍后...', id:id,
        tipTime:3, width:100, height:200, position: 3};
}

function showTipNew(config, e) {
	// 弹窗提示
	if(config.loading == 'PROP_UP'){
		$("#lookUpContent").html(config.loadingText);
        // 计算弹窗位置
		lookUp('lookUp', e, config.height, config.width, config.position, config.tag);
		showMessage('lookUp','false', false, -1);
	}

	// 浮层提示
	else if(config.loading == 'FLOAT'){
		showMessage('float','false', false, -1);
	}

	// 当前ID提示
	else if (config.loading == "ID") {
		if (document.getElementById(config.id).tagName != "INPUT"){
            $("#"+id).val(config.loadingText);
		} else{
            $("#"+id).html(config.loadingText);
		}
	}

    else if (config.loading == "TIP") {
        showTipWithTime(config.loadingText, -1)
    }

    // 不需要提示
    else if (config.loading == "FALSE") {}
}

function closeTipNew(config){
	var tipTime;
    if (config.success){
        config.loadingText = succ1 + config.loadingText +succ2;
        tipTime = 2;
    } else {
        config.loadingText = err1 + tipMessage + err2;
        tipTime = 5;
	}

    // 弹窗提示
    if(config.loading == 'PROP_UP'){
        $("#lookUpContent").html(config.loadingText);
        showMessage('lookUp', 'false', false, tipTime);
    }

    // 浮层提示
    else if(config.loading == 'FLOAT'){
        showMessage('float', 'false', false, 0);
    }

    // 当前ID，不需要关闭
    else if (config.loading == "ID") {}

    else if (config.loading == "TIP") {
        showTipWithTime(config.loadingText, tipTime)
    }

    // 不需要提示
    else if (config.loading == "FALSE") {}
}

/********* 回调方法 *********/
// 默认回调方法
function defAjaxCallBack(config, data) {
    if (config.loading == 'PROP_UP'){
        $("#lookUpContent").html(data);
        return;
    }

    try {
        var json = eval("(" + data + ")");
        config.success = (json.success == 1);
    } catch (e) {
        console.error(e);
        config.success = false;
    }
    closeTipNew(config);
}