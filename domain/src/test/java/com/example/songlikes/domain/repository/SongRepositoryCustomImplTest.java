package com.example.songlikes.domain.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.r2dbc.core.DatabaseClient;

class SongRepositoryCustomImplTest {
    @Mock
    private DatabaseClient databaseClient;

    @Test
    void testGetYearArtistAlbumCountQueryString_allNull() {
        // given
        SongRepositoryCustomImpl repository = new SongRepositoryCustomImpl(databaseClient);

        // when
        String sql = repository.getYearArtistAlbumCountQueryString(null, null, 0, 20)
            .replaceAll("\\s+", " ").trim();

        // then
        assert sql.equals("""
                SELECT release_year AS releaseYear, artist, COUNT(DISTINCT album) AS albumCount
                  FROM songs
                 WHERE album IS NOT NULL AND release_year IS NOT NULL AND artist IS NOT NULL GROUP BY artist, release_year ORDER BY release_year, artist LIMIT :size OFFSET :offset
            """.replaceAll("\\s+", " ").trim());
    }

    @Test
    void testGetYearArtistAlbumCountQueryString_releaseYearNotNull_artistNotNull() {
        // given
        SongRepositoryCustomImpl repository = new SongRepositoryCustomImpl(databaseClient);

        // when
        String sql = repository.getYearArtistAlbumCountQueryString(2025, "IU", 0, 20)
            .replaceAll("\\s+", " ").trim();

        // then
        assert sql.equals(
            """
                SELECT release_year AS releaseYear, artist, COUNT(DISTINCT album) AS albumCount
                 FROM songs
                WHERE album IS NOT NULL AND release_year = :releaseYear AND artist = :artist GROUP BY artist, release_year ORDER BY release_year, artist LIMIT :size OFFSET :offset
                """.replaceAll("\\s+", " ").trim()
        );
    }
}
