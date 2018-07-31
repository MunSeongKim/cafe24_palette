////////////////////////////////////////////////////////////////////carousel
$.fn.infiniteCarousel = function () {

	function repeat(str, num) {
		return new Array( num + 1 ).join( str );
	}
  
	return this.each(function () {
		// 스크롤 버튼 감싸는 요소 wrapper의 overflow값을 hidden처리
		var $wrapper = $('> div', this).css('overflow', 'hidden'),
			$slider = $wrapper.find('> ul'),
			$items = $slider.find('> li'),
			$single = $items.filter(':first'), // 결국 div안에 ul안에 li안에 첫번째 요소를 찾기 위함.
			singleWidth = $single.outerWidth(), // outerWidth는 maring, border값을 포함한 너비
			visible = Math.floor($wrapper.innerWidth() / singleWidth), // note: doesn't include padding or border
			currentPage = 1,
			pages = Math.ceil($items.length / visible);  // 11 / 3 => 3
			
			$wrapper.after('<i class="fas fa-arrow-circle-left arrow scroll_back plt-pn-i"></i><i class="fas fa-arrow-circle-right arrow scroll_forward plt-pn-i"></i>');
			
		// li가 1개라도 있어야 실행.
		if($items.length != 0){

			// 1. Pad so that 'visible' number will always be seen, otherwise create empty items
			if (($items.length % visible) != 0) { // 마지막이 1개나 2개일 경우는 visible값도 1 또는 2
				//li value에 -1을 준 이유는 마우스 오버 피하기 위해서 ==> IE 환경에서 -1을 0으로 자동변경 ==> value 9999로 변경.
				$slider.append(repeat('<li value="9999" class="empty" />', visible - ($items.length % visible))); // empty class추가 3 - (11 % 3)
				$items = $slider.find('> li'); // 다음 li요소로 이동
			}

			// 2. Top and tail the list with 'visible' number of items, top has the last section, and tail has the first
			$items.filter(':first').before($items.slice(- visible).clone().addClass('cloned'));
			$items.filter(':last').after($items.slice(0, visible).clone().addClass('cloned'));
			$items = $slider.find('> li'); // reselect
			
			// 3. Set the left position to the first 'real' item
			$wrapper.scrollLeft(singleWidth * visible);
			
			// 4. paging function
			function gotoPage(page) {
				var dir = page < currentPage ? -1 : 1,  // false 일 경우 -1
					n = Math.abs(currentPage - page), 
					left = singleWidth * dir * visible * n;
				
				$wrapper.filter(':not(:animated)').animate({
					scrollLeft : '+=' + left
				}, 500, function () {
					if (page == 0) {
						$wrapper.scrollLeft(singleWidth * visible * pages);
						page = pages;
					} else if (page > pages) {
						$wrapper.scrollLeft(singleWidth * visible);
						// reset back to start position
						page = 1;
					} 

					currentPage = page;
				});                
				
				return false;
			}
			
			//$wrapper.after('<a class="arrow scroll_back"></a><a class="arrow scroll_forward"></a>');
			// 180711 화살표 i태그로 수정
			$wrapper.after('<i class="fas fa-arrow-circle-left arrow scroll_back plt-pn-i"></i><i class="fas fa-arrow-circle-right arrow scroll_forward plt-pn-i"></i>');
			
			// 5. Bind to the forward and back buttons
			$('.scroll_back', this).off().click(function () {
				return gotoPage(currentPage - 1);                
			});
			
			$('.scroll_forward', this).off().click(function () {
				return gotoPage(currentPage + 1);
			});
			
			// create a public interface to move to a specific page
			$(this).bind('goto', function (event, page) {
				gotoPage(page);
			});
			
			// get li value (it means li's value) (event)
			// and visible true keyword div
			$('.scroll_btn_ul li').off().on("mouseover", function(event){
				var myindex =  $(this).val();
				
				// 여기에 마우스 올렸을 때 이벤트 추가!
				// 9999는 empty박스라서 event에서 제외
				if(myindex != 9999){
					var keyword = sessionStorage.getItem('scroll_item_keyword'+myindex);
					//alert(keyword);
					$('.keyword_popup'+myindex).text(keyword);
					if(myindex % 3 == 0)
						$('.keyword_popup'+myindex).css("margin-left", "0.938em");
					else if(myindex % 3 == 2)
						$('.keyword_popup'+myindex).css("margin-left", "-5.750em");
					$('.keyword_popup'+myindex).show();
				}
			});	

			// mouse out event
			$('.scroll_btn_ul li').on("mouseout", function(event){
				var myindex =  $(this).val();
				console.log(myindex);
				
				// 여기에 마우스 올렸을 때 이벤트 추가!
				// $('.keyword_popup').css('display', 'show');
				// 9999는 empty박스라서 event에서 제외
				if(myindex != 9999){
					$('.keyword_popup'+myindex).hide();
				}
			});					
		
			// mouse click event
			$('.scroll_btn_ul li').on("click", function(event){
				var myindex =  $(this).val();
				
				// 9999는 empty박스라서 event에서 제외
				if( myindex != 9999){
					var scroll_url = sessionStorage.getItem('scroll_item_url'+myindex);
					
					if(scroll_url.split('?')[0] == (location.href).split('?')[0]){
						// 180731 변경
						// url_parser(scroll_url);
						href_parser(scroll_url);
					}
					else
						window.location.replace(scroll_url);
				}
			});
		
		} // end if
	}); 				
};

