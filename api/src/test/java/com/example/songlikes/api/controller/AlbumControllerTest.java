package com.example.songlikes.api.controller;

import com.example.songlikes.api.service.AlbumCountService;
import com.example.songlikes.domain.dto.YearArtistAlbumCountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.*;

@WebFluxTest(controllers = AlbumController.class)
class AlbumControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private AlbumCountService albumCountService;

    @Test
    void getAlbumCounts_withParams_returnsList() {
        // given
        Integer releaseYear = 2023;
        String artist = "IU";
        int page = 0;
        int size = 5;

        YearArtistAlbumCountDto dto1 = new YearArtistAlbumCountDto(2023, "IU", 2L);

        when(albumCountService.searchAlbumCountByReleaseYearAndArtist(releaseYear, artist, page, size))
            .thenReturn(Flux.just(dto1));

        // when
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/albums/counts")
                .queryParam("releaseYear", releaseYear)
                .queryParam("artist", artist)
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
            )
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(YearArtistAlbumCountDto.class)
            .hasSize(1)
            .contains(dto1);

        // verify service call
        verify(albumCountService, times(1))
            .searchAlbumCountByReleaseYearAndArtist(releaseYear, artist, page, size);
    }

    @Test
    void getAlbumCounts_withNoParams_returnsList() {
        YearArtistAlbumCountDto dto1 = new YearArtistAlbumCountDto(2021, "IU", 2L);
        YearArtistAlbumCountDto dto2 = new YearArtistAlbumCountDto(2023, "IU", 5L);
        YearArtistAlbumCountDto dto3 = new YearArtistAlbumCountDto(2023, "BTS", 1L);
        YearArtistAlbumCountDto dto4 = new YearArtistAlbumCountDto(2024, "BTS", 4L);

        // given
        when(albumCountService.searchAlbumCountByReleaseYearAndArtist(null, null, 0, 20))
            .thenReturn(Flux.just(dto1, dto2, dto3, dto4));

        // when
        webTestClient.get()
            .uri("/api/albums/counts")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(YearArtistAlbumCountDto.class)
            .hasSize(4);

        verify(albumCountService, times(1))
            .searchAlbumCountByReleaseYearAndArtist(null, null, 0, 20);
    }
}
