<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="keywords" content="${keywords}" />
	<meta name="description" content="${description}" />
	<link href="${settings.ICON}" rel="shortcut icon" type="image/x-icon" />
	<link href="${settings.ICON}" rel="icon" type="image/x-icon" />
	<link href="${settings.ICON}" rel="shortcut" type="image/x-icon" />
	<link href="../../resources/framework/bootstrap-3.0.0/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="../../resources/framework/bootstrap-3.0.0/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
	<!-- base-min.css,admin.css应该发在bootstrap之后,覆盖部分bootstrap样式 -->
	<link href="../../resources/css/allCss.css" rel="stylesheet" type="text/css" />
	<title>${title}</title>
<!-- TODO 样式提取到setting.css中-->
<style type="text/css"> 
body{overflow-x:hidden;}
@media ( min-width : 1210px) {.container {width: {{settings.MAX_WIDTH}}px!important}}
@media ( max-width : 992px) {.col-md-mr0 {margin-right:0px!important}}
pre { white-space: normal;}

/********前端显示最小宽度*************/
.container-fluid{min-width: 400px;}
.navbar-inverse{min-width: 400px;border:0px!important;}
.container{min-width: 400px;}   
.C${module.id}{color:${settings.ADORN_COLOR}!important;font-weight:bold;}
.C${activePage}{color:${settings.ADORN_COLOR}!important;font-weight:bold;}
.s-bg-color {background-color: ${settings.BG_COLOR}}
.s-nav-bg-color {background-color: ${settings.NAV_BG_COLOR}!important}
.s-nav-color {color: ${settings.NAV_COLOR}}
.s-nav-color a {color: ${settings.NAV_COLOR}}
/* 默认主色:#B768A5*/
.main-bg {background-color: ${settings.MAIN_COLOR}!important;}
.main-color {color: ${settings.MAIN_COLOR}}
.adorn-color {color: ${settings.ADORN_COLOR}}
.adorn-bl-3{ border-left:3px solid ${settings.ADORN_COLOR}}
.adorn-bb-1{ border-bottom:1px solid ${settings.ADORN_COLOR}}
.adorn-color:hover {color: ${settings.ADORN_COLOR}}
.adorn-bg {background-color: ${settings.ADORN_COLOR}}
.interface-detail blockquote {color: ${settings.MAIN_COLOR}}
.bt-5 {border-top: 5px solid${settings.MAIN_COLOR}}
.bl-5 {border-left: 5px solid${settings.MAIN_COLOR}}
.menu_a:hover {background-color: ${settings.MAIN_COLOR}!important;color:#ffffff!important;opacity:1;}
.iactive {background-color: ${settings.MAIN_COLOR}!important;color:#ffffff!important;opacity: 0.8;}
.look-up-content {border-top: 3px solid${settings.MAIN_COLOR};}
.sk-wave .sk-rect {background-color: ${settings.MAIN_COLOR};}
.btn-adorn {
  color: #ffffff;
  background-color:${settings.ADORN_COLOR};
}
.btn-adorn:hover{
  color: #ffffff;
  background-color: ${settings.ADORN_COLOR};
  opacity: 0.8;
}
.btn-main {
  color: #ffffff;
  background-color:${settings.MAIN_COLOR};
}
.btn-main:hover{
  color: #ffffff;
  background-color: ${settings.MAIN_COLOR};
  opacity: 0.8;
}
ul{list-style-type:circle;}
<% Map<String,String> settings = (Map<String,String>)request.getAttribute("settings");%>
.login-bg{background: url('<%=settings.get("LOGINBG").startsWith("http")? settings.get("LOGINBG"):basePath + settings.get("LOGINBG")%>') 50% 50% / cover no-repeat fixed;}
.title-bg-img{
	background-image:url('<%=settings.get("TITLEBG").startsWith("http")? settings.get("TITLEBG"):basePath + settings.get("TITLEBG")%>')!important; background-position:center;
}
/****************End:颜色*****************/
/****************字体*********************/
body, h1, h2, h3, h4, h5, h6, hr, blockquote, dl, dt, dd, ul, ol, li,
	pre, form, fieldset, legend, button, input, textarea, th, td {
	font-family: ${settings.FONT_FAMILY};}
}
</style>

