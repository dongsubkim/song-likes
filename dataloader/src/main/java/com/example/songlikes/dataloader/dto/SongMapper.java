package com.example.songlikes.dataloader.dto;

import com.example.songlikes.domain.entity.Song;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SongMapper {


    public static Song toEntity(SongJsonDto dto) {
        if (dto == null) return null;

        Song.SongBuilder builder = Song.builder();

        builder.artist(dto.artists());
        builder.title(dto.song());
        builder.text(dto.text());
        builder.length(dto.length());
        builder.emotion(dto.emotion());
        builder.genre(dto.genre());
        builder.album(dto.album());
        builder.releaseDate(LocalDate.parse(dto.releaseDate()));
        builder.key(dto.key());
        builder.tempo(dto.tempo());
        builder.loudness(dto.loudness());
        builder.timeSignature(dto.timeSignature());
        builder.isExplicit(parseExplicit(dto.explicit()));

        builder.popularity(dto.popularity());
        builder.energy(dto.energy());
        builder.danceability(dto.danceability());
        builder.positiveness(dto.positiveness());
        builder.speechiness(dto.speechiness());
        builder.liveness(dto.liveness());
        builder.acousticness(dto.acousticness());
        builder.instrumentalness(dto.instrumentalness());

        builder.isGoodForParty(toBoolean(dto.goodForParty()));
        builder.isGoodForWorkStudy(toBoolean(dto.goodForWorkStudy()));
        builder.isGoodForRelaxationMeditation(toBoolean(dto.goodForRelaxationMeditation()));
        builder.isGoodForExercise(toBoolean(dto.goodForExercise()));
        builder.isGoodForRunning(toBoolean(dto.goodForRunning()));
        builder.isGoodForYogaStretching(toBoolean(dto.goodForYogaStretching()));
        builder.isGoodForDriving(toBoolean(dto.goodForDriving()));
        builder.isGoodForSocialGatherings(toBoolean(dto.goodForSocialGatherings()));
        builder.isGoodForMorningRoutine(toBoolean(dto.goodForMorningRoutine()));

        // Similar Songs 처리
        List<Map<String, Object>> similarSongs = dto.similarSongs();
        if (similarSongs != null) {
            for (int i = 0; i < similarSongs.size() && i < 3; i++) {
                Map<String, Object> item = similarSongs.get(i);
                // 키들이 "Similar Artist 1", "Similar Song 1", "Similarity Score" 이런 식으로 되어있음
                // index i=0 → suffix "1", i=1 → "2", i=2 → "3" 으로 매핑

                String suffix = String.valueOf(i + 1);

                String artistKey = "Similar Artist " + suffix;
                String songKey = "Similar Song " + suffix;
                String scoreKey = "Similarity Score";

                String simArtist = (String) item.get(artistKey);
                String simSong = (String) item.get(songKey);
                Double simScore = null;
                if (item.get(scoreKey) instanceof Number n) {
                    simScore = n.doubleValue();
                }

                switch (i) {
                    case 0 -> {
                        builder.similarArtist1(simArtist);
                        builder.similarSong1(simSong);
                        builder.similarityScore1(simScore);
                    }
                    case 1 -> {
                        builder.similarArtist2(simArtist);
                        builder.similarSong2(simSong);
                        builder.similarityScore2(simScore);
                    }
                    case 2 -> {
                        builder.similarArtist3(simArtist);
                        builder.similarSong3(simSong);
                        builder.similarityScore3(simScore);
                    }
                }
            }
        }

        return builder.build();
    }

    private static Boolean parseExplicit(String explicit) {
        if (explicit == null) return null;
        explicit = explicit.trim().toLowerCase();
        return switch (explicit) {
            case "yes" -> true;
            case "no" -> false;
            default -> null;
        };
    }

    private static Boolean toBoolean(Integer val) {
        if (val == null) return null;
        return val == 1;
    }
}
