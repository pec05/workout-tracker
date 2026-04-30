package com.peccio.workout_tracker.repository;

import com.peccio.workout_tracker.entity.WorkoutTemplateSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutTemplateSetRepository extends JpaRepository<WorkoutTemplateSet, Long> {
}
