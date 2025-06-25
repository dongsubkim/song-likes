package com.example.songlikes.api.service;

import com.example.songlikes.domain.dto.YearArtistAlbumCountDto;
import com.example.songlikes.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class SongSearchService {
    private static final Logger log = LoggerFactory.getLogger(SongSearchService.class);

    private final SongRepository songRepository;

    /**
     * 연도별, 가수별 발매 앨범 수를 조회합니다.
     * releaseYear와 artist가 모두 null인 경우, 모든 연도와 가수의 앨범 수를 조회합니다.
     * releaseYear가 null인 경우, 모든 연도의 앨범 수를 조회합니다.
     * artist가 null인 경우, 모든 가수의 앨범 수를 조회합니다.
     *
     * @param releaseYear 연도
     * @param artist      가수 이름
     * @param page        페이지 번호 (0부터 시작)
     * @param size        페이지 크기
     * @return Flux<YearArtistAlbumCountDto>
     */
    public Flux<YearArtistAlbumCountDto> searchAlbumCountByReleaseYearAndArtist(
        Integer releaseYear,
        String artist,
        int page,
        int size
    ) {
        log.info("앨범 수 조회 요청. releaseYear: {}, artist: {}, page: {}, size: {}", releaseYear, artist, page, size);
        long offset = (long) page * size;
        return songRepository.findYearArtistAlbumCountGrouped(releaseYear, artist, offset, size)
            .doOnComplete(() ->
                log.info("앨범 수 조회 완료. releaseYear: {}, artist: {}, page: {}, size: {}", releaseYear, artist, page, size)
            );
    }
}
