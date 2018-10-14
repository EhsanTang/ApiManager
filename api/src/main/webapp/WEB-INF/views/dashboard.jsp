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
                        <a class="btn btn-adorn r5 w150 f14" href="admin.do" target="_self">创建项目</a>
                    </c:if>
                    <c:if test="${login == false}">
                        <a class="btn btn-main r5 w150 f14 ml10" href="loginOrRegister.do#/login"
                           target="_self">登陆
                        </a>
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
        <c:if test="${login}">
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
        </c:if>
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
    <div class="mt-15 bc BGFFF w250 f18 z10 rel tc fw500 adorn-color">开源免费在线管理平台</div>
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

<div class="BGF9 bt1 bb1 pt50 pb50 mt50">
    <div class="container">
        <table>
            <tr>
                <td class="tc w100 f16 fb">使<br>用<br>指<br>南</td>
                <td class="bl1 pl50 mt50 pb20 pt20">
                    <c:forEach items="${articleList}" var="article" varStatus="id">
                        <div class="mb20">
                            <a href="index.do#/article/detail?projectId=${article.projectId}&moduleId=${article.moduleId}&type=${article.type}&id=${article.id}"
                               class="p10 pl0 f14 fw600 dis w C000 no_unl">${article.name}</a>
                            <div class="f12 C999">${article.brief}</div>
                        </div>
                    </c:forEach>
                    <div class="tc">
                        <a class="mt20 btn btn-sm btn-default w250" href="index.do#/article/list?type=ARTICLE&status=2">
                            More...
                        </a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>


<div class="container p0 mt10">
    <div class="row p0 m0">
        <!--<div class="cb f30 w tc dashed-b fw600 pt20">推荐项目
            <a class="adorn-color f12 fn" href="index.do#/project/list?myself=false">More...</a>
        </div> -->
        <!--<c:forEach items="${projectList}" var="item" varStatus="id">
            <div class="col-sm-6 col-md-4 col-lg-3 m0 p0">
                <div class="b1 tl r3 h220 m15 p15">
                    <div>
                        <a class="fl" href="project.do#/module/list?projectId=${item.id}" target="_blank">
                            <img class="h70 w70 r50P" src="${item.cover}"/>
                        </a>
                        <div class="lh26 fl mt20 ml10">
                            <a class="f12 text-primary mr5 cursor" href="project.do#/error/list?projectId=${item.id}"
                               target="_blank">
                                <i class="iconfont f12">&#xe6b7; 错误码</i>
                            </a>
                            <br/>
                            <a class="f12 text-primary mr5 cursor mt10" href="project.do#/module/list?projectId=${item.id}"
                               target="_blank">
                                <i class="iconfont f12">&#xe83b; 模块</i>
                            </a>
                        </div>
                    </div>
                    <div class="cb"></div>
                    <div class="h30 of-h f14 C000 pt10">
                        <a href="project.do#/module/list?projectId=${item.id}" target="_blank"
                           class="adorn-color">${item.name}</a>
                    </div>
                    <div class="h80 of-h C555 pt10 pb5">
                            ${item.remark}
                    </div>
                </div>
            </div>
        </c:forEach> -->

        <!-- 求赞赏 -->
        <div class="cb"></div>
        <div class="lh26 mt10 f12 mb30 p50">
            <div class="bc w100 fw500 f16 bb1 mb50 tc p10 adorn-color">开发者宣言</div>
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
</div>


<!-- footer navbar -->
<div class="p0 m0 mt30 def-bg w s-nav-bg-color s-nav-color">
    <div class="container p0">
        <div class="row p0 m0">
            <div class="col-xs-12 f12 p0 mb5 tl mt50 mb50">
                ©crap.cn&nbsp;版本号 [V8.0.0]
                <ul class="dis-in-tab">
                    <li class="dis-in-tab mr20">
                        <a target="_blank" href="http://api.crap.cn/static/help/help-articleList--1.html">帮助文档</a>
                    </li>
                    <li class="dis-in-tab mr20">
                        <a target="_blank" href="https://github.com/EhsanTang/CrapApi">源码:GitHub</a>
                    </li>
                    <li class="dis-in-tab mr20">
                        <a target="_blank" href="https://git.oschina.net/CrapApi/CrapApi">源码:码云</a>
                    </li>
                    <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                        <c:if test="${menuDto.menu.type=='BOTTOM'}">
                            <a target="_blank" class="mr20" href="${menuDto.menu.menuUrl}">${menuDto.menu.menuName}</a>
                        </c:if>
                    </c:forEach>
                </ul>
                <div class="mt20">
                    友情链接：
                    <ul class="dis-in-tab p0">
                        <li class="dis-in-tab mr20"><a target="_blank" href="http://api.crap.cn">CrapApi官网</a> <span
                                class="bg_line"></span></li>
                        <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                            <c:if test="${menuDto.menu.type=='FRIEND'}">
                                <a target="_blank" class="mr20" href="${menuDto.menu.menuUrl}">${menuDto.menu.menuName}</a>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
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
