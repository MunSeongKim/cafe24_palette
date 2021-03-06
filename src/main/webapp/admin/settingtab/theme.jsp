<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.card{
	display: inline-block;
	width: 12em;
	margin-bottom: 10px;
	cursor: pointer;
}

.card-body{
	padding: 10px;
	line-height: initial;
}

.card-img-top{ 
	height: 150px;
}
 
.theme-list-div{
	overflow: auto; 
	overflow-x : hidden;
	max-height: 575px;
}
.theme-detail-image-inner-div{
	text-align: center;
}

.theme-detail-image{
	max-height: 520px;
	width: 70%;
}
</style>

<script>
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
	$(".card").hover(
		/* function(){
			$(this).css({
				"border" : "1px solid #000" 
			})
		},
		
		function(){
			$(this).css({
				"border" : "1px solid rgba(0,0,0,.125)"
			})
		} */
	);
	
	$(".card").click(function(){
		$(".card:not(this)").removeClass("seleted").css({
			"border" : "1px solid rgba(0,0,0,.125)"
		});
		
		$(this).addClass("seleted");
		$(this).css({
			"border" : "2px solid #000" 
		});
		
		var cssFilePath = $(this).data("cssfilepath");
		
		includeThemeFile(cssFilePath, "css"); 
		
		// 사진 교체
		previewImgPath = $(this).data("previewpath");
		$(".theme-detail-image").attr("src", "${pageContext.servletContext.contextPath }"+previewImgPath).fadeIn();
	});
	
});
</script>  
<div class="inner">
	<div class="row" style="height: 100%; padding: 5px;">
		<!-- 테마 선택 card 형태 grid 배치 -->
		<div class="theme-list-div col-sm-12 col-md-12 col-lg-12 col-xl-12">
			<i class="custom-i fas fa-comment-alt"> 테마목록</i> 
			<hr class="custom-hr">
			<c:forEach var="theme" items="${themes }" varStatus="stat">
				<div class="card" data-cssfilepath="${pageContext.servletContext.contextPath }${theme.filePath }" data-previewpath = "${theme.titleImgPath }" data-themeid = ${theme.id } data-themetitle = ${theme.title }>
			 		<img class="card-img-top" src="${pageContext.servletContext.contextPath }${theme.titleImgPath }" alt="Card image cap">
			  		<div class="card-body">
			    		<p class="card-title">테마명:${theme.title }</p>
			  		</div>
				</div>
			</c:forEach>
		</div>
		 
		<%-- <div class="theme-detail-image-div tab-content col-sm-6 col-md-5 col-lg-4 col-xl-3">
			<i class="custom-i fas fa-images"> 테마 이미지</i>  
			<hr class="custom-hr">
			<div class="theme-detail-image-inner-div">
				<img class="theme-detail-image img-fluid" alt="theme 예시 이미지" src="${pageContext.servletContext.contextPath }/assets/admin/image/theme_none.PNG">
			</div>
		</div> --%>
	</div>
</div>
