<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<div class="col-xs-12 col-md-3 p0 m0">
				<div class="r2 BGFFF p20 C555 mr15 col-md-mr0">
					<table>
						<tr>
							<td class="fb f18 adorn-color w20"><i class="iconfont">&#xe6b4;</i></td>
							<td><a class="fl dis C000 f16 mt2 ml10">${project.name}</a></td>
						</tr>
					</table>

					<div class="cb"></div>
					<div class="f12 C999 lh20 mt10">${project.remark}</div>
				
				</div>
				
				<div class="mb0 r2 f14 mr15 mt20 p20 BGFFF mb20 col-md-mr0">
					<%if( request.getAttribute("needStaticizes").toString().split(",").length == 2){ %>
							<ul class="panel-body b0 p0 ml10">
							<c:if test="${needStaticizes.indexOf(',error,')>=0}">
								<li><a target="_self" class="p5 cursor CerrorList" href="errorList-1.html">${f.name}</a></li>
							</c:if>
							<c:forEach var="f" items="${moduleList}" varStatus="status"> 
								<c:if test="${needStaticizes.indexOf(',interface,')>=0}">
									<li><a target="_self" class="p5 cursor C${f.id}_interface" href="${f.id}-interfaceList-1.html">${f.name}</a></li>
								</c:if>
								<c:if test="${needStaticizes.indexOf(',article,')>=0}">
									<li><a target="_self" class="p5 cursor C${f.id}_article"" href="${f.id}-articleList--1.html">${f.name}</a></li>
								</c:if>
								<c:if test="${needStaticizes.indexOf(',dictionary,')>=0}">
									<li><a target="_self" class="p5 cursor C${f.id}_dictionary" href="${f.id}-dictionaryList-1.html">${f.name}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					<%}else{ %>
						<c:if test="${needStaticizes.indexOf(',error,')>=0}">
							<div class="ml10 pl10 adorn-bl-3 pl10">
								<a class="C555 cursor CerrorList" href="errorList-1.html">错误码</a>
							</div>
						</c:if>
						<c:forEach var="f" items="${moduleList}" varStatus="status"> 
						<div class="no-radius b0 mt0">
							<div class="no-radius p10" data-parent="#accordion">
								<div class="a cursor adorn-bl-3 pl10 no_unl " data-toggle="collapse" data-parent="#accordion" href="#panel${f.id}">
									${f.name}
								</div>
							</div>
							<c:if test="${status.index==0}">
								<div id="panel${f.id}" class="panel-collapse BGFFF collapse in">
							</c:if>
							<c:if test="${status.index!=0}">
								<div id="panel${f.id}" class="panel-collapse BGFFF collapse">
							</c:if>
								<div class="panel-body b0 p0">
									<c:if test="${needStaticizes.indexOf(',interface,')>=0}">
										<a class="p5 pl30 cursor C${f.id}_interface" href="${f.id}-interfaceList-1.html">接口列表</a>
									</c:if>
									<c:if test="${needStaticizes.indexOf(',article,')>=0}">
										<a class="p5 pl30 cursor C${f.id}_article" href="${f.id}-articleList--1.html">文档</a>
									</c:if>
									<c:if test="${needStaticizes.indexOf(',dictionary,')>=0}">
										<a class="p5 pl30 cursor C${f.id}_dictionary" href="${f.id}-dictionaryList-1.html">数据库表</a>
									</c:if>
								</div>
							</div>
						</div>
						</c:forEach>
					<%} %>
					
				</div>
			</div>