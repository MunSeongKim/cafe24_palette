function openNav() {
	$('#panel').css('width', '25%');
}
function closeNav() {
	$('#panel').css('width', '0');
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
$("#panel-btn").draggable({
	axis : "y"
});
$("#panel-btn").click(function() {
	openNav();
});
$("#panel-close-btn").click(function() {
	if($('.popupLayer').css('display') == 'block') {
		$('.popupLayer').css('display', 'none');
	}
	closeNav();
});