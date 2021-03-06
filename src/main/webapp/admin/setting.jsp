<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/jquery-ui/1.12.1/jquery-ui.min.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/bootstrap/4.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"/>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/static/font-awesome/5.1.0/css/all.css"/>
 
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/admin/assets/css/setting.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/admin/assets/css/default-theme.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/template/palette.css">

<script src="${pageContext.servletContext.contextPath }/static/jquery/1.11.1/jquery.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/jquery-ui/1.12.1/jquery-ui.js"></script>
<script	src="${pageContext.servletContext.contextPath }/static/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script	src="${pageContext.servletContext.contextPath }/static/mustachejs/2.2.1/mustache.min.js"></script> 
<script src="${pageContext.servletContext.contextPath }/admin/assets/js/preview_panel.js"></script>
<script src="${pageContext.servletContext.contextPath }/static/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

<title>새 패널 만들기</title>
</head>

<body>
<div id="setting-container" class="container-fluid" style="height: 90%; padding-left: 0; min-width: 900px;">
	<input type="hidden" class="panel-division-type" name="admin" value="admin">
	 
	<div class="palette-title row fouc fouc-flex"> 
		<div class="col-sm-9">
			<h4><i class="fas fa-plus-square"></i> 새 패널 만들기</h4>
		</div>
		
		<div class="col-sm-3" style="text-align: right;">
			<button class="btn btn-link btn-delete btn-sm text-white-50" id="gomain"><i class="fas fa-undo-alt" style="margin-bottom: 0;"></i> 돌아가기</button>
		</div>
	</div>
 
	<!-- tab START -->
	<div id="panel-tabs" class="row fouc">
		<ul class="nav nav-tabs" id="myTab">
			<c:forEach var="tab_element" items="${tabs.map }" varStatus="stat">
				<c:choose> 
					<c:when test="${stat.index == 0 }">
						<li class="nav-item step" id="tab-li-${stat.index}" data-tabname=${tab_element.value.jspFile } style="padding-left: 0px;">
							<a class="nav-link" id="${tab_element.value.jspFile }-tab" data-tabno=${stat.index } data-toggle="tab" role="tab" href="#${tab_element.value.jspFile }" aria-controls="${tab_element.value.jspFile }" aria-selected="true">
								${tab_element.value.tabName }
							</a>
							<span class="chevron"></span>
						</li> 
					</c:when> 
					
					<c:otherwise>  
						<li class="nav-item step" id="tab-li-${stat.index}" data-tabname=${tab_element.value.jspFile }>
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
			<div id="tabProgress" class="progress-bar progress-bar-striped progress-bar-animated bg-warning" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 2%"></div> 
		</div>
		
		<!-- 설정 값을 넘기기 위한 hidden form -->
		<form id="saveform" method="POST" action="${pageContext.servletContext.contextPath }/setting/create">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }" />
		</form>
		
		<div class="tab-content fouc" id="myTabContent">
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
			<ul class="pagination custom-pagination-ul">
				<li><button type="button" class="btn btn-danger" id="pager-previous"><i class="fas fa-chevron-left"></i> Previous</button></li>
				<li><button type="button" class="btn btn-primary" id="pager-next">Next <i class="fas fa-chevron-right"></i></button></li> 
				<li><button type="button" class="btn btn-success" id="pager-save"><i class="fas fa-plus"></i> Save</button></li>
			</ul>
		</div>
	</div>
	
	<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="palette-modal-header modal-header">
				  <h5 class="palette-modal-title modal-title" style="margin-left: 10px;">
				  	<i class="custom-i fas fa-palette"></i> Palette
				  </h5>
				    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true" style="padding: 5px;">&times;</span>
					</button>
				</div>
				<!-- 180807 modal-body충돌나서 class 추가 - hwi -->
				<div class="modal-body modal-setting">
					<p></p>
				</div>
				<div class="palette-modal-footer modal-footer">
					<button type="button" class="palette-modal-btn btn btn-secondary bg-danger" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>

</body>

<script type="text/javascript">
$(window).on("beforeunload", function(e){
	var message = "페이지를 나가면 메인 화면으로 이동 합니다. 정말 나가시겠습니까?";
	e.returnValue = message;
	return message;
});

function includeTargetElement() {
  	var element = document.createElement("div");
  	element.setAttribute("id", "panelArea"); 
  	document.body.appendChild(element);
}

var tabsMaxSize = ${fn:length(tabs.map)};
var hash = window.location.hash;  // ex) /create#func

