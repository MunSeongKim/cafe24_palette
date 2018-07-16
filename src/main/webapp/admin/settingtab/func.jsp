<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

<style>

p{
	font-size: 1vmin; 
}

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
}

#sortable li:hover{
	background-color: #c3c3c3; 
}

#sortable li span {
	position: absolute;
	margin-left: -1.3em;
}

#sortable li p {
	/* font-size: 0.625em; */
	margin: 0px;
}

.sortdiv{
	padding: 0px;
	margin: 0px; 
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

.func-detail{
	
}

.func-detail-text{
	border-left: 5px solid #fff;
	border-right: 5px solid #fff;
} 
 
</style> 

<script>
$(function() {
	$("#sortable").sortable();
	$("#sortable").disableSelection();
	
	$("#sortable > li").click(function(){
		$(this).css({
			"border" : "1px solid #000"
		})
		
		$(this).focusout(function(){
			console.log("focus out!");
			$(this).css({
				"border" : "none" 
			});
		});
		
		$("#sortable > li > .btn").click(function(){
			console.log(this); 
		});
	});
	
	// 버튼이 off되면 그 버튼을 가지고 있는 li를 찾아서 ul의 가장 아래로 배치하는 로직 필요.
	// 각 기능 focus가 되면 func-detail-text의 내용이 바뀌어야 함. -> nav랑 유사.
});
	
</script>
<!-- 기능선택 페이지 -->
<div class="inner">
	<div class="row">
		<h4>Function Select Page</h4>
		<i class="far fa-question-circle"></i>
	</div>

	<div class="step-content row" style="background-color: #efefef; height: 100%; padding: 5px;">
		<div class="sortdiv col-md-2 col-sm-4">
			<ul id="sortable">
				<c:forEach var="func" items="${funcs }" > 
					<li class="ui-state-default">  
						<span class="ui-icon ui-icon-arrowthick-2-n-s"></span>
						<!-- ui-state-disabled -->
						<div class="func-sort">
							<div class="func-sort-title">
								<p>${func.name }</p>
							</div>
							
							<div class="func-sort-btn" style="pointer-events:all;"> 
								<input type="checkbox" checked="checked" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" data-height="20" data-size="small">
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div> 
		
		<div class="func-detail col-md-10 col-sm-8">
			<!-- 기능에 마우스 오버 시 상세 한 설명이 나오는 공간. -->
			<div class="row">
				<div class="func-detail-text col-sm-7 col-lg-8 col-xl-8">
					<h5>기능 명 : 최근 본 상품</h5>
					
					<span>이 기능은 정말 좋은 엄청난 편의를 제공하는 기능입니다.</span>
					 
					<span>이 기능을 선택하여 사용자들이 더욱 최근 본 상품에 대한 구매율을 높일 수 있도록
					하세요. 많은 사용자들은 여러 상품을 조회하고 구매까지 이어지는 경우는 생각보다 많지
					않습니다.
					</span>
					
					<div class="customer-pickrate" style="margin-top: 20px;">
						<p style="margin: 0;">기능 선택 비율</p>
						<div class="progress">
							<div class="progress-bar" role="progressbar" style="width: 70%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">70%</div>
						</div>
					</div> 
				</div>  
				 
				<div class="func-detail-gif col-sm-5 col-lg-4 col-xl-4"> 
					<img class="img-fluid" alt="func1 내용" src="../admin/assets/image/test.gif" style="width: 100%;">
				</div>
			</div>
		</div>
		
		<!-- <div class="col-sm-3" style="border: 1px solid #000;"> 
			기능을 드래그하여 드롭할 공간.
		</div> -->
	</div>
</div>