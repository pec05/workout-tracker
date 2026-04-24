package com.peccio.workout_tracker.dto;

import com.peccio.workout_tracker.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
