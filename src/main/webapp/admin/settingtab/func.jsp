<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
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
	padding: 15px;
	padding-top: 0;
}

/* 기능 이미지 div */
.func-detail-image{
	padding: 15px;
	padding-top: 0; 
	margin: 0;
	padding-left : 5px; 
}

/* sort li element */
.func-element{
	
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

</style> 

<script>
$(function() {
	/* 순위 결정 메소드 */
	function determineOrder($ul){
		$ul.children().each(function(index){
    		$(this).attr("funcorder", index); 
    	})
	}
	
	/* sortable Event 선언 */
	$("#sortable").sortable({
		start: function(e, ui) {
	    	// creates a temporary attribute on the element with the old index
	    	$(this).attr('data-previndex', ui.item.index());
	    },
		
	    /* sort가 모두 일어난 후 동작 */
	    update: function(e, ui) {
	    	/* 현재 클릭 된 li의 부모 ul element */
	    	var $ul = $(ui.item.context).parent();
	    	
	    	// Determine the priority.
	    	determineOrder($ul);
	    	
	        // gets the new and old index then removes the temporary attribute
	        /* var newIndex = ui.item.index();
	        var oldIndex = $(this).attr('data-previndex');
	        var $target = $(ui.item.context);
	        $target.attr("funcorder", newIndex);
	        $(this).removeAttr('data-previndex');
	        */
	    }
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
		
		$("#sortable > li:not(this)").css("background-color","#f6f6f6"); /* 선택 외 li의 css 변경 */
		$("#sortable > li:not(this) a").css("background-color","#f6f6f6"); /* 선택 외 li 하위 a의 css 변경 */
		
		/* 선택한 li의 하위 a태그의 css 변경 */
		$a.css({
			"background-color" : "#e0e0e0" 
		});
		
		/* 선택한 li 태그의 css 변경 */
		$(this).css({
			"background-color" : "#e0e0e0"
		});
		
		$a.tab("show");   			/* a 태그와 연결된 탭 활성화 */ 
		$a.attr("aria-selected", "false").removeClass("active show");
		
		var funcName = $a.data("funcname");  /* a태그가 가지고 있는 data-funcname 값 */
		$(".func-detail-image > div.tab-pane").each(function(){
			var $this = $(this);
			if($this.data("funcname")==funcName){
				$this.addClass("show active"); 
			}else{
				$this.removeClass("show active");
			}
		});
	});
	
	// 버튼이 off되면 그 버튼을 가지고 있는 li를 찾아서 ul의 가장 아래로 배치하는 로직 필요.
	// 각 기능 focus가 되면 func-detail-text-div의 내용이 바뀌어야 함. -> nav랑 유사.
	// on/off 버튼 
	$(".func-sort-btn > div").click(function(){
		if($(this).hasClass("off")==false){  // 버튼을 눌렀는데 off라는 div를 가지고 있지 않다 -> on을 누른 것.
			$li = $(this).closest("li");
			$ul = $(this).closest("ul");
			
			$li.addClass("ui-state-disabled"); 	 /* li 비활성화 */
			$li.detach();				/* 해당 버튼이 있는 li 제거 */
			$($li).appendTo($ul);	 /* ul의 마지막에 지워진 li 추가 */
		}else{
			$(this).closest("li").removeClass("ui-state-disabled"); 
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
		<div class="tab-content" style="display: contents;"> 
			<c:forEach var="func" items="${funcs }" varStatus="stat"> 
				<c:choose>
					<c:when test="${stat.index == 0 }">
						<div class="tab-pane fade show active func-detail-text-div col-sm-4 col-lg-4 col-xl-4" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
							<i class="fas fa-comment-alt"> 기능소개</i> 
							<!-- name --> 
							<p>기능 명 : ${func.name }</p>
							<!-- description -->
							<span>${func.desciption }</span>
							<div class="customer-pickrate" style="margin-top: 20px;">
								<p style="margin: 0;">기능 선택 비율</p>
								<div class="progress">
									<div class="progress-bar" role="progressbar" style="width: 70%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">70%</div>
								</div>
							</div> 
						</div>
					</c:when>
					  
					<c:otherwise>
						<div class="tab-pane fade func-detail-text-div col-sm-4 col-lg-4 col-xl-4" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
							<i class="fas fa-comment-alt"> 기능소개</i>  
							<!-- name -->
							<p>기능 명 : ${func.name }</p>
							<!-- description --> 
							<span>${func.desciption }</span>
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
		 
		<div class="sortdiv col-sm-4 col-md-4 col-xl-4">
			<i class="fas fa-sort">기능 순서</i>
			<ul class="nav nav-tabs" id="sortable" role="tablist">
				<c:forEach var="func" items="${funcs }" varStatus="stat">
					<c:choose>
						<c:when test="${stat.index == 0 }">
							<li class="func-element nav-item ui-state-default" data-funcname="${func.nameEng }" funcorder=${stat.index }>
								<span class="ui-icon ui-icon-arrowthick-2-n-s"></span>
								<!-- ui-state-disabled --> 
								<div class="func-sort">
									<div class="func-sort-title"> 
										<a class="nav-link active show" id="${func.nameEng}-sort-tab" data-funcname="${func.nameEng }" data-toggle="tab" role="tab" href="#${func.nameEng}-sort" aria-controls="${func.nameEng}-sort" aria-selected="true">
											${func.name }
										</a>
									</div>
									  
									<div class="func-sort-btn"> 
										<input type="checkbox" checked="checked" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" data-height="20" data-size="small">
									</div>
								</div>
							</li>
						</c:when>
						
						<c:otherwise> 
							<li class="func-element nav-item ui-state-default" data-funcname="${func.nameEng }" funcorder=${stat.index }>
								<span class="ui-icon ui-icon-arrowthick-2-n-s"></span> 
								<!-- ui-state-disabled -->
								<div class="func-sort">  
									<div class="func-sort-title">
										<a class="nav-link" id="${func.nameEng }-sort-tab" data-funcname="${func.nameEng }" data-toggle="tab" role="tab" href="#${func.nameEng}-sort" aria-controls="${func.nameEng}-sort" aria-selected="false">
											${func.name }
										</a>
									</div>
									
									<div class="func-sort-btn"> 
										<input type="checkbox" checked="checked" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" data-height="20" data-size="small">
									</div>
								</div>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul> 
		</div>
		
		<div class="func-detail-image tab-content col-sm-4 col-lg-4 col-xl-4">
			<i class="fas fa-images">기능 미리보기</i>
			<c:forEach var="func" items="${funcs}" varStatus="stat"> 
				<c:choose>
					<c:when test="${stat.index == 0 }">
						<div class="tab-pane fade show active" data-funcname="${func.nameEng}">
							<img class="img-fluid" alt="${func.name } 이미지" src="${func.imgpath }">
						</div>
					</c:when> 
					
					<c:otherwise>
						<div class="tab-pane fade" data-funcname="${func.nameEng}">
							<img class="img-fluid" alt="${func.name } 이미지" src="${func.imgpath }">
						</div> 
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</div>
</div>