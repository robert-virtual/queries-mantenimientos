package com.example.queriesmantenimientos.model;

import com.example.queriesmantenimientos.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "t_audit_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String action;
    private String data_json;
    private int userId;
    private LocalDateTime date;

}
