package com.peccio.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseDTO {

    private Long id;
    private Long workoutId;
    private Long exerciseId;
    private String exerciseName;
    private Integer orderIndex;
    private List<WorkoutSetDTO> sets;
}
