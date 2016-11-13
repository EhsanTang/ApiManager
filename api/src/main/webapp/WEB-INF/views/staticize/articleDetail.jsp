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
<body class="BG_COLOR">
	<!-- top navbar -->
	<%@ include  file="topNav.jsp"%>
	<!-- Top Search-->
	<%@ include  file="topSearch.jsp"%>
	
	<div class="container p0 mt20">
		<div class="row min-h p0 m0">
			<!-- leftMenu -->
			<%@ include  file="left.jsp"%>
			<div class="col-xs-9 p0 BG_COLOR m0 pt0">
				<div class="BGFFF min-h500 p20">
					<div class="f14 mt10 mb20 b1 bl-5 p0 r3 no-right-radius BCEEE">
						<pre class="b0 p10 BGFFF">${article.brief}</pre>
					</div>	
					<div class="f14 mt-3 imgCenter">${article.content}</div>
				</div>
			</div>
			
		</div>
	</div>
	<!-- footer -->
	<%@ include  file="footer.jsp"%>
</body>
</html>
