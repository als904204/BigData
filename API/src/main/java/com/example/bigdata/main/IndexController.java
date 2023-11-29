package com.example.bigdata.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/test")
    public String test() {
        return "fragment/index-test";
    }

    @GetMapping("/chart/candle")
    public String candleChart() {
        return "fragment/candle/candleChartPage";
    }

    @GetMapping("/chart/line")
    public String lineChart() {
        return "fragment/line/lineChartPage";
    }
}
