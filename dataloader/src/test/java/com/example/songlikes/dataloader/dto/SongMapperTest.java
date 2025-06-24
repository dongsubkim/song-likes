package com.example.songlikes.dataloader.dto;

import com.example.songlikes.domain.entity.Song;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongMapperTest {
    @Test
    void toEntity_shouldMapAllFieldsCorrectly() {
        // given
        SongJsonDto[] dtos = getTestSongJsonDtos();
        Song[] expectedSongs = getTestSongs();

        for (int i = 0; i < dtos.length; i++) {
            Song actual = SongMapper.toEntity(dtos[i]);
            Song expected = expectedSongs[i];

            assertEquals(expected.getArtist(), actual.getArtist(), "artist mismatch at index " + i);
            assertEquals(expected.getTitle(), actual.getTitle(), "title mismatch at index " + i);
            assertEquals(expected.getText(), actual.getText(), "text mismatch at index " + i);
            assertEquals(expected.getLength(), actual.getLength(), "length mismatch at index " + i);
            assertEquals(expected.getEmotion(), actual.getEmotion(), "emotion mismatch at index " + i);
            assertEquals(expected.getGenre(), actual.getGenre(), "genre mismatch at index " + i);
            assertEquals(expected.getAlbum(), actual.getAlbum(), "album mismatch at index " + i);
            assertEquals(expected.getReleaseDate(), actual.getReleaseDate(), "releaseDate mismatch at index " + i);
            assertEquals(expected.getKey(), actual.getKey(), "key mismatch at index " + i);
            assertEquals(expected.getTempo(), actual.getTempo(), "tempo mismatch at index " + i);
            assertEquals(expected.getLoudness(), actual.getLoudness(), "loudness mismatch at index " + i);
            assertEquals(expected.getTimeSignature(), actual.getTimeSignature(), "timeSignature mismatch at index " + i);
            assertEquals(expected.getIsExplicit(), actual.getIsExplicit(), "explicit mismatch at index " + i);

            assertEquals(expected.getPopularity(), actual.getPopularity(), "popularity mismatch at index " + i);
            assertEquals(expected.getEnergy(), actual.getEnergy(), "energy mismatch at index " + i);
            assertEquals(expected.getDanceability(), actual.getDanceability(), "danceability mismatch at index " + i);
            assertEquals(expected.getPositiveness(), actual.getPositiveness(), "positiveness mismatch at index " + i);
            assertEquals(expected.getSpeechiness(), actual.getSpeechiness(), "speechiness mismatch at index " + i);
            assertEquals(expected.getLiveness(), actual.getLiveness(), "liveness mismatch at index " + i);
            assertEquals(expected.getAcousticness(), actual.getAcousticness(), "acousticness mismatch at index " + i);
            assertEquals(expected.getInstrumentalness(), actual.getInstrumentalness(), "instrumentalness mismatch at index " + i);

            assertEquals(expected.getIsGoodForParty(), actual.getIsGoodForParty(), "goodForParty mismatch at index " + i);
            assertEquals(expected.getIsGoodForWorkStudy(), actual.getIsGoodForWorkStudy(), "goodForWorkStudy mismatch at index " + i);
            assertEquals(expected.getIsGoodForRelaxationMeditation(), actual.getIsGoodForRelaxationMeditation(), "goodForRelaxationMeditation mismatch at index " + i);
            assertEquals(expected.getIsGoodForExercise(), actual.getIsGoodForExercise(), "goodForExercise mismatch at index " + i);
            assertEquals(expected.getIsGoodForRunning(), actual.getIsGoodForRunning(), "goodForRunning mismatch at index " + i);
            assertEquals(expected.getIsGoodForYogaStretching(), actual.getIsGoodForYogaStretching(), "goodForYogaStretching mismatch at index " + i);
            assertEquals(expected.getIsGoodForDriving(), actual.getIsGoodForDriving(), "goodForDriving mismatch at index " + i);
            assertEquals(expected.getIsGoodForSocialGatherings(), actual.getIsGoodForSocialGatherings(), "goodForSocialGatherings mismatch at index " + i);
            assertEquals(expected.getIsGoodForMorningRoutine(), actual.getIsGoodForMorningRoutine(), "goodForMorningRoutine mismatch at index " + i);

            assertEquals(expected.getSimilarArtist1(), actual.getSimilarArtist1(), "similarArtist1 mismatch at index " + i);
            assertEquals(expected.getSimilarSong1(), actual.getSimilarSong1(), "similarSong1 mismatch at index " + i);
            assertEquals(expected.getSimilarityScore1(), actual.getSimilarityScore1(), "similarityScore1 mismatch at index " + i);

            assertEquals(expected.getSimilarArtist2(), actual.getSimilarArtist2(), "similarArtist2 mismatch at index " + i);
            assertEquals(expected.getSimilarSong2(), actual.getSimilarSong2(), "similarSong2 mismatch at index " + i);
            assertEquals(expected.getSimilarityScore2(), actual.getSimilarityScore2(), "similarityScore2 mismatch at index " + i);

            assertEquals(expected.getSimilarArtist3(), actual.getSimilarArtist3(), "similarArtist3 mismatch at index " + i);
            assertEquals(expected.getSimilarSong3(), actual.getSimilarSong3(), "similarSong3 mismatch at index " + i);
            assertEquals(expected.getSimilarityScore3(), actual.getSimilarityScore3(), "similarityScore3 mismatch at index " + i);
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

    private Song[] getTestSongs() {
        Song song1 = new Song();
        song1.setArtist("artist1");
        song1.setTitle("song1");
        song1.setText("text");
        song1.setLength("3:45");
        song1.setEmotion("happy");
        song1.setGenre("Pop");
        song1.setAlbum("album1");
        song1.setReleaseDate(LocalDate.parse("2025-06-30"));
        song1.setKey("C Major");
        song1.setTempo(120.5);
        song1.setLoudness(-5.3);
        song1.setTimeSignature("4/4");
        song1.setIsExplicit(false);
        song1.setPopularity(85);
        song1.setEnergy(75);
        song1.setDanceability(80);
        song1.setPositiveness(90);
        song1.setSpeechiness(10);
        song1.setLiveness(20);
        song1.setAcousticness(15);
        song1.setInstrumentalness(0);
        song1.setIsGoodForParty(true);
        song1.setIsGoodForWorkStudy(false);
        song1.setIsGoodForRelaxationMeditation(true);
        song1.setIsGoodForExercise(false);
        song1.setIsGoodForRunning(true);
        song1.setIsGoodForYogaStretching(false);
        song1.setIsGoodForDriving(true);
        song1.setIsGoodForSocialGatherings(false);
        song1.setIsGoodForMorningRoutine(true);
        song1.setSimilarArtist1("Corey Smith");
        song1.setSimilarSong1("If I Could Do It Again");
        song1.setSimilarityScore1(0.9860607848);
        song1.setSimilarArtist2("Toby Keith");
        song1.setSimilarSong2("Drinks After Work");
        song1.setSimilarityScore2(0.9837194774);
        song1.setSimilarArtist3("Space");
        song1.setSimilarSong3("Neighbourhood");
        song1.setSimilarityScore3(0.9832363508);

        Song song2 = new Song();
        song2.setArtist("artist2");
        song2.setTitle("song2");
        song2.setText("some lyrics here");
        song2.setLength("4:10");
        song2.setEmotion("sad");
        song2.setGenre("Rock");
        song2.setAlbum("album2");
        song2.setReleaseDate(LocalDate.parse("2025-06-01"));
        song2.setKey("G Minor");
        song2.setTempo(98.7);
        song2.setLoudness(-6.8);
        song2.setTimeSignature("3/4");
        song2.setIsExplicit(true);
        song2.setPopularity(72);
        song2.setEnergy(60);
        song2.setDanceability(55);
        song2.setPositiveness(40);
        song2.setSpeechiness(15);
        song2.setLiveness(18);
        song2.setAcousticness(25);
        song2.setInstrumentalness(5);
        song2.setIsGoodForParty(false);
        song2.setIsGoodForWorkStudy(true);
        song2.setIsGoodForRelaxationMeditation(false);
        song2.setIsGoodForExercise(true);
        song2.setIsGoodForRunning(false);
        song2.setIsGoodForYogaStretching(true);
        song2.setIsGoodForDriving(false);
        song2.setIsGoodForSocialGatherings(false);
        song2.setIsGoodForMorningRoutine(true);
        song2.setSimilarArtist1("Sarah McLachlan");
        song2.setSimilarSong1("Angel");
        song2.setSimilarityScore1(0.974523829);
        song2.setSimilarArtist2("Coldplay");
        song2.setSimilarSong2("Fix You");
        song2.setSimilarityScore2(0.959384756);
        song2.setSimilarArtist3("Norah Jones");
        song2.setSimilarSong3("Come Away With Me");
        song2.setSimilarityScore3(0.942176489);

        return new Song[]{song1, song2};
    }
}
