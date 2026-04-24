package com.peccio.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecordDTO {

    private String exerciseName;
    private Double maxWeight;
    private Integer reps;
    private LocalDateTime achievedAt;
}
