<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="col-xs-3 col-sm-2 sidebar BGBBB p20 pt0 pb0 h mah ofy-a ofx-h" ng-controller="lefMenuCtrl">
				<div class="panel-group" id="accordion" role="tablist"
					aria-multiselectable="true">
					<c:set var="sessionAdminRoleIds" value=",${sessionAdminRoleIds},"></c:set>
					<c:set var="mySuper" value=",super,"></c:set>
					<c:if test="${fn:contains(sessionAdminRoleIds,mySuper)}">
						<!-- 该菜单只有超级管理员才能查看 -->
						<div class="panel panel-info no-radius b0 mt0">
							<div class="panel-heading BG999 no-radius" data-parent="#accordion">
								<div class="a cursor" data-toggle="collapse" data-parent="#accordion" href="#panel1">
									<div class="f14 mt-3 fl mr10">
										<i class="iconfont">&#xe60f;</i>
									</div> <span class="hidden-xs">系统管理</span>
								</div>
							</div>
							<div id="panel1" class="panel-collapse BGEEE collapse in">
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/menu/list/0/FRONT/一级菜单">菜单管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/interface/list/0/无">项目&模块&接口管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/user/list">用户管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/role/list">角色管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/error/list">错误码管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/setting/list/null">系统设置管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/webPage/list/DICTIONARY">数据字典管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/webPage/list/PAGE">页面管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/webPage/list/ARTICLE">文章管理</a>
								</div>
							</div>
						</div>
					</c:if>
					
					<div class="panel panel-info no-radius b0 mt0" ng-repeat="item in menus">
						<div class="panel-heading BG999 no-radius" data-parent="#accordion" ng-if="canSeeMenu(item.menu.menuId,item.menu.type);">
							<div class="a cursor" data-toggle="collapse" data-parent="#accordion" href="#panel{{item.menu.menuId}}">
								<div class="f14 mt-3 fl mr10" ng-bind-html="item.menu.iconRemark|trustHtml">
								</div> 
									<span class="hidden-xs" ng-bind="item.menu.menuName"></span> 
								</div>
						</div>
						<div id="panel{{item.menu.menuId}}" class="panel-collapse BGEEE collapse" ng-if="canSeeMenu(item.menu.menuId,item.menu.type);">
							<div class="panel-body b0 p0" ng-repeat="subItem in item.subMenu">
								<a class="menu-a" target="_self" ng-href="{{subItem.menuUrl}}" ng-bind="subItem.menuName"></a>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info no-radius b0 mt0">
						<div class="panel-heading BG999 no-radius" data-parent="#accordion">
							<a data-toggle="collapse" data-parent="#accordion" href="#panel3">
								<div class="f14 mt-3 fl mr10">
								</div> <span class="hidden-xs">&nbsp;</span>
							</a>
						</div>
					</div>

				</div>
			</div>