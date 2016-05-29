<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page language="java" import="cn.crap.utils.Cache,cn.crap.model.Setting,java.util.List" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=100%; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<style type="text/css">
	body {font-family: SimSun;background:#f5f5f5;padding:10px;margin:0px;}
	img{height:80px;width:80px;}
	pre{line-height:20px;margin:0px;padding:0px;word-break:break-all; word-wrap:break-word;white-space: pre-wrap;
word-wrap: break-word;}
	td{vertical-align: top; overflow:hidden;}
	a{text-decoration: none;color: #ffffff;
    background-color: #6f5499;
    border-color: #563d7c;
    font-size: 12px;
    line-height: 1.5;
    border-radius: 3px;
    display: inline-block;
    margin-bottom: 0;
    font-weight: normal;
    text-align: center;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    background-image: none;
    border: 1px solid transparent;
    white-space: nowrap;
    padding: 3px 3px;
    font-size: 12px;
    line-height: 1.42857143;
    border-radius: 4px;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;}
    #fade{
	    opacity: 0.5;
	    position: fixed;
	    top: 0;
	    bottom: 0px;
	    right: 0;
	    left: 0;
	    background-color: #000000;
	    z-index: 999;
	    display: none;
    }
    #bigImgDiv{
    	box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.4);
    	z-index: 1051;
    	position: fixed;
   		top: 20px;
   		left:10px;
   		right:10px;
   		display: none;
   		border-radius: 5px;
    }
    #bigImg{
    	width:100%;
    	border-radius: 5px;
    }
	</style>
<title>湘麓缘生鲜水果店</title>
</head>
<body>
<script type="text/javascript">
	function next(){
		var id = document.getElementById("shop").value*1+1;
		document.getElementById("shop").value=id;
		var list = document.getElementsByName("shop"+id);
		var images = document.getElementsByName("img"+id);
		if(list.length < 10){
			document.getElementById("more").style.display="none";
		}
		for(var i=0;i<list.length;i++){
		   list[i].style.display = 'block';
		   images[i].src = images[i].title;
		}
	}
	function showBigImg(url){
		document.getElementById("bigImg").src=url;
		document.getElementById("bigImg").style.height="auto";
		document.getElementById("bigImgDiv").style.display = 'block';
		document.getElementById("fade").style.display = 'block';
	}
	function iclose(){
		document.getElementById("bigImgDiv").style.display = 'none';
		document.getElementById("fade").style.display = 'none';
	}
</script>
<div style="text-align: right;margin-top:-5px;margin-bottom:5px;font-size:12px;">
	送货电话：<a href="tel:15974212130" style="padding:1px 2px;">15974212130</a>
	<a href="tel:15974212165" style="padding:1px 2px;">15974212165</a>
</div>
<input id="shop" value="0" type="hidden">  
<%List<Setting> settings = Cache.getSetting(); 
int i=0;
for(Setting setting:settings){
	Setting s = null;
	if(setting.getKey().startsWith("cm_")){
		s=setting;
		int div = i/10;
		
%>
	<div name="shop<%=div%>" style="width:100%;border-bottom:1px solid #f0f0f0;background:#fff;position: relative;<%if(div!=0){%>display:none;<%}%>">
		<table style="padding:10px;width:100%;">
			<tr>
				<td style="width:80px;">
				<img name="img<%=div%>" <%if(div==0){%> src="<%=s.getValue() %>" <%}%> title="<%=s.getValue() %>"
				onclick="showBigImg('<%=s.getValue() %>')"/></td>
				<td style="padding-left:10px;overflow: hidden;">
					<div style="font-size:16px;color:#555;font-weight:bold;"><%=s.getKey().replace("cm_", "") %>
					</div>
					<pre style="font-size:14px;color:#888;"><%=s.getRemark() %></pre>
				</td>
			</tr>
		</table>
		<div style="text-align: right;position: absolute;right:10px;bottom: 5px;"><a href="tel:15974212165">点击拨号</a></div>
	</div>
	<div style="clear:both;"></div>

<%i= i+1;}} %>
	<div id="more" style="width:100%;height:50px;margin-bottom:10px; background:#fff;
	margin-top:10px; border:1px solid #EEE;color:#555; text-align:center;font-size:12px; font-weight:bold;
	line-height:50px" onclick="next()" >下一页</div>	
<div id="fade" onclick="iclose()"></div>
<div id="bigImgDiv">
	<img id="bigImg" onclick="iclose()">
</div>
</body>
</html>