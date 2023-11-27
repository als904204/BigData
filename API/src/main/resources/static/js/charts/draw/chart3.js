// 두번째 도넛 차트
function renderStockChartV3Pie(data) {
  // Pie Chart Example
  const ctx = document.getElementById("chart3");
  const myPieChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: ['Average Price', 'Lowest Price', 'Highest Price'],
      datasets: [{
        data: [data.averagePrice, data.lowestPrice, data.highestPrice],
        // backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
        backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
        hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
        hoverBorderColor: "rgba(234, 236, 244, 1)",
      }],
    },
    options: {
      maintainAspectRatio: false,
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        caretPadding: 10,
      },
      legend: {
        display: false
      },
      cutoutPercentage: 80,
    },
  });

}

function updateStockStatistics(data) {
  document.getElementById('averagePrice').textContent = data.averagePrice;
  document.getElementById('highestPrice').textContent = data.highestPrice;
  document.getElementById('lowestPrice').textContent = data.lowestPrice;
}