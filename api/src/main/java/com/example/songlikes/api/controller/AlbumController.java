package com.example.songlikes.api.controller;

import com.example.songlikes.api.service.AlbumCountService;
import com.example.songlikes.domain.dto.YearArtistAlbumCountDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {
    private static final Logger log = LoggerFactory.getLogger(AlbumController.class);
    private final AlbumCountService albumCountService;

    @GetMapping("/counts")
    public Flux<YearArtistAlbumCountDto> getAlbumCounts(
        @RequestParam(required = false) Integer releaseYear,
        @RequestParam(required = false) String artist,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        log.info("앪범 수 조회 API 요청. release year: {}, artist: {}, page: {}, size: {}", releaseYear, artist, page, size);
        return albumCountService.searchAlbumCountByReleaseYearAndArtist(releaseYear, artist, page, size);
    }
}
