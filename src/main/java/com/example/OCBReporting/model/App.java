package com.example.OCBReporting.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "t_apps")
@Data
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String status;
    @OneToMany
    @JoinColumn(name = "app_id")
    private List<Table> tables;

}
