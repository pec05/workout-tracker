package com.peccio.workout_tracker.repository;

import com.peccio.workout_tracker.entity.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
    List<WorkoutSet> findByWorkoutExerciseId(Long workoutExerciseId);
}
