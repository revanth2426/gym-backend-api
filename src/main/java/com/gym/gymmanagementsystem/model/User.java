package com.gym.gymmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data; // From Lombok dependency
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity // Marks this class as a JPA entity
@Table(name = "users") // Maps this entity to the 'users' table in the DB
@Data // Lombok annotation to auto-generate getters, setters, equals, hashCode, toString
public class User {

    @Id // Marks this field as the primary key
    @Column(name = "user_id", unique = true, nullable = false)
    private String userId; // We'll generate this manually or via service

    @Column(name = "name", nullable = false)
    private String name;

    private Integer age;

    private String gender;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "membership_status")
    private String membershipStatus; // e.g., "Active", "Expired", "Pending"

    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    // Relationship to PlanAssignment (OneToMany: one user can have many plan assignments)
    // mappedBy refers to the field name in the 'PlanAssignment' entity that owns the relationship
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<PlanAssignment> planAssignments;

    // Relationship to Attendance (OneToMany: one user can have many attendance records)
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Attendance> attendanceRecords;
}
