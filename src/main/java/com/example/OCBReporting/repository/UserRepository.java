package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findOneByEmail(String email);
}
