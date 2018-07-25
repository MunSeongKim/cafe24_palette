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
		$.panel.setPosition({
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
		$.panel.changePanel('open');
	});
});

</script>
<div class="inner">
	<div class="row" style="height: 100%; padding: 5px;">
		<div class="tab-content col-sm-12 col-md-12 col-xl-12">
			
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
