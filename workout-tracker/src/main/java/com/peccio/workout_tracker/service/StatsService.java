package com.peccio.workout_tracker.service;

import com.peccio.workout_tracker.dto.PersonalRecordDTO;
import com.peccio.workout_tracker.dto.ProgressionDTO;
import com.peccio.workout_tracker.dto.VolumeDTO;
import com.peccio.workout_tracker.dto.WorkoutFrequencyDTO;
import com.peccio.workout_tracker.entity.User;
import com.peccio.workout_tracker.entity.WorkoutSet;
import com.peccio.workout_tracker.repository.UserRepository;
import com.peccio.workout_tracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;


    // 1. get current user
    // 2. get all their workouts
    // 3. for each workout, sum(reps × weight) across all sets
    public List<VolumeDTO> getVolumePerWorkout() {
        User user = getCurrentUser();
        return workoutRepository.findByUserIdOrderByDateDesc(user.getId())
                .stream()
                .map(workout -> {
                    double totalVolume = workout.getExercises().stream()
                            .flatMap(we -> we.getSets().stream())
                            .mapToDouble(set -> set.getReps() * set.getWeight())
                            .sum();
                    return new VolumeDTO(workout.getDate(), workout.getName(), totalVolume);
                })
                .toList();
    }

    public List<PersonalRecordDTO> getPersonalRecords() {
        User user = getCurrentUser();
        Map<String, PersonalRecordDTO> records = new HashMap<>();

        workoutRepository.findByUserIdOrderByDateDesc(user.getId())
                .forEach(workout ->
                        workout.getExercises().forEach(we ->
                                we.getSets().forEach(set -> {
                                    String exerciseName = we.getExercise().getName();
                                    if (!records.containsKey(exerciseName) ||
                                            set.getWeight() > records.get(exerciseName).getMaxWeight()) {
                                        records.put(exerciseName, new PersonalRecordDTO(
                                                exerciseName,
                                                set.getWeight(),
                                                set.getReps(),
                                                workout.getDate()
                                        ));
                                    }
                                })
                        )
                );

        return new ArrayList<>(records.values());
    }

    public List<ProgressionDTO> getProgression(String exerciseName) {
        User user = getCurrentUser();
        return workoutRepository.findByUserIdOrderByDateDesc(user.getId())
                .stream()
                .flatMap(workout -> workout.getExercises().stream()
                        .filter(we -> we.getExercise().getName().equalsIgnoreCase(exerciseName))
                        .map(we -> {
                            double maxWeight = we.getSets().stream()
                                    .mapToDouble(WorkoutSet::getWeight)
                                    .max()
                                    .orElse(0);
                            return new ProgressionDTO(exerciseName, workout.getDate(), maxWeight);
                        })
                )
                .toList();
    }

    public List<WorkoutFrequencyDTO> getWorkoutFrequency() {
        User user = getCurrentUser();
        return workoutRepository.findByUserIdOrderByDateDesc(user.getId())
                .stream()
                .collect(Collectors.groupingBy(
                        workout -> {
                            LocalDateTime date = workout.getDate();
                            int week = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                            int year = date.getYear();
                            return year + "-W" + String.format("%02d", week);
                        },
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(e -> new WorkoutFrequencyDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(WorkoutFrequencyDTO::getWeek).reversed())
                .toList();
    }

    private User getCurrentUser() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
