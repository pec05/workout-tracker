package com.peccio.workout_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "workout_template_exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutTemplateExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private WorkoutTemplate template;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Integer orderIndex;

    @OneToMany(mappedBy = "templateExercise", cascade = CascadeType.ALL)
    private List<WorkoutTemplateSet> sets = new ArrayList<>();
}
