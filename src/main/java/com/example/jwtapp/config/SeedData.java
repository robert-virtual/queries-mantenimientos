package com.example.jwtapp.config;

import com.example.jwtapp.repository.RoleRepository;
import com.example.jwtapp.repository.UserRepository;
import com.example.jwtapp.model.Role;
import com.example.jwtapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SeedData {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.password}")
    private String adminPassword;
    private int roleId;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedRoles();
        seedUsers();
    }

    public void seedRoles() {
        Optional<Role> role = roleRepo.findOneByName("admin");
        if (role.isPresent()) {
            roleId = role.get().getId();
            return;
        }
        roleId = roleRepo.save(Role.builder().name("admin").build()).getId();
    }

    public void seedUsers() {
        Optional<User> user = userRepo.findOneByEmail(adminEmail);
        if (user.isPresent()) {
            return;
        }
        userRepo.save(
                User
                        .builder()
                        .email(adminEmail)
                        .password(new BCryptPasswordEncoder()
                                .encode(adminPassword))
                        .roles(List.of(Role.builder().id(roleId).build()))
                        .build()
        );
    }
}
