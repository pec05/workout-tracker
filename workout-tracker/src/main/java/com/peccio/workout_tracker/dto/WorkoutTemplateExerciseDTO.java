package com.peccio.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutTemplateExerciseDTO {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private Integer orderIndex;
    private List<WorkoutTemplateSetDTO> sets;
}
