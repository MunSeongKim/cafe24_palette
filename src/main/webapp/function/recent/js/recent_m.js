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
			var resultDatas = {}; //mustache에 넘겨줄 데이터 객체
			var list = []; //resultDatas에 추가해줄 리스트 객체
			
			// 최근 본 상품 목록 갯수
			$(".recent-count").text(Object.keys(jsonData).length);
			/* front api 가상 데이터 */
			var productDatas = tmpProductData;
			var optDatas = tmpOptData;
			var hitDatas = tmpHitData;
			
			for(i in jsonData){
				var format = {};
				var iProductNo = jsonData[i].iProductNo;
				var link = 'http://'+window.location.hostname+'/product/detail.html?product_no='+iProductNo;
				var imgSrc = "http://"+jsonData[i].sImgSrc;
				
				/* 실제 front api 사용 부분*/
				/*$.recent.frontApi(iProductNo);*/
				
				/* session에서 가져오는 정보 */
				format['iProductNo'] = iProductNo;
				
				format['link'] = link;
				format['imgSrc'] = imgSrc;
				
				/* 가상 데이터 사용 부분 : 수정 요망! */
				/* api에서 가져오는 정보 */
				format['productName'] = productDatas.product.product_name;	// product name
				format['count'] = hitDatas.count;	// product hit
				format['opts'] = optDatas.options.option; // product options
				// description
				var simpleDescription = productDatas.product.simple_description;
				var summaryDescription = productDatas.product.summary_description;
				if(simpleDescription != '') {		
					format['summary'] = simpleDescription;
				} else if(summaryDescription != '') {
					format['summary'] = summaryDescription;
				} else {
					format['summary'] = '간략 설명이 없습니다.';
				}
				
				if(i==0) { format['first'] = true; }
				list.push(format);
			}
			resultDatas['datas'] = list;
			resultDatas['cut'] = function(){ return function(t,render){ return render(t).replace(/,/gi,'<br>'); }};
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
	};
	
})(jQuery);
$(document).ready(function() {   
	var dataId = $('.recent-main-div').parent().attr('id');
	
	$('.zoom-menu li[data-id='+dataId+']').click(function() {
		$.recent.init({ 
			interval : false,   // 슬라이드 자동 넘기기 속도
			preview : {
				layoutBorderRadius : 0,   // 미리보기 레이아웃 border-radius 정도 (px)
				imgHeight : 200,    // 미리보기 사진 높이 (px)
			}
		});
		
		// 최근 본 상품 링크
		$('.recent-row-title a').attr('href', window.location.hostname+'/product/recent_view_product.html');
		
		$('a[data-slide="prev"]').click(function() {
		  $('#myCarousel').carousel('prev');
		});

		$('a[data-slide="next"]').click(function() {
		  $('#myCarousel').carousel('next');
		});
	})
	
});