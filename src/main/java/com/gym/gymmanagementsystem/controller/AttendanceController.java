package com.gym.gymmanagementsystem.controller;

import com.gym.gymmanagementsystem.dto.AttendanceDTO;
import com.gym.gymmanagementsystem.model.Attendance;
import com.gym.gymmanagementsystem.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.gym.gymmanagementsystem.dto.AttendanceResponseDTO; // NEW IMPORT

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkin")
    public ResponseEntity<Attendance> checkInUser(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        try {
            Attendance attendance = attendanceService.recordAttendance(attendanceDTO.getUserId());
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // User not found
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Attendance>> getAttendanceByUserId(@PathVariable String userId) {
        try {
            List<Attendance> attendanceRecords = attendanceService.getAttendanceByUserId(userId);
            return ResponseEntity.ok(attendanceRecords);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/daily-counts")
    public ResponseEntity<Map<LocalDate, Long>> getDailyAttendanceCounts(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate) {
        Map<LocalDate, Long> dailyCounts = attendanceService.getDailyAttendanceCount(startDate, endDate);
        return ResponseEntity.ok(dailyCounts);
    }

    // MODIFIED: Returns List of AttendanceResponseDTO
    @GetMapping("/all")
    public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendance() {
        List<AttendanceResponseDTO> allAttendance = attendanceService.getAllAttendanceRecords();
        return ResponseEntity.ok(allAttendance);
    }
}