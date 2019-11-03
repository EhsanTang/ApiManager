<%@ page language="java" pageEncoding="utf-8" %>
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
<body>

<div class="container p20 login-bg s-nav-bg-color"  style="height: 230px">
    <div class="circular circular-1"></div>
    <div class="circular circular-2"></div>
    <div class="circular circular-3"></div>
    <div class="circular circular-4"></div>
    <div class="circular circular-5"></div>
    <div class="circular circular-6"></div>
        <div class="row p0 m0 CFFF">
            <div class="f30 tl col-xs-4 mt-10">
                <a href="/" class="no_unl">
                    <i class="iconfont adorn-color f40">&#xe612;</i>
                    <span class="CFFF " style="font-family: Times New Roman">CRAP-API</span>
                </a>
            </div>

            <div class="f16 tl CFFF fw200 col-xs-8">
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
            <div class="f22 tc fw200">完全开源、免费的API协作管理系统</div>

            <div class="f14 mt10 mb20 tc fw200">协作开发、在线测试、文档管理、导出接口、个性化功能定制...</div>

            <div class="tc mt15">
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


            <div class="tr mt20 fw200">
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
<!-- End: top-->


<div class="fun-introduce container p0 BGFFF">
    <c:forEach items="${menuList}" var="menuDto" varStatus="id">
        <c:if test="${menuDto.menu.type=='FUNCTION'}">
            <div class="col-xs-3 tc h100 f20">
                <div class="h30 lh30 C555 f22 adorn-color">${menuDto.menu.iconRemark}</div>
                <!--href="${menuDto.menu.menuUrl}"-->
                <span class="f12 C000 lh26 p15 tc break-word fw200" >${menuDto.menu.menuName}</span>
            </div>
        </c:if>
    </c:forEach>
    <div class="cb"></div>
</div>

<div class="container BGFFF mb100 pb20">
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
    <!-- 首页广告 -->
    <ins class="adsbygoogle"
         style="display:block"
         data-ad-client="ca-pub-2085270222106815"
         data-ad-slot="6227354044"
         data-ad-format="auto"
         data-full-width-responsive="true"></ins>
    <script>
        (adsbygoogle = window.adsbygoogle || []).push({});
    </script>
</div>

<div class="cb"></div>

</body>
</html>
