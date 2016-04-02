<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
<meta charset="UTF-8">
<title>必要|API接口文档</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="http://static.biyao.com/pc/favicon.ico" rel="shortcut icon"
	type="image/x-icon" />
<link href="http://static.biyao.com/pc/favicon.ico" rel="icon"
	type="image/x-icon" />
<link href="resources/images/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<link href="resources/images/favicon.ico" rel="icon" type="image/x-icon" />
<link href="resources/framework/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="resources/framework/bootstrap-3.0.0/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
<link href="resources/framework/kediter/themes/default/default.css" rel="stylesheet" type="text/css" />
<!-- base-min.css,admin.css应该发在bootstrap之后,覆盖部分bootstrap样式 -->
<link href="resources/css/base.css" rel="stylesheet" type="text/css" />
<link href="resources/css/crapApi.css" rel="stylesheet" type="text/css" />
<link href="resources/css/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	body, h1, h2, h3, h4, h5, h6, hr, blockquote, dl, dt, dd, ul, ol, li, pre, form, fieldset, legend, button, input, textarea, th, td{
font-family: Tahoma,"SimSun"!important;	
}
</style>
</head>
<body class="BGFFF">
	<%@include file="../tpls/webTop.tpl.jsp"%>
	<div class="container p0">
		<div class="row min-h p0 m0">
				<%@include file="../tpls/webLeft.tpl.jsp"%>
				<div class="col-xs-9 col-sm-10 p0 BGFFF m0 p10 pt0 r5">
					<div ui-view="main"></div>
				</div>
		</div>
	</div>
	<%@include file="../tpls/webBottom.tpl.jsp"%>
	<script src="resources/framework/jquery-1.9.1.min.js"></script>
	<script src="resources/framework/jquery.cookie.js"></script>
	
	<script src="resources/framework/angular-1.3.0.14/angular.js"></script>
	<script src="resources/framework/angular-1.3.0.14/angular-animate.min.js"></script>
	<script src="resources/framework/angular-ui-router-0.2.15.js"></script>
	<script src="resources/js/app.js?v=2010"></script>
	<script src="resources/js/services.js"></script>
	<script src="resources/js/router.js"></script>
    <script src="resources/js/animations.js"></script>
    <script src="resources/js/controllers.js?v=2010"></script>
    <script src="resources/js/filters.js"></script>
    
    <script src="resources/framework/bootstrap-3.0.0/js/bootstrap.js"></script>
	<script src="resources/framework/bootstrap-3.0.0/js/bootstrap-datetimepicker.min.js"></script>
	<script src="resources/framework/bootstrap-3.0.0/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="resources/framework/kediter/kindeditor-min.js"></script>
	<script src="resources/framework/kediter/lang/zh_CN.js"></script>
    <script src="resources/js/validateAndRefresh.js?v=200"></script>
    <script src="resources/js/core.js?v=200"></script>
	<script src="resources/js/global.js?v=200"></script>
	<script src="resources/js/crapApi.js?v=200"></script>
	
	<!-- 提示窗口 -->
<div id="lookUp" class="look-up shadow">
	<div id="lookUpContent" class="look-up-content f12 tc">加载中....</div>
	<i class="iconfont i-close" onclick="iClose('lookUp')">&#xe615;</i>
</div>
<div id="float" class="folat">
		<div class="sk-wave">
        <div class="sk-rect sk-rect1"></div>
        <div class="sk-rect sk-rect2"></div>
        <div class="sk-rect sk-rect3"></div>
        <div class="sk-rect sk-rect4"></div>
        <div class="sk-rect sk-rect5"></div>
      </div>
</div>

</body>
</html>
