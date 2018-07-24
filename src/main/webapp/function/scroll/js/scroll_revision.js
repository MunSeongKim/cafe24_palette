var handler_functions = (function() {

	return {
		click_handler : function(parent, target, url) {
			////event 담는 handler [각 item div]
			parent.bind('click', function() {
				if (target.css('display') == 'block') {
					console.log('handler 데이터 있는 상태');
					// scroll 이동 이벤트를 줘야됨.
					modal_functions.scroll_move(url);
				} else { // none 상태
					console.log('handler 데이터 없는 상태');

				}
			});
		}, // end click_handler
		mouseover_handler : function(target, parent, kwd) {
			parent.bind('mouseover', function() {
				target.css('display', 'block');
				$('#kwd').text(kwd);
			});
		}, // end mouseover_handler
		mouseout_handler : function(target, parent) {
			parent.bind('mouseout', function() {
				target.css('display', 'none');
				$('#kwd').text('');
			});
		}, // end mouseout_handler
		i_click_handler : function(target_f, target_s, parent, scroll_key,
				kwd_key) {
			target_s.bind('click', function(e) {
				// reference site : http://programmingsummaries.tistory.com/313
				e.stopPropagation();
				sessionStorage.removeItem(scroll_key);
				sessionStorage.removeItem(kwd_key);
				target_s.unbind('mouseover');
				target_s.unbind('mouseout');
				target_s.unbind('click');
				parent.unbind('click');
				parent.unbind('mouseout');
				parent.unbind('mouseover');
				target_f.css('display', 'none');
				target_s.css('display', 'none');
				$('#kwd').text('');

				parent.bind('click', function() {
					$('#saveItem').val($(this).attr('class'));
					// insert item event here
					$('.scroll-re-question-popup').position({
						of : window,
						my : 'center',
						at : 'center',
						collision : 'fit'
					});

					$('.scroll-re-question-popup').css('top', '30%');
					// modal 자체가 style로 때려 박기 때문에 다시 제거 해줘야됨.
					$('.scroll-re-question-popup').css('left', '');

					//$('.scroll_m_question_popup').center();
					$('.scroll-re-question-popup').fadeIn(200);
				});
			});
		}, // end i_click_handler
		i_mouseover_handler : function(target, parent) {
			target.bind('mouseover', function() {
				parent.unbind('click');
			});
		}, // end i_mouseover_handler
		i_mouseout_handler : function(target, parent, url) {
			target.bind('mouseout', function() {
				parent.bind('click', function() {
					if (target.css('display') == 'block') {
						// scroll 이동 이벤트를 줘야됨.
						modal_functions.scroll_move(url);
					} else { // none 상태
						console.log('데이터 없는 상태');
					}
				});
			});
		}, // end i_mouseout_handler
		add_handler : function (scroll_key, item_url, kwd_key, user_input, wrapper1, wrapper2, wrapper3) {
			// save at sessionStorage (key = itemID, value = item_url)
			sessionStorage.setItem(scroll_key, item_url);
			// save at sessionStorage (keyword = user input value)
			sessionStorage.setItem(kwd_key, user_input);

			// generate click event in wrapper div
			handler_functions.click_handler(wrapper3, wrapper1, item_url);

			// generate mouseover event in wrapper div
			handler_functions.mouseover_handler(wrapper2, wrapper3, user_input);

			// generate mouseover event in wrapper div
			handler_functions.mouseout_handler(wrapper2, wrapper3);

			// generate click event in last class
			handler_functions.i_click_handler(wrapper1, wrapper2, wrapper3, scroll_key, kwd_key);

			// generate mouseover event in last class
			handler_functions.i_mouseover_handler(wrapper2, wrapper3);

			// generate mouseout event in last class
			handler_functions.i_mouseout_handler(wrapper2, wrapper3, item_url);
		}
	}
})();

