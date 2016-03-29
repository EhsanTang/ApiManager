<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Result page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
	<div>
		<c:forEach items="${list}" var="item">	
			<div>Url:${item.url }</div>
			<div>Method:${item.method }</div>
			<div>Param:${item.param }</div>
			<div>RequestExam:${item.requestExam }</div>
			<div>ResponseParam:${item.responseParam }</div>
			<div>ErrorList:${item.errorList }</div>
			<div>TrueExam:${item.trueExam }</div>
			<div>FalseExam:${item.falseExam }</div>
			<div>Status:${item.status }</div>
			<div>Module:${item.module }</div>
		</c:forEach>
	</div>
</body>
</html>