// keyword input popup function
function layer_popup(el){
	// 원래는 background dim처리하는 기술이 있어서 소스를 보다보면 fadeIn을 시키는 경우가 있음.
	// 하지만 상관없는 소스라는 것을 명심.
	var $el = $(el);        //레이어의 id를 $el 변수에 저장
	var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

	isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

	var $elWidth = ~~($el.outerWidth()), // ~~의 의미는 Math.floor()
		$elHeight = ~~($el.outerHeight()),
		docWidth = $(document).width(),
		docHeight = $(document).height();

	// 화면의 중앙에 레이어를 띄운다.
	if ($elHeight < docHeight || $elWidth < docWidth) {
		$el.css({
			marginTop: -$elHeight /2,
			marginLeft: -$elWidth/2
		})
	} else {
		$el.css({top: 0, left: 0});
	}
	
	// 키워드 입력 팝업 저장 버튼 이벤트
	// off()를 사용한 이유는 event가 refresh되지 않고 중복되서 off로 막아줬다. 안해주면 1,2,4,8... 으로 이벤트 발생.
	$('.scroll_pop_layerSave').off().click(function(){
		if($('.scroll_pop_input').val() == '') {
			//$('.scroll_pop_chk_p').css('display', 'show');
			$('.scroll_pop_chk_p').show();
			return false;
		}
		
		// 버튼을 동적으로 추가하기 위해 사전에 추가된 li을 삭제한다.
		$('#scroll_btn_ul li.cloned').remove();
		$('#scroll_btn_ul li.empty cloned').remove();
		$('#scroll_btn_ul li.empty').remove();
		
		var wrapper = $('> div', $('.scroll_infiniteCarousel')),
			slider = wrapper.find('> ul'),
			items = slider.find('> li');
			
		//$('#scroll_btn_ul').append('<li class="list-group-item" value='+items.length+'><a href=#"></a><div class="keyword_popup'+items.length+'">난 키워드야</div><div class="scroll_items'+items.length+'">'+items.length+'</div></li>');
		// 수정 작업 고고
		$('#scroll_btn_ul').append('<li value='+items.length+'><a href=#"></a><div class="keyword_popup'+items.length+'">난 키워드야</div><div class="scroll_items'+items.length+' plt-pn-li">'+items.length+'</div></li>');
		
		// 현재 페이지 url
		var current_page = location.href;
		// 현재 스크롤 위치
		var current_scrollY = window.pageYOffset;
		// 사용자가 입력한 키워드 값
		var user_input = $('.scroll_pop_input').val();
		
		var url_chk_params = current_page.split('?');
		
		// url에 이미 파라미터 값이 있을 경우
		if(url_chk_params.length > 1){
			item_url = current_page + '&scrollY=' + window.pageYOffset;
		}else{ // url에 파라미터가 없는 경우
			item_url = current_page + '?scrollY=' + window.pageYOffset;
		}
		
		// item_url == 현재 페이지 + scrollY value
		//var item_url = current_page + '?scrollY=' + current_scrollY;
		
		// save at sessionStorage (key = itemID, value = item_url)
		sessionStorage.setItem("scroll_item_url"+items.length, item_url);
		// save at sessionStorage (keyword = user input value)
		sessionStorage.setItem("scroll_item_keyword"+items.length, user_input);
		
		
		$('.scroll_infiniteCarousel').infiniteCarousel();
		isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
		return false;
	});
	
	// 키워드 입력 팝업 닫기 버튼 이벤트
	$('.scroll_pop_layerClose').off().click(function(){
		console.log('취소');
		isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
		return false;
	});

	$('.layer .dimBg').click(function(){
		$('.dim-layer').fadeOut();
		return false;
	});

}

