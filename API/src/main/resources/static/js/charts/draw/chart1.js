Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';


// 첫번째 차트
function renderStockChartV1Area(data) {
  const ctx = document.getElementById('chart1').getContext('2d');
  myChart = new Chart(ctx,{
    type: 'line', // 차트 유형 선형 차트
    data: {
      labels: data.map(item => item.date), // 차트의 x축 라벨을 설정 (날짜)
      datasets: [{
        label: '종가',
        lineTension: 0.3,
        backgroundColor: "rgba(78, 115, 223, 0.05)", // 선 아래 영역의 배경색
        borderColor: "rgba(78, 115, 223, 1)", // 선의 색상
        pointRadius: 0.1, // 각 데이터 포인트의 반지름을 설정
        pointBackgroundColor: "rgba(78, 115, 223, 1)",
        pointBorderColor: "rgba(78, 115, 223, 1)",
        pointHoverRadius: 3, // 데이터 포인트에 마우스를 올렸을 때의 반지름
        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
        pointHitRadius: 10, // 데이터 포인트에 마우스 클릭이 가능한 범위의 반지름
        pointBorderWidth: 2,
        data: data.map(item => item.closePrice) // y축 데이터를 설정 (closePrice : 종가)
      }],
    },
    options: {
      maintainAspectRatio: false,
      layout: {
        padding: {
          left: 10,
          right: 25,
          top: 25,
          bottom: 0
        }
      },
      scales: {
        xAxes: [{
          time: {
            unit: 'date'
          },
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            maxTicksLimit: 7
          }
        }],
        yAxes: [{
          ticks: {
            maxTicksLimit: 5,
            padding: 10,
            // Include a dollar sign in the ticks
            callback: function(value, index, values) {
              return '₩' + number_format(value);
            }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }],
      },
      legend: {
        display: false
      },
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 14,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        intersect: false,
        mode: 'index',
        caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, chart) {
            const datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
            return datasetLabel + ': ₩' + number_format(tooltipItem.yLabel);
          }
        }
      }
    }
  });
}



//

// 종가 그래프를 그리는 함수
function renderCloseChart(data) {
  removeExistingChart();
  // 종가 차트 그리는 로직

  const ctx = document.getElementById('chart1').getContext('2d');
  myChart = new Chart(ctx,{
    type: 'line', // 차트 유형 선형 차트
    data: {
      labels: data.map(item => item.date), // 차트의 x축 라벨을 설정 (날짜)
      datasets: [{
        label: '종가',
        lineTension: 0.3,
        backgroundColor: "rgba(78, 115, 223, 0.05)", // 선 아래 영역의 배경색
        borderColor: "rgba(78, 115, 223, 1)", // 선의 색상
        pointRadius: 0.1, // 각 데이터 포인트의 반지름을 설정
        pointBackgroundColor: "rgba(78, 115, 223, 1)",
        pointBorderColor: "rgba(78, 115, 223, 1)",
        pointHoverRadius: 3, // 데이터 포인트에 마우스를 올렸을 때의 반지름
        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
        pointHitRadius: 10, // 데이터 포인트에 마우스 클릭이 가능한 범위의 반지름
        pointBorderWidth: 2,
        data: data.map(item => item.closePrice) // y축 데이터를 설정 (closePrice : 종가)
      }],
    },
    options: {
      maintainAspectRatio: false,
      layout: {
        padding: {
          left: 10,
          right: 25,
          top: 25,
          bottom: 0
        }
      },
      scales: {
        xAxes: [{
          time: {
            unit: 'date'
          },
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            maxTicksLimit: 7
          }
        }],
        yAxes: [{
          ticks: {
            maxTicksLimit: 5,
            padding: 10,
            // Include a dollar sign in the ticks
            callback: function(value, index, values) {
              return '₩' + number_format(value);
            }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }],
      },
      legend: {
        display: false
      },
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 14,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        intersect: false,
        mode: 'index',
        caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, chart) {
            const datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
            return datasetLabel + ': ₩' + number_format(tooltipItem.yLabel);
          }
        }
      }
    }
  });
}

