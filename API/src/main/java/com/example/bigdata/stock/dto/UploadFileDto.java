package com.example.bigdata.stock.dto;

import com.example.bigdata.stock.entity.Stock;
import lombok.Getter;

public class UploadFileDto {

    @Getter
    public static class UploadRes {
        public String name;
        public UploadRes(String name) {
            this.name = name;
        }
    }


}
