(function($) {
	$.panel = {
		nav : function(action) {
			$('#panel-draggable-btn').toggleClass('open');
			if(action === 'open') {
				$('#panel').css('width', '15.625em');
				$('#panel-draggable-btn').css({
					'right' : '15.625em',
					'left' : ''
				});
				$('.scroll_mm_div').css('right', '18.125em');
			} else if(action === 'close') {
				$('#panel').css('width', '0');
				$('#panel-draggable-btn').css({
					'right' : '0',
					'left' : ''
				});
				$('.scroll_mm_div').css({
					'right' : '2.5em',
					'left' : ''
				});
			} else {
				return 0;
			}
		}
	}
}(jQuery));

$(document).keyup(function(e) {
	if (e.keyCode == 27) { // escape key maps to keycode `27`
		if($('.popupLayer').css('display') == 'block') {
			$('.popupLayer').css('display', 'none');
			return;
		}
		if(!$('#panel-draggable-btn').is('.open')) { return; }
		$.panel.nav('close');
	}
});

$(document).ready(function() { 
	/// end device check 
	$('#panel-area').load('/mammoth/setting/preview', function(){
		$.panel.nav('open');
		$("#panel-draggable-btn").draggable({
			axis : "y", 
			containment : "window" // 180713 hwi 추가
		});
		
		$("#panel-draggable-btn").click(function() {
			if($('#panel-draggable-btn').is('.open') == true) {
				if($('.popupLayer').css('display') == 'block') {
					$('.popupLayer').css('display', 'none');
				}
				$.panel.nav('close');
				return;
			}
			$.panel.nav('open');
		});
		
		/*
		 * 180712 hwi
		 * 스크롤 맨 위, 아래 버튼 start 
		 */
		// draggable 참고사이트: https://api.jqueryui.com/draggable/#option-containment
		$(".scroll_mm_div").draggable({
			axis : "y",  // option : x, y
			containment:"window"  // option : parent, document, window 화면에서 element나가는 것을 방지하기 위해 window선언
		});
		$('.scroll_top').click(function(){
			console.log('toptop');
			$('html, body').animate({scrollTop: 0}, 200); // 무조건 맨 위 scrollY value 0
		});
		
		$('.scroll_bottom').click(function(){
			console.log('bottom');
			// document max height value - window max height = 스크롤을 최대로 내릴 수 있는 height값이 나온다.
			$("html, body").animate({scrollTop:$(document).height()- $(window).height()}, 200);
		});
		
	});
		
});
