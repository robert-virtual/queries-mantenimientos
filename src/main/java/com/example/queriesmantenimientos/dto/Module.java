package com.example.queriesmantenimientos.dto;

import com.example.queriesmantenimientos.dto.Screen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Module {
    private int id;

    private String name;
    private String status;
    List<Screen> screens = new ArrayList<>();
}
