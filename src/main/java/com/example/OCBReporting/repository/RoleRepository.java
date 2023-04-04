package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    List<Role> findByNameIn(List<String> names);
}
