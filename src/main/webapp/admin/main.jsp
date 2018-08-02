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

<!-- jQuery library -->
<script src="${pageContext.servletContext.contextPath }/static/jquery/1.11.1/jquery.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/mustachejs/2.2.1/mustache.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/popper.js/1.14.3/dist/umd/popper.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/admin/assets/js/cookie.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/admin/assets/js/preview_panel.js"></script>

<!-- Google Chart -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
	google.charts.load('current', {'packages' : ['corechart']});
	google.charts.setOnLoadCallback(drawVisualization);
	google.charts.setOnLoadCallback(drawChart); 
	
	function drawVisualization() { 
		var data = google.visualization.arrayToDataTable([
				['Month', 'Bolivia', 'Ecuador', 'Madagascar', 'Papua New Guinea', 'Rwanda', 'Average'],
				['2004/05',  165,      938,         522,             998,           450,      614.6],
				['2005/06',  135,      1120,        599,             1268,          288,      682],
				['2006/07',  157,      1167,        587,             807,           397,      623],
				['2007/08',  139,      1110,        615,             968,           215,      609.4],
				['2008/09',  136,      691,         629,             1026,          366,      569.6]
			]);
		var options = {
				title : 'Monthly Coffee Production by Country',
				vAxis: {title: 'Cups'},
				hAxis: {title: 'Month'}, 
				seriesType: 'bars',
				series: {5: {type: 'line'}},
				width : '100%',
				height : '100%'
			};
		
		var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
		chart.draw(data, options);
	}
	
	function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Task', 'Hours per Day'],
          ['Work',     11],
          ['Eat',      2],
          ['Commute',  2],
          ['Watch TV', 2],
          ['Sleep',    7]
        ]);

        var options = {
          title: 'My Daily Activities',
          pieHole: 0.4,
          width : '100%',
          height : '100%'
        };

        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        chart.draw(data, options);
      }
</script>

<style>
@import url(https://fonts.googleapis.com/earlyaccess/notosanskr.css);

* {
	margin: 0;
	padding: 0;
	font-family: 'Noto Sans KR', 'Dotum', '돋움', 'arial', 'verdana', sans-serif;
}

body{
	height : 100%;
	background-color: #563d7c;
} 

.container {
	position: relative;
}

table, table thead th {
	text-align: center;
}

.navbar{
	background-color: #563d7c;
	color : white;
}

.navbar-brand{
	margin-left: -15px;
}

#panellist{
	width: 100%;
}

.nav-li-inverse{
	background-color: white;
	color: #563d7c; !important;
}

#palette-tab{
	width: 100%;
}

#palette-tab > .nav-item{
	width: 100%;
}

.none-event{
	pointer-events : none;
	color: white !important;
}

.nav-tabs{
	border: none;
}

.nav-tabs li{
	margin-right: 15px;
}

.nav-tabs a {
	color: #949494;
}

.nav-tabs .nav-link {
	border: none;
}

.navbar-nav .nav-item .nav-link.active {
	background: inherit;
}

.card{
	box-shadow: 4px 4px 4px #AAAAAA;
}

.carousel-inner{
	overflow: visible;
}

.sub-tab-title{ 
	font-weight: 700;
}

.panel-detail-info-div p{
	display: inline;
}


/* 모달 CSS */
.palette-modal-header{
	padding: 0;
	background-color: #563d7c;
}

.palette-modal-title{
	padding-top : 5px;
	padding-left : 0px; 
	color : #fff;
	border-top-left-radius: 12px;
	margin-left: 10px;
}

.modal-footer{
	padding: 0.625em;
}

.selected-func-table-div{
	margin: 0px auto;
}

.panel-name-td{
	cursor: pointer;
}

</style>

<script type="text/javascript">
function renderDetail(idx, data) {
	console.log(idx);
	var template = $('#mustache-panellist-template').html();
	Mustache.parse(template);
	var rendered = Mustache.render(template, data[idx]);
	$('#mustache-result').html('').append(rendered);	
}

