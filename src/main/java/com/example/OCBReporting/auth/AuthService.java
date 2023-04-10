package com.example.OCBReporting.auth;

import com.example.OCBReporting.auth.dto.LoginResponse;
import com.example.OCBReporting.auth.dto.UserRequest;
import com.example.OCBReporting.config.JwtService;
import com.example.OCBReporting.auth.dto.AuthCredentials;
import com.example.OCBReporting.model.App;
import com.example.OCBReporting.model.Role;
import com.example.OCBReporting.model.User;
import com.example.OCBReporting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public LoginResponse login(AuthCredentials authCredentials) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authCredentials.getEmail(),
                        authCredentials.getPassword()
                )
        );
        User user = userRepo.findOneByEmail(authCredentials.getEmail()).orElseThrow();
        user.setLastLogin(LocalDateTime.now());
        userRepo.save(user);
        return LoginResponse.builder().token(jwtService.generateToken(user)).user(user).build();
    }


    public User register(UserRequest user) throws Exception {
        if (
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().noneMatch(x -> Objects.equals(x.getAuthority(), Role.USER_CREATOR))
        ) throw new Exception(User.MISSING_USER_CREATOR_ROLE_ERROR);
        Optional<User> userExist = userRepo.findOneByEmail(user.getEmail());
        if (userExist.isPresent()) throw new Exception("Email taken");
        return userRepo.save(
                User.builder()
                        .name(user.getName())
                        .lastname(user.getLastname())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .roles(user.getRoles().stream().map(x -> Role.builder().id(x).build()).collect(Collectors.toList()))
                        .apps(user.getApps().stream().map(x -> App.builder().id(x).build()).collect(Collectors.toList()))
                        .failedLoginAttempts(0)
                        .status(User.STATUS_ACTIVE)
                        .enabled(true)
                        .createdAt(LocalDateTime.now())
                        .passwordUpdatedAt(LocalDateTime.now())
                        .email(user.getEmail())
                        .build()
        );
    }
}
