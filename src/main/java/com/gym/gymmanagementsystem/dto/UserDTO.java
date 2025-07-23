package com.gym.gymmanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

@Data
public class UserDTO {
    private String userId; // Optional for creation, required for update

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    private String membershipStatus; // Can be set by the system or admin

    @PastOrPresent(message = "Joining date cannot be in the future")
    private LocalDate joiningDate;
}