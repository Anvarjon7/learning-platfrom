package com.example.aiacademy.dto;

import com.example.aiacademy.models.Module;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ModuleResponse {

    private Long id;
    private String title;
    private String description;
    private List<LessonResponse> lessons;

    public static ModuleResponse fromEntity(Module module) {
        return ModuleResponse.builder()
                .id(module.getId())
                .title(module.getDescription())
                .description(module.getDescription())
                .build();
    }
}
