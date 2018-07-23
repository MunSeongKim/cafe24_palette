/**
 * 전역 함수의 캡슐화.
 * 중복되는 네임스페이스를 방지하기 위해 사용. recentProduct이라는 네임스페이스 지정.
 */

(function($){
	
	// For Test!===============================================================================
		var tmpTestData = {
			"0" : {
				"iProductNo" : "101470",
				"sProductName" : "첫번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/assets/admin/function/recent/images/recent_product1.gif",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/린넨-suit-자켓/101470/display/2/",
				"sParam" : "?product_no=101470&cate_no=39&display_group=2"
			},
			"1" : {
				"iProductNo" : "98781",
				"sProductName" : "두번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/assets/admin/function/recent/images/recent_product2.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/cb-리얼플러스-어깨패드/98781/display/2/",
				"sParam" : "?product_no=98781&cate_no=52&display_group=2"
			},
			"2" : {
				"iProductNo" : "101479",
				"sProductName" : "세번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/assets/admin/function/recent/images/recent_product3.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/시원한-린넨-7부-셔츠/101479/display/2/",
				"sParam" : "?product_no=101479&cate_no=2825&display_group=2"
			}
		}; 
	
		sessionStorage.setItem("localRecentProduct1", JSON.stringify(tmpTestData));
	//=========================================================================================
	
	$.recent = {	
		defaults : {
			isPreview : true,
			interval : 5000, 
			preview : {
				// 미리보기 레이아웃 폭
				layoutWidth : 300,
				
				// 미리보기 이미지 높이
				imgHeight : "inherit" 
			},
			layoutBorderRadius : 20
		},
		
		// init
		init : function(options){
			// JQuery.extend(target, [object1], [objectN]) returns : Object
			// 두 개 이상의 객체를 합치기 위한 함수.
			// 같은 이름의 프로퍼티가 있을 때, 덮어 쓴다. object1에 objectN을 덮어쓴다.
			// true : 깊은 복사
			// target : 합쳐진 객체를 받을 객체.
			var opts = $.extend(true, {}, $.recent.defaults, options);
			
			// 화면 전환 속도 ms
			$('.carousel').carousel({ interval: opts.interval });
			
			// 최근 본 상품 목록 그리기.
			this.render(opts);
			
			if(options.isPreview == true){ 
				this.preview(opts);
			}
		},
		
		// SessionStorate에 있는 값을 JSON으로 리턴.
		getJson : function(){
			var jsonStr = sessionStorage.getItem("localRecentProduct1");
			var json = JSON.parse(jsonStr);
			return json;
		},
		 
		// SessionStorate에 있는 값을 Array로 리턴.
		getArray : function(){
			var array = [];
			var jsonData = this.getJson();
			for(i in jsonData){
				array.push(jsonData[i]);
			}
			return array;
		},
		
		// 입력한 iProductNo와 일치하는 array 부분 객체 리턴.
		getOneArray : function(array, iProductNo){
			for(i in array){
				if(array[i].iProductNo == iProductNo){
					return array[i];
				}
			}
		},
		
		// carousel-inner class를 사용하는 div에 recent list 정보를 바탕으로 항목 리스트를 추가한다.
		// 뮨제 : 부트스트랩에 종속 됨.
		render : function(){
			var jsonData = this.getJson();
			var datas = {};
			var imgs = [];
			
			for(i in jsonData){
				var iProductNo = jsonData[i].iProductNo;
				var link = jsonData[i].link_product_detail;
				var imgSrc = "http://"+jsonData[i].sImgSrc;
				 
				imgs.push({'iProductNo' : iProductNo, 'link' : link , 'imgSrc' : imgSrc}); 
			}
			
			datas['imgs'] = imgs;
			
			//parse
			var template = $("#recent-template").html();
			Mustache.parse(template); 
			
			var rendered = Mustache.render(template, datas);
			console.log(rendered); 
			$(".carousel-inner").append(rendered);
			
			
			$(".recent_count").text(Object.keys(jsonData).length);
			$(".carousel-inner div.carousel-item:first").addClass("active");
		},
		
		// 미리보기 레아이웃 준비
		preview : function(options){
			var array = $.recent.getArray();
			
			$(".carousel-inner").mouseenter(function(evnet){
				var iProductNo = event.target.getAttribute("data-iProductNo");
				var currentData = $.recent.getOneArray(array, iProductNo);
				 
				$(".recent_preview_img").attr({
					"src" : "http://"+currentData.sImgSrc,
					"height" : options.preview.imgHeight+"px",
				}).css({ 
					"border-top-left-radius" : options.preview.layoutBorderRadius+"px", 
					"border-top-right-radius" : options.preview.layoutBorderRadius+"px"
				});
				
				$(".recent_preview_title").text(currentData.sProductName);
				
				$(".recent-link-btn").css({
					"border-radius" : options.preview.layoutBorderRadius+"px"
				})
				
				//console.log(preViewOptions); 값이 넘어감.
				$.recent.previewRender(options);
			});
			
			$('.carousel-inner').mouseleave(function(event) {
				$('.recent_preview_layout').css("display", "none"); 
			})
			
			$('.recent_preview_layout').mouseleave(function(event){
				$('.recent_preview_layout').css("display", "none");
			});
		},
		
		// 미리보기 화면 출력 및 위치 결정
		previewRender : function(options){
			var pre = $(".recent_preview_layout");
			x = event.pageX;  // 마우스 포인터의 x위치
			y = event.pageY;  // 마우스 포인터의 y위치
			var sWidth = window.innerWidth;
			var sHeight = window.innerHeight;
			var oWidth = pre.width();
			var oHeight = pre.height();
			
			//레이어가 나타날 위치를 셋팅한다.
			var divLeft = event.clientX + 10;
			var divTop = event.clientY + 15; 
			 
			//레이어가 화면 크기를 벗어나면 위치를 바꾸어 배치한다.
			if( divLeft + oWidth > sWidth ) divLeft -= oWidth;
			if( divTop + oHeight > sHeight ) divTop -= oHeight;
			
			// 레이어 위치를 바꾸었더니 상단기준점(0,0) 밖으로 벗어난다면 상단기준점(0,0)에 배치하자.
			if( divLeft < 0 ) divLeft = 0;
			if( divTop < 0 ) divTop = 0;
			
			// preview의 속성을 변경한다.
			pre.css({
				"width" : options.preview.layoutWidth+"px",
				"border-radius" : options.preview.layoutBorderRadius+"px",
				"position" : "fixed",
				"display" : "",
				"left" : divLeft,
				"top" : divTop,
				"z-index" : "1000"
			})
		}
	};
	
})(jQuery);