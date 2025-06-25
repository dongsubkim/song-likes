package com.example.songlikes.domain.repository;


import com.example.songlikes.domain.dto.YearArtistAlbumCountDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
@RequiredArgsConstructor
public class SongRepositoryCustomImpl implements SongRepositoryCustom {
    private static final Logger log = LoggerFactory.getLogger(SongRepositoryCustomImpl.class);
    private final DatabaseClient databaseClient;

    @Override
    public Flux<YearArtistAlbumCountDto> findYearArtistAlbumCountGrouped(Integer releaseYear, String artist, long offset, int size) {
        String query = getYearArtistAlbumCountQueryString(releaseYear, artist, offset, size);
        log.debug("실행할 SQL: {}", query);
        log.debug("바인딩 파라미터 - releaseYear: {}, artist: {}, offset: {}, size: {}", releaseYear, artist, offset, size);

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query);
        if (releaseYear != null) spec = spec.bind("releaseYear", releaseYear);
        if (artist != null) spec = spec.bind("artist", artist);
        spec = spec.bind("offset", offset).bind("size", size);

        return spec.map((row, meta) -> new YearArtistAlbumCountDto(
                row.get("releaseYear", Integer.class),
                row.get("artist", String.class),
                row.get("albumCount", Long.class)
            )).all()
            .doOnSubscribe(s -> log.info("연도/가수별 앨범수 집계 쿼리 실행 시작"))
            .doOnError(e -> log.error("연도/가수별 앨범수 집계 쿼리 실행 중 오류", e))
            .doOnComplete(() -> log.info("연도/가수별 앨범수 집계 쿼리 실행 완료"));
    }

    protected String getYearArtistAlbumCountQueryString(Integer releaseYear, String artist, long offset, int size) {
        String base = """
                SELECT release_year AS releaseYear, artist, COUNT(DISTINCT album) AS albumCount
                  FROM songs
                 WHERE album IS NOT NULL
            """;
        StringBuilder sb = new StringBuilder(base);
        if (releaseYear != null) {
            // releaseYear 가 null 이 아니면 release_year = :releaseYear 조건을 추가
            sb.append(" AND release_year = :releaseYear");
        } else {
            // releaseYear 가 null 이면 release_year IS NOT NULL 조건을 추가
            sb.append(" AND release_year IS NOT NULL");
        }
        if (artist != null) {
            // artist 가 null 이 아니면 artist = :artist 조건을 추가
            sb.append(" AND artist = :artist");
        } else {
            // artist 가 null 이면 artist IS NOT NULL 조건을 추가
            sb.append(" AND artist IS NOT NULL");
        }
        sb.append(" GROUP BY artist, release_year ORDER BY release_year, artist");
        if (offset >= 0 && size > 0) {
            sb.append(" LIMIT :size OFFSET :offset");
        } else if (size > 0) {
            sb.append(" LIMIT :size");
        }
        String queryString = sb.toString();
        log.debug("동적으로 생성된 SQL: {}", queryString);
        return queryString;
    }
}
