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
				"sImgSrc" : "localhost:8080/mammoth/function/recent/images/recent_product1.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/린넨-suit-자켓/101470/display/2/",
				"sParam" : "?product_no=101470&cate_no=39&display_group=2"
			},
			"1" : {
				"iProductNo" : "98781",
				"sProductName" : "두번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/function/recent/images/recent_product2.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/cb-리얼플러스-어깨패드/98781/display/2/",
				"sParam" : "?product_no=98781&cate_no=52&display_group=2"
			},
			"2" : {
				"iProductNo" : "101479",
				"sProductName" : "세번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/function/recent/images/recent_product3.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/시원한-린넨-7부-셔츠/101479/display/2/",
				"sParam" : "?product_no=101479&cate_no=2825&display_group=2"
			}
		};
	
		sessionStorage.setItem("localRecentProduct1", JSON.stringify(tmpTestData));
	//=========================================================================================
	
	$.recent = {	
		defaults : {
			//isPreview : true,
			interval : 5000, 
			preview : {
				// 미리보기 레이아웃 폭
				// layoutWidth : 300,
				
				// 미리보기 이미지 높이
				imgHeight : "200px" 
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
			
			// 최근 본 상품 목록 그리기.
			this.render(opts);	
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
		
		render : function(opts){
			var jsonData = this.getJson();
			var resultDatas = {};
			
			var list = [];
			for(i in jsonData){
				var format = {};
				var iProductNo = jsonData[i].iProductNo;
				var sProductName = jsonData[i].sProductName;
				var link = jsonData[i].link_product_detail;
				var imgSrc = "http://"+jsonData[i].sImgSrc;
				
				format['iProductNo'] = iProductNo;
				format['sProductName'] = sProductName;
				format['link'] = link;
				format['imgSrc'] = imgSrc;
				if(i==0) { format['first'] = true; }
				list.push(format);
			}
			resultDatas['datas'] = list;
			
			// mustache render
			$.recent.mustache(resultDatas);
			
			// carousel start
			$('.carousel').carousel({ interval: opts.interval });
			
		},
		mustache : function(data) {
			var template = $('#recent-mustache-template').html();
			Mustache.parse(template);
			var rendered = Mustache.render(template, data);
			$('.carousel-inner').html('').append(rendered);
		}
	};
	
})(jQuery);
$(document).ready(function() {   
	$.recent.init({ 
		interval : false,   // 슬라이드 자동 넘기기 속도
		preview : {
			layoutBorderRadius : 0,   // 미리보기 레이아웃 border-radius 정도 (px)
			imgHeight : 200,    // 미리보기 사진 높이 (px)
		}
	});
	$('a[data-slide="prev"]').click(function() {
	  $('#myCarousel').carousel('prev');
	});

	$('a[data-slide="next"]').click(function() {
	  $('#myCarousel').carousel('next');
	});
});