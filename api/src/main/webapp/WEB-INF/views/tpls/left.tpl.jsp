<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="col-xs-3 col-sm-2 sidebar BGBBB p20 pt0 pb0 h mah ofy-a ofx-h" ng-controller="menuCtrl">
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
									</div> <span class="hidden-xs">系统管理</span> <span
									class="badge pull-right">5</span>
								</div>
							</div>
							<div id="panel1" class="panel-collapse BGEEE collapse in">
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/menuInfo/list/0/无">菜单管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/interfaceInfo/list/0/无">模块&接口管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/userInfo/list">用户管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/roleInfo/list">角色管理</a>
								</div>
								<div class="panel-body b0 p0">
									<a class="menu-a" target="_self" href="index.do#/errorInfo/list">错误码管理</a>
								</div>
							</div>
						</div>
					</c:if>
					
					<div class="panel panel-info no-radius b0 mt0" ng-repeat="item in menus">
						<div class="panel-heading BG999 no-radius" data-parent="#accordion" ng-if="canSeeMenu(item.menuInfo.menuId,item.menuInfo.type);">
							<div class="a cursor" data-toggle="collapse" data-parent="#accordion" href="#panel{{item.menuInfo.menuId}}">
								<div class="f14 mt-3 fl mr10" ng-bind-html="item.menuInfo.iconRemark|trustHtml">
								</div> <span class="hidden-xs">{{item.menuInfo.menuName}}</span> <span
								class="badge pull-right">{{item.subMenuInfo.length}}</span>
							</div>
						</div>
						<div id="panel{{item.menuInfo.menuId}}" class="panel-collapse BGEEE collapse" ng-if="canSeeMenu(item.menuInfo.menuId,item.menuInfo.type);">
							<div class="panel-body b0 p0" ng-repeat="subItem in item.subMenuInfo">
								<a class="menu-a" target="_self" ng-href="{{subItem.menuUrl}}">{{subItem.menuName}}</a>
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