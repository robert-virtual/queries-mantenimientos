package com.example.queriesmantenimientos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "t_tables")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(name = "app_id")
    private int appId;
    @OneToMany
    @JoinColumn(name = "table_id")
    List<Field> fields = new ArrayList<>();

}
