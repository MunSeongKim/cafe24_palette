<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
	<link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/assets/jquery-ui/jquery-ui.theme.min.css">
	<link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/assets/bootstrap/css/bootstrap.min.css">
	<!-- <link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/static/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"/> -->
	<link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/static/font-awesome/5.1.0/css/all.css"/>

	<link rel="stylesheet" type="text/css" href="https://devbit005.cafe24.com/mammoth/template/palette_m.css">
	<link rel="stylesheet" type="text/css" href="https://devbit005.cafe24.com/mammoth${theme.filePath }" />
	
	<script src="https://devbit005.cafe24.com/mammoth/template/palette_m.js" type="text/javascript"></script>
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
	<a class="zoom-fab zoom-btn-large plt-pn-btn-default" id="zoomBtn">
		<i class="fa fa-bars"></i>
	</a>
</div>

<ul class="zoom-menu">
	<!-- 일단 기능 2개 있다고 가정 -->
	<!-- 얘는 function idx로 활용한다. -->
	<c:forEach var="selectFunc" items="${selectFuncs }" varStatus="status">
		<li class="${status.count}" data-id="func${selectFunc.id }">
			<a class="zoom-fab zoom-btn-sm plt-pn-btn-inactive scale-transition scale-out" data-stat-id="func${selectFunc.id }">
				<span class="zoom-icon zoom-icon-func${status.count }">
					<c:set var="functionCharacter" value="${fn:substring(selectFunc.function.nameEng, 0, 1)}" />
					${fn:toUpperCase(functionCharacter) }
				</span>
			</a>
		</li>
	</c:forEach>
	<li class="palette-gohome">
		<a class="zoom-fab zoom-btn-sm plt-pn-btn-inactive scale-transition scale-out" onclick="goHome()">
			<span class="zoom-icon"><i class="fa fa-home" aria-hidden="true"></i></span>
		</a>
	</li>
	<li class="palette-gotop">
		<a class="zoom-fab zoom-btn-sm plt-pn-btn-inactive scale-transition scale-out m-scroll-top">
			<span class="zoom-icon"><i class="fas fa-angle-double-up" aria-hidden="true"></i></span>
		</a>
	</li>
	<li class="palette-gobottom">
		<a class="zoom-fab zoom-btn-sm plt-pn-btn-inactive scale-transition scale-out m-scroll-bottom">
			<span class="zoom-icon"><i class="fas fa-angle-double-down" aria-hidden="true"></i></span>
		</a>
	</li>
</ul>

<c:forEach var="selectFunc" items="${selectFuncs }" varStatus="status">
   	<div class="zoom-card scale-transition scale-out palette_m" id="func${selectFunc.id }"></div>
</c:forEach>

<script>
$(function(){
	<c:forEach var="selectFunc" items="${selectFuncs}" varStatus="idx">
		$('#func' + '${selectFunc.id}').load("https://devbit005.cafe24.com/mammoth${selectFunc.function.mobilePath}");
	</c:forEach>
});
</script>