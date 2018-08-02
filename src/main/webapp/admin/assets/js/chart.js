/*function drawVisualization() { 
	
	var data = google.visualization.arrayToDataTable([
			['Month', 'Bolivia', 'Ecuador', 'Madagascar', 'Papua New Guinea', 'Rwanda', 'Average'],
			['2004/05',  165,      938,         522,             998,           450,      614.6],
			['2005/06',  135,      1120,        599,             1268,          288,      682],
			['2006/07',  157,      1167,        587,             807,           397,      623],
			['2007/08',  139,      1110,        615,             968,           215,      609.4],
			['2008/09',  136,      691,         629,             1026,          366,      569.6]
		]);
	
	var width = .4 * window.innerWidth;
	var height = .4 * window.innerHeight;
	
	var options = {
			title : 'Monthly Coffee Production by Country',
			vAxis: {title: 'Cups'},
			hAxis: {title: 'Month'}, 
			seriesType: 'bars',
			series: {5: {type: 'line'}},
			'width' : width,
			'height' : height
		};
	
	var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
	chart.draw(data, options);
}

function drawChart() {
	console.log('오나??');
    var data = google.visualization.arrayToDataTable([
      ['Task', 'Hours per Day'],
      ['Work',     11],
      ['Eat',      2],
      ['Commute',  2],
      ['Watch TV', 2],
      ['Sleep',    7]
    ]);

    var width = .8 * window.innerWidth;
    var height = .6 * window.innerHeight;
    
    console.log('width ==> ' + width);
    console.log('height ==> ' + height);
     
    var options = {
		title: '패널 사용 현황',
		pieHole: 0.3,
		width : width,
		height : height
    };

    var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
    chart.draw(data, options);
    
    
}

window.onresize = drawVisualization;
window.onresize = drawChart;
*/

/* doughnut chart */
var randomScalingFactor = function() {
	return Math.round(Math.random() * 100);
};

var config = {
	type: 'doughnut',
	data: {
		datasets: [{
			data: [
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
			],
			backgroundColor: [
				window.chartColors.red,
				window.chartColors.orange,
				window.chartColors.yellow,
				window.chartColors.green,
				window.chartColors.blue,
			],
			label: 'Dataset 1'
		}],
		labels: [
			'스크롤',
			'최근 본 상품',
			'구매 목록',
			'공유하기',
			'복사하기'
		]
	},
	options: {
		responsive: true,
		legend: {
			position: 'top',
		},
		title: {
			display: true,
			text: 'Chart.js Doughnut Chart'
		},
		animation: {
			animateScale: true,
			animateRotate: true
		}
	}
};

$(document).ready(function(){
	document.getElementById('randomizeData').addEventListener('click', function() {
		config.data.datasets.forEach(function(dataset) {
			dataset.data = dataset.data.map(function() {
				return randomScalingFactor();
			});
		});
	
		window.myDoughnut.update();
	});
	
	var colorNames = Object.keys(window.chartColors);
	document.getElementById('addDataset').addEventListener('click', function() {
		var newDataset = {
			backgroundColor: [],
			data: [],
			label: 'New dataset ' + config.data.datasets.length,
		};
	
		for (var index = 0; index < config.data.labels.length; ++index) {
			newDataset.data.push(randomScalingFactor());
	
			var colorName = colorNames[index % colorNames.length];
			var newColor = window.chartColors[colorName];
			newDataset.backgroundColor.push(newColor);
		}
	
		config.data.datasets.push(newDataset);
		window.myDoughnut.update();
	});
	
	document.getElementById('addData').addEventListener('click', function() {
		if (config.data.datasets.length > 0) {
			config.data.labels.push('data #' + config.data.labels.length);
	
			var colorName = colorNames[config.data.datasets[0].data.length % colorNames.length];
			var newColor = window.chartColors[colorName];
	
			config.data.datasets.forEach(function(dataset) {
				dataset.data.push(randomScalingFactor());
				dataset.backgroundColor.push(newColor);
			});
	
			window.myDoughnut.update();
		}
	});
	
	document.getElementById('removeDataset').addEventListener('click', function() {
		config.data.datasets.splice(0, 1);
		window.myDoughnut.update();
	});
	
	document.getElementById('removeData').addEventListener('click', function() {
		config.data.labels.splice(-1, 1); // remove the label first
	
		config.data.datasets.forEach(function(dataset) {
			dataset.data.pop();
			dataset.backgroundColor.pop();
		});
	
		window.myDoughnut.update();
	});
});
/* doughnut chart End*/

/* vertical chart */

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var data;
var barChartData;

