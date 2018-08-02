/**
 * 전역 함수의 캡슐화.
 * 중복되는 네임스페이스를 방지하기 위해 사용. recentProduct이라는 네임스페이스 지정.
 */

(function($){
	
	// 예시 데이터
	// For Test!===============================================================================
		var tmpTestData = {
			"0" : {
				"iProductNo" : "101470",
				"sProductName" : "첫번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/function/recent/recent_product1.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/린넨-suit-자켓/101470/display/2/",
				"sParam" : "?product_no=101470&cate_no=39&display_group=2"
			},
			"1" : {
				"iProductNo" : "98781",
				"sProductName" : "두번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/function/recent/recent_product2.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/cb-리얼플러스-어깨패드/98781/display/2/",
				"sParam" : "?product_no=98781&cate_no=52&display_group=2"
			},
			"2" : {
				"iProductNo" : "101479",
				"sProductName" : "세번째 상품",
				"sImgSrc" : "localhost:8080/mammoth/function/recent/recent_product3.jpg",
				"isAdultProduct" : "F",
				"link_product_detail" : "/product/시원한-린넨-7부-셔츠/101479/display/2/",
				"sParam" : "?product_no=101479&cate_no=2825&display_group=2"
			}
		};
		
		var tmpProductData = {
				  "product": {
					    "shop_no": 1,
					    "product_no": 11,
					    "category": [
					      {
					        "category_no": 24,
					        "recommend": "F",
					        "new": "F"
					      }
					    ],
					    "product_code": "P000000K",
					    "custom_product_code": "",
					    "product_name": "test1",
					    "eng_product_name": "",
					    "model_name": "",
					    "price": "1.00",
					    "retail_price": "0.00",
					    "display": "T",
					    "description": "test",
					    "mobile_description": "test",
					    "additional_image": null,
					    "payment_info": "<p><br></p>",
					    "shipping_info": "<p><br></p>",
					    "exchange_info": "<p><br></p>",
					    "service_info": "<p><br></p>",
					    "product_tax_type_text": null,
					    "set_product_type": null,
					    "selling": "T",
					    "simple_description": "",  // 간략
					    "summary_description": "", // 요약
					    "product_tag": "",
					    "price_content": null,
					    "buy_unit": 1,
					    "minimum_quantity": 1,
					    "maximum_quantity": 0,
					    "mileage_amount": null,
					    "adult_certification": "F",
					    "detail_image": "http://qyuee.cafe24.com/web/product/big/201806/11_shop1_15294678265704.png",
					    "list_image": "http://qyuee.cafe24.com/web/product/medium/201806/11_shop1_15294678265704.png",
					    "tiny_image": "http://qyuee.cafe24.com/web/product/tiny/201806/11_shop1_15294678265704.png",
					    "small_image": "http://qyuee.cafe24.com/web/product/small/201806/11_shop1_15294678265704.png",
					    "has_option": "F",
					    "option_type": null,
					    "manufacturer_code": "M0000000",
					    "trend_code": "T0000000",
					    "brand_code": "B0000000",
					    "made_date": "",
					    "expiration_date": null,
					    "origin_classification": "F",
					    "origin_place_no": 1798,
					    "origin_place_value": "",
					    "icon_show_period": null,
					    "icon": null,
					    "product_material": "",
					    "shipping_method": "",
					    "prepaid_shipping_fee": "",
					    "shipping_period": {
					      "minimum": 0,
					      "maximum": 0
					    },
					    "shipping_scope": "A",
					    "shipping_area": "",
					    "shipping_fee_type": "",
					    "shipping_rates": [],
					    "clearance_category_code": null,
					    "origin_place_code": 1798,
					    "list_icon": {
					      "soldout_icon": false,
					      "recommend_icon": true,
					      "new_icon": true
					    },
					    "additional_information": null,
					    "main": [
					      2,
					      3
					    ],
					    "relational_product": null,
					    "select_one_by_option": "F",
					    "approve_status": "",
					    "edibot": "F",
					    "sold_out": "F"
					  }
					};
		
		var tmpOptData = {
				  "options": {
					    "shop_no": 1,
					    "product_no": 11,
					    "has_option": "T",
					    "option_type": "T",
					    "option_list_type": "S",
					    "option": [
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
					      },
					      {
					        "option_code": "",
					        "option_name": "색상",
					        "option_value": [
					          {
					            "option_image_file": "",
					            "option_color": "#c20f0a",
					            "option_text": "red",
					            "value_no": null
					          },
					          {
					            "option_image_file": "",
					            "option_color": "#000000",
					            "option_text": "black",
					            "value_no": null
					          },
					          {
					            "option_image_file": "",
					            "option_color": "#ffffff",
					            "option_text": "white",
					            "value_no": null
					          },
					          {
					            "option_image_file": "",
					            "option_color": "#0000fd",
					            "option_text": "blue",
					            "value_no": null
					          }
					        ],
					        "required_option": "T",
					        "option_display_type": "S"
					      }
					    ],
					    "select_one_by_option": "F",
					    "use_additional_option": "F",
					    "additional_option": [],
					    "use_attached_file_option": "F",
					    "attached_file_option": []
					  }
					};
		var tmpHitData = { count : 6 };
	 
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
			$('#recentBox .carousel').carousel({ interval: opts.interval });
						
			// 최근 본 상품 목록 그리기.
			this.render(opts);
			this.preview(opts);
			
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
		render : function(){
			var jsonData = this.getJson();
			for(i in jsonData){
				var iProductNo = jsonData[i].iProductNo;
				var link = jsonData[i].link_product_detail;
				var imgSrc = "http://"+jsonData[i].sImgSrc;
				var imgTag = '<img src="'+imgSrc+'" data-iProductNo="'+iProductNo+'" style="width: 100%;">';
				var carouselItem = '<div class="carousel-item" style="height: 120px;">'+imgTag+'</div>';
				$("#recentBox .carousel-inner").append(carouselItem);
				
			}
			
			$(".recent_count").text(Object.keys(jsonData).length);
			$("#recentBox .carousel-inner div.carousel-item:first").addClass("active");
		},
		
		frontApi : function(productNo) {
			CAFE24API.init('D0OdNNlzFdfWprppcum7NG'); //App Key

			CAFE24API.get('/api/v2/products/'+productNo, function(err, res){
			    console.log(res);
			});
			CAFE24API.get('/api/v2/products/'+productNo+'/options', function(err, res){
			    console.log(res);
			});
			CAFE24API.get('/api/v2/products/'+productNo+'/hits/count', function(err, res){
			    console.log(res);
			});
		},
		// 미리보기 레아이웃 준비
		preview : function(options){
			var array = $.recent.getArray();
			
			$("#recentBox .carousel-inner").click(function(event){
				var iProductNo = event.target.getAttribute("data-iProductNo");
				var sessionData = $.recent.getOneArray(array, iProductNo);
				
				/* 실제 front api 사용 부분*/
				/*$.recent.frontApi(iProductNo);*/
				/* 가상 데이터 사용 부분 */
				var productDatas = tmpProductData;
				var optDatas = tmpOptData;
				var hitDatas = tmpHitData;
				
				var productName = productDatas.product.product_name;
				var simpleDescription = productDatas.product.simple_description;
				var summaryDescription = productDatas.product.summary_description;
				var opts = optDatas.options.option;
				var hit = hitDatas.count;
				console.log(opts);
				
				// product image
				$(".recent-preview-img").attr({
					"src" : "http://"+sessionData.sImgSrc,
					"height" : options.preview.imgHeight+"px",
				}).css({ 
					"border-top-left-radius" : options.preview.layoutBorderRadius+"px", 
					"border-top-right-radius" : options.preview.layoutBorderRadius+"px"
				});
				
				// product hit
				$('.recent-preview-hit').append(hit);
				
				// product name
				$(".recent-preview-title").append(productName);
				
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
				$('.recent-preview-options').append(content);
				
				// product detail info
				if(simpleDescription != '') {
					$('.recent-preview-content').append(simpleDescription);
				} else if(summaryDescription != '') {
					$('.recent-preview-content').append(summaryDescription);
				} else {
					$('.recent-preview-content').append('간략 설명이 없습니다.');
				}
				
				
				// product link
				$(".recent-link-btn").attr({
					'href': 'http://'+window.location.hostname+'/product/detail.html?product_no='+iProductNo
				}).css({
					"border-radius" : options.preview.layoutBorderRadius+"px"
				})
				
				
				$.recent.previewRender(options);
			});
			
			/* popup X button event */
			$('#recent_preview_layout_close > span').click(function() {
				$.recent.popup('close');
			});
			
		},
		
		// 미리보기 화면 출력 및 위치 결정
		previewRender : function(options){		
			// preview의 속성을 변경한다.
			$.recent.popup('open');
		},
		popup : function(action) {
			if(action === 'open') {
				$(".recent-preview-layout").css({
					"position" : "fixed",
					"right" : $('#panel').width(),
					"top" : '20%'
				}).show();
				$('#recentBox .carousel').carousel('pause');
			} else {
				$('.recent-preview-layout').hide();
				$('#recentBox .carousel').carousel({ interval: true });
			}
			
		}
	};
	
})(jQuery);