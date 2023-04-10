package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.Query;
import com.example.OCBReporting.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<Table,Long> {
    List<Table> findByAppId(int appId);
}
