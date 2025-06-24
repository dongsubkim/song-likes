package com.example.songlikes.dataloader;

import com.example.songlikes.dataloader.dto.SongJsonDto;
import com.example.songlikes.dataloader.dto.SongMapper;
import com.example.songlikes.domain.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataLoaderRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoaderRunner.class);

    private final SongRepository songRepository;
    private final ObjectMapper objectMapper;

    private static final int BATCH_SIZE = 500;

    public DataLoaderRunner(SongRepository songRepository, ObjectMapper objectMapper) {
        this.songRepository = songRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("데이터 로딩 시작합니다.");
        // args[0] 에 파일 경로가 들어오면 해당 경로를 사용하고, 없으면 기본 경로를 사용
        String dataPath = parseDataPath(args);
        log.info("다음 파일에서부터 json 을 읽어옵니다: {}", dataPath);

        // ClassPathResource 를 사용하여 리소스 파일을 읽어옴
        Resource resource = getResource(dataPath);

        // BufferedReader 를 사용하여 파일의 각 줄을 읽어옴
        Flux<String> lines = getLinesFromResource(resource);

        // 각 줄을 JSON으로 파싱하고, Song 엔티티로 변환하여 저장
        saveSongs(lines);

        // 모든 작업이 완료되면 애플리케이션 종료
        exitApplication();
    }

    public String parseDataPath(String[] args) {
        String dataPath = "data/data.json";

        if (args != null && args.length > 0 && args[0] != null && !args[0].isBlank()) {
            dataPath = args[0];
        }

        return dataPath;  // 기본 경로
    }

    public Resource getResource(String dataPath) {
        return new ClassPathResource(dataPath);
    }

    public Flux<String> getLinesFromResource(Resource resource) {
        return Flux.using(
            () -> new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)),
            br -> Flux.fromStream(br.lines()),
            br -> {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("파일 close 중 에러가 발생했습니다.", e);
                }
            }
        );
    }

    public void saveSongs(Flux<String> lines) {
        lines
            .mapNotNull(line -> {
                try {
                    return objectMapper.readValue(line, SongJsonDto.class);
                } catch (Exception e) {
                    log.error("JSON 파싱 오류 - 무시하고 진행: {}", line, e);
                    return null;
                }
            })
            .map(SongMapper::toEntity)
            .buffer(BATCH_SIZE)
            .concatMap(batch -> songRepository.saveAll(batch).collectList())
            .doOnNext(batch -> log.info("Song {}곡 저장 완료.", batch.size()))
            .doOnError(e -> log.error("Song 저장 중 에러 발생", e))
            .blockLast();
    }

    public void exitApplication() {
        log.info("데이터 로딩 완료. 애플리케이션 종료 중.");
        System.exit(0);
    }
}
