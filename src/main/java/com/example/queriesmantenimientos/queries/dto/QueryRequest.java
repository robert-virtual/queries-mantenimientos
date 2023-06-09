package com.example.queriesmantenimientos.queries.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class QueryRequest {
    private long table_id;
    private int action_id;
    private Map<String,Object> parameters;
    private Map<String,Object> where;
}
