package com.gym.gymmanagementsystem.repository;

import com.gym.gymmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String name);
    List<User> findByMembershipStatus(String status); // Added this line
}