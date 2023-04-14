package com.example.queriesmantenimientos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    public static final String STATUS_ACTIVE = "active";
    public static final String MISSING_USER_CREATOR_ROLE_ERROR = "You do not have the required role to create users. Contact with the person that created your user to update your roles";
    private int id;
    private String name;
    private String lastname;
    private String email;
    private LocalDateTime passwordUpdatedAt;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private int failedLoginAttempts;
    private String status;
    private boolean enabled = true;

    private List<Role> roles = new ArrayList<>();
    private List<App> apps = new ArrayList<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

}
