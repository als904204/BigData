package com.example.bigdata.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final RestTemplate restTemplate;

    private final String URL = "http://127.0.0.1:5000/subway";

    @GetMapping("test")
    public ResponseEntity<?> test() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        return ResponseEntity.ok(forEntity.getBody());
    }

    @GetMapping("/test/samsung")
    public String samsung() {
        System.out.println("/test/samsung 호출 GET ");
        String url = "http://127.0.0.1:5000/test/samsung";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        System.out.println("body = " + body);
        return "hello";
    }

    @PostMapping("/test/samsung")
    public String samsung2(@RequestBody String data) {
        return "hello";
    }
}
