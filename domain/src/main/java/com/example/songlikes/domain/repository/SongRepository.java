package com.example.songlikes.domain.repository;

import com.example.songlikes.domain.entity.Song;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface SongRepository extends ReactiveCrudRepository<Song, Long>, SongRepositoryCustom {
    @Modifying
    @Query("UPDATE songs SET like_count = like_count + 1 WHERE id = :id")
    Mono<Boolean> increaseLikeCount(@Param("id") Long songId);
}
