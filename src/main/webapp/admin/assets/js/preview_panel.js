/*
 * 패널 미리보기 JS
 * $.panel 을 통해 preview_panel을 조정한다.
 * 
 */
(function($) {
	var p = {};
	
	$.panel = {
		// p의 디폴트 값.
		defaults : {
			visible : false,
			funcVisible : false,
			position : 'right',
			removePosition : 'left'
		},
		
		init : function(opts){
			
			$('#panelArea').load('/mammoth/setting/preview', function(){
				
				// preview_panel.jsp가 모두 로드되어  id가 panel인 element가 생성 된 후.
				// 그래서 callback 함수 안에 있는 것임.
				p = $.extend(true, {}, $.panel.defaults, opts);
				
				$("#panel-draggable-btn").draggable({
					axis : "y", 
					containment : "window" // 180713 hwi 추가
				});
				
				$("#panel-draggable-btn").on('click', function() {
					if($(this).is('.open') == true) {
						// 열려 있는 popup layer 모두 닫기.
						$('.popupLayer').removeClass("popup-"+p.position+"-open");
						$.panel.close();
						return;
					}
					
					$(this).addClass('open');
					$.panel.open();
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
				
				if(p.visible == true){
					$.panel.open();
				}else if(p.visible == false){
					$.panel.close(); 
				}else if(p.visible == "hide"){
					$.panel.displayNone();
				}
			});
		},
		
		// 다시 위치를 결정 할 때.
		setPosition : function(position){ 
			p = $.extend(true, {}, p, position);
			
			// 기존의 방향과 다른 class는 삭제.
			$("#panel").removeClass("panel-"+p.removePosition+"-open panel-"+p.removePosition+"-close");
			$("#panel-draggable-btn").removeClass("panel-"+p.removePosition+"-draggable-open panel-"+p.removePosition+"-draggable-close");
			$(".scroll_mm_div").removeClass("panel-"+p.removePosition+"-scroll-open panel-"+p.removePosition+"-scroll-close");
			
			// position이 바뀌어서 다시 적용시키기 위해서 여기서 open()을 호출.
			$.panel.open();
		},
		getPosition: function() {
			return p.position;
		},
		
		open : function(){
			$('#panel-draggable-btn').addClass('open');
			$.panel.changePanel('open'); 
		},
		
		close : function(){
			$('#panel-draggable-btn').removeClass('open');
			$.panel.changePanel('close');
		},
		
		displayNone : function(){
			$('#panel-draggable-btn').addClass('open');
			$("#panelArea").hide();
		},
		// 패널, 버튼, 스크롤의 CSS 결정.
		changePanel : function(action){
			if(action == 'open') {
				$("#panel").removeClass("panel-"+p.position+"-close");
				$("#panel-draggable-btn").removeClass("panel-"+p.position+"-draggable-close");
				$(".scroll_mm_div").removeClass("panel-"+p.position+"-scroll-close");
				
				$("#panel").addClass("panel-"+p.position+"-open");
				$("#panel-draggable-btn").addClass("panel-"+p.position+"-draggable-open");
				$(".scroll_mm_div").addClass("panel-"+p.position+"-scroll-open");
			}
			
			// 패널을 닫았을 때.
			else if(action == 'close'){
				$("#panel").removeClass("panel-"+p.position+"-open");
				$("#panel-draggable-btn").removeClass("panel-"+p.position+"-draggable-open");
				$(".scroll_mm_div").removeClass("panel-"+p.position+"-scroll-open");
				
				$("#panel").addClass("panel-"+p.position+"-close");
				$("#panel-draggable-btn").addClass("panel-"+p.position+"-draggable-close");
				$(".scroll_mm_div").addClass("panel-"+p.position+"-scroll-close");
			}
		},
		
		// panel position에 따른 팝업 css 만들어줌.
        makePopupCss : function(layer, action) {
        	if(action === 'open'){
        		// 현재 켜져있는 popup을 끄고 해당 layer의 popup만 켜준다.
        		$('.popupLayer').removeClass("popup-"+p.position+"-open");
        		$(layer).removeClass("popup-close");
        		$(layer).removeClass("popup-"+p.removePosition+"-open");
        		$(layer).addClass("popup-"+p.position+"-open");
        	}else if(action === 'close'){
        		$(layer).removeClass("popup-"+p.position+"-open");
        		$(layer).removeClass("popup-"+p.removePosition+"-open");
        		$(layer).addClass("popup-close");
        	}
        }
	}
}(jQuery));

// ESC키 누르면 패널 닫힘.
$(document).keyup(function(e) {
   if (e.keyCode == 27) { // escape key maps to keycode `27`
	  
	  var pos = $.panel.getPosition();
	  
	  if($('.popupLayer').hasClass("popup-"+pos+"-open")){
		  $('.popupLayer').removeClass("popup-"+pos+"-open");
	      return;
	  }
	  
      if(!$('#panel-draggable-btn').is('.open')) { return; }
      
      $.panel.close();
   }
});
