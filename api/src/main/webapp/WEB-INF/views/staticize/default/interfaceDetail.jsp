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
			<div  id="interface-content" class="col-xs-12 col-md-9 p0 s-bg-color m0 pt0">
			
			
			
				<div class="col-xs-12 p0 f12 lh26 interface-detail BGFFF p20">
				<c:forEach var="f" items="${interfaces}" varStatus="status"> 
		<div class="bb1 fb f20 pb10 C555 mt0">
			<div class="fl m0">Url：${f.model.moduleUrl}${f.model.url}</div>
			<div class="fr m0">
				版本号：
				<a href="javascript:void(0)" class="f12 btn btn-adorn btn-xs" ><i class="iconfont">&#xe620;&nbsp;</i>${f.model.version}</a>
			</div>
			<div class="cb"></div>
		</div>
		<blockquote>
  			<p class="cursor" style="color:#000;">目录</p>
  			<p class="cursor" onclick="scrollToId('1')">1  功能说明</p>
  			<p class="cursor pl20" onclick="scrollToId('1_1')">1.1  接口名</p>
  			<p class="cursor pl20" onclick="scrollToId('1_2')">1.2  版本号</p>
  			<p class="cursor pl20" onclick="scrollToId('1_3')">1.3  接口说明</p>
  			<p class="cursor pl20" onclick="scrollToId('1_4')">1.4  接口状态</p>
  			<p class="cursor" onclick="scrollToId('2')">2  接口调用说明</p>
  			<p class="cursor pl20" onclick="scrollToId('2_1')">2.1 URL</p>
  			<p class="cursor pl20" onclick="scrollToId('2_2')">2.2 Mock地址</p>
  			<p class="cursor pl20" onclick="scrollToId('2_3')">2.3 HTTP请求方式</p>
  			<p class="cursor pl20" onclick="scrollToId('2_4')">2.4  请求头说明</p>
  			<p class="cursor pl20" onclick="scrollToId('2_5')">2.5  输入参数说明</p>
  			<p class="cursor pl20" onclick="scrollToId('2_6')">2.6  请求示例</p>
  			<p class="cursor pl20" onclick="scrollToId('2_7')">2.7  返回参数说明</p>
  			<p class="cursor pl20" onclick="scrollToId('2_8')">2.8  正确返回示例</p>
  			<p class="cursor pl20" onclick="scrollToId('2_9')">2.9  错误返回示例</p>
  			<p class="cursor pl20" onclick="scrollToId('2_10')">2.10  错误码</p>
		</blockquote>
		<div class="fb f18" id="1">1 功能说明</div>
		<div id="1_1">1.1 接口名</div>
		${f.model.interfaceName}
		
		<div id="1_2">1.2 版本号</div>
		<span>${f.model.version}</span>
		
		<div id="1_3">1.3 接口说明</div>
		<span>${f.model.remark}</span>
		
		<div id="1_4">1.4 接口状态</div>
		<c:if test="${f.model.status==1}">
		<span>可用</span>
		</c:if>
		<c:if test="${f.model.status==0}">
		<span>不可用</span>
		</c:if>
		
		<div class="fb f18" id="2">2接口调用说明</div>
		<div id="2_1">2.1 URL</div>
		${f.model.moduleUrl}${f.model.url}
		
		<div id="2_2">2.2 Mock地址</div>
		<blockquote>
			<a href="${settings.DOMAIN}/mock/trueExam.do?id=${f.model.id}" target="_blank">正确数据URL</a>
		</blockquote>
		
		<input type="text" class="form-control m10 input-xs dis-in-blo b0 no-b-s"
		value="${settings.DOMAIN}/mock/trueExam.do?id=${f.model.id}">
		
		<blockquote>
			<a href="${settings.DOMAIN}/mock/falseExam.do?id=${f.model.id}" target="_blank">错误数据URL</a>
		</blockquote>
		
		<input type="text" class="form-control m10 input-xs dis-in-blo b0 no-b-s"
		value="${settings.DOMAIN}/mock/falseExam.do?id=${f.model.id}">
					
		
		<div id="2_3">2.3 HTTP请求方式</div>
		${f.model.method}
		
		<div id="2_4">2.4 请求头说明</div>
		<table class="table table-bordered">
			<thead>
				<tr class="BGEEE C000">
					<th class="tc w100">名称</th>
					<th class="tc w50">必须</th>
					<th class="tc w50">类型</th>
					<th class="tc w100">默认值</th>
					<th class="tc">备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${f.headers}" varStatus="status2"> 
				<tr>
					<td class="tc fb C000">${item.name}</td>
					<td class="tc C000">${item.necessary}</td>
					<td class="tc C000">${item.type}</td>
					<td class="tc C000">${item.def}</td>
					<td class="tc C000">${item.remark}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
		
		<div id="2_5">2.5 输入参数说明</div>
		<c:if test="${f.customParams!=null}">
		<div class="code">
			<div class="bb1 mt0 pt0">自定义参数（Custom）</div>
			<pre>${f.customParams}</pre>
			<!-- 自定义参数备注 -->
			<div class="bb1 mt0 pt0">自定义参数备注（Custom）</div>
				<table class="table table-bordered">
					<thead>
						<tr class="BGEEE C000">
							<th class="w50"></th>
							<th class="tc">名称</th>
							<th class="tc w50">类型</th>
							<th class="tc w50">必须</th>
							<th class="tc w150">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${f.paramRemarks}" varStatus="status2"> 
							<tr>
								<td class="tc" style="color:red;">${item.deep}</td>
								<td class="C000 pl20">
									<div class="m0 f12 fn" style="padding-left: ${item.deep*10}px;">${item.name}</div>
								</td>
								<td class="tc C000">${item.type}</td>
								<td class="tc C000">${item.necessary}</td>
								<td class="tc C000">${item.remark}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		</c:if>
		
		
		<!-- 如果不判断formParams!=null,当formParams还没加载成功,hasRequestHeader就会被调用导致报错 -->
		<c:if test="${f.formParams!=null}">
			<table class="table table-bordered" >
			<thead>
				<tr class="BGEEE C000">
					<th class="tc">名称</th>
					<th class="tc w50">是否必须</th>
					<th class="tc w100">参数位置</th>
					<th class="tc w100">类型</th>
					<th class="tc w100">默认值</th>
					<th class="tc w300">备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${f.formParams}" varStatus="status2"> 
				<tr>
					<td class="tc fb C000">${item.name}</td>
					<td class="tc C000">${item.necessary}</td>
					<c:if test="${item.inUrl!=null && item.inUrl=='true'}">
						<td class="tc C000">请求URL</td>
					</c:if>
					<c:if test="${item.inUrl==null || item.inUrl!='true'}">
						<td class="tc C000">普通参数</td>
					</c:if>
					<td class="tc C000">${item.type}</td>
					<td class="tc C000">${item.def}</td>
					<td class="tc C000">${item.remark}</td>
				</tr>
				</c:forEach>
			</tbody>
			</table>
		</c:if>
		
		
		<div id="2_6">2.6 请求示例</div>
		<div class="code">
			<pre>${f.model.requestExam}</pre>
		</div>
		
		<div id="2_7">2.7 返回参数说明</div>
		
			<table class="table table-bordered">
			<thead>
				<tr class="BGEEE C000">
					<th class="w50"></th>
					<th class="tc">名称</th>
					<th class="tc w50">类型</th>
					<th class="tc w50">是否必须</th>
					<th class="tc w150">备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${f.responseParam}" varStatus="status2"> 
					<tr>
						<td class="tc" style="color:red;">${item.deep}</td>
						<td class="C000 pl20">
							<div class="m0 f12 fn" style="padding-left: ${item.deep*10}px;">${item.name}</div>
						</td>
						<td class="tc C000">${item.type}</td>
						<td class="tc C000">${item.necessary}</td>
						<td class="tc C000">${item.remark}</td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
		
		<div id="2_8">2.8 正确返回示例</div>
		
		<c:if test="${f.model.trueExam==null}">
			<span>无</span>
		</c:if>
		
		<c:if test="${f.model.trueExam!=null}">
			<div class="code">
				<pre>${f.model.trueExam}</pre>
			</div>
		</c:if>
		
		<div id="2_9">2.9 错误返回示例</div>
		<c:if test="${f.model.falseExam==null}">
			<span>无</span>
		</c:if>
		
		<c:if test="${f.model.falseExam!=null}">
			<div class="code">
				<pre>${f.model.falseExam}</pre>
			</div>
		</c:if>
		
		<div id="2_10">2.10 错误码</div>
			<table class="table table-bordered">
			<thead>
				<tr class="BGEEE C000">
					<th class="tc w200">Code</th>
					<th class="tc">Msg</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${f.errors}" varStatus="status2"> 
					<tr>
						<td class="tc fb C000">${item.errorCode}</td>
						<td class="tc C000">${item.errorMsg}</td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
		</c:forEach>
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
