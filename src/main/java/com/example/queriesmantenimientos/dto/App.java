package com.example.queriesmantenimientos.dto;

import com.example.queriesmantenimientos.model.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class App {
    private int id;
    private String name;
    private String status;
    private List<Table> tables;
    public static final String STATUS_ACTIVE = "active";
}
