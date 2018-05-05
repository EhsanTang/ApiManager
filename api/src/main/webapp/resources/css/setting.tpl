/********setting.css 由模板setting.tpl.css生成*********/
body{min-width:800px;}
@media ( min-width : 1210px) {.container {width: {{settings.MAX_WIDTH}}px!important}}
.BG_COLOR {background-color: {{settings.BG_COLOR}}}
.FOOTER_BG_COLOR {background-color: {{settings.FOOTER_BG_COLOR}}}
.FOOTER_COLOR {color: {{settings.FOOTER_COLOR}}}
.FOOTER_COLOR a {color: {{settings.FOOTER_COLOR}}}
/* 默认主色:#B768A5*/
.main-bg {background-color: {{settings.MAIN_COLOR}}!important;}
.main-color {color: {{settings.MAIN_COLOR}}}
.adorn-color {color: {{settings.ADORN_COLOR}}}
.adorn-bl-3{ border-left:3px solid {{settings.ADORN_COLOR}}}
.adorn-bb-1{ border-bottom:1px solid {{settings.ADORN_COLOR}}}
.adorn-color:hover {color: {{settings.ADORN_COLOR}}}
.adorn-bg {background-color: {{settings.ADORN_COLOR}}}
.interface-detail blockquote {color: {{settings.MAIN_COLOR}}}
.bt-5 {border-top: 5px solid{{settings.MAIN_COLOR}}}
.bl-5 {border-left: 5px solid{{settings.MAIN_COLOR}}}
.menu-a:hover {background-color: {{settings.MAIN_COLOR}}!important;color:#ffffff!important;opacity:1;}
.iactive {background-color: {{settings.MAIN_COLOR}}!important;color:#ffffff!important;opacity: 0.8;}
.pickActive {color: {{settings.MAIN_COLOR}}}
.pickSelect {color: {{settings.MAIN_COLOR}}}
.separator {border-bottom: 1px dotted{{settings.MAIN_COLOR}};color: {{settings.MAIN_COLOR}}}
.look-up-content {border-top: 3px solid{{settings.MAIN_COLOR}};}
.sk-wave .sk-rect {background-color: {{settings.MAIN_COLOR}};}
.btn-adorn {
  color: #ffffff;
  background-color:{{settings.ADORN_COLOR}};
}
.btn-adorn:hover{
  color: #ffffff;
  background-color: {{settings.ADORN_COLOR}};
  opacity: 0.8;
}
.btn-main {
  color: #ffffff;
  background-color:{{settings.MAIN_COLOR}};
}
.btn-main:hover{
  color: #ffffff;
  background-color: {{settings.MAIN_COLOR}};
  opacity: 0.8;
}
.hover-a:hover{
  color: #ffffff;
  background-color: {{settings.ADORN_COLOR}};
  cursor: pointer;
  text-underline: none;
}
.active-a{
  color: {{settings.ADORN_COLOR}}!important;
  font-weight:bold;
  cursor: pointer;
  text-underline: none;
}
.login-bg{background: url('{{settings.LOGINBG}}') 50% 50% / cover no-repeat fixed;}
.title-bg-img{
	background-image:url('{{settings.TITLEBG}}')!important; background-position:center;
}
/****************End:颜色*****************/
/****************字体*********************/
body, h1, h2, h3, h4, h5, h6, hr, blockquote, dl, dt, dd, ul, ol, li,
	pre, form, fieldset, legend, button, input, textarea, th, td {
	font-family: {{settings.FONT_FAMILY}};
}
/***remote iconfont*****/
@font-face {
    font-family: 'iconfont';  /* project id 73680 */
    src: url('{{settings.ICONFONT}}.eot');
    src: url('{{settings.ICONFONT}}.eot?#iefix') format('embedded-opentype'),
    url('{{settings.ICONFONT}}.woff') format('woff'),
    url('{{settings.ICONFONT}}.ttf') format('truetype'),
    url('{{settings.ICONFONT}}.svg#iconfont') format('svg');
}