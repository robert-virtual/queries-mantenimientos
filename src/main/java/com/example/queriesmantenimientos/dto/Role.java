package com.example.queriesmantenimientos.dto;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    private int id;
    private String name;

    List<Module> modules = new ArrayList<>();

}
