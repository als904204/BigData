/**
 * 요청온 종목 차트 그리는 js
 * @param companyName 종목 이름 (삼성,lg,현대....)
 */
let myChart;
function fetchStockDataAndRenderLineChart(companyName){
  $.ajax({
    url: `/api/v1/stock/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      console.log("success to load data for", `/api/v1/stock/${companyName}`);
      $('#stock-name').text(companyName)

      renderStockChartV1Area(data);

      $('#closeButton').click(function() {
        renderCloseChart(data);
      });
      $('#highButton').click(function() {
        renderHighChart(data);
      });
      $('#lowButton').click(function() {
        renderLowChart(data);
      });
      $('#volumeButton').click(function() {
        renderVolumeChart(data);
      });
      $('#movingButton').click(function() {
        renderMovingAverageChart(data);
      });

      // renderCandlestickChart(data);

    },
    error: function (error){
      console.error('Error fetching stock data:', error);
    }
  })
}

function fetchStockStatisticsAndRenderPieChart(companyName) {
  $.ajax({
    url: `/api/v1/stock/statistics/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      console.log("Pie chart data loaded for", companyName);
      renderStockChartV3Pie(data);
      updateStockStatistics(data);
    },
    error: function (error) {
      console.error('Error fetching pie chart data:', error);
    }
  });
}

// 한달치 Stock 캔들 차트 호출
function fetchRecentStockData(companyName){
  $.ajax({
    url: `/api/v1/stock/recent/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      console.log("recent stock datas loaded");
      renderCandlestickChart(data);
    },
    error: function (error) {
      console.error('Error fetching recent stock datas:', error);
    }
  })
}


