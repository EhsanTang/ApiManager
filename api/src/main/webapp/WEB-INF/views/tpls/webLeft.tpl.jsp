<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="col-xs-3 col-sm-2 sidebar BGEEE p20 pt0 pb0 m0 min-h600" ng-controller="menuCtrl">
				<div class="panel-group" id="accordion" role="tablist"
					aria-multiselectable="true">
					<div class="panel panel-info no-radius b0 mt0" ng-repeat="item in menus">
						<div class="panel-heading BGBBB no-radius" data-parent="#accordion" ng-if="item.menuInfo.type=='FRONT'">
							<div class="a cursor" data-toggle="collapse" data-parent="#accordion" href="#panel{{item.menuInfo.menuId}}">
								<div class="f14 mt-3 fl mr10" ng-bind-html="item.menuInfo.iconRemark|trustHtml">
								</div> <span class="hidden-xs">{{item.menuInfo.menuName}}</span> <span
								class="badge pull-right">{{item.subMenuInfo.length}}</span>
							</div>
						</div>
						<div id="panel{{item.menuInfo.menuId}}" class="panel-collapse BGEEE collapse in" ng-if="item.menuInfo.type=='FRONT'">
							<div class="panel-body b0 p0" ng-repeat="subItem in item.subMenuInfo">
								<a class="menu-a" target="_self" ng-href="{{subItem.menuUrl}}">{{subItem.menuName}}</a>
							</div>
						</div>
					</div>
					
					<div class="panel panel-info no-radius b0 mt0">
						<div class="panel-heading BGBBB no-radius" data-parent="#accordion">
							<a data-toggle="collapse" data-parent="#accordion" href="#panel3">
								<div class="f14 mt-3 fl mr10">
								</div> <span class="hidden-xs">&nbsp;</span>
							</a>
						</div>
					</div>

				</div>
			</div>