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

/*
 *  properties만들어서 for문돌리기
 */
var files = [
		//'https://code.jquery.com/jquery-3.3.1.min.js',
		'http://code.jquery.com/jquery-1.11.1.min.js',
		'https://code.jquery.com/ui/1.12.1/jquery-ui.js',
		'https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css',
		'https://use.fontawesome.com/releases/v5.1.0/css/all.css',
		'/mammoth/template/panel.css',
		'/mammoth/function/orderlist/orderlist_popuplayer.css',
		'http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css',
		'http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js',
		'https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js',
		'https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css',
		'https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js',
];


function loadScriptsSequential(scriptsToLoad) {
    function loadNextScript() {
        var done = false;
        var head = document.getElementsByTagName('head')[0];
        var src = scriptsToLoad.shift();
        
        var exe = extractExt(src);
        var script;
        
        if( exe === '.js'){
	        script = document.createElement('script');
	        script.type = 'text/javascript';
	        script.src = src // grab next script off front of array
        }else if( exe === '.css'){
        	script = document.createElement("link");
        	script.rel = "stylesheet";
        	script.type = "text/css";
        	script.href = src;
        }
        
        script.onreadystatechange = function () {
            if (this.readyState == 'complete' || this.readyState == 'loaded') {
                scriptLoaded();
            }
        }

        script.onload = scriptLoaded;
       
        head.appendChild(script);

        function scriptLoaded() {
            // check done variable to make sure we aren't getting notified more than once on the same script
            if (!done) {
                script.onreadystatechange = script.onload = null;   // kill memory leak in IE
                done = true;
                if (scriptsToLoad.length != 0) {
                    loadNextScript();
                }else{
                	window.onload = function() {
    	        		includeTargetElement();
    	        		includeFile("/mammoth/template/panel.js", "js");
    	        	};
                }
            }
        }
    }
    loadNextScript();
}

loadScriptsSequential(files);


// extract exe
function extractExt( str ){
   //확장자 추출..
   //결과값 : false  .확장자
   var len = str.length;
   var last = str.lastIndexOf("."); //확장자 추출

   if( last == -1 ){ //. 를 발견하지 못한다면.
      return false; //확장자가 없음.
   }

   //var ext = str.substr(last, len - last );  //확장자 추출 (. 포함)
   var ext = str.substring(last, len);  //확장자 추출 (. 포함)

   ext = ext.toLowerCase(); //소문자로.

   return ext; //.jpg
}