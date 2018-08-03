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
		execute : function(options) {
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
					formatList = {
							'date' : function(){ return function(t,render){ return render(t).substr(0,10)+' '+render(t).substr(11,8);}}
							,'price' : function(){ return function(t,render){ return render(t).split('.')[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");}}
							,'options' : function(){ return function(t,render){ return render(t).replace(/,/gi,'<br>'); }}
							,'tags' : function(){ return function(t,render){
								var list = render(t).split(',');
								var format= [];
								for(var i in list) { 
									format.push('<a class="info-tags info-title"' + 
											' href="http://stylenanda.com/product/search.html?keyword='
											+list[i]+'"> #'
											+list[i]+'</a>'); 
								}
								return format.toString().replace(/,/gi, ' ');
								}
							}
					};
					
					resultDatas['cut'] = formatList;
					$.orderlist.originData = resultDatas;
					console.log(resultDatas)
					$.orderlist.mustache(resultDatas);
					
					$.orderlist.popup('open');
				}
			});//ajax
		},
		defaults : {
			'mall_url' : 'kimdudtj.cafe24.com',
			'start_date' : $.format.ago(),
			'end_date' : $.format.now(),
			'member_id' : 'kimdudtj' //$('#crema-login-username').children('.xans-member-var-id').text()
		},
		originData : {},
		checkedParse : function(checked) {
			var cate_no = '38';
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
							items.pop(items[itemKey]);
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
			var template = $('#mustache-template').html();
			Mustache.parse(template);
			var rendered = Mustache.render(template, data);
			$('#mustache-result').html('').append(rendered);
		},
		popup : function(action) {
			$.panel.makePopupCss($('.popupLayer'), action);
		}
	};
}(jQuery));

$(document).ready(function() {
	/* popup X button event */
	$('#popup-close > span').click(function() {
		$.orderlist.popup('close');
	});
	
	/* ORDER LIST button click event */
	$('.func-orderlist').click(function() {
		$.orderlist.execute();
	});
	
	/* popup checkbox click event */
	$('#ckbox').click(function() {
		$.orderlist.checkedParse($(this).prop('checked'));
	});

	/* period button click event */
	$('.span-period').click(function(){
		$.orderlist.execute( {'start_date' : getStartDate($(this).data('period'))} );
		
		$(this).addClass('plt-pn-btn-default').removeClass('plt-pn-btn-simple');
		$('.span-period').not($(this)).removeClass('plt-pn-btn-default').addClass('plt-pn-btn-simple');
		$('#ckbox').prop('checked', false);
	});

});

/* start_date setting */
function getStartDate(period) {
	if(period == 'week') {
		return $.format.ago();
	} else if(period == 'month') {
		return $.format.ago( {'flag':'month', 'period':'1'} );
	} else {
		return $.format.ago( {'flag':'month', 'period':'6'} );
	}
};
