<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="${keywords}"/>
    <meta name="description" content="${description}"/>
    <link href="${icon}" rel="shortcut icon" type="image/x-icon"/>
    <link href="${icon}" rel="icon" type="image/x-icon"/>
    <link href="${icon}" rel="shortcut" type="image/x-icon"/>
    <link href="resources/framework/bootstrap-3.0.0/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!-- base-min.css,admin.css应该发在bootstrap之后,覆盖部分bootstrap样式 -->
    <link href="resources/css/allCss.css?v=V8.2.0_0630" rel="stylesheet" type="text/css"/>
    <title>${title}</title>
</head>
<body class="BGFFF">
<div class="circular circular-1"></div>
<div class="circular circular-2"></div>
<div class="circular circular-3"></div>
<div class="circular circular-4"></div>
<div class="circular circular-5"></div>
<div class="circular circular-6"></div>


<div class="login-bg s-nav-bg-color mb10 w p0" style="height: 500px">
        <div class="container p0">
            <div class="row p0 m0 CFFF">
                <div class="f30 tl fl col-xs-4 mt20">
                    <a href="/" class="no_unl">
                        <i class="iconfont adorn-color f40">&#xe612;</i>
                        <span class="CFFF " style="font-family: Times New Roman">CRAP-API</span>
                    </a>
                </div>

                <div class="f16 tl fr CFFF fw200 col-xs-8 mt30">

                    <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                        <c:if test="${menuDto.menu.type=='TOP'}">
                            <a class="no_unl CFFF adorn-hover-color pl20 fr" href="${menuDto.menu.menuUrl}" target="_blank">
                                <div class="fr pl10">${menuDto.menu.menuName}</div>
                                <div class="mt-1 fr pl20">${menuDto.menu.iconRemark}</div>
                            </a>
                        </c:if>
                    </c:forEach>
                </div>



                <div class="cb"></div>
                <div class=" f30 mt100 tc fw200">完全开源、免费的API协作管理系统</div>

                <div class="f16 mt20 mb20 tc fw200">协作开发、在线测试、文档管理、导出接口、个性化功能定制...</div>

                <div class="tc mt50">
                    <c:if test="${login}">
                        <a class="btn btn-main r5 w150 f14 ml10 fw200" href="admin.do" target="_blank">管理项目</a>
                        <a class="btn btn-adorn r5 w150 f14 ml10 fw200" href="index.do#/project/list?projectShowType=3" target="_blank">查看项目</a>
                    </c:if>
                    <c:if test="${login == false}">
                        <a class="btn btn-main r5 w150 f14 ml10 fw200" href="loginOrRegister.do#/login" target="_blank">登录</a>
                        <a class="btn btn-adorn r5 w150 f14 ml10 fw200" href="/loginOrRegister.do#/register" target="_blank">注册</a>

                        <a class="f12 ml10 fw200 CFFF cursor" href="user/mock.do" target="_blank">免登录试用</a>
                    </c:if>
                </div>


                <div class="tr mt100 fw200">
                    <div class="fr ml20">
                        <a href='https://gitee.com/CrapApi/CrapApi/stargazers' target="_blank" class="CFFF">
                            ${starNum} Stars (GitHub & Gitee)
                        </a>
                    </div>
                    <div class="fr ml20">
                        <a href='https://gitee.com/CrapApi/CrapApi/members' target="_blank" class="CFFF">
                            ${forkNum} Forks
                        </a>
                    </div>
                    <div class="fr ml20">10万+ Users</div>
                </div>
            </div>
        </div>
</div>
<!-- End: top-->

<div class="cb"></div>
<div class="fun-introduce container p0 mt50">
    <div class="mt-15 bc BGFFF w400 f22 z10 rel tc adorn-color">开源免费在线管理平台</div>
    <c:forEach items="${menuList}" var="menuDto" varStatus="id">
        <c:if test="${menuDto.menu.type=='FUNCTION'}">
            <div class="col-xs-3 tc h200 f20 mt10">
                <c:if test="${menuDto.menu.menuUrl!=null && menuDto.menu.menuUrl !=''}">
                    <div class="h80 lh80 C555 f30 adorn-color">
                        <a class="adorn-color" target="_blank" href="${menuDto.menu.menuUrl}">${menuDto.menu.iconRemark}</a>
                    </div>
                    <a target="_blank" href="${menuDto.menu.menuUrl}" class="f14 C000 lh26 p15 tc break-word fw200">${menuDto.menu.menuName}</a>
                </c:if>

                <c:if test="${menuDto.menu.menuUrl==null || menuDto.menu.menuUrl ==''}">
                    <div class="h80 lh80 C555 f30 adorn-color">
                        ${menuDto.menu.iconRemark}
                    </div>
                    <span class="f14 C000 lh26 p15 tc break-word fw200" >${menuDto.menu.menuName}</span>
                </c:if>
            </div>
        </c:if>
    </c:forEach>
    <div class="cb"></div>
</div>


