// Spring Security 때문에 토큰 필요!
// ajax통신이기 때문에 meta데이터 값 빼옴.

var uploader = (function(){
	var token;
	var header;
	
	var type;
	var storeFiles;
	var API_URI;
	var elements = { select: null, list: null, upload: null };
	
	var init = function() {
		token = $("meta[name='_csrf']").attr("content");
		header = $("meta[name='_csrf_header']").attr("content");
		
		type = 'function';
		storeFiles = new Map();
	    API_URI = '/mammoth/api/app/upload/';
	    
	    elements.select = $('#func-btn-select');
	    elements.list = $('#func-div-list');
	    elements.upload = $('#func-btn-upload');
	};
	
	
	// 180719 storeFile [file을 추가할 수 있도록 만들기 위해 변수 선언]
	// 삭제를 위해 array -> map형태로 변경
	var addItem = function() {
		var files = $(elements.select).prop('files');
	    //uploader.loadStoreFiles();
	    var fileIndex = files.length;
	    for(var i = 0; i < fileIndex; i++){
	    	var file = files[i];
	    	// storeFiles.size => storeFiles.size의 키
	    	storeFiles.set(storeFiles.size, file);
	    	renderList();
	    }
	};
	
	var deleteItem = function(element) {
		// 반복문을 한 번 돌고나서
		console.log($(element).attr('id'));
		var realId = $(element).attr('id');
		var datas = realId.split('-');
		storeFiles.delete(Number(datas[2]));
		$(elements.select).val("");
	};
	
	var renderList = function () {
		$(elements.list).html('');
		
		if( storeFiles.size === 0 ){
			$(elements.list).append("<h6>파일을 선택해주세요.</h6>");
			$(elements.upload).attr('disabled', true);
			return ;
		}
		
		$(elements.upload).attr('disabled', false);
		
		storeFiles.forEach((item, key, mapObj) => {
			var data = {
					key: key,
					name: item.name
			}
			var template = $('#mustache-template').html();
            Mustache.parse(template);
            var rendered = Mustache.render(template, data);
            $(elements.list).append(rendered);
			
            $('#list-item-' + key).click(function(){
				deleteItem(this);
				renderList();
			});
		});
		
	};
	
	var upload = function() {
		// 이미 저장되있는 file들의 key값을 interator할 준비
		console.log("upload Start");
		
		if( type === 'function'){
			uploadData();
		}
		
		// storeFiles[이미 저장된 file들]의 크기만큼 포문 돌면서
		storeFiles.forEach((item, key, mapObj) => {
			// key값이 prograss bar랑 연결되있어서 key를 넘겨야됨.
			// 파라미터: 파일명, key
			console.log("upload: " + item);
			uploadFile(item, key);
		});
	};
	
	var uploadData = function() {
		console.log("uploadData");
		var requestData = generateData();
		var requestURL = API_URI + type + "/pre";
		
		console.log("uploadData before ajax");
		$.ajax({
			url: requestURL,
			type: 'POST',
			data: requestData,
			contentType: false,
			processData: false,
			beforeSend: function(request) {
				request.setRequestHeader(header, token);
			},
			success: function(response) {
				console.log(response);
				return ;
			},
			error: function(response, xhr, error){
				console.log(response.status + " (" + error + "): " + response.responseText );
			}
		});
	}
	
	var uploadFile = function(file, key){
		// 현재 form 안에 있는 file들을 넘겨주기 위함.
		// append the next file for upload
		var requestData = generateFileData(file);
		var requestURL = API_URI + type;
		
		// single 처리
		// 참고사이트:
		// https://stackoverflow.com/questions/39693482/ajax-what-is-the-equivalent-function-of-beforesend-function-in-xmlhttprequest
		// header 순서는 반드시 open한 뒤에 (csrf token)
		var ajax = new XMLHttpRequest(); // ajax 통신 준비
		ajax.open("POST", requestURL);
		ajax.setRequestHeader(header, token);
		ajax.send(requestData);

		// Cancel button
		var _cancel = $('#cancel_' + key);
		_cancel.attr('disabled', false);;
		_cancel.on('click', function () {
			// cancel 버튼을 누를 경우에는 abort 상태로
		    ajax.abort();
		});
		
		// Progress Listener
		// 로드가 너무 빨라서 거의 100%로 뜸
		// js, css, html 용량이 작아서 MB -> KB로 변경
		ajax.upload.addEventListener("progress", function (e) {
			// e.loaded는 현재 load된 크기, e.total은 업로드하는 파일의 총 크기
		    var percent = (e.loaded / e.total) * 100; // percentage로 만들기위해 * 100
		    
		    // 파일 로딩 현황을 보여주는 곳
		    // 파일 로드 상태 - success, failed, uploading
		    $("#status_" + key).text(Math.round(percent) + "% uploaded, please wait...");
		    // progress bar width값 늘려주는 곳
		    $('#progressbar_' + key).css("width", percent + "%");
		    // 현재 로드 byte양을 나타내는 곳
		    $("#notify_" + key).text("Uploaded " + (e.loaded / 1024).toFixed(2) + " KB of " + (e.total / 1024).toFixed(2) + " KB ");

		}, false);
		
		// Load Listener
		// 로드가 완료됬을 경우
		ajax.addEventListener("load", function (e) {
			$("#status_" + key).text(event.target.responseText);
			$('#progressbar_' + key).css("width", "100%")
			
			// 서버에서 404코드를 받을 경우
			if(ajax.status == 404){
				console.log('파일 없음');
			}
			
			// 서버에서 200코드를 받을 경우
			if(ajax.status == 200){
				// 완성 코드 받고 0.5초 기다렸다가 페이지 갱신. (안주면 각종 효과들을 못 봄.)
				setTimeout(function(){
					// 현재 쌓여있는 storeFile들 initialize
			    	clear();
			    	renderList();
			    	console.log('통신 성공');
				}, 500);
			}
			      
			// Hide cancel button
			// 파일 용량이 클 경우에는 로딩 중간에 cancel 버튼이 보인다.
			$('#cancel_' + key).attr('disabled', true);
		}, false);
		
		// Error Listener
		// 파일 로드 에러 이벤트
		ajax.addEventListener("error", function (e) {
		    $("#status_" + key).text("Upload Failed");
		}, false);
		
		// Abort Listener
		// 파일 로드 중간에 취소할 경우를 대비한 이벤트
		ajax.addEventListener("abort", function (e) {
		    $("#status_" + key).text("Upload Aborted");
		}, false);
	};
	
	var generateData = function() {
		console.log("generateData");
		var data = new FormData(); // Create new FormData
		
		if( type === 'function' ) { // function form안에 있는 값들
			var form = $('#func-form').serializeArray();
			form.forEach(function(item, key){
				data.append(item.name, item.value);
			});
			
			data.append('isButton', $('input[name="isButton"]').prop('checked'));
			data.append("desktopFile", $('#func-desktop-html').prop("files")[0]);
			data.append("mobileFile", $('#func-mobile-html').prop("files")[0]);
		}
		return data;
	}
	
	var generateFileData = function(file){
		var data = new FormData(); // Create new FormData
		
		if( type === 'function' ) { // function form안에 있는 값들
			var form = $('#func-form').serializeArray();
			form.forEach(function(item, key){
				data.append(item.name, item.value);
			});
		}  else { // theme form안에 있는 값들
			var form = $('#theme-form').serializeArray();
			form.forEach(function(item, key){
				data.append(item.name, item.value);
			});
			data.append("imgFile", $('#theme-img-file').prop("files")[0]);
		}
		
		data.append("files", file);
		return data;
	}
	
	var validate = function() {
		if( type === 'function' ) {
			if( ($('#func-name').val() === '') || ($('#func-eng-name').val() === '') ) {
				$('#func-name').focus();
				alert("기능 이름을 입력하세요.");
				return false ;
			} else if( $('#btn-exist').length > 0 ){
				$(this).focues();
				alert('중복 검사를 해주세요.');
				return false;
			} else if ( $('#func-desktop-html').val() === '' ){
				alert('데스크탑 버전의 HTML 파일을 등록해주세요.');
				$('#func-desktop-label').focus();
				return false;
			} else if ( $('#func-mobile-html').val() === '' ){
				alert('모바일 버전의 HTML 파일을 등록해주세요.');
				$('#func-mobile-label').focus();
				return false;
			}
		} else {
			if( $('#theme-name').val() === '' ) {
				$('#theme-name').focus();
				alert("테마 이름을 입력하세요.");
				return false ;
			} else if( $('#theme-img-file').val() === '' ) {
				$('#theme-img-file').focus();
				alert("대표 이미지를 등록하세요.");
				return false ;
			}
			
			var fileName = $('#theme-img-file').val();
			var extension = fileName.substring(fileName.lastIndexOf('.')+1, fileName.length); 
			console.log(extension);
			if ( extension !== 'jpg' && extension !== 'png' ) {
				$('#theme-img-file').focus();
				alert('jpg, png 이미지만 등록해주세요.');
				return false;
			}
			
			if ( storeFiles.size > 1 ){
				alert('1개의 CSS 파일만 등록해주세요.');
				$('#theme-img-file').focus();
				return false;
			}
			
			return storeFiles.forEach((item, key, mapObj) => {
				var fileName = item.name;
				if( fileName.substring(fileName.lastIndexOf('.')+1, fileName.length) !== 'css'){
					alert('CSS 파일만 등록해주세요.');
					$('#theme-img-file').focus();
					return false;
				}
			}) === false ? false : true;
		}
		return true;
	}
	
	var clear = function() {
		$('#func-name').val('');
		$('#func-eng-name').val('');
		$('#func-description').val('');
		$('#theme-name').val('');
		setExistButton();
		
		var functionCheck = $('#func-btn-check');
		if( $(functionCheck).prop('checked') ){
			$(functionCheck).trigger('click');
		};
		$(functionCheck).prop('checked', false);
		
		$('.custom-file-input').val('');
		$('#func-desktop-label').text("데스크탑 버전의 HTML 파일을 선택하세요.");
		$('#func-mobile-label').text("모바일 버전의 HTML 파일을 선택하세요.");
		$('#theme-img-label').text("대표이미지 1장을 선택하세요.");
		
		// 현재 file 선택한 것들 initialize
		$(elements.select).val('');
		storeFiles.clear();
	}
	
	var setElements = function(select, list, upload) {
		// select : input['file'], list : 파일 리스트가 보여질 곳, upload : upload button
		elements.select = select;
		elements.list = list;
		elements.upload = upload;
	}
	
	var getElement = function(elementName) {
		var element = null;
		switch(elementName) {
		case 'select':
			element = elements.select;
			break;
		case 'list':
			element = elements.list;
			break;
		case 'upload':
			element = elements.upload;
			break;
		}
		
		return element;
	};
	
	var setType = function(typeName){
		type = typeName;
	};
	
	
	var getType = function() {
		return type;
	};
	
	var setExistButton = function(){
		$('#checkedExist').parent().html('<button name="is-exist" id="btn-exist" class="btn btn-block btn-exist">Check!</button>');
		$('#btn-exist').unbind('click').bind('click', function(e) {
			e.preventDefault();
			uploader.isExist();
		});
	}
	
	var isExist = function() {
		if( $('#func-eng-name').val() === '' ) {
			$('#func-eng-name').focus();
			alert("기능 영문 이름을 입력하세요.");
			return false ;
		}
		
		$.ajax({
			url: API_URI + 'check',
			type: 'GET',
			dataType: 'json',
			data: {'engName': $('#func-eng-name').val()},
			beforeSend: function(request) {
				request.setRequestHeader(header, token);
			},
			success: function(response) {
				if(response.result === 'success'){
					$('#btn-exist').parent().html('<div class="alert alert-success" id="checkedExist">확인되었습니다!</div>');
					return ;
				}
				
				alert("해당 영문명의 기능이 이미 존재합니다.");
				$('#func-eng-name').focus();
				return ;
			},
			error: function(response, xhr, error){
				console.log(response.status + " (" + error + "): " + response.responseText );
			}
		});
	}
	
	// nav가 선택되면 그 선택에 따라 type이 변경된다.
	var reset = function() {
		//메뉴가 선택될 때마다 값을 초기화해준다.
		if(type === 'function'){
			uploader.setElements( $('#func-btn-select'), $('#func-div-list'), $('#func-btn-upload'));
			uploader.clear();
			uploader.renderList();
		} else {
			uploader.setElements( $('#theme-btn-select'), $('#theme-div-list'), $('#theme-btn-upload'));
			uploader.clear();
			uploader.renderList();
		}
	}
	
	return {
		init: init,
		addItem: addItem,
		deleteItem: deleteItem,
		renderList: renderList,
		upload: upload,
		setElements: setElements,
		getElement: getElement,
		setType: setType,
		getType: getType,
		validate: validate,
		clear: clear,
		isExist: isExist,
		reset: reset,
		setExistButton: setExistButton,
	}
})();

$(function() {
	uploader.init();
	
	//타입(theme, function)을 새탕햐쥰더,
	$('a.list-group-item').click(function(){
		var type = $(this).data('name');
		uploader.setType(type);
		uploader.reset();
	});

	$('.btn-upload').click(function() {
		if(uploader.validate()){
			uploader.upload();
		}
	});
	
	$('.btn-check').click(function() {
		if( !$(this).hasClass('active') ){
			$('span.label-text').text("Yes");
		} else {
			$('span.label-text').text("No");
		}
	});
	
	$('#btn-exist').click(function(e) {
		e.preventDefault();
		uploader.isExist();
	});
	
	$('.input-file-trigger').click(function(){
		var element = uploader.getElement('select');
		$(element).trigger('click');
	})
	
	$('#func-eng-name').change(function() {
		uploader.setExistButton();
	});
	
	// 파일 선택할 경우 들어오는 이벤트
    $('.input-file').change(function () {
    	uploader.addItem();
	}); // end function onready
    
	$('.custom-file-input').change(function() {
		var elementName = $(this).attr("name");
		var fileName = $(this).val();
		fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length);
		
		$('label[for="'+ elementName +'"]').text(fileName);
	});
	
}); // end script
