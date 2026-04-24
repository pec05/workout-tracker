package com.peccio.workout_tracker.controller;

import com.peccio.workout_tracker.dto.PersonalRecordDTO;
import com.peccio.workout_tracker.dto.ProgressionDTO;
import com.peccio.workout_tracker.dto.VolumeDTO;
import com.peccio.workout_tracker.dto.WorkoutFrequencyDTO;
import com.peccio.workout_tracker.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/volume")
    public ResponseEntity<List<VolumeDTO>> getVolume() {
        return ResponseEntity.ok(statsService.getVolumePerWorkout());
    }

    @GetMapping("/personal-records")
    public ResponseEntity<List<PersonalRecordDTO>> getPersonalRecords() {
        return ResponseEntity.ok(statsService.getPersonalRecords());
    }

    @GetMapping("/progression/{exerciseName}")
    public ResponseEntity<List<ProgressionDTO>> getProgression(@PathVariable String exerciseName) {
        return ResponseEntity.ok(statsService.getProgression(exerciseName));
    }

    @GetMapping("/frequency")
    public ResponseEntity<List<WorkoutFrequencyDTO>> getFrequency() {
        return ResponseEntity.ok(statsService.getWorkoutFrequency());
    }
}
