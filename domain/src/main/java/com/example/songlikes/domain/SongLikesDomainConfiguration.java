package com.example.songlikes.domain;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@ComponentScan(basePackages = {"com.example.songlikes.domain"})
@EnableR2dbcRepositories(basePackages = "com.example.songlikes.domain.repository")
public class SongLikesDomainConfiguration {
}
