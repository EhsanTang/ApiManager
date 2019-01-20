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
    <link href="resources/css/allCss.css?v=v8.0.6" rel="stylesheet" type="text/css"/>
    <title>${title}</title>
</head>
<body class="BGFFF">

<div class="login-bg mb10 w p0" style="height: 600px">
        <div class="container p0">
            <div class="row p0 m0 CFFF">
                <div class="mt100 tc">
                        <a href="/">
                            <img class="h100 w100" src="${logo}"/>
                        </a>

                </div>
                <div class="f60 tc mt30">CRAP-Api</div>

                <div class="f18 mt30 tc">完全开源、免费的API协作管理系统</div>

                <div class="f16 mt10 mb20 tc">协作开发、在线测试、文档管理、导出接口、个性化功能定制...</div>

                <div class="tc mt50">
                    <c:if test="${login}">
                        <a class="btn btn-main r5 w150 f14 ml10" href="admin.do" target="_blank">管理项目</a>
                        <a class="btn btn-adorn r5 w150 f14 ml10" href="index.do#/project/list?projectShowType=3" target="_blank">查看项目</a>
                    </c:if>
                    <c:if test="${login == false}">
                        <a class="btn btn-main r5 w150 f14 ml10" href="loginOrRegister.do#/login" target="_blank">登录</a>
                        <a class="btn btn-adorn r5 w150 f14 ml10" href="user/mock.do" target="_blank">免登录试用</a>
                    </c:if>
                </div>


                    <div class="tr mt50">
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
                        <div class="fr ml20">10k+ Users</div>
                    </div>

            </div>
        </div>
</div>
<!-- End: top-->

<!-- 功能点介绍 -->
<div class="index-top-menu">
    <div class="container p0">
        <!--<c:if test="${login}">
            <img class="main-bg fl w40 h40 r50P mt-5" src="${avatarUrl}"/>
        </c:if>
        <c:if test="${!login}">
            <div class="main-bg fl w40 h40 r50P mt-5 tc pt5 CFFF">
                Hi
            </div>
        </c:if>
        <c:if test="${login}">
            <div class="BGFFF fr ml50 pl15 pr15">
                <a class="C555 adorn-hover-color" href="index.do#/project/list?projectShowType=3"
                   target="_blank">浏览项目</a>
            </div>
        </c:if>-->
        <c:forEach items="${menuList}" var="menuDto" varStatus="id">
            <c:if test="${menuDto.menu.type=='TOP'}">
                <div class="BGFFF fr ml50 pl15 pr15">
                    <a class="C555 adorn-hover-color" href="${menuDto.menu.menuUrl}"
                       target="_blank">${menuDto.menu.menuName}</a></div>
            </c:if>
        </c:forEach>
    </div>
</div>
<div class="cb"></div>

<div class="fun-introduce container p0 bl1 br1 bb1 mt50">
    <div class="mt-15 bc BGFFF w400 f30 z10 rel tc fw500 adorn-color">开源免费在线管理平台</div>
    <c:forEach items="${menuList}" var="menuDto" varStatus="id">
        <c:if test="${menuDto.menu.type=='FUNCTION'}">
            <div class="col-xs-3 tc h200 f20 mt10">
                <div class="h80 lh80 C999 f30">${menuDto.menu.iconRemark}</div>
                <!--href="${menuDto.menu.menuUrl}"-->
                <span class="f13 C555 lh26 p15 tc break-word" >${menuDto.menu.menuName}</span>
            </div>
        </c:if>
    </c:forEach>
    <div class="cb"></div>
</div>

<div class="BGF9 pt20 mt50">
    <div class="container p0">
        <div class="tc f30 fw400 mb10 mt50">项目主页</div>
        <div class="w50 adorn-bt-3 bc mb20"> </div>
        <img src="resources/images/admin_project.jpg" class="w shadow mb20">

        <div class="tc f30 fw400 mb10 mt50">接口调试插件</div>
        <div class="w50 adorn-bt-3 bc mb20"> </div>
        <img src="resources/images/chrome_debug.jpg" class="w shadow mb50">
    </div>
</div>

<div class="BGF9 pt20 pb50">
    <div class="container BGFFF pt20 pb20 shadow">
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
                        <a class="mt20 btn btn-sm btn-default w600 BGFFF C999 b0" href="index.do#/article/list?type=ARTICLE&status=2">
                            MORE
                        </a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<div class="container p0">
        <!-- 求赞赏 -->
        <div class="cb"></div>
        <div class="lh26 f12 p30">
            <div class="bc w150 fw500 f20 bb1 mb50 tc p10 adorn-color">开发者宣言</div>
            <table>
                <td class="tl">
                    各位好，我是Nico，一名年纪轻轻就秃了头的程序猿<br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;好人有好报，求各位打赏，帮我集资买瓶生发水吧!<br/>
                    打赏10元，你的程序从此告别bug；打赏50元，你的头发茂盛得像亚马逊丛林；<br/>
                    打赏100元，加入"穿着特步相亲也能轻松俘获女神的VIP QQ群（263949884），Nico将竭诚为你提供协助部署、升级帮助、问题解答等各种羞羞的服务...<br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;或者<br/>
                    如果你宁愿情人节独自在办公室加班修bug，也不给我买生发水<br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至少帮我在 <a href='https://gitee.com/CrapApi/CrapApi' target="_blank">Gitee</a>
                    或
                    <a target="_blank" href="https://github.com/EhsanTang/ApiManager">GitHub</a> 上点个赞好不好？<br/>
                    :)
                </td>
                <td class="w200 tc h">
                    <div class="bl1 pl30 ml30">
                        <img class="w100" src="resources/images/alipay.jpg"><br><br>
                        <img class="w100" src="resources/images/wepay.jpg">
                    </div>
                </td>
            </table>
        </div>
</div>


<!-- footer navbar -->
<div class="m0 w lh26">
        <div class="container mt20 r5 r20 p5 h26 tr">
                    友情链接：
                    <ul class="dis-in-tab p0 m0">
                        <li class="dis-in-tab mr20"><a target="_blank" href="http://api.crap.cn">CrapApi官网</a> <span
                                class="bg_line"></span></li>
                        <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                            <c:if test="${menuDto.menu.type=='FRIEND'}">
                                <a target="_blank" class="mr20" href="${menuDto.menu.menuUrl}">${menuDto.menu.menuName}</a>
                            </c:if>
                        </c:forEach>
                    </ul>
        </div>

        <div class="container h26  r20 p0 pl20 tc b1 mb20">
            ©<a href="http://crap.cn" target="_blank">crap.cn</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版本号 [V8.1.1-bate]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <ul class="dis-in-tab p0 m0">
                <li class="dis-in-tab mr20">
                    <a target="_blank" href="http://api.crap.cn/static/help/help-articleList--1.html">帮助文档</a>
                </li>
                <li class="dis-in-tab mr20">
                    <a target="_blank" href="https://github.com/EhsanTang/CrapApi">GitHub</a>
                </li>
                <li class="dis-in-tab mr20">
                    <a target="_blank" href="https://gitee.com/CrapApi/CrapApi">码云</a>
                </li>
                <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                    <c:if test="${menuDto.menu.type=='BOTTOM'}">
                        <a target="_blank" class="mr20" href="${menuDto.menu.menuUrl}">${menuDto.menu.menuName}</a>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
</div>

<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?b4a454e8f7114e487f10d7852f0c55c8";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
</body>
</html>
