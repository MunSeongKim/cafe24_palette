<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- token header에 달아서 보내야 하기 때문에 meta tag로 선언.
	        파일전송을 ajax로 하기 때문에 form안에 끼우는 것 보다 이게 편한다. -->
	<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
	<!-- default header name is X-CSRF-TOKEN -->
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>

	<!-- jQuery js -->
	<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<!-- bootstrap js -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<!-- css bootstrap & fontawesome -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css">

<style>
/* 파일 선택 input을 가리기 위한 div */
.input-file-container {
  text-align:center;
} 

/* .fileBox는 클래스가 없으며 페이지 로드될 시에 css를 꼽아버림 */
/* 파일 전송 트리거 css */
.fileBox .input-file-trigger {
  display: block;
  padding: 14px 45px;
  background: #39D2B4;
  color: #fff;
  font-size: 1em;
  transition: all .4s;
  cursor: pointer;
}

/* 파일 선택 input-file */
.fileBox .input-file {
  position: absolute;
  top: 0; left: 0;
  width: 225px;
  opacity: 0;
  padding: 14px 0;
  cursor: pointer;
}

/* 파일 선택 클릭, drag & drop 이벤트가 일어날 때 일어나는 css */
.fileBox .input-file:hover + .input-file-trigger,
.fileBox .input-file:focus + .input-file-trigger,
.fileBox .input-file-trigger:hover,
.fileBox .input-file-trigger:focus {
  background: #34495E;
  color: #39D2B4;
}

/* 서버로 전송 버튼 */
#btnUpload {
	width:100%;
	margin: 0px 5px 15px 0px;
}

/* 파일 선택 input css */
#fileUploader {
	width:100%;
	height:100%;
	padding:0px;
}

/* 파일명 보이는 곳 div */
.file-wrap-div {
	text-align:center;
}

/* list안에 파일명 */
.file_name {
	display:inline-block;
	text-align:center;
	font-style: italic;
	font-weight: bold;
}

/* 삭제버튼 감싸는 div */
.wrap-rm-btn {
	width:20%;
	text-align:right;
}

/* list안에 삭제 버튼 */
.file-rm {
	cursor:pointer;
	font-size:20px;
	/* margin-left:90px;
	vertical-align:bottom; */
}

/* 취소 버튼 */
#cancel_0 {
	text-align:center;
	margin:10px;
}

/* progress bar 관련 css 시작*/
.progress-bar-striped.active{
	height:10px;
}
.col-md-12 {
	min-height:10px;
}
.row {
	margin-right:0px;
	margin-left:15px;
}
#divFiles {
	text-align:right;
	width:100%;
	padding:10px;
	border: 1px solid #A4A4A4;
	border-radius: 5px;
}
/* progress bar 관련 css 끝 */
</style>

<script>
// filebox에 해당하는 css 꼽아버리기~
document.querySelector("html").classList.add('fileBox');
// Spring Security 때문에 토큰 필요!
// ajax통신이기 때문에 meta데이터 값 빼옴.
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

// 180719 storeFile [file을 추가할 수 있도록 만들기 위해 변수 선언]
// 삭제를 위해 array -> map형태로 변경
var storeFiles = new Map();

console.log('token ==> ' + token);
console.log('header ==> ' + header);

function fileChangeLoad(i, file, fileId){
	//배열에 현재 등록된 파일 추가 : map으로 변경
	storeFiles.set(''+i, file); // map에 값 추가 ''는 스트링 형태로 만들기 위해.
	
	$("#divFiles").append('<i class="far fa-window-close file-rm" id="list-item-'+fileId+'"></i><li class="list-group-item" id="file-list'+fileId+'"><div class="file-wrap-div">&nbsp;파일명: <p class="file_name">'+file.name+'</p></div><div class="col-md-12">' +
						'<div class="progress-bar progress-bar-striped active" id="progressbar_' + fileId + '" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:0%"></div>' +
						'</div>' +
						'<div class="col-md-12">' +
						'<div class="col-md-6">' +
						'<input type="button" class="btn btn-danger" style="display:none;line-height:6px;height:25px" id="cancel_' + fileId + '" value="cancel">' +
						'</div>' +
						'<div class="col-md-6">' + // 여기 css max-width값 없애기
						'<p class="progress-status" style="text-align: right;margin-right:-15px;font-weight:bold;color:saddlebrown" id="status_' + fileId + '"></p>' +
						'</div>' +
						'</div>' +
						'<div class="col-md-12">' +
						'<p id="notify_' + fileId + '" style="text-align: right;"></p>' +
						'</div></li>');
	
	var iTagId = 'list-item-' + fileId;
	
	// 확장자 명 체크
	var exe = extractExt(file.name);
	
	$('#'+iTagId).click(function(){
		// 반복문을 한 번 돌고나서 
		var realId = $(this).get(0).id;
		var datas = realId.split('-');
		var fileId = datas[2];
		storeFiles.delete(fileId);
		if(storeFiles.size == 0){
			$('#btnUpload').hide();
		}
		$("#divFiles").empty();
		$("#fileUploader").val("");
		loadStoreFiles();
	});
}

