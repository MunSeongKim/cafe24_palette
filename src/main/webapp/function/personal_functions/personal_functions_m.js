function movePage(href) {
	console.log('move '+ href);
	window.location.href = href;
};
$Palette(document).ready(function() {
	$Palette('.personal-funcs').parent().addClass('personal-funcs-zoom-card');
});
