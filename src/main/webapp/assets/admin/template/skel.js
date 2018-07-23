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

// failed
// <meta http-equiv="Content-Security-Policy" content="block-all-mixed-content">
/*var meta_element = document.createElement('meta');
meta_element.httpEquiv = "Content-Security-Policy";
meta_element.content = "block-all-mixed-content";
document.getElementsByTagName('head')[0].appendChild(meta_element);*/


/*
 * 파일 로드 순서는 건드리지 말아주셔여!
 * 순서 꼬이면 뒤에 안불러지는 함수가 있습니다~ 혹시 바꾸시면 말해주세요
 */

/*
 *  properties만들어서 for문돌리기
 *  Mixed Content: ~~~ This request has been blocked; the content must be served over HTTPS. error 발생
 *  https통신을 하기 때문에 요청도 https로 해야됨. 다행히 모든 js cdn서버는 https제공. (solved)
 */
var files = [
		//'https://code.jquery.com/jquery-3.3.1.min.js',
		'/mammoth/assets/admin/template/theme1.css',
		'/mammoth/assets/admin/template/panel.css',
		'https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.js' 
];


function loadScriptsSequential(scriptsToLoad) {
    function loadNextScript() {
        var done = false;
        var head = document.getElementsByTagName('head')[0];
        var src = scriptsToLoad.shift();
        
        var exe = extractExt(src);
        var script;
        
        if( exe === '.js'){
        	/* $ = jQuery; $인식할 수 있도록 jQuery담아주기 */
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
                	// 170716 1:37 IE에서 여기 안들어오는 문제 발견. window.onload를 타지 못함.
                	// 어짜피 마지막에 들어오는 부분이라 onload를 빼버림.
                	//window.onload = function() {
	        		includeTargetElement();
	        		includeFile("/mammoth/assets/admin/template/panel.js", "js");
    	        	//};
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