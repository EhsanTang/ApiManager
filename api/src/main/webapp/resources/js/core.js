var succ1 = '<div class=\"text-success h tc\"><i class="iconfont">&#xe6b1;</i>&nbsp;&nbsp;';
var succ2 = '</div>';
var err1 = '<div class=\"text-danger h tc\"><i class="iconfont">&#xe6b7;</i>&nbsp;&nbsp;';
var err2 = '</div>';
var loadText = "<div class='tc h tip'><p><img src=\"resources/images/loading.gif\" />&nbsp;&nbsp;努力加载中，请稍后...</p></div>";
function openPage(link){
	location.href = link;
}
function voidFunction(){}
function goBack(){
	history.back(-1);
}
/*******************************************************************************
 *callAjax传递函数时直接写函数名即可，不需要加引号
 ******************************************************************************/
/**
 * 根据键值对调用callAjax
 */
function callAjaxByName(params,iCallBack,iCallBackParam) {
	var iUrl = getValue(params,'iUrl');
	var iFormId = getValue(params,'iFormId');
	var iPost = getValue(params,'iPost');
	var isHowMethod = getValue(params,'isHowMethod');
	var iLoading = getValue(params,'iLoading');
	var iTarget = getValue(params,'iTarget');
	var iParams = getValue(params,'iParams');
	var iAsync = getValue(params,'iAsync');
	var tipTime = getValue(params,'tipTime');
	return callAjax(iUrl, iFormId, iPost, isHowMethod, iLoading, iTarget,
			iParams, iCallBack, iCallBackParam, iAsync,tipTime) ;
}
function getValue(params, key)
{
	var splitstr= new Array();
	splitstr=params.split("|");
	for(var i=0;i<splitstr.length;i++)
	{
		var indexNum = splitstr[i].indexOf('=');
		var key2 = splitstr[i].substr(0,indexNum);
		if(key2 == key){
			return splitstr[i].replace(key+"=","");
		}
		
	}
	return '';
}
/**
 * 
 * @param iurl
 *            地址
 * @param iFormId
 *            表单id
 * @param iPost
 *            发送方式
 * @param isHowMethod
 *            返回数据显示方式:(0:doNothing).什么都不做 (1:updateInput).更新input文本域中的值 (2:updateDivWithImg).更新div中的html，带图标
 *            (3:updateDiv).更新div中的html，不带图标 (4:html).返回html页面 (5:replaceDiv).替换div 
 *            (6:deleteDiv).删除div (7:return).返回数据 (100:custom).调用自定义回调函数刷新数据
 * @param iLoading
 *            加载提示文字,false表示不显示提示，包含PROPUP表示弹出提示
 * @param iTarget
 *            刷新数据的div
 * @param iparams
 *            传递的参数
 * @param iCallBack
 *            回调函数
 * @param iCallBackParam
 *            回调函数参数，用于刷新数据
 * @param iAsync 是否异步
 * @param tipTime propup关闭时间，-1时将不会提示成功，需要使用iTarget更新数据
 * 
 * js中undefined,0,null,'',"",false均代表false
 */