var set_data = (function(){
	var data;
	return {
		get_data : function(){
			// 데이터 받아오기
			var test = $.ajax({
				url: '/mammoth/api/app/pCount',
				type: 'GET',
				/*data: requestData,*/
				contentType: false,
				processData: false,
				beforeSend: function(request) {
					request.setRequestHeader(header, token);
				},
				success: function(response) {
					console.log('여기는 패널 총 개수 ==> ' + response);
					console.log(response.data.totalPanelCount); // 총 패널 개수
					data = response.data;
					
					chart_functions.init(data);
					
					return ;
				},
				error: function(response, xhr, error){
					console.log(response.status + " (" + error + "): " + response.responseText );
				}
			});
		}
	}
})();

var chart_functions = (function(){
	var MONTHS = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
	var color = Chart.helpers.color;
	
	//console.log('data ==> ' + data);
	return {
		init : function(data){
			console.log('test ==> ' + data.totalPanelCount);
			console.log('test ==> ' + data.perPersonPanelCount);
			barChartData = {
				// 얘 조절하면 화면에 개수 조절됨.
				labels: ['패널 통계'],
				datasets: [{
					label: '전체 패널 개수',
					backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
					borderColor: window.chartColors.red,
					borderWidth: 1,
					data: [
						data.totalPanelCount
					]
				},{
					label: '사용자당 평균 패널 개수',
					backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
					borderColor: window.chartColors.blue,
					borderWidth: 1,
					data: [
						data.perPersonPanelCount
					]
				}]
			}
			
			
		} // end init
		//chart_data : function(){} //end chart_data
	}
})();

set_data.get_data();
/*var barChartData = {
	// 얘 조절하면 화면에 개수 조절됨.
	labels: ['패널 통계'],
	datasets: [{
		label: '전체 패널 개수',
		backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
		borderColor: window.chartColors.red,
		borderWidth: 1,
		data: [
			randomScalingFactor()
		]
	},{
		label: '사용자당 평균 패널 개수',
		backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
		borderColor: window.chartColors.blue,
		borderWidth: 1,
		data: [
			randomScalingFactor()
		]
	}, {
		label: '내 패널 개수',
		backgroundColor: color(window.chartColors.yellow).alpha(0.5).rgbString(),
		borderColor: window.chartColors.yellow,
		borderWidth: 1,
		data: [
			randomScalingFactor()
		]
	}
	]

};*/

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
				text: 'Chart.js Bar Chart'
			},
			scales: {
		        yAxes: [{
		            ticks: {
		                beginAtZero: true
		            }
		        }]
		    }
		}
	});
};

$(document).ready(function(){
	document.getElementById('randomizeData1').addEventListener('click', function() {
		var zero = Math.random() < 0.2 ? true : false;
		barChartData.datasets.forEach(function(dataset) {
			dataset.data = dataset.data.map(function() {
				return zero ? 0.0 : randomScalingFactor();
			});
	
		});
		window.myBar.update();
	});
	
	var colorNames = Object.keys(window.chartColors);
	
	document.getElementById('addDataset1').addEventListener('click', function() {
		var colorName = colorNames[barChartData.datasets.length % colorNames.length];
		var dsColor = window.chartColors[colorName];
		var newDataset = {
			label: 'Dataset ' + barChartData.datasets.length,
			backgroundColor: color(dsColor).alpha(0.5).rgbString(),
			borderColor: dsColor,
			borderWidth: 1,
			data: []
		};
	
		for (var index = 0; index < barChartData.labels.length; ++index) {
			newDataset.data.push(randomScalingFactor());
		}
	
		barChartData.datasets.push(newDataset);
		window.myBar.update();
	});
	
	document.getElementById('addData1').addEventListener('click', function() {
		if (barChartData.datasets.length > 0) {
			var month = MONTHS[barChartData.labels.length % MONTHS.length];
			barChartData.labels.push(month);
	
			for (var index = 0; index < barChartData.datasets.length; ++index) {
				// window.myBar.addData(randomScalingFactor(), index);
				barChartData.datasets[index].data.push(randomScalingFactor());
			}
	
			window.myBar.update();
		}
	});
	
	document.getElementById('removeDataset1').addEventListener('click', function() {
		barChartData.datasets.splice(0, 1);
		window.myBar.update();
	});
	
	document.getElementById('removeData1').addEventListener('click', function() {
		barChartData.labels.splice(-1, 1); // remove the label first
	
		barChartData.datasets.forEach(function(dataset) {
			dataset.data.pop();
		});
	
		window.myBar.update();
	});
});
/* vertical chart End */