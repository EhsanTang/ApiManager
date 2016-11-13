<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
		<div class="container-fluid m0 mt30 def-bg FOOTER_BG_COLOR FOOTER_COLOR">
		<div class="container p0">
			<div class="col-xs-12 f12 p0 mb5 tl mt50 mb50">
				©crap.cn&nbsp;版本号 [V7]
				<ul class="dis-in-tab">
					<li class="dis-in-tab mr20"><a target="_blank"
						href="http://api.crap.cn/index.do#/webPage/list/ARTICLE/帮助文档">帮助文档</a>
					</li>
					<li class="dis-in-tab mr20"><a target="_blank"
						href="https://github.com/EhsanTang/CrapApi">源码:GitHub</a> <span
						class="bg_line"></span></li>
					<li class="dis-in-tab mr20"><a target="_blank"
						href="https://git.oschina.net/CrapApi/CrapApi">源码:码云</a> <span
						class="bg_line"></span></li>
				</ul>
				
				<div class="mt20">
					友情链接：
					<ul class="dis-in-tab p0">
						<c:forEach var="f" items="${menuList}" varStatus="status"> 
							<c:if test="${f.menu.type=='FRIEND'}">
								<li class="dis-in-tab pr20">
									<a target="_blank" href="${f.menu.menuUrl}" >${f.menu.menuName}</a>
								</li>
							</c:if>
						</c:forEach>
						
					</ul>
				</div>

			</div>
		</div>
	</div>
	
	<div id="go_top" onclick="goTop()" style="padding-top: 9px; padding-left: 13px;"
		class="cursor">
		<i class="iconfont f20" style="color: #FFF;">&#xe617;</i>
	</div>
	<script src="<%=basePath %>resources/framework/jquery-1.9.1.min.js"></script>
	<script src="<%=basePath %>resources/framework/bootstrap-3.0.0/js/bootstrap.js"></script>
	<script src="<%=basePath %>resources/js/core.js?v=200"></script>
	<script src="<%=basePath %>resources/js/global.js?v=200"></script>
	<script src="<%=basePath %>resources/js/crapApi.js?v=200"></script>