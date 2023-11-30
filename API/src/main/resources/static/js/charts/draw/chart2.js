function renderCandlestickChart(stockData) {
  // 데이터 변환
  const candlestickData = stockData.map(item => {
    return {
      x: new Date(item.date), // 날짜
      y: [item.open, item.high, item.low, item.closePrice] // 시가, 고가, 저가, 종가
    };
  });

  // 차트 옵션 설정
  const options = {
    series: [{
      data: candlestickData
    }],
    chart: {
      type: 'candlestick',
      height: 350
    },
    xaxis: {
      type: 'datetime'
    },
    yaxis: {
      tooltip: {
        enabled: true
      }
    }
  };

  // 차트 생성
  const chart = new ApexCharts(document.querySelector("#chart2"), options);
  chart.render();
}
