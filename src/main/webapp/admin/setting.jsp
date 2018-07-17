<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jQuery library --> 
<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css">

<style>
* {
	margin: 0;
	padding: 0;
}

body{
	background: rgb(220, 220, 220);
}

.container {
	background-color: #fff;
	margin-top: 50px; 
	position: relative;
	border-radius: 10px;
}

.fouc{
	display: none;
}

#title{
	padding : 15px;
	padding-bottom : 0; 
	background-color: #7952b3; 
	border-top-left-radius : 10px;
	border-top-right-radius : 10px;
	color : #fff; 
} 

#title>h1{
	font-size : 1.5rem;
	padding-left: 10px;
}

.progress{
	height : 10px; 
}

.inner {
	width: 100%;  
	/* height: 500px; */
	padding: 15px;
	padding-bottom : 0px;
	padding-top: 10px; 
} 

li{
	border-color: #c5c5c5;
    background-color: #fff;
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

.button:hover::after {
	background-color: #555555;
	color: white;
	content: 'test';
}

/* Tab 비활성화 */
.disable{
	cursor: not-allowed;
}

.nav-tabs>li>a{
	pointer-events: none;
	border: 0px; 
	border-bottom : 2px solid #fff;
	border-radius: 0px;
	color : #4285f4;
	padding-left: 5px;
	z-index : 0;
}

.nav-tabs>li.active>a{
	border: 0px;
	background-color: #4285f4;
	border-radius: 0px;
}

.nav-tabs>li>.active>a:focus{
	background-color: #4285f4;
}

.nav-tabs>li.active>a:hover{
	border: none; 
	background-color: #4285f4; 
	font-weight: normal;
	font-variant : normal;
	font-size : 1em;
	font-stretch: normal;
	font-weight: normal;
}


.nav-tabs>li.active>a:active{  
	border: 0px; 
	background-color: #4285f4;
}

.nav-tabs>li.active>a:visited{
	border: 0px;
	background-color: #4285f4; 
}

.nav-tabs>li>a:link{
	border-radius: 0px; 
	border: 0px;
	margin : 0px; 
}
 
.nav-tabs>li{ 
    float: left;
    position: relative;
	margin: 0px;
	padding-left : 17px; 
} 

.nav-tabs>li.complete{ 
	padding-left: 0px;
	border : none; 
	padding-left: 17px;
}

.nav-tabs>li.step{
	background-color: #ededed;
}

.chevron{
	border: 20px solid transparent;
    border-left: 17px solid #c5c5c5;
    border-right: 0;  
    content: "";
    display: block;  
    position: absolute; 
    right: -18px; 
    top: 0px;   
    z-index : 1;
}

.chevron:before {
	border: 20px solid transparent;
	border-left: 17px solid #ededed;
	border-right:0; 
	right: 1px; 
	top: -20px;
	display:block;
	position:absolute; 
	content: ""; 
}

.chevron.complete{
	border: 20px solid transparent;
    border-left: 17px solid #c5c5c5;
    border-right: 0;  
    content: "";
    display: block;
    position: absolute; 
    right: -18px; 
    top: 0px;   
    z-index : 1; 
}

.chevron.complete:before{
	border: 20px solid transparent; 
	border-left: 17px solid #4285f4;
	border-right:0; 
	right: 1px; 
	top: -20px;
	display:block;
	position:absolute;
	content: "";
}

.nav-pagination{
	/* position: absolute;
	right: 0; 
	top: 57px; */
	width: fit-content;
	margin: 0px auto;
	padding-bottom: 15px; 
}

.nav-pagination button{
	padding: 0;
	margin: 0; 
}

.nav-pagination i{
	line-height: 0; 
}
 
</style> 
 
<script type="text/javascript">
$(document).ready(function(){
	// 이미지를 포함한 모든 요소가 로드가 되고 실행.
	/* alert("window.ready"); */
	var tabsMaxSize = ${fn:length(tabs.map)}; 
	
	// 페이지 초기화
	pageRender(0, tabsMaxSize);
	
	// Next 버튼 클릭 시.
	$("#pager-next").click(function(){
		var values = findCurrentTag("next");      // 현재 탭 정보 얻어오기.
		var currentIdx = values.currentIdx;
		var currentPage = values.currentPage;
		var currentTag = values.currentTag;
		var nextTag = values.otherTag;
		var nextPage = values.otherPage;
		
		currentTag.parent().removeClass("step").addClass("complete");
		
		// aria-selected -> false 설정 
		currentTag.attr("aria-selected", "false");
		nextTag.attr("aria-selected", "true");
		
		// 다음 태그 배경 색, 글자 색 변경  
		nextTag.parent().css("background-color", "#4285f4");
		nextTag.css("color", "#fff");
		nextTag.siblings("span").addClass("chevron complete");
		 
		// 다음 위치로 +1
		currentIdx += 1;
		if (currentIdx == tabsMaxSize) {
			currentIdx = tabsMaxSize-1;
		}
		
		// 프로그래스 바 변경.
		progressBarChange(currentIdx, tabsMaxSize); 
		
		// 증가된 currentIdx 부분에 active 달기.
		$('#tab-li-'+currentIdx).addClass("active"); 
		$('#tab-li-'+(currentIdx-1)).removeClass("active");
		
		// a 태그와 연결된 div의 class 수정
		$("#"+currentPage).removeClass("show active");
		$("#"+nextPage).addClass("show active"); 
		
		if(currentIdx != tabsMaxSize-1) {
			$("#pager-previous").show();
			$("#pager-next").show();
			$("#pager-save").hide();
		} else {
			$("#pager-next").hide();
			$("#pager-save").show();
		}
	});
	
	// Previous 버튼 클릭 시
	$('#pager-previous').click(function() {
		var values = findCurrentTag("previous");      // 현재 탭 정보 얻어오기.
		var currentIdx = values.currentIdx;
		var currentPage = values.currentPage;
		var currentTag = values.currentTag;
		var previousTag = values.otherTag;
		var previousPage = values.otherPage;
		
		// aria-selected -> false 설정
		currentTag.attr("aria-selected", "false");
		currentTag.css("color", "#4285f4");
		currentTag.siblings("span").removeClass("complete");
		currentTag.parent("li").removeClass("complete").addClass("step"); 
		currentTag.parent("li").css("background-color", "#ededed"); 
		
		previousTag.attr("aria-selected", "true"); 
		
		currentIdx -= 1;
		if (currentIdx == tabsMaxSize) {
			currentIdx = tabsMaxSize-1;
		}
		
		// 증가된 currentIdx 부분에 active 달기.
		$('#tab-li-'+currentIdx).addClass("active");
		$('#tab-li-'+(currentIdx+1)).removeClass("active");
		 
		// a 태그와 연결된 div의 class 수정
		$("#"+currentPage).removeClass("show active");
		$("#"+previousPage).addClass("show active");
		
		// 프로그래스 바 변경. 
		progressBarChange(currentIdx, tabsMaxSize);
		
		if(currentIdx == 0) {
			$("#pager-previous").hide();
			$("#pager-next").show();
			$("#pager-save").hide();
		} else {
			$("#pager-next").show();
			$("#pager-save").hide();
		}
	});
	
	
});

// 페이지 초기화 메소드
function pageRender(currentIdx, tabsMaxSize){
	var firstLi = $(".nav-tabs li:first");
	firstLi.children("a").css({
		"background-color" : "#4285f4",
		"color" : "#fff"
	});
	firstLi.children("span").addClass("chevron complete");
	$("#pager-previous").hide();
	$("#pager-save").hide();
	$(".fouc").show();
	 
	progressBarChange(currentIdx, tabsMaxSize); 
}


function findCurrentTag(flag){
	var aTag = $("#panel-tabs > ul > li > .nav-link");
	// 현재 포커스 되고 있는 a태그의 위치 구하기.
	for(var i=0; i<aTag.length; i++){
		if($(aTag[i]).attr("aria-selected") == "true"){
			currentTag = $(aTag[i]);
			currentIdx = currentTag.data("tabno");
			currentPage = currentTag.attr("aria-controls");
			
			if( flag=="next" && (i+1) < aTag.length){
				otherTag = $(aTag[i+1]);
				otherPage = otherTag.attr("aria-controls");
			}else if(flag == "previous" && (i-1) >= 0){
				otherTag = $(aTag[i-1]);
				otherPage = otherTag.attr("aria-controls");
			}
		}
	}
	
	return {
		currentTag : currentTag,
		currentIdx : currentIdx,
		currentPage : currentPage,
		otherTag : otherTag,
		otherPage : otherPage
	};
}

function progressBarChange(currentIdx, tabsMaxSize){
	console.log(currentIdx); 
	var rate = ( currentIdx/tabsMaxSize)*100;
	var bgColor;  
	 
	if(rate <= 25){
		bgColor = "bg-danger";
	}else if(rate <= 50){
		bgColor = "bg-warning"; 
	}else if(rate <= 75){
		bgColor = "bg-success";
	}
	
	$("#tabProgress").css({
		"width" : (currentIdx/tabsMaxSize)*100+"%"
	}).removeClass($("#tabProgress").attr('class').split(" ").pop()).addClass(bgColor);
}


</script> 

<title>새 패널 만들기</title>
</head>
<body>
<div class="container-fluid" style="background-color: #fff; ">
	 
	<div id="title" class="row">
		<i class="fas fa-plus-square" style="font-size: 2em;"></i>
		<h1>새 패널 만들기</h1> 
	</div>

	<!-- tab START -->
	<div id="panel-tabs" class="row fouc">
		<ul class="nav nav-tabs" id="myTab">
			<c:forEach var="tab_element" items="${tabs.map }" varStatus="stat">
				<c:choose> 
					<c:when test="${stat.index == 0 }"> 
						<li class="nav-item active complete" id="tab-li-${stat.index}" style="padding-left: 0px;">
							<a class="nav-link" id="${tab_element.value.jspFile }-tab" data-tabno=${stat.index } data-toggle="tab" role="tab" href="#${tab_element.value.jspFile }" aria-controls="${tab_element.value.jspFile }" aria-selected="true">
								${tab_element.value.tabName }
							</a>
							<span class="chevron"></span>
						</li>
					</c:when>
					
					<c:otherwise>  
						<li class="nav-item step" id="tab-li-${stat.index}">
							<a class="nav-link" id="${tab_element.value.jspFile }-tab" data-tabno=${stat.index } data-toggle="tab" role="tab" href="#${tab_element.value.jspFile }" aria-controls="${tab_element.value.jspFile }" aria-selected="false">
								${tab_element.value.tabName }
							</a>
							<span class="chevron"></span>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
		
		<div class="progress"> 
			<div id="tabProgress" class="progress-bar progress-bar-striped progress-bar-animated bg-warning" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 0%"></div>
		</div>
		 
		<div class="tab-content" id="myTabContent">
			<c:forEach var="tab_element" items="${tabs.map }" varStatus="stat">
				<c:choose>
					<c:when test="${stat.index == 0 }"> 
						<div class="tab-pane fade show active" id="${tab_element.value.jspFile }" role="tabpanel" aria-labelledby="${tab_element.value.jspFile }-tab">
							<c:import url="settingtab/${tab_element.value.jspFile }.jsp" charEncoding="UTF-8">
									<c:param name="funcs" value="${funcs }"/> 
							</c:import>		
						</div>
					</c:when>
					
					<c:otherwise>
						<div class="tab-pane fade" id="${tab_element.value.jspFile }" role="tabpanel" aria-labelledby="${tab_element.value.jspFile }-tab">
							<c:import url="settingtab/${tab_element.value.jspFile }.jsp" charEncoding="UTF-8">
									<c:param name="funcs" value="${funcs }"/>	
							</c:import>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		
		<!-- Pager START --> 
		<div class="fouc nav-pagination"> 
			<ul class="pagination">
				<li><button type="button" class="btn btn-danger" id="pager-previous"><i class="fas fa-chevron-left"></i>Previous</button></li>
				<li><button type="button" class="btn btn-primary" id="pager-next">Next<i class="fas fa-chevron-right"></i></button></li> 
				<li><button type="button" class="btn btn-success" id="pager-save"><i class="fas fa-plus"></i>Save</button></li>
			</ul>
		</div>
	</div> 
	
	<!-- tab END -->
	<!-- Pager START -->
	<!-- <div class="row fouc">
		<ul class="pagination justify-content-center">
			<li><button type="button" class="btn btn-danger" id="pager-previous">Previous</button></li>
			<li><button type="button" class="btn btn-primary" id="pager-next">Next</button></li> 
			<li><button type="button" class="btn btn-success" id="pager-save">Save</button></li>
		</ul>
	</div> --> 
	<!-- Pager END --> 
</div>
</body>
</html>