//extract exe
function extractExt( str ){
   var len = str.length;
   var last = str.lastIndexOf("."); //확장자 추출

   if( last == -1 ){ //. 를 발견하지 못한다면.
      return false; //확장자가 없음.
   }

   //var ext = str.substr(last, len - last );  //확장자 추출 (. 포함)
   var ext = str.substring(last, len);  //확장자 추출 (. 포함)

   ext = ext.toLowerCase(); //소문자로.

   return ext;
}


// uploadFiles
// 전송버튼 클릭시 들어오는 함수
function uploadFiles() {
	// 이미 저장되있는 file들의 key값을 interator할 준비
	var keyIter = storeFiles.keys();
	
	// storeFiles[이미 저장된 file들]의 크기만큼 포문 돌면서 
	for (var i = 0; i < storeFiles.size; i++) {
		var key = keyIter.next().value; // key값이 prograss bar랑 연결되있어서 key를 넘겨야됨.
		uploadSingleFile(storeFiles.get(key), key);  // 파라미터: 파일명, key 
	} // end for
}

// 파일 전송 및 progress bar 생성과 파일 전송 성공시 체크하는 함수!
function uploadSingleFile(file, i) { // file = 파일명, i = 파일의 키 값
	var fileId = i;
	var ajax = new XMLHttpRequest(); // ajax 통신 준비
	
	//Progress Listener
	// 로드가 너무 빨라서 거의 100%로 뜸
	// js, css, html 용량이 작아서 MB -> KB로 변경
	ajax.upload.addEventListener("progress", function (e) {
		// e.loaded는 현재 load된 크기, e.total은 업로드하는 파일의 총 크기
	    var percent = (e.loaded / e.total) * 100; // percentage로 만들기위해 * 100
	    
	    // 파일 로딩 현황을 보여주는 곳
	    // 파일 로드 상태 - success, failed, uploading
	    $("#status_" + fileId).text(Math.round(percent) + "% uploaded, please wait...");
	    // progress bar width값 늘려주는 곳
	    $('#progressbar_' + fileId).css("width", percent + "%");
	    // 현재 로드 byte양을 나타내는 곳
	    $("#notify_" + fileId).text("Uploaded " + (e.loaded / 1024).toFixed(2) + " KB of " + (e.total / 1024).toFixed(2) + " KB ");
	}, false);
	
	//Load Listener
	// 로드가 완료됬을 경우
	ajax.addEventListener("load", function (e) {
		$("#status_" + fileId).text(event.target.responseText);
		$('#progressbar_' + fileId).css("width", "100%")
		
		// 서버에서 404코드를 받을 경우
		if(ajax.status == 404){
			console.log('파일 없음');
		}
		
		// 서버에서 200코드를 받을 경우
		if(ajax.status == 200){
			// 완성 코드 받고 1초 기다렸다가 페이지 갱신. (안주면 각종 효과들을 못 봄.)
			setTimeout(function(){
				// 현재 쌓여있는 파일들 initialize
		    	$("#divFiles").empty();
				// 현재 쌓여있는 storeFile들 initialize
		    	storeFiles.clear();
				// 현재 file 선택한 것들 initialize
				$("#fileUploader").val("");
				// 전송버튼 숨기기
				$('#btnUpload').hide();
				// loadStoreFiles();
		    	console.log('통신 성공');
			}, 1000);
		}
		      
		//Hide cancel button
		// 파일 용량이 클 경우에는 로딩 중간에 cancel 버튼이 보인다.
		var _cancel = $('#cancel_' + fileId);
		_cancel.hide();
	}, false);
	
	//Error Listener
	// 파일 로드 에러 이벤트
	ajax.addEventListener("error", function (e) {
	    $("#status_" + fileId).text("Upload Failed");
	}, false);
	
	//Abort Listener
	// 파일 로드 중간에 취소할 경우를 대비한 이벤트
	ajax.addEventListener("abort", function (e) {
	    $("#status_" + fileId).text("Upload Aborted");
	}, false);
	
	// single 처리
	//ajax.open("POST", "/mammoth/funcupload/single_upload"); // Your API .net, php
	ajax.open("POST", "/mammoth/funcupload/multi");
	// 참고사이트: https://stackoverflow.com/questions/39693482/ajax-what-is-the-equivalent-function-of-beforesend-function-in-xmlhttprequest
	// header 순서는 반드시 open한 뒤에 (csrf token)
	ajax.setRequestHeader(header, token);
	
	// 현재 form 안에 있는 file들을 넘겨주기 위함.
	var uploaderForm = new FormData(); // Create new FormData
	uploaderForm.append("file", file); // append the next file for upload
	uploaderForm.append("name", 'test'); // 만약에 파일 이름을 수정할 경우. 현재는 'test로 박아놓음.'
	ajax.send(uploaderForm);
	
	//Cancel button
	var _cancel = $('#cancel_' + fileId);
	_cancel.show();
	
	_cancel.on('click', function () {
		// cancel 버튼을 누를 경우에는 abort 상태로
	    ajax.abort();
	});
}

