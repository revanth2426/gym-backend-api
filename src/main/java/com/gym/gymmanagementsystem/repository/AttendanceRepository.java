package com.gym.gymmanagementsystem.repository;

import com.gym.gymmanagementsystem.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph; // NEW IMPORT
import org.springframework.stereotype.Repository;

import java.time.LocalDate; // Keep if still used by findByEndDateBetween (it's not for attendance)
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Override
    @EntityGraph(value = "Attendance.withUser") // Apply entity graph here
    List<Attendance> findAll(); // Overriding default findAll to eager fetch

    // (No other changes needed for AttendanceRepository)
}