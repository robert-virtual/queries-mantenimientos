package com.example.queriesmantenimientos.repository;

import com.example.queriesmantenimientos.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<Table,Long> {
    List<Table> findByAppId(int appId);
}
