<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<div class="col-xs-3 p0 m0">
				<div class="r2 BGFFF p20 C555 mr15">
					<div class=" fb f18 adorn-color fl">
						<i class="iconfont">&#xe61f;</i>
					</div>
					<a class="fl dis C000 f16 mt2 ml10">${project.name}</a>
					<div class="cb"></div>
					<div class="f12 C999 lh20 mt10">${project.remark}</div>
				
				</div>
				
				<div class="mb0 r2 f14 mr15 mt20 p20 BGFFF">
					<ul class="panel-body b0 p0 ml10">
						<c:forEach var="f" items="${moduleList}" varStatus="status"> 
							<li><a target="_self" class="p5 cursor" href="../${f.id}/list.html">${f.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>