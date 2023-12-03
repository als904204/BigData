package com.example.bigdata.stock.service;

import com.example.bigdata.stock.dto.NewsItem;
import com.example.bigdata.stock.entity.Stock;
import com.example.bigdata.stock.repository.StockRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final StockRepository stockRepository;
    public List<NewsItem> newsCrawlingByCompany(String company) {
        // 0. company 로 stock 을 가져온다
        LocalDate firstStockDate = stockRepository.findFirstDateByCompany(company).orElseThrow(
            () -> new RuntimeException("can't find stock for CrawlingService.findFirstDateByCompany"));

        Stock stock = stockRepository.findByCompanyAndDate(company, firstStockDate).orElseThrow(
            () -> new RuntimeException(
                "can't find stock for CrawlingService.findByCompanyAndDate"));

        int stockCode = stock.getCode();

        // 1. code 를 가져온다
        String code = String.valueOf(stockCode);

        // 2. www.paxnet.co.kr/news/{code}/stock?stockCode={code}&objId=S{code} GET 요청
        String url = "https://www.paxnet.co.kr/news/" + code + "/stock?stockCode=" + code + "&objId=S" + code;

        // 3. 태그안에 요소들을 긁어와 객체화 한다
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();

            Elements newsTitles = doc.select("dl.text dt a");
            Elements newsContents = doc.select("dl.text dd.cont");
            Elements newsDates = doc.select("dl.text dd.date");

            int size = newsTitles.size();

            for (int i = 0; i < size; i++) {
                String title = newsTitles.get(i).text();
                String link = newsTitles.get(i).attr("href");
                String content = newsContents.get(i).text();
                String source = Objects.requireNonNull(newsDates.get(i).select("span").first()).text();
                String date = Objects.requireNonNull(newsDates.get(i).select("span").last()).text();

                NewsItem newsItem = new NewsItem(title, link, content, source, date);
                newsItems.add(newsItem);
            }
        } catch (IOException e) {
            log.error("error while web crawling request. e={}",e.getMessage());
            throw new RuntimeException("crawling error");
        }

        return newsItems;

    }



}
