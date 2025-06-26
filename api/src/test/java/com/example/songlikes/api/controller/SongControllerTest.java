package com.example.songlikes.api.controller;

import com.example.songlikes.api.service.SongLikeService;
import com.example.songlikes.domain.dto.TopLikedSongDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@WebFluxTest(controllers = SongController.class)
public class SongControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private SongLikeService songLikeService;

    @Test
    void songLike_success() {
        // given
        Long songId = 1L;
        when(songLikeService.likeSong(songId)).thenReturn(Mono.just(true));

        // when
        webTestClient.post()
            .uri("/api/songs/{songId}/likes", songId)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Boolean.class)
            .isEqualTo(true);

        // verify service call
        verify(songLikeService, times(1)).likeSong(songId);
    }

    @Test
    void getTop10LikedSongsInLastHour_success() {
        // given
        when(songLikeService.findTop10LikedSongInLastHour()).thenReturn(Flux.just(
            new TopLikedSongDto(10L, 10L),
            new TopLikedSongDto(9L, 9L),
            new TopLikedSongDto(8L, 8L),
            new TopLikedSongDto(7L, 7L),
            new TopLikedSongDto(6L, 6L),
            new TopLikedSongDto(5L, 5L),
            new TopLikedSongDto(4L, 4L),
            new TopLikedSongDto(3L, 3L),
            new TopLikedSongDto(2L, 2L),
            new TopLikedSongDto(1L, 1L)
        ));

        // when
        webTestClient.get()
            .uri("/api/songs/likes/hourly-top-10")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(TopLikedSongDto.class)
            .hasSize(10);

        // verify service call
        verify(songLikeService, times(1)).findTop10LikedSongInLastHour();
    }
}
