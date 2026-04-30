package com.peccio.workout_tracker.repository;

import com.peccio.workout_tracker.entity.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long> {
    List<WorkoutTemplate> findByUserIdOrderByCreatedAtDesc(Long userId);
}
