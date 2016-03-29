<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input type="hidden" id="sessionAuth" value="${sessionAdminAuthor}">
<input type="hidden" id="sessionRoleIds" value="${sessionAdminRoleIds}">
<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand pt10" href="web.do" target="_blank">
					<img class="h30" src="resources/images/logo.png" />
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right mr20">
						<li><a><i class="iconfont f14 mt-5">&#xe603;</i>&nbsp;&nbsp;${sessionAdminName}</a></li>
						<li><a href="loginOut.do"><i class="iconfont f16 mt-5">&#xe609;</i>&nbsp;&nbsp;注销</a></li>
				</ul>
			</div>
		</div>
	</nav>