// let's practice module pattern 
var readyToExe = (function() {
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
         'https://code.jquery.com/jquery-1.11.1.min.js',
         'https://code.jquery.com/ui/1.12.1/jquery-ui.js',
         //'https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js',
         //'https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css',
         //'https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css',
         //'https://use.fontawesome.com/releases/v5.1.0/css/all.css',
         //'/mammoth/template/theme1.css',
         //'/mammoth/template/panel.css',
         //'/mammoth/function/orderlist/orderlist_popuplayer.css',
         'https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js',
         //'https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css',
         'https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js',
         //'https://devbit005.cafe24.com/mammoth/static/mustachejs/2.2.1/mustache.min.js'
         //'/mammoth/template/panel.js'
         //'/mammoth/template/panel.jsp'
         ];

   var done = false;
   var head = document.getElementsByTagName('head')[0];
   // private area
   function createElement(path, type) {
      var element;

      if (type === ".js") { //if filename is a external JavaScript file
         element = document.createElement('script');
         element.type = 'text/javascript';
         element.src = path; // grab next script off front of array
      } else if (type === ".css") { //if filename is an external CSS file
         element = document.createElement("link");
         element.rel = "stylesheet";
         element.type = "text/css";
         element.href = path;
      }

      element.onreadystatechange = function() {
         if (this.readyState == 'complete' || this.readyState == 'loaded') {
            readyToExe.scriptLoaded(element);
         }
      }
      element.onload = readyToExe.scriptLoaded;

      head.appendChild(element);
   }

   return {
      // public area
      // includeFile function
      includeFile : function(path, type) {
         createElement(path, type);
      }, // end include File function

      includeTargetElement : function() {
         var element = document.createElement("div");
         element.setAttribute("id", "panel-area");
         document.body.appendChild(element);
      }, // end TargetElement function
      loadNextScript : function() {
         var src = files.shift();

         var exe = readyToExe.extractExt(src);
         var script;

         readyToExe.includeFile(src, exe);
      }, // end loadNextScript function
      scriptLoaded : function(element) {
         // check done variable to make sure we aren't getting notified more than once on the same script
         if (!done) {
            element.onreadystatechange = element.onload = null; // kill memory leak in IE
            //done = true; 원래 여기에 true있는데 패턴 변경한 순간... 로직이 바뀐듯 나중에 확인해보자.
            if (files.length != 0) {
               if (files.length == 1) {
                  //createElement div
                  readyToExe.includeTargetElement();
               }
               readyToExe.loadNextScript(files);
            } else {
            	$('#panel-area').load('/mammoth/template/panel.jsp');
            }
         }
      }, // end scriptLoaded
      extractExt : function(str) {
         //확장자 추출..
         //결과값 : false  .확장자
         var len = str.length;
         var last = str.lastIndexOf("."); //확장자 추출

         if (last == -1) { //. 를 발견하지 못한다면.
            return false; //확장자가 없음.
         }

         //var ext = str.substr(last, len - last );  //확장자 추출 (. 포함)
         var ext = str.substring(last, len); //확장자 추출 (. 포함)
         ext = ext.toLowerCase(); //소문자로.

         return ext;
      }
   }; // end return
})();

// 실행부.
readyToExe.loadNextScript();