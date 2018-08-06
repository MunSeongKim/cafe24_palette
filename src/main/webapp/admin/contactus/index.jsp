<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- token header에 달아서 보내야 하기 때문에 meta tag로 선언.
	        파일전송을 ajax로 하기 때문에 form안에 끼우는 것 보다 이게 편한다. -->
<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header"
	content="${_csrf.headerName}" />

<link rel="stylesheet" href="admin/contactus/contactus.css">
<script src="admin/contactus/contactus.js"></script>
</head>
<body>
	<div class="cu container">
		<div class="row justify-content-center cu-header">
			<div class="col">
				<h4><i class="fas fa-address-book"></i> Contact Us</h4>
			</div>
		</div>
		<div class="row cu-body">
			<div class="col-sm-6 cu-contact">
				<p>YOUR NAME</p>
				<p><input type="text" name="contact_name" placeholder="Kim Minsu" size="full"></p>
			</div>
			<div class="col-sm-6 cu-contact">
				<p>YOUR EMAIL ADDRESS</p>
				<p><input type="text" name="contact_email" placeholder="abc@gmail.com" size="full"></p>
			</div>
		</div>
		<div class="row cu-body">
			<div class="col-sm-6 cu-contact">
				<p>COMPANY</p>
				<p><input type="text" name="contact_company" placeholder="Company" size="full"></p>
			</div>
			<div class="col-sm-6 cu-contact">
				<p>YOUR PHONE NUMBER</p>
				<p><input type="text" name="contact_phone" placeholder="000-0000-0000" size="full"></p>
			</div>
		</div>	
		<div class="row cu-body">
			<div class="col-sm-12 cu-contact">
				<p>HOW CAN WE HELP YOU?</p>
				<p class="textwrapper"><textarea name="contact_message" placeholder="Hi, I would like to.. (in 300)" required maxlength="300" rows="4" cols="100%"></textarea></p>	
			</div>
		</div>
		<div class="row cu-body">
			<button type="button" class="btn btn-block cu-btn-submit">SUBMIT</button>
		</div>
	</div>
</body>
</html>