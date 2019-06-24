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
</head>
<body class="s-bg-color">
	<!-- top navbar -->
	<%@ include  file="topNav.jsp"%>
	
	<div class="container p0 mt20">
		<div class="row min-h p0 m0">
			<!-- leftMenu -->
			<%@ include  file="left.jsp"%>
			<div  id="article-content" class="col-xs-12 col-md-9 p0 s-bg-color m0 pt0">
				<div class="BGFFF min-h500 p20">
					<div class="p3 pl10 pr10 mr10 f16 fb  mt10 mb10 adorn-bl-3">${article.name}</div>
					<div class="f14 mb10 pl20 C999 f12">
						${article.brief}
					</div>	
					<div class="f14 dashed-t pt20 imgCenter">${article.content}</div>
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
