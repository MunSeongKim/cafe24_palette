<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
	<link rel="stylesheet" type="text/css" href="/mammoth/template/panel.css" />
	<link rel="stylesheet" type="text/css" href="/mammoth/template/theme2.css" />
	
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/jquery-ui/1.12.1/jquery-ui.theme.min.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/bootstrap/4.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/bootstrap-toggle/2.2.2/css/bootstrap-toggle.css"/>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/font-awesome/5.1.0/css/all.css"/>
	
	<script src="/mammoth/template/panel.js" type="text/javascript"></script>
</head>

<div class="scroll_mm_div plt-pn-i">
	<i class="fas fa-arrow-alt-circle-up scroll_top"></i>
	<i class="fas fa-arrow-alt-circle-down scroll_bottom"></i>			
</div>
<!-- scroll top, bottom end -->

<div id="panel-draggable-btn" class="draggable"> 
	<span></span>
	<span></span>
	<span></span>
	<span></span>
</div>
<div id="panel" class="panel plt-pn ">
	<div class="container">
		<%-- <c:forEach var="func" items="${funclist }">
			<div class="row">
				<c:choose>
					<c:when test="${func.isBtn eq true }">
						<button>
							<button id="panel-btn-${func.nameEng }" class="btn func-${func.nameEng }">${func.name }</button>
							<div id="paenl-div-${func.nameEng }" class="func-${func.nameEng }"></div>
						</button>
					</c:when>
					<c:otherwise>
						<div id="panel-div-${func.nameEng }" class="func-${func.nameEng }"></div>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach> --%>
		<div class="row">
			<div class="func1" id="func1">
				<p>func1</p>
			</div>
		</div>
		<div class="row">
			<div class="func2" id="func2">
				<p>func2</p>
			</div>
		</div>
		<div class="plt-pn-box">
			<div class="row">
				<div class="func4" id="func4"></div>
			</div>
			
		</div>
		<div class="row">
			<div class="func3" id="func3">
				<p>func3</p>
			</div>
		</div>
	</div>
</div>
<div id="panel-func-orderlist"></div>