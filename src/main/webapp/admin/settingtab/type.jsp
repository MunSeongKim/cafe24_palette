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
		
		// ���� ���õ� �������� ���� �� ���� �� ������ ����
		$.previewPanel.setPosition({
			'position':position,
			'removePosition':removePosition
		});
		
		// �г� ��ư class�� open �߰�.
		// ���� : open�� ������ panel.css���� 85�� line�� ���� �ڵ尡 ���������ʴ´�.
		// ������ ���ؼ� �г��� �����ִ� ���¿� �г� ��ư�� ���°� ��ġ���� ����
		if(!$('#panel-draggable-btn').hasClass('open')) {
			$('#panel-draggable-btn').addClass('open');	
		}
		
		// �г�, �г� ��ư, ��ũ�� ��ġ ���� - open�� ���·� ��ġ ����
		$.previewPanel.open();
	});
	
	$("#confirmForm").submit(function(event){
		event.preventDefault();
		
		var name = $("#inputPanelName").val();
		
		if(name==""){
			$("#confirmFlag").val("false");
			$(".modal-body").text("�гθ��� ���� �� �� �����ϴ�.");
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
	
	/* �г� �̸� �˻� */
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
			// Ajax Request ����� CSRF ��ū ���� �߰�
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
				<i class="custom-i fas fa-columns"> �г� ����</i> 
				<hr class="custom-hr">
				<form id="confirmForm" class="form-inline">
				  	<div class="form-group row align-items-center w-100"> 
				    	<div class="col-sm-3">
				    		<label id="label-inputPanelName" for="inputPanelName"><i class="far fa-times-circle text-danger"></i> �гθ�</label>
				    	</div>
				    	
				    	<div class="col-sm-8">
				      		<input type="text" class="form-control w-100" id="inputPanelName" placeholder="�г� �̸�">
				   		</div>
				   		
				   		<div class="col-sm-1">
				   			<input type="hidden" id="confirmFlag" value="false">
				   			<button id="btn-confirmPName" type="submit" class="btn btn-primary btn-sm" style="vertical-align: top;">Ȯ��</button>
				   		</div>
				  	</div>
				</form>
			</div>
			
			<div style="height: 50%;">
				<i class="custom-i fas fa-columns"> ��ġ����</i> 
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
