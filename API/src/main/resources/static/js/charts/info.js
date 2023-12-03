// 예측값 가져오기
function fetchStockPredicted(companyName){
  $.ajax({
    url: `/api/v1/stock/predicted/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      console.log("predicted : ",data);
      $('#predicted').text(data.predicted.toFixed(2));
      $('#predicted-date').text(data.predictedDate);
    },
    error: function (error){
      console.error('Error fetching stock predicted:', error);
    }
  })
}

// 해당 종목 뉴스 기사 크롤링
function fetchStockCrawling(companyName){
  $.ajax({
    url: `/api/v1/stock/crawling/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      console.log("crawling : ",data);
    },
    error: function (error){
      console.error('Error fetching stock predicted:', error);
    }
  })
}
