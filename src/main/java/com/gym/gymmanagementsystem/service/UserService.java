package com.gym.gymmanagementsystem.service;

import com.gym.gymmanagementsystem.dto.UserDTO;
import com.gym.gymmanagementsystem.model.User;
import com.gym.gymmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // To generate unique User IDs

@Service // Marks this class as a Spring Service component
public class UserService {

    @Autowired // Injects an instance of UserRepository
    private UserRepository userRepository;

    // Method to add a new user
    public User addUser(User user) {
        // Generate a unique UserID if not provided (or if auto-generated is preferred)
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(UUID.randomUUID().toString());
        }
        if (user.getJoiningDate() == null) {
            user.setJoiningDate(LocalDate.now());
        }
        if (user.getMembershipStatus() == null || user.getMembershipStatus().isEmpty()) {
            user.setMembershipStatus("Inactive"); // Default status
        }
        return userRepository.save(user);
    }

    // Method to get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Method to get a user by ID
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    // Method to update an existing user
    // BEFORE: public User updateUser(String userId, User userDetails) {
public User updateUser(String userId, UserDTO userDTO) { // Change parameter to UserDTO
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    // Copy properties from DTO to entity
    user.setName(userDTO.getName());
    user.setAge(userDTO.getAge());
    user.setGender(userDTO.getGender());
    user.setContactNumber(userDTO.getContactNumber());
    // Only update membershipStatus if it's provided in the DTO (optional)
    if (userDTO.getMembershipStatus() != null && !userDTO.getMembershipStatus().isEmpty()) {
        user.setMembershipStatus(userDTO.getMembershipStatus());
    }
    // Only update joiningDate if it's provided (optional, typically joining date isn't updated)
    if (userDTO.getJoiningDate() != null) {
        user.setJoiningDate(userDTO.getJoiningDate());
    }

    return userRepository.save(user);
}

    public User updateUser(String userId, User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)); // Custom exception handling can be added later

        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());
        user.setGender(userDetails.getGender());
        user.setContactNumber(userDetails.getContactNumber());
        user.setMembershipStatus(userDetails.getMembershipStatus());
        user.setJoiningDate(userDetails.getJoiningDate()); // Allow updating joining date if needed, or remove this line

        return userRepository.save(user);
    }

    // Method to delete a user
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}