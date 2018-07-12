window.onload = function() {
	includeFile(
			"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css",
			"css");
	includeFile(
			"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js",
			"js");
	
	
	
	// insert js - hwi
	includeTargetElement();
	$(function(){
		$('#panel').load("/template/panel.html", function(){
			$('#func1').load('/function/scroll/scroll.html', function(){
				$('[data-toggle="tooltip"]').tooltip();
			});
			$('#func2').load('/function/share/share.html');
			//$('#func2').load('https://lee33398.cafe24.com/function/share/share.html');
			// 추가 기능 - func3
			// 추가 기능 - func4
			
			//$('#panel-content').load('/function/scroll/scroll.html');
		});	
	});
	
	
}
