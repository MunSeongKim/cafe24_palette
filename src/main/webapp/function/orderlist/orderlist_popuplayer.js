(function($) {
	$.orderlist = {
		formattedNow : function() {
			var d = new Date(Date.now());
			var curr_date = d.getDate();
			var curr_month = d.getMonth() + 1;
			var curr_year = d.getFullYear();
			
			if (curr_month < 10) { curr_month = '0' + curr_month; }
		    if (curr_date < 10) { curr_date = '0' + curr_date; }
		    
		    return [curr_year, curr_month, curr_date].join('-');
		},
		formattedAgo : function(options) {
			var selectDate = this.formattedNow().split('-');
			var changeDate = new Date();
			
			var opts = $.extend(true, {}, $.orderlist.defaults, options);
			
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
		},
		popup : function(action) {
			if(action === 'open') {
				$('.popupLayer').css({
					"position" : "fixed",
					"right" : $('#panel').width(),
					"top" : '20%'
				}).show();
			} else if(action === 'close') {
				$('.popupLayer').hide();
			} else {
				return 0;
			}
		}
	};
}(jQuery));

function closeLayer() {
	$.orderlist.popup('close');
}

/* 구매목록 버튼 클릭시 클릭한 위치 근처에 레이어가 나타난다. */
$(document).ready(function() {
	$('.func-orderlist').click(function() {
		var end_date = $.orderlist.formattedNow();
		var start_date = $.orderlist.formattedAgo();		
		
		// var member_id = $('#crema-login-username').children('.xans-member-var-id').text();
		var member_id = 'kimdudtj';
		
		var datas = {
				'start_date' : start_date,
				'end_date' : end_date,
				'member_id' : member_id
			};
		
		var orderIds = [];
		var orderDates = [];
		var items = [];
		
		var exampleData = [
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
						"additionalDiscountPrice": "1000.00",
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
						"additionalDiscountPrice": "2000.00",
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
		var datas = {};
		datas['datas'] = exampleData;
		
		var template = $('#mustache-template').html();
		Mustache.parse(template);
		
		var rendered = Mustache.render(template, datas);
		console.log(rendered);
		$('#mustache-result').append(rendered);
		
		$.orderlist.popup('open');
		/*
		$.ajax({
			url:'https://devbit005.cafe24.com/mammoth/api/cafe24/orders',
			type: 'get',
			contentType: 'application/json',
			dataType: 'json',
			data: datas,
			success: function(response) {
				console.log(response.data);
				datas['datas'] = response.data;
				
				var template = $('#mustache-template').html();
				Mustache.parse(template);
				
				var rendered = Mustache.render(template, datas);
				console.log(rendered);
				$('#mustache-result').html(rendered);
				
				$.each(response.data, function(index, data) {
					
					$.each(data, function(name, value) {
						
						if(name=='orderId') { orderIds.push(value)}
						else if(name == 'orderDate') { orderDates.push(value) }
						else { items.push(value)}
					});
				});
				console.log(orderIds);
				console.log(orderDates);
				console.log(items);
				
				
			}
		});//ajax
*/		
		
		
	});
});
