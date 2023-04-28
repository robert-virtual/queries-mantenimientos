package com.example.queriesmantenimientos;

import com.example.queriesmantenimientos.config.JwtService;
import com.example.queriesmantenimientos.model.AuditLog;
import com.example.queriesmantenimientos.model.User;
import com.example.queriesmantenimientos.repository.AuditLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepo;
    private final JwtService jwtService;

    public void audit(
            String action,
            Object data,
            User user
    ) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            auditLogRepo.save(
                    AuditLog
                            .builder()
                            .userId(user.getId())
                            .action(action)
                            .data_json(objectMapper.writeValueAsString(data))
                            .date(LocalDateTime.now())
                            .build()
            );
        } catch (JsonProcessingException e) {
            auditLogRepo.save(
                    AuditLog
                            .builder()
                            .userId(user.getId())
                            .action(action)
                            .data_json(data.toString())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    public void audit(
            String action,
            Object data,
            String authorization
    ) {

        User user = null;
        try {
            user = jwtService.getUser(authorization);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        audit(action, data, user);
    }

}