// ----------------------------------//
function callAjax(iUrl, iFormId, iPost, isHowMethod, iLoading, iTarget,
		iParams, iCallBack, iCallBackParam, iAsync, tipTime) {
	//存储ajax返回的数据
	var idata = '';
	var aAsync = (iAsync=='false') ? false : true;
	var aPost = iPost ? 'POST' : 'GET';
	iTarget = iTarget ? iTarget : 'lookUpContent';
	
	// 获取参数
	var xParams = "";
	if(iFormId){
		xParams = $('#'+iFormId).serialize();
	}
	
	// 显示提示语句，只有异步请求才显示提示框
	if(aAsync) {
        showTip(iTarget, iLoading);
    }

	xParams = xParams + '&CPTS=' + new Date().getTime();
			$.ajax({
				type : aPost,
				url : iUrl,
				async : aAsync,
				timeout: 3000,
				data : xParams + iParams,
				complete : function(responseData, textStatus) {
					var data = responseData.responseText;
					if (textStatus == "timeout") {
                        data = "[ERROR]抱歉，网络异常，请稍后再试！Status:" + responseData.status + "，StatusText:" + responseData.statusText;
					}
					if (textStatus == "error" || data == null){
                    	data = "[ERROR]抱歉，系统繁忙，请稍后再试！Status:" + responseData.status + "，StatusText:" + responseData.statusText;
                    }
					/*************未登录或发生未知错误********************/
					if(data.indexOf('[ERRORPAGE]') >= 0){
						data = "[ERROR]抱歉，系统繁忙，请稍后再试！";
					}
					if(data.indexOf('[LOGINPAGE]') >= 0 || data.indexOf('"code":"000021"') >= 0){
						data = "[ERROR]尚未登录，请登录后再试！";
					}
					if(data.indexOf('"success":0') >= 0){
						try{
                            data = "[ERROR]"+ eval("(" + data + ")").error.message;
						}catch (e) {}
					}
					if(data.indexOf('"success":1') >= 0){
					    var json = eval("(" + data + ")");
					    if (json.tipMessage != null && json.tipMessage !=''){
                            data = "[OK]" + json.tipMessage;
                        }else {
                            data = "[OK]操作成功！";
                        }
					}

					//当返回失败页面时需将data替换成提示语句
					if (isHowMethod == '1' || isHowMethod == 'updateInput') {
						$("#"+iTarget).val(data.replace('[OK]', '').replace('[ERROR]', ''));
					} else if (isHowMethod == '2' || isHowMethod == 'updateDivWithImg') {
						if (data.indexOf('[OK]') >= 0) {
                            $("#" + iTarget).html(succ1 + data.replace('[OK]', '') + succ2);
                        }
						else {
                            $("#" + iTarget).html(err1 + data.replace('[ERROR]', '') + err2);
                        }
					}else if (isHowMethod == '3' || isHowMethod == 'updateDiv') {
						$("#"+iTarget).html(data.replace('[OK]', '').replace('[ERROR]', ''));
					} else if (isHowMethod == '4' || isHowMethod == 'html') {
						if (data.indexOf('[ERROR]') < 0){
							if(data.trim().length==0) {
                                $("#" + iTarget).html("<div class='tc pt10'>没有数据！</div>");
                            } else {
                                $("#" + iTarget).html(data);
                            }
						}else{
							$("#"+iTarget).html(err1+data.replace('[ERROR]', '')+ err2);
						}
					} else if (isHowMethod == '5' || isHowMethod == 'replaceDiv') {
						if (data.indexOf('[ERROR]') < 0)
							$("#"+iTarget).replaceWith(data);
						else{
							lookUp('lookUp','',100,300,3);
							$("#lookUpContent").html(err1+data.replace('[ERROR]', '')+err2);
							showMessage('lookUp','false',false,-1);
						}
							
					} else if (isHowMethod == '6' || isHowMethod == 'deleteDiv') {
						if (data.indexOf('[ERROR]') < 0)
							$("#"+iTarget).fadeOut(300);
						else{
							lookUp('lookUp','',100,300,3);
							$("#lookUpContent").html(err1+data.replace('[ERROR]', '')+err2);
							showMessage('lookUp','false',false,-1);
						}
							
					} else if (isHowMethod == '7' || isHowMethod == 'return') {
                        idata = data;
					}
					if (iCallBack) {
							if (iCallBackParam) {
								iCallBack(data,iCallBackParam);
							} else {
								iCallBack(data);
							}
					}
					//100需自行处理提示信息
					if (isHowMethod != '100' && isHowMethod != 'custom') {
                        closeTip(data, iLoading, tipTime);
                    }
				}
			});
			return idata;
}
/**
 * 
 * @param iTarget
 * @param iLoading //fase:不提示，propup:弹窗提示
 */