// check sessionStorage
function chk_sessionStorage(){
	
	console.log('cheking sessionStorage...');
	var session_value = '1'; // 값이 null이 아닐 때까지 돌려주기 위해 임시 값 저장
	var idx = 0; // index value
	while(session_value != null){ //session에 없는 key로 요청을 할 경우 null을 반환받는다.
		var storage_key = 'scroll_item_url' + idx;
	
		session_value = sessionStorage.getItem(storage_key);
		
		if(session_value != null)
			$('#scroll_btn_ul').append('<li value='+idx+'><a href=#"></a><div class="keyword_popup'+idx+'">난 키워드야</div><div class="scroll_items'+idx+' plt-pn-li">'+idx+'</div></li>');
			
		idx++;
	}
}

// scrollY파라미터 체킹하는 함수
function url_parser(url_scrollY){
	// 파라미터가 담길 배열
	function getParams(){
		var param = new Array();
	 
		// 현재 페이지의 url
		var url = decodeURIComponent(url_scrollY);
		// url이 encodeURIComponent 로 인코딩 되었을때는 다시 디코딩 해준다.
		url = decodeURIComponent(url);
	 
		var params;
		// url에서 '?' 문자 이후의 파라미터 문자열까지 자르기
		params = url.substring( url.indexOf('?')+1, url.length );
		// 파라미터 구분자("&") 로 분리
		params = params.split("&");
		// params 배열을 다시 "=" 구분자로 분리하여 param 배열에 key = value 로 담는다.
		var size = params.length;
		console.log(size);
		var key, value;
		
		// scrollY값을 제외하고 다른 파라미터를 붙이기 위한 변수 준비
		var ex_scroll_params = ' ';
		
		if(params[0].split("=")[1] != 'undefined' && params[0].split("=")[1] != null){
			for(var j=0 ; j < size ; j++) {
				key = params[j].split("=")[0];
				value = params[j].split("=")[1];
				
				// scrollY값에 엄청 큰 값을 입력했을 때 막기 위해서.
				// 현재 페이지에서 가장 하단 부분 Yvalue를 구해준다.
				var max_yvalue = $(document).height() - $(window).height();
				
				// 파라미터에 scrollY값이 있을 경우
				if(key == 'scrollY'){
					
					// 현재 파라미터로 붙은 Y값이 최대값보다 클 경우
					if(value > max_yvalue){
						console.log('max_yvalue ==> ' + max_yvalue);
						value = max_yvalue;
					}
					
					// scrollY위치로 이동.
					$('html').animate({scrollTop: value}, 200);
					
					// scrollY파라미터는 없애줌.
					history.pushState({}, null, location.pathname);
				}else{
					console.log('else ==> ');
					if(j == 0){
						ex_scroll_params += '?' + key + '=' + value;
					}else if(j >= 1){
						// 첫 번째 파라미터가 scrollY일 경우 -- 그럴리는 없지만 혹시나해서 만들어놓음
						var qu_mark_chk = ex_scroll_params.split("?");
						if(qu_mark_chk.length < 2)
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

///// start jQuery
$(document).ready(function(){
	url_parser(location.href);
	
	// chk sessionStorage
	chk_sessionStorage();
	
	// carousel start
	$('.scroll_infiniteCarousel').infiniteCarousel();	
			
	// ==> 이 크기를 기준으로 나의 기능 사이즈가 잡힌다.
	// 내 기능의 전체 사이즈
	var scroll_width = $('#remote-body').width();
	var scroll_height = $('#remote-body').height() * 0.25;
	
	// 각 기능(복사, 스크롤 저장)의 높이 사이즈
	var scroll_ef_height = scroll_height * 0.5;
	
	//////////////////////////////////////////////////////////////// 스크롤 저장 버튼을 누를 경우
	$(document).keydown(function(event) {
	  if (event.keyCode == '114' || event.keyCode == '82') {
		$('.scroll_pop_chk_p').hide(); // 키워드 저장 경고문 하이드
		$('.scroll_pop_input').val(''); // 이전 키워드 입력값 초기화
		layer_popup($('.scroll_pop_layer'));
	  }
	});
	////////////////////////////////////////////////////////////////
});


/////////////////////////////////////////////////////////////////////
function href_parser(url_scrollY){
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
	
	console.log('ex_scroll_params ==> ' + ex_scroll_params);
	
	var url_chk_params = url.split('?');
	
	var origin_url = url_chk_params[0] + ex_scroll_params;
	// current_site = origin_url;
	
	/////// 180731 파라미터 값 수정중
	
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