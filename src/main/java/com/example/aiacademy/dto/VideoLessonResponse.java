package com.example.aiacademy.dto;

import com.example.aiacademy.models.VideoLesson;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoLessonResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final Integer durationSeconds;
    private final String videoUrl;
    private final String type = "VIDEO";

    public static VideoLessonResponse fromEntity(VideoLesson vl){
        return VideoLessonResponse.builder()
                .id(vl.getId())
                .title(vl.getTitle())
                .description(vl.getDescription())
                .durationSeconds(vl.getDurationSeconds())
                .videoUrl(vl.getVideoUrl())
                .build();
    }

}
