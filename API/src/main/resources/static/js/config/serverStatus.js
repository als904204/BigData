$(document).ready(function(){
  checkDataAvailability();
});

function checkDataAvailability() {
  $.ajax({
    url: '/api/v1/stock/check',
    method: 'GET',
    success: function(isDataAvailable) {

      // DB에는 데이터가 없는데 웹 브라우져 스토리지엔 uploadedCompany 가 있을경우 삭제
      if (!isDataAvailable && localStorage.getItem('uploadedCompany') !=null) {
        // 데이터가 있는 경우의 처리
        console.log("Data is available.");
        alert('데이터는 없는데 로컬 스토리지에 있어서 삭제함')
        localStorage.removeItem('uploadedCompany');
        localStorage.removeItem('fileUploaded');
      }
    },
    error: function(error) {
      console.error("Error occurred while checking data availability:", error);
    }
  });
}