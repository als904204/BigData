package com.example.bigdata.stock.controller;

import com.example.bigdata.stock.dto.StockDto.DateRangeDtoRes;
import com.example.bigdata.stock.dto.StockDto.PredictedDtoRes;
import com.example.bigdata.stock.dto.StockDto.StockDtoRes;
import com.example.bigdata.stock.dto.StockDto.StockStatisticsDtoRes;
import com.example.bigdata.stock.dto.StockDto.CurrentAndPreviousStock;
import com.example.bigdata.stock.dto.UploadFileDto.UploadRes;
import com.example.bigdata.stock.service.StockService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockService stockService;

    // 엑셀 업로드
    @PostMapping
    public ResponseEntity<UploadRes> uploadFile(@RequestParam("file") MultipartFile file) {
        UploadRes uploadRes = stockService.readAndSaveExcelData(file);
        return ResponseEntity.ok(uploadRes);
    }

    // {company} 에 해당하는 데이터 리스트 조회
    @GetMapping("/{company}")
    public ResponseEntity<List<StockDtoRes>> getStockData(@PathVariable String company) {
        List<StockDtoRes> stocks = stockService.getStockDataByCompany(company);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/predicted/{company}")
    public ResponseEntity<PredictedDtoRes> getStockPredicted(@PathVariable String company) {
        PredictedDtoRes stockPredicted = stockService.getStockPredicted(company);
        return ResponseEntity.ok(stockPredicted);
    }


    // {company} 에 해당하는 최근,어제 데이터 조회
    @GetMapping("/yesterday/{company}")
    public ResponseEntity<List<CurrentAndPreviousStock>> getStockDataForYesterday (@PathVariable String company) {
        List<CurrentAndPreviousStock> stockDtoRes = stockService.getCurrentAndPrevStockData(company);
        return ResponseEntity.ok(stockDtoRes);
    }

    // Stock 첫 날짜, 가장 최근 날짜
    @GetMapping("/date-range/{company}")
    public ResponseEntity<DateRangeDtoRes> getStockDateRange (@PathVariable String company) {
        DateRangeDtoRes dateRange = stockService.getStockDataForDateRange(company);
        return ResponseEntity.ok(dateRange);
    }

    // TODO : 선택한 날짜별로 평균,최저,최고가 가져오는 로직 구현
    // {company} 종목 전체 평균,고가,저가
    @GetMapping("/statistics/{company}")
    public ResponseEntity<StockStatisticsDtoRes> getStockStatistics(@PathVariable String company) {
        StockStatisticsDtoRes statistics = stockService.calculateStockStatistics(
            company);
        return ResponseEntity.ok(statistics);
    }

    // 최근 한달치 Stock 데이터
    @GetMapping("/recent/{company}")
    public ResponseEntity<List<StockDtoRes>> getStockRecentData(@PathVariable String company) {
        List<StockDtoRes> stockDataAMonth = stockService.getStockRecentData(company);
        return ResponseEntity.ok(stockDataAMonth);
    }


    // DB 데이터 유/무 확인 (없으면 웹 브라우저 로컬 스토리지 정보 삭제)
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkDataStatus() {
        Boolean isDataAvailable = stockService.checkData();
        return ResponseEntity.ok(isDataAvailable);
    }

}
