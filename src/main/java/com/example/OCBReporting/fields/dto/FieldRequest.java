package com.example.OCBReporting.fields.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldRequest {

    private String name;
    private String type;
    private long table_id;
}