// initialize functions.
var init_functions = (function() {
	var settings = {};

	var item_handler = function() {
		$('#saveItem').val($(this).attr('class'));
		var wrapper1 = $('.' + $('#saveItem').val() + ' div:first-child');
		var wrapper2 = $('.' + $('#saveItem').val() + ' div:last-child');
		var wrapper3 = $('.' + $('#saveItem').val() + '');

		if (wrapper1.css('display') == 'block') {
			console.log('init 데이터 있는 상태');
			// scroll 이동 이벤트를 줘야됨.
			//modal_functions.scroll_move(url);
		} else { // none 상태
			console.log('init 데이터 없는 상태');
			// insert item event here
			$('.scroll-re-question-popup').position({
				of : window,
				my : 'center',
				at : 'center',
				collision : 'fit'
			});

			$('.scroll-re-question-popup').css('top', '30%');
			// modal 자체가 style로 때려 박기 때문에 다시 제거 해줘야됨.
			$('.scroll-re-question-popup').css('left', '');

			$('.scroll-re-question-popup').fadeIn(200);
		}
	};
	return {
		init : function() {
			settings.item = $('.scroll-item-wrapper').children();
			this.bindUIAction();
		},
		bindUIAction : function() {
			// item에 클릭할 시 event 생성
			settings.item.bind('click', item_handler);
		},
		chk_sessionStorage : function () {
			console.log('chk sessionStorage');
			var session_value = '1'; // 값이 null이 아닐 때까지 돌려주기 위해 임시 값 저장
			var idx = 1; // index value
			var idx_class = 1;

			while (idx < 10) { //session에 없는 key로 요청을 할 경우 null을 반환받는다.
				var storage_key = 'scroll_item_url' + idx;
				var kwd_key = 'scroll_item_keyword' + idx;

				session_value = sessionStorage.getItem(storage_key);
				session_keyword = sessionStorage.getItem(kwd_key);

				if (session_value != null) {
					//session_value = "'" + session_value + "'";

					// item count
					var wrapper1 = $('.item-idx-' + idx + ' div:first-child');
					var wrapper2 = $('.item-idx-' + idx + ' div:last-child');
					var wrapper3 = $('.item-idx-' + idx + '');

					wrapper1.css('display', 'block');

					handler_functions.add_handler(storage_key, session_value, kwd_key, session_keyword, wrapper1, wrapper2, wrapper3);

					// input keyword
					$('#item-inner-kwd' + idx).val(session_keyword);
				}
				idx++;
			}
		}
	} // end return
})();

// modal안에 있는 이벤트 관련 functions
var modal_functions = (function() {
	var current_scrollY = window.pageYOffset;
	var settings = {};

	// modal 그 자체
	settings.modal = $('.scroll-re-question-popup');
	// modal 속 input box객체
	settings.modalInput = $('.scroll-re-pop-input');
	// modal속 p 객체
	settings.modalP = $('.scroll-re-pop-input');
	
	return {
		init : function() {
			settings.item = $('.scroll-item-wrapper').children();
			settings.innerClose = $('.scroll-item-wrapper').children();
			this.bindModalBasicEvent();
		},
		bindModalBasicEvent : function() {

		},
		close_popup : function() {
			// close_popup = 팝업 닫기 버튼에 대한 event
			settings.modal.fadeOut(200);
			// 검색어 입력값 초기화
			settings.modalInput.val('');
		},
		save_popup : function() {
			var current_page = location.href;
			var url_chk_params = current_page.split('?');
			
			if (settings.modalInput.val() == '') {
				settings.modalP.show();
			} else {
				
				console.log('location.href ==> ' + current_page);
				
				var user_input = $('.scroll-re-pop-input').val();
				// item count
				var wrapper1 = $('.' + $('#saveItem').val() + ' div:first-child');
				var wrapper2 = $('.' + $('#saveItem').val() + ' div:last-child');
				var wrapper3 = $('.' + $('#saveItem').val() + '');

				wrapper1.css('display', 'block');

				// url에 이미 파라미터 값이 있을 경우
				if (url_chk_params.length > 1) {
					var item_url = current_page + '&scrollY=' + window.pageYOffset;
				} else { // url에 파라미터가 없는 경우
					var item_url = current_page + '?scrollY=' + window.pageYOffset;
				}

				var idx = $('#saveItem').val().split('-')[2];
				var scroll_key = "scroll_item_url" + idx;
				var kwd_key = "scroll_item_keyword" + idx;
				
				handler_functions.add_handler(scroll_key, item_url, kwd_key, user_input, wrapper1, wrapper2, wrapper3);

				// input keyword
				$('#item-inner-kwd' + idx).val(user_input);

				$('.scroll-re-question-popup').fadeOut(200);

				// 검색어 입력값 초기화
				$('.scroll-re-pop-input').val('');

			}
		},
		scroll_move : function(url_scrollY) {
			var param = new Array();

			var url = decodeURIComponent(url_scrollY);
			url = decodeURIComponent(url);

			var params;
			params = url.substring(url.indexOf('?') + 1, url.length);
			params = params.split("&");
			
			// params 배열을 다시 "=" 구분자로 분리하여 param 배열에 key = value 로 담는다.
			var size = params.length;
			var key, value;

			// scrollY값을 제외하고 다른 파라미터를 붙이기 위한 변수 준비
			var ex_scroll_params = ' ';

			if (params[0].split("=")[1] != 'undefined'
					&& params[0].split("=")[1] != null) {
				for (var j = 0; j < size; j++) {
					key = params[j].split("=")[0];
					value = params[j].split("=")[1];

					// 여기는 이상하게 '를 인식하네.. 
					//value = value.split('\'')[0];
					
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
					history.pushState({}, null, ex_scroll_params);
				} // end for
			}
			// scrollY파라미터 삭제하기 위해서. 클래스 패스만 남음.
			history.pushState({}, null, ex_scroll_params);
		}
	}// end return
})();

// 실행부
$(document).ready(function() {
	init_functions.init();
	init_functions.chk_sessionStorage();
	modal_functions.scroll_move(location.href);
});