var floatTimes = 0;
function showTip(iTarget,iLoading) {
	var oldLoadText = iLoading;
	var floatOrPropUp = false;
	if(oldLoadText.toUpperCase().indexOf('PROPUP') >= 0){
		iLoading = iLoading.toUpperCase().replace('PROPUP', '').replace('FLOAT', '');
		if(iLoading==""){
			iLoading = "努力加载中，请稍后...";
		}
		$("#lookUpContent").html(loadText.replace("努力加载中，请稍后...", iLoading));
		lookUp('lookUp','',100,300,3);
		showMessage('lookUp','false',false,-1);
		floatOrPropUp = true;
	}
	if(oldLoadText.toUpperCase().indexOf('FLOAT') >= 0){
		showMessage('float','false',false,-1);
		floatOrPropUp = true;
		if( floatTimes < 0 ){
			floatTimes = 0;
		}
		floatTimes = floatTimes + 1;
	}
	if (!floatOrPropUp && document.getElementById(iTarget)&&document.getElementById(iTarget).tagName != "INPUT") {
		if (iLoading.toUpperCase() != "FALSE"){
			//传递的参数含有图片，表示不以div的形式显示提示内容
			if(iLoading.indexOf("<img")>=0){
				$("#"+iTarget).html(iLoading);
			}else{
				if(iLoading=="")
					iLoading = "努力加载中，请稍后...";
				$("#"+iTarget).html(loadText.replace("努力加载中，请稍后...", iLoading));
			}
		}
	}
}
function closeTip(data,iLoading,tipTime){
	tipTime = tipTime?tipTime:5;
    if (data.indexOf('[OK]')>=0 || data.indexOf('[ERROR]') < 0){
        tipTime = 2;
    }

	var tipMessage = tipTime+ "秒后自动关闭";
	if(tipTime==-1){//不关闭
		tipMessage = '';
	}
	if(data.indexOf('[OK]')>=0){
		if( data.replace('[OK]','')!='' )
			tipMessage = data.replace('[OK]','') + '，' + tipMessage;
		else
			tipMessage = "操作成功" + '，' + tipMessage;
	}
	if(data.indexOf('[ERROR]')>=0){
		tipMessage = data.replace('[ERROR]', '') + "<br>" + tipMessage;
	}
	
	if(iLoading.toUpperCase().indexOf('PROPUP') >= 0){
		//返回结果有提示
		if (data.indexOf('[OK]')>=0){
			$("#lookUpContent").html(succ1 + tipMessage +succ2);
		}
		//返回结果没有提示
		else if(data.indexOf('[ERROR]') < 0){
			$("#lookUpContent").html(succ1+tipMessage+succ2);
		}
		else{
			$("#lookUpContent").html(err1+tipMessage +err2);
		}
		showMessage('lookUp','false',false,tipTime);
	}
	if(iLoading.toUpperCase().indexOf('FLOAT') >= 0){
		floatTimes = floatTimes - 1;
		if( floatTimes == 0){
			showMessage("float",'false',false,0);
		}
	}
	if(iLoading.toUpperCase().indexOf('TIP') >= 0){
		if(tipMessage!="" && tipMessage!="false" && tipMessage!=false){
            if (tipMessage.length < 10){
                $("#tip-div").width(100)
            } else if (tipMessage.length < 20){
                $("#tip-div").width(200)
            } else if (tipMessage.length < 50){
                $("#tip-div").width(400)
            }else{
                $("#tip-div").width(600)
            }
		}
		// +50 padding宽度
		$("#tip-div").css("left",  ($(window).width()/2 - $("#tip-div").width()/2 + 50) +"px");
        showMessage("tip-div",tipMessage,false,tipTime);
	}
}
function showTipWithTime(message, times) {
    if (message == null || message == ''){
        return;
    }
    if (message.length < 10){
        $("#tip-div").width(100)
    } else if (message.length < 20){
        $("#tip-div").width(200)
    } else if (message.length < 50){
        $("#tip-div").width(400)
    }else{
        $("#tip-div").width(600)
    }

    $("#tip-div").html(message);
    $("#tip-div").css("left",  ($(window).width()/2 - $("#tip-div").width()/2 + 50) +"px");
    showMessage("tip-div", message, false, times);
}
/** *********************页面提示信息显示方法************************* */
/**
 * 显示的div，提示信息，是否晃动，自动隐藏时间：-1为不隐藏，其它为隐藏时间（单位秒) message
 * 为false时表示不需要提示信息，仅需要显示div即可
 */
