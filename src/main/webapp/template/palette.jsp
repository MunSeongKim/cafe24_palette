<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
	<link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/static/jquery-ui/1.12.1/jquery-ui.theme.min.css">
	<link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/static/bootstrap/4.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/static/bootstrap-toggle/2.2.2/css/bootstrap-toggle.css"/>
	
	<link rel="stylesheet" href="https://devbit005.cafe24.com/mammoth/static/font-awesome/5.1.0/css/all.css"/>
	<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"/> -->
	<link rel="stylesheet" type="text/css" href="https://devbit005.cafe24.com/mammoth${theme.filePath }" />
	<link rel="stylesheet" type="text/css" href="https://devbit005.cafe24.com/mammoth/template/palette.css" />
	<script src="https://devbit005.cafe24.com/mammoth/template/palette.js" type="text/javascript"></script>
</head>

<div class="scroll_mm_div plt-pn-i">
	<i class="fa fa-arrow-circle-o-up scroll_top"></i>
	<i class="fa fa-arrow-circle-o-down scroll_bottom"></i>			
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
			<div class="row">
				<div class="func${selectFunc.id }" id="func${selectFunc.id}"></div>
			</div>
		
			<%-- <c:if test="${idx eq 0 }">
				<c:if test="${selectFunc.function.isButton eq true }">
				<c:out value="${<div class='plt-pn-box'>" escapeXml="false" />
				</c:if>
					<div class="row">
						<div class="func${selectFunc.id }" id="func${selectFunc.id}"></div>
					</div>
			</c:if>
			
			<c:if test="${idx ne 0 }">
				<c:if test="${selectFuncs[idx-1].function.isButton eq true and selectFunc.function.isButton ne selectFuncs[idx-1].function.isButton }">
					<c:out value="${</div>}" escapeXml="false" />
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
					<c:out value="${</div>}" escapeXml="false" />
				</c:if>
			</c:if> --%>
		</c:forEach>
	</div>
</div>

<script>
var pos = '${panel.position}';

$(function(){
	<c:forEach var="selectFunc" items="${selectFuncs}" varStatus="idx">
		$('#func' + '${selectFunc.id}').load("https://devbit005.cafe24.com/mammoth${selectFunc.function.desktopPath}");
	</c:forEach>
});
</script>