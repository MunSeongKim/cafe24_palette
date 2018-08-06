$(function() {
	$('.cu-btn-submit').click(function() {
		var input = $('.cu-contact > p:nth-child(2)');
		
		for(var i=0; i<input.size(); i++) {
			if($(input[i]).children().val() == '') {
				$(input[i]).children().focus();
				return;
			}
		}
		$('.cu-contact > p:nth-child(2)').children().val('');
		alert("success!!");
		
	});
});