package com.example.songlikes.domain.repository;

import com.example.songlikes.domain.entity.Song;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SongRepository extends ReactiveCrudRepository<Song, Long> {
}
