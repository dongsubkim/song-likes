package com.example.songlikes.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "song_like_histories")
public class SongLikeHistory {
    @Id
    private Long id;
    // 좋아요를 누른 노래의 ID
    private Long songId;
    // 좋아요를 누른 시간
    private LocalDateTime likedAt;
}
