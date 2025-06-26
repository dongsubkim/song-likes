package com.example.songlikes.domain.repository;

import com.example.songlikes.domain.dto.TopLikedSongDto;
import com.example.songlikes.domain.entity.SongLikeHistory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SongLikeHistoryRepository extends ReactiveCrudRepository<SongLikeHistory, Long> {
    @Query("""
            SELECT song_id, COUNT(*) AS like_inc
            FROM song_like_histories
            WHERE liked_at >= NOW() - INTERVAL '1' hour
            GROUP BY song_id
            ORDER BY like_inc DESC
            LIMIT 10
        """)
    Flux<TopLikedSongDto> findTop10LikedSongInLastHour();
}
