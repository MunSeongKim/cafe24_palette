(function($) {
    var p = {};
    
    $.panel = {
        // p의 디폴트 값.
        defaults : {
        	visible : false,
			funcVisible : false,
            'position' : 'right',
            'removePosition' : 'left'
        },
        
        init : function(opts){
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
        },
        
        // p에 default값 세팅 / 위치가 바뀌면 바뀐 위치+default를 p에 세팅
        // palette.jsp나 js에서는 얘를 호출 할 이유가 없음. -> position이 변할 일이 없음.
        setPosition : function(positions){
            p = $.extend(true, {}, $.panel.defaults, positions);
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
        		// Deo - 현재 켜져있는 popup을 끄고 해당 layer의 popup만 켜준다.
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

$(document).ready(function() {
	var position = pos.toLowerCase();
    var removePosition = 'left';
    if(position === 'left') { removePosition = 'right'; }
    
    // service panel 기본 동작 invisible
    $.panel.init({
    	visible : false,
    	position : position,
    	removePosition : removePosition
    });
});