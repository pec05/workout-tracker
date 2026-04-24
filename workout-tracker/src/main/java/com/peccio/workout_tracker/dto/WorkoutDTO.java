package com.peccio.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDTO {

    private Long id;
    private String name;
    private LocalDateTime date;
    private String notes;
    private Long userId;
}
