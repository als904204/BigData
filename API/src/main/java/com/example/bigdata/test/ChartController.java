package com.example.bigdata.test;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/monthly-sales/{year}")
    public List<MonthlySales> monthlySalesList(@PathVariable("year") String year){
        return chartService.getMonthlySales(year);
    }
}
