/**
 * 파일 업로드 상태 별 버튼 변경 JS
 */
function onFileSelect() {
  const fileInput = document.getElementById('fileInput');
  if (fileInput.files.length > 0) {
    document.getElementById('uploadButton').style.display = 'inline-block';
    document.getElementById('selectButton').style.display = 'none';
  }
}

// 취소 버튼 누를 시 localStorage 삭제
function onCancel() {
  localStorage.removeItem('fileUploaded');
  localStorage.removeItem('uploadedCompany');
  changeToSelectButton();
  alert('파일 업로드 취소')
  location.reload()
}

// 로컬에 fileUploaded 가 true 일때만 그려라 (없으면 DB 엔 없는데 로컬스토리지에 남아있는거)
function checkUploadStatus() {
  if (localStorage.getItem('fileUploaded') === 'true') {

    const uploadedCompany = localStorage.getItem('uploadedCompany');

    // 파일이 업로드 된 상태라면 그래프를 그림
    if (uploadedCompany) {
      changeToCancelButton();
      fetchStockDataAndRenderLineChart(uploadedCompany);
      fetchStockStatisticsAndRenderPieChart(uploadedCompany);
      fetchStockDataForYesterday(uploadedCompany);
      fetchStockDateRangeData(uploadedCompany);
      fetchRecentStockData(uploadedCompany);
      fetchStockPredicted(uploadedCompany);
      fetchStockCrawling(uploadedCompany);
    }
  } else { // 업로드 X 라면 업로드 버튼 활성화
    changeToSelectButton();
  }
}

// 파일 업로드 버튼 활성화
function changeToSelectButton() {
  document.getElementById('selectButton').style.display = 'inline-block';
  document.getElementById('uploadButton').style.display = 'none';
  document.getElementById('cancelButton').style.display = 'none';
  document.getElementById('fileInput').value = ''; // 파일 입력 필드 초기화
}

// 파일 취소 버튼 활성화
function changeToCancelButton() {
  document.getElementById('cancelButton').style.display = 'inline-block';
  document.getElementById('selectButton').style.display = 'none';
  document.getElementById('uploadButton').style.display = 'none';
}
