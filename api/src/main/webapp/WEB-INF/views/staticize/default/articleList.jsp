<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include  file="css.jsp"%>
	<style type="text/css"> 
		.C${md5Category}{color:#ffffff; border:0px; background-color:${settings.ADORN_COLOR}!important;}
	</style>
	
</head>
<body class="s-bg-color">
	<!-- top navbar -->
	<%@ include  file="topNav.jsp"%>
	
	<div class="container p0 mt20">
		<div class="row min-h p0 m0">
			<!-- leftMenu -->
			<%@ include  file="left.jsp"%>
			<div class="col-xs-12 col-md-9 p0 s-bg-color m0 pt0">
				<div class="BGFFF min-h500 p20">
					<c:if test="${categoryDtos!=null}">
						<div class="col-xs-12 f14 pl0 mb10 pb5" id="article-content">
							<div class="fl mt2">文档筛选：</div>
							<c:forEach var="f" items="${categoryDtos}" varStatus="status"> 
								<a class="dis fl b1 p3 pl10 pr10 r12 mr10 f12 fn no_unl cursor C999 C${f.md5Category}" href="${module.id}-articleList-${f.md5Category}-1.html">${f.category}</a>
							</c:forEach>
						</div>
						<div class="cb"></div>
					</c:if>
					<c:if test="${categoryDtos==null}">
						<div class="col-xs-12 f16 pl10 fb mb10 adorn-bl-3" id="article-content">
							数据库表列表
						</div>
						<div class="cb"></div>
					</c:if>
					<c:if test="${articleList.size() == 0}">
						<div class="col-xs-12 tc mt10 mb10 C999">
									<i class="iconfont f20 mt-5 mr10">&#xe69c;</i>该模块下数据
						</div>
					</c:if>
					
					<c:forEach var="f" items="${articleList}" varStatus="status">
						<div class="dashed-t">
							<a href="${f.id}.html" class="p10 pl0 f16 fb dis w C000 no_unl">${f.name}</a>
							<div class ="f14 C555">${f.brief}</div>
							<div class="tr C999 f12 p10"><span class="C999 pl20">${f.createTime}</span></div>
						</div>
					</c:forEach>
					
					<%@ include file="page.jsp"%>
				
				</div>
			</div>
			
		</div>
	</div>

	<!-- footer -->
	<%@ include  file="footer.jsp"%>
	<script type="text/javascript">
		if($(window).width() < 1000){
			scrollToId('article-content');
		}
	</script>
</body>
</html>
