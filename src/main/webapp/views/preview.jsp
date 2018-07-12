<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- jQuery library -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- Latest compiled JavaScript -->
<script	src="${pageContext.servletContext.contextPath }/assets/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath }/assets/css/bootstrap.min.css">

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

#row {
	background-color: rgba(0,0,0,0.1);
}
#row-statusbar {
	margin: 0 1.3em;
	height: 3.0em;
}
#row-statusbar #row-statusbar-content {
	margin-top: 0.5em;
}
#row-statusbar span:first-child {
	background: url('${pageContext.servletContext.contextPath}/assets/image/question-mark.png') 0 0 no-repeat;
	background-size: 1.1em 1.1em;
	padding-left: 1.2em;
	font-size: 1.4em;
}
#row-btn-save {
	float: right;
}

#div-preview-img #img-preview {
	width: 100%;
	margin-top: 0.5em;
	z-index: 1;
	
}
#div-preview-img {
	position: relative;
}
#div-preview-panel {
	background-color: green;
	width: 150px;
	height: 200px;
	z-index: 5;
	position: absolute;
	right: 1.4em;
	top: 20em;
	color: white;
	font-weight: bold;
	text-align: center;
}

</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<h1>Application Tab</h1>
		</div>
		<div class="row" id="row">
			<div id="row-statusbar">
				<div id="row-statusbar-content">
					<span>저장하시겠습니까?</span>
					<div id="row-btn-save">
						<button class="btn btn-default" id="btn-retry">CANCEL</button>
						<button class="btn btn-info" id="btn-save">SAVE</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div id="div-preview-img">
				<img src="${pageContext.servletContext.contextPath }/assets/image/preview.png" id="img-preview" />
			</div>
			<!-- 실제 선택 옵션에 따른 패널의 결과가 들어갈 자리임.-->
			<div id="div-preview-panel">REMOTE CONTROLLER</div>

		</div>
	</div>

	<script>
		$(function() {

			$('#btn-save')
					.click(
							function() {
								var openerType = typeof opener.location.href;

								if (openerType == 'string') {
									opener.location.href = '${pageContext.servletContext.contextPath}/'; //오픈된 팝업창인 경우 부모창(opener)의 페이지 이동
									opener.focus();
								}
								window.close();
								return;
							})
			$('#btn-retry').click(function() {
				window.close();
			})
		})
	</script>
</body>
</html>