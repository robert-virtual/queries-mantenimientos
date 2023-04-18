package com.example.queriesmantenimientos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "t_queries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Query {
    public static final int ACTION_INSERT = 1;
    public static final int ACTION_UPDATE = 2;
    public static final int ACTION_DELETE = 3;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;
    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;
    private String parameters;
    private String whereCondition;
    private String response;
    private String status;
    private int requestedBy;
    @Column(columnDefinition = "int null")
    private int authorizedBy;
    private LocalDateTime requestedAt;
    private LocalDateTime authorizedAt;
    public static final String STATUS_REQUESTED = "requested";
    public static final String STATUS_AUTHORIZED = "authorized";
}
