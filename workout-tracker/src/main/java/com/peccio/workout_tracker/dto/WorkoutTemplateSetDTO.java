package com.peccio.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutTemplateSetDTO {
    private Long id;
    private Integer setNumber;
    private Integer reps;
    private Double weight;
}
