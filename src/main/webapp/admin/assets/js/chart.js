/* vertical chart */
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var data;
var barChartData, config; // chart들의 설정값이 들어가는 변수들

var set_data = (function(){
	var data;
	return {
		// 패널 통계 가져오는 ajax
		get_panel_data : function(){
			// 데이터 받아오기
			$.ajax({
				url: '/mammoth/api/app/pCount',
				type: 'GET',
				contentType: false,
				processData: false,
				beforeSend: function(request) {
					request.setRequestHeader(header, token);
				},
				success: function(response) {
					chart_functions.bar_chart_init(response.data);
					
					return ;
				},
				error: function(response, xhr, error){
					console.log(response.status + " (" + error + "): " + response.responseText );
				}
			});
		},// 기능 통계 가져오는 ajax
		get_function_data : function(){
			// 데이터 받아오기
			$.ajax({
				url: '/mammoth/api/app/fCount',
				type: 'GET',
				/*data: requestData,*/
				contentType: false,
				processData: false,
				beforeSend: function(request) {
					request.setRequestHeader(header, token);
				},
				success: function(response) {
					chart_functions.doughnut_chart_init(response.data);
					
					return ;
				},
				error: function(response, xhr, error){
					console.log(response.status + " (" + error + "): " + response.responseText );
				}
			});
		}, // end get_function_data
		// 각 기능 개수를 리턴해주는 함수
		get_function_count : function(data, type){
			var function_count_array = [];
			for(idx in data){
				function_count_array.push(data[idx].functionCount);
			}
			return function_count_array;
		}, // end get_function_id
		// 각 기능의 한글이름을 리턴해주는 함수
		get_function_name : function(data){
			var function_name_array = [];
			for(idx in data){
				function_name_array.push(data[idx].functionName);
			}
			return function_name_array;
		}
	}
})();

var chart_functions = (function(){
	var color = Chart.helpers.color;
	
	return {
		bar_chart_init : function(data){
			barChartData = {
				// 얘 조절하면 화면에 개수 조절됨.
				labels: ['패널 통계'],
				datasets: [{
					label: '전체 패널 개수',
					backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
					borderColor: window.chartColors.red,
					borderWidth: 1,
					data: [
						data.totalPanelCount // 전체 패널 개수
					]
				},{
					label: '사용자당 평균 패널 개수',
					backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
					borderColor: window.chartColors.blue,
					borderWidth: 1,
					data: [
						data.perPersonPanelCount // 한 사람당 평균 개수
					]
				}]
			}
		}, // end bar_chart_init
		doughnut_chart_init : function(data){
			config = {
					type: 'doughnut',
					data: {
						datasets: [{
							data: set_data.get_function_count(data), // 각 기능의 개수
							backgroundColor: [ // 차트 색깔 정해주는 곳
								window.chartColors.red,
								window.chartColors.orange,
								window.chartColors.yellow,
								window.chartColors.green,
								window.chartColors.blue,
							],
							label: 'Dataset 1'
						}],
						labels: set_data.get_function_name(data) // 기능 이름 (한글)
					},
					options: {
						responsive: true,
						legend: {
							position: 'top',
						},
						title: {
							display: true,
							text: '각 기능별 사용 현황'
						},
						animation: {
							animateScale: true,
							animateRotate: true
						}
					}
				};
		} // end doughnut_chart_init
	}
})();

// window.onload는 2개 이상 쓰게되면 가장 마지막 load를 인식하기 때문에 차트 추가할 때 주의할 필요가 있음!
window.onload = function() {
	/* doughnut chart */
	var ctx = document.getElementById('chart-area').getContext('2d');
	window.myDoughnut = new Chart(ctx, config);
	
	/* vertical chart */
	var ctx1 = document.getElementById('canvas').getContext('2d');
	window.myBar = new Chart(ctx1, {
		type: 'bar',
		data: barChartData,
		options: {
			responsive: true,
			legend: {
				position: 'top',
			},
			title: {
				display: true,
				text: '패널 개수 현황'
			},
			scales: { // scales 옵션으로 vertical 형 chart의 시작점을 0으로 정해줌.
		        yAxes: [{
		            ticks: {
		                beginAtZero: true
		            }
		        }]
		    }
		}
	});
};
/* vertical chart End */

// 실행부
set_data.get_panel_data(); // 패널 통계
set_data.get_function_data();  // 도넛(기능) 통계