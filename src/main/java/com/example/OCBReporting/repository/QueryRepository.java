package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findAllByStatusOrderByRequestedAt(String status, Pageable pageable);

}
