package com.example.bigdata.test;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChartService {
    private final MonthlySalesRepository monthlySalesRepository;

    /**
     * 월별 매출액 조회
     * @param year
     * @return
     */
    public List<MonthlySales> getMonthlySales(String year){
        return monthlySalesRepository.findByDateStartsWith(year);
    }

//    @PostConstruct
    public void test() {
        List<MonthlySales> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MonthlySales build = MonthlySales.builder()
                .date("20230" + i)
                .sales(100 * i)
                .build();
            list.add(build);
        }
        monthlySalesRepository.saveAll(list);
    }
}
