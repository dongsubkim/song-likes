package com.example.songlikes.domain.repository;

import com.example.songlikes.domain.entity.SongLikeHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SongLikeHistoryRepository extends ReactiveCrudRepository<SongLikeHistory, Long> {
}
