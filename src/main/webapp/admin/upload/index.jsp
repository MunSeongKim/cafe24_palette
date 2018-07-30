<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- token header에 달아서 보내야 하기 때문에 meta tag로 선언.
	        파일전송을 ajax로 하기 때문에 form안에 끼우는 것 보다 이게 편한다. -->
<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header"
	content="${_csrf.headerName}" />

<title>File uploader for Palette</title>
<link rel="stylesheet" href="admin/upload/upload.css">
<script src="admin/upload/upload.js"></script>

</head>

<body>
	<div class="container">
		<!-- Header -->
		<div class="row justify-content-center" id="header">
			<div class="col">
				<h4>
					<i class="fas fa-file-upload"></i> File Upload
				</h4>
			</div>
		</div>
		<!-- body -->
		<div class="row" id="body">
			<!--<div class="col"><button class="btn btn-outline-secondary btn-lg btn-block">Functions Upload</button></div>
            <div class="col"><button class="btn btn-outline-success btn-lg btn-block">Themes Upload</button></div>-->
			<div class="col col-4">
				<div class="list-group" role="tablist">
					<a
						class="list-group-item list-group-item-light list-group-item-action active"
						data-toggle="list" href="#function" data-name="function" role="tab">Functions</a> <a
						class="list-group-item list-group-item-light list-group-item-action"
						data-toggle="list" href="#theme" data-name="theme" role="tab">Themes</a>
				</div>
			</div>
			<div class="col col-8">
				<div class="tab-content">
					<div class="tab-pane fade show active" id="function" role="tabpanel">
						<div class="row">
							<div class="col">
								<h5>Function names</h5>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<form id="func-form" enctype="multipart/form-data" method="post">
									<div class="form-group row">
										<label for="name" class="col-sm-2 col-form-label text-right">국문명</label>
										<div class="col-sm-10">
									    	<input type="text" class="form-control" name="name" id="func-name" placeholder="기능의 국문 이름을 입력하세요.">
									    </div>
									</div>
									<div class="form-group row">
										<label for="eng-name" class="col-sm-2 col-form-label text-right">영문명</label>
										<div class="col-sm-10">
									    	<input type="text" class="form-control" name="nameEng" id="func-eng-name" placeholder="기능의 영문 이름을 입력하세요.">
									    </div>
									</div>
									<div class="form-group row">
								    	<label for="description" class="col-sm-2 col-form-label text-right">설명</label>
								    	<div class="col-sm-10">
								    		<textarea class="form-control" name="description" id="func-description" rows="4" placeholder="간단한 기능 설명을 입력하세요.(100자)"></textarea>
								    	</div>
									</div>
									<div class="form-group row">
										<label for="is-button" class="col-sm-2 col-form-label text-right">버튼여부</label>
										<div class="col-sm-4">
											<div class="btn-group-toggle" data-toggle="buttons">
												<label class="btn btn-block btn-check">
											    	<input type="checkbox" name="isButton" id="func-btn-check" autocomplete="off">
											    	<span class="label-text">No</span>
												</label>
											</div>
										</div>
										<label for="is-button" class="col-sm-2 col-form-label text-right">중복검사</label>
										<div class="col-sm-4">
											<button name="is-exist" id="btn-exist" class="btn btn-block btn-exist">Check!</button>
										</div>
									</div>
									<hr>
									<div class="form-group row">
										<label for="func-desktop" class="col-sm-2 col-form-label text-right">Desktop</label>
										<div class="col-sm-10">
											<div class="custom-file">
											  <input type="file" class="form-control custom-file-input" name="func-desktop-html" id="func-desktop-html" accept=".html, .jsp">
											  <label class="custom-file-label" id="func-desktop-label" for="func-desktop-html">데스크탑 버전의 HTML 파일을 선택하세요.</label>
										  </div>
										</div>
									</div>
									<div class="form-group row">
										<label for="func-mobile" class="col-sm-2 col-form-label text-right">Mobile</label>
										<div class="col-sm-10">
											<div class="custom-file">
											  <input type="file" class="form-control custom-file-input" name="func-mobile-html" id="func-mobile-html" accept=".html, .jsp">
											  <label class="custom-file-label" id="func-mobile-label" for="func-mobile-html">모바일 버전의 HTML 파일을 선택하세요.</label>
										  </div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<form enctype="multipart/form-data" method="post">
									<label for="fileUploader"
										class="input-file-trigger">Select a file or Drag & Drop your file!</label>
									<input type="file" name="fileUploader" id="func-btn-select" class="btn input-file" accept=".js, .css, .jpg, .png" multiple >
									<!-- 나중에 accept 옵션으로 파일 확장자 막아주기. -->
								</form>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="files text-center" id="func-div-list">
									<h6>파일을 선택해주세요.</h6>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<button type="button" class="btn btn-block btn-upload" id="func-btn-upload" disabled>Upload!</button>
							</div>
						</div>
					</div>
					
					<!-- -------------- Theme part ---------------- -->
					<div class="tab-pane fade" id="theme" role="tabpanel">
						<div class="row">
							<div class="col">
								<h5>Theme</h5>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<form id="theme-form" enctype="multipart/form-data" method="post">
									<div class="form-group row">
										<label for="title" class="col-sm-3 col-form-label text-right">테마명</label>
										<div class="col-sm-9">
									    	<input type="text" class="form-control" name="title" id="theme-name" placeholder="테마 이름을 입력하세요.">
									    </div>
									</div>
									<div class="form-group row">
										<label for="img" class="col-sm-3 col-form-label text-right">대표이미지</label>
										<div class="col-sm-9">
											<div class="custom-file">
											  <input type="file" class="form-control custom-file-input" name="imgFile" id="theme-img-file" accept=".jpg, .png">
											  <label class="custom-file-label" id="theme-img-label" for="imgFile">대표이미지 1장을 선택하세요.</label>
										  </div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="col">
								<form enctype="multipart/form-data" method="post">
									<label for="fileUploader" class="input-file-trigger">Select a file or Drag & Drop your file!</label>
									<input type="file" name="fileUploader" id="theme-btn-select" class="btn input-file" accept=".css" >
									<!-- 나중에 accept 옵션으로 파일 확장자 막아주기. -->
								</form>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="files text-center" id="theme-div-list">
									<p>Empty</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<button type="button" class="btn btn-block btn-upload" id="theme-btn-upload" disabled>Upload File</button>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	
	 <script id="mustache-template" type="text/template">
		<li class="list-group-item" id="file-list{{key}}">
			<div class="row align-items-center">
				<div class="col col-1">
					<i class="fas fa-times file-rm" id="list-item-{{key}}"></i>
				</div>
				<div class="col col-3 text-left">
					<span class="file_name">{{name}}</span>
				</div>
			    <div class="col progress">
					<div class="progress-bar progress-bar-striped" id="progressbar_{{key}}" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:0%"></div>
				</div>
			    <div class="col col-2">
					<input type="button" class="btn btn-danger btn-sm btn-cancel" id="cancel_{{key}}" value="cancel" disabled>
				</div>
			    <div class="w-100"></div>
				<div class="col text-left">
					<p class="progress-status" id="status_{{key}}"></p>
				</div>
				<div class="col text-right">
					<p id="notify_{{key}}"></p>
				</div>
			</div>
		</li>
	</script>
</body>
</html>