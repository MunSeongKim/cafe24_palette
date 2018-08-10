/**
 * 전역 함수의 캡슐화.
 * 중복되는 네임스페이스를 방지하기 위해 사용. recentProduct이라는 네임스페이스 지정.
 */

(function($){
	
	var apiDatas = {};
	var resultDatas = {}; //mustache에 넘겨줄 데이터 객체
	var list = []; //resultDatas에 추가해줄 리스트 객체
	var opts = {};
	var sessionData = sessionStorage.getItem("localRecentProduct1");
	CAFE24API.init('D0OdNNlzFdfWprppcum7NG'); //App Key
	
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
			opts = $.extend(true, {}, $.recent.defaults, options);
			
			$('.carousel').carousel({ interval: opts.interval });
			
			// 최근 본 상품이 없을 때.
			if(sessionData == null || sessionData == "[]"){
				$(".recent-row-card .carousel-inner").append($("<img/>", {
					src : "https://devbit005.cafe24.com/mammoth/function/recent/no_recent.png",
					css : {
						width : "100%",
						height : "100%"
					}
				}));
				return;
			}
			
			// 최근 본 상품 목록 그리기.
			this.renderPreprocess();
		},
		
		renderPreprocess : function(){
			var jsonData = JSON.parse(sessionData);
			
			// 최근 본 상품 목록 갯수
			$(".recent-count").text(Object.keys(jsonData).length);
			
			/* front api 가상 데이터 */
			this.frontApi(jsonData);
		},
		
		// Front API 데이터 사용
		frontApi : function(jsonData) {
			jsonData = Object.values(jsonData); // json array를 Array로 변환.
			var count = 0;
			
			// 비동기 함수를 위한 callback 처리
			jsonData.forEach(function(data, index){
				$.recent.load(data, function(productInfo){
					CAFE24API.get('/api/v2/products/'+data.iProductNo+'/hits/count', function(err, res){
						data['apiDatas'] = productInfo;
						data['count'] = res.count;
						$.recent.setMustacheData(count, data);
						count++;
						if(count == jsonData.length){
							$.recent.mustache();
						}
					});
				});
			});
		},
		
		load : function(data, callback){
			CAFE24API.get('/api/v2/products/'+data.iProductNo+'?embed=options&fields=product_name, options, simple_description, summary_description, detail_image', function(err, res){
				callback(res);
			});
		},
		
		setMustacheData : function(index, jsonOneData){
			var format = {};
			var iProductNo = jsonOneData.iProductNo;
			
			var link = '/product/detail.html?product_no='+iProductNo;
			
			/* session에서 가져오는 정보 */
			format['iProductNo'] = iProductNo;
			format['link'] = link;
			
			/* api에서 가져오는 정보 */
			format['productName'] = jsonOneData.apiDatas.product.product_name;	// product name
			format['count'] = jsonOneData.count;	// product hit
			format['opts'] = jsonOneData.apiDatas.product.options.option; // product options
			format['detailImage'] = jsonOneData.apiDatas.product.detail_image;
			
			// description
			var simpleDescription = jsonOneData.apiDatas.product.simple_description;
			var summaryDescription = jsonOneData.apiDatas.product.summary_description;
			
			if(simpleDescription != '') {		
				format['summary'] = simpleDescription;
			} else if(summaryDescription != '') {
				format['summary'] = summaryDescription;
			} else {
				format['summary'] = '간략 설명이 없습니다.';
			}
			
			// 맨 첫 carousel-item에 active를 붙이기 위한 구문.
			if(index==0) { format['first'] = true; }
			list.push(format);
			
			resultDatas['datas'] = list;
			resultDatas['cut'] = function(){ return function(t,render){ return render(t).replace(/,/gi,'<br>'); }};
		},
		
		mustache : function() {
			var template = $('#recent-mustache-template').html();
			Mustache.parse(template);
			var rendered = Mustache.render(template, resultDatas);
			$('.carousel-inner').html('').append(rendered);
			
			$(".card-img").on('click', function(){
				location.href = $(this).data('link');
			});
		},
	};
	
})($Palette);

$(document).ready(function() {
	var dataId = $('.recent-main-div').parent().attr('id');
	
	$('.zoom-menu li[data-id='+dataId+']').click(function() {
		$Palette.recent.init({ 
			interval : false,   // 슬라이드 자동 넘기기 속도
			preview : {
				layoutBorderRadius : 0,   // 미리보기 레이아웃 border-radius 정도 (px)
				imgHeight : 200,    // 미리보기 사진 높이 (px)
			}
		});
		
		// 최근 본 상품 링크
		$('.recent-row-title a').attr('href', '/myshop/recent_list.html');
		
		$('a[data-slide="prev"]').click(function() {
			$Palette('#myCarousel').carousel('prev');
		});

		$('a[data-slide="next"]').click(function() {
			$Palette('#myCarousel').carousel('next');
		});
	})
	
});