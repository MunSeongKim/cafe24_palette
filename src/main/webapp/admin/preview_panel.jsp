<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
#basicInfo {
	border-top: 1px solid #eee;
}

#basicInfo ul{
	list-style: none;
}

#basicInfo li{
	padding-left: 5px;
}

#basicInfo li a{
	color: black;
}

</style>

<div class="scroll_mm_div plt-pn-i">
	<i class="fas fa-arrow-alt-circle-up scroll_top"></i>
	<i class="fas fa-arrow-alt-circle-down scroll_bottom"></i>			
</div>
<!-- scroll top, bottom end -->

<div id="panel-draggable-btn" class="draggable open">
	<span class="plt-pn-bg"></span>
	<span class="plt-pn-bg"></span>
	<span class="plt-pn-bg"></span>
	<span class="plt-pn-bg"></span>
</div>

<div id="panel" class="panel preview plt-pn">
	<div class="container preview_panel">
		<c:forEach var="func" items="${funcs }">
			<c:choose>
				<c:when test="${func.isButton eq true}">
					<div class="preview_func_div" data-panelfuncname="${func.nameEng }" data-isbtn=${func.isButton }>
						<div class="row">
							<div id="paenl-div-${func.nameEng }" class="preview-func-${func.nameEng }" style="width: 90%;">
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
	
	<!-- <div id="basicInfo" style="position: fixed; bottom: 0; width: 100%;">
		<ul>
			<li class="title"><a href="/newpage/customer.html">고객센터</a></li>
			<li class="phone"><a href="/newpage/customer.html">1688-4758</a></li>
			<li><a href="/newpage/customer.html">OPEN : am 09:00 - pm 06:00</a></li>
			<li><a href="/newpage/customer.html">LUNCH : pm 12:00 - pm 01:00</a></li>
			<li><a href="/newpage/customer.html">SAT/SUN ＆ HOLIDAY OFF</a></li>
		</ul>
	</div> -->
</div>