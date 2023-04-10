package com.example.OCBReporting.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "t_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_module_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    List<Module> modules = new ArrayList<>();

    public static String QUERY_AUTHORIZER = "query_authorizer";
    public static String QUERY_CREATOR = "query_creator";
    public static String USER_CREATOR = "user_creator";
}
