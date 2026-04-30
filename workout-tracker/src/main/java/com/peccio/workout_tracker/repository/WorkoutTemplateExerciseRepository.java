package com.peccio.workout_tracker.repository;

import com.peccio.workout_tracker.entity.WorkoutTemplateExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutTemplateExerciseRepository extends JpaRepository<WorkoutTemplateExercise,Long> {
}
