<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="col-xs-2 col-sm-2 sidebar BGEEE p20 pt0 pb0 m0 min-h600" ng-controller="lefMenuCtrl">
				<div class="panel-group" id="accordion" role="tablist"
					aria-multiselectable="true">
					<div class="panel panel-info no-radius b0 mt0" ng-repeat="item in menus">
						<div class="panel-heading BGBBB no-radius" data-parent="#accordion" ng-if="item.menu.type=='FRONT'">
							<div class="a cursor" data-toggle="collapse" data-parent="#accordion" href="#panel{{item.menu.menuId}}">
								<div class="f14 mt-3 fl mr10" ng-bind-html="item.menu.iconRemark|trustHtml">
								</div>
								 	<span class="hidden-xs" ng-bind="item.menu.menuName"></span> 
							</div>
						</div>
						<div id="panel{{item.menu.menuId}}" class="panel-collapse BGEEE collapse in" ng-if="item.menu.type=='FRONT'">
							<div class="panel-body b0 p0" ng-repeat="subItem in item.subMenu">
								<a class="menu-a" target="_self" ng-href="{{subItem.menuUrl}}" onclick="selectButton(this,'menu-a')" ng-bind="subItem.menuName"></a>
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