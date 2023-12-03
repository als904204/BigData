package com.example.bigdata.stock.controller;

import com.example.bigdata.stock.dto.NewsItem;
import com.example.bigdata.stock.service.CrawlingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
@RestController
public class CrawlingController {

    private final CrawlingService crawlingService;

    // company 로 해당 종목의 뉴스를 크롤링
    @GetMapping("/crawling/{company}")
    public ResponseEntity<List<NewsItem>> getNewsItems(@PathVariable String company) {
        List<NewsItem> newsItems = crawlingService.newsCrawlingByCompany(company);
        return ResponseEntity.ok(newsItems);
    }
}
