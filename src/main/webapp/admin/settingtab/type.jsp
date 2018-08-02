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
		$.previewPanel.setPosition({
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
		$.previewPanel.open();
	});
	
	$("#confirmForm").submit(function(event){
		event.preventDefault();
		
		var name = $("#inputPanelName").val();
		
		if(name==""){
			$("#confirmFlag").val("false");
			$(".modal-body").text("패널명은 공백 일 수 없습니다.");
			$("#confirmModal").modal('show');
			return false;
		}
		
		checkPanelName(name);
	});
	
	$("#inputPanelName").change(function(){
		$("#confirmFlag").val("false");
		$("#label-inputPanelName > i").removeClass("fa-check-circle text-success");
		$("#label-inputPanelName > i").addClass("fa-times-circle text-danger");
	});
	
	/* 패널 이름 검사 */
	function checkPanelName(name){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		var name = $("#inputPanelName").val();
		
		$.ajax({
			url: '${pageContext.servletContext.contextPath}/api/app/panel/confirmPName',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json; charset=utf8;',
			data: JSON.stringify(name),
			// Ajax Request 헤더에 CSRF 토큰 정보 추가
		 	beforeSend: function(request) {
				request.setRequestHeader(header, token);
			},
			success: function(response) {
				if(response.data == "non-exist"){
					$("#confirmFlag").val("true");
					$("#label-inputPanelName > i").removeClass("fa-times-circle text-danger");
					$("#label-inputPanelName > i").addClass("fa-check-circle text-success");
				}else{
					$("#confirmFlag").val("false");
					$("#label-inputPanelName > i").removeClass("fa-check-circle text-success");
					$("#label-inputPanelName > i").addClass("fa-times-circle text-danger");
				}
			}
		});
	}
});

</script>
<div class="inner">
	<div class="row" style="height: 100%; padding: 5px;">
		<div class="tab-content col-sm-12 col-md-12 col-xl-12">
			
			<div style="height: 50%;">
				<i class="custom-i fas fa-columns"> 패널 정보</i> 
				<hr class="custom-hr">
				<form id="confirmForm" class="form-inline">
				  	<div class="form-group row align-items-center w-100"> 
				    	<div class="col-sm-3">
				    		<label id="label-inputPanelName" for="inputPanelName"><i class="far fa-times-circle text-danger"></i> 패널명</label>
				    	</div>
				    	
				    	<div class="col-sm-8">
				      		<input type="text" class="form-control w-100" id="inputPanelName" placeholder="패널 이름">
				   		</div>
				   		
				   		<div class="col-sm-1">
				   			<input type="hidden" id="confirmFlag" value="false">
				   			<button id="btn-confirmPName" type="submit" class="btn btn-primary btn-sm" style="vertical-align: top;">확인</button>
				   		</div>
				  	</div>
				</form>
			</div>
			
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