if(hash=='' || hash==null){
	window.location = "#type";
	hash = "#type";   
} 

/* hash 위치에 맞는 탭 출력 */
$('#myTab a[href="'+hash+'"]').tab('show');

$('#myTab a').on('shown.bs.tab', function(event){
	$(".toggle-on").css({
		"padding-left" : "12px",
		"font-weight" : "600"
	})
	$(".toggle-off").css({
		"padding-right" : "12px"
	})
	
	$(".fouc-flex").css("display", "flex");
	$(".fouc").show();
});

//페이지 초기화
pageRender(0, tabsMaxSize, hash);

//페이지 초기화 메소드 
function pageRender(currentIdx, tabsMaxSize, hash){
	// 패널 미리보기 div 생성 
	includeTargetElement();
	
	// 페이지 크기 조정 함수.
	resizeDone();
	
	var currentLocation = null;
	
	$("#myTab").children().each(function(index){
		if(hash == "#"+$(this).data("tabname")){
			currentLocation = $(this);
			currentIdx = index;
		}
	});
	
	currentLocation.children("a").removeClass("active show");
	
	var prevAllTag = $(currentLocation).prevAll().add(currentLocation);	
	
	prevAllTag.each(function(){ 
		$(this).children("a").css({
			"color" : "#fff"
		});

		$(this).children("span").addClass("chevron complete");
		
		$(this).css({
			"background-color" : "#58C9B9" /* 현재 탭의 li 태그 백 그라운드 색상 */
		});
	});
	
	if(currentLocation.is(':first-child')){
		$("#pager-save").hide();
		$("#pager-previous").hide();
	}else if(currentLocation.is(':last-child')){
		$("#pager-next").hide();
		$("#pager-previous").show();
	}else{
		$("#pager-save").hide();
	}
	
	progressBarChange(currentIdx, tabsMaxSize);
}

function resizeDone(){
	var currentWidth = window.innerWidth;
	var rate;
	if(currentWidth < 1000){
		rate = 50
	} else {
		rate = ((currentWidth - 530)/currentWidth)*100;
	}
	
	$("#panel-tabs").css({
		"width" : rate+"%"
	});
	
	$(".palette-title").css({
		"width" : rate+"%"
	});
}

/* DOM이 모두 준비되고 */
$(document).ready(function(){
	window.$Palette = $;
	
	
	/* 패널 미리보기 수행 */
	$.panel.init({
		visible : true
	});
	
	$(window).on('resize', function(){
		var timer = null;
		clearTimeout(timer);
		timer = setTimeout(resizeDone, 150);
	});
	
	/* 메인가기 버튼 누르면 동작 */
	$("#gomain").click(function(){
		location.href = '/mammoth/';
	});
	
	// Next 버튼 클릭 시.
	$("#pager-next").click(function(event){
		var values = findCurrentTag("next");      // 현재 탭 정보 얻어오기.
		var currentIdx = values.currentIdx;
		var currentPage = values.currentPage;
		var currentTag = values.currentTag;
		var nextTag = values.otherTag;
		var nextPage = values.otherPage;
		
		// 다음 위치로 +1
		currentIdx += 1;
		if (currentIdx == tabsMaxSize) {
			currentIdx = tabsMaxSize-1;
		} 
		
		/* 다음 단계로 넘어갈때 검사 필터 */
		if(funcNextProcess(currentPage)==false){
			return;
		}
		
		currentTag.parent().removeClass("step").addClass("complete");
		
		// aria-selected -> false 설정 
		currentTag.attr("aria-selected", "false");
		nextTag.attr("aria-selected", "true");
		
		// 다음 태그 배경 색, 글자 색 변경 
		nextTag.parent().css("background-color", "#58C9B9");
		nextTag.css("color", "#fff"); 
		nextTag.siblings("span").addClass("chevron complete"); 
		
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
			$("#pager-previous").show();
			$("#pager-next").hide();
			$("#pager-save").show();
		}
		
		// 프로그래스 바 변경.
		progressBarChange(currentIdx, tabsMaxSize);
		
		// 다음 페이지 hash값 변경
		var id = $(nextTag).attr("href").substr(1);
		window.location.hash = id; 
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
		currentTag.removeClass("show active");
		
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
		
		if(currentIdx == 0) {
			$("#pager-previous").hide();
			$("#pager-next").show();
			$("#pager-save").hide();
		} else {
			$("#pager-next").show();
			$("#pager-save").hide();
		}
		
		// 프로그래스 바 변경. 
		progressBarChange(currentIdx, tabsMaxSize);
		
		// 다음 페이지 hash값 변경
		var id = $(previousTag).attr("href").substr(1);
		window.location.hash = id; 
	});
	
	/* save 버튼 누를 때 */
	$("#pager-save").on("click", function(evt){
		/* 여기서는 beforeunload 이벤트 해제 */
		$(window).unbind('beforeunload');
		
		var values = findCurrentTag("previous");      // 현재 탭 정보 얻어오기.
		var currentPage = values.currentPage;
		
		/* 다음 단계로 넘어갈때 검사 필터 */
		if(funcNextProcess(currentPage)==false){
			console.log(currentPage);
			return;
		}
		
		// 패널 이름 정보 추가
		var panelname = $("#inputPanelName").val();
		appendHiddenElement($("#saveform"), "panelname", panelname);
		
		// form 수정
		// ui-state-disabled가 없는 li --> 선택 된 li Element
		$($("#sortable").children("li").not(".ui-state-disabled")).each(function(){
			var funcid = $(this).data("funcid");
			var funcorder = $(this).attr("funcorder");
			
			appendHiddenElement($("#saveform"), "funcid", funcid);
			appendHiddenElement($("#saveform"), "funcorder", funcorder);
		});
		
		/* form에 선택된 themeid 정보 추가 */
		appendHiddenElement($("#saveform"), "themeid", $(".card.seleted").data("themeid"));
		appendHiddenElement($("#saveform"), "position", $("#positionTypeDiv :radio[name=position]:checked").val());
		
		/* 이벤트 동작 중지 */
		evt.preventDefault();
		
		/* 제출 여부 묻기 */
		if(!confirm('패널을 저장하시겠습니까?')){
			return;
		}
		
		/* 세션 storage clear */
		sessionStorage.clear();
		
		/* form 제출 */ 
		console.log("before submit");
		$("#saveform").submit();
		
		/* $("#saveform").submit(function(){
			//console.log("form is submitted! ");
			//$(window).unbind('beforeunload');
		}); */ 
	})
});

