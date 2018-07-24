<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>jQuery tiny-fab Plugin Demo</title>
<link rel="stylesheet" href="/mammoth/template/css/floating.css">
<link rel="stylesheet" type="text/css" href="/mammoth/template/panel.css" />
<link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" />
<link rel="stylesheet" type="text/css" href="/mammoth/template/theme1.css" />
<link rel="stylesheet" type="text/css" href="/mammoth/function/orderlist/orderlist_popuplayer.css" />
<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/velocity/2.0.5/velocity.min.js" type='text/javascript'></script>
<script src="/mammoth/template/js/panel_m.js" type="text/javascript"></script>
</head>

<h1>jQuery tiny-fab Plugin Demo</h1>
<div class="zoom">
	<a class="zoom-fab zoom-btn-large" id="zoomBtn"><i
		class="fa fa-bars"></i></a>
	<ul class="zoom-menu">
		<!-- 일단 기능 2개 있다고 가정 -->
		<!-- 얘는 function idx로 활용한다. -->
		<c:forEach begin="0" end="1" varStatus="status" step="1">
			<li class="${status.index}" data-func-id="${id }">
				<div class="zoom-card scale-transition scale-out" id="func${status.index + 1}"></div>
				<a class="zoom-fab zoom-btn-sm zoom-btn-person scale-transition scale-out">
				<i class="fa fa-user"></i>
				</a>
			</li>
		</c:forEach>
		<!-- <li><a class="zoom-fab zoom-btn-sm zoom-btn-person scale-transition scale-out"><i class="fa fa-user"></i></a></li>
      <li><a class="zoom-fab zoom-btn-sm zoom-btn-doc scale-transition scale-out"><i class="fa fa-book"></i></a></li>
      <li><a class="zoom-fab zoom-btn-sm zoom-btn-tangram scale-transition scale-out"><i class="fa fa-dashboard"></i></a></li>
      <li><a class="zoom-fab zoom-btn-sm zoom-btn-report scale-transition scale-out"><i class="fa fa-edit"></i></a></li>
      <li><a class="zoom-fab zoom-btn-sm zoom-btn-feedback scale-transition scale-out"><i class="fa fa-bell"></i></a></li> -->
	</ul>
	<!-- 얘는 실제 funcion값이 들어가는 곳 -->
	<%-- <c:forEach begin="1" end="2" varStatus="status" step="1">
    		<div class="zoom-card scale-transition scale-out" id="func${status.index}"></div>
    </c:forEach> --%>
	<!-- <div class="zoom-card scale-transition scale-out" id="func1"></div>
    <div class="zoom-card scale-transition scale-out" id="func1">
    </div> -->
</div>
<script src="/mammoth/template/js/floating.js"></script>