function showMessage(id,message,ishake,time){
	if(message!=""){
		if(message!="false"&&message!=false) {
            $("#" + id).html(message);
        }
		$("#"+id).fadeIn(300);
		if(ishake){
			shake(id);
		}
		if(time!=-1){
			if(isNaN(time)) {
                time = 2000;
            }else if(time>0) {
                time = time * 1000;
            }
			setTimeout(function(){
				if(time!=0){
				   $("#"+id).fadeOut(500);
				}
				else{
					$("#"+id).fadeOut(300);
				}
				$("#"+id).hide("fast");
			},time);
		}
	}
}
// 晃动div
function shake(o){
    var $panel = $("#"+o);
    var box_left =0;
    $panel.css({'left': box_left});
    for(var i=1; 4>=i; i++){
        $panel.animate({left:box_left-(8-2*i)},50);
        $panel.animate({left:box_left+2*(8-2*i)},50);
    }
}
/*******************************************************************************
 * 根据点击位置设置div左边
 * 
 * @param id
 * @param e
 *            为空时，局浏览器中部
 * @param lHeight
 * @param lWidth
 * @param onMouse
 *            div是否覆盖点击的点:(0).不覆盖，div居浏览器中部 (1).X轴居中 (2).Y轴居中 (3).X、Y轴均居中
 *            (4).右下方,(5).id左下方 6:居中，不需要考虑浏览器滚动 7：居中，高度不定，最大不超过浏览器80%
 */
function lookUp(id, e, lHeight, lWidth ,onMouse, positionId) {
	    var lObj = self.document.getElementById(id);
	    var lTop;
	    var lLeft;
	    //居中，高度不定，最大不超过浏览器80%
	    if(onMouse==7){
	    	lLeft=$(window).width()/2 - (lWidth/2);
		    lObj.style.top = '30px';
		    lObj.style.width = lWidth + 'px';
		    lObj.style.height = "auto";
		    lObj.style.left = lLeft + 'px';
		    return;
	    }
	    
	    //如果传入了event
	    if(e && e.clientY && onMouse&&onMouse!=0){
	    	lTop = e.clientY;
	    	lLeft = e.clientX;
	    	if(onMouse==1){
	    		lLeft = lLeft - (lWidth/2);	
		    }else if(onMouse==2){
		    	lTop = lTop - (lHeight/2);
		    }
		    else if(onMouse==3){
			    lTop = lTop - (lHeight/2);
			    lLeft = lLeft - (lWidth/2);
			}else if(onMouse==4){
				lTop = e.clientY;
		    	lLeft = e.clientX;
			}
	    }else{
	    	lTop=$(window).height()/2 - (lHeight/2);
	    	lLeft=$(window).width()/2 - (lWidth/2);
	    }
	    if(onMouse==5){
			lTop = $("#"+positionId).offset().top+$("#"+positionId).outerHeight()-1;
	    	lLeft = $("#"+positionId).offset().left-1;
		}
	    if (lLeft < 0) lLeft = 5;
	    if ((lLeft + lWidth*1) > $(window).width()) lLeft = $(window).width() - lWidth - 20;
	    if ((lTop + lHeight*1) > $(window).height()) lTop =  $(window).height() - lHeight - 70;

	    lObj.style.width = lWidth + 'px';
	    lObj.style.left = (lLeft + document.documentElement.scrollLeft) + 'px';
	    
	    lObj.style.height = lHeight + 'px';
		lObj.style.top =  lTop + 'px';
}

/**************************** 隐藏div *******************************/
function iClose(id){
	$("#"+id).fadeOut(300);
}
function iShow(id){
	$("#"+id).fadeIn(300);
}

/**
 * 全选、全不选
 * 
 * @param id
 *            点击多选框
 * @param name
 *            需要全选的多选框名称
 */
function selectAll(id,name){
	if($("#"+id).prop("checked")==true)
		$("input[name='"+name+"']").prop("checked",true); 
	else
		$("input[name='"+name+"']").prop("checked",false); 
}
