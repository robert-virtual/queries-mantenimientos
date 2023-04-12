package com.example.queriesmantenimientos.repository;

import com.example.queriesmantenimientos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    List<Role> findByNameIn(List<String> names);
}
