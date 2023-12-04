package com.example.bigdata.stock.service;

import com.example.bigdata.stock.dto.StockDto.DateRangeDtoRes;
import com.example.bigdata.stock.dto.StockDto.PredictedDtoRes;
import com.example.bigdata.stock.dto.StockDto.StockDtoRes;
import com.example.bigdata.stock.dto.StockDto.StockStatisticsDtoRes;
import com.example.bigdata.stock.dto.StockDto.CurrentAndPreviousStock;
import com.example.bigdata.stock.entity.Stock;
import com.example.bigdata.stock.repository.StockRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public List<StockDtoRes> getStockDataByCompany(String company) {
        log.info("getStockDataByCompany={}", company);
        List<Stock> stocks = stockRepository.findByCompany(company);
        if (stocks.isEmpty()) {
            log.error("no datas the company={}",company);
            throw new RuntimeException("no datas");
        }

        return stocks.stream()
            .map(StockDtoRes::new)
            .toList();
    }

    // Stock 평균가 계산
    private BigDecimal getStockAvgPrice(Stock currentStock) {
        return BigDecimal.valueOf(
                currentStock.getOpen()
                    + currentStock.getHigh()
                    + currentStock.getLow()
                    + currentStock.getClose())
            .divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP);
    }

    // Stock 예측 값,예측 날짜,예측 전 날짜
    public PredictedDtoRes getStockPredicted(String company) {
        LocalDate firstStockDate = stockRepository.findFirstDateByCompany(company)
            .orElseThrow(() -> new RuntimeException("There is no stock data"));

        LocalDate lastStockDate = stockRepository.findLastDateByCompany(company)
            .orElseThrow(() -> new RuntimeException("There is no stock data"));
        LocalDate predictedDate = lastStockDate.plusDays(1);

        // 예측 값 가져오기 (예측값은 0번쨰 Stock 이 가지고 있음)
        Stock firstStock = stockRepository.findByCompanyAndDate(company, firstStockDate)
            .orElseThrow(() -> new RuntimeException("There is no stock data"));
        Double predicted = firstStock.getPredicted();

        // 예측 값과 가장 최근 데이터를 비교하기 위해 최근 데이터 가져오기
        Stock recentStock = stockRepository.findByCompanyAndDate(company, lastStockDate)
            .orElseThrow(() -> new RuntimeException("There is no stock data"));

        Integer recentStockClose = recentStock.getClose();

        // 예측 값과 실제 값 비교
        double difference = predicted - recentStockClose;
        double percentageChange = (difference / recentStockClose) * 100;

        return new PredictedDtoRes(predicted, lastStockDate,predictedDate,difference,percentageChange);
    }

    /**
     * 해당 종목의 평균,최저,최고가
     *
     * @param company   종목
     * @return 평균, 최저, 최고가
     */
    @Transactional(readOnly = true)
    public StockStatisticsDtoRes calculateStockStatistics(String company) {
        List<Stock> stocks = stockRepository.findByCompany(company);

        long totalClose = 0;
        long lowestPrice = Integer.MAX_VALUE;
        long highestPrice = Integer.MIN_VALUE;

        int size = stocks.size();
        for (int i = 0; i < size; i++) {
            Stock stock = stocks.get(i);
            totalClose += stock.getClose();
            lowestPrice = Math.min(lowestPrice, stock.getLow());
            highestPrice = Math.max(highestPrice, stock.getHigh());
        }

        long avgPrice = stocks.isEmpty() ? 0 : totalClose / size;

        // 만약 초기값과 같다면 주식 데이터가 없는 경우 (잘못된 데이터셋)
        if (lowestPrice == Integer.MIN_VALUE || highestPrice == Integer.MIN_VALUE) {
            log.warn("calculate warn wrong dataset!");
            lowestPrice = 0;
            highestPrice = 0;
        }

        log.info("{} : avg={},low={},high={}", company, avgPrice, lowestPrice, highestPrice);

        return new StockStatisticsDtoRes(avgPrice, lowestPrice, highestPrice);
    }


    // company 데이터 첫 날짜,마지막 날짜
    @Transactional(readOnly = true)
    public DateRangeDtoRes getStockDataForDateRange(String company) {
        LocalDate first = stockRepository.findFirstDateByCompany(company).orElseThrow(()-> new RuntimeException(
            "데이터 없음"));
        LocalDate last = stockRepository.findLastDateByCompany(company).orElseThrow(()-> new RuntimeException(
            "데이터 없음"));
        return new DateRangeDtoRes(first, last);
    }

    // 해당 종목의 최근 세달치 Stock 데이터
    @Transactional(readOnly = true)
    public List<StockDtoRes> getStockRecentData(String company) {
        LocalDate endDate = stockRepository.findLastDateByCompany(company).orElseThrow(()-> new RuntimeException(
            "데이터 없음"
        ));

        LocalDate startDate = endDate.minusMonths(3);
        List<Stock> stocks = stockRepository.findRecentMonthData(company, startDate,
            endDate);

        log.info("Get Recent Stock Date From {} To {}",startDate,endDate);
        return stocks.stream()
            .map(StockDtoRes::new)
            .toList();
    }

    // 최근데이터와 최근데이터의 전 데이터
    // 0번째 최근데이터
    // 1번째 전날 데이터
    @Transactional(readOnly = true)
    public List<CurrentAndPreviousStock> getCurrentAndPrevStockData(String company) {

        // 업로드된 파일을 기준으로 당일 날짜
        LocalDate currentStockDate = stockRepository.findLastDateByCompany(company).orElseThrow(()-> new RuntimeException(
            "데이터 없음"));



        // 최근 파일날짜로 Stock 가져오기
        Stock currentStock = getValidStockDate(company, currentStockDate);

        // 최근 Stock 의 -1 한 날짜
        LocalDate prevStockDate = currentStock.getDate().minusDays(1);

        // 최근 파일날짜 전날 날짜로 Stock 가져오기
        Stock previousStock = getValidStockDate(company, prevStockDate);

        // 평균가 계산 및 DTO 생성
        BigDecimal currentStockAvgPrice = getStockAvgPrice(currentStock);
        BigDecimal previousStockAvgPrice = getStockAvgPrice(previousStock);

        List<CurrentAndPreviousStock> stockData = new ArrayList<>();

        stockData.add(new CurrentAndPreviousStock(currentStock));
        stockData.get(0).setAveragePrice(currentStockAvgPrice);

        stockData.add(new CurrentAndPreviousStock(previousStock));
        stockData.get(1).setAveragePrice(previousStockAvgPrice);

        return stockData;
    }

    // 해당 데이터의 최신데이터를 가져오는데 만약 없으면 7일전까지 검사
    private Stock getValidStockDate(String company, LocalDate date) {
        for (int i = 0; i < 7; i++) {
            Optional<Stock> stock = stockRepository.findByCompanyAndDate(company, date);
            if (stock.isPresent()) {
                return stock.get();
            }
            date = date.minusDays(1);
        }
        log.error("no data for 7 days={}",company);
        throw new RuntimeException("No data available for the past week for " + company);
    }


    // DB 데이터 유/무 체크 (DB엔 없는데 프론트 로컬 스토리지에 저장되어있는 경우 삭제)
    @Transactional(readOnly = true)
    public Boolean checkData() {
        long count = stockRepository.count();
        return count > 0; // 데이터가 하나라도 있으면 true, 없으면 false
    }


}