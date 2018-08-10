
$Palette(document).ready(function() {
	$Palette('.personal-funcs .container .row div > a').hover(function() {
		$Palette(this).parent().addClass('plt-pn-btn-default');
	}, function() {
		$Palette(this).parent().removeClass('plt-pn-btn-default');
	});
	if($Palette('#panel').hasClass('preview')) {
		$Palette('.personal-funcs .container .row div > a').on('click', function(e) {
			e.preventDefault();
			alert('해당 기능의 페이지로 이동합니다.');
		});
	}
});
