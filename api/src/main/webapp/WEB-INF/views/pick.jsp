<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var pickCallBack = ${iCallBack};
	var pickCallBackParam = '${iCallBackParam}';
	var pickRadio ="${radio}";
	var pickTag = "${tag}";
	var pickTagName = "${tagName}";
		function pickCheck(id,isRadio) {
			if ($("#"+id).is(':checked')) {
				$("#"+id).prop("checked", false);
				$("#d_"+id).removeClass("pickActive");
                $("#d_"+id).removeClass("main-color");
			} else {
				$("#"+id).prop("checked", true);
				$("#d_"+id).addClass("pickActive");
                $("#d_"+id).addClass("main-color");
				if(isRadio=='true'){
					$("#pickContent div").removeClass("pickActive");
                    $("#pickContent div").removeClass("main-color")
					setPick();
				}
			}
			navigateText = "";
			$("#pickTip").html(navigateText);
			$("#pickTip").css("display","none");
		}
		document.onkeydown = function(event) {
			if (event.keyCode == 13) {
				setPick();
			}
		}
		/* 关闭下拉快速定位
		if(hasLoad==0){ 
			$(document).ready(keyMonitor());
		} */

    // pick 确认
    function setPick() {
        var length = document.getElementsByName('cid').length;
        var checkBoxValue = "";
        var checkBoxName = "";
        var rootScope = getRootScope();
        for (var i = 0; i < length; i++) {
            if (pickRadio == 'true') {
                if (document.getElementsByName('cid')[i].checked == true) {
                    rootScope.$apply(function() {
                        if(pickTagName){
                            $("#"+pickTagName).val($(".cidName")[i].textContent);
                            if(rootScope.model) {
                                rootScope.model[pickTagName] = $(".cidName")[i].textContent;
                            }
                        }
                        $("#"+pickTag).val(document.getElementsByName('cid')[i].value);
                        if(rootScope.model) {
                            rootScope.model[pickTag] = document.getElementsByName('cid')[i].value;
                        }
                    });
                    break;
                }
            } else {
                if (document.getElementsByName('cid')[i].checked == true) {
                    checkBoxValue = checkBoxValue + document.getElementsByName('cid')[i].value + ',';
                    checkBoxName = checkBoxName + $(".cidName")[i].textContent + ',';
                }
            }
        }
        if (pickRadio == 'false') {
            //同时跟新控件的值和模型的值，有些控件没有使用模型，如接口参数
            rootScope.$apply(function() {
                $("#"+pickTag).val(checkBoxValue);
                rootScope.model[pickTag] = checkBoxValue;
                if(pickTagName){
                    checkBoxName = replaceAll(checkBoxName, "-", "");
                    checkBoxName = replaceAll(checkBoxName, " ", "");
                    $("#"+pickTagName).val(checkBoxName);
                    rootScope.model[pickTagName] = checkBoxName;
                }
            });
        }
        // 回调函数
        if (pickCallBack) {
            if (pickCallBackParam) {
                pickCallBack(pickCallBackParam);
            } else {
                pickCallBack();
            }
        }
        // 关闭对话框
        iClose('lookUp');
    }
	</script>
<c:if test="${radio!='true'}">
<div class="form-group">
	<blockquote>
		<p class="f12 fb pl10 tl C999">回车即可快速确认</p>
	</blockquote>
</div>
</c:if>
<div id="pickContent">
	${pickContent}
</div>
<c:if test="${radio!='true'}">
<div class="fr w border-t ml10 tr pt10 form-group">
	<button type="button" class="btn btn-main form-control" onclick="setPick()">确认</button>
</div>
</c:if>
