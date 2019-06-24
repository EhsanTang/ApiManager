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
					<table class="table table-bordered f12 C555 mb5">
					<tr class="main-bg CFFF">
						<th class="w200">字段</th>
						<th  class="w150">类型</th>
						<th  class="w50">空</th>
						<th  class="w150">默认</th>
						<th  class="w50">标识</th>
						<th  class="w200">注释</th>
					</tr>
						<c:forEach var="f" items="${dictionaryFields}" varStatus="status">
						<tr>
							<td>${f.name}</td>
							<td>${f.type}</td>
							<td>${f.notNull=='true'?'是':'否'}</td>
							<td>${f.def}</td>
							<td class="dic-bg-${f.flag}">${f.flag}</td>
							<td>${f.remark}</td>
						</tr>
						</c:forEach>
					</table>
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
	$(".dic-bg-primary").addClass("bg-danger");
	$(".dic-bg-primary").html("主键");
	$(".dic-bg-foreign").addClass("bg-success");
	$(".dic-bg-foreign").html("外键");
	$(".dic-bg-associate").addClass("bg-info");
	$(".dic-bg-associate").html("关联");
	$(".dic-bg-common").html("普通");
	</script>
</body>
</html>
