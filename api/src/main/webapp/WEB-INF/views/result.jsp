<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="<%=request.getContextPath()%>/resources/css/base.css" rel="stylesheet" type="text/css" />
    <title>CrapApi|接口、文档管理系统</title>
  </head>
  <body class="BGEEE">
  	<div class="BGFFF r5 h100 w400 mt50 p50" style="margin:0 auto;">
		${result}
		<br/>
		<a  class="dis fr mt20" href="index.do">返回首页</a>
	</div>
  
  </body>
 </html>
