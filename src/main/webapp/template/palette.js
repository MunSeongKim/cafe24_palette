(function($) {
    var p = {};
    $.panel = {
        
        // p의 디폴트 값.
        defaults : {
            'position' : 'right',
            'removePosition' : 'left'
        },
        
        // p에 default값 세팅 / 위치가 바뀌면 바뀐 위치+default를 p에 세팅
        setPosition : function(positions){
            p = $.extend(true, {}, $.panel.defaults, positions);
        },
        
        // 패널 버튼을 클릭하였을 때, action에 따라 패널, 패널 버튼, 스크롤의
        // CSS를 변경한다.
        nav : function(action) {
            $('#panel-draggable-btn').toggleClass('open'); // 버튼 모양 변경
            $.panel.changePanel(action);
        },
        
        // 패널, 버튼, 스크롤의 CSS 결정.
        changePanel : function(action){
            var panelCss = {};
            var draggableCss = {};
            var scrollCss = {};
            
            panelCss[p.position] = '0';
            panelCss[p.removePosition] = '';
            panelCss['width'] = '15.625em';
            
            if(action == 'open') {
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
        },
        // panel position에 따른 팝업 css 만들어줌.
       /* makePopupCss : function() {
            var popupCss = {};
            popupCss['position'] = 'fixed';
            popupCss[p.position] = $('#panel').width();
            popupCss['top'] = '20%';
            return popupCss;
        }*/
        
        // panel position에 따른 팝업 css 만들어줌.
        makePopupCss : function(layer, action) {
        	if(action === 'open'){
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
      if($('.popupLayer').css('display') == 'block') {
         $('.popupLayer').css('display', 'none');
         return;
      }
      if(!$('#panel-draggable-btn').is('.open')) { return; }
      $.panel.nav('close');
   }
});

$(document).ready(function() {
	var position = pos.toLowerCase();
	console.log(position);
    var removePosition = 'left';
    
    if(position === 'left') { removePosition = 'right'; }
    
    $.panel.setPosition({'position': position, 'removePosition':removePosition});
    $.panel.changePanel('close');
	
	$("#panel-draggable-btn").draggable({
		axis : "y",
		containment : "window" // 180713 hwi 추가
	});
	
	$("#panel-draggable-btn").click(function() {
		if ($('#panel-draggable-btn').is('.open') == true) {
			if ($('.popupLayer').css('display') == 'block') {
				$('.popupLayer').css('display', 'none');
			}
			$.panel.nav('close');
			return;
		}
		$.panel.nav('open');
	});

	/*
	 * 180712 hwi 스크롤 맨 위, 아래 버튼 start
	 */
	$(".scroll_mm_div").draggable({
		axis : "y", // option : x, y
		containment : "window" // option : parent, document,
								// window 화면에서 element나가는 것을
								// 방지하기 위해 window선언
	});
	
	$('.scroll_top').click(function() {
		console.log('toptop');
		$('html, body').animate({
			scrollTop : 0
		}, 200); // 무조건 맨 위 scrollY value 0
	});

	$('.scroll_bottom').click(
			function() {
				console.log('bottom');
				// document max height value - window max height
				// = 스크롤을 최대로 내릴 수 있는 height값이 나온다.
				$("html, body").animate(
						{
							scrollTop : $(document).height()
									- $(window).height()
						}, 200);
			});
	/* scroll end */
});