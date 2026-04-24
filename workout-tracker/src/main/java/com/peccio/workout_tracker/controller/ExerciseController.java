package com.peccio.workout_tracker.controller;

import com.peccio.workout_tracker.dto.ExerciseDTO;
import com.peccio.workout_tracker.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAll() {
        return ResponseEntity.ok(exerciseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> create(@RequestBody ExerciseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> update(@PathVariable Long id, @RequestBody ExerciseDTO dto) {
        return ResponseEntity.ok(exerciseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
