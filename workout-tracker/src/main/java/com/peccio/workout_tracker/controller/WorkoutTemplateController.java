package com.peccio.workout_tracker.controller;

import com.peccio.workout_tracker.dto.WorkoutDTO;
import com.peccio.workout_tracker.dto.WorkoutTemplateDTO;
import com.peccio.workout_tracker.service.WorkoutTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class WorkoutTemplateController {

    private final WorkoutTemplateService templateService;

    @PostMapping("/from-workout/{workoutId}")
    public ResponseEntity<WorkoutTemplateDTO> saveAsTemplate(@PathVariable Long workoutId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(templateService.saveAsTemplate(workoutId));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutTemplateDTO>> getMyTemplates() {
        return ResponseEntity.ok(templateService.getMyTemplates());
    }

    @PostMapping("/{id}/use")
    public ResponseEntity<WorkoutDTO> useTemplate(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(templateService.useTemplate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        templateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

