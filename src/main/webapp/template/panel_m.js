
$(document).ready(function() {
	 $('#func1').load("/mammoth/function/scroll/scroll_m.html");
     $('#func2').load("/mammoth/function/share/share.html");
     /*$('#func3').load("/mammoth/function/recent/recent.html");*/
     $('#func4').load("/mammoth/function/orderlist/orderlist_popuplayer_m.html");
    
     /* zoom 큰 메뉴 버튼 클릭 시 */
     $('#zoomBtn').click(function() {
    	 	console.log('zoom click');
    	 	$('.zoom-btn-sm').toggleClass('scale-out');
     });
     
     /* zoom 큰 메뉴 내부의 작은 메뉴 버튼 클릭 시 */
    $('.zoom-btn-sm').click(function() {
    	// 선택 li 색상 변경 (plt-pn-btn-active class로 bg 색상 부여)
    	$(this).addClass('plt-pn-btn-active');
    	$(this).removeClass('plt-pn-btn-inactive');
    	$('.zoom-btn-sm').not($(this)).removeClass('plt-pn-btn-active').addClass('plt-pn-btn-inactive');
    	
    	// 선택 li의 data-stat-id와 연결되는 <div> item
        var item = $('#'+$(this).data('stat-id'));
        
        // 선택 li와 연결되는 card가 열려있으면 card를 닫아줌.
        if(!item.hasClass('scale-out')) {
        	console.log(item.attr('id')+' has not class');
        	item.addClass('scale-out');
        	$(this).removeClass('plt-pn-btn-active');
        	$(this).addClass('plt-pn-btn-inactive');
        	return;
        }
        
        // 선택 li와 연결되는 card를 열어줌.
        item.removeClass('scale-out');
        
        // 선택 li를 제외한 다른 li의 카드를 닫아줌
        $('.zoom-card').not(item).addClass('scale-out');
        
        item.click(function(e){
            e.stopPropagation(); 
        });
    });
});