<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.panelTypeDiv, .positionTypeDiv{
	width: 100%;
	display: inline-block;
}

</style>
<script>

$(document).ready(function(){
	
	$(":radio[name=position]").change(function(){
		var position = $(this).val().toLowerCase();
		var removePosition = $(':radio[name=position]').not($(this)).val().toLowerCase(); 
		
		// 현재 선택된 포지션의 정보 및 삭제 할 포지션 정보
		$.panel.setPosition({
			'position':position,
			'removePosition':removePosition
		});
		
		// 패널 버튼 class에 open 추가.
		// 이유 : open이 없으면 panel.css에서 85번 line과 같은 코드가 동작하지않는다.
		// 간단히 말해서 패널이 열려있는 상태와 패널 버튼의 상태가 일치하지 않음
		if(!$('#panel-draggable-btn').hasClass('open')) {
			$('#panel-draggable-btn').addClass('open');	
		}
		
		// 패널, 패널 버튼, 스크롤 위치 변경 - open인 상태로 위치 변경
		$.panel.changePanel('open');
	});
});

</script>
<div class="inner">
	<div class="row" style="height: 100%; padding: 5px;">
		<div class="tab-content col-sm-12 col-md-12 col-xl-12">
			
			<div style="height: 50%;">
				<i class="custom-i fas fa-columns"> 위치선택</i> 
				<hr class="custom-hr">
				<div id="positionTypeDiv" class="positionTypeDiv">
				<c:forEach var="position" items="${positions }" varStatus="stat"> 
					<div class="form-check">
						<c:choose>
							<c:when test="${stat.index==0 }">
								<input class="form-check-input" type="radio" name="position" id="position" value="${position }" checked>
							</c:when> 
							<c:otherwise>
								<input class="form-check-input" type="radio" name="position" id="position" value="${position }">
							</c:otherwise>
						</c:choose>
						<label class="form-check-label" for="position">
						${position }
						</label>
					</div>
				</c:forEach>
				</div>
			</div>
		 </div>
	</div>
	
</div>
