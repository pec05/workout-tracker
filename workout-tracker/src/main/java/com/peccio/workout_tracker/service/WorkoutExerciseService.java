package com.peccio.workout_tracker.service;

import com.peccio.workout_tracker.dto.WorkoutExerciseDTO;
import com.peccio.workout_tracker.dto.WorkoutSetDTO;
import com.peccio.workout_tracker.entity.Exercise;
import com.peccio.workout_tracker.entity.Workout;
import com.peccio.workout_tracker.entity.WorkoutExercise;
import com.peccio.workout_tracker.entity.WorkoutSet;
import com.peccio.workout_tracker.repository.ExerciseRepository;
import com.peccio.workout_tracker.repository.WorkoutExerciseRepository;
import com.peccio.workout_tracker.repository.WorkoutRepository;
import com.peccio.workout_tracker.repository.WorkoutSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetRepository workoutSetRepository;

    public WorkoutExerciseDTO addExerciseToWorkout(WorkoutExerciseDTO dto) {
        Workout workout = workoutRepository.findById(dto.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkout(workout);
        workoutExercise.setExercise(exercise);
        workoutExercise.setOrderIndex(dto.getOrderIndex());

        WorkoutExercise saved = workoutExerciseRepository.save(workoutExercise);

        if (dto.getSets() != null) {
            List<WorkoutSet> sets = dto.getSets().stream().map(setDTO -> {
                WorkoutSet set = new WorkoutSet();
                set.setSetNumber(setDTO.getSetNumber());
                set.setReps(setDTO.getReps());
                set.setWeight(setDTO.getWeight());
                set.setWorkoutExercise(saved);
                return set;
            }).toList();
            workoutSetRepository.saveAll(sets);
            saved.setSets(sets);
        }

        return toDTO(saved);
    }

    public List<WorkoutExerciseDTO> getByWorkoutId(Long workoutId) {
        return workoutExerciseRepository.findByWorkoutIdOrderByOrderIndex(workoutId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void delete(Long id) {
        workoutExerciseRepository.deleteById(id);
    }

    private WorkoutExerciseDTO toDTO(WorkoutExercise we) {
        List<WorkoutSetDTO> sets = we.getSets() == null ? List.of() :
                we.getSets().stream().map(s -> new WorkoutSetDTO(
                        s.getId(),
                        s.getSetNumber(),
                        s.getReps(),
                        s.getWeight()
                )).toList();

        return new WorkoutExerciseDTO(
                we.getId(),
                we.getWorkout().getId(),
                we.getExercise().getId(),
                we.getExercise().getName(),
                we.getOrderIndex(),
                sets
        );
    }
}
