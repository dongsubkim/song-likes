package com.example.songlikes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearArtistAlbumCountDto {
    private Integer releaseYear;
    private String artist;
    private Long albumCount;
}
