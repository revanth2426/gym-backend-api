package com.gym.gymmanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class PlanAssignmentDTO {
    private Integer assignmentId; // Optional for creation

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Plan ID is required")
    private Integer planId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    // End date will be calculated by the service
}