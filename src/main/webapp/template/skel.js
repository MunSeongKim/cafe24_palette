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

/* jquery js*/ 
includeFile("https://code.jquery.com/jquery-3.3.1.min.js", "js");
/* jquery UI */
includeFile("https://code.jquery.com/ui/1.12.1/jquery-ui.js", "js");

/* css */
includeFile("https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css", "css");
includeFile("https://use.fontawesome.com/releases/v5.1.0/css/all.css", "css");

/* custom css */
includeFile("/mammoth/template/panel.css", "css");
includeFile("/mammoth/function/orderlist/orderlist_popuplayer.css", "css");

setTimeout(() => {
	includeFile("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js", "js");
	/* bootstrap */
	includeFile("https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css", "css");
	includeFile("https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js", "js");
}, 1);

/* custom js */
window.onload = function() {
	includeTargetElement();
	includeFile("/mammoth/template/panel.js", "js");
};


	