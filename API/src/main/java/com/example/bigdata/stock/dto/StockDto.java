package com.example.bigdata.stock.dto;

import com.example.bigdata.stock.entity.Stock;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

public class StockDto {

    @Getter
    public static class StockDtoRes {
        private final String company;
        private final LocalDate date;
        private final Integer open;
        private final Integer high;
        private final Integer low;
        private final Integer closePrice;
        private final Integer volume;
        private final BigDecimal changes;
        public StockDtoRes(Stock stock) {
            this.company = stock.getCompany();
            this.date = stock.getDate();
            this.open = stock.getOpen();
            this.high = stock.getHigh();
            this.low = stock.getLow();
            this.closePrice = stock.getClose();
            this.volume = stock.getVolume();
            this.changes = stock.getChanges();
        }
    }

    // 해당 종목의 평균,저가,고가
    @Getter
    @AllArgsConstructor
    public static class StockStatisticsDtoRes {
        private final long averagePrice;
        private final long lowestPrice;
        private final long highestPrice;
    }

    // 해당 종목의 이전,현재 정보
    @Getter
    public static class CurrentAndPreviousStock {
        private final LocalDate date;
        private BigDecimal averagePrice;
        private final Integer high;
        private final Integer low;
        private final Integer volume;
        private final BigDecimal change;
        private final Integer closePrice;


        public void setAveragePrice(BigDecimal averagePrice) {
            this.averagePrice = averagePrice;
        }
        public CurrentAndPreviousStock(Stock stock) {
            this.date = stock.getDate();
            this.high = stock.getHigh();
            this.low = stock.getLow();
            this.volume = stock.getVolume();
            this.change = stock.getChanges();
            this.closePrice = stock.getClose();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class DateRangeDtoRes{
        private LocalDate firstDate;
        private LocalDate lastDate;
    }

    @Getter
    @AllArgsConstructor
    public static class PredictedDtoRes{

        // 예측가
        private Double predicted;
        private LocalDate predictedDate;


    }


}
