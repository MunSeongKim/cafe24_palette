<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>

</style>
<script>

</script> 
<div class="inner">
	<div class="row" style="height: 100%; padding: 5px;">
		<div class="tab-content col-sm-6 col-md-6 col-xl-6">
			<i class="custom-i fas fa-comment-alt"> 유형선택</i>
			<hr class="custom-hr">
			<c:forEach var="panelType" items="${panelTypes }" varStatus="stat"> 
				<c:choose>
					<c:when test="${stat.index == 0 }">
						<div class="tab-pane fade show active func-detail-text-div" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
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
						<div class="tab-pane fade func-detail-text-div" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
							<i class="custom-i fas fa-comment-alt"> 기능소개</i>
							<hr class="custom-hr">
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
	</div>
</div>
