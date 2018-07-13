function includeFile(path, type) {
  if (type == "js") { //if filename is a external JavaScript file
    var element = document.createElement('script');
    element.setAttribute("type", "text/javascript");
    element.setAttribute("src", path);
  } else if (type == "css") { //if filename is an external CSS file
    var element = document.createElement("link");
    element.setAttribute("rel", "stylesheet");
    element.setAttribute("type", "text/css");
    element.setAttribute("href", path);
  }

  if (typeof element != "undefined") {
    document.head.appendChild(element);
  }
}

// append a element[palette area] on body
function includeTargetElement() {
  var element = document.createElement("div");
  element.setAttribute("id", "panel-area");
  document.body.appendChild(element);
}

/*
 * 파일 로드 순서는 건드리지 말아주셔여!
 * 순서 꼬이면 뒤에 안불러지는 함수가 있습니다~ 혹시 바꾸시면 말해주세요
 */


// 180713 hwi 추가
// javascript load 순서 수정
if(typeof jQuery == 'undefined'){
	 var headTag = document.getElementsByTagName("head")[0];
	    var jqTag = document.createElement('script');
	    jqTag.type = 'text/javascript';
	    jqTag.src = 'https://code.jquery.com/jquery-3.3.1.min.js';
	    jqTag.onload = /*myJQueryCode;*/function(){
	        var jqTag1 = document.createElement('script');
	        jqTag1.type = 'text/javascript';
	        jqTag1.src = 'https://code.jquery.com/ui/1.12.1/jquery-ui.js';
	        jqTag1.onload = function(){
	        	includeFile("https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css", "css");
	        	includeFile("https://use.fontawesome.com/releases/v5.1.0/css/all.css", "css");
	        	includeFile("/mammoth/template/panel.css", "css");
	        	includeFile("/mammoth/function/orderlist/orderlist_popuplayer.css", "css");
	        	includeFile("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js", "js");
	        	includeFile("https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css", "css");
	        	includeFile("https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js", "js");
	        	
	        	window.onload = function() {
	        		includeTargetElement();
	        		includeFile("/mammoth/template/panel.js", "js");
	        	};
	        };
	        headTag.appendChild(jqTag1);
	    };
	    headTag.appendChild(jqTag);
}/*else{
	myJQueryCode();
}*/
	