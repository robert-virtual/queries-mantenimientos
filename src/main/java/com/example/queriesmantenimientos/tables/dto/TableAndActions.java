package com.example.queriesmantenimientos.tables.dto;

import lombok.Data;

import java.util.List;

@Data
public class TableAndActions {
    private long table_id;
    private List<Integer> actions;
}
