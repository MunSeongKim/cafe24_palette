<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>

#sortable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 100%; 
} 

#sortable li {
	margin: 0 3px 3px 3px;
	padding-top : 0.25em;
	padding-right : 0px;
	padding-left: 1.5em;
	height: 40px;
	cursor: pointer;
	border-radius: 12px;
	width: 100%; 
}

#sortable li:hover{
	text-decoration: none;
}

#sortable li:focus{ 
	background-color: #e0e0e0;
}

#sortable li span {
	position: absolute;
	margin-left: -1.3em;
}

#sortable li p{
	font-size: 1em;
	margin: 0px;
}

#sortable li a{ 
	border : 0;
	padding: 0;
	width: max-content;
	background-color: #f6f6f6;
}

#sortable li a:visited{
	background-color: #f6f6f6; 
}

#sortable li a:focus{
	background-color: #e0e0e0;
}

#sortable li a:link{
	background-color: #f6f6f6;
}

i{
	margin-bottom: 20px;
}

/* 배경 : 검정, 글자 : 흰색 */
.custom-i{
	background-color: #563d7c;
    color: #fff;
    border-radius: 6px;
    padding: 10px;
    font-weight: 700;
    margin-bottom: 7px !important;
}

.custom-hr{
	margin-top: 0;
}

/* 기능 순서 div */
.sortdiv{
	padding: 15px;
	padding-top: 0; 
	margin: 0;
	border-left: 1px solid #c5c5c5;
	border-right: 1px solid #c5c5c5;
}
  
.func-sort{
	display: flex;
}

.func-sort-title{
	width: 100%; 
}

.func-sort-btn{
	width : 100%;
	text-align: right;
	padding-right: 10px;
}

/* 기능 상세 설명 div */
.func-detail-text-div{
	padding-top: 0;
	padding-bottom: 10px;
}

/* 기능 이미지 div */
.func-detail-image-div{
	padding: 15px;
	padding-left : 15px;
	padding-right : 15px;
	padding-top: 0;
	margin: 0;
}

.func-detail-image{
	max-height: 520px;
}

/* sort li element */
.func-element{
	pointer-events : all;
}

.func-sort-title > a:link{
	background-color: #f6f6f6; 
}

.func-sort-title > a:visited{
	background-color: #f6f6f6;
}

.func-sort-title > a:hover{
	background-color: #f6f6f6;
}
 
.nav-tabs .nav-link.active{
	border: 0px; 
}

.showImg{
	width: 100%;
	display: block;
}

.hideImg{
	display: none;
}

.popover-body > ul{
	list-style: none; 
}
</style>

