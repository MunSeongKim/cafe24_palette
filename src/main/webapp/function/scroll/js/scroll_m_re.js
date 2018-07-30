var data = {};
var handler_functions = (function() {
	return {
		kwd_focus : function(){
			$('#scroll-m-kwd').bind('focus', function(){
				$('.scroll-m-warning').addClass('scale-out');
			})
		}, //end kwd_focus
		item_remove : function(){
			$('.item-remove').bind('click', function(){
				var currentClass = $(this).attr('class').split(' ').pop();
				delete data[currentClass];
				sessionStorage.setItem('scroll_info', JSON.stringify(data));
				check.sessionStorage();
			})
		}
	}
})();

var check = (function() {
	
	return {
		sessionStorage : function(){
			var session_value = '1'; // 값이 null이 아닐 때까지 돌려주기 위해 임시 값 저장
			var idx = 0; // index value

			// 180730 data 형태 변경
			if (sessionStorage.getItem('scroll_info') != null) {
				$('.scroll-m-ul').empty();

				data = JSON.parse(sessionStorage.getItem('scroll_info'));
				$('.scroll-m-ul').append('<li class="list-group-item save-title" style="font-weight: bold">위치 저장 정보</li>');
				
				for (key in data) {
					var url = data[key].url;
					var kwd = data[key].kwd;

					url = "'" + url + "'"; // ''를 안해주면 문법 error 발생.
					$('.scroll-m-ul').append(
							'<li class="list-group-item list-group-item-primary scroll-m-item"><a class="'
									+ key + '" onclick="href_parser(' + url + ')">검색어: '
									+ kwd + ' 저장 URL : ' + url + '</a><i class="fas fa-times item-remove '
									+ key + '"></i></li>');
				}
			}
			
			// 삭제 이벤트
			handler_functions.item_remove();
		}
	}
})();

var default_setting = (function(){
	return {
		init : function(){
			// chk sessionStorage
			check.sessionStorage();
			
			// 사이트 기본 정보 표시
			$('#scroll-info-site').text(window.location); // 현재 페이지
			$('#scroll-info-span').text(window.scrollY);  // 현재 Y값
			
			default_setting.default_event_bind();
		}, // end init
		default_event_bind : function(){
			// 스크롤 위치 정보를 삽입해주는 이벤트
			$(window).scroll(function() {
				$('#scroll-info-span').text(window.scrollY);
			});
			
			// 키워드에 대한 이벤트
			handler_functions.kwd_focus();
			
			// 스크롤 저장 버튼 이벤트
			$('#scroll-save-btn').click(function() {
				if ($('#scroll-m-kwd').val() === '') {
					$('.scroll-m-warning').removeClass('scale-out');
				} else {
					var items = $('> li', $('.scroll-m-ul'));

					// 현재 페이지 url
					var current_page = location.href;
					// 현재 스크롤 위치
					var current_scrollY = window.pageYOffset;
					// 사용자가 입력한 키워드 값
					var user_input = $('#scroll-m-kwd').val();

					var url_chk_params = current_page.split('?');

					// url에 이미 파라미터 값이 있을 경우
					if (url_chk_params.length > 1) {
						item_url = current_page + '&scrollY=' + window.pageYOffset;
					} else { // url에 파라미터가 없는 경우
						item_url = current_page + '?scrollY=' + window.pageYOffset;
					}

					// 180730 저장 data 형태 변경
					if (sessionStorage.getItem('scroll_info') != null) {
						data = JSON.parse(sessionStorage.getItem('scroll_info'));
					}
					
					data['scroll-item' + items.length] = {
						'url' : item_url,
						'kwd' : user_input
					};

					sessionStorage.setItem('scroll_info', JSON.stringify(data));

					// select 옵션 추가
					item_url = "'" + item_url + "'"; // ''를 안해주면 문법 error 발생.
					
					$('.scroll-m-ul')
							.append('<li class="list-group-item list-group-item-primary scroll-m-item"><a class="scroll-item'
											+ items.length
											+ '" onclick="href_parser('
											+ item_url
											+ ')">검색어: '
											+ user_input
											+ ' 저장 URL : ' + item_url + '</a><i class="fas fa-times item-remove"></i></li>');

					// 검색어 입력값 초기화
					$('#scroll-m-kwd').val('');
				}
			});
		}
	}
})();