<div class="BGF5 pt50 mt30 pb50">
    <div class="container p0">
        <div class="row p0">
            <div class="col-xs-4">
                <div class="tc f20 mb20 mt30 fw400 adorn-color">项目主页</div>
                <div class="tc mt30 f12 C555 lh30">
                    接口、文档、错误码、缺陷一站式管理<br/>
                    支持团队协作，永久免费
                </div>
            </div>
            <div class="col-xs-8">
                <img src="${imgPrefix}resources/images/transparent.png" id="example-pic1"
                     imgSrc="${imgPrefix}resources/images/example1.jpg" class="w shadow mb20">
            </div>
        </div>
    </div>
</div>


<div class="BGFFF pt50 pb50">
    <div class="container p0">
        <div class="row p0">
            <div class="col-xs-8">
                <a class="adorn-color" target="_blank" href="http://api.crap.cn/index.do#/article/detail?projectId=help&moduleId=155032424248009000006&type=ARTICLE&id=155037947655301000051">
                    <img src="${imgPrefix}resources/images/transparent.png" id="example-pic2"
                         imgSrc="${imgPrefix}resources/images/example2.jpg" class="w shadow mb20">
                </a>
            </div>
            <div class="col-xs-4">
                <div class="tc f20 mb20 mt30 fw400">
                    <a class="adorn-color" target="_blank" href="http://api.crap.cn/index.do#/article/detail?projectId=help&moduleId=155032424248009000006&type=ARTICLE&id=155037947655301000051">接口调试插件</a>
                </div>
                <div class="tc mt30 f12 C555 lh30">
                    谷歌浏览器插件，支持post、get、put、自定义json调试<br/>
                    支持调试数据保存，支持历史记录查看，中英双语
                </div>
            </div>
        </div>
    </div>
</div>

<div class="BGF5 pt50 pb50">
    <div class="container pt20 pb20">
        <table>
            <tr>
                <td class="tc w100 f20 fw500 adorn-color">使<br>用<br>指<br>南</td>
                <td class="bl1 pl50 mt50">
                    <c:forEach items="${articleList}" var="article" varStatus="id">
                        <div class="mb20">
                            <a href="index.do#/article/detail?projectId=${article.projectId}&moduleId=${article.moduleId}&type=${article.type}&id=${article.id}"
                               class="p10 pl0 f14 fw600 dis w C000 no_unl">${article.name}</a>
                            <div class="f12 C999">${article.brief}</div>
                        </div>
                    </c:forEach>
                    <div class="tc">
                        <a class="mt20 btn btn-sm btn-default adorn-color w600 C999 b0" href="index.do#/article/list?type=ARTICLE&status=2">
                            MORE
                        </a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>


<!-- footer navbar -->
<div class="m0 w lh26 s-nav-bg-color shadow fw200 pt20 pb20">
        <div class="container pt20 r5 r20 p5 h26 tr CFFF">
                友情链接：
                <ul class="dis-in-tab p0 m0">
                    <li class="dis-in-tab mr20 CFFF"><a target="_blank" class="CFFF" href="http://api.crap.cn">CrapApi官网</a> <span class="bg_line"></span></li>
                    <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                        <c:if test="${menuDto.menu.type=='FRIEND'}">
                            <a target="_blank" class="mr20 CFFF" href="${menuDto.menu.menuUrl}">${menuDto.menu.menuName}</a>
                        </c:if>
                    </c:forEach>
                </ul>
        </div>

        <div class="container h26  p0 pl20 tc pb20 CFFF">
            ©<a href="http://crap.cn" target="_blank" class="CFFF">crap.cn</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版本号 [V8.1.5]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <ul class="dis-in-tab p0 m0">
                <li class="dis-in-tab mr20">
                    <a target="_blank" class="CFFF" href="http://api.crap.cn/static/help/help-articleList--1.html">帮助文档</a>
                </li>
                <li class="dis-in-tab mr20">
                    <a target="_blank" class="CFFF" href="https://github.com/EhsanTang/CrapApi">GitHub</a>
                </li>
                <li class="dis-in-tab mr20">
                    <a target="_blank" class="CFFF" href="https://gitee.com/CrapApi/CrapApi">码云</a>
                </li>
                <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                    <c:if test="${menuDto.menu.type=='BOTTOM'}">
                        <a target="_blank" class="mr20 CFFF" href="${menuDto.menu.menuUrl}">${menuDto.menu.menuName}</a>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
</div>

<script>
    loadImg('example-pic1');
    loadImg('example-pic2');

    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?500545bbc75c658703f93ac984e1d0e6";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();

    (function(){
        var bp = document.createElement('script');
        var curProtocol = window.location.protocol.split(':')[0];
        if (curProtocol === 'https') {
            bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
        }
        else {
            bp.src = 'http://push.zhanzhang.baidu.com/push.js';
        }
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(bp, s);
    })();

    // 加载图片的函数，就是把自定义属性data-src 存储的真正的图片地址，赋值给src
    function loadImg(id){
        var $img=document.getElementById(id);
        if ($img == null){
            return;
        }
        $img.setAttribute("src",$img.attributes['imgSrc'].value);
    }
</script>
</body>
</html>
