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
			} else {
				$("#"+id).prop("checked", true);
				$("#d_"+id).addClass("pickActive");
				if(isRadio=='true'){
					$("#pickContent div").removeClass("pickActive")
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
		
	</script>
<div class="form-group">
	<blockquote>
		<p class="f12 fb pl10 tl C999">回车即可快速确认</p>
	</blockquote>
</div>
<div id="pickContent">
	${pickContent}
</div>
<c:if test="${radio!='true'}">
<div class="fr w border-t ml10 tr pt10 form-group">
	<button type="button" class="btn btn-info form-control" onclick="setPick()">选择</button>
</div>
</c:if>
