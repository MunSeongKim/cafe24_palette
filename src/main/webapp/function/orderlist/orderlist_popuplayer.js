function closeLayer() {
	$('.popupLayer').hide();
}

/* 클릭시 클릭한 위치 근처에 레이어가 나타난다. */
$(document).ready(function() {
	
	$('.func-orderlist').click(function(e) {
		 
		$.ajax({
			url:'https://devbit005.cafe24.com/api/orders',
			type: 'get',
			dataType: 'json',
			data: {
				'start_date' : '2018-07-01',
				'end_date' : '2018-07-13',
				'member_id' : 'chyin370'
			},
			success: function(response) {
				console.log(response);
			}
		});
		
		$('.popupLayer').css({
			"top" : '20%',
			"right" : $('#panel').width(),
			"position" : "absolute"
		}).show();

	});
})
