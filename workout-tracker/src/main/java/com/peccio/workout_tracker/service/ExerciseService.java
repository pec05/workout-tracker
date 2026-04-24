package com.peccio.workout_tracker.service;

import com.peccio.workout_tracker.dto.ExerciseDTO;
import com.peccio.workout_tracker.entity.Exercise;
import com.peccio.workout_tracker.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<ExerciseDTO> getAll() {
        return exerciseRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ExerciseDTO getById(Long id) {
        return exerciseRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
    }

    public ExerciseDTO create(ExerciseDTO dto) {
        Exercise exercise = new Exercise();
        exercise.setName(dto.getName());
        exercise.setMuscleGroup(dto.getMuscleGroup());
        exercise.setDescription(dto.getDescription());
        return toDTO(exerciseRepository.save(exercise));
    }

    public ExerciseDTO update(Long id, ExerciseDTO dto) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        exercise.setName(dto.getName());
        exercise.setMuscleGroup(dto.getMuscleGroup());
        exercise.setDescription(dto.getDescription());
        return toDTO(exerciseRepository.save(exercise));
    }

    public void delete(Long id) {
        exerciseRepository.deleteById(id);
    }

    private ExerciseDTO toDTO(Exercise exercise) {
        return new ExerciseDTO(
                exercise.getId(),
                exercise.getName(),
                exercise.getMuscleGroup(),
                exercise.getDescription()
        );
    }
}
