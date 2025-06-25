package com.example.songlikes.api.service;

import com.example.songlikes.domain.entity.SongLikeHistory;
import com.example.songlikes.domain.repository.SongLikeHistoryRepository;
import com.example.songlikes.domain.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SongLikeServiceTest {
    @Mock
    private SongRepository songRepository;
    @Mock
    private SongLikeHistoryRepository songLikeHistoryRepository;
    @Mock
    private TransactionalOperator transactionalOperator;
    @InjectMocks
    private SongLikeService songLikeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikeSong_success() {
        // Given
        Long songId = 1L;
        when(songRepository.increaseLikeCount(songId)).thenReturn(Mono.just(true));
        when(songLikeHistoryRepository.save(any())).thenReturn(Mono.just(new SongLikeHistory()));
        when(transactionalOperator.transactional(any(Mono.class))).thenAnswer(invocation -> {
            Mono<Boolean> mono = invocation.getArgument(0);
            return mono;
        });

        // When
        ArgumentCaptor<SongLikeHistory> captor = ArgumentCaptor.forClass(SongLikeHistory.class);

        StepVerifier.create(songLikeService.likeSong(songId))
            .expectNext(true)
            .verifyComplete();

        // Then
        verify(songRepository).increaseLikeCount(songId);
        verify(songLikeHistoryRepository).save(captor.capture());
        SongLikeHistory called = captor.getValue();
        assert called.getSongId().equals(songId);

        verify(transactionalOperator).transactional(any(Mono.class));
    }

    @Test
    void testLikeSong_failure() {
        // Given
        Long songId = 2L;
        when(songRepository.increaseLikeCount(songId)).thenReturn(Mono.just(false));
        when(transactionalOperator.transactional(any(Mono.class))).thenAnswer(invocation -> {
            Mono<Boolean> mono = invocation.getArgument(0);
            return mono;
        });

        // When
        StepVerifier.create(songLikeService.likeSong(songId))
            .expectNext(false)
            .verifyComplete();

        // Then
        verify(songRepository).increaseLikeCount(songId);
        // verify SongLikeHistoryRepository is not called
        verify(songLikeHistoryRepository, never()).save(any(SongLikeHistory.class));
    }
}
