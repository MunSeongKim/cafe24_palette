<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	<div class="container preview_panel">
		<c:forEach var="func" items="${funcs }">
			<div class="row preview_func_div" data-panelfuncname="${func.nameEng }">
				<c:choose>
					<c:when test="${func.isButton eq true}">
						<div>
							<div id="paenl-div-${func.nameEng }" class="func-${func.nameEng }">
								<!-- /cafe24/tmp/funhction/recent.... -->
								<c:import url="${func.desktopPath }" charEncoding="UTF-8"/>
							</div>
						</div>
					</c:when>
					
					<c:otherwise> 
						<div id="panel-div-${func.nameEng }" class="func-${func.nameEng }" data-panelfuncname="${func.nameEng }">
							<c:import url="${func.desktopPath }" charEncoding="UTF-8"/> 
						</div>
					</c:otherwise> 
				</c:choose>
			</div>
		</c:forEach>
	</div>
</div>