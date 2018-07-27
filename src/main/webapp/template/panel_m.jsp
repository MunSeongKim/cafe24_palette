<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
	<link rel="stylesheet" href="/mammoth/template/panel_m.css">
	<link rel="stylesheet" type="text/css" href="/mammoth/template/panel.css" />
	<link rel="stylesheet" type="text/css" href="/mammoth/template/theme2.css" />
	
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/jquery-ui/1.12.1/jquery-ui.theme.min.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/bootstrap/4.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/bootstrap-toggle/2.2.2/css/bootstrap-toogle.min.css"/>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/font-awesome/5.1.0/css/all.css"/>
	
	<script src="/mammoth/template/panel_m.js" type="text/javascript"></script>
<style type="text/css">
.scale-transition { transition: transform 0.3s cubic-bezier(0.53, 0.01, 0.36, 1.63) !important; }
.scale-transition.scale-out {
 transform: scale(0);
 transition: transform 0.2s !important;
}
.scale-transition.scale-in { transform: scale(1); }
</style>
</head>
<div class="zoom">
	<a class="zoom-fab zoom-btn-large plt-pn-btn-default" id="zoomBtn"><i
		class="fa fa-bars"></i></a>
</div>

<ul class="zoom-menu">
	<!-- 일단 기능 2개 있다고 가정 -->
	<!-- 얘는 function idx로 활용한다. -->
	<c:forEach begin="0" end="3" varStatus="status" step="1">
		<li class="${status.count}">
			<a class="zoom-fab zoom-btn-sm plt-pn-btn-inactive scale-transition scale-out" 
				data-stat-id="func${status.count }">
			<span class="zoom-icon zoom-icon-func${status.count }">F</span>
			</a>
		</li>
		
		
	</c:forEach>
</ul>

<c:forEach begin="0" end="3" varStatus="status" step="1">
   	<div class="zoom-card scale-transition scale-out" id="func${status.count}"></div>
</c:forEach>