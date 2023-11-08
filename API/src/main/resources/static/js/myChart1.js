// / Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';


var chartArea = {
  labels : [],
  dataSets : [],
  render : function() {
    new Chart($("#myChart1"), {
      type: 'line',
      data: {
        labels: chartArea.labels,
        datasets: [{
          label: "매출액",
          lineTension: 0.3,
          backgroundColor: "rgba(2,117,216,0.2)",
          borderColor: "rgba(2,117,216,1)",
          pointRadius: 5,
          pointBackgroundColor: "rgba(2,117,216,1)",
          pointBorderColor: "rgba(255,255,255,0.8)",
          pointHoverRadius: 5,
          pointHoverBackgroundColor: "rgba(2,117,216,1)",
          pointHitRadius: 50,
          pointBorderWidth: 2,
          data: chartArea.dataSets,
        }],
      },
      options: {
        responsive : true,
        scales: {
          xAxes: [{
            time: {
              unit: 'date'
            },
            gridLines: {
              display: false
            },
            ticks: {
              maxTicksLimit: 7
            }
          }],
          yAxes: [{
            /*          y값에 따라 최소, 최대치 표시를 자동으로 설정하기 위해 주석처리
                          ticks: {
                          min: 0,
                          max: 40000,
                          maxTicksLimit: 5
                        },*/
            gridLines: {
              color: "rgba(0, 0, 0, .125)",
            }
          }],
        },
        legend: {
          display: false
        }
      }
    });
  },
  showData : function(){
    labels = [];
    dataSets= [];
    $.ajax({
      type : 'GET',
      url : 'monthly-sales/2023',
      contentType: 'application/json',
      //dataType 정의
      dataType: 'json',
      //요청결과가 성공일 경우
      success : function(data) {
        //console.log(data);
        $.each(data, function(index,obj){
          chartArea.labels.push(obj.date);
          chartArea.dataSets.push(obj.sales);
        });
        chartArea.render();
      },
      //요청결과가 실패일 경우
      error : function(xhr, status, error){
      }
    });
  }
};

chartArea.showData();