<script>
$(function() {
	
	/* 순위 결정 메소드 */
	function determineOrder($ul){
		$ul.children().each(function(index){
			/* OFF되지 않은 li이 일 때 순서 번호를 할당한다. */
    		if($(this).hasClass("ui-state-disabled") == false){
    			$(this).attr("funcorder", index)
    		}
    	});
	};
	
	/* popover initialize */
	$('[data-toggle="popover"]').popover({
		container : 'body',
		title : '기능 순서를 간편하게 조작할 수 있어요!',
		trigger : 'hover'
	}).on('inserted.bs.popover', function(){
		var ariaDescribedby = $(this).attr("aria-describedby");
		$('#'+ariaDescribedby).css({  
			"max-width" : "600px"
		}); 
		
		var innerHTML = "<ul><li>기능 배치의 순서를 drap & drop 방식으로 조절하세요!</li><li>버튼을 통해 기능을 간단히 ON/OFF 하세요!</li></ul>";
		$(".popover-header").prepend('<i class="custom-i fas fa-question-circle"></i>');
		$(".popover-body").html(innerHTML);
	});
	
	/* sortable Event 선언  관련*/
	$("#sortable").sortable({
		revert : 200,
		/* sortable의 동작이 시작 될 때. -> Element를 drag할 때. */
		start: function(e, ui) {
	    	// $(this).attr('data-previndex', ui.item.index());
	    },
		
	    /* sort가 모두 일어난 후 동작 */
	    update: function(e, ui) {
	    	/* 현재 클릭 된 li의 부모 ul element */
			var selectedElement = $(ui.item.context);
	    	var funcName = selectedElement.data("funcname");
	    	var $ul = selectedElement.parent();
	    	
	    	/* 움직인 li와 funcname이 같은 기능 패널 사라짐 */
	    	$(".preview_panel .preview_func_div[data-panelfuncname="+funcName+"]").fadeOut(300, function(){
	    		/* 움직인 li와 같은 funcname을 가진 func div */
	    		tmpElement = $(this);
	    		
	    		console.log(tmpElement);
	    		
	    		/* 옮긴 li의 앞쪽에 있는 div의 data-funcname의 값 정보 --> 어떤 기능이 내 앞에 있나 확인함. */
	    		prevFuncName = selectedElement.prev().data("funcname");
	    		
	    		/* 옮긴 li의 다음에 있는 div의 정보 */
	    		nextFuncName = selectedElement.next().data("funcname");
	    		
	    		/* li의 움직임에 따라 실제 기능의 위치를 움직이는 부분. */
	    		$(".preview_panel .preview_func_div").each(function(){
	    			if($(this).data("panelfuncname") == prevFuncName){
	    				console.log($(this));
	    				$(this).after(tmpElement);
	    				tmpElement.fadeIn(300);
	    			}else if(prevFuncName == undefined){
	    				/* 기능 순서를 맨앞으로 했을 때. prevFuncName == 'undefinded' 인 상황. */
	    				$(this).parent().prepend(tmpElement);  
	    				tmpElement.fadeIn(300); 
	    			}
	    		}); 
	    		
	    	});
	    	
	    	// Determine the priority.
	    	determineOrder($ul);
	    },
	    
	    /* 어떠한 Element에 대해서 sortable을 적용할지 결정한다. */
	    /* li에서 .ul-state-disabled가 없는 엘리먼트만 sortable 적용. */
	    items : 'li:not(.ui-state-disabled)'
	});
	
	$("#sortable").disableSelection();
	
	// 첫번째 func li 선택 효과.
	$("#sortable > li:eq(0)").css({
		"background-color" : "#e0e0e0"
	}).find("a").css({
		"background-color" : "#e0e0e0"
	})
	
	/* func li 클릭시 이벤트 */
	$("#sortable > li").click(function(event){
		var $a = $(this).find("a"); /* 선택한 li의 a 태그  */
		$("#sortable > li:not(this)").css("background-color","#f6f6f6");   /* 선택 외 li의 css 변경 */
		$("#sortable > li:not(this) a").css("background-color","#f6f6f6"); /* 선택 외 li 하위 a의 css 변경 */
		
		/* 선택한 li의 하위 a태그의 css 변경 */
		$a.css({"background-color" : "#e0e0e0"});
		
		/* 선택한 li 태그의 css 변경 */
		$(this).css({"background-color" : "#e0e0e0"});
		
		/* a 태그와 연결된 탭 활성화 */ 
		$a.tab("show");
		$a.attr("aria-selected", "false").removeClass("active show");
		
		/* a태그가 가지고 있는 data-funcname 값 */
		var funcName = $a.data("funcname");
		$(".func-detail-image-div > div.tab-pane").each(function(){
			var $this = $(this);
			if($this.data("funcname")==funcName){
				$this.addClass("show active");
			}else{
				$this.removeClass("show active");
			}
		});
	});
	
	/*
		live 이벤트 - 동적 생성된 엘리먼트에 대한 이벤트 지정.
		on/off 버튼을 누르면 버튼을 포함하는 li를 비활성화(pointer-events : none;)
		단, 버튼의 pointer-events : all;로 한다.
		이 때, 위의 css를 넣으려는 div가 동적으로 생성된 div임. -> on을 쓴 이유.
	*/
	$(document).on('click', '.func-sort-btn > div', function(){
		$li = $(this).closest("li");    /* 가까이 있는 li 탐색 */
		$ul = $(this).closest("ul");    /* 가까이 있는 ul 탐색 */
		var funcname = $li.data("funcname");
		if($(this).hasClass("off")==true){  // 버튼을 눌렀는데 off라는 class를 가지고 있지 않다 -> off하는 상황.
			$li.addClass("ui-state-disabled"); 	 /* li 비활성화 */
			
			$li.removeAttr("funcorder");	/* funcorder 삭제 */
			$li.detach();				/* 해당 버튼이 있는 li 제거 */
			$li.appendTo($ul);		 /* ul의 마지막에 지워진 li 추가 */
			
			/* li내의 버튼 이벤트는 활성화 */
			$(this).css({"pointer-events" : "all"});
			
			$(".preview_panel div").each(function(){
				if($(this).data("panelfuncname") == funcname){
					$(this).fadeOut(300);
				}
			});
			
		}else{ /* 버튼을 눌러서 기능을 On하는 상황. */
			$li.removeClass("ui-state-disabled");
			$li.detach();				/* 해당 버튼이 있는 li 제거 */
			$li.prependTo($ul);
			$li.attr("funcorder", "0");
			
			/* drag & drop 후 순서 정보 */
	    	var orderInfo = $("#sortable").sortable('toArray');
			
			$(".preview_panel .preview_func_div").each(function(){
				if($(this).data("panelfuncname") == funcname){ 
					var tmpElement = $(this);
					$('.preview_panel').prepend(tmpElement);
					
					/* 결과적으로 선택한 기능이 panel에 등장. */
					$(this).fadeIn(300);
				}
			});
		} 
		
		// determine Order
		determineOrder($(this).closest("ul"));
	});
	
});
	
