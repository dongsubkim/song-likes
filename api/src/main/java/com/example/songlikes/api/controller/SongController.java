package com.example.songlikes.api.controller;

import com.example.songlikes.api.service.SongLikeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
