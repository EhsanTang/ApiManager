<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>必要|API登陆</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="resources/framework/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" type="text/css" />
<!-- base-min.css,admin.css应该发在bootstrap之后,覆盖部分bootstrap样式 -->
<link href="resources/css/base-min.css" rel="stylesheet" type="text/css" />
<link href="resources/css/login.css" rel="stylesheet" type="text/css" />
<link href="resources/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body class="BGEEE">
	<div class="container">
		<div class="row p0 m0 mt100  r5">
			<div class="visible-lg-block col-lg-4"></div>
			<div class="col-sm-12 col-lg-4 p50 shadow panel panel-default r5 bt-5">
					<div id="warnMessage" class="text-error mb15 mt10"></div>
					<blockquote>
  						<p class="pl10">必要|API接口管理系统</p>
					</blockquote>
					<input type="hidden" value="[ERROR][LOGINPAGE]"/>
					<form class="form-horizontal p15 mt10" role="form" method="post" action="login.do" onsubmit="return loginValidate();" id="login_form">
						<div class="form-group">
								<label  class="sr-only">账号</label>
							    <input type="text" class="form-control" id="userName" name="userName" value="${userName}" placeholder="用户名">
						</div>
						<div class="form-group mt20">
								<label  class="sr-only">密码</label>
								<input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="密码（6-20个字符）">
						</div>
						<div class="form-group mt30">
							<button type="submit" class="btn btn-block btn-success">登入<i class='iconfont mt-1 pl10'>&#xe601;</i></button>
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
