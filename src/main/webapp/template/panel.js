function openNav() {
	// 180713 hwi 수정 width % -> 고정값 수정 (합의 완료)
	$('#panel-btn').toggleClass('open');
	/*$('#panel-btn').addClass('opentest');*/
	$('#panel-btn').css('right', '15.625em');
	$('#panel-btn').css('left', '');
	$('#panel').css('width', '15.625em');
	
	// 180713 hwi 추가 (패널 열었을 때 스크롤 맨 위, 아래 div도 밀어주기 위함)
	$('.scroll_mm_div').css('right', '18.125em');
}
function closeNav() {
	$('#panel').css('width', '0');
	$('#panel-btn').css('right', '0');
	$('#panel-btn').css('left', '');
	// 180713 hwi 수정 removeClass -> toggleClass 토글 형식으로 꼈다 넣다 하기 때문에 toggle로 class를 삭제함
	$('#panel-btn').toggleClass('open');
	
	// 180713 hwi 추가 (패널 열었을 때 스크롤 맨 위, 아래 div도 밀어주기 위함)
	$('.scroll_mm_div').css('right', '2.5em');
}
$(document).keyup(function(e) {
	if (e.keyCode == 27) { // escape key maps to keycode `27`
		if($('.popupLayer').css('display') == 'block') {
			$('.popupLayer').css('display', 'none');
			return;
		}
		closeNav();
	}
});

$(document).ready(function() {
	$('#panel-area').load('/mammoth/template/panel.html', function(){
		$("#panel-btn").draggable({
			axis : "y",
			//containment:"window" // 180713 hwi 추가
		});
		$("#panel-btn").click(function() {
			if($('#panel-btn').is('.open') == true) {
				closeNav();
				return;
			}
			openNav();
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
		/* scroll end */
		
		// order-list popup
		$('#panel-func-orderlist').load("/mammoth/function/orderlist/orderlist_popuplayer.html");
		
		// 180713 hwi scroll function add
		$('#func1').load("/mammoth/function/scroll/scroll.html");
		
		// 180713 hwi share function add
		$('#func2').load("/mammoth/function/share/share.html");
	});
		
})