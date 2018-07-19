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
}
 
.theme-list-div{
	border-right: 1px solid #c5c5c5;
	overflow: scroll;
	overflow-x : hidden;
	max-height: 575px;
}
</style>
<script>

</script> 
<div class="inner">
	<div class="row" style="height: 100%; padding: 5px;">
		<!-- 테마 선택 card 형태 grid 배치 -->
		<div class="theme-list-div col-sm-7 col-md-7 col-xl-7">
			<i class="custom-i fas fa-comment-alt"> 테마목록</i>
			<hr class="custom-hr">
			<div class="card">
		 		<img class="card-img-top" style="height: 150px;" src="../admin/assets/image/beige.PNG" alt="Card image cap">
		  		<div class="card-body" style="padding: 10px; line-height: initial;"> 
		    		<p class="card-title">Card title</p> 
		  		</div>
			</div> 
			
			<div class="card">
		 		<img class="card-img-top" style="height: 150px;" src="../admin/assets/image/beige.PNG" alt="Card image cap">
		  		<div class="card-body" style="padding: 10px; line-height: initial;"> 
		    		<p class="card-title">테마명 : 가을</p>
		  		</div>  
			</div> 
			
			<div class="card">
		 		<img class="card-img-top" style="height: 150px;" src="../admin/assets/image/beige.PNG" alt="Card image cap">
		  		<div class="card-body" style="padding: 10px; line-height: initial;"> 
		    		<p class="card-title">테마명 : Black & White</p>
		  		</div> 
			</div>
			
			<div class="card">
		 		<img class="card-img-top" style="height: 150px;" src="../admin/assets/image/beige.PNG" alt="Card image cap">
		  		<div class="card-body" style="padding: 10px; line-height: initial;"> 
		    		<p class="card-title">테마명 : White & Black</p>
		  		</div>
			</div>
		</div> 
		 
		<div class="theme-detail-image-div tab-content col-sm-5 col-lg-5 col-xl-5"> 
			<i class="custom-i fas fa-images"> 테마 이미지</i>
			<hr class="custom-hr"> 
		</div>
	</div>
</div>
