<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<!-- jQuery library -->
<!-- <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script> -->
<script src="${pageContext.servletContext.contextPath }/static/jquery/1.11.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- Latest compiled JavaScript -->
<script
	src="${pageContext.servletContext.contextPath }/admin/assets/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath }/admin/assets/css/bootstrap.min.css">


<style>
* {
	margin: 0;
	padding: 0;
}

.container {
	margin-top: 50px;
	border: 1px solid black;
	position: relative;
}

table, table thead th {
	text-align: center;
}
</style>
</head>

<body>
	<div class="container">
		<h1>Panel List</h1>
		<div class="row">
			<a href="${pageContext.servletContext.contextPath }/api/test/orders" class="btn btn-primary pull-left">test</a>
			<a href="${pageContext.servletContext.contextPath }/setting/create" class="btn btn-primary pull-right">만들기</a>
		</div>
		<div class="row">
			<table id="tbl-panel-list" class="table table-striped">
				<thead>
					<tr>
						<th>No.</th>
						<th>Panel Name</th>
						<th>Created Date</th>
						<th>State</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="panel" items="${list }" varStatus="stat">
						<tr>
							<td>${stat.count }</td>
							<td>${panel.name }</td>
							<td>${panel.createdDate }</td>
							<td id="state-td${panel.panelId }"><c:choose>
									<c:when test="${panel.script.isApply eq true}">
										Active
									</c:when>
									<c:otherwise>
										Inactive
									</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${panel.script.isApply eq true}">
										<button class="btn btn-default state" id="${panel.panelId }"
											data-apply="false">해제</button>
									</c:when>
									<c:otherwise>
										<button class="btn btn-info state" id="${panel.panelId }"
											data-apply="true">적용</button>
									</c:otherwise>
								</c:choose> <a
								href="${pageContext.servletContext.contextPath }/update/${panel.panelId }"
								class="btn btn-success">수정</a>
								<button class="btn btn-danger btn-delete"
									data-panelid="${panel.panelId }" data-count="${stat.count }">삭제</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="dialog">
			<div id="dialog-select-form" title="추가한 패널 동작 페이지 선택"
				style="display: none">
				<p class="validateTips normal">방금 만들어진 패널이 동작할 페이지를 선택해주세요.</p>
				<p class="validateTips error" style="display: none">하나 이상 선택해야
					합니다.</p>
				<form>
					<input type="checkbox" class="chkbox" id="chkbox-all"
						name="chkbox-activepage" value="ALL"> 전체 페이지 <br> <input
						type="checkbox" class="chkbox" id="chkbox-main"
						name="chkbox-activepage" value="MAIN"> 메인 페이지<br> <input
						type="checkbox" class="chkbox" id="chkbox-product"
						name="chkbox-activepage" value="PRODUCT_LIST"> 상품 페이지<br>
				</form>
			</div>

			<div id="dialog-message" title="tets" style="display: none">
				<p></p>
			</div>
		</div>
	</div>

	<script>
	function buttonChange(clickChangeState, autoChangeState) {
		var clickStatePanelId = clickChangeState.panelId;
		var clickStateIsApply = clickChangeState.isApply;
		

		console.log(clickStatePanelId+' '+clickStateIsApply)
		if(clickStateIsApply == true) {
			$('#state-td'+clickStatePanelId).text('Active');
			$('#'+clickStatePanelId).text('해제');
			$('#'+clickStatePanelId).attr('class', 'btn btn-default state');
			$('#'+clickStatePanelId).data('apply', false);
		} else {
			$('#state-td'+clickStatePanelId).text('Inactive');
			$('#'+clickStatePanelId).text('적용');
			$('#'+clickStatePanelId).attr('class', 'btn btn-info state');
			$('#'+clickStatePanelId).data('apply', true);
		}
		
		if(autoChangeState != null) {
			console.log('sdfdsfdsfsdf')
			var autoStatePanelId = autoChangeState.panelId;
			var autoStateIsApply = autoChangeState.isApply;
			
			$('#state-td'+autoStatePanelId).text('Inactive');
			$('#'+autoStatePanelId).text('적용');
			$('#'+autoStatePanelId).attr('class', 'btn btn-info state');
			$('#'+autoStatePanelId).data('apply', true);
		}
	}


	/* 패널 적용,해제 ajax */
	function stateChange(panelId, state, datas, dialog) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url: '${pageContext.servletContext.contextPath}/api/apply/'+panelId,
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(datas),
			// Ajax Request 헤더에 CSRF 토큰 정보 추가
		 	beforeSend: function(request) {
				request.setRequestHeader(header, token);
			},
			success: function(response) {
				if(state == true){
					dialog.dialog('close');	
				}
				buttonChange(response.data.clickChangeState, response.data.autoChangeState);
			}
		});
	};

	$(function() {
		/* panel 추가 시 페이지 선택 dialog */
		var pageSelectDialog = $("#dialog-select-form").dialog({
			autoOpen : false, //자동으로 띄우지 X
			modal : true,
			buttons : {
				'적용' : function() {
					if($('.chkbox:checked').length == 0) {
						$('.validateTips.normal').hide();
						$('.validateTips.error').show();
						return;
					}
					
					var datas = {"state": true};
					var list = [];
					if($('#chkbox-all').prop('checked')){
						$('.chkbox').each(function(index, obj){
							if(!this.checked){
								list.push(obj.value);
							}
						});
					} else {
						$('.chkbox:checked').each(function(index, obj) {
							list.push(obj.value);
						});	
					}
					
					datas['data'] = list; 
					stateChange($(this).data("id"), true, datas, $(this));
				}
			},
			
			close : function() {
				$('.chkbox').prop('disabled', false);
				$('.chkbox').prop('checked', false);
				$('.validateTips.normal').show();
				$('.validateTips.error').hide();
			}
		});//pageSelectDialog
		

		/* 해제 버튼 클릭 */
		$('.state').click(function() {
			var state = false;
			var panelId = $(this).attr('id');
			var aaa = $(this).data('apply');
			if( aaa== true) {
				pageSelectDialog.dialog('open').data("id", panelId);
				return;
			}
			
			stateChange(panelId, false, {"state": false}, null);
		});
		
		/* dialog checkbox event */
		$('#chkbox-all').change(function() {
			if($(this).prop('checked')) {
				$('.chkbox:not(#chkbox-all)').attr('disabled', 'true');
				return;
			}
			
			$('.chkbox:not(#chkbox-all)').removeAttr('disabled');
		});
		$('.chkbox:not(#chkbox-all)').change(function() {
			if($('.chkbox:checked:not(#chkbox-all)').length > 0) {
				$('#chkbox-all').attr('disabled', 'true');
			} else if($('.chkbox:checked:not(#chkbox-all)').length == 0) {
				$('#chkbox-all').removeAttr('disabled');
			}
		});

		/* 삭제 버튼 click */
		// 수정
		// 1. 적용 해제를 모든 row에 대해 검사하는 문제 수정
		//	131: if($(.state).data('apply') == false) 수정
		// 2. 삭제 버튼이 눌린 tr을 가져오도록 수정
		//	138: removeTarget = $(this).parent().parent(); 추가
		//	152: $(removeTarget).remove(); 추가
		$('.btn-delete').click(function() {
			if($(this).prev().prev().data('apply') == false) {
				alert('적용 해제 후 삭제해주세요.');
				return;
			}
			var panelId = $(this).data('panelid');
			var count = $(this).data('count');
			var removeTarget = $(this).parent().parent();
			
			// CSRF 요청에 필요한 헤더 정보 추출
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$.ajax({
				url: '${pageContext.servletContext.contextPath}/api/panel/'+panelId,
				type: 'DELETE',
				dataType: 'json',
				// Ajax Request 헤더에 CSRF 토큰 정보 추가
				beforeSend: function(request) {
					request.setRequestHeader(header, token);
				},
				success: function(response) {
					if(response.data != 'removed') {
						console.log('can not remove');
						return;
					}
					
					//$('#tbl-panel-list tbody tr:nth-child('+count+')').remove();
					$(removeTarget).remove();
					
					for(i=1;i<=${fn:length(list)};i++) {
						$('#tbl-panel-list tbody tr:nth-child('+i+') td:first').text(i);
					}
					alert('삭제되었습니다.');
				}
			});
		});
		
	});
	</script>
</body>
</html>