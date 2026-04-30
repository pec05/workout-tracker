package com.peccio.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutTemplateDTO {
    private Long id;
    private String name;
    private String notes;
    private LocalDateTime createdAt;
    private List<WorkoutTemplateExerciseDTO> exercises;
}
