<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav>
					  <ul class="pager mt20 fr">
	　　					<c:if test="${page.currentPage>1}">
							 <li class="cursor">
					    		<a href="${url}-${page.currentPage-1}.html"><span aria-hidden="true">&larr;</span> 上一页</a>
					   		 </li>
						</c:if>
						<c:if test="${page.currentPage<=1}">
							 <li ng-if="" class="disabled cursor">
					    	<a href="javascript:void(0);"><span aria-hidden="true">&larr;</span> 上一页</a>
					    </li>
						</c:if>
						<c:if test="${page.currentPage<page.totalPage}">
							 <li class="cursor">
					    	<a href="${url}-${page.currentPage+1}.html">下一页 <span aria-hidden="true">&rarr;</span></a>
					    </li>
						</c:if>
						<c:if test="${page.currentPage>=page.totalPage}">
						 <li class="disabled cursor">
					    	<a href="javascript:void(0);">下一页 <span aria-hidden="true">&rarr;</span></a>
					    </li>
						</c:if>
				 	 </ul>
				 </nav>
				<div class="fr C999 mt20 mr10">Total:${page.allRow}&nbsp; ${page.currentPage}/${page.totalPage}</div>
				<div class="cb"></div>
