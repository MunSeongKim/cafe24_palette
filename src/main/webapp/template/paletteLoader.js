
var readyToExe = (function() {
	var isMobile = false;
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
	   'https://devbit005.cafe24.com/mammoth/assets/js/cafe24Cookie.js',
	   'https://devbit005.cafe24.com/mammoth/static/jquery/1.11.1/jquery.min.js',
	   'https://devbit005.cafe24.com/mammoth/static/jquery-ui/1.12.1/jquery-ui.min.js',
	   'https://devbit005.cafe24.com/mammoth/static/popper.js/1.14.1/umd/popper.min.js',
	   'https://devbit005.cafe24.com/mammoth/assets/js/bootstrap.min.js',
	   'https://devbit005.cafe24.com/mammoth/static/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js',
	   'https://devbit005.cafe24.com/mammoth/static/mustachejs/2.2.1/mustache.min.js'
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
	
	      //readyToExe.scriptLoaded(element);
	      // readyToExe.scriptLoaded(element);
	      element.onreadystatechange = function() {
	         if (this.readyState == 'complete'){// || this.readyState == 'loaded') {
	            readyToExe.scriptLoaded(element);
	         }
	      }
	      //element.onreadystatechange = readyToExe.handler(element);
	      element.onload = readyToExe.scriptLoaded;
	      
	      head.appendChild(element);
	   } return {
	      // public area
	      // includeFile function
	      includeFile : function(path, type) {
	         createElement(path, type);
	      }, // end include File function
	
	      includeTargetElement : function() {
	         var element1 = document.createElement("div");
	         element1.setAttribute("id", "panel-area");
	         document.body.appendChild(element1);
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
	        	 
	        	 // 나중에 풀어서 적용할 꺼
	        	 // files 안에 있는 파일 개수 -1개 입력
	        	 // jQuery UI 로드 문제 $, jQuery 모두 오버라이드 해줘야 해결된다.
	            if(files.length == 4) {
	            	$ = jQuery.noConflict(true);
	            	jQuery = $;
	            }
	        	 element.onreadystatechange = element.onload = null; // kill memory leak in IE
	            // done = true; //원래 여기에 true있는데 패턴 변경한 순간... 로직이 바뀐듯 나중에 확인해보자.
	            if (files.length != 0) {
	            	
	               if (files.length == 1) {
	                  //createElement div
	                  //readyToExe.includeTargetElement();
	               }
	               readyToExe.loadNextScript(files);
	            } else {
	            	var url = window.location.hostname;//$('meta[property="og:url"]').attr('content');
	            	
	            	if(isMobile == false){
	            		readyToExe.includeTargetElement();
	            		$('#panel-area').load('https://devbit005.cafe24.com/mammoth/palette/' + url);
	            	}
	            	else{
	            		readyToExe.includeTargetElement();
	            		$('#panel-area').load('https://devbit005.cafe24.com/mammoth/palette/mobile/' + url);
	            	}
	            }
	         }
	      }, // end scriptLoaded
	      extractExt : function(str) {
	         var len = str.length;
	         var last = str.lastIndexOf(".");
	
	         if (last == -1) { //. 를 발견하지 못한다면.
	            return false; //확장자가 없음.
	         }
	
	         var ext = str.substring(last, len); //확장자 추출 (. 포함)
	         ext = ext.toLowerCase(); //소문자로.
	
	         return ext;
	      }, // device detection
	      chkMobile : function(){
	    	  // 모바일에서 pc버전할 때 문제가되서 디바이스 체크는 제외시킴.
	    	  if(/*/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) 
	    			  || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))
	    		|| */location.hostname.match(/^m\./i)	  
	    	  ) { 
	    		  isMobile = true; // when a user is using mobile
	    	  }
	      }
	  }; // end return
})();


window.addEventListener('load', function(){
	readyToExe.chkMobile();
	readyToExe.loadNextScript();	
});

