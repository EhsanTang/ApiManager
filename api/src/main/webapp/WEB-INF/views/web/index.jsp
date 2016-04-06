<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
<meta charset="UTF-8">
<title>${TITLE}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="keywords"
	content="CrapApi,api,crap,接口管理,应用接口管理,开源接口管理,开源api接口管理" />
<meta name="description"
	content="CrapApi|一个由anjularjs+bootstrap+springMVC搭建的免费开源的API接口管理系统（应用接口管理系统）" />
<link href="${ICON}" rel="shortcut icon" type="image/x-icon" />
<link href="${ICON}" rel="icon" type="image/x-icon" />
<link href="${ICON}" rel="shortcut" type="image/x-icon" />
<link href="resources/framework/bootstrap-3.0.0/css/bootstrap.css"
	rel="stylesheet" type="text/css" />
<link
	href="resources/framework/bootstrap-3.0.0/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" type="text/css" />
<!-- base-min.css,admin.css应该发在bootstrap之后,覆盖部分bootstrap样式 -->
<link href="resources/css/base.css" rel="stylesheet" type="text/css" />
<link href="resources/css/crapApi.css" rel="stylesheet" type="text/css" />
<link href="resources/css/global.css" rel="stylesheet" type="text/css" />
<link
	href="resources/framework/font-awesome-4.5.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/framework/textAngular-1.5.0/textAngular.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
body, h1, h2, h3, h4, h5, h6, hr, blockquote, dl, dt, dd, ul, ol, li,
	pre, form, fieldset, legend, button, input, textarea, th, td {
	font-family: Tahoma, "SimSun" !important;
}
</style>
</head>
<body class="BGFFF">
	<%@include file="../tpls/webTop.tpl.jsp"%>
	<div class="container p0">
		<div class="row min-h p0 m0">
			<%@include file="../tpls/webLeft.tpl.jsp"%>
			<div class="col-xs-9 col-sm-10 p0 BGFFF m0 p10 pt0 r5">
				<div class="col-xs-12 f16 fb p20 pt0 mb5" ng-if="error">
					<button type="button" class="btn btn-danger btn-xs break-word">
						<span class="glyphicon glyphicon-remove"></span> <span
							ng-bind="error" onclick="propUpPsswordDiv(this)"></span>
					</button>
				</div>
				<div ui-view="main"></div>
			</div>
		</div>
	</div>
	<%@include file="../tpls/webBottom.tpl.jsp"%>
	<script src="resources/framework/jquery-1.9.1.min.js"></script>
	<script src="resources/framework/jquery.cookie.js"></script>
	<script src="resources/framework/jquery.base64.js"></script>

	<script src="resources/framework/angular-1.3.0.14/angular.js"></script>
	<script
		src="resources/framework/angular-1.3.0.14/angular-animate.min.js"></script>
	<script src="resources/framework/angular-ui-router-0.2.15.js"></script>
	<script src="resources/js/app.js?v=2010"></script>
	<script src="resources/js/services.js"></script>
	<script src="resources/js/router.js"></script>
	<script src="resources/js/animations.js"></script>
	<script src="resources/js/controllers.js?v=2010"></script>
	<script src="resources/js/webControllers.js?v=2010"></script>
	<script src="resources/js/filters.js"></script>

	<script
		src='resources/framework/textAngular-1.5.0/textAngular-rangy.min.js'></script>
	<script
		src='resources/framework/textAngular-1.5.0/textAngular-sanitize.min.js'></script>
	<script src='resources/framework/textAngular-1.5.0/textAngular.min.js'></script>

	<script src="resources/framework/bootstrap-3.0.0/js/bootstrap.js"></script>
	<script
		src="resources/framework/bootstrap-3.0.0/js/bootstrap-datetimepicker.min.js"></script>
	<script
		src="resources/framework/bootstrap-3.0.0/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="resources/js/validateAndRefresh.js?v=200"></script>
	<script src="resources/js/core.js?v=200"></script>
	<script src="resources/js/global.js?v=200"></script>
	<script src="resources/js/crapApi.js?v=200"></script>

	<!-- 提示窗口 -->
	<div id="lookUp" class="look-up shadow">
		<div id="lookUpContent" class="look-up-content f12 tc">加载中....</div>
		<i class="iconfont i-close" onclick="iClose('lookUp')">&#xe615;</i>
	</div>
	<div id="fade"></div>
	<div id="passwordDiv" class="look-up shadow">
		<div id="lookUpContent" class="look-up-content f12 tc p30 BGFFF">
			<blockquote style="margin-left: -15px;">
				<p>&nbsp;私密项目，请输入访问密码</p>
			</blockquote>
			<div class="input-wrapper mt20 bt1">
				<input id="password" type="text" placeholder="请输入访问密码">
			</div>
			<c:if test="${applicationScope.VISITCODE=='true'}">
				<div class="input-wrapper bt0">
					<input type="text" id="visitCode" placeholder="图形验证码">
					<div class="imgCode" title="看不清楚？换一张" alt="看不清楚？换一张"
						data-toggle="tooltip">
						<img id="imgCode" width="80" height="30"
							onclick="changeimg('imgCode','verificationCode')"
							src="getImgCode.do">
					</div>
				</div>
			</c:if>
			<button type="button" class="btn btn btn-purple btn-sm fr mt10"
				onclick="$('#fushPage').click();closePasswordDiv();">确认</button>

		</div>
		<i class="iconfont i-close" onclick="closePasswordDiv()">&#xe615;</i>
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
