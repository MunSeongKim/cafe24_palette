<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" /> 

<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/jquery-ui/1.12.1/jquery-ui.theme.min.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/bootstrap/4.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/font-awesome/5.1.0/css/all.css"/> 
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/template/palette.css">

<!-- preview_panel 기본 theme 적용 -->
<link rel="stylesheet" id="themeEx" href="">

<!-- jQuery library -->
<script src="${pageContext.servletContext.contextPath }/static/jquery/1.11.1/jquery.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/mustachejs/2.2.1/mustache.min.js"></script>
<!-- 패널 미리보기 -->
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/admin/assets/js/preview_panel.js"></script>

<!-- Google Chart -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="./admin/assets/js/Chart.bundle.js"></script>
<script src="./admin/assets/js/utils.js"></script>

<script type="text/javascript" src="./admin/assets/js/chart.js"></script>

<!-- Google Chart End -->

<style>
@import url(https://fonts.googleapis.com/earlyaccess/notosanskr.css);
/* chart js */
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
/************/

* { 
	-webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
    box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: 'Noto Sans KR', 'Dotum', '돋움', 'arial', 'verdana', sans-serif; 
}

body#main-body{
	height : 1024px;
	overflow : scroll;
	margin-bottom : 40px;
	background-color: #563d7c;
}

a {
	text-decoration: none;
}

#mainNavbar .container, #mainContainer .container{
	position: relative;
}

#panellist{
	width: 100%;
}

#palette-tab{
	width: 100%;
}

#palette-tab > .nav-item{
	width: 100%;
}

/* #mainNavbar CSS */

#mainNavbar .navbar{
	background-color: #563d7c;
	color : white;
}

#mainNavbar .navbar-brand{
	margin-left: -15px;
}

#mainNavbar .none-event{
	pointer-events : none;
	color: white !important;
}

#mainNavbar .nav-tabs{
	border: none;
}

#mainNavbar .nav-tabs li{
	margin-right: 15px;
}

#mainNavbar .nav-tabs a {
	color: #949494;
}

#mainNavbar .nav-tabs .nav-link {
	border: none;
}

#mainNavbar .navbar-nav .nav-item .nav-link.active {
	background: inherit;
}


/* @mainContainer CSS */
#mainContainer table, table thead th {
	text-align: center;
}

#mainContainer .card{
	box-shadow: 4px 4px 4px #AAAAAA;
}

#mainContainer .carousel-inner{ 
	overflow: visible;
	position: relative;
    width: 100%;
}

#mainContainer .sub-tab-title{
	font-weight: 700;
}

.panel-detail-info-div p{
	display: inline;
	font-weight: 600;
}

/* #tblPanelList CSS start */
#tblPanelList tr{
	cursor: pointer;
}

#preview-panel-btn {
	padding: 0;
	display: none;
	background-color: #563d7c;
	color: white;
}

/* 모달 CSS */
.palette-modal-header{
	padding: 10px 5px;
	background-color: #563d7c;
}

.palette-modal-title{
	color : #fff;
	border-top-left-radius: 12px;
	margin-left: 10px;
}


</style>

<script type="text/javascript">

function renderDetailInit(){
	var template = $('#mustache-panelDetail-result').html();
	Mustache.parse(template);
}

