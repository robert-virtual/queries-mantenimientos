package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.AuditLog;
import com.example.OCBReporting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