// 고가 그래프를 그리는 함수
function renderHighChart(data) {
  removeExistingChart();
  // 고가 차트 그리는 로직
  const ctx = document.getElementById('chart1').getContext('2d');
  myChart = new Chart(ctx,{
    type: 'line', // 차트 유형 선형 차트
    data: {
      labels: data.map(item => item.date), // 차트의 x축 라벨을 설정 (날짜)
      datasets: [{
        label: '고가',
        lineTension: 0.3,
        backgroundColor: "rgba(78, 115, 223, 0.05)", // 선 아래 영역의 배경색
        borderColor: "rgba(78, 115, 223, 1)", // 선의 색상
        pointRadius: 0.1, // 각 데이터 포인트의 반지름을 설정
        pointBackgroundColor: "rgba(78, 115, 223, 1)",
        pointBorderColor: "rgba(78, 115, 223, 1)",
        pointHoverRadius: 3, // 데이터 포인트에 마우스를 올렸을 때의 반지름
        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
        pointHitRadius: 10, // 데이터 포인트에 마우스 클릭이 가능한 범위의 반지름
        pointBorderWidth: 2,
        data: data.map(item => item.high) // y축 데이터를 설정 (high : 고가)
      }],
    },
    options: {
      maintainAspectRatio: false,
      layout: {
        padding: {
          left: 10,
          right: 25,
          top: 25,
          bottom: 0
        }
      },
      scales: {
        xAxes: [{
          time: {
            unit: 'date'
          },
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            maxTicksLimit: 7
          }
        }],
        yAxes: [{
          ticks: {
            maxTicksLimit: 5,
            padding: 10,
            // Include a dollar sign in the ticks
            callback: function(value, index, values) {
              return '₩' + number_format(value);
            }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }],
      },
      legend: {
        display: false
      },
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 14,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        intersect: false,
        mode: 'index',
        caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, chart) {
            const datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
            return datasetLabel + ': ₩' + number_format(tooltipItem.yLabel);
          }
        }
      }
    }
  });
}

// 저가 그래프를 그리는 함수
function renderLowChart(data) {
  removeExistingChart();
  // 저가 차트
  const ctx = document.getElementById('chart1').getContext('2d');
  myChart = new Chart(ctx,{
    type: 'line', // 차트 유형 선형 차트
    data: {
      labels: data.map(item => item.date), // 차트의 x축 라벨을 설정 (날짜)
      datasets: [{
        label: '저가',
        lineTension: 0.3,
        backgroundColor: "rgba(78, 115, 223, 0.05)", // 선 아래 영역의 배경색
        borderColor: "rgba(78, 115, 223, 1)", // 선의 색상
        pointRadius: 0.1, // 각 데이터 포인트의 반지름을 설정
        pointBackgroundColor: "rgba(78, 115, 223, 1)",
        pointBorderColor: "rgba(78, 115, 223, 1)",
        pointHoverRadius: 3, // 데이터 포인트에 마우스를 올렸을 때의 반지름
        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
        pointHitRadius: 10, // 데이터 포인트에 마우스 클릭이 가능한 범위의 반지름
        pointBorderWidth: 2,
        data: data.map(item => item.low) // y축 데이터를 설정 (high : 고가)
      }],
    },
    options: {
      maintainAspectRatio: false,
      layout: {
        padding: {
          left: 10,
          right: 25,
          top: 25,
          bottom: 0
        }
      },
      scales: {
        xAxes: [{
          time: {
            unit: 'date'
          },
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            maxTicksLimit: 7
          }
        }],
        yAxes: [{
          ticks: {
            maxTicksLimit: 5,
            padding: 10,
            // Include a dollar sign in the ticks
            callback: function(value, index, values) {
              return '₩' + number_format(value);
            }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }],
      },
      legend: {
        display: false
      },
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 14,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        intersect: false,
        mode: 'index',
        caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, chart) {
            const datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
            return datasetLabel + ': ₩' + number_format(tooltipItem.yLabel);
          }
        }
      }
    }
  });
}

