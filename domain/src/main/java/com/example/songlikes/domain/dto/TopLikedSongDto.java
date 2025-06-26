package com.example.songlikes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopLikedSongDto {
    // song id
    private Long songId;
    // like count 증가량
    private Long likeInc;
}
