package com.example.OCBReporting.auth;

import com.example.OCBReporting.auth.dto.LoginResponse;
import com.example.OCBReporting.config.JwtService;
import com.example.OCBReporting.auth.dto.AuthCredentials;
import com.example.OCBReporting.model.User;
import com.example.OCBReporting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
