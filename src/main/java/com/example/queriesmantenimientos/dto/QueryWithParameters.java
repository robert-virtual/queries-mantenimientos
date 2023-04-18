package com.example.queriesmantenimientos.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QueryWithParameters {
    String query;
    Object[] values;
}
