<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
ul.nav li a {
	color: #555555;
	pointer-events: none;
	cursor: default;
}
.button {
    background-color: white; /* Green */
    border: 2px solid #555555;
    color: black;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
}
.button:hover {
    background-color: #555555;
    color: white;
}

</style>
<script>
$(function() {

	$("#tabs").tabs({ event : null });
	
	var currentIdx = $("#tabs").tabs("option", "active");
	$('#tab-li-'+currentIdx).addClass("active");
	$("#pager-previous").hide();
	$("#pager-save").hide();
	
	var tabs_max_size = ${fn:length(tabs.map)};
	
	$('#pager-previous').click(function() {
		var currentIdx = $("#tabs").tabs("option", "active");
		currentIdx -= 1;
		$("#tabs").tabs("option", "active", currentIdx);
		console.log(currentIdx);
		$('#tab-li-'+currentIdx).addClass("active");
		$('#tab-li-'+(currentIdx+1)).removeClass("active");
		
		if(currentIdx == 0) {
			$("#pager-previous").hide();
			$("#pager-next").show();
			$("#pager-save").hide();
		} else {
			$("#pager-next").show();
			$("#pager-save").hide();
		}
		
	});

	$('#pager-next').click(function() {
		var currentIdx = $("#tabs").tabs("option", "active");
		currentIdx += 1;
		if (currentIdx == tabs_max_size) {
			currentIdx = tabs_max_size-1;
		}
		console.log(currentIdx);
		$("#tabs").tabs("option", "active", currentIdx);
		
		$('#tab-li-'+currentIdx).addClass("active");
		$('#tab-li-'+(currentIdx-1)).removeClass("active");
		
		if(currentIdx != tabs_max_size-1) {
			$("#pager-previous").show();
			$("#pager-next").show();
			$("#pager-save").hide();
		} else {
			$("#pager-next").hide();
			$("#pager-save").show();
		}
	});
	
	$('#pager-save').click(function() {
		window.open('${pageContext.servletContext.contextPath}/${mid}/create/preview');
		
		return false;
	});
});
</script>
<div class="container">
	<h1>Application Tab</h1>
	<!-- tab START -->
	<div id="tabs" class="row">
		<ul class="nav nav-tabs">
			<c:forEach var="tab_element" items="${tabs.map }" varStatus="stat">
				<c:choose>
					<c:when test="${tab_element.key == 1 }">
						<li id="tab-li-${stat.index}"><a data-toggle="tab" href="#${stat.count }">
											${tab_element.value.tabName }</a></li>
					</c:when>
					<c:otherwise>
						<li id="tab-li-${stat.index}"><a data-toggle="tab" href="#${stat.count }">
							${tab_element.value.tabName }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>

		<div class="tab-content">
			<c:forEach var="tab_element" items="${tabs.map }" varStatus="stat">
				<c:choose>
					<c:when test="${tab_element.key == 1 }">
						<div id="${stat.count }">
							<jsp:include page="settings/${tab_element.value.jspFile }.jsp" flush="false" />	
						</div>
					</c:when>
					<c:otherwise>
						<div id="${stat.count }">
							<jsp:include page="settings/${tab_element.value.jspFile }.jsp" flush="false" />	
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</div>
	<!-- tab END -->
	<!-- Pager START -->
	<div class="row">
		<ul class="pager">
			<li><button class="button" id="pager-previous">Previous</button></li>
			<li><button class="button" id="pager-next">Next</button></li>
			<li><button class="button" id="pager-save">Save</button></li>
		</ul>
	</div>
	<!-- Pager END -->

</div>