package com.gym.gymmanagementsystem.service;

import com.gym.gymmanagementsystem.model.Attendance;
import com.gym.gymmanagementsystem.model.User;
import com.gym.gymmanagementsystem.repository.AttendanceRepository;
import com.gym.gymmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gym.gymmanagementsystem.dto.AttendanceResponseDTO; // NEW IMPORT

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository; // Still needed for recordAttendance logic

    public Attendance recordAttendance(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setCheckInTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        // Note: This returns raw Attendance entities. If used directly for display, it might have similar issues.
        // For now, it's primarily used in the Dashboard for map data, not direct display.
        return user.getAttendanceRecords();
    }

    public Map<LocalDate, Long> getDailyAttendanceCount(LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendanceList = attendanceRepository.findAll(); // This findAll will now eager fetch user

        return attendanceList.stream()
                .filter(a -> !a.getCheckInTime().toLocalDate().isBefore(startDate) &&
                             !a.getCheckInTime().toLocalDate().isAfter(endDate))
                .collect(Collectors.groupingBy(
                        a -> a.getCheckInTime().toLocalDate(),
                        Collectors.counting()
                ));
    }

    // MODIFIED: Return List of AttendanceResponseDTO
    public List<AttendanceResponseDTO> getAllAttendanceRecords() {
        List<Attendance> allAttendance = attendanceRepository.findAll(); // This findAll will now eager fetch user

        return allAttendance.stream().map(attendance -> {
            AttendanceResponseDTO dto = new AttendanceResponseDTO();
            dto.setAttendanceId(attendance.getAttendanceId());
            dto.setUserId(attendance.getUser() != null ? attendance.getUser().getUserId() : null); // Populate userId
            dto.setUserName(attendance.getUser() != null ? attendance.getUser().getName() : "N/A"); // Populate userName
            dto.setCheckInTime(attendance.getCheckInTime());
            return dto;
        }).collect(Collectors.toList());
    }
}