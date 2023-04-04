package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.App;
import com.example.OCBReporting.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field,Integer> {
}
