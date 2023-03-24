package com.example.OCBReporting.auth;

import com.example.OCBReporting.model.AuthCredentials;
import com.example.OCBReporting.model.BasicResponse;
import com.example.OCBReporting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/check-token")
    public ResponseEntity<Boolean> checkToken() {
        System.out.println("check-token");
        return ResponseEntity.ok(
                true
        );
    }

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
