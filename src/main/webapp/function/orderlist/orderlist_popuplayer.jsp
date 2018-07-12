<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- 구매 목록 팝업 레이어  -->
<div class="popupLayer">
	<div id="popup-header">
		<div id="popup-period">
			<button id="period-week">1주일</button>
			<button id="period-month">1개월</button>
			<button id="period-all">전체</button>
			<br>
		</div>
		<div id="popup-chkbox">
			<input type="checkbox" name="ckbox" id="ckbox"> 현재와 동일한 카테고리
			상품 구매 내역 보기
		</div>

	</div>
	<!-- 상품 리스트 START -->
	<div id="popup-orderlist">
		<div class="container-layout">
			<div class="container">
				<!-- 특정 주문일에 주문한 상품 리스트 -->
				<c:forEach begin="1" end="3" step="1">
					<div class="row">
						<div class="col-md-12">
							<div class="card border-dark mb-3">
								<div class="card-header">주문일자</div>
								<div class="card-body">
									<!-- 상품 이미지/사이즈 -->
									<c:forEach begin="1" end="3" step="1">
										<div class="row order-product-info">
											<div class="col-md-6">
												<img class="img-fluid d-block"
													src="/assets/image/imvely.jpg">
											</div>
											<div class="col-md-6">
												<p>상품1</p>
												<p>가격 : 40000</p>
												<p>색상 : red</p>
												<p>사이즈 : S</p>
												<button>사이즈 상세보기</button>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>


			</div>
		</div>
	</div>
	<!-- 상품 리스트 END -->

	<div id="popup-close">
		<div class="float-right">
			<button onclick="closeLayer(this)">닫기</button>
		</div>
	</div>
</div>
<!-- 구매 목록  팝업 레이어 END -->