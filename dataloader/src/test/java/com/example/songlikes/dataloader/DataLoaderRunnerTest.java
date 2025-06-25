package com.example.songlikes.dataloader;

import com.example.songlikes.domain.entity.Song;
import com.example.songlikes.domain.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.songlikes.dataloader.DataLoaderRunner.DEFAULT_DATA_PATH;
import static org.mockito.Mockito.*;

class DataLoaderRunnerTest {

    private SongRepository songRepository;
    private ObjectMapper objectMapper;
    private DataLoaderRunner dataLoaderRunner;

    @BeforeEach
    void setUp() {
        songRepository = mock(SongRepository.class);
        objectMapper = spy(ObjectMapper.class);
        dataLoaderRunner = spy(new DataLoaderRunner(songRepository, objectMapper));
    }

    @Test
    void testParseDataPath() {
        // Given
        String[] args = {"custom/path/data.json"};
        // When
        String result = dataLoaderRunner.parseDataPath(args);
        // Then
        assert result.equals("custom/path/data.json");
    }

    @Test
    void testParseDataPathWithDefault() {
        // Given
        String[] args = {};
        // When
        String result = dataLoaderRunner.parseDataPath(args);
        // Then
        assert result.equals(DEFAULT_DATA_PATH);
    }

    @Test
    void testParseDataPathWithNull() {
        // Given
        String[] args = {null};
        // When
        String result = dataLoaderRunner.parseDataPath(args);
        // Then
        assert result.equals(DEFAULT_DATA_PATH);
    }

    @Test
    void testParseDataPathWithBlank() {
        // Given
        String[] args = {" "};
        // When
        String result = dataLoaderRunner.parseDataPath(args);
        // Then
        assert result.equals(DEFAULT_DATA_PATH);
    }

    @Test
    void testParseDataPathWithEmptyString() {
        // Given
        String[] args = {""};
        // When
        String result = dataLoaderRunner.parseDataPath(args);
        // Then
        assert result.equals(DEFAULT_DATA_PATH);
    }

    @Test
    void testGetResource() {
        // Given
        String dataPath = "data/data.json";
        // When
        var resource = dataLoaderRunner.getResource(dataPath);
        // Then
        assert resource instanceof ClassPathResource;
        assert ((ClassPathResource) resource).getPath().equals(dataPath);
        assert resource.getFilename().equals("data.json");
    }

    @Test
    void testGetLinesFromResource() throws IOException {
        // 1. Resource 모킹
        Resource resource = mock(Resource.class);

        // 2. InputStream 준비 (여러 줄 문자열)
        String content = "line1\nline2\nline3";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        // 3. resource.getInputStream() 호출 시 준비한 InputStream 반환하도록 설정
        when(resource.getInputStream()).thenReturn(inputStream);

        // 4. 메서드 실행
        Flux<String> resultFlux = dataLoaderRunner.getLinesFromResource(resource);

        // 5. StepVerifier로 Flux 검증
        StepVerifier.create(resultFlux)
            .expectNext("line1")
            .expectNext("line2")
            .expectNext("line3")
            .verifyComplete();

        // 6. resource.getInputStream()가 호출되었는지 검증 (옵션)
        verify(resource, times(1)).getInputStream();
    }

    @Test
    void saveSongs_withValidAndInvalidJsonLines_savesCorrectSongs() {
        // Given
        String[] testJsons = getTestJson();
        String invalidJson = "invalid json line";
        Flux<String> lines = Flux.just(testJsons[0], invalidJson, testJsons[1]);
        when(songRepository.saveAll(anyList())).thenAnswer(invocation -> {
            List<Song> songsToSave = invocation.getArgument(0);
            return Flux.fromIterable(songsToSave);
        });

        // When
        dataLoaderRunner.saveSongs(lines);

        // Then
        ArgumentCaptor<List<Song>> captor = ArgumentCaptor.forClass(List.class);
        verify(songRepository, atLeastOnce()).saveAll(captor.capture());

        List<List<Song>> allBatchesSaved = captor.getAllValues();
        assert allBatchesSaved.getFirst().size() == 2;
    }

