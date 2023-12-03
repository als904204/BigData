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