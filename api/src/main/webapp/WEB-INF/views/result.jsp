<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
	body {font-family: PingFang SC,Lantinghei SC,Helvetica Neue,Helvetica,Arial,Microsoft YaHei,"\5FAE\8F6F\96C5\9ED1",STHeitiSC-Light,simsun,"\5B8B\4F53",WenQuanYi Zen Hei,WenQuanYi Micro Hei,"sans-serif";}
</style>
</head>
<body style="background-color:#FFF;">
<div style="position:relative;width:400px;margin:0 auto; background:#f7f7f7;color:#999999; font-size:14px;line-height:36px;">
<div style="margin-top:100px;height:60px; border-bottom:2px solid #f82c1d;padding:10px;" >
		<div style="float:left;margin-left:10px; line-height:60px;font-size:18px;font-weight:bold;color:#555;width:360px;height:60px;overflow:hidden;text-align:left;">
			<c:if test="${title==null}">
				抱歉，出错啦！
			</c:if>

			<c:if test="${title!=null}">
				${title}
			</c:if>

		</div>
</div>
		<div style="padding:20px;min-height:100px;white-space: pre-wrap;word-wrap: break-word;">
		${result}
		</div><div style="padding:20px;text-align:right;margin-top:30px;">
		<a style="color:#f82c1d;" href="/home.do">返回首页</a>
		<br/></div></div>

</body>
</html>