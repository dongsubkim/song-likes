package com.example.songlikes.domain.repository;

import com.example.songlikes.domain.entity.SongLikeHistory;
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

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class SongLikeHistoryRepositoryIntegrationTest {
    // region testcontainers 설정
    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url",
            () -> "r2dbc:postgresql://" + postgres.getHost() + ":" + postgres.getFirstMappedPort() + "/testdb");
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);

        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    // endregion

    @Autowired
    SongLikeHistoryRepository songLikeHistoryRepository;

    @BeforeEach
    void clean() {
        songLikeHistoryRepository.deleteAll().block();
    }

    @Test
    void testFindTop10LikedSongInLastHour() {
        // Given
        var now = LocalDateTime.now();
        for (int i = 1; i <= 12; i++) {
            for (int j = 0; j < i; j++) {
                SongLikeHistory songLikeHistory = SongLikeHistory.builder()
                    .songId((long) i)
                    .likedAt(now.minusMinutes(i * 5).minusSeconds(j * 5))
                    .build();
                songLikeHistoryRepository.save(songLikeHistory).block();
            }
        }

        // When
        var topLikedSongs = songLikeHistoryRepository.findTop10LikedSongInLastHour().collectList().block();

        // Then
        assert topLikedSongs != null;
        assert topLikedSongs.size() == 10;
        for (int i = 11; i > 1; i--) {
            var song = topLikedSongs.get(11 - i);
            assert song.getSongId() == i;
            assert song.getLikeInc() == (long) i;
        }
        //  1번과 12번 노래는 포함되지 않아야 함
        List<Long> songIds = topLikedSongs.stream().map(song -> song.getSongId()).toList();
        assert !songIds.contains(1L);
        assert !songIds.contains(12L);
    }

}
