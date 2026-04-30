package com.peccio.workout_tracker.service;

import com.peccio.workout_tracker.dto.WorkoutDTO;
import com.peccio.workout_tracker.dto.WorkoutTemplateDTO;
import com.peccio.workout_tracker.dto.WorkoutTemplateExerciseDTO;
import com.peccio.workout_tracker.dto.WorkoutTemplateSetDTO;
import com.peccio.workout_tracker.entity.*;
import com.peccio.workout_tracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkoutTemplateService {

    private final WorkoutTemplateRepository templateRepository;
    private final WorkoutTemplateExerciseRepository templateExerciseRepository;
    private final WorkoutTemplateSetRepository templateSetRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutSetRepository workoutSetRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 1. Save a workout as a template
    public WorkoutTemplateDTO saveAsTemplate(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        WorkoutTemplate template = new WorkoutTemplate();
        template.setName(workout.getName());
        template.setNotes(workout.getNotes());
        template.setCreatedAt(LocalDateTime.now());
        template.setUser(getCurrentUser());
        WorkoutTemplate savedTemplate = templateRepository.save(template);

        workout.getExercises().forEach(we -> {
            WorkoutTemplateExercise te = new WorkoutTemplateExercise();
            te.setTemplate(savedTemplate);
            te.setExercise(we.getExercise());
            te.setOrderIndex(we.getOrderIndex());
            WorkoutTemplateExercise savedTe = templateExerciseRepository.save(te);

            we.getSets().forEach(set -> {
                WorkoutTemplateSet ts = new WorkoutTemplateSet();
                ts.setTemplateExercise(savedTe);
                ts.setSetNumber(set.getSetNumber());
                ts.setReps(set.getReps());
                ts.setWeight(set.getWeight());
                templateSetRepository.save(ts);
            });
        });

        return toDTO(templateRepository.findById(savedTemplate.getId()).get());
    }

    // 2. Get all templates for current user
    public List<WorkoutTemplateDTO> getMyTemplates() {
        User user = getCurrentUser();
        return templateRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // 3. Create a workout from a template
    public WorkoutDTO useTemplate(Long templateId) {
        WorkoutTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        Workout workout = new Workout();
        workout.setName(template.getName());
        workout.setNotes(template.getNotes());
        workout.setDate(LocalDateTime.now());
        workout.setUser(getCurrentUser());
        Workout savedWorkout = workoutRepository.save(workout);

        template.getExercises().forEach(te -> {
            WorkoutExercise we = new WorkoutExercise();
            we.setWorkout(savedWorkout);
            we.setExercise(te.getExercise());
            we.setOrderIndex(te.getOrderIndex());
            WorkoutExercise savedWe = workoutExerciseRepository.save(we);

            te.getSets().forEach(ts -> {
                WorkoutSet set = new WorkoutSet();
                set.setWorkoutExercise(savedWe);
                set.setSetNumber(ts.getSetNumber());
                set.setReps(ts.getReps());
                set.setWeight(ts.getWeight());
                workoutSetRepository.save(set);
            });
        });

        return new WorkoutDTO(
                savedWorkout.getId(),
                savedWorkout.getName(),
                savedWorkout.getDate(),
                savedWorkout.getNotes(),
                savedWorkout.getUser().getId()
        );
    }

    // 4. Delete a template
    public void delete(Long id) {
        templateRepository.deleteById(id);
    }

    private WorkoutTemplateDTO toDTO(WorkoutTemplate template) {
        List<WorkoutTemplateExerciseDTO> exercises = template.getExercises().stream()
                .map(te -> new WorkoutTemplateExerciseDTO(
                        te.getId(),
                        te.getExercise().getId(),
                        te.getExercise().getName(),
                        te.getOrderIndex(),
                        te.getSets().stream()
                                .map(ts -> new WorkoutTemplateSetDTO(
                                        ts.getId(),
                                        ts.getSetNumber(),
                                        ts.getReps(),
                                        ts.getWeight()
                                )).toList()
                )).toList();

        return new WorkoutTemplateDTO(
                template.getId(),
                template.getName(),
                template.getNotes(),
                template.getCreatedAt(),
                exercises
        );
    }
}