    @Test
    void tesRun() throws Exception {
        // Given
        String[] args = {"data/test_data.json"};
        String testJson = getTestJson()[0];
        Resource resource = mock(Resource.class);
        when(dataLoaderRunner.getResource("data/test_data.json")).thenReturn(resource);
        Flux<String> flux = Flux.just(testJson);
        when(dataLoaderRunner.getLinesFromResource(resource)).thenReturn(flux);
        doNothing().when(dataLoaderRunner).saveSongs(any(Flux.class));
        doNothing().when(dataLoaderRunner).exitApplication();

        // When
        dataLoaderRunner.run(args);

        // Then
        verify(dataLoaderRunner, times(1)).parseDataPath(args);
        verify(dataLoaderRunner, times(1)).getResource("data/test_data.json");
        verify(dataLoaderRunner, times(1)).getLinesFromResource(resource);
        verify(dataLoaderRunner, times(1)).saveSongs(any(Flux.class));
        verify(dataLoaderRunner, times(1)).exitApplication();
    }

    private String[] getTestJson() {
        String validJson1 = """
            {
              "Artist(s)": "artist1",
              "song": "song1",
              "text": "text",
              "Length": "3:45",
              "emotion": "happy",
              "Genre": "Pop",
              "Album": "album1",
              "Release Date": "2025-06-30",
              "Key": "C Major",
              "Tempo": 120.5,
              "Loudness (db)": -5.3,
              "Time signature": "4/4",
              "Explicit": "No",
              "Popularity": 85,
              "Energy": 75,
              "Danceability": 80,
              "Positiveness": 90,
              "Speechiness": 10,
              "Liveness": 20,
              "Acousticness": 15,
              "Instrumentalness": 0,
              "Good for Party": 1,
              "Good for Work/Study": 0,
              "Good for Relaxation/Meditation": 1,
              "Good for Exercise": 0,
              "Good for Running": 1,
              "Good for Yoga/Stretching": 0,
              "Good for Driving": 1,
              "Good for Social Gatherings": 1,
              "Good for Morning Routine": 0,
              "Similar Songs": [
                {
                  "Similar Artist 1": "Corey Smith",
                  "Similar Song 1": "If I Could Do It Again",
                  "Similarity Score": 0.9860607848
                },
                {
                  "Similar Artist 2": "Toby Keith",
                  "Similar Song 2": "Drinks After Work",
                  "Similarity Score": 0.9837194774
                },
                {
                  "Similar Artist 3": "Space",
                  "Similar Song 3": "Neighbourhood",
                  "Similarity Score": 0.9832363508
                }
              ]
            }
            """;
        String validJson2 = """
            {
              "Artist(s)": "artist2",
              "song": "song2",
              "text": "some lyrics here",
              "Length": "4:10",
              "emotion": "sad",
              "Genre": "Rock",
              "Album": "album2",
              "Release Date": "2025-06-01",
              "Key": "G Minor",
              "Tempo": 98.7,
              "Loudness (db)": -6.8,
              "Time signature": "3/4",
              "Explicit": "Yes",
              "Popularity": 72,
              "Energy": 60,
              "Danceability": 55,
              "Positiveness": 40,
              "Speechiness": 15,
              "Liveness": 18,
              "Acousticness": 25,
              "Instrumentalness": 5,
              "Good for Party": 0,
              "Good for Work/Study": 1,
              "Good for Relaxation/Meditation": 0,
              "Good for Exercise": 1,
              "Good for Running": 0,
              "Good for Yoga/Stretching": 1,
              "Good for Driving": 0,
              "Good for Social Gatherings": 0,
              "Good for Morning Routine": 1,
              "Similar Songs": [
                {
                  "Similar Artist 1": "Sarah McLachlan",
                  "Similar Song 1": "Angel",
                  "Similarity Score": 0.974523829
                },
                {
                  "Similar Artist 2": "Coldplay",
                  "Similar Song 2": "Fix You",
                  "Similarity Score": 0.959384756
                },
                {
                  "Similar Artist 3": "Norah Jones",
                  "Similar Song 3": "Come Away With Me",
                  "Similarity Score": 0.942176489
                }
              ]
            }
            """;
        return new String[]{validJson1, validJson2};
    }

}
