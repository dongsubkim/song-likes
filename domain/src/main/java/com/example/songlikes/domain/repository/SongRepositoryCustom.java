package com.example.songlikes.domain.repository;

import com.example.songlikes.domain.dto.YearArtistAlbumCountDto;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface SongRepositoryCustom {
    Flux<YearArtistAlbumCountDto> findYearArtistAlbumCountGrouped(
        @Param("releaseYear") Integer releaseYear,
        @Param("artist") String artist,
        @Param("offset") long offset,
        @Param("size") int size
    );
}
