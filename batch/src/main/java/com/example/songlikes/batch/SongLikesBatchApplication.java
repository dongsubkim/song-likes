package com.example.songlikes.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.songlikes.batch", "com.example.songlikes.domain"})
public class SongLikesBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongLikesBatchApplication.class, args);
    }

}
