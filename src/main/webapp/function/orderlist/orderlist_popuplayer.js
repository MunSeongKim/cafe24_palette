function closeLayer(obj) {
	$(obj).parent().parent().parent().hide();
}

/* 클릭시 클릭한 위치 근처에 레이어가 나타난다. */
$(document).ready(function() {
	$('.func-orderlist').click(function(e) {
		$('.popupLayer').css({
			"top" : '20%',
			"right" : '25%',
			"position" : "absolute"
		}).show();

	});
})
