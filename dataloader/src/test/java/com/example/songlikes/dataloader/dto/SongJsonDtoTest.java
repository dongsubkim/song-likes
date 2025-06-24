package com.example.songlikes.dataloader.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SongJsonDtoTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSongJsonDtoMapping() {
        var jsonStrings = getTestJson();
        var expected = getTestSongJsonDtos();

        // jsonStrings 를 objectMapper 로 파싱한 결과가 expected 와 동일한지 확인
        for (int i = 0; i < jsonStrings.length; i++) {
            try {
                SongJsonDto dto = objectMapper.readValue(jsonStrings[i], SongJsonDto.class);
                assertThat(dto).usingRecursiveComparison().isEqualTo(expected[i]);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON parsing failed for index " + i, e);
            }
        }
    }


    private SongJsonDto[] getTestSongJsonDtos() {
        SongJsonDto dto1 = new SongJsonDto(
            "artist1",
            "song1",
            "text",
            "3:45",
            "happy",
            "Pop",
            "album1",
            "2025-06-30",
            "C Major",
            120.5,
            -5.3,
            "4/4",
            "No",
            85,
            75,
            80,
            90,
            10,
            20,
            15,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            1,
            List.of(
                Map.of(
                    "Similar Artist 1", "Corey Smith",
                    "Similar Song 1", "If I Could Do It Again",
                    "Similarity Score", 0.9860607848
                ),
                Map.of(
                    "Similar Artist 2", "Toby Keith",
                    "Similar Song 2", "Drinks After Work",
                    "Similarity Score", 0.9837194774
                ),
                Map.of(
                    "Similar Artist 3", "Space",
                    "Similar Song 3", "Neighbourhood",
                    "Similarity Score", 0.9832363508
                )
            )
        );
        SongJsonDto dto2 = new SongJsonDto(
            "artist2",
            "song2",
            "some lyrics here",
            "4:10",
            "sad",
            "Rock",
            "album2",
            "2025-06-01",
            "G Minor",
            98.7,
            -6.8,
            "3/4",
            "Yes",
            72,
            60,
            55,
            40,
            15,
            18,
            25,
            5,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            0,
            1,
            List.of(
                Map.of(
                    "Similar Artist 1", "Sarah McLachlan",
                    "Similar Song 1", "Angel",
                    "Similarity Score", 0.974523829
                ),
                Map.of(
                    "Similar Artist 2", "Coldplay",
                    "Similar Song 2", "Fix You",
                    "Similarity Score", 0.959384756
                ),
                Map.of(
                    "Similar Artist 3", "Norah Jones",
                    "Similar Song 3", "Come Away With Me",
                    "Similarity Score", 0.942176489
                )
            )
        );
        return new SongJsonDto[]{dto1, dto2};
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
              "Good for Social Gatherings": 0,
              "Good for Morning Routine": 1,
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