/* 현재 탭 위치정보 및 다음/이전 탭 정보를 구하는 메소드 */
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

/* 탭 진행도에 따른 progress Bar 변경 메소드 */
function progressBarChange(currentIdx, tabsMaxSize){
	var rate = ( currentIdx/tabsMaxSize )*100;
	var bgColor;
	 
	if(currentIdx == 0){
		rate = 2;
	}
	
	if(rate <= 25){
		bgColor = "bg-danger";
	}else if(rate <= 50){
		bgColor = "bg-warning"; 
	}else if(rate <= 75){
		bgColor = "bg-success";
	}
	
	$("#tabProgress").css({
		"width" : rate+"%"
	}).removeClass($("#tabProgress").attr('class').split(" ").pop()).addClass(bgColor);
}

/* 다음 단계 넘어갈 때 처리 */
function funcNextProcess(currentPage){
	var data = [];
	
	if(currentPage == "type"){
		
		if($("#inputPanelName").val()=="" || $("#confirmFlag").val()=="false"){
			// 180807 hwi 충돌 떄문에 클래스 변경
			$(".modal-setting").text("패널 명을 입력&확인 해주세요.");
			$("#confirmModal").modal('show');
			return false;
		}
		
	}else if(currentPage == "func"){ /* 기능 결정 페이지 next 할 때. */
		var $li = $("#sortable").children("li");
		var count = 0;
		$li.each(function(){
			if($(this).hasClass("ui-state-disabled") == true){
				count++;
			}
		});
		
		if(count==$li.length){
			// 180807 hwi 충돌 떄문에 클래스 변경
			$(".modal-setting").text("기능을 1개 이상 선택하세요.");
			$("#confirmModal").modal('show');
			return false;
		}
	}else if(currentPage=="theme"){  /* 테마 결정 페이지에서 next 할 때 */
		var seletedTheme = false;
		
		$(".card").each(function(){
			if($(this).hasClass("seleted")){
				seletedTheme = $(this);
			}
		});
		
		if(seletedTheme == false){
			// 180807 hwi 충돌 떄문에 클래스 변경
			$(".modal-setting").text("테마를 선택하세요.");
			$("#confirmModal").modal('show');
			return false;
		}
	}
}

/* form input element 동적 추가 */
function appendHiddenElement(form, name, value){
	var element = document.createElement("input");
	element.type = "hidden";
	element.name = name;
	element.value = value;
	form.append(element);
}
</script>
</html>