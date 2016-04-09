<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse no-radius">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand pt10" href="#">
					<img class="h30" src="${LOGO}" />
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right mr20" ng-controller="lefMenuCtrl">
					<li ng-repeat="item in menus" ng-if="item.menu.type=='TOP'&&item.subMenu.length==0">
	                	<a ng-href="{{item.menu.menuUrl}}" ng-bind="item.menu.menuName"></a>
	                </li>
	                <li class="dropdown" ng-repeat="item in menus" ng-if="item.menu.type=='TOP'&&item.subMenu.length>0">
	                	 <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">{{item.menu.menuName}} <span class="caret"></span></a>
	                 	 <ul class="dropdown-menu">
	                    	<li ng-repeat="subItem in item.subMenu">
	                    		<a ng-href="{{subItem.menuUrl}}" ng-bind="subItem.menuName"></a>
	                    	</li>
	                  </ul>
	                </li>
					<c:if test="${sessionAdminName==null}">
						<li><a href="index.do"><i class="iconfont f16 mt-5">&#xe609;</i>&nbsp;&nbsp;登录</a></li>
					</c:if>
					<c:if test="${sessionAdminName!=null}">
						<li><a href="index.do" target="_blank"><i class="iconfont f14 mt-5">&#xe603;</i>&nbsp;&nbsp;${sessionAdminName}</a></li>
						<li><a href="loginOut.do"><i class="iconfont f16 mt-5">&#xe609;</i>&nbsp;&nbsp;注销</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>