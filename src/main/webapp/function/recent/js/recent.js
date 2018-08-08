/**
 * 전역 함수의 캡슐화.
 * 중복되는 네임스페이스를 방지하기 위해 사용. recentProduct이라는 네임스페이스 지정.
 */

var _host = "devbit005.cafe24.com";
var _protocol = "https";

(function($){
	 
	// 예시 데이터
	// For Test!===============================================================================
		var tmpTestData = {
			"0" : {
				"iProductNo" : "101470",
				"sProductName" : "첫번째 상품",
				"sImgSrc" : _host+"/mammoth/function/recent/recent_product1.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/린넨-suit-자켓/101470/display/2/",
				"sParam" : "?product_no=101470&cate_no=39&display_group=2"
			},
			"1" : {
				"iProductNo" : "98781",
				"sProductName" : "두번째 상품",
				"sImgSrc" : _host+"/mammoth/function/recent/recent_product2.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/cb-리얼플러스-어깨패드/98781/display/2/",
				"sParam" : "?product_no=98781&cate_no=52&display_group=2"
			},
			"2" : {
				"iProductNo" : "101479",
				"sProductName" : "세번째 상품",
				"sImgSrc" : _host+"/mammoth/function/recent/recent_product3.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/시원한-린넨-7부-셔츠/101479/display/2/",
				"sParam" : "?product_no=101479&cate_no=2825&display_group=2"
			}
		};
		
		var tmpApiDatas = {
				  "product_name": "샘플상품 2",
				  "simple_description": "",
				  "summary_description": "",
				  "options": {
				    "option": [
				      {
				        "option_code": "",
				        "option_name": "색상",
				        "option_value": [
				          {
				            "option_image_file": "",
				            "option_color": "#000000",
				            "option_text": "블랙",
				            "value_no": null
				          },
				          {
				            "option_image_file": "",
				            "option_color": "#ff5a00",
				            "option_text": "오렌지",
				            "value_no": null
				          },
				          {
				            "option_image_file": "",
				            "option_color": "#ffffff",
				            "option_text": "화이트",
				            "value_no": null
				          }
				        ],
				        "required_option": "T",
				        "option_display_type": "S"
				      },
				      {
				        "option_code": "",
				        "option_name": "사이즈",
				        "option_value": [
				          {
				            "option_image_file": "",
				            "option_color": "",
				            "option_text": "S",
				            "value_no": null
				          },
				          {
				            "option_image_file": "",
				            "option_color": "",
				            "option_text": "M",
				            "value_no": null
				          },
				          {
				            "option_image_file": "",
				            "option_color": "",
				            "option_text": "L",
				            "value_no": null
				          }
				        ],
				        "required_option": "T",
				        "option_display_type": "S"
				      }
				    ]
				  }
				}
		
		
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
			$('#recentBox .carousel').carousel({ interval: opts.interval });
						
			// 최근 본 상품 목록 그리기.
			this.render(opts);
			
			if(opts.isPreview == true){
				this.preview(opts);
			}
		},
		
		// SessionStorate에 있는 값을 JSON으로 리턴.
		getJson : function(){
			var jsonStr = null;
			var json = null;
			
			if($("#panel").hasClass("preview")){
				json = tmpTestData;
			}else{
				jsonStr = sessionStorage.getItem("localRecentProduct1");
                json = JSON.parse(jsonStr);
			}
			
			return json;
		},
		 
		// SessionStorage에 있는 값을 Array로 리턴.
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
		render : function(){
			var jsonData = null;
			
			if($("#panel").hasClass("preview")){
				
				jsonData = tmpTestData;
				
				$('.recent-product-title').click(function(evt){
					evt.preventDefault();
					alert("[쇼핑몰의 최근 본 상품 목록으로 이동 할 것 입니다.]");
				});
				
				$('.recent-link-btn').click(function(evt){
					evt.preventDefault();
					alert("[쇼핑몰의 최근 본 상품 목록으로 이동 할 것 입니다.]");
				});
			} else {
				jsonData = this.getJson();
			}
			
			for(i in jsonData){
				var iProductNo = jsonData[i].iProductNo;
				var link = jsonData[i].link_product_detail;
				var imgSrc = _protocol+"://"+jsonData[i].sImgSrc;
				var imgTag = '<img src="'+imgSrc+'" data-iProductNo="'+iProductNo+'" style="width: 100%;">';
				var carouselItem = '<div class="carousel-item" style="height: 120px;">'+imgTag+'</div>';
				$("#recentBox .carousel-inner").append(carouselItem);
			}
			
			$(".recent_count").text(Object.keys(jsonData).length);
			$("#recentBox .carousel-inner div.carousel-item:first").addClass("active");
		},

		// Front API 데이터 사용
		frontApi : function(iProductNo, sessionData, options) {
			CAFE24API.init('D0OdNNlzFdfWprppcum7NG'); //App Key

			var productDatas = null;
			var optDatas = null;
			var hitDatas = null;
			
			// 옵션 정보 및 상품 정보 가져오기
			CAFE24API.get('/api/v2/products/'+iProductNo+'?embed=options&fields=product_name, options, simple_description, summary_description', function(err, res){
				
				var apiDatas = res;
				
				console.log("test - 1");
				console.log(apiDatas);
				
				CAFE24API.get('/api/v2/products/'+iProductNo+'/hits/count', function(err, res){
					
					apiDatas['count'] = res.count;
					
					console.log("test - 2");
					console.log(apiDatas);
					
					$.recent.setDetailData(iProductNo, sessionData, apiDatas);
					$.recent.previewRender(options);
				});
			});
		},
		
		
		
		// 미리보기 레아이웃 준비
		preview : function(options){
			var array = $.recent.getArray();
			
			$("#recentBox .carousel-inner").click(function(event){
				var iProductNo = event.target.getAttribute("data-iProductNo");
				var sessionData = $.recent.getOneArray(array, iProductNo);
				var productDatas = tmpProductData;
				var optDatas = tmpOptData;
				var hitDatas = tmpHitData;
				
				/* 가상 데이터 사용 부분 */
				if($("#panel").hasClass("preview")){
					$.recent.setDetailData(iProductNo, sessionData, tmpApiDatas)
					$.recent.previewRender(options);
				}
				/* 실제 front api 사용 부분*/
				else{
					$.recent.frontApi(iProductNo, sessionData, options);
				}
			});
			
			/* popup X button event */
			$('#recent_preview_layout_close > span').click(function() {
				$.recent.popup('close');
			});
		},
		
		// 가상 데이터 혹은 Front Api 데이터를 통해 화면을 render하는 함수
		setDetailData : function (iProductNo, sessionData, apiDatas){
			var productName = apiDatas.product_name;
			var simpleDescription = apiDatas.simple_description;
			var summaryDescription = apiDatas.summary_description;
			var opts = apiDatas.option;
			var hit = apiDatas.count;
			
			// product image
			$(".recent-preview-img").attr({
				"src" : _protocol+"://"+sessionData.sImgSrc,
				"height" : options.preview.imgHeight+"px",
			}).css({ 
				"border-top-left-radius" : options.preview.layoutBorderRadius+"px", 
				"border-top-right-radius" : options.preview.layoutBorderRadius+"px"
			});
			
			// product hit
			$('.recent-preview-hit').html(hit);
			
			// product name
			$(".recent-preview-title").html(productName);
			
			// product options
			var content = '';
			for(var i in opts) {
				content += opts[i].option_name;
				content += '<br>[ ';
				for(var j in opts[i].option_value) {
					var color = opts[i].option_value[j].option_color;
					if(color ==='#ffffff') {
						color += ';background-color: #000000';
					}
					content += '<span style="color:'+color+'; font-weight: bold;">'
					content += opts[i].option_value[j].option_text + '</span> ';
				}
				
				content += ']<br>';
			}
			$('.recent-preview-options').html(content);
			
			// product detail info
			if(simpleDescription != '') {
				$('.recent-preview-content').html(simpleDescription);
			} else if(summaryDescription != '') {
				$('.recent-preview-content').html(summaryDescription);
			} else {
				$('.recent-preview-content').html('간략 설명이 없습니다.');
			}
			
			// product link
			$(".recent-link-btn").attr({
				'href': _protocol+'://'+window.location.hostname+'/product/detail.html?product_no='+iProductNo
			}).css({
				"border-radius" : options.preview.layoutBorderRadius+"px"
			})
		},
		
		// 미리보기 화면 출력 및 위치 결정
		previewRender : function(options){		
			// preview의 속성을 변경한다.
			$.recent.popup('open');
		},
		
		popup : function(action) {
			$.panel.makePopupCss($(".recent-preview-layout"), action);
			
			if(action === 'open') {
				$('#recentBox .carousel').carousel('pause');
			} else if(action === 'close') {
				$('#recentBox .carousel').carousel({ interval: true });
			}
		}
	};
	
})(jQuery);

$(document).ready(function() {   
	$.recent.init({ 
		isPreview : true,  // 미리보기 기능 사용 여부
		interval : 3000,   // 슬라이드 자동 넘기기 속도
		preview : {
			layoutWidth : 200,  // 미리보기 레이아웃 크기 (px)
			layoutBorderRadius : 0,   // 미리보기 레이아웃 border-radius 정도 (px)
			imgHeight : 200,    // 미리보기 사진 높이 (px)
		}
	});
	
	// 최근 본 상품 링크
	$('.recent-product-title').attr('href', window.location.hostname+'/product/recent_view_product.html');
});
