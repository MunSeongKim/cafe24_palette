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
		/* scroll end */
		
		//$('#panel-func-orderlist').load("/mammoth/function/orderlist/orderlist_popuplayer.html");
		$('#func1').load("/mammoth/function/scroll/scroll_m.html");
		$('#func2').load("/mammoth/function/share/share.html");
		//$('#func3').load("/mammoth/function/recent/recent.html");
});