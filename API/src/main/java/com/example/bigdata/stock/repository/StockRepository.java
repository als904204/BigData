package com.example.bigdata.stock.repository;

import com.example.bigdata.stock.entity.Stock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


    List<Stock> findByCompany(String company);

    boolean existsByCompany(String company);

    /**
     * 종목 이름, 어제 Stock 데이터 조회
     * SELECT *
     * FROM Stock
     * WHERE company = '?' AND date = '?';
     *
     * @param company   조회할 종목의 이름
     * @param yesterday 어제 날짜
     * @return 어제 Stock 데이터
     */
    Optional<Stock> findByCompanyAndDate(String company,LocalDate yesterday);

    // 최소 날짜 조회
    @Query("SELECT MIN(s.date) FROM Stock s WHERE s.company = :company")
    Optional<LocalDate> findFirstDateByCompany(@Param("company") String company);

    // 최대 날짜 조회
    @Query("SELECT MAX(s.date) FROM Stock s WHERE s.company = :company")
    Optional<LocalDate> findLastDateByCompany(@Param("company") String company);

    /**
     * 최근 데이터를 기준으로 세달전 데이터까지만 가져오기
     * sql 예
     * SELECT * FROM stock
     * WHERE company = 'Samsung' AND date BETWEEN
     * '2023-03-01' AND '2023-04-01' ORDER BY date DESC;
     *
     * @param company   조회할 종목의 이름
     * @param startDate 세달 전 데이터 날짜
     * @param endDate   최근 데이터 날짜
     * @return 최근 데이터기준으로 세달치 데이터 List
     */
    @Query("SELECT s "
        + "FROM Stock s "
        + "WHERE s.company = :company AND s.date BETWEEN :startDate AND :endDate ORDER BY s.date DESC")
    List<Stock> findRecentMonthData(@Param("company") String company, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
