package com.example.OCBReporting.model;

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
    private String response;
    private String status;
    @ManyToOne
    @JoinColumn(name = "requested_by")
    private User requestedBy;
    @ManyToOne
    @JoinColumn(name = "authorized_by")
    private User authorizedBy;
    private LocalDateTime requestedAt;
    private LocalDateTime authorizedAt;
    public static final String STATUS_REQUESTED = "requested";
    public static final String STATUS_AUTHORIZED = "authorized";
}
