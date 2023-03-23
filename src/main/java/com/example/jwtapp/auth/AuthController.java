package com.example.jwtapp.auth;

import com.example.jwtapp.model.AuthCredentials;
import com.example.jwtapp.model.BasicResponse;
import com.example.jwtapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BasicResponse<User>> register(@RequestBody User user) {
        return ResponseEntity.ok(BasicResponse.<User>builder().data(authService.register(user)).build());
    }

    @PostMapping("/login")
    public ResponseEntity<BasicResponse<LoginResponse>> login(@RequestBody AuthCredentials authCredentials) {
        return ResponseEntity.ok(
                BasicResponse.<LoginResponse>builder().data(authService.login(authCredentials)).build()
        );
    }

}
