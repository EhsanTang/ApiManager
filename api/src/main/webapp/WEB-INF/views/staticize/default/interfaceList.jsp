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
			<div class="col-xs-12 col-md-9 p0 s-bg-color m0 pt0">
				<div class="BGFFF min-h500 p20">
					<div class="col-xs-12 f16 fb pl10 mb10 adorn-bl-3" id="interface-content">
						接口列表
					</div>
					<div class="cb"></div>
					<c:if test="${interfaceList.size() == 0}">
						<div class="col-xs-12 tc mt10 mb10 C999">
									<i class="iconfont f20 mt-5 mr10">&#xe69c;</i>该模块下没有接口
						</div>
					</c:if>
					
						
					<c:forEach var="f" items="${interfaceList}" varStatus="status">
						<div class="dashed-t">
							<a href="${f.id}.html" class="p10 pl0 f16 fb dis w C000 no_unl">${f.interfaceName}</a>
							<div class ="f14 C555">${f.remarkNoHtml}</div>
							<div class="tr C999 f12 p10"><span class="C999 pl20">${f.createTimeStr}</span></div>
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
			scrollToId('interface-content');
		}
	</script>
</body>
</html>
