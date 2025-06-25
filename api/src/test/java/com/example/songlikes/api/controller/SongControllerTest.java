package com.example.songlikes.api.controller;

import com.example.songlikes.api.service.SongLikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
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
}
