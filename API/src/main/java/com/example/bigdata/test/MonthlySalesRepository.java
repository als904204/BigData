package com.example.bigdata.test;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlySalesRepository extends JpaRepository<MonthlySales, String> {
    /**
     * 월별 매출 조회
     * @param year
     * @return
     */
    List<MonthlySales> findByDateStartsWith(String year);
}