// 거래량 그래프를 그리는 함수
function renderVolumeChart(data) {
  removeExistingChart();
  // 고가 차트 그리는 로직
  const ctx = document.getElementById('chart1').getContext('2d');
  myChart = new Chart(ctx,{
    type: 'line', // 차트 유형 선형 차트
    data: {
      labels: data.map(item => item.date), // 차트의 x축 라벨을 설정 (날짜)
      datasets: [{
        label: '거래량',
        lineTension: 0.3,
        backgroundColor: "rgba(78, 115, 223, 0.05)", // 선 아래 영역의 배경색
        borderColor: "rgba(78, 115, 223, 1)", // 선의 색상
        pointRadius: 0.1, // 각 데이터 포인트의 반지름을 설정
        pointBackgroundColor: "rgba(78, 115, 223, 1)",
        pointBorderColor: "rgba(78, 115, 223, 1)",
        pointHoverRadius: 3, // 데이터 포인트에 마우스를 올렸을 때의 반지름
        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
        pointHitRadius: 10, // 데이터 포인트에 마우스 클릭이 가능한 범위의 반지름
        pointBorderWidth: 2,
        data: data.map(item => item.volume) // y축 데이터를 설정 (high : 고가)
      }],
    },
    options: {
      maintainAspectRatio: false,
      layout: {
        padding: {
          left: 10,
          right: 25,
          top: 25,
          bottom: 0
        }
      },
      scales: {
        xAxes: [{
          time: {
            unit: 'date'
          },
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            maxTicksLimit: 7
          }
        }],
        yAxes: [{
          ticks: {
            maxTicksLimit: 5,
            padding: 10,
            // Include a dollar sign in the ticks
            callback: function(value, index, values) {
              return number_format(value);
            }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }],
      },
      legend: {
        display: false
      },
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 14,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        intersect: false,
        mode: 'index',
        caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, chart) {
            const datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
            return datasetLabel +  number_format(tooltipItem.yLabel);
          }
        }
      }
    }
  });
}

// 이동 평균선 차트 그리기
function renderMovingAverageChart(data) {
  removeExistingChart()
  const ctx = document.getElementById('chart1').getContext('2d');

  // 차트 생성
  myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: data.map(item => item.date), // 날짜 라벨
      datasets: [
        // 5일 이동 평균선
        {
          pointRadius: 0.1,
          label: '5일 이동 평균선',
          data: calculateMovingAverage(data, 5),
          borderColor: 'blue',
          borderWidth: 0.4,
          fill: false
        },
        // 20일 이동 평균선
        {
          pointRadius: 0.1,
          label: '20일 이동 평균선',
          data: calculateMovingAverage(data, 20),
          borderColor: 'green',
          borderWidth: 0.4,
          fill: false
        },
        // 60일 이동 평균선
        {
          pointRadius: 0.1,
          label: '60일 이동 평균선',
          data: calculateMovingAverage(data, 60),
          borderColor: 'red',
          borderWidth: 0.4,
          fill: false
        },
        // 120일 이동 평균선
        {
          pointRadius: 0.1,
          label: '120일 이동 평균선',
          data: calculateMovingAverage(data, 120),
          borderColor: 'purple',
          borderWidth: 0.4,
          fill: false
        }
      ]
    },
    options: {
      // responsive: true, // 차트 반응형 설정
      maintainAspectRatio: false, // 가로세로 비율 고정 해제
      layout: {
        padding: {
          left: 10,
          right: 25,
          top: 25,
          bottom: 0
        }
      },
  scales: {
        xAxes: [{
          time: {
            unit: 'date'
          },
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            maxTicksLimit: 7
          }
        }],
        yAxes: [{
          ticks: {
            maxTicksLimit: 5,
            padding: 10,
            // Include a dollar sign in the ticks
            callback: function(value, index, values) {
              return number_format(value);
            }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }]
      }
    }
  });

  return chart;
}


// 이동 평균선을 계산하는 함수
function calculateMovingAverage(data, dayCount) {
  let movingAverageData = [];

  for (let i = 0; i < data.length; i++) {
    if (i < dayCount - 1) {
      movingAverageData.push(null); // 아직 평균을 계산할 수 없는 데이터는 null 처리
    } else {
      let sum = 0;
      for (let j = 0; j < dayCount; j++) {
        sum += data[i - j].closePrice; // 지정된 일수만큼의 종가 합산
      }
      movingAverageData.push(sum / dayCount); // 평균값 계산 후 배열에 추가
    }
  }

  return movingAverageData;
}


// 기존 차트 삭제
function removeExistingChart() {
  if (myChart) {
    myChart.destroy();
  }
}

function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
      prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
      sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
      dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
      s = '',
      toFixedFix = function(n, prec) {
        var k = Math.pow(10, prec);
        return '' + Math.round(n * k) / k;
      };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}
