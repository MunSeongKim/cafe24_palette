(function($) {
	$.formattedDate = {
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
			
			var opts = $.extend(true, {}, $.formattedDate.defaults, options);
			
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
	}
	$.fn.popup = function(action) {
		if(action === 'open') {
			this.css({
				"top" : '20%',
				"right" : $('#panel').width(),
				"position" : "fixed"
			}).show();
		} else if(action === 'close') {
			this.hide();
		} else {
			return 0;
		}
	}
}(jQuery));

function closeLayer() {
	$('.popupLayer').popup('close');
}

/* 클릭시 클릭한 위치 근처에 레이어가 나타난다. */
$(document).ready(function() {
	$('.func-orderlist').click(function(e) {
		var end_date = $.formattedDate.now();
		var start_date = $.formattedDate.ago();		
		
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
		
		$('.popupLayer').popup('open');
		
		/*$.ajax({
			url:'https://devbit005.cafe24.com/mammoth/api/cafe24/orders',
			type: 'get',
			contentType: 'application/json',
			dataType: 'json',
			data: datas,
			success: function(response) {
				$.each(response.data, function(index, data) {
					$.each(data, function(name, value) {
						if(name=='orderId') { orderIds.push(value)}
						else if(name == 'orderDate') { orderDates.push(value) }
						else { items.push(value)}
					});
				});
				console.log(orderIds);
				console.log(orderDates);
				console.log(items)
			}
		});//
*/		

		
	});
});
