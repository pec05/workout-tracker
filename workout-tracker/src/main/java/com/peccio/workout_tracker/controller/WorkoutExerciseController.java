package com.peccio.workout_tracker.controller;

import com.peccio.workout_tracker.dto.WorkoutExerciseDTO;
import com.peccio.workout_tracker.service.WorkoutExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-exercises")
@RequiredArgsConstructor
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    @PostMapping
    public ResponseEntity<WorkoutExerciseDTO> addExerciseToWorkout(@RequestBody WorkoutExerciseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workoutExerciseService.addExerciseToWorkout(dto));
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<List<WorkoutExerciseDTO>> getByWorkoutId(@PathVariable Long workoutId) {
        return ResponseEntity.ok(workoutExerciseService.getByWorkoutId(workoutId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workoutExerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
