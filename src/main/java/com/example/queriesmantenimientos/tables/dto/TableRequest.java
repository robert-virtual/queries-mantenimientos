package com.example.queriesmantenimientos.tables.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TableRequest {

    private String name;
    private int app_id;
    List<Integer> actions;
}