// 사용자가 선택한 파일을 load하는 곳
function loadStoreFiles(){
	// 현재 storeFiles에 저장된 키들을 불러옴.
	var keyIter = storeFiles.keys();
    
    // 180719 파일 추가하기 위한 부분
    for(var i=0; i<storeFiles.size; i++){
    	var key = keyIter.next().value; // 반복문 돌 때마다 key를 
    	// 리스트 append section
    	 $("#divFiles").append('<i class="far fa-window-close file-rm" id="list-item-'+key+'"></i><li class="list-group-item" id="file-list'+key+'"><div class="file-wrap-div">&nbsp;파일명: <p class="file_name">'+ storeFiles.get(key).name +'</p></div><div class="col-md-12">' +
							'<div class="progress-bar progress-bar-striped active" id="progressbar_' + key + '" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:0%"></div>' +
							'</div>' +
							'<div class="col-md-12">' +
							'<div class="col-md-6">' +
							'<input type="button" class="btn btn-danger" style="display:none;line-height:6px;height:25px" id="cancel_' + key + '" value="cancel">' +
							'</div>' +
							'<div class="col-md-6">' + // 여기 css max-width값 없애기
							'<p class="progress-status" style="text-align: right;margin-right:-15px;font-weight:bold;color:saddlebrown" id="status_' + key + '"></p>' +
							'</div>' +
							'</div>' +
							'<div class="col-md-12">' +
							'<p id="notify_' + key + '" style="text-align: right;"></p>' +
							'</div></li>');

    	
    	var iTagId = 'list-item-' + key;
    	
    	// 확장자 명 체크
    	var exe = extractExt(storeFiles.get(key).name);
    	
    	// click 리스트 x눌렀을 때 이벤트
    	$('#'+iTagId).click(function(){
    		console.log('현재 파일의 확장자 ==> ' + exe);
    		var realId = $(this).get(0).id.split('-');
    		storeFiles.delete(realId[2]); // delete
    		if(storeFiles.size == 0){
    			$('#btnUpload').hide();
    		}
    		$("#divFiles").empty(); // 리스트 초기화
    		$("#fileUploader").val(""); // 파일 선택된 거 초기화
    		loadStoreFiles(); // 리스트 다시 그려준다.
    	});
	} // end for
}

$(function() {
	// 전송 버튼 누를 경우
	$('#btnUpload').click(function(){
		uploadFiles();
	});
   	
	// 파일 선택할 경우 들어오는 이벤트
    $('input[type=file]').change(function () {
    	$('#btnUpload').show();
	    $('#divFiles').html('');
	    var fileInput = document.getElementById("fileUploader");
	    var files = fileInput.files;
	    
	    loadStoreFiles();
	    
	    var fileIndex = storeFiles.size + this.files.length;
	    
	    // 추가로 등록된 파일의 위치를 잡기 위한 인덱스
	    var forIndex = 0;
	    
	    // 현재 storeFiles개수를 인덱스로 잡아서 시작 (id값을 주기위해서)
		for (var i = storeFiles.size; i < fileIndex; i++) { //Progress bar and status label's for each file genarate dynamically
			var fileId = i;
			var file = files[forIndex];
			
			var exe = extractExt(file.name);
			
			fileChangeLoad(i, file, fileId);
			
			forIndex++;
		} // end for
	}); // end function onready
}); // end script
</script>
<title>Insert title here</title>
</head>
<body>
	<h1>File upload page</h1>
	<form id="upload_form" enctype="multipart/form-data" method="post">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="input-file-container">  
						<input type="file" name="fileUploader" id="fileUploader"
							class="btn btn-default input-file" multiple accept=".js, .css, .html, .jsp"> <!-- 나중에 accept 옵션으로 파일 확장자 막아주기. -->
						<label tabindex="0" for="fileUploader" class="input-file-trigger">Select a file or Drag & Drop your file!</label>
					</div>
				</div>
				<div class="col-md-12" style="text-align: right;">
					<input type="button" id="btnUpload" style="display:none"
						class="btn btn-default" value="Upload File">
				</div>
			</div>
			<div class="row">
				<div id="divFiles" class="files"></div>
			</div>
		</div>
	</form>
</body>
</html>