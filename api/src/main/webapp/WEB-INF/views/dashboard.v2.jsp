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
    <link href="resources/css/base.css?v=V8.2.0_0630" rel="stylesheet" type="text/css"/>
    <link href="resources/css/crapApi.css?v=V8.2.0_0630" rel="stylesheet" type="text/css"/>
    <link href="resources/css/setting.css?v=V8.2.0_0630" rel="stylesheet" type="text/css"/>
    <title>${title}</title>
</head>
<body class="BGFFF">

<div class="login-bg mb20 w p0" style="height: 600px">
    <div class="trans">
        <div class="container p0">
            <div class="row p0 m0 pt30">
                <div class="col-xs-3 col-md-3 h50 lh50 p0 m0">
                    <div class="p0 fl">
                        <a href="/">
                            <img class="h50" src="${logo}"/>
                        </a>
                    </div>
                    <div class="CFFF f20 pl5 dis-in-tab fl">CRAP-Api</div>
                </div>
                <div class="col-xs-9 col-md-9 tr f14 h50 lh50">
                    <a href='http://api.crap.cn/static/help/help-articleList--1.html' target="_blank" class="dis-in-tab ml30 a-adorn">帮助文档</a>
                    <a href='https://gitee.com/CrapApi/ApiDebug' target="_blank" class="dis-in-tab ml30 a-adorn">插件下载</a>

                    <c:if test="${login == false}">
                        <a class="dis-in-tab ml30 a-adorn" href="loginOrRegister.do#/register" target="_self">注 册</a>
                        <a class="dis-in-tab ml30 btn btn-adorn btn-sm" href="loginOrRegister.do#/login"
                           target="_self">登 陆</a>
                    </c:if>

                    <c:if test="${login}">
                        <a class="dis-in-tab ml30 a-adorn cursor" onclick="loginOut()">注 销</a></li>
                    </c:if>

                    <c:if test="${login}">
                        <a class="dis-in-tab ml30 a-adorn" href="index.do#/project/list?myself=true" target="_self">查看项目</a>
                        <a class="dis-in-tab ml30 btn btn-adorn btn-sm" href="admin.do" target="_self">进入项目管理</a>
                    </c:if>
                </div>

                <div class="col-xs-12 col-md-12 mt100">
                    <!--<div class="CFFF f50 mt100">CRAP-Api</div>-->
                    <div class="CFFF f40 mt20 mt100">完全开源、免费的API协作管理系统</div>
                    <div class="CFFF f18 mt20 mb20">协作开发、在线测试、文档管理、导出接口、个性化功能定制...</div>

                    <div class="tr mt100 CFFF">
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
    </div>
</div>
<!-- End: top-->

<div class="container p0 mt10">
    <div class="row p0 m0">
        <div class="col-sm-3 col-xs-12 b1 pb20">
            <c:forEach items="${menuList}" var="menuDto" varStatus="id">
                <c:if test="${menuDto.menu.type=='FRONT'}">
                    <div class="cb dashed-b p3 pl10 pr10 mr10 f16 fw600 mt10 mb10">
                        <span class="adorn-color fn">${menuDto.menu.iconRemark}</span> ${menuDto.menu.menuName}
                    </div>
                    <c:forEach items="${menuDto.subMenu}" var="subMenu" varStatus="id">
                        <a class="dis fl b1 p3 pl10 pr10 r12 mr10 f12 fn no_unl hover-adorn C999 mb5" href="${subMenu.menuUrl}">${subMenu.menuName}</a>
                    </c:forEach>
                </c:if>
            </c:forEach>

        </div>
        <div class="col-sm-9 col-xs-12">
            <div class="cb dashed-b p3 pl10 pr10 mr10 f16 fw600 mt10 mb10 adorn-bl-3">
                推荐文档
                <div class="fr f12"><a class="adorn-color fn" href="index.do#/article/list?type=ARTICLE&status=2">More...</a></div>
            </div>
            <c:forEach items="${articleList}" var="article" varStatus="id">
                <div class="dashed-b">
                    <a href="index.do#/article/detail?projectId=${article.projectId}&moduleId=${article.moduleId}&type=${article.type}&id=${article.id}"
                       class="p10 pl0 f14 fw600 dis w C000 no_unl">${article.name}</a>
                    <div class="f12 C999">${article.brief}</div>
                    <div class="tr C999 f12 p10">点击量:${article.click}次 <span
                            class="C999 pl20">${article.createTimeStr}</span></div>
                </div>
            </c:forEach>
        </div>


        <div class="cb f30 w tc dashed-b fw600 pt20">推荐项目
            <a class="adorn-color f12 fn" href="index.do#/project/list?myself=false">More...</a>
        </div>
        <c:forEach items="${projectList}" var="item" varStatus="id">
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
        </c:forEach>

        <!-- 求赞赏 -->
        <div class="cb"></div>
        <div class="tc lh26 mt50 f12 shadow mb30 p50 C555">
            <div>

                <img class="w100 mr100" src="resources/images/alipay.jpg">

                <img class="w100" src="resources/images/wepay.jpg">
            </div>
            <div class="dashed-b mt10 mb30"></div>
            各位好，我是Nico，一名年纪轻轻就秃了头的程序猿<br/>
            好人有好报，求各位打赏，帮我集资买瓶生发水吧!<br/>
            打赏10元，你的程序从此告别bug；打赏50元，你的头发茂盛得像亚马逊丛林；<br/>
            打赏100元，加入"穿着特步相亲也能轻松俘获女神的VIP QQ群（263949884），Nico将竭诚为你提供协助部署、升级帮助、问题解答等各种羞羞的服务...<br/>
            或者<br/>
            如果你宁愿情人节独自在办公室加班修bug，也不给我买生发水<br/>
            至少帮我在 <a href='https://gitee.com/CrapApi/CrapApi' target="_blank">Gitee</a>
            或
            <a target="_blank" href="https://github.com/EhsanTang/ApiManager">GitHub</a> 上点个赞好不好？<br/>
            :)
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
                        <a target="_blank" href="https://gitee.com/CrapApi/CrapApi">源码:码云</a>
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

<script src="resources/framework/jquery-1.9.1.min.js"></script>
<script src="resources/js/core.js?v=V8.2.0_0630"></script>
<script src="resources/js/crapApi.js?v=V8.2.0_0630"></script>
</body>
</html>
