package com.example.songlikes.api.service;

import com.example.songlikes.domain.dto.YearArtistAlbumCountDto;
import com.example.songlikes.domain.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SongSearchServiceTest {

    @Mock
    SongRepository songRepository;

    @InjectMocks
    SongSearchService songSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchAlbumCountByReleaseYearAndArtist_withAllParams() {
        // Given
        Integer releaseYear = 2023;
        String artist = "IU";
        int page = 0;
        int size = 10;
        long expectedOffset = 0L;

        List<YearArtistAlbumCountDto> mockResult = List.of(
            new YearArtistAlbumCountDto(2023, "IU", 2L)
        );

        when(songRepository.findYearArtistAlbumCountGrouped(releaseYear, artist, expectedOffset, size))
            .thenReturn(Flux.fromIterable(mockResult));

        // When
        Flux<YearArtistAlbumCountDto> result = songSearchService
            .searchAlbumCountByReleaseYearAndArtist(releaseYear, artist, page, size);

        // Then
        StepVerifier.create(result)
            .expectNextMatches(dto -> dto.getReleaseYear().equals(2023) && dto.getArtist().equals("IU") && dto.getAlbumCount() == 2L)
            .verifyComplete();

        verify(songRepository, times(1)).findYearArtistAlbumCountGrouped(releaseYear, artist, expectedOffset, size);
    }

    @Test
    void searchAlbumCountByReleaseYearAndArtist_withNullParams() {
        // Given
        Integer releaseYear = null;
        String artist = null;
        int page = 1;
        int size = 2;
        long expectedOffset = 2L;

        List<YearArtistAlbumCountDto> mockResult = Arrays.asList(
            new YearArtistAlbumCountDto(2021, "BTS", 1L),
            new YearArtistAlbumCountDto(2022, "IU", 1L)
        );

        when(songRepository.findYearArtistAlbumCountGrouped(null, null, expectedOffset, size))
            .thenReturn(Flux.fromIterable(mockResult));

        // When
        Flux<YearArtistAlbumCountDto> result = songSearchService
            .searchAlbumCountByReleaseYearAndArtist(null, null, page, size);

        // Then
        StepVerifier.create(result)
            .expectNextMatches(dto -> dto.getReleaseYear() == 2021 && dto.getArtist().equals("BTS") && dto.getAlbumCount() == 1L)
            .expectNextMatches(dto -> dto.getReleaseYear() == 2022 && dto.getArtist().equals("IU") && dto.getAlbumCount() == 1L)
            .verifyComplete();

        verify(songRepository, times(1)).findYearArtistAlbumCountGrouped(null, null, expectedOffset, size);
    }

    @Test
    void searchAlbumCountByReleaseYearAndArtist_repositoryReturnsEmpty() {
        // Given
        when(songRepository.findYearArtistAlbumCountGrouped(any(), any(), anyLong(), anyInt()))
            .thenReturn(Flux.empty());

        // When/Then
        StepVerifier.create(songSearchService.searchAlbumCountByReleaseYearAndArtist(null, null, 0, 10))
            .verifyComplete();

        verify(songRepository).findYearArtistAlbumCountGrouped(null, null, 0L, 10);
    }
}
