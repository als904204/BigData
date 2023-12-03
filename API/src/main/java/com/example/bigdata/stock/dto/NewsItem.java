package com.example.bigdata.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class NewsItem {
    private String title;
    private String link;
    private String content;
    private String source;
    private String date;


    public NewsItem(String title, String link, String content, String source, String date) {
        this.title = title;
        this.link = "https://www.paxnet.co.kr"+link;
        this.content = content;
        this.source = source;
        this.date = date;
    }
}