// scrollY파라미터 체킹하는 함수
function url_parser(url_scrollY) {
	// 파라미터가 담길 배열
	function getParams() {
		var param = new Array();

		// 현재 페이지의 url
		var url = decodeURIComponent(url_scrollY);
		// url이 encodeURIComponent 로 인코딩 되었을때는 다시 디코딩 해준다.
		url = decodeURIComponent(url);

		var params;
		// url에서 '?' 문자 이후의 파라미터 문자열까지 자르기
		params = url.substring(url.indexOf('?') + 1, url.length);
		// 파라미터 구분자("&") 로 분리
		params = params.split("&");
		
		// params 배열을 다시 "=" 구분자로 분리하여 param 배열에 key = value 로 담는다.
		var size = params.length;
		
		var key, value, scrollY;

		// scrollY값을 제외하고 다른 파라미터를 붙이기 위한 변수 준비
		var ex_scroll_params = '';

		if (params[0].split("=")[1] != 'undefined'
				&& params[0].split("=")[1] != null) {
			for (var j = 0; j < size; j++) {
				key = params[j].split("=")[0];
				value = params[j].split("=")[1];

				// scrollY값에 엄청 큰 값을 입력했을 때 막기 위해서.
				// 현재 페이지에서 가장 하단 부분 Yvalue를 구해준다.
				var max_yvalue = $(document).height() - $(window).height();

				// 파라미터에 scrollY값이 있을 경우
				if (key == 'scrollY') {

					// 현재 파라미터로 붙은 Y값이 최대값보다 클 경우
					if (value > max_yvalue) {
						value = max_yvalue;
					}
					// scrollY위치로 이동.
					$('html').animate({
						scrollTop : value
					}, 200);

					scrollY = value;

					// scrollY파라미터는 없애줌.
					history.pushState({}, null, location.pathname);
				} else {
					if (j == 0) {
						ex_scroll_params += '?' + key + '=' + value;
					} else if (j >= 1) {
						// 첫 번째 파라미터가 scrollY일 경우 -- 그럴리는 없지만 혹시나해서 만들어놓음
						var qu_mark_chk = ex_scroll_params.split("?");
						if (qu_mark_chk.length < 2)
							ex_scroll_params += '?' + key + '=' + value;
						else
							ex_scroll_params += '&' + key + '=' + value;
					}
				}
				history.pushState({}, null, ex_scroll_params);
			} // end for
		}
		// scrollY파라미터 삭제하기 위해서. 클래스 패스만 남음.
		history.pushState({}, null, ex_scroll_params);
	}

	// url parse function
	getParams();
}

// /////////////////////////////////////////test
function href_parser(url_scrollY) {
	// 파라미터가 담길 배열
	function getParams() {
		var param = new Array();

		// 현재 페이지의 url
		var url = decodeURIComponent(url_scrollY);
		// url이 encodeURIComponent 로 인코딩 되었을때는 다시 디코딩 해준다.
		url = decodeURIComponent(url);

		var params;
		// url에서 '?' 문자 이후의 파라미터 문자열까지 자르기
		params = url.substring(url.indexOf('?') + 1, url.length);
		// 파라미터 구분자("&") 로 분리
		params = params.split("&");
		// params 배열을 다시 "=" 구분자로 분리하여 param 배열에 key = value 로 담는다.
		var size = params.length;
		var key, value, scrollY;

		// scrollY값을 제외하고 다른 파라미터를 붙이기 위한 변수 준비
		var ex_scroll_params = '';

		if (params[0].split("=")[1] != 'undefined'
				&& params[0].split("=")[1] != null) {
			for (var j = 0; j < size; j++) {
				key = params[j].split("=")[0];
				value = params[j].split("=")[1];

				// scrollY값에 엄청 큰 값을 입력했을 때 막기 위해서.
				// 현재 페이지에서 가장 하단 부분 Yvalue를 구해준다.
				var max_yvalue = $(document).height() - $(window).height();

				// 파라미터에 scrollY값이 있을 경우
				if (key == 'scrollY') {

					// 현재 파라미터로 붙은 Y값이 최대값보다 클 경우
					if (value > max_yvalue) {
						value = max_yvalue;
					}
					// scrollY위치로 이동.
					$('html').animate({
						scrollTop : value
					}, 200);

					scrollY = value;

					// scrollY파라미터는 없애줌.
					// history.pushState({}, null, location.pathname);
				} else {
					if (j == 0) {
						ex_scroll_params += '?' + key + '=' + value;
					} else if (j >= 1) {
						// 첫 번째 파라미터가 scrollY일 경우 -- 그럴리는 없지만 혹시나해서 만들어놓음
						var qu_mark_chk = ex_scroll_params.split("?");
						if (qu_mark_chk.length < 2)
							ex_scroll_params += '?' + key + '=' + value;
						else
							ex_scroll_params += '&' + key + '=' + value;
					}
				}
				// history.pushState({}, null, ex_scroll_params);
			} // end for
		}
		// scrollY파라미터 삭제하기 위해서. 클래스 패스만 남음.
		// history.pushState({}, null, ex_scroll_params);

		// 다른 페이지를 구분하기 위한 페이지
		var url_chk_params = url.split('?');

		var origin_url = url_chk_params[0] + ex_scroll_params;
		var cur_url = location.href;
		if (cur_url != origin_url) {
			// url에 이미 파라미터 값이 있을 경우
			if (ex_scroll_params != '') {
				location.href = origin_url + '&scrollY=' + scrollY;
			} else { // url에 파라미터가 없는 경우
				location.href = origin_url + '?scrollY=' + scrollY;
			}
		}
	}

	// url parse function
	getParams();
}

// 실행
$(document).ready(function() {
	// parse parameter
	url_parser(location.href);
	
	default_setting.init();
});