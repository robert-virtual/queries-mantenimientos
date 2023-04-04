package com.example.OCBReporting.config;

import com.example.OCBReporting.repository.RoleRepository;
import com.example.OCBReporting.repository.UserRepository;
import com.example.OCBReporting.model.Role;
import com.example.OCBReporting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SeedData {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.password}")
    private String adminPassword;
    private List<Integer> roleId;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedRoles();
        seedUsers();
    }

    public void seedRoles() {
        List<String> roles = List.of("query_authorizer", "query_creator", "user_creator");
        List<Role> role = roleRepo.findByNameIn(roles);
        if (new HashSet<>(role.stream().map(Role::getName).collect(Collectors.toList())).containsAll(roles)) {
            roleId = role.stream().map(Role::getId).collect(Collectors.toList());
            return;
        }
        roles = roles.stream().filter(x-> role.stream().noneMatch(y-> Objects.equals(y.getName(), x))).collect(Collectors.toList());
        roleId = roleRepo.saveAll(roles.stream().map(x -> Role.builder().name(x).build()).collect(Collectors.toList())).stream().map(Role::getId).collect(Collectors.toList());
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
                        .roles(roleId.stream().map(x -> Role.builder().id(x).build()).collect(Collectors.toList()))
                        .enabled(true)
                        .build()
        );
    }
}
