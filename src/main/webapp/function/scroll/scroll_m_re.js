var current_site = '';

var data = {}, scroll_max_count=0;

var handler_functions = (function() {
	return {
		kwd_focus_out : function(){
			$('#scroll-m-kwd').bind('focusout', function(){
				$('.zoom-menu').show();
			})
		}, //end kwd_focus_out
		kwd_focus : function(){
			$('#scroll-m-kwd').bind('focus', function(){
				$('.scroll-m-warning').addClass('scale-out');
				$('.zoom-menu').hide();
			})
		}, //end kwd_focus
		item_remove : function(){
			$('.item-remove-div').bind('click', function(){
				console.log('삭제!');
				var currentClass = $(this).children().attr('class').split(' ').pop();
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
			// 180730 data 형태 변경
			if (sessionStorage.getItem('scroll_info') != null) {
				$('.scroll-m-ul').empty();

				data = JSON.parse(sessionStorage.getItem('scroll_info'));
				$('.scroll-m-ul').append('<li class="list-group-item save-title" style="font-weight: bold">위치 저장 정보</li>');
				
				for (key in data) {
					/*var mus_data = {
							'url' : data[key].url,
							'realUrl' : getRealSite(data[key].url),
							'kwd' : data[key].kwd,
							'key' : key
					}*/
					var same;
					console.log('49 ==> ' + data[key].url);
					if(getRealSite(data[key].url) == decodeURIComponent(location.href)){
						same = true;
					}else{
						same = false;
					}
					
					var mus_data = data_setting.template_data(data[key].url, data[key].kwd, key, same);
					
					var scroll_m_template = $('#mustache-scroll-m-template').html();
					Mustache.parse(scroll_m_template);
					var rendered = Mustache.render(scroll_m_template, mus_data);
					$('.scroll-m-ul').append(rendered);
				} // end for
			}
			
			// 삭제 이벤트
			handler_functions.item_remove();
		}
	}
})();

var default_setting = (function(){
	// 현재 페이지 url
	var current_page = location.href;

	var url_chk_params = current_page.split('?');
	
	return {
		init : function(){
			// chk sessionStorage
			check.sessionStorage();
			
			// 사이트 기본 정보 표시
			$('#scroll-info-site').text(decodeURIComponent(window.location)); // 현재 페이지
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
			handler_functions.kwd_focus_out();
			
			// 스크롤 저장 버튼 이벤트
			$Palette('#scroll-save-btn').off().click(function() {
				console.log('모바일 버튼 클릭!');
				if ($('#scroll-m-kwd').val() === '') {
					$('.scroll-m-warning').removeClass('scale-out');
				} else {
					var items = $('> li', $('.scroll-m-ul'));
					// 사용자가 입력한 키워드 값
					var user_input = $('#scroll-m-kwd').val();
					
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
					
					if (sessionStorage.getItem('scroll_max_count') != null){
						scroll_max_count = sessionStorage.getItem('scroll_max_count');
					}
					
					console.log('124 ==> ' + item_url);

					// sessionStorage에 값 추가 준비.
					data['scroll-item' + scroll_max_count] = {
						'url' : decodeURIComponent(item_url),
						'kwd' : user_input
					};
					
					// sessionStorage에 값 저장
					sessionStorage.setItem('scroll_info', JSON.stringify(data));
					
					var same;
					if(getRealSite(item_url) == decodeURIComponent(location.href)){
						same = true;
					}else{
						same = false;
					}
					
					var mus_data = data_setting.template_data(item_url, user_input, 'scroll-item' + scroll_max_count, same);
					
					scroll_max_count = Number(scroll_max_count) + 1;
					sessionStorage.setItem('scroll_max_count', scroll_max_count);
					
					/*var mus_data = {
							'url' : item_url,
							'realUrl' : getRealSite(item_url),
							'kwd' : user_input,
							'key' : 'scroll-item' + items.length
					}*/
					
					
					var scroll_m_template = $('#mustache-scroll-m-template').html();
					Mustache.parse(scroll_m_template);
					var rendered = Mustache.render(scroll_m_template, mus_data);
					$('.scroll-m-ul').append(rendered);

					// 검색어 입력값 초기화
					$('#scroll-m-kwd').val('');
					
					// 삭제 이벤트
					handler_functions.item_remove();
				}
			});
		}
	}
})();

var data_setting = (function(){
	return {
		template_data : function(url, kwd, key, same){
			console.log('172 ==> ' + url);
			return {
				'url' : decodeURIComponent(url),
				'realUrl' : getRealSite(url),
				'kwd' : kwd,
				'key' : key,
				'same' : same
			}
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
		//url = decodeURIComponent(url);
		// url = url_scrollY;

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
		
		var url_chk_params = url.split('?');
		
		// scrollY파라미터 삭제하기 위해서. 클래스 패스만 남음.
		history.pushState({}, null, ex_scroll_params);
		current_site = url_chk_params[0] + ex_scroll_params;
	}

	// url parse function
	getParams();
}

// /////////////////////////////////////////test
function href_parser(url_scrollY) {
	console.log('268 ==> ' + url_scrollY);
	// 파라미터가 담길 배열
	function getParams() {
		var param = new Array();

		// 현재 페이지의 url
		var url = decodeURIComponent(url_scrollY);
		// url이 encodeURIComponent 로 인코딩 되었을때는 다시 디코딩 해준다.
		//url = decodeURIComponent(url);

		// url = url_scrollY;
		
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
					scrollY = value;
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
		
		// 다른 페이지를 구분하기 위한 페이지
		// 180806 페이지 이동되는 부분 위치 수정.
		var url_chk_params = url.split('?');
		
		var origin_url = url_chk_params[0] + ex_scroll_params;
		
		var cur_url = decodeURIComponent(location.href);
		if (cur_url != origin_url) {
			// url에 이미 파라미터 값이 있을 경우
			if (ex_scroll_params != '') {
				location.href = origin_url + '&scrollY=' + scrollY;
			} else { // url에 파라미터가 없는 경우
				location.href = origin_url + '?scrollY=' + scrollY;
			}
		}else{ // end if
			// scrollY위치로 이동.
			$('html').animate({
				scrollTop : scrollY
			}, 200);
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




//scrollY파라미터 체킹하는 함수
function getRealSite(url_scrollY) {
	var param = new Array();

	// 현재 페이지의 url
	var url = decodeURIComponent(url_scrollY);
	// url이 encodeURIComponent 로 인코딩 되었을때는 다시 디코딩 해준다.
	// url = decodeURIComponent(url);

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
				/*if (value > max_yvalue) {
					value = max_yvalue;
				}
				// scrollY위치로 이동.
				$('html').animate({
					scrollTop : value
				}, 200);

				scrollY = value;*/
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
		} // end for
	}
	
	var url_chk_params = url.split('?');
	current_site = url_chk_params[0] + ex_scroll_params;
	
	return current_site;
}



