$('#zoomBtn').click(function() {

	$('.zoom-btn-sm').toggleClass('scale-out');

	// 큰 버튼 클릭시 무조건 꺼버리는 조건
	$('.zoom-card').addClass('scale-out');

});

$('.zoom-btn-sm').click(function() {
	var btn = $(this);

	/*
	 * var card = $('.zoom-card');
	 	 if ($('.zoom-card').hasClass('scale-out')) {
	  $('.zoom-card').toggleClass('scale-out');
	}
	if (btn.hasClass('zoom-btn-person')) {
	  card.css('background-color', '#d32f2f');
	} else if (btn.hasClass('zoom-btn-doc')) {
	  card.css('background-color', '#fbc02d');
	} else if (btn.hasClass('zoom-btn-tangram')) {
	  card.css('background-color', '#388e3c');
	} else if (btn.hasClass('zoom-btn-report')) {
	  card.css('background-color', '#1976d2');
	} else {
	  card.css('background-color', '#7b1fa2');
	}*/
});

$(document).ready(function() {
	var find_li = $('.zoom-menu').find('> li');
	console.log('item_length' + find_li.length);
	var id = $('li').data('func-id')
	
	find_li.click(function(e) {
		//alert($(this).attr('class'));
		var div_item = $(this).find('> div');
		console.log('div_item ==> ' + div_item.attr('id'));
		
		div_item.toggleClass('scale-out');
		
		div_item.click(function(e){
			e.stopPropagation(); 
		});
		
	});
});