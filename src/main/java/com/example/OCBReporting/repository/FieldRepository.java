package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.App;
import com.example.OCBReporting.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field,Long> {
    List<Field> findByTableId(long tableId);
}
