package com.example.queriesmantenimientos.repository;

import com.example.queriesmantenimientos.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
