<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<nav class="navbar navbar-inverse no-radius FOOTER_BG_COLOR FOOTER_COLOR mb0 z10">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand p0 pt10" href="${settings.DOMAIN}">
					<img class="h30" src="<%=settings.get("LOGO").startsWith("http")? settings.get("LOGO"):basePath + settings.get("LOGO")%>" />
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse p0">
				<ul class="nav navbar-nav navbar-right ml20">
	               	<li>
	                	<a ng-href="#/project/list/false/NULL" class="menu-a cursor" onclick="selectButton(this,'menu-a')">推荐项目</a>
	                </li>
	                	<li>
	                	<a href="http://git.oschina.net/CrapApi/CrapApi" target="_blank" class="menu-a cursor" onclick="selectButton(this,'menu-a')">查看源码</a>
	                </li>
				</ul>
			</div>
		</div>
	</nav>