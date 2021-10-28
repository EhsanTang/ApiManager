/********setting.css 由模板setting.tpl.css生成*********/
@media ( min-width : 1210px) {.container {width: [MAX_WIDTH]px!important}}

body, h1, h2, h3, h4, h5, h6, hr, blockquote, dl, dt, dd, ul, ol, li,pre, form, fieldset, legend, button, input, textarea, th, td {font-family: [FONT_FAMILY];}
a{text-decoration: none;}
a:hover{color: [ADORN_COLOR];text-decoration: none;}
.a-main{color:#ffffff;}
.a-main:hover{color:[MAIN_COLOR]!important}
.main-color{color:[MAIN_COLOR]!important}
.main-hover-color:hover{color:[MAIN_COLOR]!important}

.a-adorn{color:#ffffff;}
.a-adorn:hover{color:[ADORN_COLOR]!important}
.adorn-color{color:[ADORN_COLOR]!important}
.adorn-hover-color:hover{color:[ADORN_COLOR]!important}
input:focus{border-color: [MAIN_COLOR]!important;}

.s-bg-color {background-color: [BG_COLOR]}
.s-nav-bg-color {background-color: [NAV_BG_COLOR]!important}
.s-nav-color {color: [NAV_COLOR]}
.s-nav-color a {color: [NAV_COLOR]}

.btn-group{border: 1px [MAIN_COLOR] solid;}
.btn-group > .btn-default:hover{ background-color:[MAIN_COLOR]!important;}

.btn-adorn {color: #ffffff;background-color:[ADORN_COLOR];}
.btn-adorn:hover{color: #ffffff;background-color: [ADORN_COLOR];opacity: 0.8;}

.btn-main {color: #ffffff;background-color:[MAIN_COLOR];border:0px;}
.btn-main:hover{color: #ffffff;background-color: [MAIN_COLOR];opacity: 0.8;}

.main-bg {background-color: [MAIN_COLOR]!important;}
.main-bg-opacity-1{background-color: [MAIN_COLOR_OPACITY_1]!important;}
.main-bb-1{ border-bottom:1px solid [MAIN_COLOR]!important}
.main-br-1{ border-right:1px solid  [MAIN_COLOR]}
.main-bl-3{ border-left:3px solid [MAIN_COLOR]}
.main-bt-3{ border-top:3px solid [MAIN_COLOR]}
.main-bl-1{ border-left:1px solid [MAIN_COLOR]}

.bt-5 {border-top: 5px solid[MAIN_COLOR]}
.bl-5 {border-left: 5px solid[MAIN_COLOR]}

.hover-adorn:hover,.active-adorn{background-color: [ADORN_COLOR]!important;color:#ffffff!important;}
.hover-main:hover,.active-main{background-color: [MAIN_COLOR_OPACITY_1]!important;color:[MAIN_COLOR]!important;}

.adorn-color {color: [ADORN_COLOR]}
.adorn-bl-3{ border-left:3px solid [ADORN_COLOR]}
.adorn-bb-1{ border-bottom:1px solid [ADORN_COLOR]}
.adorn-bt-3{ border-top:3px solid [ADORN_COLOR]}
.adorn-bg {background-color: [ADORN_COLOR]}

.interface-detail blockquote {color: [MAIN_COLOR]}
.iactive {background-color: [MAIN_COLOR]!important;color:#ffffff!important;opacity: 0.8;}
.look-up-content {border-top: 3px solid[MAIN_COLOR];}
.sk-wave .sk-rect {background-color: [MAIN_COLOR];}
.separator {border-bottom: 1px dotted[MAIN_COLOR];color: [MAIN_COLOR]}
.login-bg{z-index:2;background: url('[LOGINBG]') 50% 50% / cover no-repeat fixed;}
.title-bg-img{background-image:url('[TITLEBG]')!important; background-position:center;}

/***remote iconfont*****/
@font-face {
    font-family: 'iconfont';  /* project id 73680 */
    src: url('[ICONFONT].eot');
    src: url('[ICONFONT].eot?#iefix') format('embedded-opentype'),
    url('[ICONFONT].woff') format('woff'),
    url('[ICONFONT].ttf') format('truetype'),
    url('[ICONFONT].svg#iconfont') format('svg');
}