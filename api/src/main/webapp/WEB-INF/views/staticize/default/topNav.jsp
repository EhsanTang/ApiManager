<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<nav class="navbar navbar-inverse no-radius s-nav-bg-color s-nav-color mb0 z10">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!-- 使用项目LOGO作为静态化后的LOGO -->
				<a class="navbar-brand p0 pt10 ml10" href="${settings.DOMAIN}">
					<img class="avatar mt-5 ml0" src="${project.cover}" />
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse p0">
				<ul class="nav navbar-nav navbar-right ml20">
	                <li><a>${project.name}</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
<div class="def-bg s-nav-bg-color h120 mb20 title-bg-img w p0 rel of-h">
	<div class="circular circular-1"></div>
	<div class="circular circular-2"></div>
	<div class="circular circular-3"></div>
	<div class="circular circular-4"></div>
	<div class="circular circular-5"></div>
	<div class="circular circular-6"></div>

		<div class="container p0">
			<div class="row p0 m0">
				<div class="col-xs-12">
					<div class="pt30 CFFF"><h3>站内导航</h3></div>
					<ul class="nls m0 p0 f12 ACFFF fl mt10 ACFFF">
		 			 <li class="fl p0"><a href="${settings.DOMAIN}" class="ACFFF">首页</a></li>
		 			 <li class="fl p0">&nbsp;>&nbsp; </li>
		 			 <li class="fl p0"><a href="${module.id}-articleList--1.html" class="ACFFF">${module.name}</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>