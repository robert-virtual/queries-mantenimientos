package com.example.queriesmantenimientos.tables.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class TableRequest {

    private String name;
    private int app_id;
}
