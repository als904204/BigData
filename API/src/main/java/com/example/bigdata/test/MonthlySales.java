package com.example.bigdata.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "monthly_sales")
@NoArgsConstructor
public class MonthlySales {

    @Id
    private String date;
    private long sales;

    @Builder
    public MonthlySales(String date, long sales) {
        this.date = date;
        this.sales = sales;
    }
}
