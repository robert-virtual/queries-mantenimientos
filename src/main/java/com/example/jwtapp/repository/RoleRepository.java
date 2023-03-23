package com.example.jwtapp.repository;

import com.example.jwtapp.model.Role;
import com.example.jwtapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findOneByName(String name);
}
