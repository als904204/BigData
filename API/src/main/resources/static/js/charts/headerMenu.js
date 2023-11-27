
function fetchStockDateRangeData(companyName){
  $.ajax({
    url: `/api/v1/stock/date-range/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      console.log("success to load data for ", companyName);
      $('#firstDate').text(data.firstDate);
      $('#lastDate').text(data.lastDate);
      $('#nav-stock-name').text(companyName.toUpperCase());
    },
    error: function (error){
      console.error('Error fetching stock data:', error);
    }
  })
}

function fetchStockDataForYesterday(companyName){
  $.ajax({
    url: `/api/v1/stock/yesterday/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {

      // 현재 값 빼오기
      const currentClose = data[0].closePrice;
      const currentAvgPrice = data[0].averagePrice;
      const currentHigh = data[0].high;
      const currentLow  = data[0].low;
      const currentVolume  = data[0].volume;

      // 전 값 빼오기
      const prevClose = data[1].closePrice;
      const prevAvgPrice = data[1].averagePrice;
      const prevHigh = data[1].high;
      const prevLow  = data[1].low;
      const prevVolume  = data[1].volume;

      // 이전 주식 차이 값
      const closeDiff = currentClose-prevClose;
      const avgDiff = currentAvgPrice-prevAvgPrice;
      const highDiff = currentHigh-prevHigh;
      const lowDiff = currentLow-prevLow;
      const volumeDiff = currentVolume-prevVolume;

      // 이전 주식과 백분율 차이 계산 후 2번째 자리 까지만
      const calClosePercentage = (closeDiff / currentClose) * 100;
      const calAvgPercentage = (avgDiff / currentAvgPrice) * 100;
      const calHighPercentage = (highDiff / currentHigh) * 100;
      const calLowPercentage = (lowDiff / currentLow) * 100;
      const calVolumePercentage = (volumeDiff / currentVolume) * 100;

      const closePercentage = calClosePercentage.toFixed(2);
      const cvgPercentage = calAvgPercentage.toFixed(2);
      const HighPercentage = calHighPercentage.toFixed(2);
      const LowPercentage = calLowPercentage.toFixed(2);
      const VolumePercentage = calVolumePercentage.toFixed(2);

      // 가독성을 위해 숫자 포맷팅 적용
      $('#currentClose').text(formatNumber(currentClose));
      $('#currentAvgPrice').text(formatNumber(currentAvgPrice));
      $('#currentHigh').text(formatNumber(currentHigh));
      $('#currentLow').text(formatNumber(currentLow));
      $('#currentVolume').text(formatNumber(currentVolume));

      $('#prevClose').text(formatNumber(prevClose));
      $('#prevAvgPrice').text(formatNumber(prevAvgPrice));
      $('#prevHigh').text(formatNumber(prevHigh));
      $('#prevLow').text(formatNumber(prevLow));
      $('#prevVolume').text(formatNumber(prevVolume));

      $('#closePercentage').text(" | " + closePercentage + "%");
      $('#avgPercentage').text(" | " + cvgPercentage + "%");
      $('#highPercentage').text(" | " + HighPercentage + "%");
      $('#lowPercentage').text(" | " + LowPercentage + "%");
      $('#volumePercentage').text(" | " + VolumePercentage + "%");


    },
    error: function (error){
      console.error('Error fetching stock data:', error);
    }
  })
}

const formatNumber = (number) => {
  return number.toLocaleString();
};

