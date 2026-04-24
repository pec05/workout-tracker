package com.peccio.workout_tracker.controller;

import com.peccio.workout_tracker.dto.WorkoutDTO;
import com.peccio.workout_tracker.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> getMyWorkouts() {
        return ResponseEntity.ok(workoutService.getMyWorkouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getById(id));
    }

    @PostMapping
    public ResponseEntity<WorkoutDTO> create(@RequestBody WorkoutDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> update(@PathVariable Long id, @RequestBody WorkoutDTO dto) {
        return ResponseEntity.ok(workoutService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workoutService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
