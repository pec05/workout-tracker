package com.peccio.workout_tracker.repository;

import com.peccio.workout_tracker.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
    List<WorkoutExercise> findByWorkoutIdOrderByOrderIndex(Long workoutId);
}
