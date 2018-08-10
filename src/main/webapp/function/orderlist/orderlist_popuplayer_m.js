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
					format.push('<a class="info-tags info-title"' + 
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
			if(action === 'open') {
				$('.popupLayer').show();
			} 
			else if(action === 'close') { $('.popupLayer').hide(); } 
			else { return 0 };
		}
	};
}($Palette));

$Palette(document).ready(function() {
	var dataId = $Palette('.popupLayer').parent().attr('id');
	// mobile li 버튼 클릭 시 실행
	$Palette('.zoom-menu li[data-id='+dataId+']').click(function() {
		$Palette.orderlist.frontApi();
	});
	
	
	/* popup X button event */
	$Palette('#popup-close > span').click(function() {
		$Palette.orderlist.popup('close');
	});
	/* popup checkbox click event */
	$Palette('#ckbox').click(function() {
		$Palette.orderlist.checkedParse($Palette(this).prop('checked'));
	});

	/* period button click event */
	$Palette('.span-period').click(function(){
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
