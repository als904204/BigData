/**
 * 엑셀 파일 업로드 js
 * (업로드 성공) 업로드한 파일로 차트를 그린다
 * (업로드 실패) 실패 메세지
 */

$(document).ready(function(){
  checkUploadStatus();
  $('#fileUploadForm').on('submit', function (event) {
    event.preventDefault();

    const formData = new FormData(this);

    $.ajax({
      type: `POST`,
      url: `/api/v1/stock`,
      data: formData,
      processData: false,
      contentType: false,
      success: function (response) {
        console.log('File uploaded successfully');
        const companyName = response.name;
        window.alert("파일 업로드에 성공했습니다 : " + companyName);

        localStorage.setItem('fileUploaded', 'true');
        localStorage.setItem('uploadedCompany', companyName); // 업로드한 회사명 저장
        changeToCancelButton();

        // 차트1 그리기
        fetchStockDataAndRenderLineChart(companyName);
        // 차트2 그리기
        fetchStockStatisticsAndRenderPieChart(companyName);
        // 차트 3 그리기
        fetchRecentStockData(companyName)

        // Header 메뉴 값 호출
        fetchStockDataForYesterday(companyName)
        fetchStockDateRangeData(companyName);


      },
      error: function (error) {
        console.log('Error occurred during file upload');
        window.alert("실패 : "+error);
        changeToSelectButton();
      }
    });
  });

});



