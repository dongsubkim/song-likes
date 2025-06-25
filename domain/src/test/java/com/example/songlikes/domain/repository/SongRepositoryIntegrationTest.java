package com.example.songlikes.domain.repository;

import com.example.songlikes.domain.entity.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class SongRepositoryIntegrationTest {

    // region testcontainers 설정
    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        // R2DBC는 r2dbc url을 별도로 써야 함!
        registry.add("spring.r2dbc.url",
            () -> "r2dbc:postgresql://" + postgres.getHost() + ":" + postgres.getFirstMappedPort() + "/testdb");
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);

        // JDBC URL도 flyway와 spring.sql.init용으로 써야 할 수 있음
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    // endregion

    @Autowired
    SongRepository songRepository;

    @BeforeEach
    void clean() {
        // 테스트 데이터 클린업 필요시 직접 처리
        songRepository.deleteAll().block();
    }

    @Test
    void testFindYearArtistAlbumCountGrouped() {
        // Given
        Song song1 = Song.builder()
            .releaseDate(LocalDate.of(2021, 3, 25))
            .artist("IU")
            .album("LILAC")
            .build();
        Song song2 = Song.builder()
            .releaseDate(LocalDate.of(2017, 4, 21))
            .artist("IU")
            .album("Pallet")
            .build();
        Song song3 = Song.builder()
            .releaseDate(LocalDate.of(2021, 10, 19))
            .artist("IU")
            .album("Strawberry Moon")
            .build();
        Song son4 = Song.builder()
            .releaseDate(LocalDate.of(2021, 7, 23))
            .artist("BTS")
            .album("Permission to Dance")
            .build();
        songRepository.saveAll(
            List.of(song1, song2, song3, son4)
        ).blockLast();

        // When
        var result = songRepository.findYearArtistAlbumCountGrouped(
            2021, "IU", 0, 10
        ).collectList().block();

        // Then
        assert result != null;
        assert result.size() == 1;
        var dto = result.getFirst();
        assert dto.getReleaseYear() == 2021;
        assert dto.getArtist().equals("IU");
        assert dto.getAlbumCount() == 2;

        // When
        var result2 = songRepository.findYearArtistAlbumCountGrouped(
            2021, null, 0, 10
        ).collectList().block();
        // Then
        assert result2 != null;
        assert result2.size() == 2;
        var dto2 = result2.getFirst();
        assert dto2.getReleaseYear() == 2021;
        assert dto2.getArtist().equals("BTS");
        assert dto2.getAlbumCount() == 1;
        var dto3 = result2.get(1);
        assert dto3.getReleaseYear() == 2021;
        assert dto3.getArtist().equals("IU");
        assert dto3.getAlbumCount() == 2;
    }

    @Test
    void testIncreaseLikeCount() {
        // Given
        Song song = Song.builder()
            .title("Test Song")
            .artist("Test Artist")
            .album("Test Album")
            .releaseDate(LocalDate.of(2025, 1, 1))
            .likeCount(100L)
            .build();
        Song savedSong = songRepository.save(song).block();

        // When
        boolean result = songRepository.increaseLikeCount(savedSong.getId()).block();

        // Then
        assert result;
        Song updatedSong = songRepository.findById(savedSong.getId()).block();
        assert updatedSong != null;
        assert updatedSong.getLikeCount() == 101L;
    }

    @Test
    void testIncreaseLikeCountNonExistentSong() {
        // When
        boolean result = songRepository.increaseLikeCount(999L).block();

        // Then
        assert !result;
    }
}