</script>
<!-- 기능선택 페이지 --> 
<div class="inner">
	<!-- step-content -->
	<div class="row" style="height: 100%; padding: 5px;">
		<!-- 기능에 마우스 오버 시 상세 한 설명이 나오는 공간. -->
		<div class="tab-content col-sm-6 col-md-6 col-xl-6">  
			<c:forEach var="func" items="${funcs }" varStatus="stat"> 
				<c:choose>
					<c:when test="${stat.index == 0 }">
						<div class="tab-pane fade show active func-detail-text-div" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
							<i class="custom-i fas fa-comment-alt"> 기능소개</i>
							<hr class="custom-hr">
							
							<!-- name -->
							<p>기능 명 : ${func.name }</p>
							<!-- description -->
							<span>${func.description }</span>
							<div class="customer-pickrate" style="margin-top: 20px;">
								<p style="margin: 0;">기능 선택 비율</p>
								<div class="progress">
									<div class="progress-bar" role="progressbar" style="width: 70%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">70%</div>
								</div>
							</div> 
						</div> 
					</c:when>
					  
					<c:otherwise>
						<div class="tab-pane fade func-detail-text-div" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
							<i class="custom-i fas fa-comment-alt"> 기능소개</i>
							<hr class="custom-hr">
							<!-- name -->
							<p>기능 명 : ${func.name }</p>
							<!-- description --> 
							<span>${func.description }</span>
							<div class="customer-pickrate" style="margin-top: 20px;">
								<p style="margin: 0;">기능 선택 비율</p>
								<div class="progress">
									<div class="progress-bar" role="progressbar" style="width: 70%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">70%</div>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		 </div> 
		  
		<div class="sortdiv col-sm-6 col-md-6 col-xl-6"> 
			<i class="custom-i fas fa-sort" data-toggle="popover"> 기능 순서</i>
			<hr class="custom-hr"> 
			<ul class="nav nav-tabs" id="sortable" class="funcsort" role="tablist">
			
			<c:forEach var="func" items="${funcs }" varStatus="stat">
				<li class="nav-item ui-state-default ui-state-disabled" id="${func.nameEng }" data-funcname="${func.nameEng }" data-funcid="${func.funcId }">
					<span class="ui-icon ui-icon-arrowthick-2-n-s"></span> 
					<!-- ui-state-disabled -->
					<div class="func-sort">
						<div class="func-sort-title"> 
							<a class="nav-link active show" id="${func.nameEng}-sort-tab" data-funcname="${func.nameEng }" data-toggle="tab" role="tab" href="#${func.nameEng}-sort" aria-controls="${func.nameEng}-sort" aria-selected="true">
								${func.name }
							</a>
						</div>
						
						<!-- 이 버튼은 li의 pointer-events에 관계 없이 따로 동작 가능해야 함. -->
						<div class="func-sort-btn func-element">  
							<input type="checkbox" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" data-height="20" data-size="small">
						</div>
					</div>
				</li>
			</c:forEach>
			
			</ul> 
		</div>
	</div>
</div>