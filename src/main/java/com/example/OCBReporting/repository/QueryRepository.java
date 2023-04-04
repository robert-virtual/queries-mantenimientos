package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.Query;
import com.example.OCBReporting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueryRepository extends JpaRepository<Query,Integer> {
}
