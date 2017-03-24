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
<body class="BG_COLOR">
	<!-- top navbar -->
	<%@ include  file="topNav.jsp"%>
	<!-- Top Search-->
	<%@ include  file="topSearch.jsp"%>
	
	<div class="container p0 mt20">
		<div class="row min-h p0 m0">
			<!-- leftMenu -->
			<%@ include  file="left.jsp"%>
			<div class="col-xs-12 col-md-9 p0 BG_COLOR m0 pt0 ${module.id}">
				<div class="BGFFF min-h500 p20">
					<div class="col-xs-12 f14 pl0 mb10 pb5">
						<div class="fl mt2">文章筛选：</div>
						<c:forEach var="f" items="${categoryDtos}" varStatus="status"> 
							<a class="dis fl b1 p3 pl10 pr10 r12 mr10 f12 fn no_unl cursor C999 C${f.md5Category}" href="list-${f.md5Category}-1.html">${f.category}</a>
						</c:forEach>
					</div>
					<div class="cb"></div>
					
					<c:forEach var="f" items="${articleList}" varStatus="status">
						<div class="dashed-t">
							<a href="${f.id}.html" class="p10 pl0 f16 fb dis w C000 no_unl">${f.name}</a>
							<div class ="f14 C555">${f.brief}</div>
							<div class="tr C999 f12 p10"><span class="C999 pl20">${f.createTime}</span></div>
						</div>
					</c:forEach>
					
					<nav>
					  <ul class="pager mt20 fr">
	　　					<c:if test="${page.currentPage>1}">
							 <li class="cursor">
					    		<a href="list-${md5Category}-${page.currentPage-1}.html"><span aria-hidden="true">&larr;</span> 上一页</a>
					   		 </li>
						</c:if>
						<c:if test="${page.currentPage<=1}">
							 <li ng-if="" class="disabled cursor">
					    	<a href="list-${md5Category}-${page.currentPage-1}.html"><span aria-hidden="true">&larr;</span> 上一页</a>
					    </li>
						</c:if>
						<c:if test="${page.currentPage<page.totalPage}">
							 <li class="cursor">
					    	<a href="list-${md5Category}-${page.currentPage+1}.html">下一页 <span aria-hidden="true">&rarr;</span></a>
					    </li>
						</c:if>
						<c:if test="${page.currentPage>=page.totalPage}">
						 <li class="disabled cursor">
					    	<a href="list-${md5Category}-${page.currentPage+1}.html">下一页 <span aria-hidden="true">&rarr;</span></a>
					    </li>
						</c:if>
				 	 </ul>
				 </nav>
				<div class="fr C999 mt20 mr10">Total:${page.allRow}&nbsp; ${page.currentPage}/${page.totalPage}</div>
				<div class="cb"></div>
				
				
				</div>
			</div>
			
		</div>
	</div>

	<!-- footer -->
	<%@ include  file="footer.jsp"%>
</body>
</html>
