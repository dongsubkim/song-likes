package com.example.songlikes.dataloader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record SongJsonDto(
    @JsonProperty("Artist(s)") String artists,
    @JsonProperty("song") String song,
    @JsonProperty("text") String text,
    @JsonProperty("Length") String length,
    @JsonProperty("emotion") String emotion,
    @JsonProperty("Genre") String genre,
    @JsonProperty("Album") String album,
    @JsonProperty("Release Date") String releaseDate,
    @JsonProperty("Key") String key,
    @JsonProperty("Tempo") double tempo,
    @JsonProperty("Loudness (db)") double loudness,
    @JsonProperty("Time signature") String timeSignature,
    @JsonProperty("Explicit") String explicit,
    @JsonProperty("Popularity") int popularity,
    @JsonProperty("Energy") int energy,
    @JsonProperty("Danceability") int danceability,
    @JsonProperty("Positiveness") int positiveness,
    @JsonProperty("Speechiness") int speechiness,
    @JsonProperty("Liveness") int liveness,
    @JsonProperty("Acousticness") int acousticness,
    @JsonProperty("Instrumentalness") int instrumentalness,
    @JsonProperty("Good for Party") int goodForParty,
    @JsonProperty("Good for Work/Study") int goodForWorkStudy,
    @JsonProperty("Good for Relaxation/Meditation") int goodForRelaxationMeditation,
    @JsonProperty("Good for Exercise") int goodForExercise,
    @JsonProperty("Good for Running") int goodForRunning,
    @JsonProperty("Good for Yoga/Stretching") int goodForYogaStretching,
    @JsonProperty("Good for Driving") int goodForDriving,
    @JsonProperty("Good for Social Gatherings") int goodForSocialGatherings,
    @JsonProperty("Good for Morning Routine") int goodForMorningRoutine,
    @JsonProperty("Similar Songs") List<Map<String, Object>> similarSongs
) {}
