package com.peccio.workout_tracker.repository;

import com.peccio.workout_tracker.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByNameContainingIgnoreCase(String name);
}
