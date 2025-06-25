package com.example.songlikes.api.service;

import com.example.songlikes.domain.entity.SongLikeHistory;
import com.example.songlikes.domain.repository.SongLikeHistoryRepository;
import com.example.songlikes.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SongLikeService {
    private static final Logger log = LoggerFactory.getLogger(SongLikeService.class);

    private final SongRepository songRepository;
    private final SongLikeHistoryRepository songLikeHistoryRepository;
    private final TransactionalOperator transactionalOperator;

    /**
     * Song 의 likeCount 증가하고 SongLikeHistory 저장합니다.
     *
     * @param songId - 좋아요를 누른 Song 의 ID
     * @return Mono<Boolean> - likeCount 증가 성공 여부
     */
    public Mono<Boolean> likeSong(Long songId) {
        log.info("노래 좋아요 수 증가 songId: {}", songId);
        // Song 의 likeCount 증가
        return songRepository.increaseLikeCount(songId)
            .flatMap(incremented -> {
                if (incremented) {
                    // SongLikeHistory 저장
                    SongLikeHistory history = SongLikeHistory.builder()
                        .songId(songId)
                        .likedAt(LocalDateTime.now())
                        .build();
                    return songLikeHistoryRepository.save(history)
                        .thenReturn(true);
                } else {
                    return Mono.just(false);
                }
            })
            .as(transactionalOperator::transactional)
            .doOnSuccess(transactional -> {
                if (transactional) {
                    log.info("노래 좋아요 수 증가 성공 songId: {}", songId);
                } else {
                    log.warn("노래 좋아요 수 증가 실패 songId: {}", songId);
                }
            })
            .doOnError(error -> {
                log.error("노래 좋아요 수 증가 중 오류 발생 songId: {}, error: {}", songId, error.getMessage());
            });
    }
}
