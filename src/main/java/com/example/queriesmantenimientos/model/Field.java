package com.example.queriesmantenimientos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "t_fields")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;
    @Column(name = "table_id")
    private long tableId;

}
