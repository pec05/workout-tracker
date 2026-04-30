package com.peccio.workout_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workout_template_sets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutTemplateSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer setNumber;
    private Integer reps;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "template_exercise_id")
    private WorkoutTemplateExercise templateExercise;
}
