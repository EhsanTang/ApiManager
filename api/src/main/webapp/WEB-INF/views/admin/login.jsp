<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link href="${ICON}" rel="shortcut icon" type="image/x-icon" />
<link href="${ICON}" rel="icon" type="image/x-icon" />
<link href="${ICON}" rel="shortcut" type="image/x-icon" />
<title>${TITLE}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="resources/framework/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" type="text/css" />
<!-- base-min.css,admin.css应该发在bootstrap之后,覆盖部分bootstrap样式 -->
<link href="resources/css/base.css" rel="stylesheet" type="text/css" />
<link href="resources/css/crapApi.css" rel="stylesheet" type="text/css" />
</head>
<body class="BGF5">
	<div class="container">
		<div class="row p0 m0 mt100  r5">
			<div class="visible-lg-block col-lg-4"></div>
			<div class="col-sm-12 col-lg-4 p50 shadow panel panel-default r5 bt-5">
					<div id="warnMessage" class="text-error mb15 mt10"></div>
					<blockquote>
  						<p class="pl10">${TITLE}</p>
					</blockquote>
					<input type="hidden" value="[ERROR][LOGINPAGE]"/>
					<form class="form-horizontal p15 mt10" role="form" method="post" action="login.do" onsubmit="return loginValidate();" id="login_form">
						<div class="input-wrapper">
							    <input type="text" class="" id="userName" name="userName" value="${userName}" placeholder="用户名" required>
						</div>
						<div class="input-wrapper">
								<input type="password"  id="userPassword" value="${password}" name="userPassword" placeholder="密码（不少于6位）" required>
						</div>
						<c:if test="${applicationScope.VERIFICATIONCODE=='true'}">
							<div class="input-wrapper">
								<input type="text" class="" id="verificationCode" name="verificationCode"  placeholder="图形验证码" required>
								
								<div class="imgCode" title="看不清楚？换一张" alt="看不清楚？换一张" data-toggle="tooltip">
									<img id="imgCode" width="80" height="30" onclick="changeimg('imgCode','verificationCode')"
  									 src="getImgCode.do">
								</div>
							</div>
						</c:if>
						<div class="form-group mt30">
							<button type="submit" class="btn btn-block btn-success">登入<i class='iconfont mt-1 pl10'>&#xe601;</i></button>
						</div>
						
						<div class="form-group mb0 f12 C555 tr" id="remberPwd">
							<div class="btn-group fr" data-toggle="buttons">
  								<label class="btn btn-default btn-xs <c:if test="${remberPwd!='NO'}">active</c:if>">
   			 						<input type="radio" name="remberPwd" value="YES" <c:if test="${remberPwd!='NO'}">checked</c:if>> YES
  								</label>
  								<label class="btn btn-default btn-xs <c:if test="${remberPwd=='NO'}">active</c:if>">
    								<input type="radio" name="remberPwd" value="NO" <c:if test="${remberPwd=='NO'}">checked</c:if>> NO
  								</label>
							</div>
							<div class="fr lab">记住密码?</div>
						</div>
					</form>
				</div>
				<div class="visible-lg-block col-lg-4"></div>
			</div>
		</div>
	<!-- JavaScript 放置在文档最后面可以使页面加载速度更快 -->
	<!-- 可选: 包含 jQuery 库 -->
	<script src="resources/framework/jquery-1.9.1.min.js"></script>
	<script src="resources/framework/bootstrap-3.0.0/js/bootstrap.js"></script>
    <script src="resources/js/validateAndRefresh.js?v=200"></script>
    <script src="resources/js/core.js?v=200"></script>
	<script src="resources/js/global.js?v=200"></script>
    <script type="text/javascript">
	    showMessage('warnMessage','${tipMessage}',true,3);
	</script>
		 
</body>
</html>
