<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
		function pickCheck(id,isRadio) {
			if ($("#"+id).is(':checked')) {
				$("#"+id).prop("checked", false);
				$("#d_"+id).removeClass("pickActive");
			} else {
				if(isRadio=='true'){
					$("#pickContent div").removeClass("pickActive")
				}
				$("#"+id).prop("checked", true);
				$("#d_"+id).addClass("pickActive");
			}
			navigateText = "";
			$("#pickTip").html(navigateText);
			$("#pickTip").css("display","none");
		}
		function setPick() {
			var tagId = document.getElementById('tag').value;
			var radio = document.getElementById('radio').value;
			var length = document.getElementsByName('cid').length;
			var checkBox = "";
			for ( var i = 0; i < length; i++) {
				if (radio == 'true') {
					if (document.getElementsByName('cid')[i].checked == true) {
						document.getElementById(tagId).value = document.getElementsByName('cid')[i].value;
						break;
					}
				} else {
					if (document.getElementsByName('cid')[i].checked == true) {
						checkBox = checkBox
								+ document.getElementsByName('cid')[i].value
								+ ',';
					}
				}
			}
			if (radio == 'false')
				document.getElementById(tagId).value = checkBox;
			//回调函数
					var iCallBack=${iCallBack};
					var iCallBackParam='${iCallBackParam}';
					if(iCallBack){
						if (iCallBackParam) {
							iCallBack(iCallBackParam);
						} else {
							iCallBack();
						}
					}
			//关闭对话框
			iClose('lookUp');
		}
		document.onkeydown = function(event) {
			if (event.keyCode == 13) {
				setPick();
			}
		}
		if(hasLoad==0){
			$(document).ready(keyMonitor());
		}
		
	</script>
<div class="form-group">
	<blockquote>
		<p class="f12 fb pl10 tl C999">选择后回车即可快速确认，键盘输入可快速定位，退格可删除输入</p>
	</blockquote>
	</div>
<div id="pickContent">
	<c:if test="${radio=='true'}">
		<c:forEach items="${picks}" var="item">
			<c:if test="${item.value=='SEPARATOR'}">
				<div class="separator">${item.name}</div>
			</c:if>
			<c:if test="${item.value!='SEPARATOR'}">
				<div class="p5 tl cursor <c:if test="${def==item.value}">pickActive</c:if>" id="d_${item.id}"
					onclick="pickCheck('${item.id}','true');">
					<input id="${item.id}" type="radio"
						<c:if test="${def==item.value}">checked</c:if> disabled name="cid"
						value="${item.value}"> &nbsp;&nbsp; <span>${item.name}</span>
				</div>
			</c:if>
		</c:forEach>
	</c:if>
	<c:set var="def" value=",${def}"></c:set>
	<c:if test="${radio!='true'}">
		<c:forEach items="${picks}" var="item">
			<c:if test="${item.value=='SEPARATOR'}">
				<div class="separator">${item.name}</div>
			</c:if>
			<c:if test="${item.value!='SEPARATOR'}">
				<c:set var="value" value=",${item.value},"></c:set>
				<div class="p5 tl cursor <c:if test="${fn:contains(def,value)}">pickActive</c:if>" id="d_${item.id}"
				onclick="pickCheck('${item.id}');">
				<input id="${item.id}" type="checkbox" name="cid" disabled
					<c:if test="${fn:contains(def,value)}">checked</c:if>
					value="${item.value}"> &nbsp;&nbsp; 
					<span>${item.name}</span>
				<br>
			</div>
			</c:if>
		</c:forEach>
	</c:if>
</div>
<div class="fr w border-t ml10 tr pt10 form-group">
	<input type="hidden" id="radio" value="${radio}" /> <input
		type="hidden" id="tag" value="${tag}" />
	<button type="button" class="btn btn-info form-control"
		onclick="setPick()">选择</button>
</div>