/* Panel Detail Info 데이터 갱신 */
function renderDetail(idx, data) {
	var template = $('#mustache-panellist-template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, data[idx]);
	$('#mustache-panelDetail-result').html('').append(rendered);
}

/* theme 선택 css file 변경 */
function includeThemeFile(path) {
	var themeFlag = 0;
	$("head > link").each(function(){
		if($(this).attr("id")=="themeEx"){
			// href만 갈아끼우기
			$(this).attr("href", path);
			themeFlag = 1;
			return false; // break;
		}
	}); 
	
	if(themeFlag == 1){
		return;
	}
	
	var element = document.createElement("link");
	$(element).attr("id", "themeEx");
	$(element).attr("rel", "stylesheet"); 
	$(element).attr("type", "text/css");
	$(element).attr("href", path);
	
  	// element 잘 생성이 안되었을 때.
  	if (typeof element != "undefined") {
    	$("head").append(element);
  	}
}

$(document).ready(function(){
	window.$Palette = $;
	
	/* 패널 미리보기 초기화 */
	$.panel.init({
		visible : "hide",
		funcVisible : false
	});
	
	/* carousel 초기화 */
	$('.carousel').carousel({
		interval : false /* auto slide off */
	})
	
	/* panel list data preprocessing start*/
	var data = new Array();
	<c:forEach items="${list}" var="panel">
		var obj = {};
		var funcs = [];
		obj['panelId'] = "${panel.panelId}";
		obj['panelName'] = "${panel.name}"; 
		obj['panelCreatedDate'] = '<fmt:formatDate value="${panel.createdDate }" pattern="yyyy-MM-dd"/>';
		obj['panelPosition'] = "${panel.position}";
		obj['panelType'] = "${panel.panelType}";
		obj['themeTitle'] = "${panel.theme.title}";
		obj['themeFilePath'] = "${pageContext.servletContext.contextPath}${panel.theme.filePath}";
		obj['themeTitleImgPath'] = "${pageContext.servletContext.contextPath}${panel.theme.titleImgPath}";
		obj['scriptDpLocation'] = "${panel.script.dpLocation}";
		
		<c:forEach items="${panel.selectFuncs}" var="func">
			var func = {};
			func['id'] = "${func.id}";
			func['funcOrder'] = "${func.funcOrder +1}";
			func['funcEngName'] = "${func.function.nameEng }";
			func['funcKorName'] = "${func.function.name}";
			funcs.push(func);
		</c:forEach>
		
		obj["funcs"] = funcs;
		data.push(obj);
	</c:forEach>
	/* panel list data proprecessing end */
	
	/* 옵션에 해당하는 기능의 div 재배치 및 출력 */
	function funcRender(idx){
		var funcOrderList = [];  // 기능이 배치된 순서대로 기능명이 array에 있음.
		var position = data[idx].panelPosition.toLowerCase();
		var removePosition = "left";
		
		if(position == 'left'){
			removePosition = 'right';
		}
		
		$.panel.setPosition({
			position : position,
			removePosition : removePosition
		}); 
		
		data[idx].funcs.forEach(function(func, index){
			funcOrderList.push(func.funcEngName);
			
			tmpElement = $(".preview_panel > div[data-panelfuncname="+func.funcEngName+"]");
			
			if(index==0){
				$(".preview_panel").prepend(tmpElement);
			}else{
				exTmpElement = $(".preview_panel > div[data-panelfuncname="+funcOrderList[index-1]+"]");
				$(exTmpElement).after(tmpElement);
			}
			
			$(tmpElement).removeClass("hide");
		});
	} 
	
	/* 메뉴 상단 탭 클릭 시 */
	$(".nav-tabs a").click(function(event){
		event.stopPropagation;
		$(":not(this)").children("span.current-nav").text("");
		$(this).children("span").append('<i class="fas fa-check mr-1"></i>');
		
		$(":not(this)").removeClass("none-event");
		$(this).addClass("none-event");
	});
	
 	$('#adminTabContent').on('slide.bs.carousel', function (evt) {
    	$('#adminTabContent .controls li.active').removeClass('active');
    	$('#adminTabContent .controls li:eq(' + $(evt.relatedTarget).index() + ')').addClass('active');
    });
	
	/* panel List Table에 마우스를 올렸을 때. */
	$("#tblPanelList > tbody > tr").hover(function(){
		$(this).toggleClass("table-success");
	});
	
	/* 리스트에서 Panel Name을 눌러서 상세정보를 볼 때. */
	$("#tblPanelList > tbody > tr").click(function(){
		/* 클릭 된 tr 색 변경 */
		$(this).addClass("bg-success");
		$(":not(this)").removeClass("bg-success");
		
		/* preview Panel 숨김 */
		$.panel.close();
		
		/* 모든 기능 div 숨김 */
		$(".preview_func_div").each(function(){
			if(!$(this).hasClass("hide")){
				$(this).addClass("hide"); 
			}
		});
		 
		/* panel-name-td에 있는 순서 번호 가져오기 */
		var idx = ($(".panel-name-td", this).prev().text()-1);
		
		/* preview panle에 적용될 CSS 파일 위치 */
		var cssFilePath = data[idx].themeFilePath;
		
		/* 맨 처음만 동작 */
		if($("#preview-panel-btn").hasClass("hide")){
			funcRender(idx);
		}
		
		/* 미리보기 버튼 출력 */
		$("#preview-panel-btn").fadeIn(300); 
		
		/* preview_panel이 사라지게 하면서.. */
		$("#panelArea").fadeOut(300, function(){
			funcRender(idx);
		}); 
		
		/* theme css File 교체 */
		includeThemeFile(cssFilePath, "css");
		
		/* Panel Detail Info에 데이터 교체 */
		renderDetail(idx, data);
	}); 
	
	/* 미리보기 클릭 시 - 패널 보이기 */
	$("#preview-panel-btn").click(function(){
		$("#panelArea").show();
		$(this).fadeOut(300); 
	});
});
</script>

<title>Palette</title>
</head>

<body id="main-body">
	<!-- Navigation -->  
    <nav id="mainNavbar" class="navbar navbar-expand-lg navbar-dark static-top">
    	<div class="container">
        	<a class="navbar-brand" href="#" style="font-size: 1.5em;"><i class="custom-i fas fa-palette"></i> Palette</a>
        	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#controls" aria-controls="controls" aria-expanded="false" aria-label="Toggle navigation">
          		<span class="navbar-toggler-icon"></span> 
        	</button>
        	
	        <div class="collapse navbar-collapse" id="controls">
	        	<ul class="nav navbar-nav ml-auto nav-tabs" id="adminTab">
	            	<li class="active" data-target="#adminTabContent" data-slide-to="0">
	             		<a class="none-event" href="#">
	             			<span class="current-nav">
	             			<i class="fas fa-check mr-1"></i>
	             			</span>Panel List
	             			<span class="sr-only">(current)</span>
	             		</a>
	            	</li>
	             
	            	<li data-target="#adminTabContent" data-slide-to="1">
	            		<a href="#">
	            		<span class="current-nav"></span> 
	            		Statistics</a>
	            	</li>
	            	
	            	<!-- <li data-target="#adminTabContent" data-slide-to="2">
	              		<a href="#">
	              		<span class="current-nav"></span>
	              		About</a>
	            	</li> -->
	            	
	            	<li data-target="#adminTabContent" data-slide-to="2"> 
	              		<a href="#">
	              		<span class="current-nav"></span>
	              		Contact Us</a>
	            	</li>
	            	
	            	<li data-target="#adminTabContent" data-slide-to="3">
	              		<a href="#">
	              		<span class="current-nav"></span>
	              		Upload</a>
	            	</li>
	            	
	          	</ul>
	      	</div>
    	</div>
    </nav>
     
    <div id="panelArea">
    </div>
  
    <!-- Page Content -->
    <div id="mainContainer" class="container">
		<div class="row carousel slide" id="adminTabContent" data-ride="carousel">
			<div class="carousel-inner"> 
				<!-- Panel List Tab-->
				<div class="row carousel-item active" id="panellist">
					
					<!-- Panel List -->
					<div class="col-sm-12 col-md-12 card mt-5 rounded">
						<div class="main card-body">
							<div class="row mb-3"> 
								<div class="col-sm-6">
									<h4 class="sub-tab-title"><i class="fas fa-list"></i> Panel List</h4>
								</div>
								 
								<div class="col-sm-6" style="text-align: right;"> 
									<a href="${pageContext.servletContext.contextPath }/setting/create" class="btn btn-primary btn-sm pull-right float-right"><i class="fas fa-plus-square"></i> 만들기</a>
								</div>
							</div>
							
							<div class="row"> 
								<table id="tblPanelList" class="table table-striped table-centered mb-0 table-sm">
									<thead>
										<tr>
											<th>No.</th>
											<th>Panel Name</th>
											<th>Created Date</th>
											<th>State</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="panel" items="${list }" varStatus="stat">
											<tr>
												<td>${stat.count }</td>
												<td id="panel-name-td${panel.panelId }" class="panel-name-td">${panel.name }</td>
												<td><fmt:formatDate value="${panel.createdDate }" pattern="yyyy-MM-dd"/></td>
												
												<td id="state-td${panel.panelId }">
													<c:choose>
														<c:when test="${panel.script.isApply eq true}">
															Active
														</c:when>
														<c:otherwise>
															Inactive
														</c:otherwise>
													</c:choose>
												</td>
												
												<td>
													<c:choose>
														<c:when test="${panel.script.isApply eq true}">
															<button class="btn btn-default btn-sm btn-apply" id="${panel.panelId }" data-apply="false"><i class="fas fa-ban"></i> 해제</button>
														</c:when>
														
														<c:otherwise>
															<button class="btn btn-info btn-sm btn-apply" id="${panel.panelId }" data-apply="true"><i class="fas fa-play"></i> 적용</button>
														</c:otherwise>
													</c:choose>
													
													<a href="${pageContext.servletContext.contextPath }/update/${panel.panelId }" class="btn btn-success disabled btn-sm" style="text-decoration: line-through"><i class="fas fa-wrench" style="text-decoration: line-through"></i> 수정</a> 
													<button class="btn btn-danger btn-delete btn-sm" data-panelid="${panel.panelId }" data-count="${stat.count }"><i class="fas fa-trash-alt"></i> 삭제</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
						</div>
					</div>
					
					<!-- Panel Detail Info -->
					<div class="col-sm-12 col-md-12 card mt-5 rounded panel-detail-info-div">
						<div class="main card-body">
							<div class="row mb-3"> 
								<div class="col-sm-6"> 
									<h4 class="sub-tab-title d-inline align-middle"><i class="fas fa-info-circle"></i> Panel Detail Info</h4>
									<button id="preview-panel-btn" class="btn btn-sm"><i class="fas fa-eye"></i> 미리보기</button> 
								</div>
							</div>
							
							<div id="mustache-panelDetail-result">
								<h5>Please Click Panel List</h5>
							</div>
							
							<script id="mustache-panellist-template" type="text/template">
							<div id="panel-detail-info">
								<div class="row">
									<div class="col-sm-5">
										<p class="text-monospace">패널명:</p>
										<p>{{panelName}}</p>
									</div>
									<div class="col-sm-3">
										<p class="text-monospace">패널 위치:</p>
										<p>{{panelPosition}}</p>
									</div>
									<div class="col-sm-4">
										<p class="text-monospace">생성일:</p>
										<p>{{panelCreatedDate}}</p> 
									</div>
								</div>
							
								<div class="row mt-4">
									<div class="col-sm-8">
										<p class="text-monospace">적용 화면:</p>
										<p id="dpLocation{{panelId}}">{{^scriptDpLocation}}없음{{/scriptDpLocation}}{{#scriptDpLocation}}{{scriptDpLocation}}{{/scriptDpLocation}}</p>
									</div>
								
									<div class="col-sm-4">
										<p class="text-monospace">적용테마명:</p>
										<p>{{themeTitle}}</p>
									</div>
								</div>
							
								<div class="row mt-4 d-flex">
									<div class="col-sm-8">
										<p class="text-monospace"><i class="fas fa-table"></i> 선택된 기능 정보</p>
										<table class="table table-sm table-dark">
											<thead>
												<tr>
													<th scope="col">기능 순서</th>
													<th scope="col">기능명(Kor)</th>
													<th scope="col">기능명(Eng)</th>
												</tr>
											</thead>
											<tbody>
												{{#funcs}}
												<tr>
													<th scope="row">{{funcOrder}}</th>
													<td>{{funcKorName}}</td>
													<td>{{funcEngName}}</td>
												</tr>
												{{/funcs}}
											</tbody>
										</table>
									</div>
								<div class="col-sm-4 text-center">
									<img src="{{themeTitleImgPath}}" class="img-thumbnail">
								</div>
							</div>
							</div>
							</script>
						</div>
					</div>
				</div>
			
				<!-- Statistics Tab Start --> 
				<div class="col-sm-12 col-md-12 carousel-item" id="stat">
					<!-- Header -->
					<div class="row justify-content-center"> 
						<div class="col-12 card mt-5 rounded" id="header">
							<h4>
								<i class="fas fa-file-upload"></i> Statistics
							</h4>
						</div>
					</div>
				
					<div class="row">
						<!-- chart js 추가 -->
						<!-- 1. doughnut chart -->
						<div class="col-12 col-md-6 card rounded">
							<div id="canvas-holder" style="width:100%">
								<canvas id="chart-area"></canvas>
							</div>
						</div>
						<!-- 2. vertical chart 
							    [- 전체 패널 개수
							    - 사용자 평균 개수]
							    - 내 패널 개수 -->
						<div class="col-12 col-md-6 card rounded">
							<div id="container" style="width: 100%;">
								<canvas id="canvas"></canvas>
							</div>
						</div>
					</div>
				</div>
				
				<!-- about Tab Start -->
				<!-- <div class="col-sm-12 col-md-12 carousel-item" id="about">
					<div class="row">
						<div class="col-sm-12 card mt-5 rounded">
							<h1>About Palette</h1>
						</div>
					</div>
				</div> -->
				 
				<!-- 영서 -->
				<!-- Contact Us Tab Start -->
				<div class="col-sm-12 col-md-12 carousel-item" id="contact">
					<div class="row">
						<div class="col-sm-12 card mt-5 rounded">
							<jsp:include page="/admin/contactus/index.jsp" />
						</div>
					</div>
				</div>
				
				<!-- fileupload Tab Start -->
				<div class="col-sm-12 col-md-12 carousel-item" id="fileupload"> 
					<div class="row">
						<div class="col-sm-12 card mt-5 rounded">
							<!-- 파일 업로드 레이아웃 소스. -->
							<jsp:include page="/admin/upload/index.jsp" />
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<!-- Apply Modal -->
	<div class="modal fade main-modal" id="applyModal" tabindex="-1" role="dialog" aria-labelledby="applyModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="palette-modal-header modal-header">
				  	<h5 class="palette-modal-title modal-title" style="margin-left: 10px;">
				  		<i class="custom-i fas fa-palette"> Palette</i>
				  	</h5>
				</div>
				<div class="modal-body">
					<p class="">패널을 적용할 페이지를 선택해주세요.</p>
					<p class="text-danger warning-msg" style="display: none">페이지를 하나 이상 선택해야 합니다.</p>
					<form class="apply-form">
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-all" value="ALL">
						  <label class="custom-control-label" for="chkbox-all">전체</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-main" value="MAIN">
						  <label class="custom-control-label" for="chkbox-main">메인</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-main-intro-member" value="MAIN_INTRO_MEMBER">
						  <label class="custom-control-label" for="chkbox-main-intro-member">회원만 접근 가능 페이지</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-product-list" value="PRODUCT_LIST">
						  <label class="custom-control-label" for="chkbox-product-list" >상품 분류</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-product-detail" value="PRODUCT_DETAIL">
						  <label class="custom-control-label" for="chkbox-product-detail" >상품 상세</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-product-search" value="PRODUCT_SEARCH">
						  <label class="custom-control-label" for="chkbox-product-search" >상품 검색</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-product-project" value="PRODUCT_PROJECT">
						  <label class="custom-control-label" for="chkbox-product-project" >상품 기획전</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-product-recent" value="PRODUCT_RECENT">
						  <label class="custom-control-label" for="chkbox-product-recent" >최근 본 상품</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-product-compare" value="PRODUCT_COMPARE">
						  <label class="custom-control-label" for="chkbox-product-compare" >상품 비교</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-main" value="BOARD_MAIN">
						  <label class="custom-control-label" for="chkbox-board-main" >게시판 메인</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-free-list" value="BOARD_FREE_LIST">
						  <label class="custom-control-label" for="chkbox-board-free-list" >게시판 목록</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-free-detail" value="BOARD_FREE_DETAIL">
						  <label class="custom-control-label" for="chkbox-board-free-detail" >게시판 상세 보기</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-free-write" value="BOARD_FREE_WRITE">
						  <label class="custom-control-label" for="chkbox-board-free-write" >게시물 쓰기</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-free-modify" value="BOARD_FREE_MODIFY">
						  <label class="custom-control-label" for="chkbox-board-free-modify" >게시물 수정</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-free-reply" value="BOARD_FREE_REPLY">
						  <label class="custom-control-label" for="chkbox-board-free-reply" >게시물 답글</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-free-secret" value="BOARD_FREE_SECRET">
						  <label class="custom-control-label" for="chkbox-board-free-secret" >게시물 비밀글</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-product-list" value="BOARD_PRODUCT_LIST">
						  <label class="custom-control-label" for="chkbox-board-product-list" >상품사용후기 목록</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-product-detail" value="BOARD_PRODUCT_DETAIL">
						  <label class="custom-control-label" for="chkbox-board-product-detail" >상품사용후기 상세보기</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-product-write" value="BOARD_PRODUCT_WRITE">
						  <label class="custom-control-label" for="chkbox-board-product-write" >상품사용후기 쓰기</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-product-modify" value="BOARD_PRODUCT_MODIFY">
						  <label class="custom-control-label" for="chkbox-board-product-modify" >상품사용후기 수정</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-product-reply" value="BOARD_PRODUCT_REPLY">
						  <label class="custom-control-label" for="chkbox-board-product-reply" >상품사용후기 답글</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-gallery-list" value="BOARD_GALLERY_LIST">
						  <label class="custom-control-label" for="chkbox-board-gallery-list" >갤러리 목록(갤러리 형)</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-gallery-detail" value="BOARD_GALLERY_DETAIL">
						  <label class="custom-control-label" for="chkbox-board-gallery-detail" >갤러리 상세 보기</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-gallery-write" value="BOARD_GALLERY_WRITE">
						  <label class="custom-control-label" for="chkbox-board-gallery-write" >갤러리 쓰기</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-gallery-modify" value="BOARD_GALLERY_MODIFY">
						  <label class="custom-control-label" for="chkbox-board-gallery-modify" >갤러리 수정</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-gallery-reply" value="BOARD_GALLERY_REPLY">
						  <label class="custom-control-label" for="chkbox-board-gallery-reply" >갤러리 답글</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-gallery-commentdel" value="BOARD_GALLERY_COMMENTDEL">
						  <label class="custom-control-label" for="chkbox-board-gallery-commentdel" >갤러리 댓글삭제</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-gallery-secret" value="BOARD_GALLERY_SECRET">
						  <label class="custom-control-label" for="chkbox-board-gallery-secret" >갤러리 비밀글</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-consult-list" value="BOARD_CONSULT_LIST">
						  <label class="custom-control-label" for="chkbox-board-consult-list" >1:1맞춤상담 목록</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-consult-detail" value="BOARD_CONSULT_DETAIL">
						  <label class="custom-control-label" for="chkbox-board-consult-detail" >1:1맞춤상담 상세</label>          
						</div>                                                                                              
						<div class="custom-control custom-checkbox">                                                        
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-consult-write" value="BOARD_CONSULT_WRITE">
						  <label class="custom-control-label" for="chkbox-board-consult-write" >1:1맞춤상담 쓰기</label>              
						</div>                                                                                              
						<div class="custom-control custom-checkbox">                                                        
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-consult-modify" value="BOARD_CONSULT_MODIFY">
						  <label class="custom-control-label" for="chkbox-board-consult-modify" >1:1맞춤상담 수정</label>              
						</div>                                                                                               
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-consult-reply" value="BOARD_CONSULT_REPLY">
						  <label class="custom-control-label" for="chkbox-board-consult-reply" >1:1맞춤상담 답글</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-main" value="MYSHOP_MAIN">
						  <label class="custom-control-label" for="chkbox-myshop-main" >마이쇼핑 메인 화면</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-order-list" value="MYSHOP_ORDER_LIST">
						  <label class="custom-control-label" for="chkbox-myshop-order-list" >마이쇼핑 주문내역</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-order-detail" value="MYSHOP_ORDER_DETAIL">
						  <label class="custom-control-label" for="chkbox-myshop-order-detail" >마이쇼핑 주문상세내역</label>          
						</div>                                                                                              
						<div class="custom-control custom-checkbox">                                                        
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-mileage-list" value="MYSHOP_MILEAGE_LIST">
						  <label class="custom-control-label" for="chkbox-myshop-mileage-list" >마이쇼핑 적립금내역</label>              
						</div>                                                                                              
						<div class="custom-control custom-checkbox">                                                        
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-coupon-coupon" value="MYSHOP_COUPON_COUPON">
						  <label class="custom-control-label" for="chkbox-myshop-coupon-coupon" >마이쇼핑 쿠폰내역</label>              
						</div>                                                                                               
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-deposit-list" value="MYSHOP_DEPOSIT_LIST">
						  <label class="custom-control-label" for="chkbox-myshop-deposit-list" >마이쇼핑 예치금내역</label>
						</div>
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-wishlist" value="MYSHOP_WISHLIST">
						  <label class="custom-control-label" for="chkbox-myshop-wishlist" >마이쇼핑 관심상품 목록</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-boardlist" value="MYSHOP_BOARDLIST">
						  <label class="custom-control-label" for="chkbox-myshop-boardlist">나의 게시글</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-addr-list" value="MYSHOP_ADDR_LIST">
						  <label class="custom-control-label" for="chkbox-myshop-addr-list" >배송주소록목록</label>
						</div>
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-addr-regist" value="MYSHOP_ADDR_REGIST">
						  <label class="custom-control-label" for="chkbox-myshop-addr-regist" >배송주소록등록</label>
						</div>
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-myshop-addr-modify" value="MYSHOP_ADDR_MODIFY">
						  <label class="custom-control-label" for="chkbox-myshop-addr-modify" >배송주소록수정</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-order-basket" value="ORDER_BASKET">
						  <label class="custom-control-label" for="chkbox-order-basket" >장바구니 화면</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-order-giftlist" value="ORDER_GIFTLIST">
						  <label class="custom-control-label" for="chkbox-order-giftlist" >사은품 안내</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-inquiry-list" value="BOARD_INQUIRY_LIST">
						  <label class="custom-control-label" for="chkbox-board-inquiry-list" >대량구매문의 목록</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-inquiry-modify" value="BOARD_INQUIRY_MODIFY">
						  <label class="custom-control-label" for="chkbox-board-inquiry-modify" >대량구매문의 수정</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-inquiry-write" value="BOARD_INQUIRY_WRITE">
						  <label class="custom-control-label" for="chkbox-board-inquiry-write" >대량구매문의 등록</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-inquiry-detail" value="BOARD_INQUIRY_DETAIL">
						  <label class="custom-control-label" for="chkbox-board-inquiry-detail" >대량구매문의 상세보기</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-attend-stamp" value="ATTEND_STAMP">
						  <label class="custom-control-label" for="chkbox-attend-stamp">출석체크(달력형)</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-calendar-day" value="CALENDAR_DAY">
						  <label class="custom-control-label" for="chkbox-calendar-day">캘린더게시판(일간)</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-calendar-field" value="CALENDAR_FIELD">
						  <label class="custom-control-label" for="chkbox-calendar-field">캘린더게시판 추가항목</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-calendar-month" value="CALENDAR_MONTH">
						  <label class="custom-control-label" for="chkbox-calendar-month">캘린더게시판(월간)</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-calendar-week" value="CALENDAR_WEEK">
						  <label class="custom-control-label" for="chkbox-calendar-week">캘린더게시판(주간)</label>
						</div>
						<hr>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-member-adminfail" value="MEMBER_ADMINFAIL">
						  <label class="custom-control-label" for="chkbox-member-adminfail">접근제한</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-memo-list" value="BOARD_MEMO_LIST">
						  <label class="custom-control-label" for="chkbox-board-memo-list" >한줄메모 목록</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-coupon-couponzone" value="COUPON_COUPONZONE">
						  <label class="custom-control-label" for="chkbox-coupon-couponzone" >쿠폰존</label>
						</div>
						<div class="custom-control custom-checkbox">
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-board-urgency" value="BOARD_URGENCY">
						  <label class="custom-control-label" for="chkbox-board-urgency" >긴급문의접수</label>
						</div>
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-shopinfo-faq" value="SHOPINFO_FAQ">
						  <label class="custom-control-label" for="chkbox-shopinfo-faq" >이용안내(FAQ)</label>
						</div>
						<div class="custom-control custom-checkbox">                                                         
						  <input type="checkbox" class="custom-control-input chkbox" name="chkbox-activepage" id="chkbox-shopinfo-company" value="SHOPINFO_COMPANY">
						  <label class="custom-control-label" for="chkbox-shopinfo-company" >회사소개</label>
						</div>
						
						<input type="hidden" id="modalPanelId" value="">
					</form>
					
				</div>
				<div class="palette-modal-footer modal-footer">
					<button type="button" class="palette-modal-btn panel-apply-btn btn btn-sm btn-secondary bg-primary">적용</button>
					<button type="button" class="palette-modal-btn btn btn-sm btn-secondary bg-info" data-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /Apply Modal -->
	
	<!-- Modal -->
	<div class="modal fade main-modal" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="palette-modal-header modal-header">
				  	<h5 class="palette-modal-title modal-title" style="margin-left: 10px;">
				  		<i class="custom-i fas fa-palette"> Palette</i>
				  	</h5>
				</div>
				<div class="modal-body">
					<p></p>
				</div>
				<div class="palette-modal-footer modal-footer">
					<button type="button" class="palette-modal-btn panel-delete-btn btn btn-sm btn-secondary bg-danger d-none" data-dismiss="modal">삭제</button>
					<button type="button" class="palette-modal-btn btn btn-sm btn-secondary bg-info" data-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 적용, 해체 등에 대한 script -->
	<script>
	function buttonChange(clickChangeState, autoChangeState) {
		var clickStatePanelId = clickChangeState.panelId;
		var clickStateIsApply = clickChangeState.isApply;
		if(clickStateIsApply == true) {
			$('#state-td'+clickStatePanelId).text('Active');
			$('#'+clickStatePanelId).html('<i class="fas fa-ban"></i> 해제');
			$('#'+clickStatePanelId).attr('class', 'btn btn-default btn-sm btn-apply');
			$('#'+clickStatePanelId).data('apply', false);
			$('#'+clickStatePanelId).attr('data-apply', 'false');
		} else {
			$('#state-td'+clickStatePanelId).text('Inactive');
			$('#'+clickStatePanelId).html('<i class="fas fa-play"></i> 적용');
			$('#'+clickStatePanelId).attr('class', 'btn btn-info btn-sm btn-apply');
			$('#'+clickStatePanelId).data('apply', true);
			$('#'+clickStatePanelId).attr('data-apply', 'true');
		}
		
		if(autoChangeState != null) {
			var autoStatePanelId = autoChangeState.panelId;
			var autoStateIsApply = autoChangeState.isApply;
			
			$('#state-td'+autoStatePanelId).text('Inactive');
			$('#'+autoStatePanelId).html('<i class="fas fa-play"></i> 적용');
			$('#'+autoStatePanelId).attr('class', 'btn btn-info btn-sm btn-apply');
			$('#'+autoStatePanelId).data('apply', true);
			$('#'+autoStatePanelId).attr('data-apply', 'true');
		}
	}
	
	/* 패널 적용,해제 ajax */
	function stateChange(panelId, state, datas, list) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url: '${pageContext.servletContext.contextPath}/api/cafe24/apply/'+panelId,
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(datas),
			// Ajax Request 헤더에 CSRF 토큰 정보 추가
		 	beforeSend: function(request) {
				request.setRequestHeader(header, token);
			},
			success: function(response) {
				if(state == true){
					$('#applyModal').modal('hide');
					console.log(panelId);
					$('#dpLocation'+panelId).text(list);
				}
				buttonChange(response.data.clickChangeState, response.data.autoChangeState);
			}
		});
	};
	
	$(function() {
		
		$('#applyModal').on('hidden.bs.modal', function() {
			$('.chkbox').each(function(index, obj) {
				$(obj).removeAttr('disabled').prop('checked', false);
			});
		});
		
		/* panel 추가 시 페이지 선택 dialog */
		/* var pageSelectDialog = $("#dialog-select-form").dialog({
			autoOpen : false, //자동으로 띄우지 X
			modal : true,
			buttons : {
				'적용' : function() {
					if($('.chkbox:checked').length == 0) {
						$('.validateTips.normal').hide();
						$('.validateTips.error').show();
						return;
					}
					
					var datas = {"state": true};
					var list = [];
					if($('#chkbox-all').prop('checked')){
						console.log($('#chkbox-all').val());
						list.push($('#chkbox-all').val());
					} else {
						$('.chkbox:checked').each(function(index, obj) {
							list.push(obj.value);
						});	
					}
					
					datas['data'] = list; 
					stateChange($(this).data("id"), true, datas, $(this));
				}
			},
			
			close : function() {
				$('.chkbox').prop('disabled', false);
				$('.chkbox').prop('checked', false);
				$('.validateTips.normal').show();
				$('.validateTips.error').hide();
			}
		}); *///pageSelectDialog
		
		
		
		/* 적용/해제 버튼 클릭 */
		$('button.btn-apply').click(function(evt) {
			evt.stopPropagation();
			var state = false;
			var panelId = $(this).attr('id');
			var aaa = $(this).data('apply');
			
			if( aaa == true) {
				var btn = $('button.btn-apply');
				for(var i = 0; i < btn.length; i++){
					if($(btn[i]).data('apply') === false){
						alert("이미 적용 된 패널이 있습니다.\n해제 후 적용해주세요.");
						return false;
					}
				}
				
				$("#applyModal").modal();
				$('#modalPanelId').val(panelId);
				return ;
			}
			
			stateChange(panelId, false, {"state": false});
		});

		
		/* modal 내 적용 버튼 눌렸을 때. */
		$(".panel-apply-btn").on("click", function(){
			if($('.chkbox:checked').length == 0) {
				$('.warning-msg').show();
				return;
			}
			
			var datas = {"state": true};
			var list = [];
			if($('#chkbox-all').prop('checked')){
				$('.chkbox:not(#chkbox-all)').each(function(index, obj){
					console.log(obj.value)
						list.push(obj.value);
				});
				/* console.log($('#chkbox-all').val());
				list.push($('#chkbox-all').val()); */
			} else {
				$('.chkbox:checked').each(function(index, obj) {
					list.push(obj.value);
				});	
			}
			
			datas['data'] = list; 
			stateChange($('#modalPanelId').val(), true, datas, list);
		});
		
		/* dialog checkbox event */
		$('#chkbox-all').change(function() {
			if($(this).prop('checked')) {
				$('.chkbox:not(#chkbox-all)').attr('disabled', 'true');
				return;
			}
			
			$('.chkbox:not(#chkbox-all)').removeAttr('disabled');
		});
		
		$('.chkbox:not(#chkbox-all)').change(function() {
			if($('.chkbox:checked:not(#chkbox-all)').length > 0) {
				$('#chkbox-all').attr('disabled', 'true');
			} else if($('.chkbox:checked:not(#chkbox-all)').length == 0) {
				$('#chkbox-all').removeAttr('disabled');
			}
		});

		/* 삭제 버튼 click */
		// 수정
		// 1. 적용 해제를 모든 row에 대해 검사하는 문제 수정
		//	131: if($(.state).data('apply') == false) 수정
		// 2. 삭제 버튼이 눌린 tr을 가져오도록 수정
		//	138: removeTarget = $(this).parent().parent(); 추가
		//	152: $(removeTarget).remove(); 추가
		$('.btn-delete').click(function(evt) {
			evt.stopPropagation();
			
			if($(this).prev().prev().data('apply') == false) {
				alert('적용 해제 후 삭제해주세요.');
				return;
			}
			
			var panelId = $(this).data('panelid');
			var count = $(this).data('count');
			var removeTarget = $(this).parent().parent();
			
			// CSRF 요청에 필요한 헤더 정보 추출
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			/* modal 출력 */
			$("#confirmModal").modal();
			
			/* modal 출력 전 이벤트 */
			$("#confirmModal").on('shown.bs.modal', function () {
				/* <i class="fas fa-exclamation-circle"></i> */ 
				$(".modal-body", this).text('정말 삭제합니까?');
				$(".panel-delete-btn", this).removeClass("d-none");
		    });
			
			/* modal 내 삭제 버튼 눌렸을 때. */
			$(".panel-delete-btn").on("click", function(){
				deleteAjax(panelId, count, removeTarget, header, token);
			});
			
		});
		
		/* 삭제 Ajax 메소드 */
		function deleteAjax(panelId, count, removeTarget, header, token){
			/* Ajax 요청 시작 */
			$.ajax({
				url: '${pageContext.servletContext.contextPath}/api/app/panel/'+panelId,
				type: 'DELETE',
				dataType: 'json',
				// Ajax Request 헤더에 CSRF 토큰 정보 추가
				beforeSend: function(request) {
					request.setRequestHeader(header, token);
				}, 
				success: function(response) {
					if(response.data != 'removed') {
						console.log('can not remove');
						return;
					}
					
					$(removeTarget).remove();
					$("#panelArea").hide();
					
					for(i=1;i<=${fn:length(list)};i++) {
						$('#tblPanelList tbody tr:nth-child('+i+') td:first').text(i);
					}
					alert('삭제되었습니다.');
				}
			});
		}
		
	});
	</script>
</body>
</html>