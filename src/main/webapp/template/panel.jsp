<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="scroll_mm_div">
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
				<span id="btn-orderlist" class="func-orderlist plt-pn-btn-default">ORDER LIST</span>
			</div>
			<div class="row">
				<span id="" class="plt-pn-btn-default">ORDER LIST2</span>
			</div>
			<div class="row">
				<span id="" class="plt-pn-btn-default">ORDER LIST3</span>
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

