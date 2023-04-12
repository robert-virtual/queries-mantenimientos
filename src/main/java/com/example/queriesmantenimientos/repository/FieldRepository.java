package com.example.queriesmantenimientos.repository;

import com.example.queriesmantenimientos.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field,Long> {
    List<Field> findByTableId(long tableId);
}
