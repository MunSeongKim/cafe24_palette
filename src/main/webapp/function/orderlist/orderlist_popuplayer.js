(function($) {
	$.format = {
		now : function() {
			var d = new Date(Date.now());
			var curr_date = d.getDate();
			var curr_month = d.getMonth() + 1;
			var curr_year = d.getFullYear();
			
			if (curr_month < 10) { curr_month = '0' + curr_month; }
		    if (curr_date < 10) { curr_date = '0' + curr_date; }
		    
		    return [curr_year, curr_month, curr_date].join('-');
		}, 
		ago : function(options) {
			var selectDate = this.now().split('-');
			var changeDate = new Date();
			
			var opts = $.extend(true, {}, $.format.defaults, options);
			
			if(opts.flag.toLowerCase() === 'day') {
				changeDate.setFullYear(selectDate[0], selectDate[1], selectDate[2]-Number(opts.period));
			} else if(opts.flag.toLowerCase() === 'month') {
				changeDate.setFullYear(selectDate[0], selectDate[1]-Number(opts.period), selectDate[2]);
			} else {
				return 0;
			}
			
			var y = changeDate.getFullYear();
			var m = changeDate.getMonth();
			var d = changeDate.getDate();
			if (m < 10) { m="0"+m; }
			if (d < 10) { d="0"+d; }
			
			var resultDate = y + "-" + m + "-" + d;
			return resultDate;
		},
		defaults : {
			flag: 'day',
			period: 7
		}
		
	};
	
	$.orderlist = {
		preview : function() {
			var tmpDatas = [
			    {
			        "orderId": "20180712-0000045",
			        "orderDate": "2018-07-12T16:29:51+09:00",
			        "items": [
			            {
			                "itemNo": "4",
			                "productNo": "9",
			                "productName": "샘플상품 1",
			                "productPrice": "5000.00",
			                "optionValue": "색상=레드, 사이즈=S",
			                "quantity": "2",
			                "additionalDiscountPrice": "10000.00",
			                "products": {
			                    "productNo": "9",
			                    "smallImage": null,
			                    "categories": [
			                        {
			                            "category_no": "29",
			                            "recommend": "F",
			                            "new": "F"
			                        }
			                    ]
			                }
			            },
			            {
			                "itemNo": "5",
			                "productNo": "10",
			                "productName": "샘플상품 2",
			                "productPrice": "10000.00",
			                "optionValue": "",
			                "quantity": "2",
			                "additionalDiscountPrice": "20000.00",
			                "products": {
			                    "productNo": "10",
			                    "smallImage": null,
			                    "categories": [
			                        {
			                            "category_no": "33",
			                            "recommend": "F",
			                            "new": "F"
			                        },
			                        {
			                            "category_no": "38",
			                            "recommend": "F",
			                            "new": "F"
			                        },
			                        {
			                            "category_no": "34",
			                            "recommend": "F",
			                            "new": "F"
			                        },
			                        {
			                            "category_no": "29",
			                            "recommend": "F",
			                            "new": "F"
			                        }
			                    ]
			                }
			            }
			        ]
			    },
			    {
			        "orderId": "20180712-0000033",
			        "orderDate": "2018-07-12T16:17:29+09:00",
			        "items": [
			            {
			                "itemNo": "3",
			                "productNo": "9",
			                "productName": "샘플상품 1",
			                "productPrice": "5000.00",
			                "optionValue": "색상=레드, 사이즈=L",
			                "quantity": "1",
			                "additionalDiscountPrice": "500.00",
			                "products": {
			                    "productNo": "9",
			                    "smallImage": null,
			                    "categories": [
			                        {
			                            "category_no": "29",
			                            "recommend": "F",
			                            "new": "F"
			                        }
			                    ]
			                }
			            }
			        ]
			    }
			];
			var resultDatas = {};
			resultDatas['datas'] = tmpDatas;
			resultDatas['cut'] = $.orderlist.formatList;
			$.orderlist.mustache(resultDatas);
			$.orderlist.popup('open'); 
		},
		defaults : {
			'start_date' : $.format.ago(),
			'end_date' : $.format.now(),
			'member_id' : ''
		},
		originData : {},
		formatList : {
			'date' : function(){ return function(t,render){ return render(t).substr(0,10)+' '+render(t).substr(11,8);}}
			,'price' : function(){ return function(t,render){ return render(t).split('.')[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");}}
			,'options' : function(){ return function(t,render){ return render(t).replace(/,/gi,'<br>'); }}
			,'tags' : function(){ return function(t,render){
				var list = render(t).split(',');
				var format= [];
				for(var i in list) { 
					format.push('<a class="info-tags info-title orderlist-click"' + 
							' href="/product/search.html?keyword='
							+list[i]+'"> #'
							+list[i]+'</a>'); 
				}
				return format.toString().replace(/,/gi, ' ');
				}
			}
		},
		frontApi : function() {
			CAFE24API.init('D0OdNNlzFdfWprppcum7NG');
			
			// front api : get member_id (현재 로그인한 고객 ID)
			CAFE24API.get('/api/v2/customers/id', function(err, res) {
				var member_id = res.id.member_id;
				if(member_id == null) {
					console.log('member id null');
					$.orderlist.mustache({'datas' : []});
					$.orderlist.popup('open');
					return;
				}
				var opts = $.extend(true, {}, $.orderlist.defaults, {'member_id': member_id});
				$.orderlist.defaults = opts;
				$.orderlist.execute();
			});
		},
		execute : function(options) {
			if($.orderlist.defaults.member_id == '') { return; }
			var opts = $.extend(true, {}, $.orderlist.defaults, options);
			$.orderlist.defaults = opts;
			
			var resultDatas = {};
			var formatList = {};
			
			$.ajax({
				url:'https://devbit005.cafe24.com/mammoth/api/cafe24/orders',
				type: 'get',
				contentType: 'application/json',
				dataType: 'json',
				data: opts,
				success: function(response) {
					resultDatas['datas'] = response.data;
					formatList = $.orderlist.formatList;
					
					resultDatas['cut'] = formatList;
					$.orderlist.originData = resultDatas;
					console.log(resultDatas)
					$.orderlist.mustache(resultDatas);
					
					$.orderlist.popup('open');
				}
			});//ajax
		},
		checkedParse : function(checked) {
			if($.orderlist.defaults.member_id == '') { return; }
			var search= window.location.search;
			var cate_no = '';
			if(search != '') {
				search = location.search.substring(1);
				var objSearch = JSON.parse('{"' + decodeURI(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g,'":"') + '"}');
				cate_no = objSearch.cate_no;
			} else {
				var pathname = location.pathname.split("/");
				cate_no = pathname[pathname.indexOf('category')+1];
			}
			if(cate_no == '' || cate_no == null || cate_no=='1') {
				cate_no = null;
			}
			
			var obj = $.extend(true, {}, $.orderlist.originData);
			var orders = obj['datas'];
			var tmp = {};
			tmp['cut'] = obj['cut'];
			tmp['datas'] = [];
			if(checked == true) {
				for(var orderKey in orders){
					var items = orders[orderKey].items;
					for(var itemKey in items){
						var categories = items[itemKey].product.categories;
						var categoryNoList = []; 
						Array.from(categories, x => categoryNoList.push(x['category_no']))
						if( $.inArray(cate_no, categoryNoList) == -1 ){
							items[itemKey]={};
						}
					}
					if($.type(items) != 'undefined') {
						tmp['datas'].push(orders[orderKey]);
					}
				} 
				$.orderlist.mustache(tmp);
				return;
			}
			$.orderlist.mustache($.orderlist.originData);
		},
		mustache : function(data) {
			var template = $('#orderlist-mustache-template').html();
			var noLoginTemplate = $('#orderlist-nologin-mustache-template').html();
			var rendered = '';
			if($.orderlist.defaults.member_id == '') {
				Mustache.parse(noLoginTemplate);
				rendered = Mustache.render(noLoginTemplate, data);
			} else {
				Mustache.parse(template);
				rendered = Mustache.render(template, data);
			}
			
			$('#orderlist-mustache-result').html('').append(rendered);
		},
		popup : function(action) {
			$.panel.makePopupCss($('.popupLayer.orderlist'), action);
		}
	};
}($Palette));

$Palette(document).ready(function() {
	/* popup X button event */
	$Palette('#popup-close > span').click(function() {
		$Palette.orderlist.popup('close');
	});
	
	/* ORDER LIST button click event */
	$Palette('.func-orderlist').click(function() {
		if($Palette('#panel').hasClass('preview')) {
			$Palette.orderlist.preview();
            
			$Palette('.info-detail-link').on('click', function(event) {
                alert('상품 상세 페이지로 이동합니다.'); 
				event.preventDefault();
			});
			$Palette('.info-tags').on('click', function(event) {
                alert('상품 태그를 검색합니다.'); 
				event.preventDefault();
            });
            
		} else {
			/* writer: deo
			 * orderlist 최초 실행 시,
			 * $.orderlist.execute() 실행하지 않고
			 * frontApi()를 실행하여 $.orderlist.defaults에 member_id 값을 세팅해준다.
			 * frontApi() 내부에서 execute() 가 실행된다.
			 * */
			$Palette.orderlist.frontApi();
		}
	});
	
	/* popup checkbox click event */
	$Palette('#ckbox').click(function() {
		if(!$Palette('#panel').hasClass('preview')) {
			$Palette.orderlist.checkedParse($Palette(this).prop('checked'));
		}
	});

	/* period button click event */
	$Palette('.span-period').click(function(){
        if($Palette('#panel').hasClass('preview')) {
            alert('기간에 해당하는 주문목록이 출력됩니다.');
			return;
		}
        
        /* writer: deo
         * 최초 실행이 아닌 경우, $.orderlist.defaults에 member_id 기본값이 세팅되어 있으므로,
         * $.orderlist.frontApi를 실행하지 않고
         * 날짜 데이터만 전달해주는 형태로 $.orderlist.execute를 실행한다.*/
        $Palette.orderlist.execute( {'start_date' : getStartDate($Palette(this).data('period'))} );
		
        $Palette(this).addClass('plt-pn-btn-default').removeClass('plt-pn-btn-simple');
        $Palette('.span-period').not($Palette(this)).removeClass('plt-pn-btn-default').addClass('plt-pn-btn-simple');
        $Palette('#ckbox').prop('checked', false);
	});

});

/* start_date setting */
function getStartDate(period) {
	if(period == 'week') {
		return $Palette.format.ago();
	} else if(period == 'month') {
		return $Palette.format.ago( {'flag':'month', 'period':'1'} );
	} else {
		return $Palette.format.ago( {'flag':'month', 'period':'6'} );
	}
};
