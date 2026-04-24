package com.peccio.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressionDTO {

    private String exerciseName;
    private LocalDateTime date;
    private Double maxWeightOnDay;
}
