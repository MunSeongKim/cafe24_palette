// center function 구현
jQuery.fn.center = function() {
	this.css("position", "absolute");
	this.css("top", Math.max(0,
			(($(window).height() - $(this).outerHeight()) / 2)
					+ $(window).scrollTop())
			+ "px");
	this.css("left", Math.max(0,
			(($(window).width() - $(this).outerWidth()) / 2)
					+ $(window).scrollLeft())
			+ "px");
	return this;
}

// 스크롤 저장 클릭시
function save_button_click() {
	$('.scroll_m_question_popup').position({
		of : window,
		my : 'center',
		at : 'center',
		collision : 'fit'
	});

	$('.scroll_m_question_popup').css('top', '30%');
	// modal 자체가 style로 때려 박기 때문에 다시 제거 해줘야됨.
	$('.scroll_m_question_popup').css('left', '');

	//$('.scroll_m_question_popup').center();
	$('.scroll_m_question_popup').fadeIn(200);
}

// scroll_m_question_popup 닫기시
function close_popup() {
	$('.scroll_m_question_popup').fadeOut(200);
	// 검색어 입력값 초기화
	$('.scroll_pop_input').val('');
}

// scroll_m_question_popup 저장시
function save_popup() {
	$('.scroll_pop_chk_p').hide();

	if ($('.scroll_pop_input').val() == '') {
		$('.scroll_pop_chk_p').show();
	} else {
		var items = $('> a', $('.scroll_m_sel_btn'));
		//items = wrapper.find('> a');

		console.log('items ==> ' + items.length);

		// 현재 페이지 url
		var current_page = location.href;
		// 현재 스크롤 위치
		var current_scrollY = window.pageYOffset;
		// 사용자가 입력한 키워드 값
		var user_input = $('.scroll_pop_input').val();

		var url_chk_params = current_page.split('?');

		// url에 이미 파라미터 값이 있을 경우
		if (url_chk_params.length > 1) {
			item_url = current_page + '&scrollY=' + window.pageYOffset;
		} else { // url에 파라미터가 없는 경우
			item_url = current_page + '?scrollY=' + window.pageYOffset;
		}

		// save at sessionStorage (key = itemID, value = item_url)
		sessionStorage.setItem("scroll_item_url" + items.length, item_url);
		// save at sessionStorage (keyword = user input value)
		sessionStorage
				.setItem("scroll_item_keyword" + items.length, user_input);

		// select 옵션 추가
		item_url = "'" + item_url + "'"; // ''를 안해주면 문법 error 발생.
		$('.scroll_m_sel_btn').append(
				'<div><a class="dropdown-item scroll_m_item' + items.length
						+ '" onclick="url_parser(' + item_url + ')">'
						+ user_input + '</a></div>');

		$('.scroll_m_question_popup').fadeOut(200);

		// 검색어 입력값 초기화
		$('.scroll_pop_input').val('');
	}
}

// check sessionStorage
function chk_sessionStorage() {
	console.log('cheking sessionStorage...');
	var session_value = '1'; // 값이 null이 아닐 때까지 돌려주기 위해 임시 값 저장
	var idx = 0; // index value
	while (session_value != null) { //session에 없는 key로 요청을 할 경우 null을 반환받는다.
		var storage_key = 'scroll_item_url' + idx;
		console.log(sessionStorage.getItem(storage_key));
		session_value = sessionStorage.getItem(storage_key);
		session_keyword = sessionStorage.getItem('scroll_item_keyword' + idx);

		if (session_value != null) {
			session_value = "'" + session_value + "'";
			$('.scroll_m_sel_btn').append(
					'<div><a class="dropdown-item scroll_m_item' + idx
							+ '" onclick="url_parser(' + session_value + ')">'
							+ session_keyword + '</a></div>');
		}

		idx++;
	}
}

// scrollY파라미터 체킹하는 함수
function url_parser(url_scrollY) {
	// 파라미터가 담길 배열
	function getParams() {
		var param = new Array();

		// 현재 페이지의 url
		var url = decodeURIComponent(url_scrollY);
		// url이 encodeURIComponent 로 인코딩 되었을때는 다시 디코딩 해준다.
		url = decodeURIComponent(url);

		console.log('url ==> ' + url);

		var params;
		// url에서 '?' 문자 이후의 파라미터 문자열까지 자르기
		params = url.substring(url.indexOf('?') + 1, url.length);
		// 파라미터 구분자("&") 로 분리
		params = params.split("&");
		console.log('params==> ' + params);
		// params 배열을 다시 "=" 구분자로 분리하여 param 배열에 key = value 로 담는다.
		var size = params.length;
		console.log(size);
		var key, value;

		// scrollY값을 제외하고 다른 파라미터를 붙이기 위한 변수 준비
		var ex_scroll_params = ' ';

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
						console.log('max_yvalue ==> ' + max_yvalue);
						value = max_yvalue;
					}

					// scrollY위치로 이동.
					$('html').animate({
						scrollTop : value
					}, 200);

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
		//console.log('ex_scroll_params ==> ' + ex_scroll_params);
		// scrollY파라미터 삭제하기 위해서. 클래스 패스만 남음.
		history.pushState({}, null, ex_scroll_params);

	}

	// url parse function
	getParams();
}

$(document).ready(function() {
	// parse parameter
	url_parser(location.href);

	// chk sessionStorage
	chk_sessionStorage();
	
	$('.dropdown-toggle').dropdown()
});