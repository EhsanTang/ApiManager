<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
	body {font-family: SimSun;}
	pre {
	    border: 0px;
	    color: #595959;
	    font-weight: 100;
	    line-height: 24px;
	    font-size: 12px;
	}
	td{padding:5px;}
	tr{background:#F0F0F0;}
	</style>
</head>
<body screen_capture_injected="true" ryt11773="1">
		<div style="padding-left:4px;padding-right:4px; background:#fff; 
		color:#B768A5;font-size:16px;text-align:right;">
			CrapApi接口管理系统
		</div>
		<div style="height:2px;width:100%;background:${MAIN_COLOR};margin-bottom:20px;"></div>
		<br/>
		<br/>
		
		<div style="background:#F0F0F0;padding:6px;font-size:18px;">${model.interfaceName}——【${model.moduleName}】</div>
		<div>
			<br/>
			<h3>1 功能说明</h3>
			${model.remark}
			<br/>
			<br/>
			<h3>2 URL</h3>
			${model.moduleUrl}${model.url}
			<br/>
			<br/>
			<h3>3 HTTP请求方式</h3>
			${model.method}
			<br/>
			<br/>
			<h3>4 请求头说明</h3>
			<table style="width:100%;">
				<tr style="background:${MAIN_COLOR};color:#fff;">
					<td>名称</td>
					<td>是否必须</td>
					<td>类型</td>
					<td>默认值</td>
					<td style="width:260px;">备注</td>
				</tr>
				<c:forEach var="v" items="${requestScope.headers}"> 
				<tr>
					<td>${v.name}</td>
					<td>${v.necessary}</td>
					<td>${v.type}</td>
					<td>${v.def}</td>
					<td>${v.remark}</td>
				</tr>
				</c:forEach>
			</table>
			<br/>
			<br/>
			<h3>5 输入参数说明<c:if test="${requestScope.customParams!=null}">(自定义参数)</c:if></h3>
				<c:if test="${requestScope.formParams!=null}">
					<table style="width:100%;">
						<tr style="background:${MAIN_COLOR};color:#fff;">
							<td>名称</td>
							<td>是否必须</td>
							<td>类型</td>
							<td>默认值</td>
							<td style="width:260px;">备注</td>
						</tr>
						
						<c:forEach var="v" items="${requestScope.formParams}"> 
						
						<tr>
							<td>${v.name}</td>
							<td>${v.necessary}</td>
							<td>${v.type}</td>
							<td>${v.def}</td>
							<td>${v.remark}</td>
						</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${requestScope.customParams!=null}">
					${requestScope.customParams}
				</c:if>
			<br/>
			<br/>
			<h3>6 请求示例</h3>
			<div style="background:#F0F0F0; padding:10px;">
			<pre style="font-family: SimSun;">${model.requestExam}</pre>
			</div>
			<br/>
			<br/>
			<h3>7 返回参数说明</h3>
			<table style="width:100%;">
				<tr style="background:${MAIN_COLOR};color:#fff;">
					<td>名称</td>
					<td>类型</td>
					<td>备注</td>
				</tr>
				<c:forEach var="v" items="${requestScope.responseParam}"> 
				<tr>
					<td>${v.name}</td>
					<td>${v.type}</td>
					<td>${v.remark}</td>
				</tr>
				</c:forEach>
			</table>
			<br/>
			<br/>
			<h3>8 正确返回示例</h3>
			<div style="background:#F0F0F0; padding:10px;">
			<pre>${model.trueExam}</pre>
			</div>
			<br/>
			<br/>
			<h3>9 错误返回示例</h3>
			<div style="background:#F0F0F0; padding:10px;">
			<pre>${model.falseExam}</pre>
			</div>
			<br/>
			<br/>
			<h3>10 错误码</h3>
			<table style="width:100%;">
				<tr style="background:${MAIN_COLOR};color:#fff;">
					<td>Code</td>
					<td>Msg</td>
				</tr>
				<c:forEach var="v" items="${requestScope.errors}"> 
				<tr>
					<td>${v.errorCode}</td>
					<td>${v.errorMsg}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		
</body>
</html>