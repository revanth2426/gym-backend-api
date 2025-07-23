package com.gym.gymmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "plan_assignments")
@Data
@NamedEntityGraph(
    name = "PlanAssignment.withUserAndMembershipPlan",
    attributeNodes = {
        @NamedAttributeNode("user"),
        @NamedAttributeNode("membershipPlan")
    }
)
public class PlanAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer assignmentId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY) // Many assignments to one user
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // Many assignments to one plan
    @JoinColumn(name = "plan_id", nullable = false) // Foreign key column
    private MembershipPlan membershipPlan;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}