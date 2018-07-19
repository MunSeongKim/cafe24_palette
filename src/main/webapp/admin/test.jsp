<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jQuery library --> 
<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="">
		<ul class="nav nav-tabs" role="tablist">
			<li class="nav-item"> 
		    	<a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Home</a>
		  	</li>
		  	<li class="nav-item">
		    	<a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Profile</a>
		  	</li>
		  	<li class="nav-item">
		   		<a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">Contact</a>
		  	</li>
		</ul> 
	
		<div class="tab-content">
			<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
				<h1>AAAA</h1>
			</div>
		 	<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
		 		<h1>BBBB</h1>
		 	</div>
		  	<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
		  		<h1>CCCC</h1>
		  	</div>
		</div>
	</div>
</body>
</html>