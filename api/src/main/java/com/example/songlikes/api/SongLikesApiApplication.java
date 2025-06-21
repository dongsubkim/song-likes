package com.example.songlikes.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.songlikes.domain", "com.example.songlikes.api"})
public class SongLikesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongLikesApiApplication.class, args);
    }

}
