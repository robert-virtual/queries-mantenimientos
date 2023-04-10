package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.Query;
import com.example.OCBReporting.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table,Long> {
}
