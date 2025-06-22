package com.example.songlikes.domain.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "songs")
public class Song {

    @Id
    private Long id;
    private String artist;
    private String title;
    private String text;
    private String length;
    private String emotion;
    private String genre;
    private String album;
    private LocalDate releaseDate;

    // features
    private String key;
    private Double tempo;
    private Double loudness;
    private String timeSignature;
    private Boolean isExplicit;
    private Integer popularity;
    private Integer Energy;
    private Integer Danceability;
    private Integer Positiveness;
    private Integer Speechiness;
    private Integer Liveness;
    private Integer Acousticness;
    private Integer Instrumentalness;

    // contextual tags. 추후 분리 가능성 있음
    private Boolean isGoodForParty;
    private Boolean isGoodForWorkStudy;
    private Boolean isGoodForRelaxationMeditation;
    private Boolean isGoodForExercise;
    private Boolean isGoodForRunning;
    private Boolean isGoodForYogaStretching;
    private Boolean isGoodForDriving;
    private Boolean isGoodForSocialGatherings;
    private Boolean isGoodForMorningRoutine;

    // Similiarity score, artist, song 은 추후 별개의 테이블로 분리할 수 있음
    private String similarArtist1;
    private String similarSong1;
    private Double similarityScore1;
    private String similarArtist2;
    private String similarSong2;
    private Double similarityScore2;
    private String similarArtist3;
    private String similarSong3;
    private Double similarityScore3;
}
