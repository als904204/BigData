package com.example.bigdata.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주식 종목명
    private String company;

    // 날짜
    private LocalDate date;

    // 시가
    private Integer open;

    // 고가
    private Integer high;

    // 저가
    private Integer low;

    // 종가
    private Integer close;

    // 거래량
    private Integer volume;

    // 변동률
    private BigDecimal changes;

    private Double predicted;

    @Builder
    public Stock(String company,LocalDate date, Integer open, Integer high, Integer low, Integer close,
        Integer volume,
        BigDecimal changes,
        Double predicted) {
        this.company = company;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.changes = changes;
        this.predicted = predicted;
    }


    public Stock() {}

    public void setPredicted(Double predicted) {
        this.predicted = predicted;
    }
}
