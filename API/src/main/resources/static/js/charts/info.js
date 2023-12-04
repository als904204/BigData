// 예측값 가져오기
function fetchStockPredicted(companyName){
  $.ajax({
    url: `/api/v1/stock/predicted/${companyName}`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      console.log("predicted : ",data);
      $('#predicted').text(formatNumber(data.predicted.toFixed(1)));
      $('#predicted-date').text(data.predictedDate+")");
      $('#predicted-date-previous').text(data.lastStockDate);

      $('#predicted-start').text("AI를 이용한 다음 예측 값은(");
      $('#predicted-end').text("원 입니다");
      const diff = data.difference.toFixed(2);
      const percent = data.percentageChange.toFixed(2);

      // 예측가가 마이너스라면
      if (!isPositive(diff)) {
        $('#predicted-guess').html(`전날 대비 <span class="text-primary">${diff} 원이 (${percent}%) <strong>감소</strong></span> 될 것으로 예측됩니다`);
      } else {
        $('#predicted-guess').html(`전날 대비 <span class="text-danger">${diff} 원이 (${percent}%) <strong>증가</strong></span> 될 것으로 예측됩니다`);
      }


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
      updateNewsList(data) // 데이터를 사용하여 뉴스 리스트를 업데이트
    },
    error: function (error){
      console.error('Error fetching stock predicted:', error);
    }
  })
}

function updateNewsList(newsData) {
  const newsList = $('#news-list');
  newsList.empty(); // 기존 목록을 비웁니다

  newsData.forEach((news, index) => {
    const newsItem = `
      <li class="pl-3 mb-1 font-weight-bold news-title">
        <a href="${news.link}" target="_blank" style="color: inherit; text-decoration: none;">${news.title}</a>
      </li>
       <li class="pl-3 mb-1">
        <a href="${news.link}" target="_blank" style="color: inherit; text-decoration: none;">${news.content}</a>
      </li>
      <li class="pl-3 mb-1">
        <small>${news.source}</small>
        <small class="opacity-50"> | </small>
        <small>${news.date}</small>
        <hr/>
      </li>

    `;
    newsList.append(newsItem); // 새 뉴스 항목을 추가합니다

  });
}

// 음수 양수
isPositive = function(num) {
  return num >= 0;
};

