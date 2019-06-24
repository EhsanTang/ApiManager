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
					<div class="col-xs-12 f14 pl10 mb10 adorn-bl-3" id="error-content">
						错误码列表
					</div>
					<c:if test="${errorList.size() == 0}">
						<div class="col-xs-12 tc mt10 mb10 C999">
									<i class="iconfont f20 mt-5 mr10">&#xe69c;</i>该模块下没有错误码
						</div>
					</c:if>
					
						
					<c:if test="${errorList.size() > 0}">	
						<div class="col-xs-12 p0" >
							<table class="table f12 table-hover">
								<thead>
									<tr class="main-bg f14 CFFF">
										<th class="tc w100">Code</th>
										<th class="tc">Msg</th>
										<th class="tc w150">项目</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="f" items="${errorList}" varStatus="status">
										<tr>
											<td class="tc p12">${f.errorCode}</td>
											<td class="tc p12">${f.errorMsg}</td>
											<td class="tc p12">${f.projectName}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<%@ include file="page.jsp"%>
				
				</div>
			</div>
			
		</div>
	</div>

	<!-- footer -->
	<%@ include  file="footer.jsp"%>
	<script type="text/javascript">
		if($(window).width() < 1000){
			scrollToId('error-content');
		}
	</script>
</body>
</html>
