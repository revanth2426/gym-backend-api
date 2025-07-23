package com.gym.gymmanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PlanAssignmentResponseDTO {
    private Integer assignmentId;
    private String userName; // To hold the user's name
    private String planName; // To hold the plan's name
    private LocalDate startDate;
    private LocalDate endDate;

    // Optionally, if you need the IDs for frontend logic:
    private String userId;
    private Integer planId;
}