package com.gym.gymmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "attendance")
@Data
@NamedEntityGraph(
    name = "Attendance.withUser",
    attributeNodes = {
        @NamedAttributeNode("user")
    }
)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Integer attendanceId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key
    private User user;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;
}