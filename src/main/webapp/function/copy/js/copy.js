var copy_init = (function(){
	// click event 중복 방지 handle
	var scroll_copy_btn_handle = null;	
	
	return {
		init : function(){
			// tooltip 초기화
			$('.scroll_copy_button').tooltip();
			
			$('#scroll_text_url').val(location.href);
			// 복사 버튼 클릭시
			$('.scroll_copy_button').off().click(function(){
				var scroll_copy_text = $('#scroll_text_url');
				
				// 현재 페이지에 scrollY값 붙여주기.
				var url_chk_params = (location.href).split('?');
				var scrollY = window.pageYOffset;
				
				// url에 이미 파라미터 값이 있을 경우
				if(url_chk_params.length > 1){
					$('#scroll_text_url').val(location.href + '&scrollY=' + window.pageYOffset );
				}else{ // url에 파라미터가 없는 경우
					$('#scroll_text_url').val(location.href + '?scrollY=' + window.pageYOffset );
				}
				
				// input text에 있는 값 drag효과		
				scroll_copy_text.select();
				// clipboard로 text값 복사
				document.execCommand("copy");
				
				// 1.5초 뒤에 visibility 값 hidden으로
				///////////////////////////////////////////////////////문제 mouseout일 때 이상하게 돌아감. solved
				// 중복 이벤트 제거
				// 원래 버튼에 사용하던 div 팝업 이벤트 
				var el = $(this);
				
				if(scroll_copy_btn_handle == null){
					copy_init.startInterval(el);
				}else{
					copy_init.stopInterval();
					copy_init.startInterval(el);
				}
			}); //end #scroll_text_url click function
		}, // end init
		stopInterval : function(){
			// interval 이벤트 종료
			clearInterval(scroll_copy_btn_handle);
			scroll_copy_btn_handle = null;
		}, // end stopInterval
		startInterval : function(el){
			// copy 버튼 눌렀을 때 발생하는 이벤트
			el.attr('data-original-title', 'copied').tooltip('show');
			
			scroll_copy_btn_handle = setInterval(function(){
				el.attr('data-original-title', '').tooltip('hide');
			}, 500);
		} // end startInterval
	
	} // end return
})();

$(document).ready(function(){
	copy_init.init();
	/* 도움말 관련
	$('.far.scroll_question_button').off().click(function(){
		if($('.scroll_question_popup').css('display') == 'none' ){
			$('.scroll_question_popup').fadeIn(500);
		}
	});
	
	$('.scroll_question_popup_close').off().click(function(){
		if($('.scroll_question_popup').css('display') == 'block'){
			$('.scroll_question_popup').fadeOut();
		}
	});
	 */
});