$(document).ready(function(){
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
		console.log(data);
	</c:forEach>
	
	console.log(data);
	
	$(".nav-tabs a").click(function(event){
		event.stopPropagation;
		$(":not(this)").children("span.current-nav").text("");
		$(this).children("span").append('<i class="fas fa-check mr-1"></i>');
		
		$(":not(this)").removeClass("none-event");
		$(this).addClass("none-event");
	});
	
	$('.carousel').carousel({
		interval : false /* auto slide off */
	})
	
 	$('#adminTabContent').on('slide.bs.carousel', function (evt) {
    	$('#adminTabContent .controls li.active').removeClass('active');
    	$('#adminTabContent .controls li:eq(' + $(evt.relatedTarget).index() + ')').addClass('active');
    });
	
	$("#tbl-panel-list > tbody > tr").hover(function(){
		$(this).toggleClass("table-success");
	});
	
	$("#tbl-panel-list > tbody .panel-name-td").click(function(){
		renderDetail(($(this).prev().text()-1), data);
	});
});
</script> 
<title>Palette</title>
</head>

<body>
	<!-- Navigation -->  
    <nav class="navbar navbar-expand-lg navbar-dark static-top">
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
	            	
	            	<li data-target="#adminTabContent" data-slide-to="2">
	              		<a href="#">
	              		<span class="current-nav"></span>
	              		About</a>
	            	</li>
	            	
	            	<li data-target="#adminTabContent" data-slide-to="3">
	              		<a href="#">
	              		<span class="current-nav"></span>
	              		FileUpload</a>
	            	</li>
	          	</ul>
	      	</div>
    	</div>
    </nav>
     
    <!-- <div id="panel-area">
    </div>  -->
  
    <!-- Page Content -->
    <div class="container">
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
								<table id="tbl-panel-list" class="table table-striped table-centered mb-0 table-sm">
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
															<button class="btn btn-default btn-sm state" id="${panel.panelId }" data-apply="false"> 해제</button>
														</c:when>
														
														<c:otherwise>
															<button class="btn btn-info btn-sm state" id="${panel.panelId }" data-apply="true"><i class="fas fa-play"></i> 적용</button>
														</c:otherwise>
													</c:choose>
													
													<a href="${pageContext.servletContext.contextPath }/update/${panel.panelId }" class="btn btn-success disabled btn-sm" style="text-decoration: line-through"><i class="fas fa-wrench" style="text-decoration: line-through"></i> 수정</a> 
													<button class="btn btn-danger btn-delete btn-sm" data-panelid="${panel.panelId }" data-count="${stat.count }"><i class="fas fa-trash-alt"></i>삭제</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
							<!-- jQuery UI -->
							<div id="dialog">
								<div id="dialog-select-form" title="추가한 패널 동작 페이지 선택"
									style="display: none">
									<p class="validateTips normal">방금 만들어진 패널이 동작할 페이지를 선택해주세요.</p>
									<p class="validateTips error" style="display: none">하나 이상 선택해야 합니다.</p>
									<form>
										<input type="checkbox" class="chkbox" id="chkbox-all"
											name="chkbox-activepage" value="ALL"> 전체 페이지 <br> <input
											type="checkbox" class="chkbox" id="chkbox-main"
											name="chkbox-activepage" value="MAIN"> 메인 페이지<br> <input
											type="checkbox" class="chkbox" id="chkbox-product"
											name="chkbox-activepage" value="PRODUCT_LIST"> 상품 페이지<br>
									</form>
								</div>
					
								<div id="dialog-message" title="tets" style="display: none">
									<p></p>
								</div>
							</div>
						</div>
					</div>
					
					<!-- Panel Detail Info -->
					<div class="col-sm-12 col-md-12 card mt-5 rounded panel-detail-info-div">
						<div class="main card-body">
							<div class="row mb-3"> 
								<div class="col-sm-6">
									<h4 class="sub-tab-title"><i class="fas fa-info-circle"></i> Panel Detail Info</h4>
								</div>
							</div>
							<div id="mustache-result">
								<h5>Please Click Panel List</h5>
							</div>
							<script id="mustache-panellist-template" type="text/template">
							<!-- 보여줄 정보 
								패널명
								패널 position 정보
								패널 생성일
								패널이 적용된 지점 (dp_location)
								적용된 기능 list + 순서
								적용된 테마 이름
							 -->
							<div class="row">
								<div class="col-sm-6">
									<p class="text-monospace">패널명:</p>
									<p>{{panelName}}</p> 
								</div>
								<div class="col-sm-3">
									<p class="text-monospace">패널 위치:</p>
									<p>{{panelPosition}}</p>
								</div>
								<div class="col-sm-3">
									<p class="text-monospace">생성일:</p>
									<p>{{panelCreatedDate}}</p> 
								</div>
							</div>
							
							<div class="row mt-4">
								<div class="col-sm-8">
									<p class="text-monospace">적용 화면:</p>
									<p>{{^scriptDpLocation}}없음{{/scriptDpLocation}}{{#scriptDpLocation}}{{scriptDpLocation}}{{/scriptDpLocation}}</p>
								</div>
								
								<div class="col-sm-4">
									<p class="text-monospace">적용테마명:</p>
									<p>{{themeTitle}}</p>
								</div>
							</div>
							
							<div class="row mt-4 d-flex">
								<div class="col-sm-8">
									<p class="text-monospace">선택된 기능 정보</p>
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
							</script>
						</div>
					</div>
				</div>
			
				<!-- Statistics Tab Start --> 
				<div class="col-sm-12 col-md-12 carousel-item" id="stat">
					<div class="row" style="height: 100%;">
						<div class="col-sm-6 card mt-5 rounded" style="height: 500px; ">
							<div id="chart_div"> 
							</div> 
						</div>
						
						<div class="col-sm-6 card mt-5 rounded">
							<div id="donutchart"></div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-6 card mt-5 rounded">
							<h1>stat3</h1>
						</div>
						
						<div class="col-sm-6 card mt-5 rounded">
							<h1>stat4</h1>
						</div>
					</div>
				</div>
				
				<!-- about Tab Start -->
				<div class="col-sm-12 col-md-12 carousel-item" id="about">
					<div class="row">
						<div class="col-sm-12 card mt-5 rounded">
							<h1>About Palette</h1>
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
	
	<!-- Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
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
					<button type="button" class="palette-modal-btn panel-delete-btn btn btn-sm btn-secondary bg-danger d-none" data-dismiss="modal"> 삭제</button>
					<button type="button" class="palette-modal-btn btn btn-sm btn-secondary bg-info" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	function buttonChange(clickChangeState, autoChangeState) {
		var clickStatePanelId = clickChangeState.panelId;
		var clickStateIsApply = clickChangeState.isApply;
		
		console.log(clickStatePanelId+' '+clickStateIsApply)
		if(clickStateIsApply == true) {
			$('#state-td'+clickStatePanelId).text('Active');
			$('#'+clickStatePanelId).text('해제');
			$('#'+clickStatePanelId).attr('class', 'btn btn-default state');
			$('#'+clickStatePanelId).data('apply', false);
		} else {
			$('#state-td'+clickStatePanelId).text('Inactive');
			$('#'+clickStatePanelId).text('적용');
			$('#'+clickStatePanelId).attr('class', 'btn btn-info state');
			$('#'+clickStatePanelId).data('apply', true);
		}
		
		if(autoChangeState != null) {
			var autoStatePanelId = autoChangeState.panelId;
			var autoStateIsApply = autoChangeState.isApply;
			
			$('#state-td'+autoStatePanelId).text('Inactive');
			$('#'+autoStatePanelId).text('적용');
			$('#'+autoStatePanelId).attr('class', 'btn btn-info state');
			$('#'+autoStatePanelId).data('apply', true);
		}
	}


	/* 패널 적용,해제 ajax */
	function stateChange(panelId, state, datas, dialog) {
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
					dialog.dialog('close');	
				}
				buttonChange(response.data.clickChangeState, response.data.autoChangeState);
			}
		});
	};

	$(function() {
		/* panel 추가 시 페이지 선택 dialog */
		var pageSelectDialog = $("#dialog-select-form").dialog({
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
						$('.chkbox').each(function(index, obj){
							if(!this.checked){
								list.push(obj.value);
							}
						});
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
		});//pageSelectDialog
		
		/* 해제 버튼 클릭 */
		$('.state').click(function() {
			var state = false;
			var panelId = $(this).attr('id');
			var aaa = $(this).data('apply');
			if( aaa== true) {
				pageSelectDialog.dialog('open').data("id", panelId);
				return;
			}
			
			stateChange(panelId, false, {"state": false}, null);
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
		$('.btn-delete').click(function() {
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
			
			/* $("#confirmModal").on('hidden.bs.modal', function () {
				$(".modal-body", this).text("");
				$(".panel-delete-btn", this).addClass("d-none");
		    }); */ 
			
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
					
					//$('#tbl-panel-list tbody tr:nth-child('+count+')').remove();
					$(removeTarget).remove();
					
					for(i=1;i<=${fn:length(list)};i++) {
						$('#tbl-panel-list tbody tr:nth-child('+i+') td:first').text(i);
					}
					alert('삭제되었습니다.');
				}
			});
		}
		
	});
	</script>
</body>
</html>