<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="scroll_mm_div plt-pn-i">
	<i class="fas fa-arrow-alt-circle-up scroll_top"></i>
	<i class="fas fa-arrow-alt-circle-down scroll_bottom"></i>			
</div>
<!-- scroll top, bottom end -->

<div id="panel-draggable-btn" class="draggable open">
	<span></span>
	<span></span>
	<span></span>
	<span></span>
</div>

<div id="panel" class="panel plt-pn ">
	<div class="container preview_panel">
		<c:forEach var="func" items="${funcs }">
			<c:choose>
				<c:when test="${func.isButton eq true}">
					<div class="preview_func_div" data-panelfuncname="${func.nameEng }" data-isbtn=${func.isButton }>
						<div class="row">
							<div id="paenl-div-${func.nameEng }" class="preview preview-func-${func.nameEng }" style="width: 90%;">
								<c:import url="${func.desktopPath }" charEncoding="UTF-8">
									<c:param name="isPreview" value="true" />
								</c:import>
							</div>
						</div>
					</div>
				</c:when>
				
				<c:otherwise>
				
				<div class="preview_func_div" data-panelfuncname="${func.nameEng }" data-isbtn=${func.isButton }> 
					<div class="row">
						<div id="panel-div-${func.nameEng }" class="preview-func-${func.nameEng }">
							<c:import url="${func.desktopPath }" charEncoding="UTF-8"/> 
						</div>
					</div>
				</div>
				</c:otherwise> 
			</c:choose>
		</c:forEach>
	</div>
</div>