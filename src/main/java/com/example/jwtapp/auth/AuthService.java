package com.example.jwtapp.auth;

import com.example.jwtapp.config.JwtService;
import com.example.jwtapp.model.AuthCredentials;
import com.example.jwtapp.model.User;
import com.example.jwtapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return LoginResponse.builder().token(jwtService.generateToken(user)).build();
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
