package com.peccio.workout_tracker.service;

import com.peccio.workout_tracker.dto.WorkoutDTO;
import com.peccio.workout_tracker.entity.User;
import com.peccio.workout_tracker.entity.Workout;
import com.peccio.workout_tracker.repository.UserRepository;
import com.peccio.workout_tracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public List<WorkoutDTO> getMyWorkouts() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return workoutRepository.findByUserIdOrderByDateDesc(user.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public WorkoutDTO getById(Long id) {
        return workoutRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Workout not found"));
    }

    public WorkoutDTO create(WorkoutDTO dto) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = new Workout();
        workout.setName(dto.getName());
        workout.setDate(dto.getDate());
        workout.setNotes(dto.getNotes());
        workout.setUser(user);

        return toDTO(workoutRepository.save(workout));
    }

    public WorkoutDTO update(Long id, WorkoutDTO dto) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setName(dto.getName());
        workout.setDate(dto.getDate());
        workout.setNotes(dto.getNotes());
        return toDTO(workoutRepository.save(workout));
    }

    public void delete(Long id) {
        workoutRepository.deleteById(id);
    }

    private WorkoutDTO toDTO(Workout workout) {
        return new WorkoutDTO(
                workout.getId(),
                workout.getName(),
                workout.getDate(),
                workout.getNotes(),
                workout.getUser().getId()
        );
    }
}
