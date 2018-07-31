<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
	<link rel="stylesheet" type="text/css" href="/mammoth/template/panel.css" />
	<link rel="stylesheet" type="text/css" href="${theme.filePath }" />
	
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


<div id="panel-draggable-btn" class="draggable"> 
	<span></span>
	<span></span>
	<span></span>
	<span></span>
</div>

<div id="panel" class="panel plt-pn ">
	<div class="container">
		<c:set var="size" value="${fn:length(selectFuncs) }" />
		<c:forEach var="selectFunc" items="${selectFuncs}" varStatus="idx">
			<c:if test="${idx eq 0 }">
				<c:if test="${selectFunc.function.isButton eq true }">
				<c:out value="${&ltdiv class='plt-pn-box'&rt}" escapeXml="false" />
				</c:if>
					<div class="row">
						<div class="func${selectFunc.id }" id="func${selectFunc.id}"></div>
					</div>
			</c:if>
			
			<c:if test="${idx ne 0 }">
				<c:if test="${selectFuncs[idx-1].function.isButton eq true and selectFunc.function.isButton ne selectFuncs[idx-1].function.isButton }">
					<c:out value="${&lt/div&rt}" escapeXml="false" />
					<div class="row">
						<div class="func${selectFunc.id }" id="func${selectFunc.id}"></div>
					</div>
				</c:if>
				<c:if test="${selectFuncs[idx-1].function.isButton eq true and selectFunc.function.isButton eq selectFuncs[idx-1].function.isButton }">
					<div class="row">
						<div class="func${selectFunc.id }" id="func${selectFunc.id}"></div>
					</div>
				</c:if>
			</c:if>
			
			<c:if test="${idx eq size-1 }">
				<c:if test="${selectFuncs[idx-1].function.isButton eq true and selectFunc.function.isButton eq selectFuncs[idx-1].function.isButton }">
					<c:out value="${&lt/div&rt}" escapeXml="false" />
				</c:if>
			</c:if>
		</c:forEach>
	</div>
</div>
<script>
$(function() {
	$.panel.setPosition("${panel.position}");
	<c:forEach var="selectFunc" items="${selectFuncs}" varStatus="idx">
		$(${"#func" + selectFunc.id}}).load(${selectFunc.function.filePath});
	</c:forEach>
});
</script>