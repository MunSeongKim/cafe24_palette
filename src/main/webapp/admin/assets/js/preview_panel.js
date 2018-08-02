/*
 * 패널 미리보기 JS
 * $.previewPanel 을 통해 preview_panel을 조정한다.
 * 
 */
(function($) {
	var p = {};
	
	$.previewPanel = {
		// p의 디폴트 값.
		defaults : {
			visible : false,
			funcVisible : false,
			position : 'right',
			removePosition : 'left'
		},
		
		init : function(opts){
			
			$('#panelArea').load('/mammoth/setting/preview', function(){
				
				$("#panel-draggable-btn").draggable({
					axis : "y", 
					containment : "window" // 180713 hwi 추가
				});
				
				$("#panel-draggable-btn").on('click', function() {
					if($(this).is('.open') == true) { 
						$.previewPanel.close(); 
						return;
					}
					
					$(this).addClass('open');
					$.previewPanel.open();
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
					$('html, body').animate({scrollTop: 0}, 200); // 무조건 맨 위 scrollY value 0
				});
				
				$('.scroll_bottom').click(function(){
					// document max height value - window max height = 스크롤을 최대로 내릴 수 있는 height값이 나온다.
					$("html, body").animate({scrollTop:$(document).height()- $(window).height()}, 200);
				});
				
				// preview_panel.jsp가 모두 로드되어  id가 panel인 element가 생성 된 후.
				// 그래서 callback 함수 안에 있는 것임.
				p = $.extend(true, {}, $.previewPanel.defaults, opts);
				
				if(p.visible == true){
					$.previewPanel.open();
				}else if(p.visible == false){
					$.previewPanel.close(); 
				}else if(p.visible == "hide"){
					$.previewPanel.displayNone();
				}
				
				/*if(p.funcVisible == false){
					$(".preview_func_div").addClass("hide");
				}*/ 
				
			});

		},
		
		setPosition : function(position){ 
			p = $.extend(true, {}, p, position);
			$.previewPanel.open();
		},
		
		open : function(){
			$('#panel-draggable-btn').addClass('open');
			$.previewPanel.changePanel('open'); 
		},
		
		close : function(){
			$('#panel-draggable-btn').toggleClass('open');
			$.previewPanel.changePanel('close');
		},
		
		displayNone : function(){
			$('#panel-draggable-btn').toggleClass('open');
			$("#panelArea").hide();
		},
		
		// 패널, 버튼, 스크롤의 CSS 결정.
		changePanel : function(action){
			var panelCss = {};
			var draggableCss = {};
			var scrollCss = {};
			
			panelCss[p.position] = '0';       // ex) 오른쪽 패널이면 position -> right 이므로 right를 0으로 하고
			panelCss[p.removePosition] = '';  //     left 속성은 제거한다.
			
			if(action == 'open') {
				panelCss['width'] = '15.625em';
				panelCss['margin-'+p.position] = '0';
				draggableCss[p.position] ="15.625em";
				draggableCss[p.removePosition]="";
				
				if(p.position == 'left'){
					scrollCss[p.position] ="16em";
				}else{
					scrollCss[p.position] ="17.625em";
				}
				
				scrollCss[p.removePosition]="";
			} 
			// 패널을 닫았을 때.
			else {
				panelCss['width'] = '0em';
				panelCss['margin-'+p.position] = '-15.625em';
				draggableCss[p.position] ="0";
				draggableCss[p.removePosition]="";
				
				if(p.position == 'left'){
					scrollCss[p.position] ="0em";
				}else{
					scrollCss[p.position] ="2.5em";
				}
				
				scrollCss[p.removePosition]="";
			}
			
			// CSS 적용
			$("#panel").css(panelCss);
			$("#panel-draggable-btn").css(draggableCss);
			$(".scroll_mm_div").css(scrollCss);
		}
	}
}(jQuery));

// ESC키 누르면 패널 닫힘.
$(document).keyup(function(e) {
	if (e.keyCode == 27) { // escape key maps to keycode `27`
		if(!$('#panel-draggable-btn').is('.open')) { return; }
		$.previewPanel.close();
	}
});
