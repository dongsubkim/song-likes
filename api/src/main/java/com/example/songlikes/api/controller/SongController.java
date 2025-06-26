package com.example.songlikes.api.controller;

import com.example.songlikes.api.service.SongLikeService;
import com.example.songlikes.domain.dto.TopLikedSongDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {
    private static final Logger log = LoggerFactory.getLogger(SongController.class);
    private final SongLikeService songLikeService;

    @PostMapping("/{songId}/likes")
    public Mono<Boolean> likeSong(@PathVariable Long songId) {
        log.info("노래 좋아요 증가 API 요청 songId: {}", songId);
        return songLikeService.likeSong(songId);
    }

    @GetMapping("/likes/hourly-top-10")
    public Flux<TopLikedSongDto> getTop10LikedSongsInLastHour() {
        log.info("최근 1시간동안 좋아요 증가 top 10 노래 조회 API 요청");
        return songLikeService.findTop10LikedSongInLastHour();
    }
}
